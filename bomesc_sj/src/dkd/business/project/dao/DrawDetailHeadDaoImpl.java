package dkd.business.project.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import dkd.business.project.domain.DrawDetailHead;
import dkd.paltform.base.QueryResult;
import dkd.paltform.base.dao.BaseDao;
@Repository
public class DrawDetailHeadDaoImpl extends BaseDao<DrawDetailHead>{
	
	public QueryResult<Map<String, Object>> getScrollData(Map<String,String> params){
		String sql = "select dh.id,dd.id cid,dh.entity_createdate,dh.entity_createuser,dh.job_no,dh.list_no,dh.project_name,"
				+ "dh.project,dh.statistical_month,dh.major,dd.cumulative_draw_actual,"
				+ "dd.cumulative_draw_forecast,dd.cumulative_discrepancy,dd.draw_type,dd.list_head,dd.pre_draw_actual,"
				+ "dd.pre_draw_forecast,dd.pre_discrepancy,dd.this_draw_actual,dd.this_draw_forecast,dd.this_discrepancy,"
				+ "dd.total_draw_quantity,dd.drawDetailHead from b_draw_list_detail dd "
				+ "left join b_draw_list_head dh on dd.list_head=dh.list_no "
				+ "where 1=1";
		return getScrollData(sql,params,new String[]{"major","draw_type"});
	}
	
	public List<DrawDetailHead> checkValidate(Map<String,Object> map) {
		String jpql = "from DrawDetailHead where job_no =:project and major =:major and statistical_month=:month"
				+ " and id<> :id";
		return super.findAll(jpql, map);
	}
	
}
