package dkd.business.project.domain;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import dkd.paltform.base.annotation.BeanSelect;
import dkd.paltform.base.domain.BusinessEntity;

@Entity
@Table(name="b_project")
public class Project extends BusinessEntity{
	@BeanSelect(gain=false)
	private static final long serialVersionUID = -4058396058686502258L;
	//项目ID
	@BeanSelect
	@Column(nullable=false,length=32)
	private String projectId;
	//项目名称
	@BeanSelect
	@Column(length=100,nullable = false)
	private String project_name;
	//项目工作号
	@BeanSelect
	@Column(length=40,nullable = false)
	private String job_no;
	//结构专业图纸数量信息
	@BeanSelect
	@Column
	private Integer draw_quantity_st;
	//管线专业图纸数量信息
	@BeanSelect
	@Column
	private Integer draw_quantity_pi;
	//电仪专业图纸数量信息
	@BeanSelect
	@Column
	private Integer draw_quantity_ei;
	//空调专业图纸数量信息
	@BeanSelect
	@Column
	private Integer draw_quantity_hvac;
	//舾装专业图纸数量信息
	@BeanSelect
	@Column
	private Integer draw_quantity_ar;
	//结构专业额定工时
	@BeanSelect
	@Column(name = "rate_man_hour_st",precision = 20, scale = 1)
	private BigDecimal rate_man_hour_st;
	//管线专业额定工时
	@BeanSelect
	@Column(name = "rate_man_hour_pi",precision = 20, scale = 1)
	private BigDecimal rate_man_hour_pi;
	//电仪专业额定工时
	@BeanSelect
	@Column(name = "rate_man_hour_ei",precision = 20, scale = 1)
	private BigDecimal rate_man_hour_ei;
	//空调专业额定工时
	@BeanSelect
	@Column(name = "rate_man_hour_hvac",precision = 20, scale = 1)
	private BigDecimal rate_man_hour_hvac;
	//舾装专业额定工时
	@BeanSelect
	@Column(name = "rate_man_hour_ar",precision = 20, scale = 1)
	private BigDecimal rate_man_hour_ar;
	
	//状态
	@BeanSelect(gain=false)
	@Transient
	private String state;
	//确认人
	@BeanSelect(gain=false)
	@Transient
	private String confirm_user_name;
	//确认日期
	@BeanSelect(gain=false)
	@Transient
	private Date confirm_date;
	//合同号
	@BeanSelect(gain=false)
	@Transient
	private String project_contract_no;
	//项目签订公司
	@BeanSelect(gain=false)
	@Transient
	private String company_name;
	//合同甲方
	@BeanSelect(gain=false)
	@Transient
	private String contractor;
	//项目用户1
	@BeanSelect(gain=false)
	@Transient
	private String project_owner_1;
	//项目用户2
	@BeanSelect(gain=false)
	@Transient
	private String project_owner_2;
	//项目经理
	@BeanSelect(gain=false)
	@Transient
	private String project_manager_name;
	//项目合同开始时间
	@BeanSelect(gain=false)
	@Transient
	private Date contract_start_time;
	//项目合同结束时间
	@BeanSelect(gain=false)
	@Transient
	private Date contract_finish_time;
	//项目合同实际结束时间
	@BeanSelect(gain=false)
	@Transient
	private Date actual_finish_time;
	//项目合同类型
	@BeanSelect(gain=false)
	@Transient
	private String contract_type_name;
	//项目类型
	@BeanSelect(gain=false)
	@Transient
	private String project_type;
	//项目分类
	@BeanSelect(gain=false)
	@Transient
	private String project_category;
	//设计类型
	@BeanSelect(gain=false)
	@Transient
	private String design_type_name;
	//项目重量
	@BeanSelect(gain=false)
	@Transient
	private BigDecimal dry_weight;
	//项目面积
	@BeanSelect(gain=false)
	@Transient
	private BigDecimal project_area;
	
	public String getProjectId() {
		return projectId;
	}
	public void setProjectId(String projectId) {
		this.projectId = projectId;
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
	public Integer getDraw_quantity_st() {
		return draw_quantity_st;
	}
	public void setDraw_quantity_st(Integer draw_quantity_st) {
		this.draw_quantity_st = draw_quantity_st;
	}
	public Integer getDraw_quantity_pi() {
		return draw_quantity_pi;
	}
	public void setDraw_quantity_pi(Integer draw_quantity_pi) {
		this.draw_quantity_pi = draw_quantity_pi;
	}
	public Integer getDraw_quantity_ei() {
		return draw_quantity_ei;
	}
	public void setDraw_quantity_ei(Integer draw_quantity_ei) {
		this.draw_quantity_ei = draw_quantity_ei;
	}
	public Integer getDraw_quantity_hvac() {
		return draw_quantity_hvac;
	}
	public void setDraw_quantity_hvac(Integer draw_quantity_hvac) {
		this.draw_quantity_hvac = draw_quantity_hvac;
	}
	public Integer getDraw_quantity_ar() {
		return draw_quantity_ar;
	}
	public void setDraw_quantity_ar(Integer draw_quantity_ar) {
		this.draw_quantity_ar = draw_quantity_ar;
	}
	public BigDecimal getRate_man_hour_st() {
		return rate_man_hour_st;
	}
	public void setRate_man_hour_st(BigDecimal rate_man_hour_st) {
		this.rate_man_hour_st = rate_man_hour_st;
	}
	public BigDecimal getRate_man_hour_pi() {
		return rate_man_hour_pi;
	}
	public void setRate_man_hour_pi(BigDecimal rate_man_hour_pi) {
		this.rate_man_hour_pi = rate_man_hour_pi;
	}
	public BigDecimal getRate_man_hour_ei() {
		return rate_man_hour_ei;
	}
	public void setRate_man_hour_ei(BigDecimal rate_man_hour_ei) {
		this.rate_man_hour_ei = rate_man_hour_ei;
	}
	public BigDecimal getRate_man_hour_hvac() {
		return rate_man_hour_hvac;
	}
	public void setRate_man_hour_hvac(BigDecimal rate_man_hour_hvac) {
		this.rate_man_hour_hvac = rate_man_hour_hvac;
	}
	public BigDecimal getRate_man_hour_ar() {
		return rate_man_hour_ar;
	}
	public void setRate_man_hour_ar(BigDecimal rate_man_hour_ar) {
		this.rate_man_hour_ar = rate_man_hour_ar;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getConfirm_user_name() {
		return confirm_user_name;
	}
	public void setConfirm_user_name(String confirm_user_name) {
		this.confirm_user_name = confirm_user_name;
	}
	public Date getConfirm_date() {
		return confirm_date;
	}
	public void setConfirm_date(Date confirm_date) {
		this.confirm_date = confirm_date;
	}
	public String getProject_contract_no() {
		return project_contract_no;
	}
	public void setProject_contract_no(String project_contract_no) {
		this.project_contract_no = project_contract_no;
	}
	public String getCompany_name() {
		return company_name;
	}
	public void setCompany_name(String company_name) {
		this.company_name = company_name;
	}
	public String getContractor() {
		return contractor;
	}
	public void setContractor(String contractor) {
		this.contractor = contractor;
	}
	public String getProject_owner_1() {
		return project_owner_1;
	}
	public void setProject_owner_1(String project_owner_1) {
		this.project_owner_1 = project_owner_1;
	}
	public String getProject_owner_2() {
		return project_owner_2;
	}
	public void setProject_owner_2(String project_owner_2) {
		this.project_owner_2 = project_owner_2;
	}
	
	public String getProject_manager_name() {
		return project_manager_name;
	}
	public void setProject_manager_name(String project_manager_name) {
		this.project_manager_name = project_manager_name;
	}
	public Date getContract_start_time() {
		return contract_start_time;
	}
	public void setContract_start_time(Date contract_start_time) {
		this.contract_start_time = contract_start_time;
	}
	public Date getContract_finish_time() {
		return contract_finish_time;
	}
	public void setContract_finish_time(Date contract_finish_time) {
		this.contract_finish_time = contract_finish_time;
	}
	public Date getActual_finish_time() {
		return actual_finish_time;
	}
	public void setActual_finish_time(Date actual_finish_time) {
		this.actual_finish_time = actual_finish_time;
	}
	public String getContract_type_name() {
		return contract_type_name;
	}
	public void setContract_type_name(String contract_type_name) {
		this.contract_type_name = contract_type_name;
	}
	public String getProject_type() {
		return project_type;
	}
	public void setProject_type(String project_type) {
		this.project_type = project_type;
	}
	public String getProject_category() {
		return project_category;
	}
	public void setProject_category(String project_category) {
		this.project_category = project_category;
	}
	public String getDesign_type_name() {
		return design_type_name;
	}
	public void setDesign_type_name(String design_type_name) {
		this.design_type_name = design_type_name;
	}
	public BigDecimal getDry_weight() {
		return dry_weight;
	}
	public void setDry_weight(BigDecimal dry_weight) {
		this.dry_weight = dry_weight;
	}
	public BigDecimal getProject_area() {
		return project_area;
	}
	public void setProject_area(BigDecimal project_area) {
		this.project_area = project_area;
	}
	
}
