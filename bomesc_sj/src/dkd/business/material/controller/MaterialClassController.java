package dkd.business.material.controller;



import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import dkd.business.material.domain.MaterialClass;
import dkd.business.material.service.MaterialClassService;
import dkd.paltform.base.controller.BaseController;

@Controller
@RequestMapping(value = "/class")
public class MaterialClassController extends BaseController{
	@Autowired
	private MaterialClassService materialClassService;
	@RequestMapping(value = "/findNextClass.do")
	@ResponseBody
	public String findNextClass(String parentId) {
		return materialClassService.findNextClass(parentId);
	}
	@RequestMapping(value = "/save.do")
	@ResponseBody
	public String addClass(@RequestBody MaterialClass materialClass) {
		materialClass = materialClassService.savaClass(materialClass);
		return toWriteSuccess("保存分类成功！",materialClass.getId());
	}
	@RequestMapping(value = "/query.do")
	@ResponseBody
	public String query(HttpServletRequest request) {
		return materialClassService.getScrollData(getParam(request)).toJson();
	}
	@RequestMapping(value = "/findById.do")
	@ResponseBody
	public String findById(String id) {
		return materialClassService.findByIDToJson(id);
	}
	@RequestMapping(value = "/delete.do")
	@ResponseBody
	public String deleteClass(String id) {
		materialClassService.deleteClass(id);
		return toWriteSuccess("删除分类成功！");
	}
}
