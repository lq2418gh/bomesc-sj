package dkd.business.designDataManager.service;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dkd.business.dataParamConfig.service.ColumnDisplaySettingService;
import dkd.business.designDataManager.dao.StDataTrackTableDao;
import dkd.business.designDataManager.domain.StDataTrack;
import dkd.paltform.authority.domain.User;
import dkd.paltform.base.BusinessException;
import dkd.paltform.base.QueryResult;
import dkd.paltform.base.annotation.BeanSelect;
import dkd.paltform.base.dao.BaseDao;
import dkd.paltform.base.service.BaseService;
import dkd.paltform.util.HttpClientUtil;
import dkd.paltform.util.SpringUtil;

@Service
@Transactional
public class StDataTrackTableService extends BaseService<StDataTrack> {

	@Autowired
	private StDataTrackTableDao dataTrackTableDao;
	
	@Autowired
	private DataTrackColumnsService dataTrackColumnsService;
	
	@Autowired
	private ColumnDisplaySettingService columnDisplaySettingService;
	
	@Override
	public BaseDao<StDataTrack> getDao() {
		return dataTrackTableDao;
	}

	public QueryResult<Map<String, Object>> getScrollDataByUser(Map<String, String> param,User user) {
		return dataTrackTableDao.getScrollDataByUser(param,user);
	}
	/**
	 * @date 2017年12月6日
	 * @author gaoxp
	 * @param mtoInfo  存储料单号，行号，以及相应的专业缩写的map
	 * @param orderColumn   存储排序字段的list  这两个参数用来拼接
	 * @return
	 * 描述：根据专业缩写，料单号，料单行号按照指定的排序规则查询出排序后的数据跟踪表的list
	 */
	public List<StDataTrack> getTrackListByMto(Map<String,String> mtoInfo,List<String> orderColumn){
		return dataTrackTableDao.getTrackListByMto(mtoInfo, orderColumn);
	}
	/**
	 * @date 2017年12月12日
	 * @author gaoxp
	 * @param projectName
	 * @param moduleName
	 * @return 
	 * 描述：根据模块名称及项目名称获取数据跟踪表中的数据
	 */
	public List<Map<String, Object>> getScrollDataByPM(String projectName,String moduleName){
		return dataTrackTableDao.getTrackDataByPM(projectName, moduleName);
	}
	
	/**
	 * @date 2017-12-13
	 * @author ZHAOLW
	 * @throws IOException 
	 * @throws InvalidFormatException 
	 * @desc 导出功能（页面显示列）
	 */
	public void exportData(Map<String, String> param,User user,HttpServletRequest request,HttpServletResponse response) throws InvalidFormatException, IOException{
		String showColumn = this.getShowColumn(param, user);//用户自定义显示的列名字段+变更次数+更新时间
		String[] showColumns = showColumn.split(",");
		Map<String, String> resultMap = this.getAllColumn(param);//所有列
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		//设置excel每列的列名
		Workbook work = new SXSSFWorkbook(5000);//设置excel缓存5000，大于5000时将数据刷新到excel中
		Sheet sheet = work.createSheet();
		work.setSheetName(0, "结构数据跟踪表");
		Font font = work.createFont();
		font.setFontHeightInPoints((short)10);
		font.setFontName("宋体");
		CellStyle style= work.createCellStyle();
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN); 
		style.setBorderRight(HSSFCellStyle.BORDER_THIN); 
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN); 
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER); //设置样式垂直居中
		style.setFont(font);
		Row row = null;
		Cell cell = null;
		Map<String,Integer> columns = setHeads(showColumns,resultMap,cell,row,sheet,style);//设置表头
		param.remove("professional");
		param.remove("beanName");
		setDetails(row,sheet,cell,style,param,user,columns);//设置表体数据内容
		response.setHeader("Content-disposition", "attachment; filename=" + new String(("结构数据跟踪表" + format.format(new Date())).getBytes("gb2312"),"iso8859-1").replaceAll(" ", "")+ ".xlsx");
        response.setContentType("application/octet-stream");
        OutputStream out = response.getOutputStream();
		work.write(out);
		out.flush();
		out.close();
	}
	
	//获取所有的列名 code:专业 jobNo:项目工作号
	private Map<String, String> getAllColumn(Map<String, String> param){
		String beanName = param.get("beanName");//专业数据跟踪表对应的类
		Map<String, String> rowMap = new HashMap<String, String>();
		Properties beanProp = SpringUtil.getBeanProp();
		Class<?> c;
		try {
			c = Class.forName(beanProp.getProperty(beanName));
		} catch (ClassNotFoundException e) {
			throw new BusinessException(-1, "", "读取实体字段错误");
		}
		BeanSelect beanSelect;
		String jobNo=param.get("job_no eq text");//因页面项目为搜索框，拼接的key为job_no = text 故以其取项目值
		Map<String, Object> map = columnDisplaySettingService.findByMajor(jobNo,param.get("professional"));//查询用户自定义补充列的列名
		String columnName;
		for (Field field : c.getDeclaredFields()) {
			beanSelect = field.getAnnotation(BeanSelect.class);
			if(beanSelect != null){
				if(field.getName().indexOf("column")!=-1&&null!=map){
					columnName=String.valueOf(map.get(field.getName())==null?"":map.get(field.getName()));
					if("".equals(columnName)){
						rowMap.put("stt."+field.getName(), beanSelect.fieldName());
					}else{
						rowMap.put("stt."+field.getName(), columnName);
					}
				}else{
					rowMap.put("stt."+field.getName(), beanSelect.fieldName());
				}
			}
		}
		return rowMap;
	}
	//获取显示的列名
	public String getShowColumn(Map<String, String> param,User user){
		String professional = param.get("professional");//专业
		Map<String,Object> mapObject = new HashMap<String, Object>();
		mapObject.put("user", user.getId());
		mapObject.put("major",professional.substring(professional.indexOf("_")+1));
		Map<String, Object> dataTrackColumns = dataTrackColumnsService.findByUserAndMajor(mapObject);
		String show_columns="";
		if(null!=dataTrackColumns){
			show_columns = String.valueOf(dataTrackColumns.get("show_columns"));
		}
		return show_columns;
	}
	//设置表头（第一行标题）
	public Map<String,Integer> setHeads(String[] showColumns,Map<String, String> resultMap,
			Cell cell,Row row,Sheet sheet,CellStyle style){
		row =sheet.createRow(0);
		cell=row.createCell(0);
		cell.setCellStyle(style);
		cell.setCellValue("序号");
		cell=row.createCell(1);
		cell.setCellStyle(style);
		cell.setCellValue("变更次数");
		cell=row.createCell(2);
		cell.setCellStyle(style);
		cell.setCellValue("更新时间");
		String column;
		Map<String,Integer> columns = new HashMap<String, Integer>();//存储字段名及字段在excel中的列数
		columns.put("change_size",1);
		columns.put("update_date",2);
		for(int i=0;i<showColumns.length;i++){
			column=resultMap.get(showColumns[i]);
			cell=row.createCell(i+3);
			sheet.setColumnWidth(i+3, column.getBytes().length*256);
			cell.setCellStyle(style);
			cell.setCellValue(column);
			columns.put(showColumns[i].substring(4,showColumns[i].length()), i+3);
		}
		return columns;
	}
	//设置表体内容
	public void setDetails(Row row,Sheet sheet,Cell cell,CellStyle style,Map<String, String> param,User user,
			Map<String,Integer> columns){
		String maxNo="1000";//设置初始化数据集大小
		String begin="1";//查询开始位置
		param.put("page",begin);
		param.put("rows",maxNo);
		param.put("export","true");
		Boolean flag = true;
		QueryResult<Map<String, Object>> result;
		List<Map<String, Object>> resultList;
		Integer position;
		String value;
		int count =0;
		while(flag){
			result = this.getScrollDataByUser(param, user);
			resultList = result.getRows();
			if(!resultList.isEmpty()){
				for(int i=0;i<resultList.size();i++){
					++count;
					row =sheet.createRow(count);
					cell=row.createCell(0);
					cell.setCellStyle(style);
					cell.setCellValue(count);//设置序号
					cell=row.createCell(1);
					cell.setCellStyle(style);
					cell.setCellValue(String.valueOf(resultList.get(i).get("change_size")));//设置变更次数
					cell=row.createCell(2);
					cell.setCellStyle(style);
					cell.setCellValue(String.valueOf(resultList.get(i).get("update_date")));//设置变更时间
					for(String key:resultList.get(i).keySet()){
						position = columns.get(key);
						cell=row.createCell(position);
						cell.setCellStyle(style);
						value=String.valueOf(resultList.get(i).get(key));
						if("Y".equals(value)){
							value="是";
						}else if("N".equals(value)){
							value="否";
						}
						cell.setCellValue((value==null||"null".equals(value))?"":value);
					}
				}
				//重新设置查询结果
				begin = (Integer.parseInt(begin)+1)+"";
				param.put("page",begin);
				param.put("rows",maxNo);
				param.put("export","true");
			}else{//查到的结果为空，退出循环
				flag=false;
			}
		}
	}
	/**
	 * @date 2017年12月22日
	 * @author gaoxp
	 * @param date
	 * @return
	 * 描述：获取设计料单数量缺料的数据跟踪表数据
	 */
	public List<Map<String, Object>> getTrackDataLackMaterial(String date){
		return dataTrackTableDao.getTrackDataLackMaterial(date);
	}
	//获取显示列
	public List<Map<String, Object>> getColumns(String professional,User user) {
		return dataTrackTableDao.getColumns(professional,user);
	}

	public QueryResult<Map<String, Object>> queryForMark(Map<String, String> param, User user) {
		return dataTrackTableDao.queryForMark(param,user);
	}

	@SuppressWarnings("rawtypes")
	public String saveState(String ids,String state, User currentUser) {
		//此处做校验，去采办查询数据，是否都可以标记为删除
		String result="";
		if("删除".equals(state)){
			String[] idArray = ids.split(",");
			Map<String,String> map = new HashMap<String, String>();
			StDataTrack dataTrack;
			for(int i=0;i<idArray.length;i++){
				dataTrack=this.findByID(idArray[i]);
				if(null!=dataTrack.getMto_no()&!"".equals(dataTrack.getMto_no())
					&&null!=dataTrack.getMto_row_no()&!"".equals(dataTrack.getMto_row_no())){
					map.put(dataTrack.getMto_no(),(null==map.get(dataTrack.getMto_no())||"".equals(map.get(dataTrack.getMto_no())))?dataTrack.getMto_row_no():map.get(dataTrack.getMto_no())+","+dataTrack.getMto_row_no());
				}
			}
			result = HttpClientUtil.validateMto("ssoServerValidateMto",map);
		}else{
			dataTrackTableDao.saveState(ids,state,currentUser);
			return "保存成功!";
		}
		if("[]".equals(result)){
			dataTrackTableDao.saveState(ids,state,currentUser);
			return "保存成功!";
		}else{
			String message="料单号为:";
			JSONArray results = JSONArray.fromObject(result);
			String key="";
			for(int i=0;i<results.size();i++){
				JSONObject o = results.getJSONObject(i);
				Iterator it = o.keys(); 
				while (it.hasNext()) { 
					key = (String)it.next();
					message+=key+"料单行号为："+o.getString(key)+",";
				}
			}
			message=message.substring(0, message.length()-1)+"的料单已在采办录入，不可标记为删除状态!";
			return message;
		}
	}

	public void getBomescCbMtoDetail(Map<String, String> paramDecrypt) {
		// TODO Auto-generated method stub
		System.out.println("2++++++++++++++++++++++++"+paramDecrypt);
		dataTrackTableDao.findAll();
	}
}
