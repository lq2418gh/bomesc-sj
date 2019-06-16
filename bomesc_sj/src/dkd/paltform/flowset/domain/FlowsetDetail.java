package dkd.paltform.flowset.domain;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import dkd.paltform.base.domain.BusinessEntity;

@Entity
@Table(name = "base_flowset_detail")
public class FlowsetDetail extends BusinessEntity{
	private static final long serialVersionUID = 7642883950074671013L;
	@Column(nullable = false, length = 3)
	private int order_no;//序号
	@Column(nullable = false, length = 40)
	private String name;//审批流名称
	@Column(nullable = false)
	@org.hibernate.annotations.Type(type="yes_no")
	private boolean is_validate = true;//是否启用
	@Column(nullable = false)
	@org.hibernate.annotations.Type(type="yes_no")
	private boolean is_parallel;//审批类型：0 单个审批 1 并行审批
	@Column(nullable = true)
	@org.hibernate.annotations.Type(type="yes_no")
	private boolean is_parallel_all;//并行时是否验证所有节点全部审批通过
	@Column(nullable = false)
	@org.hibernate.annotations.Type(type="yes_no")
	private boolean is_email;//流转到该节点时是否要给需要审核的角色下属用户发送提醒邮件
	@Column(nullable = true, length = 40)
	private String handle_pass_method;//审核通过时回调函数（参数只能为业务主键）
	@Column(nullable = true, length = 1000)
	private String remark;//备注
	@ManyToOne
	@JoinColumn(name = "flowset", nullable = false)
	private Flowset flowset;//关联审批类型ID
	@OneToMany(mappedBy = "flowset_detail", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
	@OrderBy(value="role_name")
	private Set<FlowsetDetailRole> detail_roles;//角色编码
	
	public Flowset getFlowset() {
		return flowset;
	}
	public void setFlowset(Flowset flowset) {
		this.flowset = flowset;
	}
	public int getOrder_no() {
		return order_no;
	}
	public void setOrder_no(int order_no) {
		this.order_no = order_no;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public boolean isIs_validate() {
		return is_validate;
	}
	public void setIs_validate(boolean is_validate) {
		this.is_validate = is_validate;
	}
	public boolean isIs_parallel() {
		return is_parallel;
	}
	public void setIs_parallel(boolean is_parallel) {
		this.is_parallel = is_parallel;
	}
	public boolean isIs_parallel_all() {
		return is_parallel_all;
	}
	public void setIs_parallel_all(boolean is_parallel_all) {
		this.is_parallel_all = is_parallel_all;
	}
	public String getHandle_pass_method() {
		return handle_pass_method;
	}
	public void setHandle_pass_method(String handle_pass_method) {
		this.handle_pass_method = handle_pass_method;
	}
	public boolean isIs_email() {
		return is_email;
	}
	public void setIs_email(boolean is_email) {
		this.is_email = is_email;
	}
	public Set<FlowsetDetailRole> getDetail_roles() {
		return detail_roles;
	}
	public void setDetail_roles(Set<FlowsetDetailRole> detail_roles) {
		this.detail_roles = detail_roles;
	}
}