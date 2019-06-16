package dkd.business.material.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import dkd.business.material.domain.MaterialClass;
import dkd.paltform.base.QueryResult;
import dkd.paltform.base.dao.BaseDao;
@Repository
public class MaterialClassDao extends BaseDao<MaterialClass>{
	/**
	 * @date 2018年1月8日
	 * @author gaoxp
	 * @param parentId
	 * @return
	 * 描述：根据父id获取所有的子数据
	 */
	public List<MaterialClass> findNextClass(String parentId){
		String jpql;
		Map<String, Object> map = new HashMap<String, Object>();
		if(StringUtils.isEmpty(parentId)){
			jpql = "from MaterialClass where parent is null order by sort_no";
		}else{
			jpql = "from MaterialClass where parent.id=:parent order by sort_no";
			map.put("parent", parentId);
		}
		return super.findAll(jpql, map);
	}
	/**
	 * @date 2018年1月8日
	 * @author gaoxp
	 * @param materialClass
	 * @return
	 * 描述：校验code是否重复
	 */
	public MaterialClass findByValidate(MaterialClass materialClass){
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("code", materialClass.getCode());
		params.put("parent", materialClass.getParent());
		String jpql = "from MaterialClass where code=:code and parent=:parent";
		if(!StringUtils.isEmpty(materialClass.getId())){
			jpql += " and id!=:id";
			params.put("id", materialClass.getId());
		}
		return find(jpql,params);
	}
	@Override
	public QueryResult<Map<String, Object>> getScrollData(Map<String,String> params){
		if(!StringUtils.isEmpty(params.get("parent"))){
			params.put("a.parent", params.get("parent"));
			params.remove("parent");
		}
		String sql="select isnull(k.code,'')+isnull(j.code,'')+isnull(i.code,'')+isnull(h.code,'')+isnull(g.code,'')"
				+ "+isnull(f.code,'')+isnull(e.code,'')+isnull(d.code,'')+isnull(c.code,'')+isnull(b.code,'')+isnull(a.code,'') "
				+ "as parent_code, a.* from base_material_class a "
				+"left join base_material_class b on a.parent=b.id "
				+"left join base_material_class c on b.parent=c.id "
				+"left join base_material_class d on c.parent=d.id "
				+"left join base_material_class e on d.parent=e.id "
				+ "left join base_material_class f on e.parent=f.id "
				+ "left join base_material_class g on f.parent=g.id "
				+ "left join base_material_class h on g.parent=h.id "
				+ "left join base_material_class i on h.parent=i.id "
				+ "left join base_material_class j on i.parent=j.id "
				+ "left join base_material_class k on j.parent=k.id";
		if("".equals(params.get("parent isnull"))){
			params.remove("parent isnull");
			sql+=" where a.parent is null ";
		}
		sql+=" order by sort_no";
		return getScrollData(sql,params);
	}
}
