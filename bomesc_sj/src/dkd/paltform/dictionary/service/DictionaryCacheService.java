package dkd.paltform.dictionary.service;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import dkd.paltform.dictionary.domain.Dictionary;

/**
 * @ClassName: DicUtil
 * @Description:字典缓存服务
 * @author CST-TONGLZ
 * @date 2015-10-16 下午4:38:28
 * 
 */
@Component
public class DictionaryCacheService {
	private static DictionaryService dictionaryService;
	private static Map<String, Map<String, String>> dicMap = null;
	private static Map<String, Dictionary> runMap = new HashMap<String, Dictionary>();

	@Autowired
	public void setSysLogService(DictionaryService dictionaryService) {
		DictionaryCacheService.dictionaryService = dictionaryService;
	}

	/**
	 * @Title: loadDicMap
	 * @Description:刷新缓存
	 * @param
	 * @author CST-TONGLZ
	 * @return void
	 * @throws
	 */
	public static synchronized void loadDicMap() {
		runMap.clear();
		dicMap = new HashMap<String, Map<String, String>>();
		String rootPareat = "0";
		List<Dictionary> list = dictionaryService.findAll();
		for (Dictionary dictionary : list) {
			runMap.put(dictionary.getId(), dictionary);
			String parent = dictionary.getParent();
			if (!rootPareat.equals(parent))
				continue;
			String code = dictionary.getCode();
			dicMap.put(code, new LinkedHashMap<String, String>());
		}
		for (Dictionary dictionary : list) {
			String parent = dictionary.getParent();
			if (rootPareat.equals(parent))
				continue;
			Dictionary parentDictionary = runMap.get(parent);
			if (parentDictionary == null)
				continue;
			String group = parentDictionary.getCode();
			String id = dictionary.getId();
			String name = dictionary.getName();
			if (!dicMap.containsKey(group))
				continue;
			dicMap.get(group).put(id, name);
		}
		//runMap.clear();
	}

	/**
	 * @Title: getDicName
	 * @Description:获取名称
	 * @param
	 * @author CST-TONGLZ
	 * @return String
	 * @throws
	 */
	public static String getDicName(String dicId) {
		if (runMap.isEmpty())
			loadDicMap();
		if (runMap.containsKey(dicId)) {
			return runMap.get(dicId).getName();
		}
		return "";
	}
	public static Dictionary getDic(String dicId) {
		if (runMap.isEmpty())
			loadDicMap();
		if (runMap.containsKey(dicId)) {
			return runMap.get(dicId);
		}
		return null;
	}
	public static Map<String, String> getMapByGroup(String group) {
		if (dicMap == null)
			loadDicMap();
		if (dicMap.containsKey(group))
			return dicMap.get(group);
		return new HashMap<String, String>();
	}
}