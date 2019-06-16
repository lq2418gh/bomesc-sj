package dkd.paltform.util.bigExcelReaderExt;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.xml.sax.SAXException;

import dkd.business.dataParamConfig.service.DataManagerService;
import dkd.business.designDataManager.domain.StDataTrack;
import dkd.business.designDataManager.service.StDataTrackTableService;
import dkd.business.project.service.ProjectService;
import dkd.paltform.dictionary.service.DictionaryService;
import dkd.paltform.util.BigExcelReader;
import dkd.paltform.util.ConvertMapToDomainUtil;
import dkd.paltform.util.HttpClientUtil;
import dkd.paltform.util.MailSend;
import dkd.paltform.util.SpringUtil;

public class StImportReader extends BigExcelReader{
	private ProjectService projectService;
	private StDataTrackTableService stDataTrackTableService; 
	private Map<String,Map<String, Object>> trackDataMap;
	private String filePath;
	private String moduleName;
	private String jobNo;
	private String project;
	private List<String> list;
	private Date date;
	private DataManagerService dataManagerService;
	private DictionaryService dictionaryService;
	private Map<String,String> shopDrawRevMap;
	//存放加设图纸版本变换的新增数据
	private Map<String,Object> updateMap;
	private List<String> partNos;
	/**
	 * @param filename
	 * @param major
	 * @throws IOException
	 * @throws OpenXML4JException
	 * @throws SAXException
	 */
	public StImportReader(String filename, String major,Map<String,Map<String, Object>> trackDataMap,String moduleName,
			String jobNo,String project,Date date,Map<String,String> shopDrawRevMap,Map<String,Object> updateMap) throws IOException,OpenXML4JException, SAXException {
		super(filename, major);
		this.trackDataMap=trackDataMap;
		this.filePath=filename;
		this.moduleName=moduleName;
		this.jobNo=jobNo;
		this.project=project;
		this.date=date;
		this.shopDrawRevMap=shopDrawRevMap;
		this.projectService=(ProjectService) SpringUtil.getBean("projectService");
		this.stDataTrackTableService=(StDataTrackTableService) SpringUtil.getBean("stDataTrackTableService");
		this.dataManagerService=(DataManagerService) SpringUtil.getBean("dataManagerService");
		this.dictionaryService=(DictionaryService) SpringUtil.getBean("dictionaryService");
		this.list=new ArrayList<String>();
		this.partNos=new ArrayList<String>();
		this.updateMap=updateMap;
	}
	@Override
	protected void outputRow(Map<String, String> mapData, int[] rowTypes,
			int rowIndex) {
		String item = mapData.get("item");//获取该行excel数据的行号
		if(StringUtils.isNotEmpty(item)&&item.matches("^[0-9]*[1-9][0-9]*$")){//行号不为空，且是正整数，则该行数据可以导入
			if(partNos.contains(mapData.get("part_no"))){//校验零件编号是否为空
				list.add("第"+item+"行零件编号重复，请检查！");
				return;
			}else{
				partNos.add(mapData.get("part_no"));
			}
			try {
				if(StringUtils.equals(project, mapData.get("project_name"))&&StringUtils.equals(moduleName, mapData.get("module_name"))){//校验文件夹写的项目名称和excel数据中项目名称是否一致
					if(null!=projectService.findProjectByNameAndNo(project, mapData.get("job_no"))){//校验项目和工作号是否匹配
						handleStImport(trackDataMap,mapData,item);
					}else{
						trackDataMap.remove(mapData.get("part_no"));
						list.add("第"+item+"行项目名称和项目工作号不匹配！");
					}
				}else{//不一致的话则将数据库中查询出的该行对应的数据移除，最后剩下的为excel数据中没有的，这些是执行删除操作的数据，同时将错误信息添加到错误信息的list中，发送邮件时使用
					trackDataMap.remove(mapData.get("part_no"));
					list.add("第"+item+"行项目名称或者模块编号和文件夹对应的不匹配！");
				}
			} catch (Exception e) {
				trackDataMap.remove(mapData.get("part_no"));//发生了未知异常，同样移除，添加错误信息到list中
				list.add("第"+item+"行导入时发生未知异常,请联系系统管理员！");
				//这里添加未知异常导入失败时，插入失败数据到数据库相应表格的方法
				e.printStackTrace();
			}
		}
	}
	/**
	 * @date 2017年12月12日
	 * @author gxp
	 * @param trackDataMap 根据项目，专业，模块编号查询到的数据库中的数据
	 * @param ExcelData 读取到的excel中的每一行数据
	 * 描述：	将结构专业数据跟踪表中的一条数据导入到数据库中
	 * 		将excel中的这一行数据，按照零件编号在查询到的trackDataMap中看是否有数据；
	 * 		若没有相应的数据，则证明为新数据，执行插入操作
	 * 		若有相应的数据，则对比其他列，将变更列执行更新操作：更新时，需要将原先的那条is_history设置为Y，change_size，同时需要插入最新的那条数据
	 * @throws Exception 
	 */
	private void handleStImport(Map<String,Map<String, Object>> trackDataMap,Map<String,String> excelData,String item) throws Exception{
		String partNo = excelData.get("part_no");
		StDataTrack stDataTrack;
		String fieldCheckMsg;//校验方法返回的信息，返回ok则校验成功，返回其他错误信息则校验失败；
		String ifNullMsg;
		String shopDrawRev;
		if(StringUtils.isNotEmpty(partNo)&&null==trackDataMap.get(partNo)){//excel中的零件编号不为空，数据跟踪表中没有查询到相应的数据，为新数据，执行插入操作
			fieldCheckMsg = BigExcelReaderUtil.checkField(excelData,"StDataTrack");
			if(StringUtils.equals("ok", fieldCheckMsg)){//校验全数据字段类型，通过；此处为插入操作
				ifNullMsg = BigExcelReaderUtil.checkFieldIfNull(excelData, "StDataTrack");
				if(StringUtils.equals("ok", ifNullMsg)){//需要校验非空字段是否为空；
					//这里需要编写插入方法，循环遍历map后，实例化一个实体，直接插入操作
					stDataTrack=(StDataTrack)ConvertMapToDomainUtil.assemble(excelData, StDataTrack.class);
					stDataTrack.setIs_history(false);
					stDataTrack.setChange_size(0);
					stDataTrack.setUpdate_date(date);
					stDataTrack.setState("新增");
					stDataTrack.setProject(projectService.getProjectId(stDataTrack.getJob_no()));
					shopDrawRev=shopDrawRevMap.get(stDataTrack.getShop_draw_no());
					if(null!=shopDrawRev&&!shopDrawRev.equals(stDataTrack.getShop_draw_rev())){
						stDataTrack.setState_change_date(date);
						stDataTrack.setAdded_by_drawing_update("Y");
						updateMap.put(stDataTrack.getJob_no(), null==updateMap.get(stDataTrack.getJob_no())?stDataTrack.getPart_no():updateMap.get(stDataTrack.getJob_no())+","+stDataTrack.getPart_no());
					}
					stDataTrackTableService.create(stDataTrack);
				}else{
					list.add("第"+item+"行"+ifNullMsg);
				}
			}else{//校验数据，没有通过；原因：字段类型不匹配或者存在非空字段为空值的现象
				list.add("第"+item+"行"+fieldCheckMsg);//这里将异常信息存入list
			}
		}else if(StringUtils.isNotEmpty(partNo)&&null!=trackDataMap.get(partNo)){//excel中零件编号不为空，数据跟踪表中根据零件编号查询到了相应数据，则执行更新操作
			//首先校验数据
			Map<String, Object> trackData = trackDataMap.get(partNo);//获取到读取行对应的数据跟踪表的数据
			trackDataMap.remove(partNo);//移除excel中对应的零件编号在数据库查询出的结果集的记录，为了后面做删除操作，最后剩下的就是要删除的数据
			fieldCheckMsg = BigExcelReaderUtil.checkField(excelData,"StDataTrack");//校验每一列的有效性，包括字符串是否超过长度，数字是否为数字等；
			if(StringUtils.equals("ok", fieldCheckMsg)){//校验成功
				if(!BigExcelReaderUtil.isSame(excelData,trackData, "StDataTrack")){//假如有字段不相同，则执行更新操作
					//将数据库该行数据转换为相应实体
					stDataTrack=(StDataTrack)ConvertMapToDomainUtil.anotherAssemble(trackData, StDataTrack.class);
					stDataTrack.setIs_history(true);
					stDataTrack.setState("修改");
					stDataTrackTableService.update(stDataTrack);//更新了该条数据
					//将excel中读取出的一行数据更新到数据库读取出的零件编号对应的数据中
					Map<String, Object> newTrackData=BigExcelReaderUtil.updateTrackDataMap(trackData,excelData);//将excel中数据替换数据库中相应字段的值；假如trackData和newTrackData一样，则不
					stDataTrack=(StDataTrack)ConvertMapToDomainUtil.anotherAssemble(newTrackData, StDataTrack.class);
					stDataTrack.setId(null);
					stDataTrack.setState("新增");
					stDataTrack.setUpdate_date(date);
					stDataTrack.setIs_history(false);
					stDataTrack.setChange_size(stDataTrack.getChange_size()+1);
					stDataTrackTableService.create(stDataTrack);//插入新数据
				};
			}else{//数据校验没有通过，则该行数据插入到另一张表中；原因：数据校验未通过
				list.add("第"+item+"行"+fieldCheckMsg);
			}
		}else{//母材编号为空，也插入到另外的表中
			//这里需要将数据插入到另一张表中
			list.add("第"+item+"行零件编号为空值！");
		}
	}
	/**
	 * 结束解析后，删除所有的数据库中存在，excel中不存在的对应的零件编号的数据，为已删除数据
	 * 同时若有错误信息，插入导入失败提醒记录，以及给相应数据负责人发送邮件
	 * 参数的map中，key为对应的零件编号
	 */
	@Override
	protected void endParse(){
		super.endParse();
		deleteTrackData();
		BigExcelReaderUtil.createRecordAndSendEmail("ST", filePath, list, moduleName, jobNo);
	}
	/**
	 * @date 2017年12月21日
	 * @author gaoxp	
	 * 描述：删除数据跟踪表方法
	 */
	@SuppressWarnings("rawtypes")
	private void deleteTrackData(){
		//此处调用接口去采办系统查询数据
		Map<String,String> mapPurchase = this.validateMto();
		String result="[]";
		if(!mapPurchase.isEmpty()){
			result = HttpClientUtil.validateMto("ssoServerValidateMto",mapPurchase);
		}
		//result = "[{'TBJN0104-D-MTO-ST-0001':'2,1'},{'TBJN0104-D-MTO-ST-0001':'1,2'}]";
		JSONArray results = JSONArray.fromObject(result);
		Map<String,String> map = new HashMap<String, String>();
		String key="";
		for(int i=0;i<results.size();i++){
			JSONObject o = results.getJSONObject(i);
			Iterator it = o.keys(); 
			while (it.hasNext()) { 
				key = (String)it.next();
				map.put(key,o.getString(key));
			}
		}
		Boolean flag=false;
		Map<String,Object> cancelMap = new HashMap<String, Object>();
		for (Map.Entry<String, Map<String, Object>> entry : trackDataMap.entrySet()) {
			//执行删除   ，更改为历史记录
			Map<String, Object> trackData = trackDataMap.get(entry.getKey());//这条数据在excel中不存在
			try {
				StDataTrack stDataTrack = (StDataTrack) ConvertMapToDomainUtil.anotherAssemble(trackData, StDataTrack.class);
				stDataTrack.setIs_history(true);
				if(null!=map.get(stDataTrack.getMto_no())){
					String[] rowNos = map.get(stDataTrack.getMto_no()).split(",");
					for(int i=0;i<rowNos.length;i++){
						if(rowNos[i].equals(stDataTrack.getMto_row_no())){
							stDataTrack.setState_change_date(date);
							stDataTrack.setState("作废");
							cancelMap.put(stDataTrack.getJob_no(),null==cancelMap.get(stDataTrack.getJob_no())?stDataTrack.getPart_no():cancelMap.get(stDataTrack.getJob_no())+","+stDataTrack.getPart_no());
							flag = true;
							break;
						}
					}
					if(flag){
						flag=false;
						stDataTrackTableService.update(stDataTrack);//更新了该条数据为历史记录，状态改为删除
						continue;
					}
				}
				stDataTrack.setState("删除");
				stDataTrackTableService.update(stDataTrack);//更新了该条数据为历史记录，状态改为删除
			} catch (Exception e) {
				list.add("数据跟踪表零件编号为"+trackData.get("part_no")+"的数据删除时出现异常,请联系系统管理员！");
				e.printStackTrace();
			}
		}
		//此处发送邮件通知负责人及时处理确认（作废的数据）
		//this.stateConfirmSendMail("ST",cancelMap);
		//此处发送邮件通知负责人及时处理确认（图纸升版增加的数据）
		//sendMailAddUpdate("ST",updateMap);
	}
	public Map<String,String> validateMto(){
		//存储所有的料单
		Map<String,String> map = new HashMap<String, String>();
		//遍历所有的删除的数据跟踪表的数据，去采办中查找是否存在（返回存在的料单号和行号）
		for (Map.Entry<String, Map<String, Object>> entry : trackDataMap.entrySet()) {
			Map<String, Object> trackData = trackDataMap.get(entry.getKey());//这条数据在excel中不存在
			try {
				StDataTrack stDataTrack = (StDataTrack) ConvertMapToDomainUtil.anotherAssemble(trackData, StDataTrack.class);
				if(null!=stDataTrack&&null!=stDataTrack.getMto_no()){
					if(null!=stDataTrack.getMto_row_no()&&!"".equals(stDataTrack.getMto_row_no())){
						map.put(stDataTrack.getMto_no(), null==map.get(stDataTrack.getMto_no())?stDataTrack.getMto_row_no():map.get(stDataTrack.getMto_no())+","+stDataTrack.getMto_row_no());
					}else{
						list.add("数据跟踪表零件编号为"+trackData.get("part_no")+"的数据有料单号但无料单行号,请确认！");
					}
				}
			} catch (Exception e) {
				list.add("数据跟踪表零件编号为"+trackData.get("part_no")+"的数据转换时出现异常,请联系系统管理员！");
				e.printStackTrace();
			}
		}
		return map;
	}
	
	@SuppressWarnings("unused")
	private void stateConfirmSendMail(String major,Map<String,Object> map) {
		List<Map<String, Object>> majorList = dictionaryService.findProfessionalByAbb(major);//更具专业缩写获取专业信息，为了获取id
		MailSend mailSend = new MailSend();
		List<Map<String, Object>> managerDetails;
		//key为专业编号
		for(String key:map.keySet()){
			managerDetails = dataManagerService.findByJobNoAndMajor(key,(String)majorList.get(0).get("id"));
			//通过job_no和专业id获取相应的数据负责的明细,获取明细的为record设置;
			for(Map<String, Object> managerDetail:managerDetails){
				mailSend.sendMessage("结构专业数据跟踪表数据作废提醒", "零件编号为"+map.get(key)+"的状态的作废，请及时到系统数据变更提醒模块处理!", (String)managerDetail.get("email"));
			}
		}
	}
	//因图纸升版而增加发送给对应的数据负责人
	@SuppressWarnings("unused")
	private void sendMailAddUpdate(String major,Map<String, Object> updateMap) {
		List<Map<String, Object>> majorList = dictionaryService.findProfessionalByAbb(major);//更具专业缩写获取专业信息，为了获取id
		MailSend mailSend = new MailSend();
		List<Map<String, Object>> managerDetails;
		//key为专业编号
		for(String key:updateMap.keySet()){
			managerDetails = dataManagerService.findByJobNoAndMajor(key,(String)majorList.get(0).get("id"));
			//通过job_no和专业id获取相应的数据负责的明细,获取明细的为record设置;
			for(Map<String, Object> managerDetail:managerDetails){
				mailSend.sendMessage("结构专业数据跟踪表数据图纸升版增加确认提醒", "零件编号为"+updateMap.get(key)+"的数据因图纸升版而增加，请及时到系统数据变更提醒模块处理!", (String)managerDetail.get("email"));
			}
		}
	}
}
