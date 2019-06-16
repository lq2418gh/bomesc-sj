package dkd.paltform.base.domain;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;

import dkd.paltform.util.json.annotation.RefJsonWrite;

/**
 * 扁平档案
 * @author 悦
 * @date 2015年10月8日
 * @version 1.0
 */

@MappedSuperclass
public class ArchiveEntity extends BusinessEntity{
	private static final long serialVersionUID = 1339318976084425697L;

	@Column(nullable = false, length = 20)
	@RefJsonWrite
	@NotNull(message = "code不允许为空")
	private String code;
	
	@Column(nullable = false, length = 40)
	@RefJsonWrite 
	@NotNull(message = "name不允许为空")
	private String name;
	@Column(nullable = true)
	@org.hibernate.annotations.Type(type="yes_no")
	private Boolean isvalidate = true;
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getIsvalidate() {
		return isvalidate;
	}

	public void setIsvalidate(Boolean isvalidate) {
		this.isvalidate = isvalidate;
	}
	
}
