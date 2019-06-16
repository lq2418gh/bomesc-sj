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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import dkd.business.project.domain.WorkHours;
import dkd.business.project.service.WorkHoursService;
import dkd.paltform.base.controller.BaseController;
import dkd.paltform.util.json.request.RequestJsonNode;


@Controller
@RequestMapping(value = "/workHours")
public class WorkHoursController extends BaseController{
	@Autowired
	private WorkHoursService workHoursService;
	/**
	 * 查询
	 * @date 2017年11月15日
	 * @param request
	 * @return
	 * 描述：
	 */
	@RequestMapping(value="/query.do")
	@ResponseBody
	public String query(HttpServletRequest request){
		String str = workHoursService.getScrollData(getParam(request)).toJson();
		return str;
	}
	/**
	 * 更新回显
	 * @date 2017年11月15日
	 * @param model
	 * @param request
	 * @param id
	 * @return
	 * 描述：
	 */
	@RequestMapping(value="/edit.do")
	@ResponseBody
	public String edit(ModelMap model,HttpServletRequest request,String id){
		return workHoursService.findByIDToJson(id);
	}
	/**
	 * 保存方法
	 * @date 2017年11月15日
	 * @param workHours_form
	 * @param request
	 * @return
	 * 描述：
	 */
	@RequestMapping(value="/save.do",method=RequestMethod.POST,consumes="application/json")
	@ResponseBody
	public String save(@RequestBody WorkHours  workHours_form,HttpServletRequest request){
		workHoursService.saveWorkHours(workHours_form);
		return toWriteSuccess("保存工时成功!");
	}
	/**
	 * 删除方法
	 * @date 2017年11月15日
	 * @author wzm
	 * @param id
	 * @return
	 * 描述：
	 */
	@RequestMapping(value="/delete.do")
	@ResponseBody
	public String delete(String id) {
		workHoursService.delete(id);
		return toWriteSuccess("删除成功");
	}
	/**
	 * @date 2017年12月1日
	 * @param response
	 * @param statistical_month_edit
	 * @param project
	 * @param major
	 * @param id
	 * @throws IOException
	 * 描述： 根据项目查询
	 */
	@RequestMapping(value="/findCNH.do")
	@ResponseBody
	public String findCNH(HttpServletResponse response ,@RequestJsonNode("statistical_month_edit") String statistical_month_edit,@RequestJsonNode("project") String project,@RequestJsonNode("major") String major,@RequestJsonNode("id")String id) throws IOException {
		Map<String,Object> map2=workHoursService.findSum(statistical_month_edit,project,major,id);
		if ( map2.get("actual_man_hour")!=null) {
			String sum =  map2.get("actual_man_hour").toString();
			return sum;
		}else{
			return "0";
		}
	}
	/**
	 * @date 2017年12月1日
	 * @param response
	 * @param statistical_month_edit
	 * @param project
	 * @param major
	 * @param id
	 * 描述：
	 * @throws IOException 
	 */
	@RequestMapping(value="/checkIfExist.do")
	@ResponseBody
	public String checkIfExist(HttpServletResponse response ,@RequestJsonNode("statistical_month_edit") String statistical_month_edit,@RequestJsonNode("project") String project,@RequestJsonNode("major") String major,@RequestJsonNode("id")String id) throws IOException{
		WorkHours findWH = workHoursService.findWH(statistical_month_edit,project,major,id);
		if (findWH!=null) {
			return toWriteSuccess();
		}else{
			return toWriteFail();
		}
	}


}
