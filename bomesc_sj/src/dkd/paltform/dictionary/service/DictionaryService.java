package dkd.paltform.dictionary.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import dkd.paltform.base.BusinessException;
import dkd.paltform.base.QueryResult;
import dkd.paltform.base.dao.BaseDao;
import dkd.paltform.base.service.BaseService;
import dkd.paltform.dictionary.dao.DictionaryDao;
import dkd.paltform.dictionary.domain.Dictionary;
import dkd.paltform.util.json.JSONUtil;

@Service
@Transactional
public class DictionaryService extends BaseService<Dictionary> {

	@Autowired
	private DictionaryDao dictionaryDao;

	@Override
	public BaseDao<Dictionary> getDao() {
		return dictionaryDao;
	}

	@Transactional(propagation = Propagation.SUPPORTS)
	public QueryResult<Map<String, Object>> findByParent(String parent) {
		return dictionaryDao.findByParent(parent);
	}

	@Transactional(propagation = Propagation.SUPPORTS)
	public List<Dictionary> getDictionary() {
		List<Dictionary> list = dictionaryDao.findAll();
		List<Dictionary> parents = new ArrayList<Dictionary>();
		List<Dictionary> childs = new ArrayList<Dictionary>();
		for (Dictionary dictionary : list) {
			if (dictionary.getParent().equals("0")) {
				parents.add(dictionary);
			} else {
				childs.add(dictionary);
			}
		}
		for (Dictionary dictionary : parents) {
			List<Dictionary> childNodes = new ArrayList<Dictionary>();
			for (Dictionary dic : childs) {
				if (dic.getParent().equals(dictionary.getId())) {
					childNodes.add(dic);
				}
				continue;
			}
			dictionary.setChildNodes(childNodes);
		}
		return parents;
	}

	private void checkCode(Dictionary dictionary) {
		List<Map<String, Object>> list = findByParent(dictionary.getParent()).getRows();
		for (Map<String, Object> dict : list) {
			if (dict.get("id").toString().equals(dictionary.getId()))
				continue;
			if (dictionary.getCode().equals(dict.get("code").toString()))
				throw new BusinessException(-1, "code", "字典编号重复，请重新输入");
			if (dictionary.getName().equals(dict.get("name").toString()))
				throw new BusinessException(-1, "code", "字典名称重复，请重新输入");
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public String save(Dictionary dictionary) {
		checkCode(dictionary);
		if (dictionary.getId() != null) {
			update(dictionary);
		} else {
			create(dictionary);
		}
		flush();
		DictionaryCacheService.loadDicMap();
		return JSONUtil.getJsonByEntity(dictionary);
	}

	@Transactional(propagation = Propagation.SUPPORTS)
	public String findByParentCode(String code) {
		Map<String, String> map = DictionaryCacheService.getMapByGroup(code);
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		Map<String, String> rowMap = new HashMap<String, String>();
		rowMap.put("value", "");
		rowMap.put("text", "请选择");
		list.add(rowMap);
		for (String key : map.keySet()) {
			rowMap = new HashMap<String, String>();
			rowMap.put("value", key);
			rowMap.put("text", map.get(key));
			list.add(rowMap);
		}
		return JSONUtil.getJsonByEntity(list);
	}

	/*@Transactional(propagation = Propagation.SUPPORTS)
	public String findByPage(QueryCondition condition) {
		return dictionaryDao.findByPage(condition);
	}*/

	@Transactional(propagation = Propagation.REQUIRED)
	public void deleteDic(String id) {
		List<Map<String, Object>> list = findByParent(id).getRows();
		for (Map<String, Object> dictionary : list){
			delete(dictionary.get("id").toString());			
		}
		delete(id);
		flush();
		DictionaryCacheService.loadDicMap();
	}
	@Transactional(propagation = Propagation.SUPPORTS)
	public String selectDictionaryByMajor(String id) throws JsonGenerationException, JsonMappingException, IOException {
		Dictionary dic = dictionaryDao.findByID(id);
		String code = dic.getCode();
		if ("professional_st".equals(code)) {
			QueryResult<Map<String,Object>> findByParent = dictionaryDao.findByParent(dictionaryDao.findByCode("sort_column").getId());
			ObjectMapper json = new ObjectMapper();
			return json.writeValueAsString(findByParent);
		}
		if ("professional_pi".equals(code)) {
			QueryResult<Map<String,Object>> findByParent = dictionaryDao.findByParent(dictionaryDao.findByCode("pi_column").getId());
			ObjectMapper json = new ObjectMapper();
			return json.writeValueAsString(findByParent);
		}
		if ("professional_ei".equals(code)) {
			QueryResult<Map<String,Object>> findByParent = dictionaryDao.findByParent(dictionaryDao.findByCode("ei_column").getId());
			ObjectMapper json = new ObjectMapper();
			return json.writeValueAsString(findByParent);
		}
		if ("professional_hvac".equals(code)) {
			QueryResult<Map<String,Object>> findByParent = dictionaryDao.findByParent(dictionaryDao.findByCode("hvac_column").getId());
			ObjectMapper json = new ObjectMapper();
			return json.writeValueAsString(findByParent);
		}
		if ("professional_ar".equals(code)) {
			QueryResult<Map<String,Object>> findByParent = dictionaryDao.findByParent(dictionaryDao.findByCode("ar_column").getId());
			ObjectMapper json = new ObjectMapper();
			return json.writeValueAsString(findByParent);
		}
		return "查询失败!";
	}
	
	@Transactional(propagation = Propagation.SUPPORTS)
	public QueryResult<Map<String, Object>> findAllByParent(String parent) {
		return dictionaryDao.findAllByParent(parent);
	}
	/**
	 * @date 2017年12月2日
	 * @author wzm
	 * @param majorAbb
	 * @return
	 * 描述：获取对应缩写的字典实体
	 */
	public List<Map<String, Object>> findProfessionalByAbb(String majorAbb){
		return dictionaryDao.findProfessionalByAbb(majorAbb);
	}
}
