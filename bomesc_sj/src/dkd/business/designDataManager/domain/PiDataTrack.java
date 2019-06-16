package dkd.business.designDataManager.domain;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import dkd.paltform.base.annotation.BeanSelect;
import dkd.paltform.base.domain.BusinessEntity;
//结构专业数据跟踪表
@Entity
@Table(name="b_pi_track_table")
public class PiDataTrack extends BusinessEntity{

	private static final long serialVersionUID = 1L;
	
	@Column(nullable=false,length=32)
	private String project;//项目
	
	@BeanSelect(fieldName="项目名称",isSearch=false)
	@Column(nullable=false,length=200)
	private String project_name;//项目名称
	
	@BeanSelect(fieldName="项目工作号")
	@Column(nullable=false,length=200)
	private String job_no;//项目工作号
	
	@BeanSelect(fieldName="模块号")
	@Column(nullable=true,length=200)
	private String module_no;//模块名称
	
	@BeanSelect(fieldName="模块工作包号")
	@Column(nullable=true,length=200)
	private String work_package_no;//模块工作包号
	
	@BeanSelect(fieldName="试压包号")
	@Column(nullable=true,length=200)
	private String test_package_no;//试压包号
	
	@BeanSelect(fieldName="ISO图纸编号")
	@Column(nullable=true,length=200)
	private String iso_drawing_no;
	
	@BeanSelect(fieldName="页码")
	@Column(nullable=true,length=200)
	private String page_no;//页码
	
	@BeanSelect(fieldName="总页数")
	@Column(nullable=true,length=200)
	private String total_page;//总页数
	
	@BeanSelect(fieldName="ISO图纸版本")
	@Column(nullable=true,length=200)
	private String iso_drawing_rev;//ISO图纸版本
	
	@BeanSelect(fieldName="加设图纸编号")
	@Column(nullable=true,length=200)
	private String shop_draw_no;//加设图纸编号
	
	@BeanSelect(fieldName="加设图纸版本")
	@Column(nullable=true,length=200)
	private String shop_draw_rev;//加设图纸版本
	
	@BeanSelect(fieldName="系统代码")
	@Column(nullable=false,length=200)
	private String system_code;//系统代码
	
	@BeanSelect(fieldName="管线级别")
	@Column(nullable=false,length=200)
	private String pipe_class;//管线级别
	
	@BeanSelect(fieldName="管线号")
	@Column(nullable=false,length=200)
	private String line_no;//管线号
	
	@BeanSelect(fieldName="管段号")
	@Column(nullable=true,length=200)
	private String spool_no;//管段号
	
	@BeanSelect(fieldName="零件名称")
	@Column(nullable=false,length=200)
	private String part_name;//零件名称
	
	@BeanSelect(fieldName="零件类型")
	@Column(nullable=true,length=200)
	private String part_type;//零件类型
	
	@BeanSelect(fieldName="零件编号")
	@Column(nullable=true,length=200)
	private String part_no;//零件编号
	
	@BeanSelect(fieldName="业主零件编号")
	@Column(nullable=true,length=200)
	private String client_part_no;//业主零件编号
	
	@BeanSelect(fieldName="识别编号")
	@Column(nullable=true,length=200)
	private String ident_code;//识别编号(对应进出口的tagNo)
	
	@BeanSelect(fieldName="物料编码")
	@Column(nullable=true,length=200)
	private String material_code;//物料编码
	
	@BeanSelect(fieldName="位号")
	@Column(nullable=true,length=200)
	private String tag_no;//位号
	
	@BeanSelect(fieldName="零件主尺寸")
	@Column(nullable=false,length=200)
	private String part_main_size;//零件主尺寸
	
	@BeanSelect(fieldName="零件副尺寸")
	@Column(nullable=true,length=200)
	private String part_vice_size;//零件副尺寸
	
	@BeanSelect(fieldName="压力级别")
	@Column(nullable=true,length=200)
	private String pressure_class;//压力级别
	
	@BeanSelect(fieldName="主壁厚级别")
	@Column(nullable=true,length=200)
	private String main_thickness_grade;//主壁厚级别
	
	@BeanSelect(fieldName="次壁厚级别")
	@Column(nullable=true,length=200)
	private String vice_thickness_grade;//次壁厚级别
	
	@BeanSelect(fieldName="主连接方式")
	@Column(nullable=true,length=200)
	private String main_connection_type;//主连接方式
	
	@BeanSelect(fieldName="次连接方式")
	@Column(nullable=true,length=200)
	private String vice_connection_type;//次连接方式
	
	@BeanSelect(fieldName="材质")
	@Column(nullable=true,length=200)
	private String grade;//材质
	
	@BeanSelect(fieldName="特殊要求")
	@Column(nullable=true,length=200)
	private String special_requirement;//特殊要求
	
	@BeanSelect(fieldName="焊口编号1")
	@Column(nullable=true,length=200)
	private String welding_no_one;//焊口编号1
	
	@BeanSelect(fieldName="焊口编号2")
	@Column(nullable=true,length=200)
	private String welding_no_two;//焊口编号2
	
	@BeanSelect(fieldName="焊口编号3")
	@Column(nullable=true,length=200)
	private String welding_no_three;//焊口编号3
	
	@BeanSelect(fieldName="焊口编号4")
	@Column(nullable=true,length=200)
	private String welding_no_four;//焊口编号4
	
	@BeanSelect(fieldName="数量",isSearch=false)
	@Column(nullable=true)
	private Integer qty;//数量
	
	@BeanSelect(fieldName="净长度",isSearch=false)
	@Column(nullable=true)
	private Integer net_length;//净长度
	
	@BeanSelect(fieldName="余量")
	@Column(nullable=true)
	private Integer additional_length;//余量
	
	@BeanSelect(fieldName="总长度")
	@Column(nullable=true)
	private Integer final_length;//总长度
	
	@BeanSelect(fieldName="施工领料单编号")
	@Column(nullable=true,length=200)
	private String material_requisition_no;//施工领料单编号
	
	@BeanSelect(fieldName="套料图纸编号")
	@Column(nullable=true,length=200)
	private String nesting_draw_no;//套料图纸编号
	
	@BeanSelect(fieldName="套料图纸版本")
	@Column(nullable=true,length=200)
	private String nesting_draw_rev;//套料图纸版本
	
	@BeanSelect(fieldName="母材编号")
	@Column(nullable=true,length=200)
	private String bulk_material_no;//对应母材编号
	
	@BeanSelect(fieldName="余料编号")
	@Column(nullable=true,length=200)
	private String remnant_part_no;//余料编号
	
	@BeanSelect(fieldName="余料尺寸")
	@Column(nullable=true)
	private Integer remnant_part_size;//余料尺寸
	
	@BeanSelect(fieldName="条形码编号")
	@Column(nullable=true,length=200)
	private String bar_code_no;//条形码编号
	
	@BeanSelect(fieldName="单位净重")
	@Column(nullable=true,precision=20,scale=2)
	private BigDecimal unit_net_weight;//单位净重
	
	@BeanSelect(fieldName="净重")
	@Column(nullable=true,precision=20,scale=2)
	private BigDecimal net_weight;//净重
	
	@BeanSelect(fieldName="表面处理")
	@Column(nullable=true,length=200)
	private String surface_treatment;//表面处理
	
	@BeanSelect(fieldName="油漆配套")
	@Column(nullable=true,length=200)
	private String coating_system;//油漆配套
	
	@BeanSelect(fieldName="单位净表面积")
	@Column(nullable=true,length=200)
	private String unit_net_area;//单位净表面积
	
	@BeanSelect(fieldName="净表面积",isSearch=false)
	@Column(nullable=true,precision=20,scale=2)
	private BigDecimal net_area;//净表面积
	
	@BeanSelect(fieldName="保温类型")
	@Column(nullable=true,length=200)
	private String insultation_type;//保温类型
	
	@BeanSelect(fieldName="保温厚度")
	@Column(nullable=true)
	private Integer insultation_thk;//保温厚度
	
	@BeanSelect(fieldName="保温表面积")
	@Column(nullable=true,precision=20,scale=2)
	private BigDecimal insultation_surface_area;//保温表面积
	
	@BeanSelect(fieldName="保温延米")
	@Column(nullable=true,precision=20,scale=2)
	private BigDecimal insultation_equivalent_length;//保温延米
	
	@BeanSelect(fieldName="供货方")
	@Column(nullable=false,length=200)
	private String supplier;//供货方
	
	@BeanSelect(fieldName="料单编号")
	@Column(nullable=true,length=200)
	private String mto_no;//料单编号
	
	@BeanSelect(fieldName="料单行号")
	@Column(nullable=true,length=200)
	private String mto_row_no;//料单行号
	
	@BeanSelect(fieldName="采购余量")
	@Column(nullable=true,length=200)
	private String recommend_surplus;//采购余量
	
	@BeanSelect(fieldName="升版作废",isSearch=false)
	@Column(nullable=false,length=10)
	private String wasted_by_drawing_update;//材料是否因升版作废
	
	@BeanSelect(fieldName="升版增加")
	@Column(nullable=false,length=10)
	private String added_by_drawing_update;//材料是否因升版增加
	
	@BeanSelect(fieldName="业主付款编号")
	@Column(nullable=true,length=200)
	private String pay_item_no;//业主付款编号
	
	@BeanSelect(fieldName="备注")
	@Column(nullable=true,length=200)
	private String remark;//备注
	
	@Temporal(TemporalType.DATE)
	@Column(nullable=false)
	private Date update_date;//更新日期
	
	@Column(nullable=false,length=1)
	@org.hibernate.annotations.Type(type = "yes_no")
	private Boolean is_history;//是否是历史记录
	
	@Column(nullable=false)
	private Integer change_size;//变更次数
	
	@Column(nullable=true,length=200)
	private String reason_for_change;//变更原因
	
	@Column(nullable=true,length=200)
	@Temporal(TemporalType.TIMESTAMP)
	private Date change_cause_mark_date;//变更原因标注日期
	
	@Column(nullable=true,length=200)
	private String change_cause_mark_user;//变更原因标记人
	
	@BeanSelect(fieldName="设计料单中的数量",isSearch=false)
	@Column(nullable=true)
	private Integer quantity_in_dm_mto;//设计料单中的数量
	
	@BeanSelect(fieldName="采办料单中的数量",isSearch=false)
	@Column(nullable=true)
	private Integer quantity_in_pd_mto;//采办料单中的数量
	
	@BeanSelect(fieldName="生成领料单数量",isSearch=false)
	@Column(nullable=true)
	private Integer generate_request_number;//生成领料单数量
	
	@BeanSelect(fieldName="厂家")
	@Column(nullable=true,length=200)
	private String vendor;//厂家
	
	@BeanSelect(fieldName="设备厂址")
	@Column(nullable=true,length=200)
	private String site;//设备厂址
	
	@BeanSelect(fieldName="设备网址")
	@Column(nullable=true,length=200)
	private String website;//设备网址
	
	@BeanSelect(fieldName="联系电话")
	@Column(nullable=true,length=200)
	private String tellphone_no;//联系电话
	
	@BeanSelect(fieldName="补充列1")
	@Column(nullable=true,length=200)
	private String column1;//补充列1
	
	@BeanSelect(fieldName="补充列2")
	@Column(nullable=true,length=200)
	private String column2;//补充列2
	
	@BeanSelect(fieldName="补充列3")
	@Column(nullable=true,length=200)
	private String column3;//补充列3
	
	@BeanSelect(fieldName="补充列4")
	@Column(nullable=true,length=200)
	private String column4;//补充列4
	
	@BeanSelect(fieldName="补充列5")
	@Column(nullable=true,length=200)
	private String column5;//补充列5
	
	@BeanSelect(fieldName="补充列6")
	@Column(nullable=true,length=200)
	private String column6;//补充列6
	
	@BeanSelect(fieldName="补充列7")
	@Column(nullable=true,length=200)
	private String column7;//补充列7
	
	@BeanSelect(fieldName="补充列8")
	@Column(nullable=true,length=200)
	private String column8;//补充列8
	
	@BeanSelect(fieldName="补充列9")
	@Column(nullable=true,length=200)
	private String column9;//补充列9
	
	@BeanSelect(fieldName="补充列10")
	@Column(nullable=true,length=200)
	private String column10;//补充列10
	
	@BeanSelect(fieldName="补充列11")
	@Column(nullable=true,length=200)
	private String column11;//补充列11
	
	@BeanSelect(fieldName="补充列12")
	@Column(nullable=true,length=200)
	private String column12;//补充列12
	
	@BeanSelect(fieldName="补充列13")
	@Column(nullable=true,length=200)
	private String column13;//补充列13
	
	@BeanSelect(fieldName="补充列14")
	@Column(nullable=true,length=200)
	private String column14;//补充列14
	
	@BeanSelect(fieldName="补充列15")
	@Column(nullable=true,length=200)
	private String column15;//补充列15
	
	@BeanSelect(fieldName="补充列16")
	@Column(nullable=true,length=200)
	private String column16;//补充列16
	
	@BeanSelect(fieldName="补充列17")
	@Column(nullable=true,length=200)
	private String column17;//补充列17
	
	@BeanSelect(fieldName="补充列18")
	@Column(nullable=true,length=200)
	private String column18;//补充列18
	
	@BeanSelect(fieldName="补充列19")
	@Column(nullable=true,length=200)
	private String column19;//补充列19
	
	@BeanSelect(fieldName="补充列20")
	@Column(nullable=true,length=200)
	private String column20;//补充列20
	
	@Column(nullable=true,length=20)
	private String state;//状态（null或空：新增；修改；删除）
	
	@BeanSelect(fieldName="入库数量",isSearch=false)
	@Column(nullable=true)
	private Integer quantity_of_inwarehouse;//入库数量
	
	@BeanSelect(fieldName="领用出库数量",isSearch=false)
	@Column(nullable=true)
	//private Integer quantity_of_outwarehouse;//出库数量(改成具体模块的出库数量)
	private Integer recipients_of_warehouse;//领用出库
	
	@BeanSelect(fieldName="废旧物资出库数量",isSearch=false)
	@Column(nullable=true)
	private Integer waste_materials_outbound;//废旧物资出库
	
	@BeanSelect(fieldName="调拨出库数量",isSearch=false)
	@Column(nullable=true)
	private Integer in_allocating_outbound;//调拨出库
	
	@BeanSelect(fieldName="建造退库数量",isSearch=false)
	@Column(nullable=true)
	private Integer construction_of_withdrawing;//建造退库
	
	@BeanSelect(fieldName="库存",isSearch=false)
	@Column(nullable=true)
	private Integer stock;//库存
	
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

	public String getModule_no() {
		return module_no;
	}

	public void setModule_no(String module_no) {
		this.module_no = module_no;
	}

	public String getWork_package_no() {
		return work_package_no;
	}

	public void setWork_package_no(String work_package_no) {
		this.work_package_no = work_package_no;
	}

	public String getTest_package_no() {
		return test_package_no;
	}

	public void setTest_package_no(String test_package_no) {
		this.test_package_no = test_package_no;
	}

	public String getIso_drawing_no() {
		return iso_drawing_no;
	}

	public void setIso_drawing_no(String iso_drawing_no) {
		this.iso_drawing_no = iso_drawing_no;
	}

	public String getPage_no() {
		return page_no;
	}

	public void setPage_no(String page_no) {
		this.page_no = page_no;
	}

	public String getTotal_page() {
		return total_page;
	}

	public void setTotal_page(String total_page) {
		this.total_page = total_page;
	}

	public String getIso_drawing_rev() {
		return iso_drawing_rev;
	}

	public void setIso_drawing_rev(String iso_drawing_rev) {
		this.iso_drawing_rev = iso_drawing_rev;
	}

	public String getShop_draw_no() {
		return shop_draw_no;
	}

	public void setShop_draw_no(String shop_draw_no) {
		this.shop_draw_no = shop_draw_no;
	}

	public String getShop_draw_rev() {
		return shop_draw_rev;
	}

	public void setShop_draw_rev(String shop_draw_rev) {
		this.shop_draw_rev = shop_draw_rev;
	}

	public String getSystem_code() {
		return system_code;
	}

	public void setSystem_code(String system_code) {
		this.system_code = system_code;
	}

	public String getPipe_class() {
		return pipe_class;
	}

	public void setPipe_class(String pipe_class) {
		this.pipe_class = pipe_class;
	}

	public String getLine_no() {
		return line_no;
	}

	public void setLine_no(String line_no) {
		this.line_no = line_no;
	}

	public String getSpool_no() {
		return spool_no;
	}

	public void setSpool_no(String spool_no) {
		this.spool_no = spool_no;
	}

	public String getPart_name() {
		return part_name;
	}

	public void setPart_name(String part_name) {
		this.part_name = part_name;
	}

	public String getPart_type() {
		return part_type;
	}

	public void setPart_type(String part_type) {
		this.part_type = part_type;
	}

	public String getPart_no() {
		return part_no;
	}

	public void setPart_no(String part_no) {
		this.part_no = part_no;
	}

	public String getClient_part_no() {
		return client_part_no;
	}

	public void setClient_part_no(String client_part_no) {
		this.client_part_no = client_part_no;
	}

	public String getIdent_code() {
		return ident_code;
	}

	public void setIdent_code(String ident_code) {
		this.ident_code = ident_code;
	}

	public String getMaterial_code() {
		return material_code;
	}

	public void setMaterial_code(String material_code) {
		this.material_code = material_code;
	}

	public String getTag_no() {
		return tag_no;
	}

	public void setTag_no(String tag_no) {
		this.tag_no = tag_no;
	}

	public String getPart_main_size() {
		return part_main_size;
	}

	public void setPart_main_size(String part_main_size) {
		this.part_main_size = part_main_size;
	}

	public String getPart_vice_size() {
		return part_vice_size;
	}

	public void setPart_vice_size(String part_vice_size) {
		this.part_vice_size = part_vice_size;
	}

	public String getPressure_class() {
		return pressure_class;
	}

	public void setPressure_class(String pressure_class) {
		this.pressure_class = pressure_class;
	}

	public String getMain_thickness_grade() {
		return main_thickness_grade;
	}

	public void setMain_thickness_grade(String main_thickness_grade) {
		this.main_thickness_grade = main_thickness_grade;
	}

	public String getVice_thickness_grade() {
		return vice_thickness_grade;
	}

	public void setVice_thickness_grade(String vice_thickness_grade) {
		this.vice_thickness_grade = vice_thickness_grade;
	}

	public String getMain_connection_type() {
		return main_connection_type;
	}

	public void setMain_connection_type(String main_connection_type) {
		this.main_connection_type = main_connection_type;
	}

	public String getVice_connection_type() {
		return vice_connection_type;
	}

	public void setVice_connection_type(String vice_connection_type) {
		this.vice_connection_type = vice_connection_type;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public String getSpecial_requirement() {
		return special_requirement;
	}

	public void setSpecial_requirement(String special_requirement) {
		this.special_requirement = special_requirement;
	}

	public String getWelding_no_one() {
		return welding_no_one;
	}

	public void setWelding_no_one(String welding_no_one) {
		this.welding_no_one = welding_no_one;
	}

	public String getWelding_no_two() {
		return welding_no_two;
	}

	public void setWelding_no_two(String welding_no_two) {
		this.welding_no_two = welding_no_two;
	}

	public String getWelding_no_three() {
		return welding_no_three;
	}

	public void setWelding_no_three(String welding_no_three) {
		this.welding_no_three = welding_no_three;
	}

	public String getWelding_no_four() {
		return welding_no_four;
	}

	public void setWelding_no_four(String welding_no_four) {
		this.welding_no_four = welding_no_four;
	}

	public Integer getQty() {
		return qty;
	}

	public void setQty(Integer qty) {
		this.qty = qty;
	}

	public Integer getNet_length() {
		return net_length;
	}

	public void setNet_length(Integer net_length) {
		this.net_length = net_length;
	}

	public Integer getAdditional_length() {
		return additional_length;
	}

	public void setAdditional_length(Integer additional_length) {
		this.additional_length = additional_length;
	}

	public Integer getFinal_length() {
		return final_length;
	}

	public void setFinal_length(Integer final_length) {
		this.final_length = final_length;
	}

	public String getMaterial_requisition_no() {
		return material_requisition_no;
	}

	public void setMaterial_requisition_no(String material_requisition_no) {
		this.material_requisition_no = material_requisition_no;
	}

	public String getNesting_draw_no() {
		return nesting_draw_no;
	}

	public void setNesting_draw_no(String nesting_draw_no) {
		this.nesting_draw_no = nesting_draw_no;
	}

	public String getNesting_draw_rev() {
		return nesting_draw_rev;
	}

	public void setNesting_draw_rev(String nesting_draw_rev) {
		this.nesting_draw_rev = nesting_draw_rev;
	}

	public String getBulk_material_no() {
		return bulk_material_no;
	}

	public void setBulk_material_no(String bulk_material_no) {
		this.bulk_material_no = bulk_material_no;
	}

	public String getRemnant_part_no() {
		return remnant_part_no;
	}

	public void setRemnant_part_no(String remnant_part_no) {
		this.remnant_part_no = remnant_part_no;
	}

	public Integer getRemnant_part_size() {
		return remnant_part_size;
	}

	public void setRemnant_part_size(Integer remnant_part_size) {
		this.remnant_part_size = remnant_part_size;
	}

	public String getBar_code_no() {
		return bar_code_no;
	}

	public void setBar_code_no(String bar_code_no) {
		this.bar_code_no = bar_code_no;
	}

	public BigDecimal getUnit_net_weight() {
		return unit_net_weight;
	}

	public void setUnit_net_weight(BigDecimal unit_net_weight) {
		this.unit_net_weight = unit_net_weight;
	}

	public BigDecimal getNet_weight() {
		return net_weight;
	}

	public void setNet_weight(BigDecimal net_weight) {
		this.net_weight = net_weight;
	}

	public String getSurface_treatment() {
		return surface_treatment;
	}

	public void setSurface_treatment(String surface_treatment) {
		this.surface_treatment = surface_treatment;
	}

	public String getCoating_system() {
		return coating_system;
	}

	public void setCoating_system(String coating_system) {
		this.coating_system = coating_system;
	}

	public String getUnit_net_area() {
		return unit_net_area;
	}

	public void setUnit_net_area(String unit_net_area) {
		this.unit_net_area = unit_net_area;
	}

	public BigDecimal getNet_area() {
		return net_area;
	}

	public void setNet_area(BigDecimal net_area) {
		this.net_area = net_area;
	}

	public String getInsultation_type() {
		return insultation_type;
	}

	public void setInsultation_type(String insultation_type) {
		this.insultation_type = insultation_type;
	}

	public Integer getInsultation_thk() {
		return insultation_thk;
	}

	public void setInsultation_thk(Integer insultation_thk) {
		this.insultation_thk = insultation_thk;
	}

	public BigDecimal getInsultation_surface_area() {
		return insultation_surface_area;
	}

	public void setInsultation_surface_area(BigDecimal insultation_surface_area) {
		this.insultation_surface_area = insultation_surface_area;
	}

	public BigDecimal getInsultation_equivalent_length() {
		return insultation_equivalent_length;
	}

	public void setInsultation_equivalent_length(
			BigDecimal insultation_equivalent_length) {
		this.insultation_equivalent_length = insultation_equivalent_length;
	}

	public String getSupplier() {
		return supplier;
	}

	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}

	public String getMto_no() {
		return mto_no;
	}

	public void setMto_no(String mto_no) {
		this.mto_no = mto_no;
	}

	public String getMto_row_no() {
		return mto_row_no;
	}

	public void setMto_row_no(String mto_row_no) {
		this.mto_row_no = mto_row_no;
	}

	public String getRecommend_surplus() {
		return recommend_surplus;
	}

	public void setRecommend_surplus(String recommend_surplus) {
		this.recommend_surplus = recommend_surplus;
	}

	public String getWasted_by_drawing_update() {
		return wasted_by_drawing_update;
	}

	public void setWasted_by_drawing_update(String wasted_by_drawing_update) {
		this.wasted_by_drawing_update = wasted_by_drawing_update;
	}

	public String getAdded_by_drawing_update() {
		return added_by_drawing_update;
	}

	public void setAdded_by_drawing_update(String added_by_drawing_update) {
		this.added_by_drawing_update = added_by_drawing_update;
	}

	public String getPay_item_no() {
		return pay_item_no;
	}

	public void setPay_item_no(String pay_item_no) {
		this.pay_item_no = pay_item_no;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Date getUpdate_date() {
		return update_date;
	}

	public void setUpdate_date(Date update_date) {
		this.update_date = update_date;
	}

	public Boolean getIs_history() {
		return is_history;
	}

	public void setIs_history(Boolean is_history) {
		this.is_history = is_history;
	}

	public Integer getChange_size() {
		return change_size;
	}

	public void setChange_size(Integer change_size) {
		this.change_size = change_size;
	}

	public String getReason_for_change() {
		return reason_for_change;
	}

	public void setReason_for_change(String reason_for_change) {
		this.reason_for_change = reason_for_change;
	}

	public Date getChange_cause_mark_date() {
		return change_cause_mark_date;
	}

	public void setChange_cause_mark_date(Date change_cause_mark_date) {
		this.change_cause_mark_date = change_cause_mark_date;
	}

	public String getChange_cause_mark_user() {
		return change_cause_mark_user;
	}

	public void setChange_cause_mark_user(String change_cause_mark_user) {
		this.change_cause_mark_user = change_cause_mark_user;
	}

	public String getColumn1() {
		return column1;
	}

	public void setColumn1(String column1) {
		this.column1 = column1;
	}

	public String getColumn2() {
		return column2;
	}

	public void setColumn2(String column2) {
		this.column2 = column2;
	}

	public String getColumn3() {
		return column3;
	}

	public void setColumn3(String column3) {
		this.column3 = column3;
	}

	public String getColumn4() {
		return column4;
	}

	public void setColumn4(String column4) {
		this.column4 = column4;
	}

	public String getColumn5() {
		return column5;
	}

	public void setColumn5(String column5) {
		this.column5 = column5;
	}

	public String getColumn6() {
		return column6;
	}

	public void setColumn6(String column6) {
		this.column6 = column6;
	}

	public String getColumn7() {
		return column7;
	}

	public void setColumn7(String column7) {
		this.column7 = column7;
	}

	public String getColumn8() {
		return column8;
	}

	public void setColumn8(String column8) {
		this.column8 = column8;
	}

	public String getColumn9() {
		return column9;
	}

	public void setColumn9(String column9) {
		this.column9 = column9;
	}

	public String getColumn10() {
		return column10;
	}

	public void setColumn10(String column10) {
		this.column10 = column10;
	}

	public String getColumn11() {
		return column11;
	}

	public void setColumn11(String column11) {
		this.column11 = column11;
	}

	public String getColumn12() {
		return column12;
	}

	public void setColumn12(String column12) {
		this.column12 = column12;
	}

	public String getColumn13() {
		return column13;
	}

	public void setColumn13(String column13) {
		this.column13 = column13;
	}

	public String getColumn14() {
		return column14;
	}

	public void setColumn14(String column14) {
		this.column14 = column14;
	}

	public String getColumn15() {
		return column15;
	}

	public void setColumn15(String column15) {
		this.column15 = column15;
	}

	public String getColumn16() {
		return column16;
	}

	public void setColumn16(String column16) {
		this.column16 = column16;
	}

	public String getColumn17() {
		return column17;
	}

	public void setColumn17(String column17) {
		this.column17 = column17;
	}

	public String getColumn18() {
		return column18;
	}

	public void setColumn18(String column18) {
		this.column18 = column18;
	}

	public String getColumn19() {
		return column19;
	}

	public void setColumn19(String column19) {
		this.column19 = column19;
	}

	public String getColumn20() {
		return column20;
	}

	public void setColumn20(String column20) {
		this.column20 = column20;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Integer getQuantity_of_inwarehouse() {
		return quantity_of_inwarehouse;
	}

	public void setQuantity_of_inwarehouse(Integer quantity_of_inwarehouse) {
		this.quantity_of_inwarehouse = quantity_of_inwarehouse;
	}

	public Integer getRecipients_of_warehouse() {
		return recipients_of_warehouse;
	}

	public void setRecipients_of_warehouse(Integer recipients_of_warehouse) {
		this.recipients_of_warehouse = recipients_of_warehouse;
	}

	public Integer getWaste_materials_outbound() {
		return waste_materials_outbound;
	}

	public void setWaste_materials_outbound(Integer waste_materials_outbound) {
		this.waste_materials_outbound = waste_materials_outbound;
	}

	public Integer getIn_allocating_outbound() {
		return in_allocating_outbound;
	}

	public void setIn_allocating_outbound(Integer in_allocating_outbound) {
		this.in_allocating_outbound = in_allocating_outbound;
	}

	public Integer getConstruction_of_withdrawing() {
		return construction_of_withdrawing;
	}

	public void setConstruction_of_withdrawing(Integer construction_of_withdrawing) {
		this.construction_of_withdrawing = construction_of_withdrawing;
	}

	public Integer getStock() {
		return stock;
	}

	public void setStock(Integer stock) {
		this.stock = stock;
	}

	public Integer getQuantity_in_dm_mto() {
		return quantity_in_dm_mto;
	}

	public void setQuantity_in_dm_mto(Integer quantity_in_dm_mto) {
		this.quantity_in_dm_mto = quantity_in_dm_mto;
	}

	public Integer getQuantity_in_pd_mto() {
		return quantity_in_pd_mto;
	}

	public void setQuantity_in_pd_mto(Integer quantity_in_pd_mto) {
		this.quantity_in_pd_mto = quantity_in_pd_mto;
	}

	public Integer getGenerate_request_number() {
		return generate_request_number;
	}

	public void setGenerate_request_number(Integer generate_request_number) {
		this.generate_request_number = generate_request_number;
	}

	public String getVendor() {
		return vendor;
	}

	public void setVendor(String vendor) {
		this.vendor = vendor;
	}

	public String getSite() {
		return site;
	}

	public void setSite(String site) {
		this.site = site;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public String getTellphone_no() {
		return tellphone_no;
	}

	public void setTellphone_no(String tellphone_no) {
		this.tellphone_no = tellphone_no;
	}
	
}
