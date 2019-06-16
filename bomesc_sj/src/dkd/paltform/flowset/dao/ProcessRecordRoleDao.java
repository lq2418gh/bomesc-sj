package dkd.paltform.flowset.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import dkd.paltform.base.QueryResult;
import dkd.paltform.base.dao.BaseDao;
import dkd.paltform.flowset.domain.ProcessRecordRole;

@Repository
public class ProcessRecordRoleDao extends BaseDao<ProcessRecordRole>{
	public List<Map<String,Object>> selectByRecord(String recordId){
		String sql = "select rr.id recordId,rr.role_id roleId,rr.pend_check_user from base_flowset_record_role rr " +
				"where rr.process_record=:process_record and rr.is_check='N' order by rr.role_name";
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("process_record", recordId);
		return findBySql(sql, params);
	}
	@Override
	public QueryResult<Map<String, Object>> getScrollData(Map<String,String> params){
		return getScrollData("select fd.name,rr.check_user_name,rr.role_name,rr.check_opinion,rr.is_check," +
			"rr.is_pass,check_date from base_flowset_record_role rr " +
			" left join base_flowset_record r on rr.process_record=r.id " +
			" left join base_flowset_detail fd on r.flowset_detail=fd.id " +
			" order by r.order_no,rr.role_name",params);
	}
}