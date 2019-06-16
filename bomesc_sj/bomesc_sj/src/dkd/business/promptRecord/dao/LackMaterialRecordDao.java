package dkd.business.promptRecord.dao;

import java.util.Map;

import org.springframework.stereotype.Repository;

import dkd.business.promptRecord.domain.LackMaterialRecord;
import dkd.paltform.base.QueryResult;
import dkd.paltform.base.dao.BaseDao;

@Repository
public class LackMaterialRecordDao extends BaseDao<LackMaterialRecord>{
	@Override
	public QueryResult<Map<String, Object>> getScrollData(Map<String,String> params){
		String sql="select plm.prompt_date,cdmd.job_number,cdmd.manager_user_name,cdmd.email,"
						+ " cdmh.project_name,cdmh.job_no,bd.name major_name,plm.prompt_content from p_lack_material plm"+
						" left join c_data_manager_detail cdmd on plm.manager_detail = cdmd.id"+ 
						" left join c_data_manager_head cdmh on cdmd.dataManagerHead=cdmh.id"+
						" left join base_dictionary bd on cdmh.major=bd.id"+
						" order by plm.entity_createdate desc";
		return getScrollData(sql,params,new String[]{"major"});
	}
}
