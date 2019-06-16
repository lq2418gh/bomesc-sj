package dkd.paltform.base.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.Version;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import dkd.paltform.authority.domain.User;
import dkd.paltform.base.BusinessException;
import dkd.paltform.base.CustomJsonDateDeserializer;
import dkd.paltform.base.annotation.BeanSelect;
import dkd.paltform.util.SpringUtil;
import dkd.paltform.util.UUIDGenerator;
import dkd.paltform.util.json.JSONUtil;
import dkd.paltform.util.json.annotation.MyselfJsonIngore;
import dkd.paltform.util.json.annotation.RefJsonWrite;

//@Entity
//@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@MappedSuperclass
public class BusinessEntity implements Serializable{
	@BeanSelect(gain=false)
	private static final long serialVersionUID = -6777629979178327661L;

	// 实体的id,由父类生成,采用UUID的方式实现
	@Id
	@Column(nullable = true)
	@RefJsonWrite
	private String id;

	// 实体的版本,实现并发控制
	@Version
	@Column(nullable = true)
	private int version;
	// 实体的状态,有以下属性 新增,修改,删除,未修改等,不进行持久化
	@Transient
	@MyselfJsonIngore
	private String sys_status;
	// 实体的创建时间
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	@JsonDeserialize(using = CustomJsonDateDeserializer.class)
	private Date entity_createdate;
	@Column(nullable = true)
	private String entity_createuser;
	@Column(nullable = true)
	private String entity_modifyuser;
	// 实体的最新修改时间
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = true)
	@JsonDeserialize(using = CustomJsonDateDeserializer.class) 
	private Date entity_modifydate;

	@PrePersist
	private void entityBeforeInsert() {
		setId(UUIDGenerator.getUUID());
		setEntity_createdate(new Date());
		User currentUser = SpringUtil.getCurrentUser();
		if (currentUser != null ) {
			setEntity_createuser(currentUser.getName());
		}
	}

	// 更新实体时,给修改时间赋值
	@PreUpdate
	private void entityBeforeModify() {
		setEntity_modifydate(new Date());
		User currentUser = SpringUtil.getCurrentUser();
		if (currentUser != null ) {
			setEntity_modifyuser(currentUser.getName());
		}
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public String getSys_status() {
		return sys_status;
	}

	public void setSys_status(String sys_status) {
		this.sys_status = sys_status;
	}

	public Date getEntity_createdate() {
		return entity_createdate;
	}

	public void setEntity_createdate(Date entity_createdate) {
		this.entity_createdate = entity_createdate;
	}

	public String getEntity_createuser() {
		return entity_createuser;
	}

	public void setEntity_createuser(String entity_createuser) {
		this.entity_createuser = entity_createuser;
	}

	public String getEntity_modifyuser() {
		return entity_modifyuser;
	}

	public void setEntity_modifyuser(String entity_modifyuser) {
		this.entity_modifyuser = entity_modifyuser;
	}

	public Date getEntity_modifydate() {
		return entity_modifydate;
	}

	public void setEntity_modifydate(Date entity_modifydate) {
		this.entity_modifydate = entity_modifydate;
	}

	/**
	 * @Title: validateModel
	 * @Description:验证Entity数据，不符合设定规则，则抛出BusinessException
	 * @param entity
	 * @author CST-TONGLZ
	 * @return void
	 * @throws
	 */
	public void validateModel() {
		Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
		Set<ConstraintViolation<BusinessEntity>> constraintViolations = validator.validate(this);
		Iterator<ConstraintViolation<BusinessEntity>> iter = constraintViolations.iterator();
		while (iter.hasNext()) {// 只捕获第一个抛出
			ConstraintViolation<BusinessEntity> con = iter.next();
			String message = con.getMessage();
			String field = con.getPropertyPath().toString();
			int index = getExceptionIndex();
			throw new BusinessException(index, field, message);
		}
	}

	/**
	 * @Title: validateProperty
	 * @Description:验证Entity指定的属性，不合法则抛出BusinessException
	 * @param entity
	 * @param propertys
	 *            属性数组
	 * @author CST-TONGLZ
	 * @return void
	 * @throws
	 */
	public void validateProperty(String... propertys) {
		Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
		for (String property : propertys) {
			Set<ConstraintViolation<BusinessEntity>> constraintViolations = validator.validateProperty(this, property);
			Iterator<ConstraintViolation<BusinessEntity>> iter = constraintViolations.iterator();
			while (iter.hasNext()) {
				ConstraintViolation<BusinessEntity> con = iter.next();
				String message = con.getMessage();
				String field = con.getPropertyPath().toString();
				int index = getExceptionIndex();
				throw new BusinessException(index, field, message);
			}
		}
	}

	/**
	 * @Title: getExceptionIndex
	 * @Description:获取异常Index
	 * @param
	 * @author CST-TONGLZ
	 * @return int
	 * @throws
	 */
	private int getExceptionIndex() {
		if (this instanceof DocLine) {
			return ((DocLine) this).getIndex();
		}
		return -1;
	}

	public String toJson() {
		return JSONUtil.getJsonByEntity(this);
	}

}
