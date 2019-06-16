package dkd.paltform.authority.controller;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import dkd.business.dataParamConfig.service.ColumnDisplaySettingService;
import dkd.business.designDataManager.service.DataTrackColumnsService;
import dkd.paltform.authority.domain.User;
import dkd.paltform.base.BusinessException;
import dkd.paltform.base.annotation.BeanSelect;
import dkd.paltform.base.controller.BaseController;
import dkd.paltform.flowset.service.ProcessRecordService;
import dkd.paltform.util.SpringUtil;
import dkd.paltform.util.StringUtils;
import dkd.paltform.util.json.JSONUtil;

@Controller
@RequestMapping(value = "/login")
public class LoginController extends BaseController{
	@Autowired
	private ProcessRecordService processRecordService;
	
	@Autowired 
	private ColumnDisplaySettingService columnDisplaySettingService;
	
	@Autowired
	private DataTrackColumnsService dataTrackColumnsService;
	
	@RequestMapping(value = "/index.do")
	public String index(HttpServletRequest request,String current_user_sj,String auths_sj,ModelMap model) {
		model.put("current_user_sj", current_user_sj);
		model.put("auths_sj", auths_sj);
		return "index";
	}
	@RequestMapping(value = "/welcome.do")
	public String welcome(HttpServletRequest request) {
		return "welcome";
	}
	@RequestMapping(value = "/task.do")
	@ResponseBody
	public String task(HttpServletRequest request) {
		return processRecordService.getTaskScrollData().toJson();
	}
	@RequestMapping(value = "/showList.do")
	public String showList(String url,String module,HttpServletRequest request,ModelMap model) {
		model.put("module",module);
		String[] urls = url.split("/");
		if(url.indexOf("system")!=-1){
			return url + "/" + urls[1] + "List";
		}else{
			return url + "/" + urls[2] + "List";
		}
	}
	@RequestMapping(value = "/menu.do")
	public String menu(HttpServletRequest request,ModelMap model) {
		return "menu";
	}
	@RequestMapping(value = "/selectUserList.do")
	public String selectUserList(HttpServletRequest request,ModelMap model) {
		return "common/selectUser";
	}
	@RequestMapping(value = "/findRole.do")
	@ResponseBody
	public String findRole(HttpServletRequest request,ModelMap model) {
		return SpringUtil.getAllRolesStr();
	}
	@RequestMapping(value = "/selectUser.do")
	@ResponseBody
	public String selectUser(HttpServletRequest request,ModelMap model) {
		return SpringUtil.getUserByParams(getParam(request));
	}
	@RequestMapping(value = "/selectDept.do")
	@ResponseBody
	public String selectDept(HttpServletRequest request,ModelMap model) {
		return SpringUtil.getDeptByParams(getParam(request));
	}
	@RequestMapping("/searchItems.do")
	@ResponseBody
	public String searchItems(String beanName,String professional,String jobNo) throws IllegalArgumentException, IllegalAccessException {
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		Map<String, String> rowMap = new HashMap<String, String>();
		Properties beanProp = SpringUtil.getBeanProp();
		Class<?> c = testGet(beanProp,beanName);
		BeanSelect beanSelect;
		String searchType;
		Boolean isSearch;
		Map<String, Object> columnDisplaySetting;
		for (Field field : c.getDeclaredFields()) {
			beanSelect = field.getAnnotation(BeanSelect.class);
			if(beanSelect != null){
				isSearch= beanSelect.isSearch();
				if(!isSearch){
					continue;
				}
				rowMap = new HashMap<String, String>();
				searchType = beanSelect.searchType();
				if(StringUtils.isEmpty(searchType)){
					searchType = field.getName() + " like text";
				}
				if(beanSelect.fieldName().indexOf("补充列")!=-1){
					if("professional_st".equals(professional)){
						columnDisplaySetting = columnDisplaySettingService.findByMajor(jobNo,professional);
						if(null!=columnDisplaySetting){
							String value =String.valueOf(columnDisplaySetting.get(field.getName()));
							if(!"".equals(value)&&null!=value){
								rowMap.put("value", searchType);
								rowMap.put("text", String.valueOf(columnDisplaySetting.get(field.getName())));
							}
						}
					}
				}else{
					rowMap.put("value", searchType);
					rowMap.put("text", beanSelect.fieldName());
				}
				if(!rowMap.isEmpty()){
					list.add(rowMap);
				}
			}
		}
		return JSONUtil.getJsonByEntity(list);
	}
	
	@RequestMapping("/searchColumnName.do")
	@ResponseBody
	public String searchColumnName(String beanName,String professional,HttpServletRequest request) throws IllegalArgumentException, IllegalAccessException {
		User user = getCurrentUser(request.getSession());
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		Map<String, String> rowMap = new HashMap<String, String>();
		Properties beanProp = SpringUtil.getBeanProp();
		Class<?> c =testGet(beanProp,beanName);
		BeanSelect beanSelect;
		Map<String,Object> mapObject = new HashMap<String, Object>();
		mapObject.put("user", user.getId());
		mapObject.put("major",professional.substring(professional.indexOf("_")+1));
		Map<String, Object> dataTrackColumns = dataTrackColumnsService.findByUserAndMajor(mapObject);
		String show_columns="";
		if(null!=dataTrackColumns){
			show_columns = ","+ String.valueOf(dataTrackColumns.get("show_columns"))+",";
		}
		Boolean isShow;
		for (Field field : c.getDeclaredFields()) {
			beanSelect = field.getAnnotation(BeanSelect.class);
			if(beanSelect != null){
				isShow=beanSelect.isShow();
				if(!isShow){
					continue;
				}
				rowMap = new HashMap<String, String>();
				String majorTrg = professional.substring(professional.indexOf("_")+1);
				if ("pi".equals(majorTrg)) {
					rowMap.put("value", "pit."+field.getName());
				}else if ("st".equals(majorTrg)) {
					rowMap.put("value", "stt."+field.getName());
				}
				rowMap.put("text", beanSelect.fieldName());
				if(!"".equals(show_columns)){
					int indexOf = 0;
					if ("pi".equals(majorTrg)) {
						 indexOf = show_columns.indexOf((",pit."+field.getName()+","));
					}else if ("st".equals(majorTrg)) {
						 indexOf = show_columns.indexOf((",stt."+field.getName()+","));
					}
					if(indexOf!=-1){
						rowMap.put("check","true");
					}else{
						rowMap.put("check","false");
					}
				}else{
					rowMap.put("check","true");
				}
				list.add(rowMap);
			}
		}
		return JSONUtil.getJsonByEntity(list);
	}
	
	private Class<?> testGet(Properties beanProp,String beanName){
		Class<?> c;
		try {
			c = Class.forName(beanProp.getProperty(beanName));
			return c;
		} catch (ClassNotFoundException e) {
			throw new BusinessException(-1, "", "读取实体字段错误");
		}
	}
}