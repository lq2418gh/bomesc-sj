package dkd.paltform.dictionary.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import dkd.paltform.base.QueryResult;
import dkd.paltform.base.dao.BaseDao;
import dkd.paltform.dictionary.domain.Dictionary;

@Repository
public class DictionaryDao extends BaseDao<Dictionary> {

	public QueryResult<Map<String, Object>> findByParent(String parent) {
		Map<String,String> params = new HashMap<String,String>();
		params.put("page", "-1");
		return getScrollData("select id,code,name from base_dictionary where parent='" + parent + "' order by ENTITY_CREATEDATE",params);
	}

	@Override
	public QueryResult<Map<String, Object>> getScrollData(Map<String,String> params){
		return getScrollData("select id,code,name from base_dictionary where parent='0' order by ENTITY_CREATEDATE",params);
	}

	@Override
	public List<Dictionary> findAll() {
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<Dictionary> criteriaQuery = criteriaBuilder.createQuery(Dictionary.class);
		Root<Dictionary> root = criteriaQuery.from(Dictionary.class);
		criteriaQuery.select(root);
		criteriaQuery.orderBy(criteriaBuilder.asc(root.get("sort_no")));
		return find(criteriaQuery);
	}
	public QueryResult<Map<String, Object>> findAllByParent(String parent) {
		return getScrollData("select b.id ,b.code,b.name,b.sort_no,b.entity_createdate,b.entity_createuser,b.entity_modifyuser,b.entity_modifydate from base_dictionary b where b.parent='" + parent +"' order by b.sort_no"  );
	}
	/**
	 * @date 2017年12月2日
	 * @author gaoxp
	 * @return
	 * 描述：获取对应专业缩写的字典
	 */
	public List<Map<String, Object>> findProfessionalByAbb(String majorAbb){
		String sql = "select * from base_dictionary where name like '%"+majorAbb+"%' and parent = (select id from base_dictionary where code = 'professional')";
		return super.findBySql(sql);
	}
}
