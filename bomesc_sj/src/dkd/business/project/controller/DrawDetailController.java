package dkd.business.project.controller;

import java.io.IOException;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import dkd.business.project.domain.DrawDetailHead;
import dkd.business.project.service.DrawDetailService;
import dkd.business.project.service.ProjectService;
import dkd.paltform.base.controller.BaseController;
import dkd.paltform.util.json.request.RequestJsonNode;
import net.sf.json.JSONObject;

@Controller
@RequestMapping(value = "/drawDetail")
public class DrawDetailController extends BaseController{

	@Autowired
	private DrawDetailService drawDetailService;
	
	@Autowired
	private ProjectService projectService;
	
	@RequestMapping(value="/query.do")
	@ResponseBody
	public String query(HttpServletRequest request){
		String str = drawDetailService.getScrollData(getParam(request)).toJson();
		return str;
	}
	
	@RequestMapping("/edit.do")
	public String edit(String id,ModelMap model){
		model.put("id", id);
		return "business/project/drawDetail/drawDetailEdit";
	}
	
	@RequestMapping("/save.do")
	@ResponseBody
	public String save(@RequestBody DrawDetailHead drawDetailHead){
		String id = drawDetailService.saveDrawDetail(drawDetailHead);
		return toWriteSuccess("保存成功！",id);
	}
	
	@RequestMapping(value = "/findByNo.do")
	@ResponseBody
	public String findByNo(String id) {
		String str = drawDetailService.findByFieldToJson("id", id);
		return str;
	}
	
	@RequestMapping("/view.do")
	public String view(String id,ModelMap model){
		model.put("id", id);
		return "business/project/drawDetail/drawDetailView";
	}
	
	@RequestMapping("/delete.do")
	@ResponseBody
	public String delete(String id){
		drawDetailService.delete(id);
		return toWriteSuccess("删除成功!");
	}
	
	//获取上一个月的完成情况
	@RequestMapping("/getPre.do")
	public void getPre(HttpServletRequest request,@RequestJsonNode("project")String project,@RequestJsonNode("major")String major,
			@RequestJsonNode("month")String month,@RequestJsonNode("drawType")String drawType,HttpServletResponse response) throws IOException{
		Map<String, Object> map=  drawDetailService.find(project,major,month,drawType,request,response);
		response.getWriter().print(JSONObject.fromObject(map));
	}
	
	@RequestMapping("/checkValidate.do")
	public void checkValidate(@RequestJsonNode("project")String project,@RequestJsonNode("major")String major,@RequestJsonNode("month")String month,
			@RequestJsonNode("id")String id,HttpServletResponse response) throws IOException{
		Boolean flag = drawDetailService.checkValidate(project,major,month,id);
		response.getWriter().println(flag);
	}
	
	//项目专业下的图纸总量
	@RequestMapping("/getTotalNum.do")
	public void getTotalNum(@RequestJsonNode("project")String project,@RequestJsonNode("major")String major,HttpServletResponse response,HttpServletRequest request) throws IOException{
		String totalQty = projectService.showDrawQty(request, project, major, response,"qty");
		response.getWriter().print(totalQty);
	}
	//获取之前月的图纸完成情况（按照月份取和）
	@RequestMapping("/getTotalPre.do")
	public void getTotalPre(HttpServletRequest request,@RequestJsonNode("project")String project,@RequestJsonNode("major")String major,
			@RequestJsonNode("month")String month,@RequestJsonNode("drawType")String drawType,HttpServletResponse response) throws IOException{
		Map<String, Object> map=  drawDetailService.getTotalPre(project,major,month,drawType,request,response);
		response.getWriter().print(JSONObject.fromObject(map));
	}
}
