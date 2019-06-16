package dkd.business.project.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import dkd.business.project.domain.DrawDetailDetail;
import dkd.paltform.base.dao.BaseDao;

@Repository
public class DrawDetailDetailDaoImpl extends BaseDao<DrawDetailDetail>{

	public List<Map<String, Object>> find(Map<String, Object> map) {
		String sql="select isnull(sum(dd.this_draw_forecast),0) this_draw_forecast,isnull(sum(dd.this_draw_actual),0) this_draw_actual,"
				+ "isnull(avg(dd.this_discrepancy),0) this_discrepancy,isnull(sum(dd.cumulative_draw_forecast),0) cumulative_draw_forecast,isnull(sum(dd.cumulative_draw_actual),0) cumulative_draw_actual,isnull(avg(dd.cumulative_discrepancy),0) cumulative_discrepancy From b_draw_list_detail dd"
				+ " left join b_draw_list_head dh on dd.list_head=dh.list_no where dh.job_no=:project and dh.major=:major "
				+ " and dh.statistical_month<:month and dd.draw_type=:drawType group by dh.job_no,dh.major,dd.draw_type,dh.statistical_month order by dh.statistical_month desc";
		return super.findBySql(sql, map);
	}

	public List<Map<String, Object>> findByHead(String id) {
		String sql = "select id from b_draw_list_detail where drawDetailHead =:id";
		Map<String,Object> map = new HashMap<>();
		map.put("id", id);
		return super.findBySql(sql, map);
	}

	public List<Map<String, Object>> getTotalPre(Map<String, Object> map) {
		String sql="select isnull(sum(dd.this_draw_forecast),0) cumulative_draw_forecast,isnull(sum(dd.this_draw_actual),0) cumulative_draw_actual,isnull(avg(dd.this_discrepancy),0) cumulative_discrepancy From b_draw_list_detail dd"
				+ " left join b_draw_list_head dh on dd.list_head=dh.list_no where dh.job_no=:project and dh.major=:major "
				+ " and dh.statistical_month<:month and dd.draw_type=:drawType ";
		return super.findBySql(sql, map);
	}
}
