package dkd.business.project.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import dkd.business.project.domain.Draw;
import dkd.business.project.service.DrawService;
import dkd.paltform.base.controller.BaseController;
import dkd.paltform.dictionary.service.DictionaryService;
import dkd.paltform.util.json.request.RequestJsonNode;

@Controller
@RequestMapping(value = "/draw")
public class DrawController extends BaseController{

	@Autowired
	private DrawService drawService;
	@Autowired
	private DictionaryService dictionaryService;
	
	@RequestMapping(value="/query.do")
	@ResponseBody
	public String query(HttpServletRequest request){
		String str = drawService.getScrollData(getParam(request)).toJson();
		return str;
	}
	
	@RequestMapping(value="/edit.do")
	@ResponseBody
	public String edit(ModelMap model,HttpServletRequest request,String id){
		String str =  drawService.findByIDToJson(id);
		return str;
	}
	@RequestMapping(value="/save.do",method=RequestMethod.POST,consumes="application/json")
	@ResponseBody
	public String save(@RequestBody Draw  draw_form){
		String id = drawService.saveDraw(draw_form);
		return toWriteSuccess("保存图纸成功!",id);
	}
	
	@RequestMapping("/findPre.do")
	@ResponseBody
	public void findPre(HttpServletRequest request,HttpServletResponse response,@RequestJsonNode("month") String month
			,@RequestJsonNode("project") String project,@RequestJsonNode("major") String major,@RequestJsonNode("id") String id) throws IOException{
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("id", id);
		map.put("statistical_month", month);
		map.put("project", project);
		map.put("major", dictionaryService.findByID(major));
		//校验是否已录入
		Draw drawOld = drawService.findPreOld(map);
		if(null!=drawOld){
			response.getWriter().print(true);
			return;
		}
		map.put("major", major);
		Map<String, Object> mapObject = drawService.findPre(map);
		if(mapObject!=null){
			response.reset();
			response.setContentType("text/html;charset=UTF-8");
			response.getWriter().print(JSONObject.fromObject(mapObject));
		}
	}
	
	@RequestMapping("/delete.do")
	@ResponseBody
	public String delete(String id,HttpServletResponse response) throws IOException{
		drawService.delete(id);
		return toWriteSuccess("删除成功！");
	}
	
	@RequestMapping("/findTotalPre.do")
	@ResponseBody
	public void findTotalPre(HttpServletRequest request,HttpServletResponse response,@RequestJsonNode("month") String month
			,@RequestJsonNode("project") String project,@RequestJsonNode("major") String major,@RequestJsonNode("id") String id) throws IOException{
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("id", id);
		map.put("statistical_month", month);
		map.put("project", project);
		map.put("major", major);
		Map<String, Object> mapObject = drawService.findTotalPre(map);
		response.reset();
		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().print(JSONObject.fromObject(mapObject));
	}
}
