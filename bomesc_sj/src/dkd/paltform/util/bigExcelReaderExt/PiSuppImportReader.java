package dkd.paltform.util.bigExcelReaderExt;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.xml.sax.SAXException;

import dkd.business.designDataManager.dao.PiDataTrackSuppDao;
import dkd.business.designDataManager.domain.PiDataTrackSupp;
import dkd.business.project.service.ProjectService;
import dkd.paltform.base.BusinessException;
import dkd.paltform.util.BigExcelReader;
import dkd.paltform.util.ConvertMapToDomainUtil;
import dkd.paltform.util.SpringUtil;

public class PiSuppImportReader extends BigExcelReader{

	private ProjectService projectService;
	private PiDataTrackSuppDao piDataTrackSuppDao;
	private PiDataTrackSupp piDataTrackSupp;
	private List<PiDataTrackSupp> list = new ArrayList<PiDataTrackSupp>();
	public PiSuppImportReader(InputStream inputStream, String major,String path) throws IOException, OpenXML4JException, SAXException {
		super(inputStream, major,path);
		this.projectService=(ProjectService) SpringUtil.getBean("projectService");
		this.piDataTrackSuppDao=(PiDataTrackSuppDao) SpringUtil.getBean("piDataTrackSuppDao");
	}

	@Override
	protected void outputRow(Map<String, String> mapData, int[] rowTypes, int rowIndex) {
		if(StringUtils.isNotEmpty(mapData.get("item"))&&mapData.get("item").matches("^[0-9]*[1-9][0-9]*$")){//从iteam开始校验
			//校验第一个项目名称
			Map<String, Object> selectIfNullByJobNo = projectService.selectIfNullByJobNo(mapData.get("job_no"));
			if (null==selectIfNullByJobNo) {
				throw new BusinessException(-1, null, "第"+mapData.get("item")+"行，项目名称不存在");
			}
			//校验excel读取过来的数据 的非空  长度   int格式
			String checkField = null;
			try {
				checkField = BigExcelReaderUtil.checkFieldIfNull(mapData, "PiDataTrackSupp");
				if (!StringUtils.equals("ok", checkField)) {
					throw new  BusinessException(-1, null, checkField);
				}
				checkField = BigExcelReaderUtil.checkField(mapData, "PiDataTrackSupp");
				if (!StringUtils.equals("ok", checkField)) {
					throw new  BusinessException(-1, null, checkField);
				}
			} catch (Exception e1) {
				e1.printStackTrace();
				throw new  BusinessException(-1, null, checkField);
			}
			//转对象
			try {
				piDataTrackSupp = (PiDataTrackSupp)ConvertMapToDomainUtil.assemble(mapData, PiDataTrackSupp.class);
			} catch (IllegalAccessException | InvocationTargetException | InstantiationException e) {
				e.printStackTrace();
			}
			String id = selectIfNullByJobNo.get("id").toString();
			piDataTrackSupp.setProject(id);
			list.add(piDataTrackSupp);
			
		}
	}
	//--校验焊口一到焊口四 不能全部都是 N/A----------------------------------------------------------------------------------------------------------------
//	private void checkField(Map<String, String> excelData) {
//		Map<String, String> newExcelData = reWrongKeyValue(excelData);
//		if ((newExcelData.containsKey("welding_no_one")==false)&&(newExcelData.containsKey("welding_no_two")==false)
//			&&(newExcelData.containsKey("welding_no_three")==false)&&(newExcelData.containsKey("welding_no_four")==false)
//			) {
//			throw new BusinessException(-1,null,"第"+newExcelData.get("item")+"行，焊接编号1-焊接编号4不能都为N/A");
//		}
//	}

//	private Map<String, String> reWrongKeyValue(Map<String, String> excelData) {
//		Map<String,String> newExcelData=excelData;
//		Iterator<Map.Entry<String, String>> it = newExcelData.entrySet().iterator();
//		while(it.hasNext()){  
//			Map.Entry<String, String> entry=it.next();
//			if(StringUtils.equals(entry.getValue(), "N/A")){
//				it.remove();
//			}
//		}
//		return newExcelData;
//	}

	@Override
	public void endParse(){
		super.endParse();
		String partNos="";
		String seven="";
		String alertPartNos="";
		String alertSeven="";
		StringBuffer bufferPartNo = new StringBuffer();
		StringBuffer bufferSeven = new StringBuffer();
		StringBuffer bufferAlertPartNos = new StringBuffer();
		StringBuffer bufferAlertSeven = new StringBuffer();
		for (PiDataTrackSupp piDataTrackSupp : list) {
			if (StringUtils.equals("BOMESC", piDataTrackSupp.getSupplier())) {
				//实仓：ISO图纸编号+识别编号+管段号+焊口编号
				bufferSeven.append("'"+piDataTrackSupp.getIso_drawing_no()+piDataTrackSupp.getMaterial_code()
				+piDataTrackSupp.getSpool_no()+piDataTrackSupp.getWelding_no_one()+piDataTrackSupp.getWelding_no_two()
				+piDataTrackSupp.getWelding_no_three()+piDataTrackSupp.getWelding_no_four()+"'").append(",");
			}else {
				//虚仓：零件编号
				bufferPartNo.append("'"+piDataTrackSupp.getPart_no()+"'").append(",");
			}
		}
		if (bufferPartNo.length()>0) {
			 partNos = bufferPartNo.substring(0, bufferPartNo.length()-1);
			 List<Map<String,Object>> findIfNull = piDataTrackSuppDao.findIfNULL(partNos);
			 if (findIfNull.size()>0) {
				//有数据  提示： 文件导入失败
				for (Map<String, Object> map : findIfNull) {
					bufferAlertPartNos.append("'"+map.get("part_no")+"'").append(",");
				}
				if (bufferAlertPartNos.length()>0) {
					alertPartNos = bufferAlertPartNos.substring(0, bufferAlertPartNos.length()-1);
				}
				throw new BusinessException(-1, null, "零件编号是"+alertPartNos+"的数据，数据库已经存在，文件导入失败");
			}
		}else if (bufferSeven.length()>0) {
			seven=bufferSeven.substring(0, bufferSeven.length()-1);
			List<Map<String,Object>> findSevenIfNULL =piDataTrackSuppDao.findSevenIfNULL(seven);
			if (findSevenIfNULL.size()>0) {
				//有数据  提示： 文件导入失败
				for (Map<String, Object> map : findSevenIfNULL) {
					bufferAlertSeven.append("'"+map.get("part_no")+"'").append(",");
				}
				if (bufferAlertSeven.length()>0) {
					alertSeven=bufferAlertSeven.substring(0, bufferAlertSeven.length()-1);
				}
				throw new BusinessException(-1, null, "唯一键是"+alertSeven+"的数据，数据库已经存在，文件导入失败");
			}
		}
		//没有的话  添加文件到数据 库
		for (PiDataTrackSupp piDataTrackSupp : list) {
			if (piDataTrackSupp.getWelding_no_one()==null) {
				piDataTrackSupp.setWelding_no_one("N/A");
			}
			if (piDataTrackSupp.getWelding_no_two()==null) {
				piDataTrackSupp.setWelding_no_two("N/A");
			}
			if (piDataTrackSupp.getWelding_no_three()==null) {
				piDataTrackSupp.setWelding_no_three("N/A");
			}
			if (piDataTrackSupp.getWelding_no_four()==null) {
				piDataTrackSupp.setWelding_no_four("N/A");
			}
			piDataTrackSuppDao.create(piDataTrackSupp);
		}
	}
	
	
	
}
