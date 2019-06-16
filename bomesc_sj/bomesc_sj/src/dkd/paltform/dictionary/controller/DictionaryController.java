package dkd.paltform.dictionary.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import dkd.paltform.base.controller.BaseController;
import dkd.paltform.dictionary.domain.Dictionary;
import dkd.paltform.dictionary.service.DictionaryService;
import dkd.paltform.util.json.JSONUtil;

@Controller
@RequestMapping("/dictionary")
public class DictionaryController extends BaseController{

	@Autowired
	private DictionaryService dictionaryService;

	@RequestMapping(value = "/showList.do")
	public String showList() {
		return "system/dictionary/userList";
	}
	@RequestMapping(value = "/query.do")
	@ResponseBody
	public String query(HttpServletRequest request) {
		return dictionaryService.getScrollData(getParam(request)).toJson();
	}
	
	@RequestMapping("/getDictionary.do")
	@ResponseBody
	public String getDictionary() {
		String records = JSONUtil.getJsonByEntity(dictionaryService.getDictionary());
		return records;
	}

	@RequestMapping("/getNextDictionary.do")
	@ResponseBody
	public String getNextDictionary(String pid) {
		return dictionaryService.findByParent(pid).toJson();
	}

	@RequestMapping("/searchDictionary.do")
	@ResponseBody
	public String seachDictionary(String code) {
		String str =  dictionaryService.findByParentCode(code);
		return str;
	}
	
	@RequestMapping(value = "/save.do")
	@ResponseBody
	public String save(@RequestBody Dictionary dictionary) {
		dictionaryService.save(dictionary);
		return toWriteSuccess("保存字典成功！");
	}

	@RequestMapping("/deleteDictionary.do")
	@ResponseBody
	public String deleteDictionary(String id) {
		dictionaryService.deleteDic(id);
		return toWriteSuccess("删除字典成功！");
	}

	@RequestMapping("/findById.do")
	@ResponseBody
	public String findById(String id) {
		return dictionaryService.findByIDToJson(id);
	}
	@RequestMapping("/selectDictionaryByMajor.do")
	@ResponseBody
	public String selectDictionaryByMajor(String major) throws JsonGenerationException, JsonMappingException, IOException {
		 String selectDictionaryByMajor = dictionaryService.selectDictionaryByMajor(major);
		return selectDictionaryByMajor;
	}
	
}
