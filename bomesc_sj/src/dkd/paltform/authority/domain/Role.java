package dkd.paltform.authority.domain;

import java.io.Serializable;

import dkd.paltform.base.domain.BusinessEntity;
import dkd.paltform.util.json.annotation.RefJsonWrite;

//角色
public class Role extends BusinessEntity implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@RefJsonWrite
	private String code;
	
	@RefJsonWrite
	private String name;
	
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
}
