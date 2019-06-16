package dkd.business.mto.domain;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import dkd.paltform.base.domain.BusinessEntity;

@Entity
@Table(name = "b_mto_detail")
public class MtoDetail extends BusinessEntity{
	private static final long serialVersionUID = -9056169625876088312L;
	//料单主表
	@ManyToOne
	@JoinColumn(name = "mto_head", nullable = false)
	private MtoHead mto_head;
	//料单行号
	@Column(length = 3, nullable = false)
	private Integer mto_row_no;
	//物料id,暂时为空
	@Column(length = 32)
	private String material_id;
	//物料编码
	@Column(length = 50, nullable = false)
	private String material_code;
	//物料名称
	@Column(length = 300, nullable = false)
	private String material_name;
	//杆件号
	@Column(length = 100)
	private String member_num;
	//规格尺寸
	@Column(length = 4000)
	private String size;
	//材质
	@Column(length = 4000)
	private String material;
	//执行标准
	@Column(length = 500)
	private String standard;
	//技术要求
	@Column(length = 4000)
	private String description;
	//单位
	@Column(length = 10, nullable = false)
	private String unit;
	//设计数量
	@Column(nullable=false)
	private Integer design_qty;
	//推荐余量
	@Column(nullable=false)
	private Integer recommend_surplus;
	//采购数量
	@Column(nullable=false)
	private Integer purchase_qty;
	//推荐厂家
	@Column(length = 4000)
	private String recommend_vendor;
	//证书要求
	@Column(length = 4000)
	private String certificate_requirement;
	//采购日期
	@Column
	private String arrival_date;
	//库存使用量
	@Column
	private Integer stock_qty;
	//备注
	@Column(length = 4000)
	private String remark;
	public MtoHead getMto_head() {
		return mto_head;
	}
	public void setMto_head(MtoHead mto_head) {
		this.mto_head = mto_head;
	}
	public Integer getMto_row_no() {
		return mto_row_no;
	}
	public void setMto_row_no(Integer mto_row_no) {
		this.mto_row_no = mto_row_no;
	}
	public String getMaterial_id() {
		return material_id;
	}
	public void setMaterial_id(String material_id) {
		this.material_id = material_id;
	}
	public String getMaterial_code() {
		return material_code;
	}
	public void setMaterial_code(String material_code) {
		this.material_code = material_code;
	}
	public String getMaterial_name() {
		return material_name;
	}
	public void setMaterial_name(String material_name) {
		this.material_name = material_name;
	}
	public String getMember_num() {
		return member_num;
	}
	public void setMember_num(String member_num) {
		this.member_num = member_num;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	public String getMaterial() {
		return material;
	}
	public void setMaterial(String material) {
		this.material = material;
	}
	public String getStandard() {
		return standard;
	}
	public void setStandard(String standard) {
		this.standard = standard;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public String getRecommend_vendor() {
		return recommend_vendor;
	}
	public void setRecommend_vendor(String recommend_vendor) {
		this.recommend_vendor = recommend_vendor;
	}
	public String getCertificate_requirement() {
		return certificate_requirement;
	}
	public void setCertificate_requirement(String certificate_requirement) {
		this.certificate_requirement = certificate_requirement;
	}
	public String getArrival_date() {
		return arrival_date;
	}
	public void setArrival_date(String arrival_date) {
		this.arrival_date = arrival_date;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Integer getDesign_qty() {
		return design_qty;
	}
	public void setDesign_qty(Integer design_qty) {
		this.design_qty = design_qty;
	}
	public Integer getRecommend_surplus() {
		return recommend_surplus;
	}
	public void setRecommend_surplus(Integer recommend_surplus) {
		this.recommend_surplus = recommend_surplus;
	}
	public Integer getPurchase_qty() {
		return purchase_qty;
	}
	public void setPurchase_qty(Integer purchase_qty) {
		this.purchase_qty = purchase_qty;
	}
	public Integer getStock_qty() {
		return stock_qty;
	}
	public void setStock_qty(Integer stock_qty) {
		this.stock_qty = stock_qty;
	}
	
}
