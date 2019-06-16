package dkd.paltform.fileinfo.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import dkd.paltform.base.BusinessException;
import dkd.paltform.base.controller.BaseController;
import dkd.paltform.fileinfo.domain.FileInfo;
import dkd.paltform.fileinfo.service.FileInfoService;
import dkd.paltform.util.Constant;
@Controller
@RequestMapping(value = "/fileInfo")
public class FileInfoController extends BaseController{
	@Autowired
	private FileInfoService fileInfoService;
	@RequestMapping(value = "/query.do")
	@ResponseBody
	public String seachFileInfo(HttpServletRequest request) {
		return fileInfoService.getScrollData(getParam(request)).toJson();
	}
	@RequestMapping(value = "/save.do")
	@ResponseBody
	public String save(FileInfo fileInfo) {
		fileInfoService.saveFileInfo(fileInfo);
		return toWriteSuccess("上传文件成功！");
	}
	@RequestMapping(value = "/delete.do")
	@ResponseBody
	public String delete(String id) {
		fileInfoService.deleteFileInfo(id);
		return toWriteSuccess("删除文件成功！");
	}
	@RequestMapping(value = "/download.do")
	public void download(String id,HttpServletResponse response) {
		FileInfo fileInfo = fileInfoService.findByID(id);
		File file = new File(Constant.file_path + fileInfo.getPath());
		if(!file.exists()){
			throw new BusinessException(-1, "", "文件不存在");
		}
		
		try {
			response.setHeader("Content-disposition", "attachment; filename='" + new String(fileInfo.getFile_name().getBytes("GB2312"), "ISO8859-1") + "'");
			response.setContentType("application/octet-stream");
			
			OutputStream out = response.getOutputStream();
			FileInputStream in = new FileInputStream(file);
			
			byte buffer[] = new byte[1024];
			int len = 0;
			while((len=in.read(buffer))>0){
				out.write(buffer, 0, len);
			}
			in.close();
			out.flush();
	        out.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException(-1, "", "读取文件失败");
		}
	}
}
