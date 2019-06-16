package dkd.paltform.log.dao;

import java.util.Map;

import org.springframework.stereotype.Repository;

import dkd.paltform.base.QueryResult;
import dkd.paltform.base.dao.BaseDao;
import dkd.paltform.log.domain.Log;
@Repository
public class LogDao extends BaseDao<Log>{
	@Override
	public QueryResult<Map<String, Object>> getScrollData(Map<String,String> params){
		return getScrollData("select id,entity_group,entity_type,operater_type,entity_code,description,ip_address,user_uame,operate_time from log_log order by operate_time desc",params);
	}
}
