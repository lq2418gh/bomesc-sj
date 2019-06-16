package dkd.business.promptRecord.dao;

import java.util.Map;

import org.springframework.stereotype.Repository;

import dkd.business.promptRecord.domain.ImportFailureRecord;
import dkd.paltform.base.QueryResult;
import dkd.paltform.base.dao.BaseDao;

@Repository
public class ImportFailureRecordDao extends BaseDao<ImportFailureRecord>{
	@Override
	public QueryResult<Map<String, Object>> getScrollData(Map<String,String> params){
		String sql="select pif.prompt_date,cdmd.job_number,cdmd.manager_user_name,cdmd.email,pif.file_path,"
						+ " cdmh.project_name,cdmh.job_no,bd.name major_name,pif.module,pif.prompt_content from p_import_failure pif"+
						" left join c_data_manager_detail cdmd on pif.manager_detail = cdmd.id"+ 
						" left join c_data_manager_head cdmh on cdmd.dataManagerHead=cdmh.id"+
						" left join base_dictionary bd on cdmh.major=bd.id"+
						" order by pif.entity_createdate desc";
		return getScrollData(sql,params,new String[]{"major"});
	}
}
