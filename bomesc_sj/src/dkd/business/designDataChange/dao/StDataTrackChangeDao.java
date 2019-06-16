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
import dkd.business.designDataManager.domain.StDataTrack;
import dkd.paltform.authority.domain.User;
import dkd.paltform.base.QueryResult;
import dkd.paltform.base.dao.BaseDao;

@Repository
public class StDataTrackChangeDao extends BaseDao<StDataTrack>{
	
	@Autowired
	private DataTrackColumnDao dataTrackColumnDao;
	
	public QueryResult<Map<String, Object>> getScrollDataByUser(Map<String,String> params,User user) {
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("major","st");
		map.put("user", user.getId());
		List<Map<String, Object>> dataTrackColumns = dataTrackColumnDao.findByUserAndMajor(map);
		//结构数据跟踪表全部字段
		String data="stt.id,stt.update_date,stt.state,stt.project_name,stt.job_no,stt.module_name,stt.shop_draw_no,stt.shop_draw_rev,stt.contractor_draw_no,stt.contractor_draw_rev,"
				+ "stt.part_no,part_name,stt.steel_type,stt.structure_type,stt.area_of_part,stt.level_no,stt.work_package_no,stt.part_profile_name,"
				+ "stt.part_profile,stt.grade,stt.qty,stt.quantity_in_dm_mto,stt.quantity_in_pd_mto,stt.net_single_length,stt.net_single_width,"
				+ "stt.unit_weight,stt.net_single_weight,stt.net_total_weight,stt.gross_single_weight,stt.gross_total_weight,stt.net_area,stt.surface_treatment,"
				+ "stt.coating_system,stt.coating_area,stt.fireproof_type,stt.fireproof_area,stt.fireproof_length,stt.fireproof_thickness,stt.wasted_by_drawing_update,"
				+ "stt.added_by_drawing_update,stt.nesting_draw_no,stt.nesting_draw_rev,stt.bulk_material_no,stt.remnant_part_no,stt.material_code,"
				+ "stt.supplier,stt.mto_no,stt.mto_row_no,stt.pay_item_no,stt.bar_code_no,stt.vendor,stt.site,stt.website,stt.tellphone_no,stt.column1,stt.column2,stt.column3,"
				+ "stt.column4,stt.column5,stt.column6,stt.column7,stt.column8,stt.column9,stt.column10,stt.column11,stt.column12,stt.column13,stt.column14,stt.column15,stt.column16,"
				+ "stt.column17,stt.column18,stt.column19,stt.column20,stt.quantity_of_inwarehouse,stt.recipients_of_warehouse,stt.waste_materials_outbound,stt.in_allocating_outbound,"
				+ "stt.construction_of_withdrawing,stt.stock,stt.generate_request_number";
		//用户自定义查询字段
		if(!dataTrackColumns.isEmpty()){
			data = "stt.id,stt.update_date,stt.state,"+String.valueOf(dataTrackColumns.get(0).get("show_columns"));
		}
		String sql = "select stt.reason_for_change,stt.change_cause_mark_date,stt.change_cause_mark_user,stt.mark_user,stt.mark_date,state_change_date,"+data +" from b_st_track_table stt where 1=1 ";
		if("remind".equals(params.get("type"))){
			params.remove("type");
			params.remove("is_history");
			sql += " and (stt.state ='作废' or stt.state ='新增' ) and state_change_date is not null ";
		}else{
			sql += " and stt.state in ('修改','删除','作废')";
		}
		sql+=" order by stt.shop_draw_no desc,stt.id desc ";
		params.remove("professional");
		return getScrollData(sql,params);
	}
	/**
	 * @date 2017年12月26日
	 * @author gaoxp
	 * @param update_date
	 * @param project_name
	 * @return
	 * 描述：按照图纸进行分组后查询出的分组
	 */
	public List<Map<String, Object>> findByProfessional(String update_date,String project_name,String module) {
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("is_history", 'Y');
		//map.put("state", "修改");
		if(StringUtils.isEmpty(update_date)){
			Calendar cal=Calendar.getInstance();
			cal.add(Calendar.DATE,-1);
			map.put("update_date",new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime()));
		}else{
			map.put("update_date", update_date);
		}
		//查询字段
		String data="";
		if("remind".equals(module)){
			data="shop_draw_no,project_name,module_name,level_no,reason_for_change,update_date,change_cause_mark_user,mto_no,mto_row_no,state,mark_date,mark_user,state_change_date ";
		}else{
			data="shop_draw_no,project_name,module_name,level_no,reason_for_change,update_date,change_cause_mark_user ";
		}
		String sql = "select '结构专业-ST' as professional,"+data+
				 " from b_st_track_table where 1=1 and update_date = :update_date ";
		if(StringUtils.isNotEmpty(project_name)){
			map.put("project_name", project_name);
			sql+=" and project_name=:project_name ";
		}
		if("remind".equals(module)){
			map.remove("is_history");
			//此sql拼接的条件为查询的因图纸升版增加、作废的数据，因客户确认只查询作废的数据，故注释，如需查询升版增加和作废的数据，还原即可
			//sql+=" and (state = '作废' or state ='新增') and mark_date is null and state_change_date is not null ";
			//此sql拼接的条件为只查询升版作废的数据
			sql+=" and state = '作废' and mark_date is null and state_change_date is not null ";
		}else{
			sql+=" and is_history=:is_history ";
		}
		sql+=" group by "+data+" ";
		return findBySql(sql, map);
	}
	/**
	 * @date 2017年12月26日
	 * @author gaoxp
	 * @param project_name
	 * @param shop_draw_no
	 * @param module_name
	 * @param level_no
	 * @param update_date
	 * @return
	 * 描述：按照图纸分类查询出的变更记录，具体到某一天
	 */
	public List<Map<String, Object>> findChangeDetails(String project_name,String shop_draw_no,String module_name,String level_no,String update_date,
			String mtoNo,String mtoRowNo,String module){
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("is_history", "Y");
		//map.put("state", "修改");
		map.put("project_name", project_name);
		map.put("shop_draw_no", shop_draw_no);
		map.put("module_name", module_name);
		map.put("level_no", level_no);
		map.put("update_date", update_date);
		String data;
		if("remind".equals(module)){
			data="state,shop_draw_no,project_name,module_name,level_no,part_no,part_name,change_cause_mark_user,reason_for_change,change_cause_mark_date,update_date,mto_no,mto_row_no,mark_user,mark_date,state_change_date";
		}else{
			data="state,shop_draw_no,project_name,module_name,level_no,part_no,part_name,change_cause_mark_user,reason_for_change,change_cause_mark_date,update_date";
		}
		String sql="select '结构专业-ST' as professional,"+data
				+ " from b_st_track_table  where 1=1 and project_name=:project_name and shop_draw_no=:shop_draw_no and module_name=:module_name and level_no=:level_no and update_date=:update_date";
		if(!StringUtils.isEmpty(mtoNo)){
			map.put("mto_no", mtoNo);
			sql+=" and mto_no=:mto_no ";
		}
		if(!StringUtils.isEmpty(mtoNo)){
			map.put("mto_row_no", mtoRowNo);
			sql+=" and mto_row_no = :mto_row_no and mark_date is null ";
		}
		if("remind".equals(module)){
			map.remove("is_history");
			sql +=" and state_change_date is not null ";
			//此处增加判断查询的为图纸升版作废的数据，如需查询升版作废和增加的数据，将此行代码注释即可
			sql+=" and state ='作废' ";
		}else{
			sql+= " and is_history=:is_history ";
		}
		return findBySql(sql, map);
	}
	/**
	 * @date 2017年12月26日
	 * @author gaoxp
	 * @param stDataTrack
	 * @param currentUser
	 * @return
	 * 描述：批量更新变更原因
	 */
	public int batchUpdateReason(StDataTrack stDataTrack,User currentUser){
		String jpql = "update StDataTrack set reason_for_change=:reason_for_change,change_cause_mark_user=:change_cause_mark_user,change_cause_mark_date=:change_cause_mark_date where shop_draw_no=:shop_draw_no and "
				+ "project_name=:project_name and module_name=:module_name and level_no=:level_no and update_date=:update_date";
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("reason_for_change", stDataTrack.getReason_for_change());
		map.put("change_cause_mark_user", currentUser.getName());
		map.put("change_cause_mark_date", new Date());
		map.put("shop_draw_no", stDataTrack.getShop_draw_no());
		map.put("project_name", stDataTrack.getProject_name());
		map.put("module_name", stDataTrack.getModule_name());
		map.put("level_no", stDataTrack.getLevel_no());
		map.put("update_date", stDataTrack.getUpdate_date());
		return super.batchUpdate(jpql, map);
	}
	public int saveState(StDataTrack stDataTrack,User currentUser){
		String setData = "state=:state,entity_modifyuser=:entity_modifyuser,entity_modifydate=:entity_modifydate";
		Map<String,Object> map = new HashMap<String, Object>();
		if("删除".equals(stDataTrack.getState())){
			setData +=",state_change_date=null";
		}
		//此处注释的代码为升版增加更改状态的代码逻辑，因目前客户确认不可修改，注释
		/*if("新增".equals(stDataTrack.getState())){
			setData +=",state_change_date=null";
		}
		if(!"作废".equals(stDataTrack.getState())&&!"删除".equals(stDataTrack.getState())){
			stDataTrack.setState("新增");
		}*/
		String jpql = "update StDataTrack set "+setData+" where shop_draw_no=:shop_draw_no and "
				+ "project_name=:project_name and module_name=:module_name and level_no=:level_no and "
				+ "update_date=:update_date and mto_no=:mto_no and mto_row_no = :mto_row_no and mark_date is null";
		map.put("state", stDataTrack.getState());
		map.put("entity_modifyuser", currentUser.getName());
		map.put("entity_modifydate", new Date());
		map.put("shop_draw_no", stDataTrack.getShop_draw_no());
		map.put("project_name", stDataTrack.getProject_name());
		map.put("module_name", stDataTrack.getModule_name());
		map.put("level_no", stDataTrack.getLevel_no());
		map.put("update_date", stDataTrack.getUpdate_date());
		map.put("mto_no", stDataTrack.getMto_no());
		map.put("mto_row_no", stDataTrack.getMto_row_no());
		return super.batchUpdate(jpql, map);
	}
	public int stateConfirm(String id,User user) {
		String jpql ="update StDataTrack set mark_date=:mark_date,mark_user=:mark_user where id=:id";
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("mark_date", new Date());
		map.put("mark_user", user.getName());
		map.put("id", id);
		return super.batchUpdate(jpql, map);
	}
	public List<Map<String, Object>> findTotalTask() {
		//分别查询各专业的待办任务（升版增加、作废）后续用union拼接查询其它专业的数量(建议将专业设置为ST,EI,PI,HVAC,AR与前台页面的id对应)
		String sql = "select 'ST' as professional,count(1) num from b_st_track_table stt where (stt.state ='作废' or stt.state ='新增' ) and state_change_date is not null and mark_date is null ";
		return super.findBySql(sql);
	}
}
