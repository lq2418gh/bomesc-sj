package dkd.business.order.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import dkd.business.order.dao.SaleOrderDaoImpl;
import dkd.business.order.domain.SaleOrder;
import dkd.business.order.domain.SaleOrderDetail;
import dkd.paltform.base.BusinessException;
import dkd.paltform.base.dao.BaseDao;
import dkd.paltform.base.service.BaseService;
import dkd.paltform.flowset.service.FlowsetService;
import dkd.paltform.util.StringUtils;

@Service
@Transactional
public class SaleOrderService extends BaseService<SaleOrder>{
	@Autowired
	private SaleOrderDaoImpl saleOrderDao;
	@Autowired
	private FlowsetService flowsetService;
	@Override
	public BaseDao<SaleOrder> getDao() {
		return saleOrderDao;
	}
	@Transactional(propagation = Propagation.REQUIRED)
	public void saveOrder(SaleOrder order){
		for(SaleOrderDetail detail : order.getDetails()){
			detail.setSaleorder(order);
		}
		if(StringUtils.isEmpty(order.getId())){
			SaleOrder temp = saleOrderDao.findByField("order_no", order.getOrder_no());
			if (temp != null){
				throw new BusinessException(-1, "order_no", "订单号已经存在");
			}
			order.setState("新建");
			create(order);
		}else{
			SaleOrder temp = saleOrderDao.findByField("order_no", order.getOrder_no(),order.getId());
			if (temp != null){
				throw new BusinessException(-1, "order_no", "订单号已经存在");
			}
			update(order);
		}
	}
	@Transactional(propagation = Propagation.REQUIRED)
	public void submit(String order_no){
		SaleOrder saleOrder = findByField("order_no", order_no);
		saleOrder.setState("提交");
		update(saleOrder);
		flowsetService.initRecord("stPicking", order_no,null);
	}
	public void testSuccess(String order_no){
		System.out.println("成功调用：" + order_no);
	}
}
