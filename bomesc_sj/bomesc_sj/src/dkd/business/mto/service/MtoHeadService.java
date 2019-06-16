package dkd.business.mto.service;
import java.io.IOException;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.Column;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import dkd.business.dataParamConfig.domain.AllocationPriorityDetail;
import dkd.business.dataParamConfig.domain.AllocationPriorityHead;
import dkd.business.dataParamConfig.service.AllocationPriorityHeadService;
import dkd.business.designDataManager.domain.StDataTrack;
import dkd.business.designDataManager.domain.StDataTrackSupp;
import dkd.business.designDataManager.service.StDataTrackSuppService;
import dkd.business.designDataManager.service.StDataTrackTableService;
import dkd.business.material.service.MaterialArchivesService;
import dkd.business.mto.dao.MtoHeadDao;
import dkd.business.mto.domain.MtoDetail;
import dkd.business.mto.domain.MtoHead;
import dkd.business.project.service.ProjectService;
import dkd.paltform.authority.domain.User;
import dkd.paltform.base.BusinessException;
import dkd.paltform.base.dao.BaseDao;
import dkd.paltform.base.service.BaseService;
import dkd.paltform.dictionary.domain.Dictionary;
import dkd.paltform.dictionary.service.DictionaryService;
import dkd.paltform.fileinfo.domain.FileInfo;
import dkd.paltform.util.Common;
import dkd.paltform.util.SpringUtil;
import dkd.paltform.util.bigExcelReaderExt.BigExcelReaderUtil;

@Service
@Transactional	
public class MtoHeadService extends BaseService<MtoHead>{
	@Autowired
	private MtoHeadDao mtoHeadDao;
	@Autowired
	private DictionaryService dictionaryService;
	@Autowired
	private ProjectService projectService;
	@Autowired
	private AllocationPriorityHeadService allocationPriorityHeadService;
	@Autowired
	private StDataTrackTableService stDataTrackTableService;
	@Autowired
	private StDataTrackSuppService stDataTrackSuppService;
	@Autowired
	private MaterialArchivesService materialArchivesService;
	@Override
	public BaseDao<MtoHead> getDao() {
		return mtoHeadDao;
	}
	/**
	 * @date 2017年12月1日
	 * @author gaoxp
	 * @param fileInfo
	 * @return
	 * 描述：料单导入主方法
	 * @throws IOException 
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public String poi(FileInfo fileInfo) throws Exception{
		checkFileFormat(fileInfo);//校验是否是xlsx格式的文件
		XSSFWorkbook book = new XSSFWorkbook(fileInfo.getFileL().getInputStream());//获取一个workbook
		XSSFSheet sheet = book.getSheetAt(0);//获取第一张表
		MtoHead mtoHead = getMtoHead(sheet);//校验表头信息，同时返回表头实体,用来插入料单表头中
		List<MtoDetail> details=getMtoDetails(sheet,mtoHead);//校验明细信息，同时返回明细列表list,用来插入料单明细表中
		if(null==details||details.size()==0){
			throw new BusinessException(-1, "", "没有检测到明细，请检查导入文件明细相关信息！");
		}
		mtoHead.setDetails(details);
		getDao().create(mtoHead);
		return mtoHead.getMto_no();//返回mtoNo
	}
	/**
	 * @date 2017年12月1日
	 * @author gaoxp
	 * @param sheet
	 * @param mtoHead
	 * @return
	 * 描述：导入功能校验明细列，获取所有的明细,返回明细list
	 * @throws Exception 
	 */
	private List<MtoDetail> getMtoDetails(XSSFSheet sheet,MtoHead mtoHead) throws Exception{
		//获取明细开始的列
		int DetailBeginRow=0;
		int totalRow=sheet.getPhysicalNumberOfRows();
		for (int i = sheet.getFirstRowNum(); i < totalRow; i++) {
			if(null != sheet.getRow(i) && null != sheet.getRow(i).getCell(0) && StringUtils.contains(Common.getCellContent(sheet.getRow(i).getCell(0)), "序号")){//找到了明细的表头
				DetailBeginRow=i;
				break;
			}
		}
		//循环明细
		String rowNo;//行号
		String materialName;//物料名称，行号和物料名称两个共同作为是否已经到最后一条明细的校验规则
		List<MtoDetail> details = new ArrayList<MtoDetail>();//明细list
		List<String> rowNoList=new ArrayList<String>();//存放行号，每次读取后校验是否有相同的行号
		for (int i = DetailBeginRow+1; i < totalRow; i++) {
			 rowNo=Common.getCellContent(sheet.getRow(i).getCell(0)).trim();//行号
			 materialName=Common.getCellContent(sheet.getRow(i).getCell(1)).trim();//物料名称
			 if(StringUtils.isNotEmpty(rowNo)&&rowNo.matches("[1-9]\\d*")&&StringUtils.isNotEmpty(materialName)){//行号为数字，物料名称不为空，则认为该行有明细
				 if(rowNoList.contains(rowNo)){
					 throw new BusinessException(-1, "", "行号"+rowNo+"重复，请检查！");
				 }
				 rowNoList.add(rowNo);
				 MtoDetail mtoDetail = new MtoDetail();
				//获取关联外键
				 mtoDetail.setMto_head(mtoHead);
				//获取行号
				 mtoDetail.setMto_row_no(Integer.parseInt(rowNo));
				//获取物料名称
				 checkFieldLength("物料名称","material_name",materialName,"MtoDetail");
				 mtoDetail.setMaterial_name(materialName);
				//获取杆件号
				 if(StringUtils.isNotEmpty(Common.getCellContent(sheet.getRow(i).getCell(2)).trim())){
					 checkFieldLength("杆件号","member_num",Common.getCellContent(sheet.getRow(i).getCell(2)).trim(),"MtoDetail");
					 mtoDetail.setMember_num(Common.getCellContent(sheet.getRow(i).getCell(2)).trim());
				 }
				//获取规格尺寸
				 if(StringUtils.isNotEmpty(Common.getCellContent(sheet.getRow(i).getCell(3)).trim())){
					 checkFieldLength("规格尺寸","size",Common.getCellContent(sheet.getRow(i).getCell(3)).trim(),"MtoDetail");
					 mtoDetail.setSize(Common.getCellContent(sheet.getRow(i).getCell(3)).trim());
				 }
				//获取材质
				 if(StringUtils.isNotEmpty(Common.getCellContent(sheet.getRow(i).getCell(4)).trim())){
					 checkFieldLength("材质","material",Common.getCellContent(sheet.getRow(i).getCell(4)).trim(),"MtoDetail");
					 mtoDetail.setMaterial(Common.getCellContent(sheet.getRow(i).getCell(4)).trim());
				 }
				//获取执行标准
				 if(StringUtils.isNotEmpty(Common.getCellContent(sheet.getRow(i).getCell(5)).trim())){
					 checkFieldLength("执行标准","standard",Common.getCellContent(sheet.getRow(i).getCell(5)).trim(),"MtoDetail");
					 mtoDetail.setStandard(Common.getCellContent(sheet.getRow(i).getCell(5)).trim());
				 }
				//获取技术要求
				 if(StringUtils.isNotEmpty(Common.getCellContent(sheet.getRow(i).getCell(6)).trim())){
					 checkFieldLength("技术要求","description",Common.getCellContent(sheet.getRow(i).getCell(6)).trim(),"MtoDetail");
					 mtoDetail.setDescription(Common.getCellContent(sheet.getRow(i).getCell(6)).trim());
				 }
				//获取单位
				 if(StringUtils.isEmpty(Common.getCellContent(sheet.getRow(i).getCell(7)).trim())){
					 throw new BusinessException(-1, "单位", "序号为"+rowNo+"的明细单位为空，请检查！");
				 }
				 checkFieldLength("单位","unit",Common.getCellContent(sheet.getRow(i).getCell(7)).trim(),"MtoDetail");
				 mtoDetail.setUnit(Common.getCellContent(sheet.getRow(i).getCell(7)).trim());
				//获取设计数量
				 if(!isNum(Common.getCellContent(sheet.getRow(i).getCell(8)).trim())){
					 throw new BusinessException(-1, "设计数量", "序号为"+rowNo+"的设计数量为空或者格式不正确，请检查！");
				 }
				 mtoDetail.setDesign_qty(Integer.parseInt(Common.getCellContent(sheet.getRow(i).getCell(8)).trim()));
				//获取建议余量
				 if(!isNum(Common.getCellContent(sheet.getRow(i).getCell(9)).trim())){
					 throw new BusinessException(-1, "建议余量", "序号为"+rowNo+"的建议余量为空或者格式不正确，请检查！");
				 }
				 mtoDetail.setRecommend_surplus(Integer.parseInt(Common.getCellContent(sheet.getRow(i).getCell(9)).trim()));
				//获取采购数量
				 if(!isNum(Common.getCellContent(sheet.getRow(i).getCell(10)).trim())){
					 throw new BusinessException(-1, "采购数量", "序号为"+rowNo+"的采购数量为空或者格式不正确，请检查！");
				 }
				 mtoDetail.setPurchase_qty(Integer.parseInt(Common.getCellContent(sheet.getRow(i).getCell(10)).trim()));
				//获取推荐厂家
				 if(StringUtils.isNotEmpty(Common.getCellContent(sheet.getRow(i).getCell(11)).trim())){
					 checkFieldLength("推荐厂家","recommend_vendor",Common.getCellContent(sheet.getRow(i).getCell(11)).trim(),"MtoDetail");
					 mtoDetail.setRecommend_vendor(Common.getCellContent(sheet.getRow(i).getCell(11)).trim());
				 }
				//获取证书要求
				 if(StringUtils.isNotEmpty(Common.getCellContent(sheet.getRow(i).getCell(12)).trim())){
					 checkFieldLength("证书要求","certificate_requirement",Common.getCellContent(sheet.getRow(i).getCell(12)).trim(),"MtoDetail");
					 mtoDetail.setCertificate_requirement(Common.getCellContent(sheet.getRow(i).getCell(12)).trim());
				 }
				 //获取到货日期，到货日期没有固定格式，有可能是文字说明，所以实体字段类型设置为字符串
				 if(StringUtils.isNotEmpty(Common.getCellContent(sheet.getRow(i).getCell(13)).trim())){
					 mtoDetail.setArrival_date(Common.getCellContent(sheet.getRow(i).getCell(13)).trim());
				 }
				 //获取库存使用量
				 if(StringUtils.isNotEmpty(Common.getCellContent(sheet.getRow(i).getCell(14)).trim())){
					 if(!Common.getCellContent(sheet.getRow(i).getCell(14)).trim().matches("^[+]?(([0-9]+)([.]([0-9]+))?|([.]([0-9]+))?)$")){
						 throw new BusinessException(-1, "库存使用量", "序号为"+rowNo+"的库存使用量格式不正确，请检查！"); 
					 }
					 mtoDetail.setStock_qty(Integer.parseInt(Common.getCellContent(sheet.getRow(i).getCell(14)).trim()));
				 }
				 //获取物料编码
				 if(StringUtils.isEmpty(Common.getCellContent(sheet.getRow(i).getCell(15)).trim())){
					 throw new BusinessException(-1, "物料编码", "序号为"+rowNo+"的物料编码为空，请检查！"); 
				 }
				 if(null==materialArchivesService.findByField("material_code", Common.getCellContent(sheet.getRow(i).getCell(15)).trim().toUpperCase())){
					 throw new BusinessException(-1, "物料编码", "序号为"+rowNo+"的物料编码在系统物料档案中不存在，请检查！");
				 }
				 mtoDetail.setMaterial_code(Common.getCellContent(sheet.getRow(i).getCell(15)).trim().toUpperCase());
				 //获取备注
				 if(StringUtils.isNotEmpty(Common.getCellContent(sheet.getRow(i).getCell(16)).trim())){
					 checkFieldLength("备注","remark",Common.getCellContent(sheet.getRow(i).getCell(16)).trim(),"MtoDetail");
					 mtoDetail.setRemark(Common.getCellContent(sheet.getRow(i).getCell(16)).trim());
				 }
				 details.add(mtoDetail);
			 }
			 continue;
		}
		return details;
	}
	/**
	 * @date 2017年12月2日
	 * @author gxp
	 * @param str
	 * @return
	 * 描述：判断是否是大于0的数字
	 */
	private boolean isNum(String str){
		if(StringUtils.isEmpty(str)){
			return false;
		}
		if(str.matches("^[+]?(([0-9]+)([.]([0-9]+))?|([.]([0-9]+))?)$")){
			return true;
		}
		return false;
	}
	/**
	 * 
	 * @date 2017年12月1日
	 * @author gaoxp
	 * @param sheet
	 * 描述：从excel读取表头信息：校验excel中的表头信息,同时返回表头实体；
	 * @throws Exception 
	 */
	private MtoHead getMtoHead(XSSFSheet sheet) throws Exception{

		String mtoHeadMsg=Common.getCellContent(sheet.getRow(1).getCell(0));
		if(StringUtils.isEmpty(mtoHeadMsg)||mtoHeadMsg.length()<12||!StringUtils.equals("Application", mtoHeadMsg.substring(0, 11))){
			throw new BusinessException(-1, "", "导入的excel文件可能内容有误，请检查！");
		}
		MtoHead mtoHead = new MtoHead();
		mtoHead.setState("新建");
		//初始化采购部门，一般情况下是设计部
		String purchaseCompany = mtoHeadMsg.substring(mtoHeadMsg.indexOf("申购部门:")+5, mtoHeadMsg.indexOf("Date/日期:")).trim();
		if(StringUtils.isNotEmpty(purchaseCompany)){
			mtoHead.setPurchase_company(purchaseCompany);
		}
		//校验采购日期，同时初始化实体采购日期
		String purchaseDate = mtoHeadMsg.substring(mtoHeadMsg.indexOf("Date/日期")+8,mtoHeadMsg.indexOf("Used For/使用项目")).trim();
		if(StringUtils.isEmpty(purchaseDate)){
			throw new BusinessException(-1, "日期", "日期为空，请检查！");
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			mtoHead.setPurchase_date(sdf.parse(purchaseDate));
		} catch (Exception e) {
			throw new BusinessException(-1, "日期", "日期解析失败，请检查日期是否符合yyyy-MM-dd格式！");
		}
		//校验项目工作号是否为空
		String jobNo=mtoHeadMsg.substring(mtoHeadMsg.indexOf("Job No.工作号")+11,mtoHeadMsg.length()).replace(" ","").trim();
		if(StringUtils.isEmpty(jobNo)){
			throw new BusinessException(-1, "工作号", "工作号为空，请检查！");
		}
		//校验项目名称是否为空
		String projectName=mtoHeadMsg.substring(mtoHeadMsg.indexOf("Project Name")+13, mtoHeadMsg.indexOf("Doc.No")).trim();
		if(StringUtils.isEmpty(projectName)){
			throw new BusinessException(-1, "使用项目", "使用项目为空，请检查！");
		}
		//通过项目工作号获取该项目信息，和项目名称匹配，校验是否正确
		Map<String, Object> projectData = projectService.getProjectData(jobNo);
		if(null==projectData){
			throw new BusinessException(-1, "工作号", "根据工作号未检索到相应项目，请检查！");
		}
		if(!StringUtils.equals(projectName, (String) projectData.get("project_name"))){
			throw new BusinessException(-1, "项目名称和项目工作号", "使用项目和工作号不匹配，请检查！");
		}
		mtoHead.setJob_no(jobNo);
		mtoHead.setProject_name((String) projectData.get("project_name"));
		mtoHead.setProject((String) projectData.get("id"));
		//文件编号（料单申购单号）是否为空，及是否已经存在该料单校验，倒数第四位是否符合要求，校验通过后初始化料单号属性
		String mtoNo=mtoHeadMsg.substring(mtoHeadMsg.indexOf("文件编号:")+6, mtoHeadMsg.indexOf("Job No")).trim();
		if(StringUtils.isEmpty(mtoNo)){
			throw new BusinessException(-1, "文件编号", "文件编号为空，请检查！");
		}
		if(null!=findByNo(mtoNo)){
			throw new BusinessException(-1, "料单", "该文件编号已经存在，请检查！");
		}
		List<String> mtoLast4 = Arrays.asList("0","1","2","3","4","5","6","7","8","9","B","C","b","c");//料单行号倒数第四位只能是这几个,用来做校验
		if(!mtoLast4.contains(mtoNo.substring(mtoNo.length()-4, mtoNo.length()-3))){//倒数第四位不是上述的值则不能导入
			throw new BusinessException(-1, "料单", "该文件编号倒数第四位不符合规则！");
		}
		mtoHead.setMto_no(mtoNo);
		//通过文件编号获取相应的专业的缩写,获取该专业的字典id，实例化一个字典对象属性
		String majorAbb=mtoNo.substring(mtoNo.lastIndexOf("-")-2, mtoNo.lastIndexOf("-"));
		List<Map<String, Object>> majorList=dictionaryService.findProfessionalByAbb(majorAbb);
		if(null==majorList||majorList.size()==0){
			throw new BusinessException(-1, "专业", "文件编号中未检查到专业缩写信息或者编号格式有误，请检查！");
		}
		Dictionary majorDic = new Dictionary();
		majorDic.setId((String) majorList.get(0).get("id"));
		mtoHead.setMajor(majorDic);
		//获取采购类型，同时进行校验，校验通过后初始化实体采购类型属性
		String purchaseType=Common.getCellContent(sheet.getRow(4).getCell(3));
		if(StringUtils.isEmpty(purchaseType)){
			throw new BusinessException(-1, "采购类型", "采购类型为空，请检查！");
		}
		mtoHead.setPurchase_type(purchaseType);
		//应急采购原因说明拼接初始化
		String emergencyReason="";
		for (int i = 0; i < 2; i++) {
			if(StringUtils.isNotEmpty(Common.getCellContent(sheet.getRow(6+i).getCell(3)).trim())){
				emergencyReason+=Common.getCellContent(sheet.getRow(6+i).getCell(3)).trim()+";";
			}
		}
		checkFieldLength("应急采购原因","emergency_reason",emergencyReason,"MtoHead");
		mtoHead.setEmergency_reason(emergencyReason);
		//采购技术要求拼接
		String purchaseTech="";
		for(int i=0;i<10;i++){
			if(StringUtils.isNotEmpty(Common.getCellContent(sheet.getRow(9+i).getCell(3)).trim())){
				purchaseTech+=Common.getCellContent(sheet.getRow(9+i).getCell(3)).trim()+";";
			}
		}
		checkFieldLength("采购技术","purchase_tech",purchaseTech,"MtoHead");
		mtoHead.setPurchase_tech(purchaseTech);
		//初始化是否检查库存
		String ifCheckStock = Common.getCellContent(sheet.getRow(19).getCell(3)).trim();
		if(StringUtils.isNotEmpty(ifCheckStock)){
			if(StringUtils.equals("是", ifCheckStock)){
				mtoHead.setIf_check_stock(true);
			}else if(StringUtils.equals("否", ifCheckStock)){
				mtoHead.setIf_check_stock(false);
			}else{
				throw new BusinessException(-1, "是否检查库存", "是否检查库存值有误，请检查！");
			}
		}else{
			throw new BusinessException(-1, "是否检查库存", "是否检查库存为空，请检查！");
		}
		//初始化是否使用库存
		String ifUseStock = Common.getCellContent(sheet.getRow(20).getCell(3)).trim();
		if(StringUtils.isNotEmpty(ifUseStock)){
			if(StringUtils.equals("是", ifUseStock)){
				mtoHead.setIf_use_stock(true);
			}else if(StringUtils.equals("否", ifUseStock)){
				mtoHead.setIf_use_stock(false);
			}else{
				throw new BusinessException(-1, "是否使用库存", "是否使用库存值有误，请检查！");
			}
		}else{
			throw new BusinessException(-1, "是否使用库存", "是否使用库存为空，请检查！");
		}
		//初始化库存使用说明
		String stockMemo = Common.getCellContent(sheet.getRow(21).getCell(3)).trim();
		if(StringUtils.isNotEmpty(stockMemo)){
			checkFieldLength("库存使用说明","stock_memo",stockMemo,"MtoHead");
			mtoHead.setStock_memo(stockMemo);
		}
		return mtoHead;
	}
	/**
	 * @date 2017年12月1日
	 * @author gaoxp
	 * @param fileInfo
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
	 * @date 2017年12月1日
	 * @author gaoxp
	 * @param name
	 * @return
	 * 描述：根据料单号获取料单表头
	 */
	private MtoHead findByNo(String mtoNo){
		return getDao().findByField("mto_no", mtoNo);
	}
	/**
	 * @date 2017年12月4日
	 * @author gaoxp
	 * @param id  单据id
	 * @param opr 参数：confirm:确认单据操作，concelConfirm:取消确认单据操作
	 * 描述：根据id确认或者取消确认该材料申购单
	 */
	public String confirmMto(String id,String opr,User user){
		MtoHead mtoHead = getDao().findByID(id);
		if(StringUtils.isNotEmpty(mtoHead.getState())&&StringUtils.equals(mtoHead.getState(), "确认")&&StringUtils.equals("confirm", opr)){
			throw new BusinessException(-1, "单据状态", "该单据已确认，请检查！");	
		}
		if(StringUtils.isNotEmpty(mtoHead.getState())&&StringUtils.equals(mtoHead.getState(), "新建")&&StringUtils.equals("confirm", opr)){
			mtoHead.setState("确认");
			mtoHead.setConfirm_user(user.getName());
			mtoHead.setConfirm_date(new Date());
			//这里需要添加料单拆分物料到数据跟踪表的方法
			splitMto(mtoHead);
		}
		if(StringUtils.isNotEmpty(mtoHead.getState())&&StringUtils.equals(mtoHead.getState(), "新建")&&StringUtils.equals("concelConfirm", opr)){
			throw new BusinessException(-1, "单据状态", "该单据为新建单据，请检查！");
		}
		if(StringUtils.isNotEmpty(mtoHead.getState())&&StringUtils.equals(mtoHead.getState(), "确认")&&StringUtils.equals("concelConfirm", opr)){
			mtoHead.setState("新建");
			mtoHead.setConfirm_user(null);
			mtoHead.setConfirm_date(null);
			//这里需要添加撤回料单拆分的的数据跟踪表的数据的方法
			backMto(mtoHead);
		}
		update(mtoHead);
		if(StringUtils.equals("confirm", opr)){
			return "确认成功！";
		}else{
			return "取消确认成功！";
		}
	}
	/**
	 * @date 2017年12月7日
	 * @author gaoxp
	 * @param mtoHead
	 * 描述：料单取消确认时，撤销匹配到数据跟踪表的料单数据
	 */
	private void backMto(MtoHead mtoHead){
		String mtoNo=mtoHead.getMto_no();
		Map<String,String> mtoInfo=getMtoInfo(mtoHead);//获取料单信息，包含料单号，专业
		List<MtoDetail> details = mtoHead.getDetails();
		if(StringUtils.equals("b", mtoNo.substring(mtoNo.length()-4, mtoNo.length()-3).toLowerCase())){//补料的材料申购单
			for(int i = 0; i < details.size(); i++){//遍历所有的料单明细
				mtoInfo.put("mto_row_no", details.get(i).getMto_row_no()+"");//将行号放入查询的map中
				mtoInfo.put("material_code", details.get(i).getMaterial_code());
				if(StringUtils.equals(mtoInfo.get("majorAbb"), "St")){//结构专业
					List<StDataTrackSupp> trackDatas = stDataTrackSuppService.getTrackListByMto(mtoInfo, null);//查询料单号和行号对应的结构专业数据跟踪表数据
					backStFedBatchDataTrack(trackDatas,details.get(i).getMto_row_no()+"");//撤回方法
				}else if(StringUtils.equals(mtoInfo.get("majorAbb"), "Pi")){//管线专业
					
				}else if(StringUtils.equals(mtoInfo.get("majorAbb"), "Ei")){//电仪专业
					
				}else if(StringUtils.equals(mtoInfo.get("majorAbb"), "Ar")){//舾装专业
					
				}else if(StringUtils.equals(mtoInfo.get("majorAbb"), "Havc")){//空调专业
					
				}else{
					throw new BusinessException(-1,"料单号", "料单号专业缩写有误或者料单号格式有误，请检查！");
				}		
			}
	   }else{
		   for(int i = 0; i < details.size(); i++){//遍历所有的料单明细
			   mtoInfo.put("mto_row_no", details.get(i).getMto_row_no()+"");//将行号放入查询的map中
			   mtoInfo.put("material_code", details.get(i).getMaterial_code());
			   if(StringUtils.equals(mtoInfo.get("majorAbb"), "St")){//结构专业
				   List<StDataTrack> trackDatas = stDataTrackTableService.getTrackListByMto(mtoInfo, null);//查询料单号和行号对应的结构专业数据跟踪表数据
				   backStDataTrack(trackDatas,details.get(i).getMto_row_no()+"");//撤回方法
			   }else if(StringUtils.equals(mtoInfo.get("majorAbb"), "Pi")){//管线专业
					
			   }else if(StringUtils.equals(mtoInfo.get("majorAbb"), "Ei")){//电仪专业
					
			   }else if(StringUtils.equals(mtoInfo.get("majorAbb"), "Ar")){//舾装专业
					
			   }else if(StringUtils.equals(mtoInfo.get("majorAbb"), "Havc")){//空调专业
					
			   }else{
					throw new BusinessException(-1,"料单号", "料单号专业缩写有误或者料单号格式有误，请检查！");
			   }		
		   }
		}
	}
	/**
	 * @date 2017年12月8日
	 * @author gaoxp
	 * @param trackDatas
	 * @param mtoRowNo
	 * 描述：结构专业料单取消确认时，撤回匹配到数据跟踪表的qty数据
	 */
	private void backStDataTrack(List<StDataTrack> trackDatas,String mtoRowNo){
		checkIfHaveTrackData(trackDatas,mtoRowNo);//校验该行号的明细数据跟踪表是否有数据
		for(StDataTrack stDataTrack:trackDatas){
			stDataTrack.setQuantity_in_dm_mto(0);
			stDataTrackTableService.update(stDataTrack);
		}
	}
	/**
	 * @date 2017年12月8日
	 * @author gaoxp
	 * @param trackDatas
	 * @param mtoRowNo
	 * 描述：取消确认结构专业补料单时，撤掉分配到结构专业补料数据跟踪表的qty
	 */
	private void backStFedBatchDataTrack(List<StDataTrackSupp> trackDatas,String mtoRowNo){
		checkIfHaveTrackData(trackDatas,mtoRowNo);//校验该行号的明细数据跟踪表是否有数据
		for(StDataTrackSupp stFedBatchDataTrack:trackDatas){//将所有的明细数量都返回
			stFedBatchDataTrack.setQuantity_in_dm_mto(0);
			stDataTrackSuppService.update(stFedBatchDataTrack);
		}
	}
	/**
	 * @date 2017年12月6日
	 * @author gaoxp
	 * @param mtoHead
	 * 描述：料单确认时，将料单明细拆分到相应的数据跟踪表中的明细
	 */
	private void splitMto(MtoHead mtoHead){
		String mtoNo=mtoHead.getMto_no();
		List<String> orderColumn = getPriorityList(mtoHead);//获取按优先级的排序字段
		Map<String,String> mtoInfo=getMtoInfo(mtoHead);//获取料单号，专业缩写的map，用来拼接sql
		List<MtoDetail> details = mtoHead.getDetails();
		if(StringUtils.equals("b", mtoNo.substring(mtoNo.length()-4, mtoNo.length()-3).toLowerCase())){//料单编号倒数第四位为B,此时为补料单
			for(int i = 0; i < details.size(); i++){//遍历补料单的所有明细
				mtoInfo.put("mto_row_no", details.get(i).getMto_row_no()+"");//将行号放入查询的map中
				mtoInfo.put("material_code", details.get(i).getMaterial_code());//将物料编码放入查询的map中
				if(StringUtils.equals(mtoInfo.get("majorAbb"), "St")){//结构专业
					List<StDataTrackSupp> trackDatas = stDataTrackSuppService.getTrackListByMto(mtoInfo, orderColumn);//查询料单号和行号对应的结构专业数据跟踪表数据
					splitMtoToStFedBatchDataTrack(trackDatas,details.get(i).getMto_row_no()+"",details.get(i).getPurchase_qty(),mtoHead.getProject_name());//
				}else if(StringUtils.equals(mtoInfo.get("majorAbb"), "Pi")){//管线专业
					
				}else if(StringUtils.equals(mtoInfo.get("majorAbb"), "Ei")){//电仪专业
					
				}else if(StringUtils.equals(mtoInfo.get("majorAbb"), "Ar")){//舾装专业
					
				}else if(StringUtils.equals(mtoInfo.get("majorAbb"), "Havc")){//空调专业
					
				}else{
					throw new BusinessException(-1,"料单号", "料单号专业缩写有误或者料单号格式有误，请检查！");
				}				
			}
		}else{//客户的材料申购单或者正常的材料申购单
			for (int i = 0; i < details.size(); i++) {//遍历料单的所有明细
				mtoInfo.put("mto_row_no", details.get(i).getMto_row_no()+"");//将行号放入查询的map中
				mtoInfo.put("material_code", details.get(i).getMaterial_code());
				if(StringUtils.equals(mtoInfo.get("majorAbb"), "St")){//结构专业
					List<StDataTrack> trackDatas = stDataTrackTableService.getTrackListByMto(mtoInfo, orderColumn);//查询料单号和行号对应的结构专业数据跟踪表数据
					splitMtoToStDataTrack(trackDatas,details.get(i).getMto_row_no()+"",details.get(i).getPurchase_qty(),mtoHead.getProject_name());
				}else if(StringUtils.equals(mtoInfo.get("majorAbb"), "Pi")){//管线专业
					
				}else if(StringUtils.equals(mtoInfo.get("majorAbb"), "Ei")){//电仪专业
					
				}else if(StringUtils.equals(mtoInfo.get("majorAbb"), "Ar")){//舾装专业
					
				}else if(StringUtils.equals(mtoInfo.get("majorAbb"), "Havc")){//空调专业
					
				}else{
					throw new BusinessException(-1,"料单号", "料单号专业缩写有误或者料单号格式有误，请检查！");
				}
			}
		}
	}
	/**
	 * @date 2017年12月7日
	 * @author gaoxp
	 * @param trackDatas
	 * @param mtoRowNo
	 * @param mtoPurchaseQty
	 * 描述：结构专业补料单拆分到结构专业补料数据跟踪表
	 */
	private void splitMtoToStFedBatchDataTrack(List<StDataTrackSupp> trackDatas,String mtoRowNo,int mtoPurchaseQty,String projectName){
		checkIfHaveTrackData(trackDatas,mtoRowNo);//校验该行号的明细数据跟踪表是否有数据
		checkMtoPurchaseQty(mtoPurchaseQty,mtoRowNo);//校验料单明细的采购数量是否大于0
		Set<String> bulkNoList = new HashSet<String>();//保存母材编号
		for (StDataTrackSupp stFedBatchDataTrack : trackDatas) {
			checkIfProjectNameFit(stFedBatchDataTrack.getProject_name(),projectName,mtoRowNo);
			//母材编号不为空，且不是首次分配，直接设置数量为相应的数量即可，不扣减料单中数量
			if(StringUtils.isNotEmpty(stFedBatchDataTrack.getBulk_material_no())&&bulkNoList.contains(stFedBatchDataTrack.getBulk_material_no())){
				stFedBatchDataTrack.setQuantity_in_dm_mto(stFedBatchDataTrack.getQty());
			//母材编号不为空，且是首次分配该母材编号对应数量，需要从料单中减去相应数量，将该母材编号加入set集合中，表明该母材编号已经扣减过数量	
			}else if(StringUtils.isNotEmpty(stFedBatchDataTrack.getBulk_material_no())&&!bulkNoList.contains(stFedBatchDataTrack.getBulk_material_no())){
				mtoPurchaseQty-=stFedBatchDataTrack.getQty();
				checkMtoPurchaseQty(mtoPurchaseQty,mtoRowNo);//校验是否足量
				stFedBatchDataTrack.setQuantity_in_dm_mto(stFedBatchDataTrack.getQty());
				bulkNoList.add(stFedBatchDataTrack.getBulk_material_no());
			//不含母材编号，扣减料单中采购数量	
			}else{
				mtoPurchaseQty-=stFedBatchDataTrack.getQty();
				checkMtoPurchaseQty(mtoPurchaseQty,mtoRowNo);//校验是否足量
				stFedBatchDataTrack.setQuantity_in_dm_mto(stFedBatchDataTrack.getQty());
			}
			stDataTrackSuppService.update(stFedBatchDataTrack);
		}
		if(mtoPurchaseQty>0){//若拆分完毕后仍然数量剩余,分配到最后一条上，暂时这样处理
			Collections.reverse(trackDatas);//将list倒序排列
			String bulkNo = trackDatas.get(0).getBulk_material_no();
			if(StringUtils.isEmpty(bulkNo)){//假如最后一个不包含母材编号的，富裕量分配到上面
				trackDatas.get(0).setQuantity_in_dm_mto(trackDatas.get(0).getQuantity_in_dm_mto()+mtoPurchaseQty);
				stDataTrackSuppService.update(trackDatas.get(0));
			}else{//假如最后一条包含母材编号
				for(StDataTrackSupp stDataTrackSupp:trackDatas){
					if(StringUtils.equals(stDataTrackSupp.getBulk_material_no(), bulkNo)){
						stDataTrackSupp.setQuantity_in_dm_mto(stDataTrackSupp.getQuantity_in_dm_mto()+mtoPurchaseQty);
						stDataTrackSuppService.update(stDataTrackSupp);
					}
				}
			}
		}
//		if(mtoPurchaseQty>0){//再次校验是否分配完毕
//			throw new BusinessException(-1,"物料匹配", "料单明细中行号为"+mtoRowNo+"的明细按母材编号匹配完毕后仍然有数量剩余，请检查！");
//		}
	}
	/**
	 * @date 2017年12月7日
	 * @author gaoxp
	 * @param trackDatas  对应的数据跟踪表list
	 * @param mtoRowNo    对应的料单的行号
	 * @param mtoPurchaseQty  料单采购数量
	 * 描述：拆分结构专业料单到相应的数据跟踪表方法,详细注释参考结构专业补料单拆分到结构专业补料数据跟踪表
	 */
	private void splitMtoToStDataTrack(List<StDataTrack> trackDatas,String mtoRowNo,int mtoPurchaseQty,String projectName){
		checkIfHaveTrackData(trackDatas,mtoRowNo);//校验该行号的明细数据跟踪表是否有数据
		checkMtoPurchaseQty(mtoPurchaseQty,mtoRowNo);//校验料单明细的采购数量是否大于0
		Set<String> bulkNoList = new HashSet<String>();//保存母材编号
		for (StDataTrack stDataTrack : trackDatas) {//遍历数据跟踪表
			checkIfProjectNameFit(stDataTrack.getProject_name(),projectName,mtoRowNo);
			if(StringUtils.isNotEmpty(stDataTrack.getBulk_material_no())&&bulkNoList.contains(stDataTrack.getBulk_material_no())){
				stDataTrack.setQuantity_in_dm_mto(stDataTrack.getQty());
			}else if(StringUtils.isNotEmpty(stDataTrack.getBulk_material_no())&&!bulkNoList.contains(stDataTrack.getBulk_material_no())){
				mtoPurchaseQty-=stDataTrack.getQty();
				checkMtoPurchaseQty(mtoPurchaseQty,mtoRowNo);//校验是否足量
				stDataTrack.setQuantity_in_dm_mto(stDataTrack.getQty());
				bulkNoList.add(stDataTrack.getBulk_material_no());
			}else{
				mtoPurchaseQty-=stDataTrack.getQty();
				checkMtoPurchaseQty(mtoPurchaseQty,mtoRowNo);//校验是否足量
				stDataTrack.setQuantity_in_dm_mto(stDataTrack.getQty());
			}
			stDataTrackTableService.update(stDataTrack);
		}
		if(mtoPurchaseQty>0){//若拆分完毕后仍然数量剩余
			Collections.reverse(trackDatas);//将list倒序排列
			String bulkNo = trackDatas.get(0).getBulk_material_no();
			if(StringUtils.isEmpty(bulkNo)){
				trackDatas.get(0).setQuantity_in_dm_mto(trackDatas.get(0).getQuantity_in_dm_mto()+mtoPurchaseQty);
				stDataTrackTableService.update(trackDatas.get(0));
			}else{
				for (StDataTrack stDataTrack : trackDatas) {
					if(StringUtils.equals(bulkNo, stDataTrack.getBulk_material_no())){
						stDataTrack.setQuantity_in_dm_mto(stDataTrack.getQuantity_in_dm_mto()+mtoPurchaseQty);
						stDataTrackTableService.update(stDataTrack);
					}
				}
			}
		}
//		if(mtoPurchaseQty>0){//再次校验是否分配完毕
//			throw new BusinessException(-1,"物料匹配", "料单明细中行号为"+mtoRowNo+"的明细按母材编号匹配完毕后仍然有数量剩余，请检查！");
//		}
	}
	/**
	 * @date 2018年1月15日
	 * @author gaoxp
	 * @param aProjectName
	 * @param bProjectName
	 * 描述：校验料单项目和查询出的数据跟踪表数据的项目是否一致
	 */
	private void checkIfProjectNameFit(String aProjectName,String bProjectName,String mtoRowNo){
		if(!StringUtils.equals(aProjectName, bProjectName)){
			throw new BusinessException(-1,"项目名称", "料单中行号为"+mtoRowNo+"的明细跟查询到的数据跟踪表项目名称不匹配，请检查！");
		}
	}
	/**
	 * @date 2017年12月7日
	 * @author gaoxp
	 * @param mtoPurchaseQty
	 * @param rowNo
	 * 描述：校验料单采购数量是否大于0
	 */
	private void checkMtoPurchaseQty(int mtoPurchaseQty,String rowNo){
		if(mtoPurchaseQty<0){
			throw new BusinessException(-1,"料单采购数量", "行号为"+rowNo+"的料单明细采购数量不足,请检查！");
		}
	}
	/**
	 * @date 2017年12月6日
	 * @author gaoxp
	 * @param list
	 * @param rowNo
	 * 描述：校验料单具体某一明细是否在数据跟踪表是否有数据
	 */
	@SuppressWarnings("rawtypes")
	private void checkIfHaveTrackData(List list,String rowNo){
		if(null==list||list.size()==0){//校验是否检测到数据
			throw new BusinessException(-1,"数据跟踪表", "行号为"+rowNo+"的明细在数据跟踪表中未检测到数据,请检查料单号,行号,物料编码是否匹配！");
		}
	}
	/**
	 * @date 2017年12月6日
	 * @author gaoxp
	 * @param mtoHead
	 * @return
	 * 描述：获取排序后的列的优先级的顺序   1最大
	 */
	private List<String> getPriorityList(MtoHead mtoHead){
		List<AllocationPriorityHead> priorityHeadList = allocationPriorityHeadService.checkIsData("", mtoHead.getJob_no(), mtoHead.getMajor().getId());//根据项目id和专业字典id查询相应的物料分配优先级
		if(null==priorityHeadList||priorityHeadList.size()==0){
			throw new BusinessException(-1,"物资分配优先级", "由项目工作号和专业未查询到对应的物资分配优先级规则,请检查！");
			
		}
		List<AllocationPriorityDetail> priorityDetailList = priorityHeadList.get(0).getAllocationPriorityDetail();
		Collections.sort(priorityDetailList, new Comparator<AllocationPriorityDetail>(){
			@Override
			public int compare(AllocationPriorityDetail o1,
					AllocationPriorityDetail o2) {
				return o1.getSort_no()-o2.getSort_no();
			}
		});
		List<String> orderColumn = new ArrayList<String>();
		for(AllocationPriorityDetail a:priorityDetailList){
			orderColumn.add(a.getSort_column().getCode());
		}
		return orderColumn;
	}
	/**
	 * @date 2017年12月6日
	 * @author gaoxp
	 * @param mtoHead
	 * @return
	 * 描述：获取表头中的相关信息，专业缩写和料单号,专业缩写未首字母大写，为了匹配对应的数据跟踪表类名
	 */
	private Map<String,String> getMtoInfo(MtoHead mtoHead){
		HashMap<String, String> mtoInfo = new HashMap<String,String>();
		String majorAbb=mtoHead.getMto_no().split("-")[3].toLowerCase();
		char[] majorAbbChars=majorAbb.toCharArray();//转换为首字母大写
		majorAbbChars[0]-=32;
		mtoInfo.put("majorAbb", String.valueOf(majorAbbChars));
		mtoInfo.put("mto_no", mtoHead.getMto_no());
		return mtoInfo;
	}
	/**
	 * @date 2018年1月10日
	 * @author gaoxp
	 * @param chineseName
	 * @param fieldName
	 * @param fieldValue
	 * @param className
	 * @throws Exception
	 * 描述：长度太长，抛异常
	 */
	private void checkFieldLength(String chineseName,String fieldName,String fieldValue,String className) throws Exception{
		if(!checkField(fieldName,fieldValue,className)){
			throw new BusinessException(-1, "长度超长", chineseName+"的值"+fieldValue+"过长，请检查！");
		}
	}
	/**
	 * 
	 * @date 2018年1月10日
	 * @author gaoxp
	 * @param fieldValue
	 * @param className
	 * @return
	 * 描述：
	 * @throws SecurityException 
	 * @throws NoSuchFieldException 
	 */
	private boolean checkField(String fieldName,String fieldValue,String className) throws Exception{
		Class<?> clazz =BigExcelReaderUtil.testGet(SpringUtil.getBeanProp(), className);
		Field declaredField = clazz.getDeclaredField(fieldName);
		if(null!=declaredField){
			if(null!=declaredField.getAnnotation(Column.class)){
				if(declaredField.getAnnotation(Column.class).length()<fieldValue.length()){
					return false;
				}
			}
		}
		return true;
	}
	/**
	 * @date 2018年1月10日
	 * @author gaoxp
	 * @param mto_no
	 * @return
	 * 描述：根据料单号获取料单表头及其表体
	 */
	public MtoHead findByField(String mto_no){
		return mtoHeadDao.findByField(mto_no);
	}
}
