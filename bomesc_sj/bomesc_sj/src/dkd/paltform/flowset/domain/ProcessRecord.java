package dkd.paltform.flowset.domain;

import java.util.List;

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
@Table(name = "base_flowset_record")
public class ProcessRecord extends BusinessEntity{
	private static final long serialVersionUID = -8410415005165740703L;
	@Column(nullable = false, length = 3)
	private int order_no;//序号
	@ManyToOne
	@JoinColumn(name = "flowset_detail", nullable = false)
	private FlowsetDetail flowset_detail;//审批流程节点
	@Column(nullable = false, length = 40)
	private String work_no;//单据号
	@Column(nullable = false, length = 40)
	private String name;//名称
	@Column(nullable = false)
	@org.hibernate.annotations.Type(type="yes_no")
	private boolean is_complete;//是否通过
	@Column(nullable = false)
	@org.hibernate.annotations.Type(type="yes_no")
	private boolean is_check;//是否审核
	@OneToMany(mappedBy = "process_record", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
	@OrderBy(value="role_name")
	private List<ProcessRecordRole> process_record_role;//角色审批列表
	
	public int getOrder_no() {
		return order_no;
	}
	public void setOrder_no(int order_no) {
		this.order_no = order_no;
	}
	public FlowsetDetail getFlowset_detail() {
		return flowset_detail;
	}
	public void setFlowset_detail(FlowsetDetail flowset_detail) {
		this.flowset_detail = flowset_detail;
	}
	public String getWork_no() {
		return work_no;
	}
	public void setWork_no(String work_no) {
		this.work_no = work_no;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public boolean isIs_complete() {
		return is_complete;
	}
	public void setIs_complete(boolean is_complete) {
		this.is_complete = is_complete;
	}
	public boolean isIs_check() {
		return is_check;
	}
	public void setIs_check(boolean is_check) {
		this.is_check = is_check;
	}
	public List<ProcessRecordRole> getProcess_record_role() {
		return process_record_role;
	}
	public void setProcess_record_role(List<ProcessRecordRole> process_record_role) {
		this.process_record_role = process_record_role;
	}
}