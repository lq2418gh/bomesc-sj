package dkd.business.designDataChange.dao;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import dkd.business.designDataManager.dao.DataTrackColumnDao;
import dkd.business.designDataManager.domain.PiDataTrack;
import dkd.paltform.authority.domain.User;
import dkd.paltform.base.QueryResult;
import dkd.paltform.base.dao.BaseDao;
@Repository
public class PiDataTrackChangeDao  extends BaseDao<PiDataTrack>{
	@Autowired
	private DataTrackColumnDao dataTrackColumnDao;
	/**
	 * 描述：
	 */
	public QueryResult<Map<String, Object>> getScrollDataByUser(Map<String, String> params, User currentUser) {
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("major","pi");
		map.put("user", currentUser.getId());
		List<Map<String, Object>> dataTrackColumns = dataTrackColumnDao.findByUserAndMajor(map);
		//管线数据跟踪表全部字段
		String data="pit.id,pit.update_date,pit.project_name,pit.job_no,pit.module_no,pit.work_package_no,pit.test_package_no,pit.iso_drawing_no,pit.page_no,"
				+ "pit.total_page,pit.iso_drawing_rev,pit.shop_draw_no,pit.shop_draw_rev,pit.system_code,pit.pipe_class,pit.line_no,pit.spool_no,"
				+ "pit.part_name,pit.part_type,pit.part_no,pit.client_part_no,pit.ident_code,pit.material_code,pit.tag_no,"
				+ "pit.part_main_size,pit.part_vice_size,pit.pressure_class,pit.main_thickness_grade,pit.vice_thickness_grade,pit.main_connection_type,pit.vice_connection_type,"
				+ "pit.grade,pit.special_requirement,pit.welding_no_one,pit.welding_no_two,pit.welding_no_three,pit.welding_no_four,pit.qty,"
				+ "pit.net_length,pit.additional_length,pit.final_length,pit.material_requisition_no,pit.nesting_draw_no,pit.nesting_draw_rev,"
				+ "pit.bulk_material_no,pit.remnant_part_no,pit.remnant_part_size,pit.bar_code_no,pit.unit_net_weight,pit.net_weight,pit.surface_treatment,pit.coating_system,pit.unit_net_area,"
				+ "pit.net_area,pit.insultation_type,pit.insultation_thk,pit.insultation_surface_area,pit.insultation_equivalent_length,pit.supplier,pit.mto_no,pit.mto_row_no,pit.recommend_surplus,"
				+ "pit.wasted_by_drawing_update,pit.added_by_drawing_update,pit.pay_item_no,pit.remark,pit.quantity_in_dm_mto,pit.quantity_in_pd_mto,pit.generate_request_number,"
				+ "pit.vendor,pit.site,pit.website,pit.tellphone_no,pit.column1,pit.column2,pit.column3,"
				+ "pit.column4,pit.column5,pit.column6,pit.column7,pit.column8,pit.column9,pit.column10,pit.column11,pit.column12,pit.column13,pit.column14,pit.column15,pit.column16,"
				+ "pit.column17,pit.column18,pit.column19,pit.column20,pit.quantity_of_inwarehouse,pit.recipients_of_warehouse,pit.waste_materials_outbound,pit.in_allocating_outbound,pit.construction_of_withdrawing,pit.stock";
		//用户自定义查询字段
		if(!dataTrackColumns.isEmpty()){
			data = "pit.id,pit.update_date,pit.state,"+String.valueOf(dataTrackColumns.get(0).get("show_columns"));
		}
		String sql = "select pit.reason_for_change,pit.change_cause_mark_date,pit.change_cause_mark_user,"+data +" from b_pi_track_table pit where pit.state in ('修改','删除')";
		sql+=" order by pit.shop_draw_no desc,pit.id desc ";
		params.remove("professional");
		return getScrollData(sql,params);
	}
	/**
	 * 描述：
	 */
	public List<Map<String, Object>> findByProfessional(String update_date, String project_name) {
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("is_history", 'Y');
		if(StringUtils.isEmpty(update_date)){
			Calendar cal=Calendar.getInstance();
			cal.add(Calendar.DATE,-1);
			map.put("update_date",new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime()));
		}else{
			map.put("update_date", update_date);
		}
		String sql = "select '管线专业-PI' as professional,shop_draw_no,project_name,module_no,reason_for_change,update_date,change_cause_mark_user"+
				 " from b_pi_track_table where is_history=:is_history  and update_date = :update_date";
		if(StringUtils.isNotEmpty(project_name)){
			map.put("project_name", project_name);
			sql+=" and project_name=:project_name ";
		}
		sql+=" group by shop_draw_no,project_name,module_no,reason_for_change,update_date,change_cause_mark_user";
		return findBySql(sql, map);
	}
	/**
	 * 描述：
	 */
	public List<Map<String, Object>> findChangeDetails(String project_name, String shop_draw_no, String module_no,String update_date) {
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("is_history", "Y");
		//map.put("state", "修改");
		map.put("project_name", project_name);
		map.put("shop_draw_no", shop_draw_no);
		map.put("module_no", module_no);
		map.put("update_date", update_date);
		String sql="select '管线专业-PI' as professional,state,shop_draw_no,project_name,module_no,part_no,part_name,change_cause_mark_user,reason_for_change,change_cause_mark_date,update_date "
				+ "from b_pi_track_table  where is_history=:is_history  and project_name=:project_name and shop_draw_no=:shop_draw_no and module_no=:module_no and update_date=:update_date";
		return findBySql(sql, map);
	}
	/**
	 * 描述：pi专业  变更记录按图纸汇总的变更原因  保存方法  数据库更新  变更原因字段
	 */
	public int batchUpdateReason(PiDataTrack piDataTrack, User currentUser) {
		String jpql = "update PiDataTrack set reason_for_change=:reason_for_change,change_cause_mark_user=:change_cause_mark_user,change_cause_mark_date=:change_cause_mark_date where shop_draw_no=:shop_draw_no and "
				+ "project_name=:project_name and module_no=:module_no and update_date=:update_date";
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("reason_for_change", piDataTrack.getReason_for_change());
		map.put("change_cause_mark_user", currentUser.getName());
		map.put("change_cause_mark_date", new Date());
		map.put("shop_draw_no", piDataTrack.getShop_draw_no());
		map.put("project_name", piDataTrack.getProject_name());
		map.put("module_no", piDataTrack.getModule_no());
		map.put("update_date", piDataTrack.getUpdate_date());
		return super.batchUpdate(jpql, map);
	}

}
