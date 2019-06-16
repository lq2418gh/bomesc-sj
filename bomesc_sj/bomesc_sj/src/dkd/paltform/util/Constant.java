package dkd.paltform.util;

public class Constant {
	//读取数据跟踪表时的根地址
	public static final String file_path = "D:\\BomescSJ";
	public static final String secretKey = "111111112222222233333333";
	//结构专业数据跟踪表中的列对应的数据库字段名
	public static final String[] st_columns = {"item","project_name","job_no","module_name","shop_draw_no",
		"shop_draw_rev","contractor_draw_no","contractor_draw_rev","part_no","part_name","steel_type","structure_type",
		"area_of_part","level_no","work_package_no","part_profile_name","part_profile","grade","qty","net_single_length",
		"net_single_width","unit_weight","net_single_weight","net_total_weight","gross_single_weight","gross_total_weight",
		"net_area","surface_treatment","coating_system","coating_area","fireproof_type","fireproof_area","fireproof_length",
		"fireproof_thickness","wasted_by_drawing_update","added_by_drawing_update","nesting_draw_no","nesting_draw_rev",
		"bulk_material_no","remnant_part_no","material_code","supplier","mto_no","mto_row_no","ident_code","pay_item_no","bar_code_no",
		"vendor","site","website","tellphone_no"};
	//管线专业数据跟踪表中的列对应的数据库字段名
	public static final String[] pi_columns = {"item","project_name","job_no","module_no","work_package_no","test_package_no","iso_drawing_no",
		"page_no","total_page","iso_drawing_rev","shop_draw_no","shop_draw_rev","system_code","pipe_class","line_no","spool_no",
		"part_name","part_type","part_no","client_part_no","ident_code","material_code","tag_no","part_main_size","part_vice_size","pressure_class",
		"main_thickness_grade","vice_thickness_grade","main_connection_type","vice_connection_type","grade","special_requirement","welding_no_one",
		"welding_no_two","welding_no_three","welding_no_four","qty","net_length","additional_length","final_length","material_requisition_no",
		"nesting_draw_no","nesting_draw_rev","bulk_material_no","remnant_part_no","remnant_part_size","bar_code_no","unit_net_weight",
		"net_weight","surface_treatment","coating_system","unit_net_area","net_area","insultation_type","insultation_thk","insultation_surface_area",
		"insultation_equivalent_length","supplier","mto_no","mto_row_no","recommend_surplus","wasted_by_drawing_update","added_by_drawing_update",
		"pay_item_no","remark","vendor","site","website","tellphone_no"};
	//专业缩写
	public static final String[] major_abbs = {"ST","AR","EI","HAVC","PI"};
	//数字和字母
	public static final String[] letter={"0","1","2","3","4","5","6","7","8","9","A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};

}
