package dkd.business.mto.domain;

import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import dkd.paltform.base.domain.BusinessEntity;
import dkd.paltform.dictionary.domain.Dictionary;

@Entity
@Table(name = "b_mto_head")
public class MtoHead extends BusinessEntity{
	private static final long serialVersionUID = 1L;
	//材料申购单号
	@Column(nullable = false, length = 50)
	private String mto_no;
	//采购状态
	@Column(nullable = false, length = 10)
	private String state;
	//采购日期
	@Column(nullable = false)
	@Temporal(TemporalType.DATE)
	private Date purchase_date;
	//项目id
	@Column(nullable = false,length = 32)
	private String project;
	//项目名称
	@Column(nullable = false,length = 80)
	private String project_name;
	//项目工作号
	@Column(nullable = false,length = 80)
	private String job_no;
	//专业
	@ManyToOne
	@JoinColumn(name="major",nullable=false)
	private Dictionary major;
	//采购类型      默认为正常采办
	@Column(nullable = false,length = 10)
	private String purchase_type;
	//应急采购原因
	@Column(nullable = true, length = 4000)
	private String emergency_reason;
	//采购技术
	@Column(nullable = true, length = 4000)
	private String purchase_tech;
	//是否检查库存    Y/N
	@Column(nullable = true, length = 1)
	@org.hibernate.annotations.Type(type = "yes_no")
	private boolean if_check_stock;
	//是否使用库存   Y/N
	@org.hibernate.annotations.Type(type = "yes_no")
	@Column(nullable = true, length = 1)
	private boolean if_use_stock;
	//库存使用说明
	@Column(nullable = true, length = 4000)
	private String stock_memo;
	//确认人
	@Column(nullable = true, length = 50)
	private String confirm_user;
	//确认日期
	@Column(nullable = true)
	@Temporal(TemporalType.DATE)
	private Date confirm_date;
	@Column(nullable = true,length=500)
	private String purchase_company;
	//材料申购单明细
	@OneToMany(mappedBy = "mto_head", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
	private List<MtoDetail> details;
	public String getMto_no() {
		return mto_no;
	}
	public void setMto_no(String mto_no) {
		this.mto_no = mto_no;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public Date getPurchase_date() {
		return purchase_date;
	}
	public void setPurchase_date(Date purchase_date) {
		this.purchase_date = purchase_date;
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
	public String getPurchase_type() {
		return purchase_type;
	}
	public void setPurchase_type(String purchase_type) {
		this.purchase_type = purchase_type;
	}
	public String getEmergency_reason() {
		return emergency_reason;
	}
	public void setEmergency_reason(String emergency_reason) {
		this.emergency_reason = emergency_reason;
	}
	public String getPurchase_tech() {
		return purchase_tech;
	}
	public void setPurchase_tech(String purchase_tech) {
		this.purchase_tech = purchase_tech;
	}
	public boolean isIf_check_stock() {
		return if_check_stock;
	}
	public void setIf_check_stock(boolean if_check_stock) {
		this.if_check_stock = if_check_stock;
	}
	public boolean isIf_use_stock() {
		return if_use_stock;
	}
	public void setIf_use_stock(boolean if_use_stock) {
		this.if_use_stock = if_use_stock;
	}
	public String getStock_memo() {
		return stock_memo;
	}
	public void setStock_memo(String stock_memo) {
		this.stock_memo = stock_memo;
	}
	public List<MtoDetail> getDetails() {
		return details;
	}
	public void setDetails(List<MtoDetail> details) {
		this.details = details;
	}
	public String getConfirm_user() {
		return confirm_user;
	}
	public void setConfirm_user(String confirm_user) {
		this.confirm_user = confirm_user;
	}
	public Date getConfirm_date() {
		return confirm_date;
	}
	public void setConfirm_date(Date confirm_date) {
		this.confirm_date = confirm_date;
	}
	public String getPurchase_company() {
		return purchase_company;
	}
	public void setPurchase_company(String purchase_company) {
		this.purchase_company = purchase_company;
	}
}
