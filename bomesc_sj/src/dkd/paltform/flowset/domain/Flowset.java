package dkd.paltform.flowset.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import dkd.paltform.base.domain.BusinessEntity;
import dkd.paltform.util.json.annotation.MyselfJsonIngore;

@Entity
@Table(name = "base_flowset_head")
public class Flowset extends BusinessEntity{
	private static final long serialVersionUID = -9007706605689502231L;
	@Column(nullable = false, length = 40)
	private String code;//编码
	@Column(nullable = false, length = 40)
	private String name;//名称
	@Column(nullable = false, length = 40)
	private String business_name;//业务单据名称
	@Column(nullable = false, length = 100)
	private String table_name;//操作数据库表名
	@Column(nullable = false, length = 40)
	private String no_col;//单据业务主键字段
	@Column(nullable = false, length = 40)
	private String state_col;//状态字段名
	@Column(nullable = false, length = 100)
	private String pass_val;//审核通过状态值
	@Column(nullable = false, length = 100)
	private String unpass_val;//审核不通过状态值
	@Column(nullable = false, length = 100)
	private String service_bean;//该单据的service名称，用于审核流转时回调函数用
	@Column(nullable = true, length = 200)
	private String view_url;//备注
	@Column(nullable = false, length = 40)
	private int levels;//审核层级数
	@Column(nullable = true, length = 40)
	private String handle_pass_method;//审核通过时回调函数（参数只能为业务主键）
	@Column(nullable = true, length = 40)
	private String handle_unpass_method;//审核不通过时回调函数（参数只能为业务主键）
	@Column(nullable = true, length = 1000)
	private String remark;//备注
	@MyselfJsonIngore
	@OneToMany(mappedBy = "flowset", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@OrderBy(value="order_no")
	private List<FlowsetDetail> flowset_details;//关联审批流程明细id
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getBusiness_name() {
		return business_name;
	}
	public void setBusiness_name(String business_name) {
		this.business_name = business_name;
	}
	public String getTable_name() {
		return table_name;
	}
	public void setTable_name(String table_name) {
		this.table_name = table_name;
	}
	public String getState_col() {
		return state_col;
	}
	public void setState_col(String state_col) {
		this.state_col = state_col;
	}
	public String getPass_val() {
		return pass_val;
	}
	public void setPass_val(String pass_val) {
		this.pass_val = pass_val;
	}
	public String getUnpass_val() {
		return unpass_val;
	}
	public void setUnpass_val(String unpass_val) {
		this.unpass_val = unpass_val;
	}
	public String getNo_col() {
		return no_col;
	}
	public void setNo_col(String no_col) {
		this.no_col = no_col;
	}
	public int getLevels() {
		return levels;
	}
	public void setLevels(int levels) {
		this.levels = levels;
	}
	public String getHandle_pass_method() {
		return handle_pass_method;
	}
	public void setHandle_pass_method(String handle_pass_method) {
		this.handle_pass_method = handle_pass_method;
	}
	public String getHandle_unpass_method() {
		return handle_unpass_method;
	}
	public void setHandle_unpass_method(String handle_unpass_method) {
		this.handle_unpass_method = handle_unpass_method;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getService_bean() {
		return service_bean;
	}
	public void setService_bean(String service_bean) {
		this.service_bean = service_bean;
	}
	public String getView_url() {
		return view_url;
	}
	public void setView_url(String view_url) {
		this.view_url = view_url;
	}
	public List<FlowsetDetail> getFlowset_details() {
		return flowset_details;
	}
	public void setFlowset_details(List<FlowsetDetail> flowset_details) {
		this.flowset_details = flowset_details;
	}
}