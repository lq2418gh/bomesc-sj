package dkd.business.designDataManager.controller;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import dkd.business.designDataManager.service.DataTrackColumnsService;
import dkd.business.designDataManager.service.StDataTrackTableService;
import dkd.paltform.base.controller.BaseController;
import dkd.paltform.util.Common;
import dkd.paltform.util.json.JSONUtil;
import dkd.paltform.util.json.request.RequestJsonNode;

@Controller
@RequestMapping(value = "/stDesignDataManager")
public class StDataTrackTableController extends BaseController{
	
	@Autowired
	private StDataTrackTableService dataTrackTableService;
	
	@Autowired
	private DataTrackColumnsService dataTrackColumnService;
	
	@RequestMapping("/query.do")
	@ResponseBody
	public String query(HttpServletRequest request){
		return dataTrackTableService.getScrollDataByUser(getParam(request),getCurrentUser(request.getSession())).toJson();
	}
	
	@RequestMapping("/chooseColumnView.do")
	public String chooseColumnView(String major,String beanName,ModelMap model){
		model.put("major", major);
		model.put("beanName", beanName);
		return "business/designDataManager/stDataTrackTable/chooseColumnView";
	}
	
	//根据用户设置显示列（数据跟踪表）
	@RequestMapping("/save.do")
	@ResponseBody
	public String save(@RequestJsonNode("values")String values,@RequestJsonNode("major")String major,HttpServletRequest request){
		//此处写实体持久化方法
		dataTrackColumnService.create(values,major,getCurrentUser(request.getSession()));
		return toWriteSuccess("保存成功!");
	}
	
	//返回查看历史记录页面
	@RequestMapping("/viewOldRecord.do")
	public String viewOldRecord(String part_no,String jobNo,String id,String change_size,String update_date,ModelMap model){
		model.put("part_no", part_no);
		model.put("jobNo", jobNo);
		model.put("id", id);
		model.put("change_size", change_size);
		model.put("update_date", update_date);
		return "business/designDataManager/stDataTrackTable/viewOldRecord";
	}
	
	//导出功能
	@RequestMapping("/exportData.do")
	public void exportData(HttpServletRequest request,HttpServletResponse response) throws InvalidFormatException, IOException{
		dataTrackTableService.exportData(getParam(request),getCurrentUser(request.getSession()),request,response);
	}
	
	//获取显示列
	@RequestMapping("/getColumns.do")
	@ResponseBody
	public String getColumns(String professional,HttpServletRequest request){
		return JSONUtil.getJsonByEntity(dataTrackTableService.getColumns(professional,getCurrentUser(request.getSession())));
	}
	//图纸升版标记页面
	@RequestMapping("/drawUpgradeMark.do")
	public String drawUpgradeMark(){
		return "business/designDataManager/stDataTrackTable/drawUpgradeMark";
	}
	//查询需标记的数据
	@RequestMapping("/queryForMark.do")
	@ResponseBody
	public String queryForMark(HttpServletRequest request){
		return dataTrackTableService.queryForMark(getParam(request),getCurrentUser(request.getSession())).toJson();
	}
	//变更标记保存方法
	@RequestMapping("/saveState.do")
	@ResponseBody
	public String saveSatate(@RequestJsonNode("ids")String ids,@RequestJsonNode("state")String state,HttpServletRequest request){
		return toWriteSuccess(dataTrackTableService.saveState(ids,state,getCurrentUser(request.getSession())));
	}
	/**
	 * @date 2018年1月17日
	 * 描述：得到博迈科采办项目里面  料单 模块的料单明细   提交之后的数据
	 */
	@RequestMapping("/getBomescCbMtoDetail.do")
	public void getBomescCbMtoDetail(HttpServletRequest request){
		Map<String, String> paramDecrypt = Common.getParamDecrypt(request);
		System.out.println("+++++++++++++++++++++++++++"+paramDecrypt);
		dataTrackTableService.getBomescCbMtoDetail(paramDecrypt);
	}
}
