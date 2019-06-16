package dkd.business.project.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import dkd.business.project.domain.WorkHours;
import dkd.paltform.base.QueryResult;
import dkd.paltform.base.dao.BaseDao;
@Repository
public class WorkHoursDao extends BaseDao<WorkHours>{
	
	/**
	 * 重写getScrollData方法
	 */
	public QueryResult<Map<String, Object>> getScrollData(Map<String,String> params){
		String sql="select * from b_workhours "
				+ "order by hour_no desc";
		return getScrollData(sql,params,new String[]{"major"});
	}

	public List<WorkHours> findWH(Map<String, Object> map) {
		String jpql= "From WorkHours where job_no = :project and major = :major and statistical_month=:statistical_month ";
		if(null!=map.get("id")){
			jpql+=" and id<>:id";
		}
		return super.findAll(jpql, map);
	}

	public List<Map<String, Object>> findSum(Map<String, Object> map) {
		String sql="select SUM(actual_man_hour) actual_man_hour FROM b_workhours WHERE job_no=:project "
				+ "and major=:major and statistical_month < :statistical_month ";
		if(null!=map.get("id")){
			sql+=" and id!=:id";
		}
		return findBySql(sql, map);
	}
}
