package dkd.business.designDataManager.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import dkd.business.designDataManager.domain.StDataTrackSupp;
import dkd.paltform.authority.domain.User;
import dkd.paltform.base.QueryResult;
import dkd.paltform.base.dao.BaseDao;
@Repository
public class StDataTrackSuppDao extends BaseDao<StDataTrackSupp>{
	
	@Autowired
	private DataTrackColumnDao dataTrackColumnDao;
	/**
	 * 重写getScrollData方法
	 */
	public QueryResult<Map<String, Object>> getScrollDataByUser(Map<String, String> param, User currentUser) {

		Map<String,Object> map = new HashMap<String, Object>();
		map.put("major","st");
		map.put("user", currentUser.getId());
		List<Map<String,Object>> findByUM = this.findByUM(map);
		//结构数据跟踪表全部字段
		String data="stt.id,stt.project_name,stt.job_no,stt.module_name,stt.shop_draw_no,stt.shop_draw_rev,stt.contractor_draw_no,stt.contractor_draw_rev,"
				+ "stt.part_no,stt.part_name,stt.steel_type,stt.structure_type,stt.area_of_part,stt.level_no,stt.work_package_no,stt.part_profile_name,"
				+ "stt.part_profile,stt.grade,stt.qty,stt.quantity_in_dm_mto,stt.quantity_in_pd_mto,stt.net_single_length,stt.net_single_width,"
				+ "stt.unit_weight,stt.net_single_weight,stt.net_total_weight,stt.gross_single_weight,stt.gross_total_weight,stt.net_area,stt.surface_treatment,"
				+ "stt.coating_system,stt.coating_area,stt.fireproof_type,stt.fireproof_area,stt.fireproof_length,stt.fireproof_thickness,stt.wasted_by_drawing_update,"
				+ "stt.added_by_drawing_update,stt.nesting_draw_no,stt.nesting_draw_rev,stt.bulk_material_no,stt.remnant_part_no,stt.material_code,"
				+ "stt.supplier,stt.mto_no,stt.mto_row_no,stt.ident_code,stt.pay_item_no,stt.bar_code_no,stt.vendor,stt.site,stt.website,stt.tellphone_no,stt.column1,stt.column2,stt.column3,"
				+ "stt.column4,stt.column5,stt.column6,stt.column7,stt.column8,stt.column9,stt.column10,stt.column11,stt.column12,stt.column13,stt.column14,stt.column15,stt.column16,"
				+ "stt.column17,stt.column18,stt.column19,stt.column20,stt.quantity_of_inwarehouse,stt.recipients_of_warehouse,stt.waste_materials_outbound,stt.in_allocating_outbound,"
				+ "stt.construction_of_withdrawing,stt.stock,stt.generate_request_number";
		//用户自定义查询字段
		if(!findByUM.isEmpty()){
			data = "stt.id,"+String.valueOf(findByUM.get(0).get("show_columns"));
		}
		String sql = "select "+data+" from b_st_data_track_supp stt";
		
		return getScrollData(sql, param);
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
	
	public QueryResult<Map<String, Object>> getScrollData(Map<String,String> params){
		String sql="select * from b_st_fed_batch_data_track ";
		return getScrollData(sql,params);
	}
	/**
	 * @date 2017年12月6日
	 * @author gaoxp
	 * @param mtoInfo
	 * @param orderColumn
	 * @return
	 * 描述：根据专业缩写，料单号，料单行号按照指定的排序规则查询出排序后的数据跟踪表的list
	 */
	public List<StDataTrackSupp> getTrackListByMto(Map<String,String> mtoInfo,List<String> orderColumn){
		String jpql="From "+mtoInfo.get("majorAbb")+"DataTrackSupp dt where mto_no=:mto_no and mto_row_no=:mto_row_no and material_code=:material_code";
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
		map.put("material_code",mtoInfo.get("material_code"));
		return findAll(jpql,map);
	}
	public List<Map<String, Object>> findIfNULL(StringBuffer buffer) {
		String partNos="";
		if (buffer.length()>0) {
			 partNos = buffer.substring(0, buffer.length()-1);
		}
		String sql = "select * from b_st_data_track_supp where part_no in ("+partNos+")";
		return findBySql(sql);
	}
	/**
	 * @date 2018年1月10日
	 * @param idList
	 * @return
	 * 描述：物资编码  查询   物料档案的数据（类型 ，名称 ，单位） 
	 */
	public  List<Map<String, Object>> findPickingList(String idList) {
		// TODO Auto-generated method stub
		String sql = "select s.module_name,s.coating_area,s.project_name,s.job_no,s.mto_no,s.remnant_part_no,s.grade,b.material_name,b.unit from b_st_data_track_supp s,base_material_archives b where s.material_code = b.material_code and s.id in ("+idList+")";
		return findBySql(sql);
	}
	/**
	 * @date 2018年1月10日
	 * @param bulk_material_nos
	 * 描述：根据母材编号查寻 是同一个的明细
	 */
	public List<Map<String, Object>> findElseSuppByBMO(String bulk_material_nos) {
		String sql = "select s.module_name,s.coating_area,s.project_name,s.job_no,s.mto_no,s.remnant_part_no,s.grade,b.material_name,b.unit from b_st_data_track_supp s,base_material_archives b where s.material_code = b.material_code and s.bulk_material_no in ("+bulk_material_nos+")";
		return findBySql(sql);
	}
	
}
