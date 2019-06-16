package dkd.paltform.util.bigExcelReaderExt;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.persistence.Column;

import org.apache.commons.lang.StringUtils;

import dkd.business.dataParamConfig.domain.DataManagerDetail;
import dkd.business.dataParamConfig.service.DataManagerService;
import dkd.business.promptRecord.domain.ImportFailureRecord;
import dkd.business.promptRecord.service.ImportFailureRecordService;
import dkd.paltform.base.BusinessException;
import dkd.paltform.base.annotation.BeanSelect;
import dkd.paltform.dictionary.service.DictionaryService;
import dkd.paltform.util.MailSend;
import dkd.paltform.util.SpringUtil;

public class BigExcelReaderUtil {
	/**
	 * 获取数据跟踪表Class对象
	 * @date 2017年12月20日
	 * @author gaoxp
	 * @param beanProp
	 * @param beanName
	 * @return
	 */
	public static Class<?> testGet(Properties beanProp,String beanName){
		Class<?> c;
		try {
			c = Class.forName(beanProp.getProperty(beanName));
			return c;
		} catch (ClassNotFoundException e) {
			throw new BusinessException(-1, "", "读取实体字段错误");
		}
	}
	/**
	 * @date 2018年1月2日
	 * @author gaoxp
	 * @param ExcelData
	 * @return
	 * 描述：移除读取到的一行excel数据中value为N/A(NA)的值以及key为item的值
	 */
	public static Map<String,String> removeWrongKeyValue(Map<String,String> excelData){
		Map<String,String> newExcelData=excelData;
		Iterator<Map.Entry<String, String>> it = newExcelData.entrySet().iterator();
		while(it.hasNext()){  
			Map.Entry<String, String> entry=it.next();
			if(StringUtils.equals(entry.getKey(), "item")||StringUtils.equals(entry.getValue(), "N/A")||StringUtils.equals(entry.getValue(), "NA")){
				it.remove();
			}
		}
		return newExcelData;
	}
	/**
	 * @date 2018年1月2日
	 * @author gaoxp
	 * @param ExcelData
	 * @return
	 * 描述：校验非空字段是否为空
	 * @throws SecurityException 
	 * @throws NoSuchFieldException 
	 */
	public static String checkFieldIfNull(Map<String,String> excelData,String className) throws Exception{
		Class<?> clazz = testGet(SpringUtil.getBeanProp(),className);
		Field[] declaredFields = clazz.getDeclaredFields();
		List<String> exceptFieldNames = new ArrayList<String>();
		exceptFieldNames.add("update_date");
		exceptFieldNames.add("is_history");
		exceptFieldNames.add("change_size");
		exceptFieldNames.add("project");
		List<String> notNullFiledNames = new ArrayList<String>();
		for(Field declaredField:declaredFields){//获取这个专业数据跟踪表所有的非空属性,不包含上面三条属性，这三条非空属性为自己定义的逻辑字段
			if(null!=declaredField.getAnnotation(Column.class)&&!declaredField.getAnnotation(Column.class).nullable()){
				if(!exceptFieldNames.contains(declaredField.getName())){
					notNullFiledNames.add(declaredField.getName());
				}
			}
		}
		for(int i=0;i<notNullFiledNames.size();i++){//循环这个list，从读取出的数据中取值
			if(null==excelData.get(notNullFiledNames.get(i))){//按照这个key，没有取到相应值，则没有读取到数据，返回校验失败的信息
				return clazz.getDeclaredField(notNullFiledNames.get(i)).getAnnotation(BeanSelect.class).fieldName()+"是空值！";
			}
		}
		return "ok";
	}
	/**
	 * @date 2017年12月12日
	 * @author gaoxp
	 * @param ExcelData
	 * @return
	 * @throws Exception
	 * 描述：校验每一行excel数值；字符串长度是否超过字段设置的长度；浮点型是否可以parse成浮点型，整型是否可以parse成整型;以及供应商只能是BOMESC或者
	 */
	public static String checkField(Map<String,String> excelData,String className) throws Exception{
		Field field ;
		int length;
		removeWrongKeyValue(excelData);//移除map中value为N/A的值，和key是item的值
		Class<?> clazz = testGet(SpringUtil.getBeanProp(),className);
		for (Map.Entry<String, String> entry : excelData.entrySet()) {
			field = clazz.getDeclaredField(entry.getKey());
			//校验值是否符合实体要求
			if(null!=field&&String.class==field.getType()){//字段长度
				length=field.getAnnotation(Column.class).length();
				if(entry.getValue().length()>length){
					return field.getAnnotation(BeanSelect.class).fieldName()+"长度过长！";
				}
			}else if(null!=field&&Integer.class==field.getType()){//是否是整数
				if(!entry.getValue().matches("^\\d+$")){
					return field.getAnnotation(BeanSelect.class).fieldName()+"无法转换为整数！";
				}
			}else if(null!=field&&BigDecimal.class==field.getType()){//是否是小数
				if(!entry.getValue().matches("^\\d+$")&&!entry.getValue().matches("^\\d+(\\.\\d+)?$")){
					return field.getAnnotation(BeanSelect.class).fieldName()+"无法转换为小数！";
				}
			}
		}
		//校验供货商分别为BOMESC和Free Issue时的情况
		//BOMESC:料单号，料单行号，物料编码不能为空
		//Free Issue：Tag_no 不能为空
		if(StringUtils.isNotEmpty(excelData.get("supplier"))&&StringUtils.equals("BOMESC", excelData.get("supplier"))){
			if(StringUtils.isEmpty(excelData.get("material_code"))||StringUtils.isEmpty(excelData.get("mto_no"))||StringUtils.isEmpty(excelData.get("mto_row_no"))||StringUtils.isNotEmpty(excelData.get("ident_code"))){
				return "供应商为Bomesc,物料编码、料单编号、料单中序号有空值或者标签编号有值！";
			}
		}
		if(StringUtils.isNotEmpty(excelData.get("supplier"))&&StringUtils.equals("Free Issue", excelData.get("supplier"))){
			if(StringUtils.isNotEmpty(excelData.get("material_code"))||StringUtils.isNotEmpty(excelData.get("mto_no"))||StringUtils.isNotEmpty(excelData.get("mto_row_no"))||StringUtils.isEmpty(excelData.get("ident_code"))){
				return "供应商为Free Issue,标签编号为空值或者物料编码、料单编号、料单中序号不为空值！";
			}
		}
		return "ok";
	}
	/**
	 * @date 2017年12月13日
	 * @author gxp
	 * @param trackData
	 * @param excelData
	 * @return
	 * 描述：将读取到的excel中一行数据更新到查询出的数据跟踪表对应的一条数据（都是map）
	 */
	public static Map<String, Object> updateTrackDataMap(Map<String, Object> trackData,Map<String,String> excelData){
		Map<String, Object> newTrackData = trackData;
		for(Map.Entry<String, String> entry:excelData.entrySet()){
			trackData.put(entry.getKey(), entry.getValue());
		}
		return newTrackData;
	}
	/**
	 * @date 2017年12月15日
	 * @author gaoxp
	 * @param ExcelData
	 * @param trackData
	 * @return
	 * 描述：判断两个map中相同的key对应的值是否相等；全部都相同则返回true，有一个不同返回false
	 */
	public static boolean isSame(Map<String,String> excelData,Map<String, Object> trackData,String className) throws Exception{
		Field field;
		for(Map.Entry<String, String> entry:excelData.entrySet()){
			field = testGet(SpringUtil.getBeanProp(),className).getDeclaredField(entry.getKey());
			if(null==trackData.get(entry.getKey())){//假如数据库中对应字段为空值，则新增了值，判定为有改变
				return false;
			}
			if(null!=field&&BigDecimal.class==field.getType()){//假如比较的字段对应的是小数，这时候需要把值转换成小数进行比较，直接比较字符串可能会有30(Excel读取出的字段)和30.00(数据库中查询出的值)这种情况
				if(new BigDecimal(entry.getValue()).compareTo(new BigDecimal(String.valueOf(trackData.get(entry.getKey()))))!=0){
					return false;
				}
			}else{
				if(!StringUtils.equals(entry.getValue(), String.valueOf(trackData.get(entry.getKey())))){
					return false;
				}
			}
		}
		return true;
	}
	/**
	 * @date 2017年12月21日
	 * @author gaoxp
	 * 描述：创建导入失败提醒记录，同时给相关数据负责人发送邮件
	 */
	public static void createRecordAndSendEmail(String majorAbb,String filePath,List<String> list,String moduleName,String jobNo){
		if(null!=list&&list.size()>0){//list中有错误信息，则将该错误信息循环后拼接存入导入失败记录的数据库中
			//获得三个需要使用的service
			DictionaryService dictionaryService = (DictionaryService) SpringUtil.getBean("dictionaryService");
			DataManagerService dataManagerService = (DataManagerService) SpringUtil.getBean("dataManagerService");
			ImportFailureRecordService importFailureRecordService = (ImportFailureRecordService) SpringUtil.getBean("importFailureRecordService");
			//提示信息头
			String titleMsg="文件"+filePath+"有数据导入失败！"+"\n"+"错误信息如下："+"\n";
			StringBuffer buffer = new StringBuffer();
			for(int i = 0;i<list.size();i++){
				buffer.append(i+1).append(". ").append(list.get(i)).append("\n");
			}
			ImportFailureRecord record = new ImportFailureRecord();//初始化属性
			record.setPrompt_content(buffer.toString());
			record.setFile_path(filePath);
			record.setModule(moduleName);
			record.setPrompt_date(new Date());
			//按照专业和工作号去查询数据负责人的表头，表头关联表体获得负责人(可能多个)，有多少个就插入多少条数据；同时发邮件
			List<Map<String, Object>> majorList = dictionaryService.findProfessionalByAbb(majorAbb);//更具专业缩写获取专业信息，为了获取id
			//通过job_no和专业id获取相应的数据负责的表头及明细;
			List<Map<String, Object>> ManagerDetails = dataManagerService.findByJobNoAndMajor(jobNo, (String)majorList.get(0).get("id"));
			DataManagerDetail detail = new DataManagerDetail();
			MailSend mailSend = new MailSend();
			for(int i =0;i<ManagerDetails.size();i++){
				if(StringUtils.isNotEmpty((String)ManagerDetails.get(i).get("export_fail"))&&StringUtils.equals("Y", (String)ManagerDetails.get(i).get("export_fail"))){
					detail.setId((String)ManagerDetails.get(i).get("id"));
					record.setManager_detail(detail);
					importFailureRecordService.create(record);
					mailSend.sendMessage("数据跟踪表导入失败", titleMsg+buffer.toString(), (String)ManagerDetails.get(i).get("email"));
				}
			}
		}
	}
}
