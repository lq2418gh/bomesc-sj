package dkd.business.project.domain;

import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import dkd.paltform.base.domain.BusinessEntity;
import dkd.paltform.dictionary.domain.Dictionary;

@Entity
@Table(name="b_draw_overview")
public class Draw extends BusinessEntity{

	private static final long serialVersionUID = 1L;
	
	@Column(nullable=false,length=20)
	private String overview_no;//图纸编码(oid+9位流水号)
	
	@ManyToOne
	@JoinColumn(name="major",nullable=false)
	private Dictionary major;//专业ID
	
	@Column(nullable=false,length=10)
	private String statistical_month;//年份
	
	@Column(nullable=false,length=32)
	private String project;//项目
	
	@Column(nullable=false,length=100)
	private String project_name;//项目名称
	
	@Column(nullable=false,length=40)
	private String job_no;//项目工作号
	
	@Column(nullable=false)
	private Integer total_draw_quantity;//图纸总数量
	
	@Column(nullable=false)
	private Integer pre_draw_quantity;//上月图纸数量
	
	@Column(nullable=false,precision=20,scale=2)
	private BigDecimal pre_percentage;//上月完成百分比
	
	@Column(nullable=false)
	private Integer this_draw_quantity;//当月图纸数量
	
	@Column(nullable=false,precision=20,scale=2)
	private BigDecimal this_percentage;//当月完成百分比
	
	@Column(nullable=false)
	private Integer draw_quantity;//图纸数量
	
	@Column(nullable=false,precision=20,scale=2)
	private BigDecimal total_percentage;//完成百分比

	public String getOverview_no() {
		return overview_no;
	}

	public void setOverview_no(String overview_no) {
		this.overview_no = overview_no;
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

	public Integer getTotal_draw_quantity() {
		return total_draw_quantity;
	}

	public void setTotal_draw_quantity(Integer total_draw_quantity) {
		this.total_draw_quantity = total_draw_quantity;
	}

	public Integer getPre_draw_quantity() {
		return pre_draw_quantity;
	}

	public void setPre_draw_quantity(Integer pre_draw_quantity) {
		this.pre_draw_quantity = pre_draw_quantity;
	}

	public BigDecimal getPre_percentage() {
		return pre_percentage;
	}

	public void setPre_percentage(BigDecimal pre_percentage) {
		this.pre_percentage = pre_percentage;
	}

	public Integer getThis_draw_quantity() {
		return this_draw_quantity;
	}

	public void setThis_draw_quantity(Integer this_draw_quantity) {
		this.this_draw_quantity = this_draw_quantity;
	}

	public BigDecimal getThis_percentage() {
		return this_percentage;
	}

	public void setThis_percentage(BigDecimal this_percentage) {
		this.this_percentage = this_percentage;
	}

	public Integer getDraw_quantity() {
		return draw_quantity;
	}

	public void setDraw_quantity(Integer draw_quantity) {
		this.draw_quantity = draw_quantity;
	}

	public BigDecimal getTotal_percentage() {
		return total_percentage;
	}

	public void setTotal_percentage(BigDecimal total_percentage) {
		this.total_percentage = total_percentage;
	}

}
