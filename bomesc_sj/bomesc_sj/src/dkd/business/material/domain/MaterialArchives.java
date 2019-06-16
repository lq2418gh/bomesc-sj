package dkd.business.material.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import dkd.paltform.base.domain.BusinessEntity;

/**
 * @author gaoxp
 *	物料档案
 */
@Entity
@Table(name = "base_material_archives")
public class MaterialArchives extends BusinessEntity{
	private static final long serialVersionUID = 2597741094910194219L;
	//物料编码
	@Column(nullable = false, length = 19)
	private String material_code;
	//物料名称
	@Column(nullable = false, length = 500)
	private String material_name;
	//单位
	@Column(nullable = false, length = 20)
	private String unit;
	//采办物料编码
	@Column(nullable = true, length = 500)
	private String purchase_material_code;
	//是否删除（Y/N）
	@Column(nullable = false, length = 1)
	@org.hibernate.annotations.Type(type = "yes_no")
	private boolean is_del;

	@ManyToOne
	@JoinColumn(name="standard5",nullable=false)
	private MaterialClass standard5;
	
	public String getPurchase_material_code() {
		return purchase_material_code;
	}
	public void setPurchase_material_code(String purchase_material_code) {
		this.purchase_material_code = purchase_material_code;
	}
	public boolean isIs_del() {
		return is_del;
	}
	public void setIs_del(boolean is_del) {
		this.is_del = is_del;
	}
	public MaterialClass getStandard5() {
		return standard5;
	}
	public void setStandard5(MaterialClass standard5) {
		this.standard5 = standard5;
	}
	public String getMaterial_code() {
		return material_code;
	}
	public void setMaterial_code(String material_code) {
		this.material_code = material_code;
	}
	public String getMaterial_name() {
		return material_name;
	}
	public void setMaterial_name(String material_name) {
		this.material_name = material_name;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
}
