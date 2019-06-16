package dkd.business.order.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import dkd.business.order.domain.SaleOrder;
import dkd.business.order.service.SaleOrderService;
import dkd.paltform.base.controller.BaseController;
@Controller
@RequestMapping(value = "/saleOrder")
public class SaleOrderController extends BaseController{
	@Autowired
	private SaleOrderService saleOrderService;
	@RequestMapping(value = "/edit.do")
	public String edit(String order_no,ModelMap model) {
		model.put("order_no", order_no);
		return "business/order/saleOrder/saleOrderEdit";
	}
	@RequestMapping(value = "/view.do")
	public String view(String order_no,ModelMap model) {
		model.put("order_no", order_no);
		return "business/order/saleOrder/saleOrderView";
	}
	@RequestMapping(value = "/query.do")
	@ResponseBody
	public String query(HttpServletRequest request) {
		return saleOrderService.getScrollData(getParam(request)).toJson();
	}
	@RequestMapping(value = "/save.do")
	@ResponseBody
	public String save(@RequestBody SaleOrder order) {
		saleOrderService.saveOrder(order);
		return toWriteSuccess("保存订单成功！");
	}
	@RequestMapping(value = "/findByNo.do")
	@ResponseBody
	public String findByNo(String order_no) {
		return saleOrderService.findByFieldToJson("order_no", order_no);
	}
	@RequestMapping(value = "/submit.do")
	@ResponseBody
	public String submit(String order_no) {
		saleOrderService.submit(order_no);
		return toWriteSuccess("提交成功");			
	}
}
