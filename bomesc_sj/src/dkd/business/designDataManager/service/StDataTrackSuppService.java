package dkd.business.designDataManager.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.xml.sax.SAXException;

import dkd.business.designDataManager.dao.StDataTrackSuppDao;
import dkd.business.designDataManager.domain.StDataTrackSupp;
import dkd.paltform.authority.domain.User;
import dkd.paltform.base.BusinessException;
import dkd.paltform.base.QueryResult;
import dkd.paltform.base.dao.BaseDao;
import dkd.paltform.base.service.BaseService;
import dkd.paltform.fileinfo.domain.FileInfo;
import dkd.paltform.util.bigExcelReaderExt.StSuppImportReader;
@Service
@Transactional
public class StDataTrackSuppService extends BaseService<StDataTrackSupp>{

	@Autowired
	private StDataTrackSuppDao stDataTrackSuppDao;

	@Override
	public BaseDao<StDataTrackSupp> getDao() {
		// TODO Auto-generated method stub
		return stDataTrackSuppDao;
	}
	/**
	 * @date 2017年12月7日
	 * @author gaoxp
	 * @param mtoInfo  存储料单号，行号，以及相应的专业缩写的map
	 * @param orderColumn   存储排序字段的list  这两个参数用来拼接
	 * @return
	 * 描述：根据专业缩写，料单号，料单行号按照指定的排序规则查询出排序后的数据跟踪表的list
	 */
	public List<StDataTrackSupp> getTrackListByMto(Map<String,String> mtoInfo,List<String> orderColumn){
		return stDataTrackSuppDao.getTrackListByMto(mtoInfo, orderColumn);
	}
	public QueryResult<Map<String, Object>> getScrollData(Map<String, String> param, User currentUser) {
		return stDataTrackSuppDao.getScrollDataByUser(param,currentUser);
	}
	/**
	 * @date 2017年12月13日
	 * @param fileInfo
	 * @return
	 * 描述：导入补料
	 * @throws IOException 
	 * @throws SAXException 
	 * @throws OpenXML4JException 
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public String importStDTS(FileInfo fileInfo) throws IOException, OpenXML4JException, SAXException {
		checkFileFormat(fileInfo);//校验是否是xlsx格式的文件
		InputStream inputStream = fileInfo.getFileL().getInputStream();
  		StSuppImportReader readerExt = new StSuppImportReader(inputStream,"ST",fileInfo.getFileL().getOriginalFilename());
  		readerExt.parse();
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
	/**
	 * @date 2018年1月10日
	 * @param idList
	 * @return
	 * 描述：物资编码  查询   物料档案的数据（类型 ，名称 ，单位） 
	 */
	public List<Map<String, Object>> findPickingList(String ids) {
		String[] split = ids.split(",");
		String idList="";
		for (int i = 0; i < split.length; i++) {
			 idList+="'"+split[i]+"',";
		}
		String idString = idList.substring(0, idList.length()-1);
		List<Map<String, Object>> findPickingList = stDataTrackSuppDao.findPickingList(idString);
		return findPickingList;
		
	}
	/**
	 * @date 2018年1月10日
	 * @param bulk_material_nos
	 * 描述：根据母材编号查寻 是同一个的明细
	 */
	public List<Map<String, Object>> findElseSuppByBMO(String bmoArry) {
		String[] split = bmoArry.split(",");
		String bmoList="";
		for (int i = 0; i < split.length; i++) {
			bmoList+=split[i]+",";
		}
		String bulk_material_nos = bmoList.substring(0, bmoList.length()-1);
		List<Map<String, Object>> findPickingList = stDataTrackSuppDao.findElseSuppByBMO(bulk_material_nos);
		return findPickingList;
	}
}
