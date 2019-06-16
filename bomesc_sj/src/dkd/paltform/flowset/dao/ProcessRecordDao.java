package dkd.paltform.flowset.dao;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import dkd.paltform.base.QueryResult;
import dkd.paltform.base.dao.BaseDao;
import dkd.paltform.flowset.domain.Flowset;
import dkd.paltform.flowset.domain.ProcessRecord;
import dkd.paltform.util.SpringUtil;

@Repository
public class ProcessRecordDao extends BaseDao<ProcessRecord>{
	public void deleteByWorkNo(String work_no){
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("work_no", work_no);
		batchDelete("delete ProcessRecord where work_no=:work_no",params);
	}
	public void deleteByUnPass(String work_no){
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("work_no", work_no);
		params.put("is_check", false);
		batchDelete("delete ProcessRecord where is_check=:is_check and work_no=:work_no",params);
	}
	public ProcessRecord selectByWorkNo(String work_no){
		String jpql = "from ProcessRecord where work_no=:work_no and is_check=:is_check order by order_no";
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("work_no", work_no);
		params.put("is_check", false);
		return find(jpql,params);
	}
	public void updateSqlByPass(Flowset flowset,String work_no){
		String sql = "update " + flowset.getTable_name().replaceAll(" ", "") + " set " + flowset.getState_col().replaceAll(" ", "") + 
				" = :pass_val where " + flowset.getNo_col().replaceAll(" ", "") + " = :work_no";
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("pass_val", flowset.getPass_val());
		params.put("work_no", work_no);
		updateBySql(sql, params);
	}
	public void updateSqlByUnPass(Flowset flowset,String work_no){
		String sql = "update " + flowset.getTable_name().replaceAll(" ", "") + " set " + flowset.getState_col().replaceAll(" ", "") + 
				" = :unpass_val where " + flowset.getNo_col().replaceAll(" ", "") + " = :work_no";
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("unpass_val", flowset.getUnpass_val());
		params.put("work_no", work_no);
		updateBySql(sql, params);
	}
	public QueryResult<Map<String, Object>> getTaskScrollData(){
		String sql = "select distinct fh.name flowset_name,r.name,r.work_no,fh.view_url,r.entity_createdate from (select r.* from (select work_no,MIN(order_no) order_no " +
					"	from base_flowset_record " +
					"	where is_check='N' group by work_no) as a left join base_flowset_record r " +
					"	on a.work_no=r.work_no and a.order_no=r.order_no) r " +
					" left join base_flowset_detail fd on r.flowset_detail=fd.id " +
					" left join base_flowset_head fh on fd.flowset=fh.id " +
					" left join base_flowset_record_role rr on rr.process_record=r.id " +
					" where rr.role_id in (" + SpringUtil.getUserRolesStr() + ") " +
					" order by r.entity_createdate desc";
		return getScrollData(sql);
	}
}
