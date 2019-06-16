package dkd.paltform.flowset.service;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;

import dkd.business.project.service.ProjectService;
import dkd.paltform.authority.domain.Role;
import dkd.paltform.authority.domain.User;
import dkd.paltform.base.BusinessException;
import dkd.paltform.base.dao.BaseDao;
import dkd.paltform.base.service.BaseService;
import dkd.paltform.flowset.dao.FlowsetDao;
import dkd.paltform.flowset.domain.Flowset;
import dkd.paltform.flowset.domain.FlowsetDetail;
import dkd.paltform.flowset.domain.FlowsetDetailRole;
import dkd.paltform.flowset.domain.ProcessRecord;
import dkd.paltform.flowset.domain.ProcessRecordRole;
import dkd.paltform.util.Common;
import dkd.paltform.util.HttpClientUtil;
import dkd.paltform.util.MailSend;
import dkd.paltform.util.SpringUtil;
import dkd.paltform.util.StringUtils;

@Service
@Transactional
public class FlowsetService extends BaseService<Flowset>{
	@Autowired
	private FlowsetDao flowsetDao;
	@Autowired
	private ProcessRecordService processRecordService;
	@Autowired
	private ProcessRecordRoleService processRecordRoleService;
	@Autowired
	private FlowsetDetailService flowsetDetailService;
	@Autowired
	private ProjectService projectService;
	@Override
	public BaseDao<Flowset> getDao() {
		return flowsetDao;
	}
	@Transactional(propagation = Propagation.REQUIRED)
	public void save(Flowset flowset){
		if(StringUtils.isEmpty(flowset.getId())){
			if (findByCode(flowset.getCode()) != null) {
				throw new BusinessException(-1, "code", "审批流程编号已经存在");
			}
			flowsetDao.create(flowset);
		}else{
			if (findByCode(flowset.getCode(), flowset.getId()) != null) {
				throw new BusinessException(-1, "code", "审批流程编号已经存在");
			}
			flowsetDao.update(flowset);
		}
	}
	@Transactional(propagation = Propagation.REQUIRED)
	public void deleteFlowset(String id){
		List<FlowsetDetail> details = findByID(id).getFlowset_details();
		for (FlowsetDetail detail : details){
			flowsetDetailService.delete(detail.getId());			
		}
		delete(id);
	}
	/**
	 * 
	 * @date 2017-8-29
	 * @author wzm
	 * @param record_code 审批类型代码
	 * @param work_no 业务单据编号
	 * @return 错误信息，若成功返回空值
	 * 描述：根据系统配置初始化审批流程
	 */
	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.REQUIRED)
	public String initRecord(String record_code, String work_no,String job_no) {
		Flowset flowset = flowsetDao.findByCode(record_code);
		List<FlowsetDetail> flowsetDetails = flowset.getFlowset_details();
		processRecordService.deleteByWorkNo(work_no);
		boolean isFirst = true;
		String userIds,userNames,userMails;
		int size;
		Map<String,Object> project = null;
		List<Map<String,List<Map<String,String>>>> role_users = null;
		if(StringUtils.isNotEmpty(job_no)){
			project = projectService.getProjectData(job_no);
			role_users = (List<Map<String,List<Map<String,String>>>>)project.get("role_users");
		}
		for(FlowsetDetail flowsetDetail : flowsetDetails){
			if(flowsetDetail.isIs_validate()){
				ProcessRecord processRecord = new ProcessRecord();
				processRecord.setName(flowsetDetail.getName());
				processRecord.setOrder_no(flowsetDetail.getOrder_no());
				processRecord.setWork_no(work_no);
				processRecord.setFlowset_detail(flowsetDetail);
				processRecord.setIs_complete(false);
				processRecord.setIs_check(false);
				processRecord.setProcess_record_role(new ArrayList<ProcessRecordRole>());
				for(FlowsetDetailRole detail_role : flowsetDetail.getDetail_roles()){
					ProcessRecordRole processRecordRole = new ProcessRecordRole();
					processRecordRole.setProcess_record(processRecord);
					processRecordRole.setRole_id(detail_role.getRole_id());
					processRecordRole.setRole_name(detail_role.getRole_name());
					processRecordRole.setIs_check(false);
					processRecordRole.setIs_pass(false);
					if(project != null && Common.isNotEmpty(role_users)){
						for(Map<String,List<Map<String,String>>> roles : role_users){
							if(roles.containsKey(detail_role.getRole_id()) && Common.isNotEmpty(roles.get(detail_role.getRole_id()))){
								userIds = "";
								userMails = "";
								userNames = "";
								size = 0;
								for(Map<String,String> user : roles.get(detail_role.getRole_id())){
									if(++size > 10){
										break;
									}
									userIds += user.get("id") + ",";
									userNames += user.get("name") + ",";
									userMails += user.get("email") + ",";
								}
								processRecordRole.setPend_check_user(userIds.substring(0, userIds.length() - 1));
								processRecordRole.setPend_check_user_name(userNames.substring(0, userNames.length() - 1));
								processRecordRole.setPend_check_user_mail(userMails.substring(0, userMails.length() - 1));
							}
						}
					}
					processRecord.getProcess_record_role().add(processRecordRole);
				}
				processRecordService.create(processRecord);
				if(isFirst){
					sendRemindEmail(processRecord);
					isFirst = false;
				}
			}
		}
		if(isFirst){
			return "该流程下没有可用节点！";
		}else{
			return "";
		}
	}
	/**
	 * 
	 * @date 2017-8-29
	 * @author wzm
	 * @param work_no 业务单据编号
	 * @param is_pass 是否通过
	 * @param check_opinion 审批以及
	 * @return 错误信息，若成功返回空
	 * @throws Exception
	 * 描述：业务单据审批操作（通过/退回），会根据配置回调系统中设置的成功、失败函数，并修改流程中定义的业务单据字段。
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public String change(String work_no,boolean is_pass,String check_opinion) throws Exception {
		//step1:根据单据号查询待审核的第一条审核记录
		ProcessRecord processRecord = processRecordService.selectByWorkNo(work_no);
		
		Object service = null;
		FlowsetDetail flowsetDetail = processRecord.getFlowset_detail();
		Flowset flowset = flowsetDetail.getFlowset();
		String service_bean = flowset.getService_bean();
		if(StringUtils.isNotEmpty(service_bean)){
			service = SpringUtil.getBean(service_bean);
		}
		User checkUser = SpringUtil.getCurrentUser();
		List<Role> urs = SpringUtil.getUserRoles();
		
		//step2:变更审核记录
		boolean is_all_check = true;
		String record_role_id = null;
		List<Map<String,Object>> record_roles = processRecordRoleService.selectByRecord(processRecord.getId());
		//获取当前用户对应的审核角色节点，角色按照名称排序，以第一个匹配到的为准
		outer:
		for(Map<String,Object> map : record_roles){
			for(Role ur : urs){
				if(map.get("roleid").equals(ur.getId()) && map.containsKey("pend_check_user") &&
					map.get("pend_check_user").toString().indexOf(checkUser.getId()) >= 0){
					record_role_id = map.get("recordid").toString();
					break outer;
				}
			}
		}
		if(record_role_id == null){
			return "抱歉，您无权审核该单据。";
		}
		for(ProcessRecordRole record_role : processRecord.getProcess_record_role()){
			if(record_role.getId().equals(record_role_id)){
				//修改该节点信息
				record_role.setCheck_date(new Date());
				record_role.setIs_check(true);
				record_role.setIs_pass(is_pass);
				record_role.setCheck_opinion(check_opinion);
				record_role.setCheck_user_id(checkUser.getId());
				record_role.setCheck_user_name(checkUser.getName());
				processRecordRoleService.update(record_role);
			}else{
				//若流程节点设置全部审核，则统计该节点下其余节点是否审核；若有审核不通过的则不会执行本流程，故统计是否审核字段即可。
				if(flowsetDetail.isIs_parallel_all() && !record_role.isIs_check()){
					is_all_check = false;
				}
			}
		}
		
		if(is_pass){
			if(is_all_check){
				//该节点下所有角色全部审核，即该节点审核通过
				processRecord.setIs_check(true);
				processRecord.setIs_complete(true);
				processRecordService.update(processRecord);
				//调用审批流程节点上定义的成功函数
				handleMethod(service,flowsetDetail.getHandle_pass_method(),work_no);
				
				//下级节点
				ProcessRecord next = processRecordService.selectByWorkNo(work_no);
				if(next != null && !next.getId().equals(processRecord.getId())){
					//根据配置发送邮件
					sendRemindEmail(next);
				}else{
					//没有下级节点，表明审批通过
					//修改对应业务表记录
					processRecordService.updateSqlByPass(flowset,work_no);
					//调用审批流程节点上定义的成功函数
					handleMethod(service,flowset.getHandle_pass_method(),work_no);
				}
			}
		}else{
			//拒绝
			processRecord.setIs_check(true);
			processRecord.setIs_complete(false);
			processRecordService.update(processRecord);
			//删除待审核节点记录
			processRecordService.deleteByUnPass(work_no);
			//修改对应业务表记录
			processRecordService.updateSqlByUnPass(flowset,work_no);
			//调用审批流程上定义的失败函数
			handleMethod(service,flowset.getHandle_unpass_method(),work_no);
		}
		return "";
	}
	/**
	 * 
	 * @date 2017年9月10日
	 * @author wzm
	 * @param work_no
	 * @return
	 * 描述：检查当前登录人是否可以审核该单据
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public boolean checkRole(String work_no) {
		ProcessRecord processRecord = processRecordService.selectByWorkNo(work_no);
		if(processRecord==null){
			return false;
		}
		User currentUser = SpringUtil.getCurrentUser();
		//当前用户所有的角色
		List<Role> urs = SpringUtil.getUserRoles();
		//审核所需要的角色
		List<Map<String,Object>> record_roles = processRecordRoleService.selectByRecord(processRecord.getId());
		//获取当前用户对应的审核角色节点，角色按照编码排序，以第一个匹配到的为准
		for(Map<String,Object> map : record_roles){
			for(Role ur : urs){
				if(map.get("roleid").equals(ur.getId()) && map.containsKey("pend_check_user") &&
						map.get("pend_check_user").toString().indexOf(currentUser.getId()) >= 0){
					return true;
				}
			}
		}
		return false;
	}
	/**
	 * 
	 * @date 2017-8-29
	 * @author wzm
	 * @param service 业务单据中的service实体名称
	 * @param method_name 回调方法名称
	 * @param work_no 业务单据编号
	 * @throws Exception
	 * 描述：执行成功或失败回调函数
	 */
	protected void handleMethod(Object service,String method_name,String work_no) throws Exception{
		if(service != null){
			if(StringUtils.isNotEmpty(method_name)){
				Method method = service.getClass().getMethod(method_name, String.class);
				if(method != null){
					method.invoke(service, work_no);
				}
			}
		}
	}
	/**
	 * 
	 * @date 2017-8-29
	 * @author wzm
	 * @param record 审批流程节点
	 * @param business_name 业务单据名称
	 * @param work_no 业务单据编号
	 * 描述：根据审批流程节点的配置给相关的审核角色人员发送提醒邮件
	 */
	protected void sendRemindEmail(ProcessRecord record) {
		if(record.getFlowset_detail().isIs_email()){
			MailSend mailSend = new MailSend();
			StringBuffer roleIds = new StringBuffer();
			String[] emails,names;
			for(ProcessRecordRole record_role : record.getProcess_record_role()){
				if(StringUtils.isNotEmpty(record_role.getPend_check_user_mail())){
					names = record_role.getPend_check_user_mail().split(",");
					emails = record_role.getPend_check_user_name().split(",");
					for(int i = 0;i < names.length;i++){
						mailSend.sendMessage("新的待审任务", names[i] + ":\r\n   您有一条新的待审任务：" + record.getFlowset_detail().getFlowset().getBusiness_name() + ",单据编号为" + record.getWork_no(), emails[i]);
					}
				}else{
					roleIds.append(record_role.getRole_id()).append(",");	
				}
			}
			
			if(StringUtils.isNotEmpty(roleIds.toString())){
				//获取系统配置文件
				InputStream in = this.getClass().getClassLoader().getResourceAsStream("system.properties");
				Properties prop = new Properties();
				try {
					prop.load(in);
				} catch (IOException e) {
					e.printStackTrace();
				}
				Map<String,String> params = new HashMap<String,String>();
				params.put("roleIds", roleIds.substring(0,roleIds.length() - 1));
				String result = HttpClientUtil.getUserCenterData("ssoServerSelectUserByRoleUrl", params);
				JSONObject userInfo = JSONObject.parseObject(result);
				List<User> users = JSONObject.parseArray(userInfo.getString("users"), User.class);
				if(users != null && !users.isEmpty()){
					for(User user : users){
						if(StringUtils.isNotEmpty(user.getEmail())){
							mailSend.sendMessage("新的待审任务", user.getName() + ":\r\n   您有一条新的待审任务：" + record.getFlowset_detail().getFlowset().getBusiness_name() + ",单据编号为" + record.getWork_no(), user.getEmail());
						}
					}
				}
			}
		}
	}
}
