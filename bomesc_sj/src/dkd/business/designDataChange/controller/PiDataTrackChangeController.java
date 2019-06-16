package dkd.business.designDataChange.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import dkd.business.designDataChange.service.PiDataTrackChangeService;
import dkd.business.designDataManager.domain.PiDataTrack;
import dkd.paltform.authority.domain.User;
import dkd.paltform.base.controller.BaseController;
import dkd.paltform.util.json.JSONUtil;

@Controller
@RequestMapping(value = "/piDesignDataChange")
public class PiDataTrackChangeController extends BaseController{
	@Autowired
	private PiDataTrackChangeService piDataTrackChangeService;
	/**
	 * 描述：查询
	 */
	@RequestMapping("/query.do")
	@ResponseBody
	public String query(HttpServletRequest request){
		return piDataTrackChangeService.getScrollDataByUser(getParam(request),getCurrentUser(request.getSession())).toJson();
	}
	//变更信息图纸汇总页面
	@RequestMapping("/drawChangeCollect.do")
	public String drawChangeCollect(String professional,ModelMap model){
		model.put("professional", professional);
		return "business/designDataManager/piDataTrackChange/piDataTrackChangeCollect";
	}
	/**
	 * 描述：查询制定日期的变更记录，按图纸维度
	 */
	//根据专业查询数据跟踪表变更记录
	@RequestMapping("/findByProfessional.do")
	@ResponseBody
	public String findByProfessional(String update_date,String project_name){
		return JSONUtil.getJsonByEntity(piDataTrackChangeService.findByProfessional(update_date,project_name));
	}
	/**
	 * 描述：按照指定参数分组查询所有的变更记录
	 */
	@RequestMapping("/findChangeDetails.do")
	@ResponseBody
	public String findChangeDetails(String project_name,String shop_draw_no,String module_no,String update_date){
		return JSONUtil.getJsonByEntity(piDataTrackChangeService.findChangeDetails(project_name,shop_draw_no,module_no,update_date));
	}
	/**
	 * 描述：批量更新变更记录变更原因
	 */
	@RequestMapping("/saveReason.do")
	@ResponseBody
	public String saveReason(@RequestBody PiDataTrack piDataTrack,HttpServletRequest request){
		User currentUser = getCurrentUser(request.getSession());
		return toWriteSuccess(piDataTrackChangeService.saveReason(piDataTrack, currentUser));
	}
}
