package dkd.business.project.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import dkd.business.project.domain.Project;
import dkd.business.project.service.ProjectService;
import dkd.paltform.base.controller.BaseController;
import dkd.paltform.util.json.JSONUtil;
import dkd.paltform.util.json.request.RequestJsonNode;
@Controller
@RequestMapping(value = "/project")
public class ProjectController extends BaseController{
	
	@Autowired
	private ProjectService projectService;
	
	@RequestMapping(value = "/show.do")
	@ResponseBody
	public String show(HttpServletRequest request) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> rowMap = new HashMap<String, Object>();
		rowMap.put("value", "");
		rowMap.put("text", "请选择");
		list.add(rowMap);
        for (Map.Entry<String, Map<String,Object>> entry : projectService.getProjectDatas().entrySet()) {
        	rowMap = new HashMap<String, Object>();
        	rowMap.put("value", entry.getValue().get("job_no"));
			rowMap.put("text", entry.getValue().get("project_name"));
			list.add(rowMap); 
        }
        return JSONUtil.getJsonByEntity(list);
	}
	@RequestMapping(value = "/showDrawQty.do")
	@ResponseBody
	public String showDrawQty(HttpServletRequest request,@RequestJsonNode("project")String project,@RequestJsonNode("major") String major,@RequestJsonNode("type")String type,HttpServletResponse response) throws IOException {
		return projectService.showDrawQty(request, project, major, response,type);
	}
	/**
	 * 查询方法
	 * @date 2017年12月19日
	 * @author zhaolw
	 * @param request
	 * @return
	 * 描述：
	 */
	@RequestMapping(value = "/query.do")
	@ResponseBody
	public String query(HttpServletRequest request) {
		return projectService.getScrollData(getParam(request)).toJson();
	}
	/**
	 * @date 2017年12月19日
	 * @author zhaolw
	 * @param id
	 * @param model
	 * @return
	 * 描述：跳转到编辑页面
	 */
	@RequestMapping(value = "/edit.do")
	public String editProject(String id,ModelMap model) {
		model.put("id", id);
		return "business/project/project/projectEdit";
	}
	/**
	 * @author ZHAOLW
	 * @date 2017-12-19
	 * @param jobNo:项目编号
	 * @desc 从缓存中取项目信息
	 */
	@RequestMapping("/findByNo.do")
	@ResponseBody
	public String findByNo(@RequestJsonNode("jobNo")String jobNo){
		return JSONUtil.getJsonByEntity(projectService.getProjectData(jobNo));
	}
	/**
	 * @author ZHAOLW
	 * @date 2017-12-20
	 * @desc 保存信息
	 */
	@RequestMapping("/save.do")
	@ResponseBody
	public String save(@RequestBody Project project){
		projectService.save(project);
		return toWriteSuccess("保存成功!", project.getId());
	}
	/**
	 * @author ZHAOLW
	 * @date 2017-12-20
	 * @desc 返回项目的查看页面
	 */
	@RequestMapping("/view.do")
	public String view(String id,ModelMap model){
		model.put("id", id);
		return "business/project/project/projectView";
	}
	
	@RequestMapping("/findById.do")
	@ResponseBody
	public String findById(String id){
		String str = projectService.findProjectByIDToJson(id);
		return str;
	}
	/**
	 * @date 2017年12月20日
	 * @author zhaolw
	 * @param project
	 */
	@RequestMapping("/deleteProject.do")
	@ResponseBody
	public String deleteProject(String id){
		projectService.delete(id);
		return toWriteSuccess("删除项目成功！");
	}
	/**
	 * @date 2017-12-20
	 * @author ZHAOLW
	 * @param jobNo:项目工作号 id：项目ID
	 * @throws IOException 
	 */
	@RequestMapping("/checkValidate.do")
	public void checkValidate(@RequestJsonNode("jobNo")String jobNo,@RequestJsonNode("id")String id
			,HttpServletResponse response) throws IOException{
		response.getWriter().print(projectService.checkValidate(jobNo,id));;
	}
	
}