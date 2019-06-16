package dkd.business.dataParamConfig.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import dkd.business.dataParamConfig.domain.DataManagerHead;
import dkd.business.dataParamConfig.service.DataManagerService;
import dkd.paltform.base.controller.BaseController;
import dkd.paltform.util.json.request.RequestJsonNode;

@Controller
@RequestMapping(value = "/dataManager")
public class DataManagerController extends BaseController{

	@Autowired
	private DataManagerService dataManagerService;
	
	@RequestMapping("/query.do")
	@ResponseBody
	public String query(HttpServletRequest request){
		return dataManagerService.getScrollData(getParam(request)).toJson();
	}
	
	@RequestMapping("/edit.do")
	public String edit(String id,ModelMap model){
		model.put("id", id);
		return "business/dataParamConfig/dataManager/dataManagerEdit";
	}
	
	@RequestMapping("/findByNo.do")
	@ResponseBody
	public String findByNo(String id){
		String str = dataManagerService.findByFieldToJson("id", id);
		return str;
	}
	
	@RequestMapping("/checkValidate.do")
	public void checkValidate(@RequestJsonNode("project")String project,@RequestJsonNode("major")String major,
			@RequestJsonNode("id")String id,HttpServletResponse response) throws IOException{
		Boolean flag = dataManagerService.checkValidate(project,major,id);
		response.getWriter().print(flag);
	}
	
	@RequestMapping("/save.do")
	@ResponseBody
	public String save(@RequestBody DataManagerHead dataManagerHead){
		String id = dataManagerService.save(dataManagerHead);
		return toWriteSuccess("保存成功！",id);
	}
	
	@RequestMapping("/view.do")
	public String view(String id,ModelMap model){
		model.put("id", id);
		return "business/dataParamConfig/dataManager/dataManagerView";
	}
	
	@RequestMapping("/delete.do")
	@ResponseBody
	public String delete(String id){
		dataManagerService.delete(id);
		return toWriteSuccess("删除成功！");
	}
}
