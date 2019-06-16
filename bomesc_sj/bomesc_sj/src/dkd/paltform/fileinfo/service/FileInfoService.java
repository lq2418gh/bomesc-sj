package dkd.paltform.fileinfo.service;

import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import dkd.paltform.base.BusinessException;
import dkd.paltform.base.dao.BaseDao;
import dkd.paltform.base.service.BaseService;
import dkd.paltform.fileinfo.dao.FileInfoDao;
import dkd.paltform.fileinfo.domain.FileInfo;
import dkd.paltform.util.Common;
import dkd.paltform.util.Constant;

@Service
@Transactional
public class FileInfoService  extends BaseService<FileInfo>{
	@Autowired
	private FileInfoDao fileInfoDao;
	@Override
	public BaseDao<FileInfo> getDao() {
		// TODO Auto-generated method stub
		return fileInfoDao;
	}
	@Transactional(propagation = Propagation.REQUIRED)
	public void saveFileInfo(FileInfo fileInfo) {
		fileInfo.validateModel();
		copyFile(fileInfo);
		create(fileInfo);
	}
	@Transactional(propagation = Propagation.SUPPORTS)
	private void copyFile(FileInfo fileInfo) {
		String fileName = fileInfo.getFileL().getOriginalFilename();
		//创建目录
		File target = new File(Constant.file_path + fileInfo.getEntity_code() +"/");
		if(!target.exists()){
			target.mkdirs();
		}
		String suffix = fileName.substring(fileName.lastIndexOf("."));
		int i = 0;
		String targetPath = fileInfo.getEntity_code() + "/" + fileName;
		target = new File(Constant.file_path + targetPath);
		while(true){
			if(!target.exists()){
				break;
			}
			targetPath= fileInfo.getEntity_code() + "/" + fileName + "(" + (++i) + ")" + suffix;
			target = new File(Constant.file_path + targetPath);
		}
		
		try {
			Common.copyFile(fileInfo.getFileL().getInputStream(), target);
		} catch (IOException e) {
			throw new BusinessException(-1, "", "读取文件错误");	
		}
		fileInfo.setPath(targetPath);
		fileInfo.setFile_name(fileName);
	}
	public void deleteFileInfo(String id){
		FileInfo fileInfo = fileInfoDao.findByID(id);
		fileInfoDao.delete(id);
		File file = new File(Constant.file_path + "/" + fileInfo.getPath());
		if(file.exists()){
			file.delete();
		}
	}
}
