package dkd.business.order.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import dkd.paltform.base.domain.BusinessEntity;

@Entity
@Table(name = "order_detail")
public class SaleOrderDetail extends BusinessEntity{
	private static final long serialVersionUID = -5432375840319747519L;
	@Column(nullable = false)
	private int order_row_no;
	@Column(nullable = false,length=50)
	private String commodity_name;
	@ManyToOne
	@JoinColumn(name = "saleorder", nullable = false)
	private SaleOrder saleorder;
	@Column(nullable = false,length=50)
	private String purchaser_id;
	@Column(nullable = false,length=50)
	private String purchaser_name;
	public int getOrder_row_no() {
		return order_row_no;
	}
	public void setOrder_row_no(int order_row_no) {
		this.order_row_no = order_row_no;
	}
	public String getCommodity_name() {
		return commodity_name;
	}
	public void setCommodity_name(String commodity_name) {
		this.commodity_name = commodity_name;
	}
	public SaleOrder getSaleorder() {
		return saleorder;
	}
	public void setSaleorder(SaleOrder saleorder) {
		this.saleorder = saleorder;
	}
	public String getPurchaser_id() {
		return purchaser_id;
	}
	public void setPurchaser_id(String purchaser_id) {
		this.purchaser_id = purchaser_id;
	}
	public String getPurchaser_name() {
		return purchaser_name;
	}
	public void setPurchaser_name(String purchaser_name) {
		this.purchaser_name = purchaser_name;
	}
}