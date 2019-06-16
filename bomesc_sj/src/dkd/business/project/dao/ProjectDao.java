package dkd.business.project.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;
import dkd.business.project.domain.Project;
import dkd.paltform.base.QueryResult;
import dkd.paltform.base.dao.BaseDao;
@Repository
public class ProjectDao extends BaseDao<Project>{
	
	@Override
	public QueryResult<Map<String, Object>> getScrollData(Map<String,String> params){
		String sql="select bp.* from b_project bp order by bp.entity_createdate desc";
		return getScrollData(sql,params,null);
	}

	public List<Map<String, Object>> checkValidate(String jobNo, String id) {
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("jobNo", jobNo);
		String sql = "select id from b_project where job_no= :jobNo ";
		if(null!=id&&!"".equals(id)){
			map.put("id", id);
			sql+=" and id!=:id";
		}
		return findBySql(sql, map);
	}

	public Project findProject(String job_no) {
		String jpql="from Project where job_no=:job_no";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("job_no", job_no);
		return find(jpql, map);
	}
	/**
	 * @date 2018年1月12日
	 * @author gaoxp
	 * @param project_name
	 * @param job_no
	 * @return
	 * 描述：根据项目名称，项目工作号查询项目
	 */
	public Project findProjectByNameAndNo(String project_name,String job_no) {
		String jpql="from Project where job_no=:job_no and project_name=:project_name";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("job_no", job_no);
		map.put("project_name", project_name);
		return find(jpql, map);
	}
}
