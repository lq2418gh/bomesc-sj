package dkd.paltform.flowset.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import dkd.paltform.base.domain.BusinessEntity;

@Entity
@Table(name = "base_flowset_record_role")
public class ProcessRecordRole extends BusinessEntity{
	private static final long serialVersionUID = -4907045043587435744L;
	@ManyToOne
	@JoinColumn(name = "process_record", nullable = false)
	private ProcessRecord process_record;//审核节点
	@JoinColumn(nullable = false)
	private String role_id;//角色id
	@JoinColumn(nullable = false)
	private String role_name;//角色name
	@Column(nullable = true, length = 500)
	private String pend_check_user;//待审人（人员id）
	@Column(nullable = true, length = 500)
	private String pend_check_user_name;//待审人名称
	@Column(nullable = true, length = 500)
	private String pend_check_user_mail;//待审人邮箱
	@Column(nullable = true)
	private String check_user_id;//审批人id
	@Column(nullable = true)
	private String check_user_name;//审批人name
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = true)
	private Date check_date;//审批时间
	@Column(nullable = true)
	@org.hibernate.annotations.Type(type="yes_no")
	private boolean is_pass;//是否通过
	@Column(nullable = false)
	@org.hibernate.annotations.Type(type="yes_no")
	private boolean is_check;//是否审核
	@Column(nullable = true, length = 500)
	private String check_opinion;//审核意见
	public ProcessRecord getProcess_record() {
		return process_record;
	}
	public void setProcess_record(ProcessRecord process_record) {
		this.process_record = process_record;
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
	public String getPend_check_user() {
		return pend_check_user;
	}
	public void setPend_check_user(String pend_check_user) {
		this.pend_check_user = pend_check_user;
	}
	public String getPend_check_user_name() {
		return pend_check_user_name;
	}
	public void setPend_check_user_name(String pend_check_user_name) {
		this.pend_check_user_name = pend_check_user_name;
	}
	public String getPend_check_user_mail() {
		return pend_check_user_mail;
	}
	public void setPend_check_user_mail(String pend_check_user_mail) {
		this.pend_check_user_mail = pend_check_user_mail;
	}
	public String getCheck_user_id() {
		return check_user_id;
	}
	public void setCheck_user_id(String check_user_id) {
		this.check_user_id = check_user_id;
	}
	public String getCheck_user_name() {
		return check_user_name;
	}
	public void setCheck_user_name(String check_user_name) {
		this.check_user_name = check_user_name;
	}
	public Date getCheck_date() {
		return check_date;
	}
	public void setCheck_date(Date check_date) {
		this.check_date = check_date;
	}
	public boolean isIs_pass() {
		return is_pass;
	}
	public void setIs_pass(boolean is_pass) {
		this.is_pass = is_pass;
	}
	public String getCheck_opinion() {
		return check_opinion;
	}
	public void setCheck_opinion(String check_opinion) {
		this.check_opinion = check_opinion;
	}
	public boolean isIs_check() {
		return is_check;
	}
	public void setIs_check(boolean is_check) {
		this.is_check = is_check;
	}
}