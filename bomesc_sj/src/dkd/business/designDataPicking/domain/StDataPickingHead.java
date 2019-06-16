package dkd.business.designDataPicking.domain;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import dkd.paltform.base.domain.BusinessEntity;
/**
 * 结构领料单
 */
@Entity
@Table(name="b_st_data_picking_head")
public class StDataPickingHead extends BusinessEntity{

	private static final long serialVersionUID = 1L;

	@Column(nullable=false,length=32)
	private String project;//项目
	
	@Column(nullable=false,length=100)
	private String project_name;//项目名称
	
	@Column(nullable=false,length=40)
	private String job_no;//项目工作号

	@Temporal(TemporalType.DATE)
	@Column(nullable=true)
	private Date compilation_time;//编制时间
	
	@Column(nullable=false,length=200)
	private String supplier;//供货方
	
	@Column(nullable=false,length=200)
	private String storehouse;//库房
	
	@Column(nullable=false,length=200)
	private String material_category;//材料类别
	
	@Column(nullable=false,length=200)
	private String module_name;//模块名称
	
	@Column(nullable=false,length=200)
	private String picking_no;//领料单号
	
	@Column(nullable=false,length=200)
	private String issuing_material;//发料方
	
	@Column(nullable=false,length=800)
	private String purpose;//材料用途
	
	@Column(nullable=false,length=800)
	private String materials_used_for;//材料使用区域描述
	
	@Column(nullable=false,length=800)
	private String notes;//领料说明
	
	@Column(nullable=true,length=800)
	private String state;//状态
	
	@OneToMany(mappedBy="stDataPickingHead",cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
	private List<StDataPickingDetail> stDataPickingDetail;

	public List<StDataPickingDetail> getStDataPickingDetail() {
		return stDataPickingDetail;
	}

	public void setStDataPickingDetail(List<StDataPickingDetail> stDataPickingDetail) {
		this.stDataPickingDetail = stDataPickingDetail;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Date getCompilation_time() {
		return compilation_time;
	}

	public void setCompilation_time(Date compilation_time) {
		this.compilation_time = compilation_time;
	}

	public String getProject() {
		return project;
	}

	public void setProject(String project) {
		this.project = project;
	}

	public String getProject_name() {
		return project_name;
	}

	public void setProject_name(String project_name) {
		this.project_name = project_name;
	}

	public String getJob_no() {
		return job_no;
	}

	public void setJob_no(String job_no) {
		this.job_no = job_no;
	}

	public String getSupplier() {
		return supplier;
	}

	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}

	public String getStorehouse() {
		return storehouse;
	}

	public void setStorehouse(String storehouse) {
		this.storehouse = storehouse;
	}

	public String getMaterial_category() {
		return material_category;
	}

	public void setMaterial_category(String material_category) {
		this.material_category = material_category;
	}

	public String getModule_name() {
		return module_name;
	}

	public void setModule_name(String module_name) {
		this.module_name = module_name;
	}

	public String getPicking_no() {
		return picking_no;
	}

	public void setPicking_no(String picking_no) {
		this.picking_no = picking_no;
	}

	public String getIssuing_material() {
		return issuing_material;
	}

	public void setIssuing_material(String issuing_material) {
		this.issuing_material = issuing_material;
	}

	public String getPurpose() {
		return purpose;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}

	public String getMaterials_used_for() {
		return materials_used_for;
	}

	public void setMaterials_used_for(String materials_used_for) {
		this.materials_used_for = materials_used_for;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}
	
	
}
