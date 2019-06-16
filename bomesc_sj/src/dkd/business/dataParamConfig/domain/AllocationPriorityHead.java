package dkd.business.dataParamConfig.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import dkd.paltform.base.domain.BusinessEntity;
import dkd.paltform.dictionary.domain.Dictionary;
/**
 *物资分配优先级头表
 */
@Entity
@Table(name = "c_allocation_priority_head")
public class AllocationPriorityHead extends BusinessEntity{
	private static final long serialVersionUID = 1L;

	@Column(nullable=false,length=20)
	private String rule_no;//单据编码(PMH+9位流水号)
	
	@Column(nullable=false,length=80)
	private String job_no;//项目工作号
	
	@ManyToOne
	@JoinColumn(name="major", nullable=false)
	private Dictionary major;//专业ID
	
	@Column(nullable=false,length=32)
	private String project;//项目id
	
	@Column(nullable=false,length=80)
	private String project_name;//项目名称
	
	@OneToMany(mappedBy="allocationPriorityHead",cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
	private List<AllocationPriorityDetail> allocationPriorityDetail;
	
	public List<AllocationPriorityDetail> getAllocationPriorityDetail() {
		return allocationPriorityDetail;
	}

	public void setAllocationPriorityDetail(List<AllocationPriorityDetail> allocationPriorityDetail) {
		this.allocationPriorityDetail = allocationPriorityDetail;
	}

	public String getRule_no() {
		return rule_no;
	}

	public void setRule_no(String rule_no) {
		this.rule_no = rule_no;
	}

	public String getJob_no() {
		return job_no;
	}

	public void setJob_no(String job_no) {
		this.job_no = job_no;
	}

	public Dictionary getMajor() {
		return major;
	}

	public void setMajor(Dictionary major) {
		this.major = major;
	}

	public String getProject() {
		return project;
	}

	public void setProject(String project) {
		this.project = project;
	}

	public String getProject_name() {
		return project_name;
	}

	public void setProject_name(String project_name) {
		this.project_name = project_name;
	}
	
}
