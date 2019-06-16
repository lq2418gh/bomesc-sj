package dkd.business.designDataManager.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import dkd.business.designDataManager.domain.PiDataTrack;
import dkd.paltform.authority.domain.User;
import dkd.paltform.base.QueryResult;
import dkd.paltform.base.dao.BaseDao;

@Repository
public class PiDataTrackTableDao extends BaseDao<PiDataTrack>{
	
	@Autowired
	private DataTrackColumnDao dataTrackColumnDao;
	
	public QueryResult<Map<String, Object>> getScrollDataByUser(Map<String,String> params,User user) {
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("major","pi");
		map.put("user", user.getId());
		List<Map<String, Object>> dataTrackColumns = dataTrackColumnDao.findByUserAndMajor(map);
		//结构数据跟踪表全部字段
		String data="pit.id,pit.change_size,pit.update_date,pit.project_name,pit.job_no,pit.module_no,pit.work_package_no,pit.test_package_no,pit.iso_drawing_no,pit.page_no,"
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
			//查询
			if(null==params.get("export")||"".equals(params.get("export"))){
				data = "pit.id,pit.update_date,pit.change_size,case when supplier='Free Issue' then pit.part_no "+
						"when supplier='BOMESC' then pit.iso_drawing_no+pit.material_code+pit.spool_no+"+
						"pit.welding_no_one+pit.welding_no_two+pit.welding_no_three+pit.welding_no_four "+
						"end part_no_hide,"+String.valueOf(dataTrackColumns.get(0).get("show_columns"));
			}else{//导出
				params.remove("export");
				data = "pit.update_date,pit.change_size,"+String.valueOf(dataTrackColumns.get(0).get("show_columns"));
			}
		}
		String sql = "select "+data+" from b_pi_track_table pit where pit.wasted_by_drawing_update ='N' ";
		//判断是否查询的为历史数据
		if(null==params.get("is_history")||""==params.get("is_history")){
			String[] filter;
			String historyDate="";
			for (Map.Entry<String, String> entry : params.entrySet()) {
				filter = entry.getKey().split(" ");
				if(filter[0].indexOf("update_date")==-1){
					continue;
				}else{
					if("le".equals(filter[1])||"<=".equals(filter[1])){
						historyDate=entry.getValue();
					}
				}
			}
			//管线专业按照零件编号(实仓)、ISO图纸编号+识别编号+管段号+焊口编号(虚仓)作为唯一标识列来区分新增与更新
			String childSql="select case when supplier='bomesc' then iso_drawing_no+material_code+spool_no+"+
						"welding_no_one+welding_no_two+welding_no_three+welding_no_four "+
						"when supplier='free issue' then part_no end part_no,max(update_date) update_date from b_pi_track_table where update_date <='"+historyDate +"' and wasted_by_drawing_update ='N' group by "+
						"case when supplier='bomesc' then iso_drawing_no+material_code+spool_no+"+
						"welding_no_one+welding_no_two+welding_no_three+welding_no_four "+
						"when supplier='free issue' then part_no end ";
			sql = "select "+data +" from ("+childSql+") a left join b_pi_track_table pit on a.part_no=(case when pit.supplier='bomesc' then pit.iso_drawing_no+pit.material_code+pit.spool_no+"+
					"pit.welding_no_one+pit.welding_no_two+pit.welding_no_three+pit.welding_no_four when pit.supplier='free issue' then pit.part_no end ) and a.update_date=pit.update_date where pit.wasted_by_drawing_update ='N' " ;
		}
		//判断是否查询的为单条数据的历史记录
		if(!"0".equals(params.get("change_size"))&&null!=params.get("change_size")&&!"".equals(params.get("change_size"))){
			params.remove("change_size");
			params.remove("is_history");
			String update_date = params.get("update_date"); 
			params.remove("update_date");
			String part_no = params.get("part_no");
			params.remove("part_no");
			sql = "select pit.reason_for_change,pit.change_cause_mark_date,pit.change_cause_mark_user,"+data +" from b_pi_track_table pit where update_date <= '"+update_date+"' and "+
					"case when supplier='free issue' then pit.part_no "+
					"when supplier='bomesc' then (pit.iso_drawing_no+pit.material_code+pit.spool_no"+
					"+pit.welding_no_one+pit.welding_no_two+pit.welding_no_three+"+
					"pit.welding_no_four) end = '" +part_no+"'";
		}
		//判断是否查询的为修改的记录
		if("Y".equals(params.get("is_history"))){
			sql = "select pit.reason_for_change,pit.change_cause_mark_date,pit.change_cause_mark_user,"+data +" from b_pi_track_table pit where state='修改' ";
		}
		sql+=" order by pit.shop_draw_no desc,pit.id desc ";
		return getScrollData(sql,params,null);
	}
	public List<Map<String, Object>> getColumns(String professional,User user) {
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("major","pi");
		map.put("user", user.getId());
		List<Map<String, Object>> dataTrackColumns = dataTrackColumnDao.findByUserAndMajor(map);
		return dataTrackColumns;
	}
	/**
	 * @date 2018年1月2日
	 * @author gaoxp
	 * @param projectName
	 * @param moduleName
	 * @return 根据项目名称和模块编号获取管线专业数据跟踪表数据
	 * 描述：
	 */
	public List<Map<String, Object>> getTrackDataByPM(String projectName,String moduleNo){
		String sql = "select * from b_pi_track_table where project_name='"+projectName+"' and module_no='"
				+moduleNo +"' and is_history='N'";
		return findBySql(sql);
	}
	/**
	 * @date 20180103
	 * @author gaoxp
	 * @param date
	 * @return
	 * 描述：查询出设计料单数量有值但是数量小于qty的数据；以及设计料单数量为空，但是更新日期不是当前日期的；这两类作为缺料提醒的记录
	 */
	public List<Map<String, Object>> getTrackDataLackMaterial(String date){
		String sql="select qty,quantity_in_dm_mto,mto_no,project_name,material_code,shop_draw_no,job_no from b_pi_track_table "
				 + "where is_history='N' and ((quantity_in_dm_mto is not null and quantity_in_dm_mto<qty and update_date<'"+date+"')  or "
				 + "(quantity_in_dm_mto is null and update_date<'"+date+"')) order by mto_no,shop_draw_no";
		return findBySql(sql);
	}
}
