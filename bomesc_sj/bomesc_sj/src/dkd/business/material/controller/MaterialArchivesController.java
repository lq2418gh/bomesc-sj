package dkd.business.material.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import dkd.business.material.domain.MaterialArchives;
import dkd.business.material.service.MaterialArchivesService;
import dkd.paltform.base.controller.BaseController;
import dkd.paltform.util.json.request.RequestJsonNode;
@Controller
@RequestMapping(value = "/archives")
public class MaterialArchivesController extends BaseController{
	@Autowired
	private MaterialArchivesService materialArchivesService;
	@RequestMapping(value = "/query.do")
	@ResponseBody
	public String query(HttpServletRequest request) {
		return materialArchivesService.getScrollData(getParam(request)).toJson();
	}
	/**
	 * @date 2018年1月9日
	 * @author gaoxp
	 * @return
	 * 描述：通过ajax请求获取产品中类下
	 * @throws IOException 
	 */
	@RequestMapping(value = "/findTotalCode.do")
	@ResponseBody
	public String findTotalCode(@RequestJsonNode("id")String id,HttpServletResponse responses){
		return materialArchivesService.findTotalCodeBySql(id);
	}
	/**
	 * @date 2018年1月10日
	 * @author gaoxp
	 * @param totalCode
	 * @param detailCode
	 * @return
	 * 描述：后台获取流水号
	 */
//	@RequestMapping(value = "/findSerialNum.do")
//	@ResponseBody
//	public String findSerialNum(@RequestJsonNode("totalCode")String totalCode,@RequestJsonNode("detailCode")String detailCode){
//		return materialArchivesService.findSerialNum(totalCode, detailCode);
//	}
	@RequestMapping(value = "/save.do")
	@ResponseBody
	public String addArchives(@RequestBody MaterialArchives materialArchives) {
		return toWriteSuccess(materialArchivesService.save(materialArchives));
	}
	@RequestMapping(value = "/findById.do")
	@ResponseBody
	public String findById(String id) {
		return materialArchivesService.findByIDToJson(id);
	}
	@RequestMapping(value = "/update.do")
	@ResponseBody
	public String updateArchives(@RequestBody MaterialArchives materialArchives) {
		materialArchivesService.update(materialArchives);
		return toWriteSuccess("更新成功！");
	}
	@RequestMapping(value = "/delete.do")
	@ResponseBody
	public String deleteArchives(String id) {
		materialArchivesService.deleteArchives(id);
		return toWriteSuccess("删除成功！");
	}
}
