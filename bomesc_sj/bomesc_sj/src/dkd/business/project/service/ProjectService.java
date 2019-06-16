package dkd.business.project.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import redis.clients.jedis.Jedis;
import dkd.business.project.dao.ProjectDao;
import dkd.business.project.domain.Project;
import dkd.paltform.base.dao.BaseDao;
import dkd.paltform.base.service.BaseService;
import dkd.paltform.dictionary.domain.Dictionary;
import dkd.paltform.dictionary.service.DictionaryService;
import dkd.paltform.util.RedisUtil;
import dkd.paltform.util.SerializationUtil;

@Service
@Transactional
public class ProjectService extends BaseService<Project>{
	
	@Autowired
	private DictionaryService dictionaryService;
	
	@Autowired
	private ProjectDao projectDao;
	
	@Override
	public BaseDao<Project> getDao() {
		return projectDao;
	}
	@SuppressWarnings("unchecked")
	public Map<String,Map<String,Object>> getProjectDatas() {
		Jedis jedis = RedisUtil.getJedis();
		byte[] bs = jedis.get("projects".getBytes());
		jedis.close();
		return (Map<String,Map<String,Object>>) SerializationUtil.deserialize(bs);
	}
	public Map<String,Object> getProjectData(String job_no) {
		return getProjectDatas().get(job_no);
	}
	public String showDrawQty(HttpServletRequest request,String job_no,String major,HttpServletResponse response,String type){
		Dictionary dic = dictionaryService.findByID(major);
		Project project = projectDao.findProject(job_no);
		if(type.equals("qty")){
			if("professional_st".equals(dic.getCode())){
	 			return String.valueOf(project.getDraw_quantity_st());
	 		}else if("professional_pi".equals(dic.getCode())){
	 			return String.valueOf(project.getDraw_quantity_pi());
	 		}else if("professional_ei".equals(dic.getCode())){
	 			return String.valueOf(project.getDraw_quantity_ei());
	 		}else if("professional_hvac".equals(dic.getCode())){
	 			return String.valueOf(project.getDraw_quantity_hvac());
	 		}else if("professional_ar".equals(dic.getCode())){
	 			return String.valueOf(project.getDraw_quantity_ar());
	 		}else{
	 			return "";	
	 		}
		}else{
			if (null==project) {
				return "0";
			}
			if("professional_st".equals(dic.getCode())){
				return String .valueOf(project.getRate_man_hour_st());
			}else if("professional_pi".equals(dic.getCode())){
				return String .valueOf(project.getRate_man_hour_pi());
			}else if("professional_ei".equals(dic.getCode())){
				return String .valueOf(project.getRate_man_hour_ei());
			}else if("professional_hvac".equals(dic.getCode())){
				return String .valueOf(project.getRate_man_hour_hvac());
			}else if("professional_ar".equals(dic.getCode())){
				return String .valueOf(project.getRate_man_hour_ar());
			}else if("hour_manage".equals(dic.getCode())){
				return "0";
			}else {
				return "";
			}
		}
	}
	
	public String getProjectId(String job_no){
		return getProjectData(job_no).get("id").toString();
	}
	//保存项目中设计部分的信息（图纸数量及工时值）
	public void save(Project project) {
		project.setProjectId(this.getProjectId(project.getJob_no()));
		if(null==project.getId()||"".equals(project.getId())){
			this.create(project);
		}else{
			this.update(project);
		}
	}
	//根据id查询项目
	public String findProjectByIDToJson(String id){
		Project project= findByID(id);
		Map<String,Object> map = getProjectData(project.getJob_no());
		return findByIDToJson(id, map);
	}
	//检查项目的合法性
	public boolean checkValidate(String jobNo, String id) {
		return projectDao.checkValidate(jobNo,id).isEmpty()?true:false;
	}
	public Map<String, Object> selectIfNullByJobNo(String job_no) {
		Map<String, Object> projectData = getProjectData(job_no);
		return projectData;
	}
	public Project findProjectByNameAndNo(String project_name,String job_no) {
		return projectDao.findProjectByNameAndNo(project_name, job_no);
	}
}
