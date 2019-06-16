package dkd.paltform.base.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import dkd.paltform.base.QueryResult;
import dkd.paltform.base.dao.BaseDao;
import dkd.paltform.util.json.JSONUtil;

@Service
@Transactional
public abstract class BaseService<T> {

	public abstract BaseDao<T> getDao();

	@Transactional(propagation = Propagation.REQUIRED)
	public void create(T T) {
		getDao().create(T);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void delete(String id) {
		getDao().delete(id);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void update(T t) {
		getDao().update(t);
	}

	@Transactional(propagation = Propagation.SUPPORTS)
	public T findByID(String ID) {
		T t = getDao().findByID(ID);
		return t;
	}

	@Transactional(propagation = Propagation.SUPPORTS)
	public String findByIDToJson(String ID) {
		T t = getDao().findByID(ID);
		return JSONUtil.getJsonByEntity(t);
	}

	@Transactional(propagation = Propagation.SUPPORTS)
	public List<T> findAll() {
		List<T> entitys = getDao().findAll();
		return entitys;
	}

	@Transactional(propagation = Propagation.SUPPORTS)
	public T findByCode(String code) {
		T t = getDao().findByCode(code);
		return t;
	}

	@Transactional(propagation = Propagation.SUPPORTS)
	public T findByCode(Serializable code, Serializable id) {
		T t = getDao().findByCode(code, id);
		return t;
	}

	@Transactional(propagation = Propagation.SUPPORTS)
	public void flush() {
		getDao().flush();
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
		return getDao().findByField(fieldName, fieldValue);
	}
	public String findByFieldToJson(String fieldName, Object fieldValue) {
		T t = getDao().findByField(fieldName, fieldValue);
		return JSONUtil.getJsonByEntity(t);
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
		return getDao().findByField(fieldName, fieldValue, id);
	}
	
	/**
	 * @Title: getCurrentUser
	 * @Description:获取当前登录用户-->未登录则回抛出异常
	 * @param
	 * @author CST-TONGLZ
	 * @return User
	 * @throws
	 */
/*	public User getCurrentUser() {
		Object obj = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (!(obj instanceof SysUserDetails)) {
			throw new SessionInvalidException("Session失效,需要重新登录");
		}
		SysUserDetails userDetails = (SysUserDetails) obj;
		if (userDetails == null || userDetails.getUser() == null)
			throw new SessionInvalidException("Session失效,需要重新登录");
		return userDetails.getUser();
	}*/

	public static String formatNumber(int number, int len) {
		StringBuffer result = new StringBuffer(len);
		result.append(number);
		for (int i = 0; i < len - String.valueOf(number).length(); i++) {
			result.insert(0, "0");
		}
		return result.toString();
	}
	public QueryResult<T> getScrollData(Class<T> entity, Map<String,String> params) {
		return getDao().getScrollData(entity, params);
	}
	public QueryResult<Map<String,Object>> getScrollData(Map<String,String> params) {
		return getDao().getScrollData(params);
	}
	
	@Transactional(propagation = Propagation.SUPPORTS)
	public String findByIDToJson(String ID,Map<String,Object> map) {
		T t = getDao().findByID(ID);
		return JSONUtil.getJsonByEntity(t,map);
	}
}
