package dkd.business.dataParamConfig.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import dkd.business.dataParamConfig.domain.ColumnDisplaySetting;
import dkd.paltform.base.QueryResult;
import dkd.paltform.base.dao.BaseDao;
import dkd.paltform.util.StringUtils;
@Repository
public class ColumnDisplaySettingDao extends BaseDao<ColumnDisplaySetting>{

	/**
	 * 校验是否有相同的项目号/项目工作号
	 */
	public ColumnDisplaySetting findByValidate(ColumnDisplaySetting columnDisplaySetting){
		String jpql = "from ColumnDisplaySetting where (project_name=:project_name and major=:major)";
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("project_name", columnDisplaySetting.getProject_name());
		params.put("major", columnDisplaySetting.getMajor());
		if(StringUtils.isNotEmpty(columnDisplaySetting.getId())){
			jpql += " and id!=:id";
			params.put("id", columnDisplaySetting.getId());
		}
		return find(jpql,params);
	}
	
	/**
	 * 
	 * 
	 * */
	@Override
	public QueryResult<Map<String, Object>> getScrollData(Map<String,String> params){
		String sql="select bsc.* from b_supplement_columns bsc order by bsc.entity_createdate desc";
		return getScrollData(sql,params,new String[]{"major"});
	}

	public List<Map<String, Object>> findByMajor(String jobNo,String professional) {
		String sql = "select bsc.* from b_supplement_columns bsc "
				+ "left join base_dictionary bd on bsc.major=bd.id "
				+ "where bd.code = :code and job_no=:jobNo";
		Map<String,Object> paraMap = new HashMap<String, Object>();
		paraMap.put("code", professional);
		paraMap.put("jobNo", jobNo);
		return findBySql(sql, paraMap);
	}
	
}
