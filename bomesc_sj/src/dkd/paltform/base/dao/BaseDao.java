package dkd.paltform.base.dao;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.FlushModeType;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.SQLQuery;

import dkd.paltform.base.QueryResultTransformTuple;
import dkd.paltform.dictionary.domain.Dictionary;
import dkd.paltform.dictionary.service.DictionaryCacheService;
import dkd.paltform.base.QueryResult;
import dkd.paltform.util.StringUtils;

@SuppressWarnings({ "unchecked" })
public class BaseDao<T> {
	private SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
	private Class<T> entityClass;
	@PersistenceContext(unitName="entityManagerFactory")
	protected EntityManager em;

	public BaseDao() {
		Type type = getClass().getGenericSuperclass();
		Type paraTypes[] = ((ParameterizedType) type).getActualTypeArguments();
		entityClass = (Class<T>) paraTypes[0];
	}

	public void flush() {
		em.flush();
	}

	/**
	 * @Title: create
	 * @Description:持久化Entity
	 * @param
	 * @author CST-TONGLZ
	 * @return void
	 * @throws
	 */
	public void create(T entity) {
		em.persist(entity);
	}

	/**
	 * @Title: findByID
	 * @Description:根据主键查询
	 * @param
	 * @author CST-TONGLZ
	 * @return T
	 * @throws
	 */
	public T findByID(Serializable entityID) {
		if (entityID == null)
			throw new RuntimeException(entityClass.getName() + ":id 不允许为空");
		return em.find(entityClass, entityID);
	}

	/**
	 * @Title: update
	 * @Description:更新Entity
	 * @param
	 * @author CST-TONGLZ
	 * @return void
	 * @throws
	 */
	public T update(T entity) {
		return em.merge(entity);
	}

	/**
	 * @Title: delete
	 * @Description:单个实体删除
	 * @param
	 * @author CST-TONGLZ
	 * @return void
	 * @throws
	 */
	public void delete(Serializable entityID) {
		em.remove(em.getReference(entityClass, entityID));
	}

	/**
	 * @Title: delete
	 * @Description:多行删除
	 * @param
	 * @author CST-TONGLZ
	 * @return void
	 * @throws
	 */
	public void delete(Serializable... entityids) {
		for (Serializable EntityID : entityids) {
			em.remove(em.getReference(entityClass, EntityID));
		}
	}

	/**
	 * @Title: findByField
	 * @Description:根据fieldName查询
	 * @param
	 * @author CST-TONGLZ
	 * @return void
	 * @throws
	 */
	public T findByField(String fieldName, Object fieldValue) {
		if (fieldValue == null)
			throw new RuntimeException(entityClass.getName() + ":" + fieldName + "不允许为空");
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(entityClass);
		Root<T> root = criteriaQuery.from(entityClass);
		criteriaQuery.select(root);
		Predicate condition = criteriaBuilder.and(criteriaBuilder.equal(root.get(fieldName), fieldValue));
		criteriaQuery.where(condition);
		List<T> list = find(criteriaQuery);
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	/**
	 * @Title: findByCode
	 * @Description:根据fieldName查询
	 * @param
	 * @author CST-TONGLZ
	 * @return void
	 * @throws
	 */
	public T findByField(String fieldName, Object fieldValue, Serializable id) {
		if (fieldValue == null)
			throw new RuntimeException(entityClass.getName() + ":" + fieldName + "不允许为空");
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(entityClass);
		Root<T> root = criteriaQuery.from(entityClass);
		criteriaQuery.select(root);
		Predicate condition = criteriaBuilder.and(criteriaBuilder.equal(root.get(fieldName), fieldValue),
				criteriaBuilder.and(criteriaBuilder.notEqual(root.get("id"), id)));
		criteriaQuery.where(condition);
		List<T> list = find(criteriaQuery);
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	/**
	 * @Title: findByCode
	 * @Description:根据Code查询
	 * @param
	 * @author CST-TONGLZ
	 * @return void
	 * @throws
	 */
	public T findByCode(Serializable code) {
		if (code == null)
			throw new RuntimeException(entityClass.getName() + ":code不允许为空");
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(entityClass);
		Root<T> root = criteriaQuery.from(entityClass);
		criteriaQuery.select(root);
		Predicate condition = criteriaBuilder.and(criteriaBuilder.equal(root.get("code"), code));
		criteriaQuery.where(condition);
		List<T> list = find(criteriaQuery);
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	/**
	 * @Title: findByCode
	 * @Description:根据Code查询
	 * @param
	 * @author CST-TONGLZ
	 * @return void
	 * @throws
	 */
	public T findByCode(Serializable code, Serializable id) {
		if (code == null)
			throw new RuntimeException(entityClass.getName() + ":code不允许为空");
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(entityClass);
		Root<T> root = criteriaQuery.from(entityClass);
		criteriaQuery.select(root);
		Predicate condition = criteriaBuilder.and(criteriaBuilder.equal(root.get("code"), code),
				criteriaBuilder.and(criteriaBuilder.notEqual(root.get("id"), id)));
		criteriaQuery.where(condition);
		List<T> list = find(criteriaQuery);
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	/**
	 * @Title: findAll
	 * @Description:查询T的所有数据
	 * @param
	 * @author CST-TONGLZ
	 * @return List<T>
	 * @throws
	 */
	public List<T> findAll() {
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(entityClass);
		Root<T> root = criteriaQuery.from(entityClass);
		criteriaQuery.select(root);
		return find(criteriaQuery);
	}

	/**
	 * @Title: find
	 * @Description:执行查询
	 * @param
	 * @author CST-TONGLZ
	 * @return List<T>
	 * @throws
	 */
	protected List<T> find(CriteriaQuery<T> criteriaQuery) {
		TypedQuery<T> query = em.createQuery(criteriaQuery).setFlushMode(FlushModeType.COMMIT);
		return query.getResultList();
	}

	/**
	 * @Title: find
	 * @Description:接受JPQL的条件查询,用于返回单个实体，如：结果集大于或者等于1条数据，则返回结果集的第一条数据。否则返回NULL
	 * @param where条件
	 * @author CST-TONGLZ
	 * @return List<T>
	 * @throws
	 */
	public T find(String jpql, Map<String, Object> map) {
		TypedQuery<T> query = em.createQuery(jpql, entityClass).setFlushMode(FlushModeType.COMMIT);
		if (map != null)
			for (String key : map.keySet())
				query.setParameter(key, map.get(key));
		query.setFirstResult(0).setMaxResults(1);// 设置查询1条
		List<T> list = query.getResultList();
		if (list.size() > 0)
			return list.get(0);
		return null;
	}

	/**
	 * @Title: find
	 * @Description:接受JPQL的条件查询,用于返回单个实体，如：结果集大于或者等于1条数据，则返回结果集的第一条数据。否则返回NULL
	 * @param where条件
	 * @author CST-TONGLZ
	 * @return List<T>
	 * @throws
	 */
	public T find(String jpql) {
		return find(jpql, null);
	}

	/**
	 * @Title: find4Jpql
	 * @Description:执行JPQL语句并装载指定类型，如JPQL语句是基于多个实体的查询，则抛出异常
	 * @param
	 * @author CST-TONGLZ
	 * @return List<E>
	 * @throws
	 */
	protected List<T> findAll(String jpql, Map<String, Object> map) {
		TypedQuery<T> query = em.createQuery(jpql, entityClass).setFlushMode(FlushModeType.COMMIT);
		if (map != null)
			for (String key : map.keySet())
				query.setParameter(key, map.get(key));
		return query.getResultList();
	}

	/**
	 * @Title: find4Jpql
	 * @Description:执行JPQL语句并装载指定类型
	 * @param
	 * @author CST-TONGLZ
	 * @return List<E>
	 * @throws
	 */
	protected List<T> findAll(String jpql) {
		return findAll(jpql, null);
	}

	/**
	 * @Title: update4Jpql
	 * @Description:执行批量删除JPQL
	 * @param
	 * @author CST-TONGLZ
	 * @return
	 * @throws
	 */
	protected int batchDelete(String jpql, Map<String, Object> map) {
		Query query = em.createQuery(jpql);
		for (String key : map.keySet())
			query.setParameter(key, map.get(key));
		return query.executeUpdate();
	}

	/**
	 * @Title: batchUpdate
	 * @Description:批量更新JPQL
	 * @param
	 * @author CST-TONGLZ
	 * @return int
	 * @throws
	 */
	protected int batchUpdate(String jpql, Map<String, Object> map) {
		Query query = em.createQuery(jpql);
		for (String key : map.keySet())
			query.setParameter(key, map.get(key));
		return query.executeUpdate();
	}

	
	/**
	 * @Title: findBySql
	 * @Description:根据SQL查询
	 * @param
	 * @author CST-TONGLZ
	 * @return List<Map<String,Object>>
	 * @throws
	 */
	protected List<Map<String, Object>> findBySql(String sql, Map<String, Object> paraMap) {
		Query query = em.createNativeQuery(sql);
		query.unwrap(SQLQuery.class).setResultTransformer(QueryResultTransformTuple.INSTANCE);
		if (paraMap != null) {
			for (String key : paraMap.keySet())
				query.setParameter(key, paraMap.get(key));
		}
		return query.getResultList();
	}

	/**
	 * @Title: findBySql
	 * @Description:根据SQL查询
	 * @param
	 * @author CST-TONGLZ
	 * @return List<Map<String,Object>>
	 * @throws
	 */
	protected List<Map<String, Object>> findBySql(String sql) {
		return findBySql(sql, null);
	}

	/**
	 * @Title: findSingleResultBySql
	 * @Description:获取单结果的SQL结果
	 * @param
	 * @author CST-TONGLZ
	 * @return Object
	 * @throws
	 */
	protected Object findSingleResultBySql(String sql, Map<String, Object> paraMap) {
		Query query = em.createNativeQuery(sql);
		if (paraMap != null) {
			for (String key : paraMap.keySet())
				query.setParameter(key, paraMap.get(key));
		}
		return query.getSingleResult();
	}

	/**
	 * @Title: findSingleResultBySql
	 * @Description:
	 * @param
	 * @author CST-TONGLZ
	 * @return Object
	 * @throws
	 */
	protected Object findSingleResultBySql(String sql) {
		return findSingleResultBySql(sql, null);
	}
	/**
	 * 
	 * @date 2016-5-14
	 * @author wzm
	 * 描述：执行原生sql的update语句
	 */
	protected int updateBySql(String sql) {
		return updateBySql(sql,null);
	}
	/**
	 * 
	 * @date 2016-5-14
	 * @author wzm
	 * 描述：执行原生sql的update语句
	 */
	protected int updateBySql(String sql,Map<String, Object> paraMap) {
		Query query = em.createNativeQuery(sql);
		if (paraMap != null) {
			for (String key : paraMap.keySet())
				query.setParameter(key, paraMap.get(key));
		}
		return query.executeUpdate();
	}
	/**
	 * 
	 * @date 2016-5-14
	 * @author wzm
	 * 描述：执行原生sql的delete语句
	 */
	protected int deleteBySql(String sql) {
		return updateBySql(sql,null);
	}
	/**
	 * 
	 * @date 2016-5-14
	 * @author wzm
	 * 描述：执行原生sql的delete语句
	 */
	protected int deleteBySql(String sql,Map<String, Object> paraMap) {
		return updateBySql(sql,paraMap);
	}

	public Date getyymmdd() {
		Calendar curDate = Calendar.getInstance();
		String str = fmt.format(curDate.getTime());
		try {
			return fmt.parse(str);
		} catch (Exception e) {

		}
		return null;
	}
	protected String getEntityName(Class<T> entity) {
		// 获得类的简单名（去掉.class之后的名称）
		String entityName = entity.getSimpleName();
		// 这个实体类上指定的注解（现在获得的是Entity注解）
		Entity enti = entity.getAnnotation(Entity.class);
		if (enti.name() != null && !"".equals(enti.name())) {
			entityName = enti.name();
		}
		return entityName;
	}
	/**
	 * 拼接参数
	 * @param params
	 * @return
	 */
	protected String buildParam(Map<String,String> params,boolean hasWhere) {
		StringBuffer condition = new StringBuffer();
		if(params != null && !params.isEmpty()){
			if(!hasWhere){
				condition.append(" where 1=1 ");				
			}
			String[] filter;
			for (Map.Entry<String, String> entry : params.entrySet()) {
				filter = entry.getKey().split(" ");
				//第一个字段为key，即字段名不管是hql或sql都是在前台自己定义
				condition.append(" and " + filter[0]);
				//第二个字符串为比较符，如 > < >=等，特例：notin，为了统一格式将比较符去掉空格
				if(filter.length > 1 && !StringUtils.isEmpty(filter[1])){
					if(filter[1].equals("isnull")){
						condition.append(" is null ");
						continue;
					}else if(filter[1].equals("notin")){
						condition.append(" not in ");
					}else if(filter[1].equals("le")){
						condition.append(" <= ");
					}else if(filter[1].equals("ge")){
						condition.append(" >= ");
					}else if(filter[1].equals("lt")){
						condition.append(" < ");
					}else if(filter[1].equals("gt")){
						condition.append(" > ");
					}else if(filter[1].equals("n")){
						condition.append(" <> ");
					}else if(filter[1].equals("eq")){
						condition.append(" = ");
					}else{
						condition.append(" " + filter[1] + " ");						
					}
				}else{
					condition.append(" = ");
				}
				//第三个字段为类型，如：text number date
				if(filter.length > 2 && !StringUtils.isEmpty(filter[2])){
					if(filter[2].equals("text")){
						if(filter[1].equals("like")){
							condition.append(" '%" + entry.getValue() + "%'");							
						}else if(filter[1].equals("in") || filter[1].equals("notin")){
							if(StringUtils.isEmpty(entry.getValue())){
								condition.append(" ('') ");
							}else{
								condition.append(" ('"+entry.getValue().replaceAll(",", "','")+"')");
								/*values = entry.getValue().split(",");
								condition.append(" (");
								for(int i = 0;i < values.length;i++){
									condition.append("'" + values[i] + "'");
									if (i < values.length - 1)
										condition.append(",");
								}
								condition.append(")");*/
							}
						}else{
							condition.append(" '" + entry.getValue() + "'");
						}
					}else if(filter[2].equals("number")){
						if(filter[1].equals("in") || filter[1].equals("notin")){
							condition.append(" (" + entry.getValue() + ") ");
						}else{
							condition.append(" " + entry.getValue());							
						}
					}else if(filter[2].equals("date")){
						//日期类型，sqlserver可以直接用字符串进行比较，如替换数据库需按具体数据库处理方式修改
						condition.append("'" + entry.getValue() + "'");
					}
				}else{
					condition.append("'" + entry.getValue() + "'");
				}
			} 
		}
		return condition.toString();
	}
	
	public QueryResult<T> getScrollData(Class<T> entity, Map<String,String> params) {
		String page = params.get("page");
		params.remove("page");
		String rows = params.get("rows");
		params.remove("rows");
		String sort = params.get("sort");
		params.remove("sort");
		String order = params.get("order");
		params.remove("order");
		
		int intPage = Integer.parseInt((page == null || page == "0") ? "1" : page);
		int maxNo = Integer.parseInt((rows == null || rows == "0") ? "10" : rows);
		int firstNo = (intPage-1)*maxNo;
		
		String entityName = getEntityName(entity);
		String condition = buildParam(params,false);
		String hql = "from " + entityName + " " + condition;
		if(StringUtils.isNotEmpty(sort)){
			hql += " order by " + sort + " " + order;
		}
		Query query = em.createQuery(hql);
		if (firstNo >= 0){
			query.setFirstResult(firstNo).setMaxResults(maxNo);
		}
		List<T> data = query.getResultList();
		
		query = em.createQuery("select count(o) from " + entityName + " o " + condition);
		return new QueryResult<T>(data,(Long)query.getSingleResult());
	}
	public QueryResult<Map<String, Object>> getScrollData(String sql) {
		return getScrollData(sql,null,null);
	}
	public QueryResult<Map<String, Object>> getScrollData(String sql,Map<String,String> params) {
		return getScrollData(sql,params,null);
	}
	public QueryResult<Map<String, Object>> getScrollData(String sql, Map<String,String> params,String[] dicReplace) {
		if(StringUtils.isEmpty(sql)){
			return new QueryResult<Map<String,Object>>();
		}else{
			sql = sql.toLowerCase();
			String page = null,rows = null,sort = null,order = null;
			if(params != null){
				page = params.get("page");
				params.remove("page");
				rows = params.get("rows");
				params.remove("rows");
				sort = params.get("sort");
				params.remove("sort");
				order = params.get("order");
				params.remove("order");
			}
			int intPage = Integer.parseInt((page == null || page == "0") ? "1" : page);
			int maxNo = Integer.parseInt((rows == null || rows == "0") ? "10" : rows);
			int firstNo = (intPage-1)*maxNo;
			
			int leftBracketSize = sql.indexOf("(");
			int rightBracketSize = sql.lastIndexOf(")");
			
			int leftWhere = sql.substring(0,leftBracketSize == -1 ? 0 : leftBracketSize).indexOf("where");
			int rightWhere = sql.substring(rightBracketSize == -1 ? 0 : rightBracketSize).indexOf("where");
			
			boolean hasWhere = leftWhere >= 0 || rightWhere >= 0;
			String condition = buildParam(params,hasWhere);
			
			String orderBy = "";
			int orderBySize = sql.lastIndexOf("order by");
			if(orderBySize != -1){
				orderBy = sql.substring(orderBySize);	
				sql = sql.substring(0, orderBySize);
				if(StringUtils.isNotEmpty(sort)){
					orderBy = " order by " + sort + " " + order;
				}
			}else{
				if(StringUtils.isNotEmpty(sort)){
					orderBy = " order by " + sort + " " + order;
				}				
			}
			Query query = em.createNativeQuery(sql + condition + orderBy);
			query.unwrap(SQLQuery.class).setResultTransformer(QueryResultTransformTuple.INSTANCE);
			if (firstNo >= 0){
				query.setFirstResult(firstNo).setMaxResults(maxNo);
			}
			List<Map<String, Object>> data = query.getResultList();
			if(dicReplace != null && dicReplace.length > 0){
				Dictionary temp;
				for(Map<String, Object> map : data){
					for(String str : dicReplace){
						if(map.containsKey(str)){
							temp = DictionaryCacheService.getDic(map.get(str).toString());
							if(temp != null){
								map.put(str + "_code", temp.getCode());
								map.put(str + "_name", temp.getName());
							}
						}
					}
				}
			}
			if (firstNo >= 0){
				int size = sql.indexOf("from");
				sql = "select count(1) " + sql.substring(size);
				Query querySize = em.createNativeQuery(sql + condition);
				return new QueryResult<Map<String, Object>>(data,new Long((Integer)querySize.getSingleResult()));
			}else{
				return new QueryResult<Map<String, Object>>(data,new Long(data.size()));
			}
		}
	}
	public QueryResult<Map<String, Object>> getScrollData(Map<String,String> params) {
		return new QueryResult<Map<String,Object>>();
	}
}