package dkd.business.order.dao;

import java.util.Map;

import org.springframework.stereotype.Repository;

import dkd.business.order.domain.SaleOrder;
import dkd.paltform.base.QueryResult;
import dkd.paltform.base.dao.BaseDao;

@Repository
public class SaleOrderDaoImpl extends BaseDao<SaleOrder>{
	@Override
	public QueryResult<Map<String, Object>> getScrollData(Map<String,String> params){
		return getScrollData("select id,order_no,state,customer,entity_createdate from sale_order order by entity_createdate desc",params);
	}
}
