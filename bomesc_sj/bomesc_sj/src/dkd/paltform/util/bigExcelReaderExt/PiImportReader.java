package dkd.paltform.util.bigExcelReaderExt;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;


import org.apache.commons.lang.StringUtils;
import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.xml.sax.SAXException;


import dkd.business.designDataManager.domain.PiDataTrack;
import dkd.business.designDataManager.service.PiDataTrackTableService;
import dkd.business.project.service.ProjectService;
import dkd.paltform.util.BigExcelReader;
import dkd.paltform.util.ConvertMapToDomainUtil;
import dkd.paltform.util.SpringUtil;

public class PiImportReader extends BigExcelReader{
	private ProjectService projectService;
	private PiDataTrackTableService piDataTrackTableService; 
	private Map<String,Map<String, Object>> trackDataMap;
	private String filePath;
	private String moduleName;
	private String jobNo;
	private String project;
	private List<String> list;
	private Date date;
	/**
	 * @param filename
	 * @param major
	 * @throws IOException
	 * @throws OpenXML4JException
	 * @throws SAXException
	 */
	public PiImportReader(String filename, String major,Map<String,Map<String, Object>> trackDataMap,String moduleName,
			String jobNo,String project,Date date) throws IOException,OpenXML4JException, SAXException {
		super(filename, major);
		this.trackDataMap=trackDataMap;
		this.filePath=filename;
		this.moduleName=moduleName;
		this.jobNo=jobNo;
		this.project=project;
		this.date=date;
		this.projectService=(ProjectService) SpringUtil.getBean("projectService");
		this.piDataTrackTableService=(PiDataTrackTableService) SpringUtil.getBean("piDataTrackTableService");
		this.list=new ArrayList<String>();
	}
	@Override
	protected void outputRow(Map<String, String> mapData, int[] rowTypes,
			int rowIndex) {
		String item = mapData.get("item");
		if(StringUtils.isNotEmpty(item)&&item.matches("^[0-9]*[1-9][0-9]*$")){
			try {
				if(StringUtils.equals(project, mapData.get("project_name"))&&StringUtils.equals(moduleName, mapData.get("module_name"))){
					if(null!=projectService.findProjectByNameAndNo(project, mapData.get("job_no"))){
						handlePiImport(trackDataMap,mapData,item);
					}else{
						remove(mapData);
						list.add("第"+item+"行项目名称和项目工作号不匹配！");
					}
				}else{
					remove(mapData);//移除，剩下的后面执行删除操作
					list.add("第"+item+"行项目名称或者模块编号和文件夹对应的不匹配！");
				}
			} catch (Exception e) {
				remove(mapData);
				list.add("第"+item+"行导入时发生未知异常,请联系系统管理员！");
				e.printStackTrace();
			}
		}
	}
	/**
	 * @date 2018年1月2日
	 * @author gxp
	 * @param trackDataMap 根据项目，专业，模块编号查询到的数据库中的数据
	 * @param ExcelData 读取到的excel中的每一行数据
	 * 描述：	将管线专业数据跟踪表中的一条数据导入到数据库中
	 * 		将excel中的这一行数据，按照不同供应商时的主键在查询到的trackDataMap中看是否有数据；
	 * 		若没有相应的数据，则证明为新数据，执行插入操作
	 * 		若有相应的数据，则对比其他列，将变更列执行更新操作：更新时，需要将原先的那条is_history设置为Y，change_size，同时需要插入最新的那条数据
	 * @throws Exception 
	 */
	private void handlePiImport(Map<String,Map<String, Object>> trackDataMap,Map<String,String> excelData,String item) throws Exception{
		String key = getKey(excelData);//根据不同情况获取该条excel数据的主键
		PiDataTrack piDataTrack;
		String fieldCheckMsg;//校验方法返回的信息，返回ok则校验成功，返回其他错误信息则校验失败；
		if(StringUtils.isNotEmpty(key)&&null==trackDataMap.get(key)){//数据跟踪表中没有查询到相应的数据，为新数据，执行插入操作
			fieldCheckMsg = BigExcelReaderUtil.checkField(excelData,"PiDataTrack");//校验列是否符合要求，同时移除了值为N/A和key为item的键值对
			if(StringUtils.equals("ok", fieldCheckMsg)){//校验全数据字段类型，通过；此处为插入操作
				String ifNullMsg = BigExcelReaderUtil.checkFieldIfNull(excelData, "PiDataTrack");//字段非空校验，根据实体属性nullable进行校验
				if(StringUtils.equals("ok", ifNullMsg)){//需要校验非空字段是否为空；
					//这里增加逻辑校验，假如是bomesc则七列中前三列不能为空，假如是free issue则part_no为空
					String handleSupplierMsg=handleSupplierCheck(excelData);
					if(StringUtils.equals("ok", handleSupplierMsg)){
						//这里需要编写插入方法，循环遍历map后，实例化一个实体，直接插入操作
						piDataTrack=(PiDataTrack)ConvertMapToDomainUtil.assemble(excelData, PiDataTrack.class);
						piDataTrack.setIs_history(false);
						piDataTrack.setChange_size(0);
						piDataTrack.setUpdate_date(date);
						piDataTrack.setState("新增");
						piDataTrack.setProject(projectService.getProjectId(piDataTrack.getJob_no()));
						piDataTrackTableService.create(handleBomescSupplier(piDataTrack));
					}else{
						list.add("第"+item+"行"+handleSupplierMsg);
					}
				}else{
					list.add("第"+item+"行"+ifNullMsg);
				}
			}else{//校验数据，没有通过，则将该行数据插入到另一表中，现在还没有那张表；原因：字段类型不匹配或者存在非空字段为空值的现象
				list.add("第"+item+"行"+fieldCheckMsg);//这里将异常信息存入list
			}
		}else if(StringUtils.isNotEmpty(key)&&null!=trackDataMap.get(key)){//数据跟踪表中查询到了相应数据，则执行更新操作
			//首先校验数据
			Map<String, Object> trackData = trackDataMap.get(key);//获取到读取行对应的数据跟踪表的数据
			trackDataMap.remove(key);//移除excel中对应的零件编号在数据库查询出的结果集的记录，为了后面做删除操作，最后剩下的就是要删除的数据
			fieldCheckMsg = BigExcelReaderUtil.checkField(excelData,"PiDataTrack");//校验每一列的有效性，包括字符串是否超过长度，数字是否为数字等；
			if(StringUtils.equals("ok", fieldCheckMsg)){//校验成功
				if(!BigExcelReaderUtil.isSame(excelData, trackData,"PiDataTrack")){//假如有字段不相同，则执行更新操作
					//将数据库该行数据转换为相应实体
					piDataTrack=(PiDataTrack)ConvertMapToDomainUtil.anotherAssemble(trackData, PiDataTrack.class);
					piDataTrack.setIs_history(true);
					piDataTrack.setState("修改");
					piDataTrackTableService.update(piDataTrack);//更新了该条数据
					//将excel中读取出的一行数据更新到数据库读取出的零件编号对应的数据中
					Map<String, Object> newTrackData=BigExcelReaderUtil.updateTrackDataMap(trackData, excelData);//将excel中数据替换数据库中相应字段的值；假如trackData和newTrackData一样，则不
					piDataTrack=(PiDataTrack)ConvertMapToDomainUtil.anotherAssemble(newTrackData, PiDataTrack.class);
					piDataTrack.setId(null);
					piDataTrack.setState("新增");
					piDataTrack.setUpdate_date(date);
					piDataTrack.setIs_history(false);
					piDataTrack.setChange_size(piDataTrack.getChange_size()+1);
					piDataTrackTableService.create(piDataTrack);//插入新数据
				};
			}else{//数据校验没有通过，则该行数据插入到另一张表中；原因：数据校验未通过
				list.add("第"+item+"行"+fieldCheckMsg);
			}
		}else{//母材编号为空，也插入到另外的表中
			//这里需要将数据插入到另一张表中
			list.add("第"+item+"行无法确定唯一性！");
		}
	}
	/**
	 * @date 2018年1月4日
	 * @author gaoxp
	 * @param excelData
	 * @return
	 * 描述：供应商校验，BOMESC则拼接主键的前三个字段不能为空；Free Issue则part_no不能为空
	 */
	private String handleSupplierCheck(Map<String,String> excelData){
		String msg="ok";
		if(StringUtils.equals(excelData.get("supplier"), "Free Issue")&&StringUtils.isEmpty(excelData.get("part_no"))){
			msg="供应商为Free Issue,零件编号为空！";
		}
		if(StringUtils.equals(excelData.get("supplier"), "BOMESC")){
			if(StringUtils.isEmpty(excelData.get("iso_drawing_no"))&&StringUtils.isEmpty(excelData.get("material_code"))&&StringUtils.isEmpty(excelData.get("spool_no"))){
				msg = "供应商为BOMESC,ISO图纸编号、物料编码、管段号都为空！";
			}
		}
		return msg;
	}
	/**
	 * @date 2018年1月3日
	 * @author gaoxp
	 * @param piDataTrack
	 * @return
	 * 描述：供应商为bomesc，则拼接主键的七个字段假如为空则填N/A，用来拼接主键,插入时使用
	 */
	private PiDataTrack handleBomescSupplier(PiDataTrack piDataTrack){
		PiDataTrack newPiDataTrack = piDataTrack;
		if(StringUtils.equals("BOMESC", piDataTrack.getSupplier())){
			if(StringUtils.isEmpty(newPiDataTrack.getWelding_no_one())){
				newPiDataTrack.setWelding_no_one("N/A");
			}
			if(StringUtils.isEmpty(newPiDataTrack.getWelding_no_two())){
				newPiDataTrack.setWelding_no_two("N/A");
			}
			if(StringUtils.isEmpty(newPiDataTrack.getWelding_no_three())){
				newPiDataTrack.setWelding_no_three("N/A");
			}
			if(StringUtils.isEmpty(newPiDataTrack.getWelding_no_four())){
				newPiDataTrack.setWelding_no_four("N/A");
			}
			if(StringUtils.isEmpty(newPiDataTrack.getIso_drawing_no())){
				newPiDataTrack.setIso_drawing_no("N/A");
			}
			if(StringUtils.isEmpty(newPiDataTrack.getMaterial_code())){
				newPiDataTrack.setMaterial_code("N/A");
			}
			if(StringUtils.isEmpty(newPiDataTrack.getSpool_no())){
				newPiDataTrack.setSpool_no("N/A");
			}
		}
		return newPiDataTrack;
	}
	/**
	 * 结束解析后，删除所有的数据库中存在，excel中不存在的对应的零件编号的数据，为已删除数据
	 * 同时若有错误信息，插入导入失败提醒记录，以及给相应数据负责人发送邮件
	 * 参数的map中，key为对应的零件编号
	 */
	@Override
	protected void endParse(){
		super.endParse();
		deleteTrackData();
		BigExcelReaderUtil.createRecordAndSendEmail("PI", filePath, list, moduleName, jobNo);
	}
	/**
	 * @date 2017年12月21日
	 * @author gaoxp	
	 * 描述：删除数据跟踪表方法
	 */
	private void deleteTrackData(){
		for (Map.Entry<String, Map<String, Object>> entry : trackDataMap.entrySet()) {
			//执行删除   ，更改为历史记录
			Map<String, Object> trackData = trackDataMap.get(entry.getKey());//这条数据在excel中不存在
			try {
				PiDataTrack piDataTrack = (PiDataTrack) ConvertMapToDomainUtil.anotherAssemble(trackData, PiDataTrack.class);
				piDataTrack.setIs_history(true);
				piDataTrack.setState("删除");
				piDataTrackTableService.update(piDataTrack);//更新了该条数据为历史记录，状态改为删除
			} catch (Exception e) {
				list.add("数据跟踪表零件编号为"+trackData.get("part_no")+"的数据删除时出现异常,请联系系统管理员！");
				e.printStackTrace();
			}
		}
	}
	/**
	 * @date 2018年1月2日
	 * @author gaoxp
	 * @param mapData
	 * 描述：根据不同的供货商选择不同的数据唯一性确定方法，移除相应的查询出的数据库数据
	 */
	private void remove(Map<String, String> mapData){
		if(StringUtils.isNotEmpty(mapData.get("supplier"))&&StringUtils.equals("Free Issue", mapData.get("supplier"))){
			trackDataMap.remove(mapData.get("part_no"));
		}else if(StringUtils.isNotEmpty(mapData.get("supplier"))&&StringUtils.equals("BOMESC", mapData.get("supplier"))){
			StringBuffer mixKeyBuffer = new StringBuffer();
			mixKeyBuffer.append(handleNull((String)mapData.get("iso_drawing_no")))
			.append(handleNull((String)mapData.get("material_code"))).append(handleNull((String)mapData.get("spool_no")))
			.append(handleNull((String)mapData.get("welding_no_one"))).append(((String)mapData.get("welding_no_two")))
			.append(handleNull((String)mapData.get("welding_no_three"))).append(handleNull((String)mapData.get("welding_no_four")));
			trackDataMap.remove(mixKeyBuffer.toString());
		}
	}
	/**
	 * @date 2018年1月2日
	 * @author gaoxp
	 * @param excelData
	 * @return
	 * 描述：根据供货商获取不同的主键
	 */
	private String getKey(Map<String,String> excelData){
		String key="";
		if(StringUtils.isNotEmpty(excelData.get("supplier"))&&StringUtils.equals("Free Issue", excelData.get("supplier"))){
			key = excelData.get("part_no");
		}else if(StringUtils.isNotEmpty(excelData.get("supplier"))&&StringUtils.equals("BOMESC", excelData.get("supplier"))){
			StringBuffer mixKeyBuffer = new StringBuffer();
			mixKeyBuffer.append(handleNull((String)excelData.get("iso_drawing_no")))
			.append(handleNull((String)excelData.get("material_code"))).append(handleNull((String)excelData.get("spool_no")))
			.append(handleNull((String)excelData.get("welding_no_one"))).append(handleNull((String)excelData.get("welding_no_two")))
			.append(((String)excelData.get("welding_no_three"))).append(handleNull((String)excelData.get("welding_no_four")));
			key=mixKeyBuffer.toString();
		}
		return key;
	}
	/**
	 * @date 2018年1月3日
	 * @author gaoxp
	 * @param weldingNo
	 * @return
	 * 描述：将null值变换为N/A
	 */
	private String handleNull(String str){
		String newStr=str;
		if(StringUtils.isEmpty(newStr)||StringUtils.equals("NA", newStr)){
			return "N/A";
		}else{
			return newStr;
		}
	}
}
