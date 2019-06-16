package dkd.business.dataParamConfig.service;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dkd.business.dataParamConfig.dao.AllocationPriorityHeadDao;
import dkd.business.dataParamConfig.domain.AllocationPriorityDetail;
import dkd.business.dataParamConfig.domain.AllocationPriorityHead;
import dkd.business.project.service.ProjectService;
import dkd.paltform.base.dao.BaseDao;
import dkd.paltform.base.service.BaseService;
import dkd.paltform.dictionary.service.DictionaryService;
import dkd.paltform.sequence.service.SequenceService;
import dkd.paltform.util.Common;
import dkd.paltform.util.StringUtils;

@Service
@Transactional
public class AllocationPriorityHeadService extends BaseService<AllocationPriorityHead>{
	@Autowired
	private AllocationPriorityHeadDao allocationPriorityHeadDao;
	@Autowired
	private ProjectService projectService;
	@Autowired
	private DictionaryService dictionaryService;
	@Autowired
	private SequenceService sequenceService;
	@Override
	public BaseDao<AllocationPriorityHead> getDao() {
		return allocationPriorityHeadDao;
	}
	/**
	 * @date 2017年11月23日
	 * @param allocationPriorityHead
	 * 描述：添加
	 */
	public void saveallocationPriority(AllocationPriorityHead allocationPriorityHead) {
		String projectId = projectService.getProjectId(allocationPriorityHead.getJob_no());
		allocationPriorityHead.setProject(projectId);
		if(StringUtils.isEmpty(allocationPriorityHead.getId())){
			String no = sequenceService.getFlowNo("allocationPri", null)+"";
			allocationPriorityHead.setRule_no("MDR"+Common.formatCode(no, 9));
			for(AllocationPriorityDetail detail:allocationPriorityHead.getAllocationPriorityDetail()){
				detail.setAllocationPriorityHead(allocationPriorityHead);
			}
			allocationPriorityHeadDao.create(allocationPriorityHead);
		}else{
			allocationPriorityHeadDao.update(allocationPriorityHead);
		}
	}
	/**
	 * @date 2017年11月27日
	 * @author wzm
	 * @param id
	 * @param project
	 * @param major
	 * 描述：校验是否存在
	 * @return 
	 */
	public List<AllocationPriorityHead> checkIsData(String id, String project, String major) {
		Map<String,Object> map = new HashMap<String, Object>();
		if (null!=id) {
			map.put("id", id);
		}
		map.put("project", project);
		map.put("major", dictionaryService.findByID(major));
		return allocationPriorityHeadDao.checkIsData(map);
	}
	
}
