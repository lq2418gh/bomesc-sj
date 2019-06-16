package dkd.business.order.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import dkd.paltform.base.annotation.BeanSelect;
import dkd.paltform.base.domain.BusinessEntity;

@Entity
@Table(name = "sale_order")
public class SaleOrder extends BusinessEntity{
	
	private static final long serialVersionUID = 8653608212183857744L;
	@Column(nullable = false, length = 20)
	@BeanSelect(fieldName="订单号")
	private String order_no;
	@BeanSelect(fieldName="状态")
	@Column(nullable = false, length = 20)
	private String state;
	@Column(nullable = false, length = 50)
	private String customer;
	@OneToMany(mappedBy = "saleorder", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
	private List<SaleOrderDetail> details;
	
	public String getOrder_no() {
		return order_no;
	}
	public void setOrder_no(String order_no) {
		this.order_no = order_no;
	}
	public String getCustomer() {
		return customer;
	}
	public void setCustomer(String customer) {
		this.customer = customer;
	}
	public List<SaleOrderDetail> getDetails() {
		return details;
	}
	public void setDetails(List<SaleOrderDetail> details) {
		this.details = details;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
}
