package dkd.paltform.util.bigExcelReaderExt;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.xml.sax.SAXException;

import dkd.business.designDataManager.dao.StDataTrackSuppDao;
import dkd.business.designDataManager.domain.StDataTrackSupp;
import dkd.business.material.dao.MaterialArchivesDao;
import dkd.business.project.service.ProjectService;
import dkd.paltform.base.BusinessException;
import dkd.paltform.util.BigExcelReader;
import dkd.paltform.util.ConvertMapToDomainUtil;
import dkd.paltform.util.SpringUtil;


public class StSuppImportReader extends BigExcelReader{

	private StDataTrackSuppDao stDataTrackSuppDao;
	private ProjectService projectService;
	private List<StDataTrackSupp> list = new ArrayList<StDataTrackSupp>();
	private StDataTrackSupp stDataTrackSupp;
	private MaterialArchivesDao materialArchivesDao;
	
	public StSuppImportReader(InputStream in, String major,String path) throws IOException, OpenXML4JException, SAXException {
		 super(in, major,path); 
		 this.projectService=(ProjectService) SpringUtil.getBean("projectService");
		 this.stDataTrackSuppDao=(StDataTrackSuppDao) SpringUtil.getBean("stDataTrackSuppDao");
		 this.materialArchivesDao=(MaterialArchivesDao) SpringUtil.getBean("materialArchivesDao");
	}
	
	@Override
	public void outputRow(Map<String, String> mapData, int[] rowTypes, int rowIndex) {
		if(StringUtils.isNotEmpty(mapData.get("item"))&&mapData.get("item").matches("^[0-9]*[1-9][0-9]*$")){//从iteam开始校验
			
			Map<String, Object> selectIfNullByJobNo = projectService.selectIfNullByJobNo(mapData.get("job_no"));
			
			if (null==selectIfNullByJobNo||null==selectIfNullByJobNo.get("project_name")||!StringUtils.equals(selectIfNullByJobNo.get("project_name").toString(), mapData.get("project_name").toString())) {
				throw new BusinessException(-1, null, "第"+mapData.get("item")+"行，项目名称和项目工作号不为空，需要存在于上游数据且需要对应");
			}
			mapData.get("material_code");
			
			//校验excel读取过来的数据 
			String checkField = null;
			try {
				checkField = BigExcelReaderUtil.checkFieldIfNull(mapData, "StDataTrackSupp");
				if (!StringUtils.equals("ok", checkField)) {
					throw new  BusinessException(-1, null, checkField);
				}
				checkField = BigExcelReaderUtil.checkField(mapData, "StDataTrackSupp");
				if (!StringUtils.equals("ok", checkField)) {
					throw new  BusinessException(-1, null, checkField);
				}
			} catch (Exception e1) {
				e1.printStackTrace();
				throw new  BusinessException(-1, null, checkField);
			}
			try {
				stDataTrackSupp = (StDataTrackSupp)ConvertMapToDomainUtil.assemble(mapData, StDataTrackSupp.class);//转对象
			} catch (IllegalAccessException | InvocationTargetException | InstantiationException e) {
				e.printStackTrace();
			}
			String id = selectIfNullByJobNo.get("id").toString();
			stDataTrackSupp.setProject(id);
			list.add(stDataTrackSupp);
			
		}
	}

	@Override
	public void endParse(){
		super.endParse();
		
		StringBuffer buffer = new StringBuffer();
		StringBuffer bufferPartNos = new StringBuffer();
		//---------------------
		String materialCodes="";
		String alertMaterialCodes="";
		String alertCodes="";
		String codes="";
		StringBuffer bufferMCS = new StringBuffer();
		StringBuffer bufferMaterialCodes = new StringBuffer();
		
		for (StDataTrackSupp stDataTrackSupp : list) {
			buffer.append("'"+stDataTrackSupp.getPart_no()+"'").append(",");
			bufferMCS.append("'"+stDataTrackSupp.getMaterial_code()+"'").append(",");
		}
		if (bufferMCS.length()>0) {
			materialCodes = bufferMCS.substring(0, bufferMCS.length()-1);
		}
		String[] split = materialCodes.split(",");
		Set<String> set = new HashSet<>(Arrays.asList(split));
		for (String string : set) {
			codes+=string;
		}
		List<Map<String,Object>> findMaterCode =materialArchivesDao.findMaterCode(set);
		//存在编码没录入情况
		if (findMaterCode.size()!=set.size()) {
			for (Map<String, Object> map : findMaterCode) {
				bufferMaterialCodes.append("'"+map.get("material_code")+"'").append(",");
			}
			if (bufferMaterialCodes.length()>0) {
				alertMaterialCodes = bufferMaterialCodes.substring(0, bufferMaterialCodes.length()-1);
			}
			String[] split2 = alertMaterialCodes.split(",");
			String[] split3 = codes.split(",");
			List<String> lists = compare(split2,split3);  
			for (String code : lists) {
				alertCodes+=code+",";
			}  
			throw new BusinessException(-1, null, "物料编码是"+alertCodes+"的数据，物料档案模块没录入");
		}
			
		List<Map<String,Object>> findIfNull = stDataTrackSuppDao.findIfNULL(buffer);
		if (findIfNull.size()>0) {
			String alert="";
			//有数据  提示： 文件导入失败
			for (Map<String, Object> map : findIfNull) {
				bufferPartNos.append("'"+map.get("part_no")+"'").append(",");
			}
			if (buffer.length()>0) {
				alert = buffer.substring(0, bufferPartNos.length()-1);
			}
			throw new BusinessException(-1, null, "零件编号是"+alert+"的数据，数据库已经存在，文件导入失败");
			
		}else{
			//没有的话  添加文件到数据 库
			for (StDataTrackSupp stDataTrackSupp : list) {
				stDataTrackSuppDao.create(stDataTrackSupp);
			}
		}
	}
	//对比两个数组找出不同
	public static <T> List<T> compare(T[] t1, T[] t2) {    
	      List<T> list1 = Arrays.asList(t1);    
	      List<T> list2 = new ArrayList<T>();    
	      for (T t : t2) {    
	          if (!list1.contains(t)) {    
	              list2.add(t);    
	          }    
	      }    
	      return list2;    
	  }  
}

