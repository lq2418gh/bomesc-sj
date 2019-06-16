package dkd.business.dataParamConfig.controller;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import dkd.business.dataParamConfig.domain.AllocationPriorityDetail;
import dkd.business.dataParamConfig.domain.AllocationPriorityHead;
import dkd.business.dataParamConfig.service.AllocationPriorityDetailService;
import dkd.business.dataParamConfig.service.AllocationPriorityHeadService;
import dkd.paltform.base.QueryResult;
import dkd.paltform.base.controller.BaseController;
import dkd.paltform.dictionary.domain.Dictionary;
import dkd.paltform.util.json.request.RequestJsonNode;

@Controller
@RequestMapping(value = "/allocationPriority")
public class AllocationPriorityController extends BaseController {
	@Autowired
	private AllocationPriorityHeadService allocationPriorityHeadService;
	@Autowired
	private AllocationPriorityDetailService allocationPriorityDetailService;
	/**
	 * 查询方法
	 * @date 2017年11月21日
	 * @param request
	 * @return
	 * 描述：
	 */
	@ResponseBody
	@RequestMapping(value="/query.do")
	public String query(HttpServletRequest request){
		QueryResult<AllocationPriorityHead> scrollData = allocationPriorityHeadService.getScrollData(AllocationPriorityHead.class,getParam(request));
		List<AllocationPriorityHead> list = scrollData.getRows();
		for (AllocationPriorityHead allocationPriorityHead : list) {
			 Collections.sort(allocationPriorityHead.getAllocationPriorityDetail(), new Comparator<AllocationPriorityDetail>() {
				 public int compare(AllocationPriorityDetail arg0, AllocationPriorityDetail arg1) {
		                return arg0.getSort_no().compareTo(arg1.getSort_no());
		            }
			});
		}
		return scrollData.toJson();
	}
	
	/**
	 * 保存方法
	 * @date 2017年11月22日
	 * @param allocationPriorityHead
	 * @return
	 * 描述：
	 */
	@RequestMapping("/save.do")
	@ResponseBody
	public String save(@RequestBody AllocationPriorityHead allocationPriorityHead,ModelMap model){
		allocationPriorityHeadService.saveallocationPriority(allocationPriorityHead);
		return toWriteSuccess("添加成功");
	}
	/**
	 * 修改按钮跳转编辑页
	 */
	@RequestMapping(value="/toAdd.do")
	public String toAdd() {
		return "business/dataParamConfig/allocationPriority/allocationPriorityEdit";
	}
	@RequestMapping(value="/toEdit.do")
	public String toEdit(String id,ModelMap model) {
		model.put("id", id); 
		return "business/dataParamConfig/allocationPriority/allocationPriorityEdit";
	}
	/**
	 * 编辑回显
	 * @date 2017年11月23日
	 * @return
	 * 描述：
	 */
	@RequestMapping(value="/edit_load_data.do")
	@ResponseBody
	public String edit_load_data(String id){
		AllocationPriorityHead head = allocationPriorityHeadService.findByID(id);
		List<AllocationPriorityDetail> list = head.getAllocationPriorityDetail();
		Collections.sort(list,new Comparator<AllocationPriorityDetail>(){
            public int compare(AllocationPriorityDetail arg0, AllocationPriorityDetail arg1) {
                return arg0.getSort_no().compareTo(arg1.getSort_no());
            }
        });
		// head.setAllocationPriorityDetail(list);
		String string =  head.toJson();
		return string;
	}
	/**
	 * @date 2017年11月23日
	 * @return
	 * 描述：删除
	 */
	@RequestMapping(value="/delete.do")
	@ResponseBody
	public String delete(String id){
		allocationPriorityHeadService.delete(id);
		return toWriteSuccess("删除成功!");
	}
	/**
	 * @date 2017年11月27日
	 * @param id
	 * @param model
	 * @return
	 * 描述：查看页面
	 */
	@RequestMapping(value="/view.do")
	public String view(String id,ModelMap model) {
		model.put("id", id);
		return "business/dataParamConfig/allocationPriority/allocationPriorityView";
	}
	/**
	 * @date 2017年11月23日
	 * @return
	 * 描述：校验
	 */
	@RequestMapping(value="/checkIsData.do")
	@ResponseBody
	public String checkIsData(@RequestJsonNode("id")String id,@RequestJsonNode("project")String project,@RequestJsonNode("major")String major){
		List<AllocationPriorityHead> checkIsData = allocationPriorityHeadService.checkIsData(id,project,major);
		if (checkIsData.size()>0) {
			return toWriteSuccess();
		}
		return toWriteFail();
	}
	/**
	 * @date 2017年11月27日
	 * @param id
	 * @return
	 * 描述：通过明细id找到字典：子表里的（sort_column：id）
	 */
	@ResponseBody
	@RequestMapping(value="/findDicId.do")
	public String findDicId(String id) {
		AllocationPriorityDetail findByID = allocationPriorityDetailService.findByID(id);
		Dictionary sort_column = findByID.getSort_column();
		return sort_column.toJson();
	}
	
}
