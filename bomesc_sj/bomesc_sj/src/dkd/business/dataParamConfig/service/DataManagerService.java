package dkd.business.dataParamConfig.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dkd.business.dataParamConfig.dao.DataManagerDetailDao;
import dkd.business.dataParamConfig.dao.DataManagerHeadDao;
import dkd.business.dataParamConfig.domain.DataManagerDetail;
import dkd.business.dataParamConfig.domain.DataManagerHead;
import dkd.business.project.service.ProjectService;
import dkd.paltform.base.BusinessException;
import dkd.paltform.base.dao.BaseDao;
import dkd.paltform.base.service.BaseService;
import dkd.paltform.sequence.service.SequenceService;
import dkd.paltform.util.Common;
import dkd.paltform.util.StringUtils;
@Service
@Transactional
public class DataManagerService extends BaseService<DataManagerHead>{
	
	@Autowired 
	private DataManagerHeadDao dataManagerHeadDao;
	
	@Autowired
	private DataManagerDetailDao dataManagerDetailDao;
	
	@Autowired
	private ProjectService projectService;
	
	@Autowired
	private SequenceService sequenceService;

	@Override
	public BaseDao<DataManagerHead> getDao() {
		return dataManagerHeadDao;
	}

	public Boolean checkValidate(String project, String major,String id) {
		return dataManagerHeadDao.checkValidate(project,major,id).isEmpty()?false:true;
	}

	public String save(DataManagerHead dataManagerHead) {
		String projectId= projectService.getProjectId(dataManagerHead.getJob_no());
		dataManagerHead.setProject(projectId);
		if(StringUtils.isEmpty(dataManagerHead.getId())){
			String no = sequenceService.getFlowNo("dataManager", null)+"";
			dataManagerHead.setManager_no("DMS"+Common.formatCode(no,9));
			for(DataManagerDetail detail:dataManagerHead.getDataManagerHeads()){
				detail.setDataManagerHead(dataManagerHead);
			}
			dataManagerHeadDao.create(dataManagerHead);
		}else{
			for(DataManagerDetail detail:dataManagerHead.getDataManagerHeads()){
				detail.setDataManagerHead(dataManagerHead);
			}
			dataManagerHeadDao.update(dataManagerHead);
		}
		return dataManagerHead.getId();
	}
	
	public void delete(String id){
		try {
			DataManagerDetail detail = dataManagerDetailDao.findByID(id);
			dataManagerDetailDao.delete(id);
			flush();
			DataManagerHead head = dataManagerHeadDao.findByID(detail.getDataManagerHead().getId());
			if(null!=head){
				List<Map<String, Object>> details=dataManagerDetailDao.findByHead(head.getId());
				if(details.size()==0){
					dataManagerHeadDao.delete(head.getId());
				}
			}
		} catch (Exception e) {
			throw new BusinessException(-1,"提醒记录","提醒记录中已经有该数据负责人记录，不能删除,可配置是否提醒为否关闭该数据负责人！");
		}
	}
	/**
	 * @date 2017年12月20日
	 * @author gaoxp
	 * @param jobNo
	 * @param major
	 * @return
	 * 描述：通过工作号和专业查询所有的数据负责表体的id
	 */
	public List<Map<String, Object>> findByJobNoAndMajor(String jobNo,String major){
		return dataManagerHeadDao.findByJobNoAndMajor(jobNo, major);
	}
}
