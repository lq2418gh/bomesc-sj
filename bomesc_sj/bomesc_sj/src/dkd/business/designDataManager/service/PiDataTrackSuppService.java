package dkd.business.designDataManager.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.xml.sax.SAXException;

import dkd.business.designDataManager.dao.PiDataTrackSuppDao;
import dkd.business.designDataManager.domain.PiDataTrackSupp;
import dkd.paltform.authority.domain.User;
import dkd.paltform.base.BusinessException;
import dkd.paltform.base.QueryResult;
import dkd.paltform.base.dao.BaseDao;
import dkd.paltform.base.service.BaseService;
import dkd.paltform.fileinfo.domain.FileInfo;
import dkd.paltform.util.bigExcelReaderExt.PiSuppImportReader;

@Service
@Transactional
public class PiDataTrackSuppService extends BaseService<PiDataTrackSupp>{
	@Autowired
	private PiDataTrackSuppDao piDataTrackSuppDao;
	
	@Override
	public BaseDao<PiDataTrackSupp> getDao() {
		return piDataTrackSuppDao;
	}

	public QueryResult<Map<String, Object>> getScrollData(Map<String, String> param, User currentUser) {
		return piDataTrackSuppDao.getScrollDataByUser(param,currentUser);
	}
	/**
	 * @date 2017年12月25日
	 * @param fileInfo
	 * @return
	 * @throws IOException
	 * @throws OpenXML4JException
	 * @throws SAXException
	 * 描述：导入
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public String importPiDTS(FileInfo fileInfo) throws IOException, OpenXML4JException, SAXException {
		checkFileFormat(fileInfo);//校验是否是xlsx格式的文件
		InputStream inputStream = fileInfo.getFileL().getInputStream();
		PiSuppImportReader piSuppImportReader = new PiSuppImportReader(inputStream,"PI",fileInfo.getFileL().getOriginalFilename());
		piSuppImportReader.parse();
		return null;
	}

	/**
	 * @date 2017年12月1日
	 * @author gaoxp
	 * @param fileName2
	 * @return
	 * 描述：导入文件格式校验，检查是否是.xlsx格式文件
	 */
	private void checkFileFormat(FileInfo fileInfo){
		String fileName = fileInfo.getFileL().getOriginalFilename();
		if(!fileName.equals("")){
			String ext = fileName.substring(fileName.lastIndexOf(".")+1,fileName.length());
			ext = ext.toLowerCase();  
			if(!ext.equals("xlsx")){
				throw new BusinessException(-1, "文件格式", "文件非xlsx格式，请检查！");	
			}
		}
	}
}
