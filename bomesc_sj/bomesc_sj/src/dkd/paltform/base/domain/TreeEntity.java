package dkd.paltform.base.domain;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import dkd.paltform.util.json.annotation.MyselfJsonIngore;
import dkd.paltform.util.json.annotation.RefJsonWrite;

/**
 * @ClassName: TreeEntity
 * @Description:树菜单基类
 * @author CST-TONGLZ
 * @date 2016-3-16 下午1:24:54
 * 
 */
@MappedSuperclass
public class TreeEntity<T> extends BusinessEntity {
	private static final long serialVersionUID = 3616199819302354694L;

	@Column(nullable = false, length = 40)
	@RefJsonWrite
	@NotNull(message = "code不允许为空")
	private String code;

	@Column(nullable = false, length = 40)
	@RefJsonWrite
	@NotNull(message = "name不允许为空")
	private String name;
	
	@ManyToOne(fetch=FetchType.EAGER) 
	@JoinColumn(name = "parent", nullable = true)
	@RefJsonWrite
	private T parent;
	
	@OneToMany(mappedBy = "parent", fetch = FetchType.EAGER)
	@Fetch(FetchMode.SUBSELECT)
	@MyselfJsonIngore
	private List<T> details;
	
	@Column(nullable = true, precision = 4)
	@RefJsonWrite
	private int levels = 0;

	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code.toUpperCase();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public T getParent() {
		return parent;
	}

	public void setParent(T parent) {
		this.parent = parent;
	}

	public int getLevels() {
		return levels;
	}

	public void setLevels(int levels) {
		this.levels = levels;
	}

	public List<T> getDetails() {
		return details;
	}

	public void setDetails(List<T> details) {
		this.details = details;
	}

}