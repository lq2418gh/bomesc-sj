package dkd.paltform.log.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "log_log")
public class Log {
	@Id
	@Column(length = 32, nullable = false)
	private String id;
	
	@Column(length = 32, nullable = true)
	private String entity_id;
	//单据号
	@Column(length = 20, nullable = true)
	private String entity_code;
	
	@Column(length = 20, nullable = true)
	private String entity_group;

	@Column(length = 10, nullable = true)
	private String entity_type;

	// 登录的账号,对应user.code
	@Column(length = 20, nullable = true)
	private String user_uame;

	@Column(length = 40, nullable = true)
	private String ip_address;

	@Column(length = 20, nullable = false)
	private String operater_type;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	private Date operate_time;

	@Column(length = 4000, nullable = false)
	private String description;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getEntity_type() {
		return entity_type;
	}

	public void setEntity_type(String entity_type) {
		this.entity_type = entity_type;
	}

	public String getUser_uame() {
		return user_uame;
	}

	public void setUser_uame(String user_uame) {
		this.user_uame = user_uame;
	}

	public String getIp_address() {
		return ip_address;
	}

	public void setIp_address(String ip_address) {
		this.ip_address = ip_address;
	}

	public String getOperater_type() {
		return operater_type;
	}

	public void setOperater_type(String operater_type) {
		this.operater_type = operater_type;
	}

	public Date getOperate_time() {
		return operate_time;
	}

	public void setOperate_time(Date operate_time) {
		this.operate_time = operate_time;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getEntity_id() {
		return entity_id;
	}

	public void setEntity_id(String entity_id) {
		this.entity_id = entity_id;
	}

	public String getEntity_code() {
		return entity_code;
	}

	public void setEntity_code(String entity_code) {
		this.entity_code = entity_code;
	}

	public String getEntity_group() {
		return entity_group;
	}

	public void setEntity_group(String entity_group) {
		this.entity_group = entity_group;
	}
	
	

}
