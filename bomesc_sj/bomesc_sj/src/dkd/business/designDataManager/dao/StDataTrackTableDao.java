package dkd.business.designDataManager.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import dkd.business.designDataManager.domain.StDataTrack;
import dkd.paltform.authority.domain.User;
import dkd.paltform.base.QueryResult;
import dkd.paltform.base.dao.BaseDao;

@Repository
public class StDataTrackTableDao extends BaseDao<StDataTrack>{
	
	@Autowired
	private DataTrackColumnDao dataTrackColumnDao;
	
	public QueryResult<Map<String, Object>> getScrollDataByUser(Map<String,String> params,User user) {
		String data = this.getShowData(params,user);
		String sql = "select "+data+" from b_st_track_table stt where stt.wasted_by_drawing_update ='N' ";
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
			//结构专业按照零件编号作为唯一标识列来区分新增与更新
			String childSql="select part_no,max(update_date) update_date from b_st_track_table where update_date <='"+historyDate +"' and wasted_by_drawing_update ='N' group by part_no ";
			sql = "select "+data +" from ("+childSql+") a left join b_st_track_table stt on a.part_no=stt.part_no and a.update_date=stt.update_date where stt.wasted_by_drawing_update ='N' " ;
		}
		//判断是否查询的为单条数据的历史记录
		if(!"0".equals(params.get("change_size"))&&null!=params.get("change_size")&&!"".equals(params.get("change_size"))){
			params.remove("change_size");
			params.remove("is_history");
			String update_date = params.get("update_date"); 
			params.remove("update_date");
			sql = "select stt.reason_for_change,stt.change_cause_mark_date,stt.change_cause_mark_user,"+data +" from b_st_track_table stt where update_date <= '"+update_date+"' ";
		}
		//判断是否查询的为修改的记录
		if("Y".equals(params.get("is_history"))){
			sql = "select stt.reason_for_change,stt.change_cause_mark_date,stt.change_cause_mark_user,"+data +" from b_st_track_table stt where state='修改' ";
		}
		sql+=" order by stt.shop_draw_no desc,stt.id desc ";
		return getScrollData(sql,params,null);
	}
	//获取显示的字段列
		private String getShowData(Map<String,String> params,User user){
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("major","st");
			map.put("user", user.getId());
			List<Map<String, Object>> dataTrackColumns = dataTrackColumnDao.findByUserAndMajor(map);
			//结构数据跟踪表全部字段
			String data="stt.id,stt.change_size,stt.update_date,stt.project_name,stt.job_no,stt.module_name,stt.shop_draw_no,stt.shop_draw_rev,stt.contractor_draw_no,stt.contractor_draw_rev,"
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
				//查询
				if(null==params.get("export")||"".equals(params.get("export"))){
					data = "stt.id,stt.update_date,stt.change_size,stt.part_no part_no_hide,"+String.valueOf(dataTrackColumns.get(0).get("show_columns"));
				}else{//导出
					params.remove("export");
					data = "stt.update_date,stt.change_size,"+String.valueOf(dataTrackColumns.get(0).get("show_columns"));
				}
			}	
			return data;
		}
	/**
	 * @date 2017年12月6日
	 * @author gaoxp
	 * @param mtoInfo
	 * @param orderColumn
	 * @return
	 * 描述：根据专业缩写，料单号，料单行号按照指定的排序规则查询出排序后的数据跟踪表的list
	 */
	public List<StDataTrack> getTrackListByMto(Map<String,String> mtoInfo,List<String> orderColumn){
		String jpql="From "+mtoInfo.get("majorAbb")+"DataTrack dt where mto_no=:mto_no and mto_row_no=:mto_row_no and is_history='N' and material_code=:material_code";
		if(null!=orderColumn&&orderColumn.size()>0){
			jpql+=" order by ";
			for(String order:orderColumn){
				jpql+="dt."+order+",";
			}
			jpql=jpql.substring(0, jpql.length()-1);
		}
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("mto_no", mtoInfo.get("mto_no"));
		map.put("mto_row_no", mtoInfo.get("mto_row_no"));
		map.put("material_code", mtoInfo.get("material_code"));
		return findAll(jpql,map);
	}
	/**
	 * @date 2017年12月12日
	 * @author gaoxp
	 * @param projectName
	 * @param moduleName
	 * @return 根据项目名称和模块编号获取结构专业数据跟踪表数据
	 * 描述：
	 */
	public List<Map<String, Object>> getTrackDataByPM(String projectName,String moduleName){
		String sql = "select * from b_st_track_table where project_name='"+projectName+"' and module_name='"
				+moduleName +"' and is_history='N'";
		return findBySql(sql);
	}
	/**
	 * @date 2017年12月22日
	 * @author gaoxp
	 * @param date
	 * @return
	 * 描述：查询出设计料单数量有值但是数量小于qty的数据；以及设计料单数量为空，但是更新日期不是当前日期的；这两类作为缺料提醒的记录
	 */
	public List<Map<String, Object>> getTrackDataLackMaterial(String date){
		String sql="select qty,quantity_in_dm_mto,mto_no,project_name,material_code,shop_draw_no,job_no from b_st_track_table "
				 + "where is_history='N' and ((quantity_in_dm_mto is not null and quantity_in_dm_mto<qty and update_date<'"+date+"')  or "
				 + "(quantity_in_dm_mto is null and update_date<'"+date+"')) order by mto_no,shop_draw_no";
		return findBySql(sql);
	}
	public List<Map<String, Object>> getColumns(String professional,User user) {
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("major","st");
		map.put("user", user.getId());
		List<Map<String, Object>> dataTrackColumns = dataTrackColumnDao.findByUserAndMajor(map);
		return dataTrackColumns;
	}
	public QueryResult<Map<String, Object>> queryForMark(Map<String, String> params,User user) {
		String data = this.getShowData(params, user);
		String stateValue=params.get("state");
		String sql ="select '"+stateValue+"' as warn_type,stt.mark_user,stt.mark_date,stt.state_change_date,"+data+" from b_st_track_table stt where 1=1 and "+
				" stt.state_change_date is not null ";
		return getScrollData(sql,params);
	}
	public void saveState(String ids,String state, User currentUser) {
		Map<String,Object> map = new HashMap<String, Object>();
		String setColumn = " mark_user = :mark_user,mark_date=:mark_date,wasted_by_drawing_update=:waste,state=:state ";
		if("作废".equals(state)){
			map.put("waste", "Y");
			map.put("mark_user", currentUser.getName());
			map.put("mark_date", new Date());
		}else{
			map.put("waste", "N");
			map.put("mark_user", null);
			map.put("mark_date", null);
			setColumn+=",state_change_date = null ";
		}
		String jpql = "update StDataTrack set "+setColumn+ " where id in (:ids)";
		map.put("state",state);
		ArrayList<String> idList = new ArrayList<String>();
		String[] idArray = ids.split(",");
		for(int i=0;i<idArray.length;i++){
			idList.add(idArray[i]);
		}
		map.put("ids",idList);
		super.batchUpdate(jpql, map);
	}
}
