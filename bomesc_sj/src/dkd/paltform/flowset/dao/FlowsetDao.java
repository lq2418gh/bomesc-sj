package dkd.paltform.flowset.dao;

import java.util.Map;

import org.springframework.stereotype.Repository;

import dkd.paltform.base.QueryResult;
import dkd.paltform.base.dao.BaseDao;
import dkd.paltform.flowset.domain.Flowset;

@Repository
public class FlowsetDao extends BaseDao<Flowset>{
	@Override
	public QueryResult<Map<String, Object>> getScrollData(Map<String,String> params){
		return getScrollData("select id,code,name,table_name,levels from base_flowset_head order by code",params);
	}
}
