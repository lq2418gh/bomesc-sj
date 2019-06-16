package dkd.paltform.flowset.domain;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import dkd.paltform.base.domain.BusinessEntity;
@Entity
@Table(name = "base_flowset_detail_role")
public class FlowsetDetailRole extends BusinessEntity{
	private static final long serialVersionUID = 2296803751378675065L;
	@ManyToOne
	@JoinColumn(name = "flowset_detail", nullable = false)
	private FlowsetDetail flowset_detail;
	@JoinColumn(nullable = false)
	private String role_id;
	@JoinColumn(nullable = false)
	private String role_name;
	public FlowsetDetail getFlowset_detail() {
		return flowset_detail;
	}
	public void setFlowset_detail(FlowsetDetail flowset_detail) {
		this.flowset_detail = flowset_detail;
	}
	public String getRole_id() {
		return role_id;
	}
	public void setRole_id(String role_id) {
		this.role_id = role_id;
	}
	public String getRole_name() {
		return role_name;
	}
	public void setRole_name(String role_name) {
		this.role_name = role_name;
	}
}
