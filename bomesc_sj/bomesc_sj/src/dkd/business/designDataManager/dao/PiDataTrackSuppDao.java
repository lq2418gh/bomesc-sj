package dkd.business.designDataManager.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import dkd.business.designDataManager.domain.PiDataTrackSupp;
import dkd.paltform.authority.domain.User;
import dkd.paltform.base.QueryResult;
import dkd.paltform.base.dao.BaseDao;

@Repository
public class PiDataTrackSuppDao extends BaseDao<PiDataTrackSupp>{
	@Autowired
	private DataTrackColumnDao dataTrackColumnDao;
	
	public QueryResult<Map<String, Object>> getScrollDataByUser(Map<String, String> param, User currentUser) {
 		Map<String,Object> map = new HashMap<String, Object>();
		map.put("major","pi");
		map.put("user", currentUser.getId());
		List<Map<String,Object>> findByUM = this.findByUM(map);
		//结构数据跟踪表全部字段
		String data="pit.id,pit.project_name,pit.job_no,pit.module_no,pit.work_package_no,pit.test_package_no,pit.iso_drawing_no,pit.page_no,"
				+ "pit.total_page,pit.iso_drawing_rev,pit.shop_draw_no,pit.shop_draw_rev,pit.system_code,pit.pipe_class,pit.line_no,pit.spool_no,"
				+ "pit.part_no,pit.part_name,pit.part_type,pit.client_part_no,pit.ident_code,pit.material_code,pit.tag_no"
				+ "pit.part_main_size,pit.part_vice_size,pit.pressure_class,pit.main_thickness_grade,pit.vice_thickness_grade,pit.main_connection_type,pit.vice_connection_type,"
				+ "pit.grade,pit.special_requirement,pit.welding_no_one,pit.welding_no_two,pit.welding_no_three,pit.welding_no_four,pit.qty,pit.quantity_in_dm_mto,pit.quantity_in_pd_mto,"
				+ "pit.net_length,pit.additional_length,pit.final_length,pit.material_requisition_no,pit.nesting_drawing_no,pit.nesting_drawing_rev,"
				+ "pit.bulk_material_no,pit.remnant_part_no,pit.remnant_part_size,pit.bar_code_no,pit.unit_net_weight,pit.net_weight,pit.surface_treatment,"
				+ "pit.coating_system,pit.unit_net_area,pit.net_area,pit.insultation_type,pit.insultation_thk,pit.insultation_surface_area,pit.insultation_equivalent_length,"
				+ "pit.supplier,pit.mto_no,pit.mto_row_no,pit.recommend_surplus,pit.wasted_by_drawing_update,pit.added_by_drawing_update,pit.pay_item_no,pit.remark,"
				+ "pit.vendor,pit.site,pit.website,pit.tellphone_no,pit.column1,pit.column2,pit.column3,pit.column4,pit.column5,pit.column6,pit.column7,pit.column8,pit.column9,pit.column10,"
				+ "pit.column11,pit.column12,pit.column13,pit.column14,pit.column15,pit.column16,pit.column17,pit.column18,pit.column19,pit.column20,pit.generate_request_number,"
				+ "pit.quantity_of_inwarehouse,pit.recipients_of_warehouse,pit.waste_materials_outbound,pit.in_allocating_outbound,"
				+ "pit.construction_of_withdrawing,pit.stock";
		//用户自定义查询字段
		if(!findByUM.isEmpty()){
			data = "pit.id,"+String.valueOf(findByUM.get(0).get("show_columns"));
		}
		String sql = "select "+data+" from b_pi_data_track_supp pit";
		
		return getScrollData(sql, param);
	}

	public List<Map<String, Object>> findIfNULL(String partNos) {
		String sql = "select * from b_pi_data_track_supp where supplier='Free Issue' and part_no in ("+partNos+")";
		return findBySql(sql);
	}

	public List<Map<String, Object>> findSevenIfNULL(String seven) {
		String sql = "select * from b_pi_data_track_supp where supplier='BOMESC' and (iso_drawing_no+material_code+spool_no+welding_no_one+welding_no_two+welding_no_three+welding_no_four) in ("+seven+")";
		return findBySql(sql);
	}
	
	/**
	 * @date 2017年12月11日
	 * @param map
	 * @return
	 * 描述：查询出来的show_coulmuns当前用户要展示的列
	 */
	public List<Map<String, Object>> findByUM(Map<String,Object> map){
		return dataTrackColumnDao.findByUserAndMajor(map);
	}
}
