package dkd.business.material.dao;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import dkd.business.material.domain.MaterialArchives;
import dkd.paltform.base.QueryResult;
import dkd.paltform.base.dao.BaseDao;

@Repository
public class MaterialArchivesDao extends BaseDao<MaterialArchives>{
	/**
	 * 查询方法，点击不同分类节点，查询相应的物料档案
	 */
	@Override
	public QueryResult<Map<String, Object>> getScrollData(Map<String,String> params){
		String total_code="";
		if(!StringUtils.equals("0",params.get("id"))){
			List<Map<String, Object>> totalCodeList = findTotalCodeBySql(params.get("id"));
			total_code = (String) totalCodeList.get(0).get("total_code");//根据传来的树的id，获取这个书和前置分类拼接的分类编码，用来查询时进行模糊查询
		}
		String sql="";
		if("".equals(total_code)){
			sql="select * from base_material_archives order by material_code desc";
		}else{
			sql="select * from base_material_archives where material_code like '"+total_code+"%' order by material_code desc";
		}
		params.put("is_del", "N");
		params.remove("id");
		params.remove("levels");
		return getScrollData(sql,params);
	}
	/**
	 * 根据产品分类id获取拼接的分类编码
	 */
	public List<Map<String, Object>> findTotalCodeBySql(String id){
		String sql="select isnull(k.code,'')+isnull(j.code,'')+isnull(i.code,'')+isnull(h.code,'')+isnull(g.code,'')"
				+ "+isnull(f.code,'')+isnull(e.code,'')+isnull(d.code,'')+isnull(c.code,'')+isnull(b.code,'')+isnull(a.code,'') "
				+ "as total_code from base_material_class a "
				+"left join base_material_class b on a.parent=b.id "
				+"left join base_material_class c on b.parent=c.id "
				+"left join base_material_class d on c.parent=d.id "
				+"left join base_material_class e on d.parent=e.id "
				+ "left join base_material_class f on e.parent=f.id "
				+ "left join base_material_class g on f.parent=g.id "
				+ "left join base_material_class h on g.parent=h.id "
				+ "left join base_material_class i on h.parent=i.id "
				+ "left join base_material_class j on i.parent=j.id "
				+ "left join base_material_class k on j.parent=k.id"
				+" where  a.id='"+id+"'";
		return super.findBySql(sql);
	}
	/**
	 * @date 2018年1月11日
	 * @author gaoxp
	 * @param subMaterialCode
	 * @return
	 * 描述：更具分类编号获取这个分类编号下的流水号
	 */
	public List<Map<String, Object>> findSerialNum(String subMaterialCode){
		String sql = "select max(substring(material_code,17,2)) as serial_num "
				+" from base_material_archives where material_code like '"+subMaterialCode+"%'";
		return super.findBySql(sql);
	}
	/**
	 * @date 2018年1月11日
	 * @param set2
	 * @return
	 * 描述：根据多条物资编码  查询
	 */
	public List<Map<String, Object>> findMaterCode(Set<String> set2) {
		String codes="";
		StringBuffer buf= new StringBuffer();
		for (String string : set2) {
			buf.append(string).append(",");
		}
		codes =  buf.substring(0, buf.length()-1);
		String sql = "select * from base_material_archives where material_code in ("+codes+")";
		return findBySql(sql);
	}
//	/**
//	 * @date 2018年1月11日
//	 * @author gaoxp
//	 * @param materialClass
//	 * @return
//	 * 描述：根据物料分类实体查询物料档案是否有相关数据，有的话返回false，用于物料分类更新操作时的校验
//	 */
//	public boolean findByVaryClass(MaterialClass materialClass){
//		Map<Integer,String> levelsToIdMap=new HashMap<Integer,String>();
//		levelsToIdMap.put(1, "product_professionl");
//		levelsToIdMap.put(2, "product_big_species");
//		levelsToIdMap.put(3, "product_middle_species");
//		levelsToIdMap.put(4, "design_criteria");
//		levelsToIdMap.put(5, "quality");
//		levelsToIdMap.put(6, "size");
//		levelsToIdMap.put(7, "standard1");
//		levelsToIdMap.put(8, "standard2");
//		levelsToIdMap.put(9, "standard3");
//		levelsToIdMap.put(10, "standard4");
//		levelsToIdMap.put(11, "standard5");
//		String jpql="From MaterialArchives where "+levelsToIdMap.get(materialClass.getLevels())+"=:"+levelsToIdMap.get(materialClass.getLevels());
//		Map<String,Object> params = new HashMap<String,Object>();
//		params.put(levelsToIdMap.get(materialClass.getLevels()), materialClass);
//		if(null==find(jpql, params)){
//			return false;
//		}else{
//			return true;
//		}
//	} 
	/**
	 * @date 2018年1月15日
	 * @author gaoxp
	 * @param totalCode
	 * @return
	 * 描述：根据totalcode查询物料编码
	 */
	public List<Map<String, Object>> findByTotalCode(String totalCode){
		String sql = "select * from base_material_archives where material_code like '"+totalCode+"%'";
		return findBySql(sql);
	}
}
