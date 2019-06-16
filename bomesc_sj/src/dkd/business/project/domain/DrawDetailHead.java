package dkd.business.project.domain;

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
@Table(name="b_draw_list_head")
public class DrawDetailHead extends BusinessEntity{

	private static final long serialVersionUID = 1L;
	
	@Column(nullable=false,length=20)
	private String list_no;//编号
	
	@ManyToOne
	@JoinColumn(name="major",nullable=false)
	private Dictionary major;//专业
	
	@Column(nullable=false,length=10)
	private String statistical_month;//月份
	
	@Column(nullable=false,length=32)
	private String project;//项目
	
	@Column(nullable=false,length=100)
	private String project_name;//项目名称
	
	@Column(nullable=false,length=40)
	private String job_no;//项目工作号
	
	@OneToMany(mappedBy = "drawDetailHead", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
	private List<DrawDetailDetail> drawDetailHeads;
	
	public String getList_no() {
		return list_no;
	}

	public void setList_no(String list_no) {
		this.list_no = list_no;
	}

	public Dictionary getMajor() {
		return major;
	}

	public void setMajor(Dictionary major) {
		this.major = major;
	}

	public String getStatistical_month() {
		return statistical_month;
	}

	public void setStatistical_month(String statistical_month) {
		this.statistical_month = statistical_month;
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

	public List<DrawDetailDetail> getDrawDetailHeads() {
		return drawDetailHeads;
	}

	public void setDrawDetailHeads(List<DrawDetailDetail> drawDetailHeads) {
		this.drawDetailHeads = drawDetailHeads;
	}
	
}
