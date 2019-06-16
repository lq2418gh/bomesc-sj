package dkd.business.designDataChange.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import dkd.business.designDataChange.service.StDataTrackChangeService;
import dkd.business.designDataManager.domain.StDataTrack;
import dkd.paltform.authority.domain.User;
import dkd.paltform.base.controller.BaseController;
import dkd.paltform.util.json.JSONUtil;
import dkd.paltform.util.json.request.RequestJsonNode;

@Controller
@RequestMapping(value = "/designDataChange")
public class StDataTrackChangeController extends BaseController{
	
	@Autowired
	private StDataTrackChangeService dataTrackChangeService;
	
	@RequestMapping("/query.do")
	@ResponseBody
	public String query(HttpServletRequest request){
		return dataTrackChangeService.getScrollDataByUser(getParam(request),getCurrentUser(request.getSession())).toJson();
	}
	
	//变更信息图纸汇总页面
	@RequestMapping("/drawChangeCollect.do")
	public String drawChangeCollect(String professional,String module,ModelMap model){
		model.put("professional", professional);
		model.put("module", module);
		return "business/designDataManager/stDataTrackChange/dataTrackChangeCollect";
	}
	//变更信息图纸汇总页面
	@RequestMapping("/drawRemindCollect.do")
	public String drawRemindCollect(String professional,String module,ModelMap model){
		model.put("professional", professional);
		model.put("module", module);
		return "business/designDataManager/stDataTrackChange/dataTrackRemindCollect";
	}
	/**
	 * @date 2017年12月26日
	 * @author gaoxp
	 * @param update_date
	 * @param project_name
	 * @return
	 * 描述：查询制定日期的变更记录，按图纸维度
	 */
	//根据专业查询数据跟踪表变更记录
	@RequestMapping("/findByProfessional.do")
	@ResponseBody
	public String findByProfessional(String update_date,String project_name,String module){
		return JSONUtil.getJsonByEntity(dataTrackChangeService.findByProfessional(update_date,project_name,module));
	}
	/**
	 * @date 2017年12月26日
	 * @author gaoxp
	 * @param project_name
	 * @param shop_draw_no
	 * @param module_name
	 * @param level_no
	 * @param update_date
	 * @return
	 * 描述：按照指定参数分组查询所有的变更记录
	 */
	@RequestMapping("/findChangeDetails.do")
	@ResponseBody
	public String findChangeDetails(String project_name,String shop_draw_no,String module_name,String level_no,String update_date,
			String mtoNo,String mtoRowNo,String module){
		return JSONUtil.getJsonByEntity(dataTrackChangeService.findChangeDetails(project_name,shop_draw_no,module_name,level_no,
				update_date,mtoNo,mtoRowNo,module));
	}
	/**
	 * @date 2017年12月26日
	 * @author gaoxp
	 * @param stDataTrack
	 * @param request
	 * @return
	 * 描述：批量更新变更记录变更原因
	 */
	@RequestMapping("/saveReason.do")
	@ResponseBody
	public String saveReason(@RequestBody StDataTrack stDataTrack,HttpServletRequest request){
		User currentUser = getCurrentUser(request.getSession());
		return toWriteSuccess(dataTrackChangeService.saveReason(stDataTrack, currentUser));
	}
	@RequestMapping("/saveState.do")
	@ResponseBody
	public String saveState(@RequestBody StDataTrack stDataTrack,HttpServletRequest request){
		User currentUser = getCurrentUser(request.getSession());
		return toWriteSuccess(dataTrackChangeService.saveState(stDataTrack, currentUser));
	}
	//状态确认
	@RequestMapping("/stateConfirm.do")
	@ResponseBody
	public String stateConfirm(@RequestJsonNode("id")String id,HttpServletRequest request){
		String result = dataTrackChangeService.stateConfirm(id,getCurrentUser(request.getSession()));
		return toWriteSuccess(result);
	}
	//查询所有专业的待处理确认的单据（升版增加，升版作废）
	@RequestMapping("/findTotalTask.do")
	public void findTotalTask(HttpServletResponse response) throws IOException{
		String result = JSONUtil.getJsonByEntity(dataTrackChangeService.findTotalTask());
		response.getWriter().print(result);
	}
}
