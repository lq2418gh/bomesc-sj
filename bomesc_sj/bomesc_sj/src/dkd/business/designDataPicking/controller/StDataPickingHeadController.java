package dkd.business.designDataPicking.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import dkd.business.designDataManager.service.StDataTrackSuppService;
import dkd.business.designDataPicking.domain.StDataPickingHead;
import dkd.business.designDataPicking.service.StDataPickingHeadService;
import dkd.paltform.base.QueryResult;
import dkd.paltform.base.controller.BaseController;
import dkd.paltform.util.StringUtils;
import dkd.paltform.util.json.JSONUtil;
@Controller
@RequestMapping(value = "/stDataPicking")
public class StDataPickingHeadController extends BaseController{

	@Autowired
	private StDataPickingHeadService stDataPickingHeadService;
	@Autowired
	private StDataTrackSuppService stDataTrackSuppService;
	
	/**
	 * @date 2018年1月8日
	 * @param request
	 * @return
	 * 描述：查询方法
	 */
	@RequestMapping(value="/query.do")
	@ResponseBody
	public String query(HttpServletRequest request) {
		QueryResult<StDataPickingHead> scrollData = stDataPickingHeadService.getScrollData(StDataPickingHead.class, getParam(request));
		return scrollData.toJson();
	}
	
	@RequestMapping(value="/toView.do")
	public String findView(String id,ModelMap model) {
		model.put("id", id);
		return "business/designDataManager/stDataPicking/stDataPickingView";
	}
	
	@RequestMapping(value="/view.do")
	@ResponseBody
	public String view(String id,ModelMap model) {
		StDataPickingHead findByID = stDataPickingHeadService.findByID(id);
		return findByID.toJson();
	}
	
	@RequestMapping(value="/toEdit.do")
	public String findEdit(String id,ModelMap model) {
		model.put("id", id);
		return "business/designDataManager/stDataPicking/stDataPickingEdit";
	}
	
	@RequestMapping(value="/edit.do")
	@ResponseBody
	public String edit(String id,ModelMap model) {
		StDataPickingHead stDataPickingHead = stDataPickingHeadService.findByID(id);
		stDataPickingHead.toJson();
		return stDataPickingHead.toJson();
	}
	/**
	 * @date 2018年1月9日
	 * @param ids
	 * @param model
	 * @return
	 * 描述：补料带来的物料信息
	 * //补料数据跟踪表  ,物资分配(此模块有生成领料单)  带数据
		//---------造数据-------------
		//1    物资分配  模块  选中  按图纸选中  明细 跳到领料单页面  ,填写领料单表头数据  进行保存
		//2 如果 在物资分配   所选 明细有母材编号  -->带 此母材编号下的所有数据  进行保存
	 */
	@RequestMapping(value="/editBySupp.do")
	@ResponseBody
	public String editBySupp(String ids,ModelMap model) {
		Set<Map<String, Object>> set=new HashSet<Map<String, Object>>();      
		//1查询出补料数据跟踪表的数据
		List<Map<String, Object>> findPickingList = stDataTrackSuppService.findPickingList(ids);
		//判断  findPickingList明细有没有母材编号，有的话 -->查询出 这个母材编号相关的所有数据（1除去本条数据  或者2 去重）
		String bulk_material_no="";
		for (Map<String, Object> map : findPickingList) {
			bulk_material_no+="'"+map.get("bulk_material_no")+"',";
		}
		if (StringUtils.isNotEmpty(bulk_material_no)) {
			String bmoArry = bulk_material_no.substring(0, bulk_material_no.length()-1);
			List<Map<String, Object>> findElsePickingList =stDataTrackSuppService.findElseSuppByBMO(bmoArry);
			if (findElsePickingList.size()>bmoArry.split(",").length) {
				set.addAll(findElsePickingList);
				set.addAll(findPickingList);
				return JSONUtil.getJsonByEntity(set);
			}
		}
		return JSONUtil.getJsonByEntity(findPickingList);
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
	public String save(@RequestBody StDataPickingHead stDataPickingHead,ModelMap model){
		stDataPickingHeadService.saveStDataPicking(stDataPickingHead);
		return toWriteSuccess(stDataPickingHead.getId());
	}
	
	
	@RequestMapping("/delete.do")
	@ResponseBody
	public String delete(String id) {
		stDataPickingHeadService.delete(id);
		return toWriteSuccess("删除成功!");
	}
	/**
	 * @date 2018年1月12日
	 * @param id
	 * @return
	 * 描述：提交
	 */
	@RequestMapping("/submit.do")
	@ResponseBody
	public String submit(String pickingNo,String job_no) {
		stDataPickingHeadService.submit(pickingNo,job_no);
		return toWriteSuccess("提交成功!");
	}
}
