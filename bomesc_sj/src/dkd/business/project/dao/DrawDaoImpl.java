package dkd.business.project.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import dkd.business.project.domain.Draw;
import dkd.paltform.base.QueryResult;
import dkd.paltform.base.dao.BaseDao;

@Repository
public class DrawDaoImpl extends BaseDao<Draw>{
	
	public QueryResult<Map<String, Object>> getScrollData(Map<String,String> params){
		String sql="select * from b_draw_overview "
				+ "order by entity_createdate desc";
		return getScrollData(sql,params,new String[]{"major"});
	}
	
	public List<Draw> findPreOld(Map<String, Object> map) {
		String jpql= "From Draw where job_no = :project and major = :major and statistical_month=:statistical_month and id<>:id";
		return super.findAll(jpql, map);
	}

	public List<Map<String, Object>> findPre(Map<String, Object> map) {
		String sql= "select this_draw_quantity,this_percentage,draw_quantity from b_draw_overview where job_no = :project and major = :major and statistical_month<:statistical_month and id<>:id order by statistical_month desc";
		return findBySql(sql, map);
	}

	public List<Map<String, Object>> findTotalPre(Map<String, Object> map) {
		String sql= "select isnull(sum(this_draw_quantity),0) this_draw_quantity from b_draw_overview where job_no = :project and major = :major and statistical_month<:statistical_month and id<>:id";
		return findBySql(sql, map);
	}
}
