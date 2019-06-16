package dkd.business.designDataManager.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.xml.sax.SAXException;

import dkd.business.designDataManager.domain.PiDataTrackSupp;
import dkd.business.designDataManager.service.PiDataTrackSuppService;
import dkd.paltform.base.controller.BaseController;
import dkd.paltform.fileinfo.domain.FileInfo;

@Controller
@RequestMapping(value = "/piDataTrackSupp")
public class PiDataTrackSuppController extends BaseController{

	
	@Autowired
	private PiDataTrackSuppService piDataTrackSuppService; 
	/**
	 * @date 2017年12月26日
	 * @param request
	 * @return
	 * 描述：查询
	 */
	@RequestMapping(value="/query.do")
	@ResponseBody
	public String query(HttpServletRequest request) {
		String str = piDataTrackSuppService.getScrollData(getParam(request),getCurrentUser(request.getSession())).toJson();
		return str;
	}
	/**
	 * @date 2017年12月26日
	 * @param fileInfo
	 * @param model
	 * @return
	 * @throws IOException
	 * @throws OpenXML4JException
	 * @throws SAXException
	 * 描述：导入
	 */
	@RequestMapping(value="/importPiDTS.do")
	@ResponseBody
	public String importPiDTS(FileInfo fileInfo,ModelMap model) throws IOException, OpenXML4JException, SAXException {
		return toWriteSuccess("导入成功！",piDataTrackSuppService.importPiDTS(fileInfo));
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
		return "business/designDataManager/piDataTrackSupp/piDataTrackSuppEdit";
	}
	/**
	 * @date 2017年12月15日
	 * @param id
	 * @return
	 * 描述：根据id查询对象
	 */
	@RequestMapping(value="/findPiDTS.do")
	@ResponseBody 
	public String findPiDTS(String id) {
		PiDataTrackSupp findByID = piDataTrackSuppService.findByID(id);
		return findByID.toJson();
	}
	
	/**
	 * @date 2017年12月27日
	 * @param piDataTrackSupp
	 * @return
	 * 描述：更新方法
	 */
	@RequestMapping(value="/updatePiDTS.do")
	@ResponseBody
	public String updatePiDTS(@RequestBody PiDataTrackSupp piDataTrackSupp) {
		piDataTrackSuppService.update(piDataTrackSupp);
		return toWriteSuccess("保存成功");
	}
	/**
	 * @date 2017年12月27日
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
}
