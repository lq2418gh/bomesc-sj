package dkd.paltform.flowset.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import dkd.paltform.base.dao.BaseDao;
import dkd.paltform.flowset.domain.FlowsetDetail;
import dkd.paltform.util.StringUtils;

@Repository
public class FlowsetDetailDao extends BaseDao<FlowsetDetail>{
	public List<FlowsetDetail> findByOrderNo(FlowsetDetail detail){
		String jpql = "from FlowsetDetail where flowset.id='" + detail.getFlowset().getId() + "' and order_no=" + detail.getOrder_no();
		if(StringUtils.isNotEmpty(detail.getId())){
			jpql += " and id!='" + detail.getId() + "'";
		}
		return findAll(jpql);
	}
}
