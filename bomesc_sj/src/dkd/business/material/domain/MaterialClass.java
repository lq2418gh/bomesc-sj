package dkd.business.material.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import dkd.paltform.base.domain.TreeEntity;
import dkd.paltform.util.json.annotation.RefJsonWrite;
/**
 * @author gaoxp
 * 物资分类
 */
@Entity
@Table(name = "base_material_class")
public class MaterialClass extends TreeEntity<MaterialClass> implements Serializable{
	private static final long serialVersionUID = 7236568269591273876L;
	@Column(nullable = true, length = 20)
	@RefJsonWrite
	private int sort_no;//排序字段

	public int getSort_no() {
		return sort_no;
	}
	public void setSort_no(int sort_no) {
		this.sort_no = sort_no;
	}
}
