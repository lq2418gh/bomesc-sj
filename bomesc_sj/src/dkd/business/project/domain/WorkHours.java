package dkd.business.project.domain;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import dkd.paltform.base.domain.BusinessEntity;
import dkd.paltform.dictionary.domain.Dictionary;
/**
 *项目工时维护
 */
@Entity
@Table(name = "b_workhours")
public class WorkHours extends BusinessEntity{

	
	private static final long serialVersionUID = 1L;

	@Column(nullable=false,length=20)
	private String hour_no;//图纸编码(PMH+9位流水号)
	
	@Column(nullable=false,length=80)
	private String job_no;//项目工作号
	
	@Column(nullable=false,length=10)
	private String statistical_month;//月份
	
	@ManyToOne
	@JoinColumn(name="major", nullable=false)
	private Dictionary major;//专业ID
	
	@Column(nullable=false,length=32)
	private String project;//项目id
	
	@Column(nullable=false,length=80)
	private String project_name;//项目名称
	
	@Column(nullable=true,precision=20,scale = 1)
	private BigDecimal total_rate_man_hour;//项目额定工时
	
	@Column(nullable=false,precision=20,scale = 1)
	private BigDecimal forecast_man_hour;//本月计划工时
	
	@Column(nullable=false,precision=20,scale = 1)
	private BigDecimal change_man_hour;//本月变更工时
	
	@Column(nullable=false,precision=20,scale = 1)
	private BigDecimal actual_man_hour;//本月实际工时
	
	@Column(nullable=false,precision=20,scale = 1)
	private BigDecimal cumulative_man_hour;//项目累计工时


	public String getHour_no() {
		return hour_no;
	}

	public void setHour_no(String hour_no) {
		this.hour_no = hour_no;
	}

	public String getJob_no() {
		return job_no;
	}

	public void setJob_no(String job_no) {
		this.job_no = job_no;
	}

	public String getStatistical_month() {
		return statistical_month;
	}

	public void setStatistical_month(String statistical_month) {
		this.statistical_month = statistical_month;
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

	public BigDecimal getTotal_rate_man_hour() {
		return total_rate_man_hour;
	}

	public void setTotal_rate_man_hour(BigDecimal total_rate_man_hour) {
		this.total_rate_man_hour = total_rate_man_hour;
	}

	public BigDecimal getForecast_man_hour() {
		return forecast_man_hour;
	}

	public void setForecast_man_hour(BigDecimal forecast_man_hour) {
		this.forecast_man_hour = forecast_man_hour;
	}

	public BigDecimal getChange_man_hour() {
		return change_man_hour;
	}

	public void setChange_man_hour(BigDecimal change_man_hour) {
		this.change_man_hour = change_man_hour;
	}

	public BigDecimal getActual_man_hour() {
		return actual_man_hour;
	}

	public void setActual_man_hour(BigDecimal actual_man_hour) {
		this.actual_man_hour = actual_man_hour;
	}

	public BigDecimal getCumulative_man_hour() {
		return cumulative_man_hour;
	}

	public void setCumulative_man_hour(BigDecimal cumulative_man_hour) {
		this.cumulative_man_hour = cumulative_man_hour;
	}

	

	
}
