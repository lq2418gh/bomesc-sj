package dkd.business.dataParamConfig.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import dkd.paltform.base.domain.BusinessEntity;
@Entity
@Table(name="c_data_manager_detail")
public class DataManagerDetail extends BusinessEntity {

	private static final long serialVersionUID = 1L;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "dataManagerHead", nullable = false)
	private DataManagerHead dataManagerHead;//数据负责人头部

	@Column(nullable=false,length=32)
	private String manager_user;//数据负责人 （用户ID）
	
	@Column(nullable=false,length=20)
	private String job_number;//负责人工号
	
	@Column(nullable=false,length=20)
	private String manager_user_name;//负责人名称
	
	@Column(nullable=false,length=20)
	private String department;//部门名称
	
	@Column(nullable=false,length=20)
	private String email;//邮箱
	
	@Column(nullable=false,length=1)
	private String export_fail;//导入失败是否提醒
	
	@Column(nullable=false,length=1)
	private String lack_material;//缺料是否提醒

	public DataManagerHead getDataManagerHead() {
		return dataManagerHead;
	}

	public void setDataManagerHead(DataManagerHead dataManagerHead) {
		this.dataManagerHead = dataManagerHead;
	}

	public String getManager_user() {
		return manager_user;
	}

	public void setManager_user(String manager_user) {
		this.manager_user = manager_user;
	}

	public String getJob_number() {
		return job_number;
	}

	public void setJob_number(String job_number) {
		this.job_number = job_number;
	}

	public String getDepartment() {
		return department;
	}

	public String getManager_user_name() {
		return manager_user_name;
	}

	public void setManager_user_name(String manager_user_name) {
		this.manager_user_name = manager_user_name;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getExport_fail() {
		return export_fail;
	}

	public void setExport_fail(String export_fail) {
		this.export_fail = export_fail;
	}

	public String getLack_material() {
		return lack_material;
	}

	public void setLack_material(String lack_material) {
		this.lack_material = lack_material;
	}

}
