package dkd.paltform.util.quartz;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import dkd.business.dataParamConfig.domain.DataManagerDetail;
import dkd.business.dataParamConfig.service.DataManagerService;
import dkd.business.designDataManager.service.PiDataTrackTableService;
import dkd.business.designDataManager.service.StDataTrackTableService;
import dkd.business.project.service.ProjectService;
import dkd.business.promptRecord.domain.LackMaterialRecord;
import dkd.business.promptRecord.service.ImportFailureRecordService;
import dkd.business.promptRecord.service.LackMaterialRecordService;
import dkd.paltform.dictionary.service.DictionaryService;
import dkd.paltform.util.BigExcelReader;
import dkd.paltform.util.Constant;
import dkd.paltform.util.MailSend;
import dkd.paltform.util.bigExcelReaderExt.PiImportReader;
import dkd.paltform.util.bigExcelReaderExt.StImportReader;
public class QuartzImportTrackData {
	private String runDate;//执行该定时任务时的日期，格式暂定20171211
	@Autowired
	private ProjectService projectService;
	@Autowired
	private StDataTrackTableService stDataTrackTableService;
	@Autowired
	private DictionaryService dictionaryService;
	@Autowired
	private DataManagerService dataManagerService;
	@Autowired
	private ImportFailureRecordService importFailureRecordService;
	@Autowired
	private LackMaterialRecordService lackMaterialRecordService;
	@Autowired
	private PiDataTrackTableService piDataTrackTableService;
	private Map<String,Map<String, Object>> trackDataMap;
	private Map<String,String> shopDrawRevMap;
	/**
	 * @date 2017年12月29日
	 * @author gaoxp
	 * @throws Exception
	 * 描述：定时任务主方法
	 */
	public void importTrackDataAndCheck() throws Exception{
		runDate=getRunDate();//实例化当前的执行日期，格式为yyyyMMDD,该日期下面两个方法都需要用到
		importDataTrack(new Date());//数据跟踪表导入主方法，此方法可能消耗时间较长
		createLackMaterialPromptRecord();//校验更新日期为昨天的记录是否有缺料情况
	}
	/**
	 * @date 2017年12月29日
	 * @author gaoxp
	 * @param date
	 * @throws Exception
	 * 描述：数据跟踪表导入主方法，导入完毕后会有相应的导入失败提醒
	 */
	@SuppressWarnings("unchecked")
	private void importDataTrack(Date date) throws Exception{
		File file = new File(Constant.file_path);
		File [] majorNames = file.listFiles();
		Map<String,Object> totalMap = new HashMap<String, Object>();
		for(File majorName:majorNames){
			if(majorName.isDirectory()){
				if(!Arrays.asList(Constant.major_abbs).contains(majorName.getName())){//不是专业文件夹直接跳出
					continue;
				}
				File[] projectNames=majorName.listFiles();//获取所有的项目名称文件夹
				for(File projectName:projectNames){//循环下面获得项目名称
					if(projectName.isDirectory()){
						//校验根据项目工作号是否有相应的项目
						String jobNo = projectName.getName().substring(projectName.getName().indexOf("(")+1,projectName.getName().indexOf(")"));
						String project = projectName.getName().substring(0, projectName.getName().indexOf("("));
						if(StringUtils.isEmpty(jobNo)&&null==projectService.getProjectData(jobNo)){
							continue;
						}
						File [] moduleNames=projectName.listFiles();//获取模块名称
						for(File moduleName:moduleNames){//循环所有模块文件夹
							if(moduleName.isDirectory()){
								////按照项目和专业以及模块名称获取相应的数据跟踪表数据，获取数据跟总表中的相应数据
								totalMap=getTrackData(majorName.getName(),project,moduleName.getName());//获取数据跟踪表的数据
								trackDataMap=(Map<String,Map<String, Object>>)totalMap.get("trackDataMap");
								shopDrawRevMap=(Map<String,String>)totalMap.get("shopDrawRevMap");//获取数据跟踪表的数据(key:项目工作号+模块名称+加设图纸编号+层数，value：加设图纸版本)
								File[] dates= moduleName.listFiles();//获取所有的日期文件夹
								Arrays.sort(dates, new Comparator<File>() {//将文件夹按照最后更新顺序排序，最后更新的在数组第一个
								   @Override
								   public int compare(File file1, File file2) {
								      return (int)(file2.lastModified()-file1.lastModified());
								   }
								});
								if(!StringUtils.equals(dates[0].getName(), runDate)){//判断最新的日期文件夹是否是今天的
									continue;
								}
								File[] datas=dates[0].listFiles();
								for(File data:datas){
									if(data.isDirectory()){//文件夹则直接进行下一个循环
										continue;
									}
									if(majorName.getName().toUpperCase().equals("ST")){//结构专业
										BigExcelReader stImportReader = new StImportReader(data.getAbsolutePath(),"ST",trackDataMap,moduleName.getName(),jobNo,project,date,shopDrawRevMap,new HashMap<String, Object>());
										stImportReader.parse();
									}
									if(majorName.getName().toUpperCase().equals("PI")){//管线专业
										BigExcelReader piImportReader = new PiImportReader(data.getAbsolutePath(),"PI",trackDataMap,moduleName.getName(),jobNo,project,date);
										piImportReader.parse();
									}
								}
							}
						}
					}
				}
			}
		}
	}
	/**
	 * @date 2017年12月29日
	 * @author gaoxp
	 * 描述：	缺料提醒主方法
	 * 		校验是否有缺料的方法，如果有给相关负责人发送邮件提醒，同时生成相应的提醒记录
	 */
	private void createLackMaterialPromptRecord(){
		List<Map<String, Object>> lacks = new ArrayList<Map<String, Object>>();
		String titleMsg="";
		String majorId="";
		for(String majorAbb:Constant.major_abbs){
			if(StringUtils.equals("ST", majorAbb)){//机构专业
				lacks = stDataTrackTableService.getTrackDataLackMaterial(runDate);
				titleMsg="结构专业数据跟踪表发现缺料数据！"+"\n"+"信息如下："+"\n";
				majorId=(String)dictionaryService.findProfessionalByAbb(majorAbb).get(0).get("id");
				handleMsg(lacks,majorId,titleMsg);
			}else if(StringUtils.equals("PI", majorAbb)){//管线专业
				lacks = piDataTrackTableService.getTrackDataLackMaterial(runDate);
				titleMsg="管线专业数据跟踪表发现缺料数据！"+"\n"+"信息如下："+"\n";
				majorId=(String)dictionaryService.findProfessionalByAbb(majorAbb).get(0).get("id");
				handleMsg(lacks,majorId,titleMsg);
			}else if(StringUtils.equals("AR", majorAbb)){
				
			}else if(StringUtils.equals("EI", majorAbb)){
				
			}else if(StringUtils.equals("HAVC", majorAbb)){
				
			}
		}
	}
	/**
	 * @date 2018年1月3日
	 * @author gaoxp
	 * @param lacks
	 * @param majorId
	 * @param titleMsg
	 * 描述：拼接缺料提醒信息，拼接完一条发送邮件，拼接完一条的标准是按照材料申购单编号
	 */
	private void handleMsg(List<Map<String, Object>> lacks,String majorId,String titleMsg){
		//查询所有的数据跟踪表，不是历史记录，1.设计料单有数量；2.设计料单数量为空，但是更新日期不是今天的；比较qty和设计料单数量;每一个专业都查询
		//结构专业数据跟踪表
		List<String> msgs=new ArrayList<String>();
		for(int i = 0;i<lacks.size();i++){
			Map<String, Object> lack = lacks.get(i);
			if(i==lacks.size()-1){//最后一条
				msgs.add("图纸编号"+lack.get("shop_draw_no")+",料单号"+lack.get("mto_no")+",物料编码"+lack.get("material_code")+",设计数量"+(null==lack.get("quantity_in_dm_mto")?0:lack.get("quantity_in_dm_mto"))+",理论数量"+lack.get("qty")+",请注意！\n");
				createRecordAndSendMail(titleMsg,msgs,lack,majorId);
				msgs=new ArrayList<String>();
				continue;
			}
			Map<String, Object> nextLack = lacks.get(i+1);
			if(StringUtils.equals((String)lack.get("mto_no"), (String)nextLack.get("mto_no"))){//有下一条数据，并且料单编号一样，则只是拼接缺料信息
				msgs.add("图纸编号"+lack.get("shop_draw_no")+",料单号"+lack.get("mto_no")+",物料编码"+lack.get("material_code")+",设计数量"+(null==lack.get("quantity_in_dm_mto")?0:lack.get("quantity_in_dm_mto"))+",理论数量"+lack.get("qty")+",请注意！\n");
			}else{//料单编号不相同，则创建一条缺料提醒插入数据库，同时给数据负责人发送邮件
				msgs.add("图纸编号"+lack.get("shop_draw_no")+",料单号"+lack.get("mto_no")+",物料编码"+lack.get("material_code")+",设计数量"+(null==lack.get("quantity_in_dm_mto")?0:lack.get("quantity_in_dm_mto"))+",理论数量"+lack.get("qty")+",请注意！\n");
				createRecordAndSendMail(titleMsg,msgs,lack,majorId);
				msgs=new ArrayList<String>();
			}
		}
	}
	/**
	 * @date 2017年12月22日
	 * @author gaoxp
	 * @param msg
	 * @param lack
	 * @param majorId
	 * 描述：创建缺料提醒记录，同时插入数据库，给相关人员发送邮件；按照专业和项目配置的数据负责人，有几个人就发送几次邮件
	 */
	private void createRecordAndSendMail(String titleMsg,List<String> msgs,Map<String, Object> lack,String majorId){
		LackMaterialRecord record = new LackMaterialRecord();
		StringBuffer buffer=new StringBuffer();
		buffer.append(titleMsg);
		for(int i=0;i<msgs.size();i++){//拼接序号
			buffer.append(i+1).append("、 ").append(msgs.get(i));
		}
		record.setPrompt_content(buffer.toString());//全部信息保存在字段中
		record.setPrompt_date(new Date());
		DataManagerDetail detial = new DataManagerDetail();
		//通过job_no和专业id获取相应的数据负责的明细,获取明细的为record设置;
		MailSend mailSend = new MailSend();
		List<Map<String, Object>> ManagerDetails = dataManagerService.findByJobNoAndMajor((String)lack.get("job_no"), majorId);
		for(Map<String, Object> managerDetail:ManagerDetails){
			if(StringUtils.isNotEmpty((String)managerDetail.get("lack_material"))&&StringUtils.equals("Y", (String)managerDetail.get("lack_material"))){
				detial.setId((String) managerDetail.get("id"));
				record.setManager_detail(detial);
				lackMaterialRecordService.create(record);
				mailSend.sendMessage("数据跟踪表表缺料提醒", buffer.toString(), (String)managerDetail.get("email"));
			}
		}
	}
	/**
	 * @date 2017年12月11日
	 * @author gaoxp
	 * @return
	 * 描述：获取当前日期
	 */
	private String getRunDate(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		return sdf.format(new Date());
	}
	/**
	 * @date 2017年12月12日
	 * @author gxp
	 * @return
	 * 描述：根据专业缩写，项目名称，模块编号获取相应的数据跟踪表的值,同时封装成新的map，新map中key是对应数据跟踪表主键的值，value为该条数据
	 */
	private Map<String,Object> getTrackData(String majorAbb,String project,String moduleName){
		Map<String,Object> mapTotal = new HashMap<String, Object>();
		Map<String,Map<String, Object>> map = new HashMap<String,Map<String, Object>>();//新建的存储map
		//根据项目、模块、图纸编号、层数查找图纸版本
		Map<String,String> shopDrawRevMap = new HashMap<String,String>();//新建的存储map
		if(StringUtils.equals(majorAbb.toUpperCase(), "ST")){//如果是结构专业
			List<Map<String, Object>> rows = stDataTrackTableService.getScrollDataByPM(project, moduleName);
			for(Map<String, Object> rowMap:rows){//遍历查询得到的list，将每一行的零件编号作为key，rowMap作为value存入新的map中
				map.put((String)rowMap.get("part_no"), rowMap);
				shopDrawRevMap.put((String)rowMap.get("shop_draw_no"),(String)rowMap.get("shop_draw_rev"));
			}
		}
		if(StringUtils.equals(majorAbb.toUpperCase(), "PI")){//管线专业，主键规则根据供应商不同而不同，BOMESC则七个字段拼接，假如为Free Issue则为零件编号
			List<Map<String, Object>> rows = piDataTrackTableService.getScrollDataByPM(project, moduleName);
			StringBuffer mixKeyBuffer = new StringBuffer();
			for(Map<String, Object> rowMap:rows){
				//管线专业  如果供货商是bomesc，则数据唯一性用partno确认
				if(StringUtils.isNotEmpty((String)rowMap.get("supplier"))&&StringUtils.equals("Free Issue", (String)rowMap.get("supplier"))){
					map.put((String)rowMap.get("part_no"), rowMap);
				}else if(StringUtils.isNotEmpty((String)rowMap.get("supplier"))&&StringUtils.equals("BOMESC", (String)rowMap.get("supplier"))){//否则用这七个字段拼接组成混合主键；ISO图纸编号+识别编号+管段号+焊口编号，这里是从数据库读取出来的值，不需要判断是否为空，保存时，空的值都是存的N/A
					mixKeyBuffer.append((String)rowMap.get("iso_drawing_no"))
					.append((String)rowMap.get("material_code")).append((String)rowMap.get("spool_no"))
					.append((String)rowMap.get("welding_no_one")).append((String)rowMap.get("welding_no_two"))
					.append((String)rowMap.get("welding_no_three")).append((String)rowMap.get("welding_no_four"));
					map.put(mixKeyBuffer.toString(), rowMap);
					mixKeyBuffer=new StringBuffer();
				}
			}
		}
		mapTotal.put("trackDataMap", map);
		mapTotal.put("shopDrawRevMap", shopDrawRevMap);
		return mapTotal;
	}
}
