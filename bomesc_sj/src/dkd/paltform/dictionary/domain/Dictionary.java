package dkd.paltform.dictionary.domain;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import dkd.paltform.base.domain.BusinessEntity;

@Entity
@Table(name="base_dictionary")
public class Dictionary extends BusinessEntity {
	
	private static final long serialVersionUID = -4545361564253246218L;

	@Column(nullable=false,length=20)
	private String code;
	
	@Column(nullable=false,length=40)
	private String name;
	
	@Column(nullable=false,length=40)
	private String parent;
	@Column(nullable=true)
	private int sort_no;
	
	@Transient
	private List<Dictionary> childNodes;

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

	public String getParent() {
		return parent;
	}

	public void setParent(String parent) {
		this.parent = parent;
	}

	public List<Dictionary> getChildNodes() {
		return childNodes;
	}

	public void setChildNodes(List<Dictionary> childNodes) {
		this.childNodes = childNodes;
	}

	public int getSort_no() {
		return sort_no;
	}

	public void setSort_no(int sort_no) {
		this.sort_no = sort_no; 
	}
	
	
}
