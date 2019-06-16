package dkd.paltform.flowset.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import dkd.paltform.base.QueryResult;
import dkd.paltform.base.controller.BaseController;
import dkd.paltform.flowset.domain.Flowset;
import dkd.paltform.flowset.domain.FlowsetDetail;
import dkd.paltform.flowset.service.FlowsetDetailService;
import dkd.paltform.flowset.service.FlowsetService;
import dkd.paltform.flowset.service.ProcessRecordRoleService;

@Controller
@RequestMapping(value = "/flowset")
public class FlowsetController extends BaseController{
	@Autowired
	private FlowsetService flowsetService;
	@Autowired
	private FlowsetDetailService flowsetDetailService;
	@Autowired
	private ProcessRecordRoleService processRecordRoleService;
	
	@RequestMapping(value = "/query.do")
	@ResponseBody
	public String query(HttpServletRequest request) {
		return flowsetService.getScrollData(getParam(request)).toJson();
	}
	@RequestMapping(value = "/save.do")
	@ResponseBody
	public String save(@RequestBody Flowset flowset) {
		flowsetService.save(flowset);
		return toWriteSuccess("保存审批流程成功！",flowset.getId());
	}
	@RequestMapping(value = "/findById.do")
	@ResponseBody
	public String findById(String id) {
		return flowsetService.findByIDToJson(id);
	}
	@RequestMapping(value = "/findFlowsetDetail.do")
	@ResponseBody
	public String findFlowsetDetail(String id) {
		Flowset flowset = flowsetService.findByID(id);
		if(flowset != null && flowset.getFlowset_details() != null && !flowset.getFlowset_details().isEmpty()){
			return new QueryResult<FlowsetDetail>(flowset.getFlowset_details(),new Long(flowset.getFlowset_details().size())).toJson();
		}else{
			return new QueryResult<FlowsetDetail>().toJson();			
		}
	}
	@RequestMapping(value = "/saveDetail.do")
	@ResponseBody
	public String saveDetail(@RequestBody FlowsetDetail flowsetDetail) {
		flowsetDetailService.saveDetail(flowsetDetail);
		return toWriteSuccess("保存审批节点成功！",flowsetDetail.getId());
	}
	@RequestMapping("/delete.do")
	@ResponseBody
	public String delete(String id) {
		flowsetService.delete(id);
		return toWriteSuccess("删除流程成功！");
	}
	@RequestMapping("/disableFlowsetDetail.do")
	@ResponseBody
	public String disableFlowsetDetail(String id) {
		flowsetDetailService.setValidate(id,false);
		return toWriteSuccess("禁用流程节点成功！");
	}
	@RequestMapping("/enableFlowsetDetail.do")
	@ResponseBody
	public String enableFlowsetDetail(String id) {
		flowsetDetailService.setValidate(id,true);
		return toWriteSuccess("启用流程节点成功！");
	}
	@RequestMapping("/deleteFlowsetDetail.do")
	@ResponseBody
	public String deleteFlowsetDetail(String id,String flowsetId) {
		flowsetDetailService.deleteDetail(id,flowsetId);
		return toWriteSuccess("删除流程节点成功！");
	}
	@RequestMapping(value = "/findByDetailId.do")
	@ResponseBody
	public String findByDetailId(String id) {
		return flowsetDetailService.findByIDToJson(id);
	}
	@RequestMapping(value = "/checkBill.do")
	public String checkBill(String work_no,ModelMap model) {
		model.put("work_no", work_no);
		return "system/flowset/checkBill";
	}
	@RequestMapping(value = "/change.do")
	@ResponseBody
	public String change(String work_no,boolean is_pass,String check_opinion,ModelMap model) throws Exception {
		String message = flowsetService.change(work_no, is_pass, check_opinion);
		if(StringUtils.isEmpty(message)){
			//审批成功
			return toWriteSuccess("审核成功");
		}else{
			//审批失败
			return toWriteFail(message);
		}
	}
	@RequestMapping(value = "/checkRole.do")
	@ResponseBody
	public String checkRole(String work_no) {
		if(flowsetService.checkRole(work_no)){
			return toWriteSuccess();
		}else{
			return toWriteFail();
		}
	}
	@RequestMapping(value = "/loadCheckHistory.do")
	public String loadCheckHistory(String work_no,ModelMap model) {
		model.put("work_no", work_no);
		return "system/flowset/loadCheckHistory";
	}
	@RequestMapping(value = "/loadCheckHistoryData.do")
	@ResponseBody
	public String loadCheckHistoryData(HttpServletRequest request,ModelMap model) {
		return processRecordRoleService.getScrollData(getParam(request)).toJson();
	}
}
