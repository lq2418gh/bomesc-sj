package dkd.business.mto.controller;


import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import dkd.business.mto.service.MtoHeadService;
import dkd.paltform.base.controller.BaseController;
import dkd.paltform.fileinfo.domain.FileInfo;
import dkd.paltform.util.json.JSONUtil;

@Controller
@RequestMapping(value = "/mto")
public class MtoController extends BaseController{
	@Autowired
	private MtoHeadService mtoService;
	/**
	 * @date 2017年11月30日
	 * @author gaoxp
	 * @param request
	 * @return
	 * 描述：查询方法
	 */
	@RequestMapping(value = "/mtoHeadList.do")
	@ResponseBody
	public String query(HttpServletRequest request) {
		return mtoService.getScrollData(getParam(request)).toJson();
	}
	/**
	 * 
	 * @date 2017年11月30日
	 * @author gaoxp
	 * @param mto_no
	 * @param model
	 * @param msg 导入成功后接受导入成功信息，用于view页面显示
	 * @return
	 * 描述：跳转到申购单的查看页面
	 */
	@RequestMapping(value = "/view.do")
	public String view(String mto_no,String msg,ModelMap model) {
		model.put("mto_no", mto_no);
		if(StringUtils.isNotEmpty(msg)){
			model.put("msg", msg);
		}
		return "business/mto/mtoDetail/mtoView";
	}
	/**
	 * @date 2017年11月30日
	 * @author gaoxp
	 * @param order_no
	 * @return
	 * 描述：加载查看页面数据方法
	 */
	@RequestMapping(value = "/findByNo.do")
	@ResponseBody
	public String findByNo(String mto_no) {
		return JSONUtil.getJsonByEntity(mtoService.findByField(mto_no));
	}
	/**
	 * @date 2017年12月1日
	 * @author gaoxp
	 * @param fileInfo
	 * @return
	 * 描述：料单导入
	 * @throws Exception 
	 */
	@RequestMapping(value = "/poi.do")
	@ResponseBody
	public String save(FileInfo fileInfo,ModelMap model) throws Exception {
		return toWriteSuccess("导入成功！",mtoService.poi(fileInfo));//提示信息，同时返回相应的mtoNo,这里临时存储在id字段名里
	}
	/**
	 * @date 2017年12月4日
	 * @author gaoxp
	 * @param id
	 * @return
	 * 描述：
	 */
	@RequestMapping(value="deleteMto.do")
	@ResponseBody
	public String deleteMto(String id){
		mtoService.delete(id);
		return toWriteSuccess("删除成功！");
	}
	/**
	 * @date 2017年12月4日
	 * @author gaoxp
	 * @param id 单据id
	 * @param opr 单据操作   confirm：确认单据；concelConfirm：取消确认单据；
	 * @return
	 * 描述：
	 */
	@RequestMapping(value="mtoState.do")
	@ResponseBody
	public String confrimMto(String id,String opr,HttpServletRequest request){
		//此处有确认时，将料单中的物料拆分到数据跟踪表中的方法，数据跟踪表模块还未完成
		return toWriteSuccess(mtoService.confirmMto(id,opr,getCurrentUser(request.getSession())));
	}
}
