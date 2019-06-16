package dkd.business.project.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dkd.business.project.dao.WorkHoursDao;
import dkd.business.project.domain.WorkHours;
import dkd.paltform.base.dao.BaseDao;
import dkd.paltform.base.service.BaseService;
import dkd.paltform.dictionary.service.DictionaryService;
import dkd.paltform.sequence.service.SequenceService;
import dkd.paltform.util.Common;

@Service
@Transactional
public class WorkHoursService extends BaseService<WorkHours>{
	@Autowired
	private WorkHoursDao workHoursDao;
	@Autowired
	private ProjectService projectService;
	@Autowired
	private SequenceService sequenceService;
	@Autowired
	private DictionaryService dictionaryService;

	@Override
	public BaseDao<WorkHours> getDao() {
		return workHoursDao;
	}

	/**
	 * 添加方法  修改方法
	 * @date 2017年11月15日
	 * @param workHours
	 * 描述：
	 */
	public void saveWorkHours(WorkHours workHours) {
        String projectId = projectService.getProjectId(workHours.getJob_no());
        workHours.setProject(projectId);
		if(StringUtils.isEmpty(workHours.getId())){
			String no = sequenceService.getFlowNo("workHour", null)+"";
			workHours.setHour_no("PMH"+Common.formatCode(no, 9));
			create(workHours);
		}
		else{
			update(workHours);
		}
	}
//	/**
//	 * 条件查询工时
//	 * @date 2017年11月16日
//	 * @author wzm
//	 * @param map
//	 * @return
//	 * 描述：
//	 */
//	public WorkHours findWH(Map<String, Object> map) {
//		List<WorkHours> findWHList = workHoursDao.findWH(map);
//		 return findWHList.size()==0?null:findWHList.get(0);
//	}
//
//	public Map<String, Object> findSum(Map<String, Object> map) {
//		 List<Map<String, Object>> findWHList = workHoursDao.findSum(map);
//		 return findWHList.size()==0?null:findWHList.get(0);
//	}

	/**
	 * @date 2017年12月4日
	 * @param statistical_month_edit
	 * @param project
	 * @param major
	 * @param id
	 * @return
	 * 描述：
	 */
	public WorkHours findWH(String statistical_month_edit, String project, String major, String id) {
		// TODO Auto-generated method stub
		Map<String,Object> map = new HashMap<String, Object>();
		if(null!=id){
			map.put("id", id);
		}
		map.put("statistical_month", statistical_month_edit);
		map.put("project", project);
		map.put("major", dictionaryService.findByID(major));
		List<WorkHours> findWHList = workHoursDao.findWH(map);
	    return findWHList.size()==0?null:findWHList.get(0);
	}
	/**
	 * @date 2017年12月4日
	 * @param statistical_month_edit
	 * @param project
	 * @param major
	 * @param id
	 * @return
	 * 描述：查询前几个月实际工时总和
	 */
	public Map<String, Object> findSum(String statistical_month_edit, String project, String major, String id) {
		// TODO Auto-generated method stub
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("statistical_month", statistical_month_edit);
		map.put("project", project);
		map.put("major", dictionaryService.findByID(major));
		if(null!=id){
			map.put("id", id);
		}
		List<Map<String, Object>> findWHList = workHoursDao.findSum(map);
		return findWHList.size()==0?null:findWHList.get(0);
	}


}
