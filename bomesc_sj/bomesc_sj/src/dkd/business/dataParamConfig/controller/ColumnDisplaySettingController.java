package dkd.business.dataParamConfig.controller;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import dkd.business.dataParamConfig.domain.ColumnDisplaySetting;
import dkd.business.dataParamConfig.service.ColumnDisplaySettingService;
import dkd.paltform.base.controller.BaseController;
import dkd.paltform.util.json.request.RequestJsonNode;

@Controller
@RequestMapping(value = "/columnDisplaySet")
public class ColumnDisplaySettingController extends BaseController {
	@Autowired
	private ColumnDisplaySettingService columnDisplaySettingService;
	
	@RequestMapping(value="/query.do")
	@ResponseBody
	public String query(HttpServletRequest request){
		String str = columnDisplaySettingService.getScrollData(getParam(request)).toJson();
		return str;
	}
	
	@RequestMapping(value = "/findById.do")
	@ResponseBody
	public String findById(String id) {
		String str =  columnDisplaySettingService.findByFieldToJson("id", id);
		return str;
	}
	
	@RequestMapping(value="/edit.do")
	public String edit(String id,ModelMap map){
		map.put("id", id);
		return "business/dataParamConfig/columnDisplaySet/edit";
	}
	
	@RequestMapping(value="/edit_load_data.do")
	@ResponseBody
	public String edit_load_data(String id){
		String str =  columnDisplaySettingService.findByFieldToJson("id", id);
		return str;
	}
		
	@RequestMapping(value="/view.do")
	public String view(String id,String job_no,ModelMap model) {
		if(StringUtils.isNotEmpty(job_no)){
			model.put("id", columnDisplaySettingService.findByField("job_no", job_no).getId());
		}else{
			model.put("id", id);
		}
		return "business/dataParamConfig/columnDisplaySet/view";
	}
	
	@RequestMapping(value = "/view_load_data.do")
	@ResponseBody
	public String view_load_data(String id) {
		return columnDisplaySettingService.findByFieldToJson("id", id);
	}
	
	@RequestMapping(value="/save.do")
	@ResponseBody
	public String saveData(@RequestBody ColumnDisplaySetting columnDisplaySetting){
		columnDisplaySettingService.saveData(columnDisplaySetting);
		return toWriteSuccess("保存成功！");
	}

	@RequestMapping("/delete.do")
	@ResponseBody
	public String delete(String id){
		columnDisplaySettingService.deleteData(id);
		return toWriteSuccess("删除项目成功！");
	}
	//通过专业编号查询补充列名称
	@RequestMapping("/getColumnsByMajor.do")
	public void getColumnsByMajor(@RequestJsonNode("jobNo")String jobNo,@RequestJsonNode("code") String code,HttpServletResponse response) throws IOException{
		Map<String,Object> map = columnDisplaySettingService.findByMajor(jobNo,code);
		response.getWriter().print(JSONObject.fromObject(map));
	}
	
}
