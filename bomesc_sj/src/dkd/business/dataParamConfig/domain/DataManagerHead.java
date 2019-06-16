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
@Entity
@Table(name="c_data_manager_head")
public class DataManagerHead extends BusinessEntity {
	
	private static final long serialVersionUID = 1L;

	@Column(nullable=false,length=20)
	private String manager_no;//编号
	
	@Column(nullable=false,length=32)
	private String project;//项目
	
	@Column(nullable=false,length=80)
	private String project_name;//项目名称
	
	@Column(nullable=false,length=80)
	private String job_no;//项目工作号
	
	@ManyToOne
	@JoinColumn(name="major",nullable=false)
	private Dictionary major;//专业

	@OneToMany(mappedBy = "dataManagerHead", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
	private List<DataManagerDetail> dataManagerHeads;
	
	public String getManager_no() {
		return manager_no;
	}

	public void setManager_no(String manager_no) {
		this.manager_no = manager_no;
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

	public List<DataManagerDetail> getDataManagerHeads() {
		return dataManagerHeads;
	}

	public void setDataManagerHeads(List<DataManagerDetail> dataManagerHeads) {
		this.dataManagerHeads = dataManagerHeads;
	}
	
}
