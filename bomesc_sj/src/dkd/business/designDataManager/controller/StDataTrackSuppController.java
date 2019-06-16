package dkd.business.designDataManager.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import dkd.business.designDataManager.domain.StDataTrackSupp;
import dkd.business.designDataManager.service.StDataTrackSuppService;
import dkd.paltform.base.controller.BaseController;
import dkd.paltform.fileinfo.domain.FileInfo;
@Controller
@RequestMapping(value = "/stDataTrackSupp")
public class StDataTrackSuppController  extends BaseController{
 
	@Autowired
	private StDataTrackSuppService stDataTrackSuppService;
	
	/**
	 * @date 2017年12月15日
	 * @param request
	 * @return
	 * 描述： 查询方法
	 */
	@RequestMapping(value="/query.do")
	@ResponseBody
	public String fedBatchDataTrack(HttpServletRequest request) {
		String str = stDataTrackSuppService.getScrollData(getParam(request),getCurrentUser(request.getSession())).toJson();
		return str;
	}
	/**
	 * @date 2017年12月15日
	 * @param major
	 * @param beanName
	 * @param model
	 * @return
	 * 描述：列表显示设置功能
	 */
	@RequestMapping(value="/chooseColumnView.do")
	public String chooseColumnView(String major,String beanName,ModelMap model) {
		model.put("beanName", beanName);
		model.put("major", major);
		return "business/designDataManager/stDataTrackTable/chooseColumnView";
	}
	/**
	 * @date 2017年12月15日
	 * @param id
	 * @param model
	 * @return
	 * 描述：跳转编辑页
	 */
	@RequestMapping(value="/toEdit.do")
	public String toEdit(String id,ModelMap model) {
		model.put("id", id);
		return "business/designDataManager/stDataTrackSupp/stDataTrackSuppEdit";
	}
	/**
	 * @date 2017年12月15日
	 * @param fileInfo
	 * @param model
	 * @return
	 * @throws Exception
	 * 描述：导入
	 */
	@RequestMapping(value="/importStDTS.do")
	@ResponseBody
	public String save(FileInfo fileInfo,ModelMap model) throws Exception {
		return toWriteSuccess("导入成功！",stDataTrackSuppService.importStDTS(fileInfo));//提示信息，同时返回相应的mtoNo,这里临时存储在id字段名里
	}
	
	/**
	 * @date 2017年12月15日
	 * @param id
	 * @return
	 * 描述：根据id查询对象
	 */
	@RequestMapping(value="/findStDTS.do")
	@ResponseBody 
	public String findStDTS(String id) {
		StDataTrackSupp findByID = stDataTrackSuppService.findByID(id);
		return findByID.toJson();
	}
	
	@RequestMapping(value="/updateStDTS.do")
	@ResponseBody
	public String updateStDTS(@RequestBody StDataTrackSupp stDataTrackSupp) {
		stDataTrackSuppService.update(stDataTrackSupp);
		return toWriteSuccess("保存成功");
	}
	/**
	 * @date 2018年1月9日
	 * @param idArry
	 * @param modelMap
	 * @return
	 * 描述：
	 */
	@RequestMapping(value="/makePicking.do")
	public String makePicking(String idArry,ModelMap modelMap){
		modelMap.put("ids", idArry);
		return "business/designDataManager/stDataPicking/stDataPickingEdit";
	}
}
