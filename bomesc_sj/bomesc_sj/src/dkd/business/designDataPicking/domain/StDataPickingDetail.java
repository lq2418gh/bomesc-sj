package dkd.business.designDataPicking.domain;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import dkd.paltform.base.domain.BusinessEntity;
/**
 * 结构领料单明细
 */
@Entity
@Table(name="b_st_data_picking_detail")
public class StDataPickingDetail extends BusinessEntity{

	private static final long serialVersionUID = 1L;

	@Column(nullable=true,length=200)
	private String species_name;//类型
	
	@Column(nullable=true,length=200)
	private String material_name;//材料名称
	
	@Column(nullable=true,length=200)
	private String grade;//材质
	
	@Column(nullable=true,length=200)
	private String unit;//单位
	
	@Column(nullable=true,length=200)
	private int use_quantity;//使用数量
	
	@Column(nullable=true,length=200)
	private String mto_no;//料单编号
	
	@Column(nullable=true,length=200)
	private String remnant_part_no;//使用余料编号
	
	@Column(nullable=true,precision=20,scale=2)
	private BigDecimal coating_area;//油漆面积
	
	@Column(nullable=true,precision=20,scale=2)
	private BigDecimal material_weight;//材料重量
	
	@Column(nullable=true,precision=20,scale=2)
	private String use_reason;//使用原因
	
	@Column(nullable=true,precision=20,scale=2)
	private BigDecimal section_circumference;//截面周长
	
	@Column(nullable=true,precision=20,scale=2)
	private BigDecimal unit_weight;//单位重量

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="stDataPickingHead", nullable=false)
	private StDataPickingHead stDataPickingHead;
	
	
	
	
	
	public String getSpecies_name() {
		return species_name;
	}

	public void setSpecies_name(String species_name) {
		this.species_name = species_name;
	}

	public String getMaterial_name() {
		return material_name;
	}

	public void setMaterial_name(String material_name) {
		this.material_name = material_name;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public int getUse_quantity() {
		return use_quantity;
	}

	public void setUse_quantity(int use_quantity) {
		this.use_quantity = use_quantity;
	}

	public String getMto_no() {
		return mto_no;
	}

	public void setMto_no(String mto_no) {
		this.mto_no = mto_no;
	}

	public String getRemnant_part_no() {
		return remnant_part_no;
	}

	public void setRemnant_part_no(String remnant_part_no) {
		this.remnant_part_no = remnant_part_no;
	}

	public BigDecimal getCoating_area() {
		return coating_area;
	}

	public void setCoating_area(BigDecimal coating_area) {
		this.coating_area = coating_area;
	}

	public BigDecimal getMaterial_weight() {
		return material_weight;
	}

	public void setMaterial_weight(BigDecimal material_weight) {
		this.material_weight = material_weight;
	}

	public String getUse_reason() {
		return use_reason;
	}

	public void setUse_reason(String use_reason) {
		this.use_reason = use_reason;
	}

	public BigDecimal getSection_circumference() {
		return section_circumference;
	}

	public void setSection_circumference(BigDecimal section_circumference) {
		this.section_circumference = section_circumference;
	}

	public BigDecimal getUnit_weight() {
		return unit_weight;
	}

	public void setUnit_weight(BigDecimal unit_weight) {
		this.unit_weight = unit_weight;
	}

	public StDataPickingHead getStDataPickingHead() {
		return stDataPickingHead;
	}

	public void setStDataPickingHead(StDataPickingHead stDataPickingHead) {
		this.stDataPickingHead = stDataPickingHead;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}
	
	
}
