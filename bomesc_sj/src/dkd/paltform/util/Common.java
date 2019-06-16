package dkd.paltform.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;

public class Common {
	private static SimpleDateFormat sdf= new SimpleDateFormat("yyyy/MM/dd");
	public static boolean isNotEmpty(List<?> list){
		return list != null && !list.isEmpty();
	}
	
	public static void copyFile(InputStream input, File targetFile)
			throws IOException {
		// 新建文件输入流并对它进行缓冲
		BufferedInputStream inBuff = new BufferedInputStream(input);
		targetFile.createNewFile();
		// 新建文件输出流并对它进行缓冲
		FileOutputStream output = new FileOutputStream(targetFile);
		BufferedOutputStream outBuff = new BufferedOutputStream(output);

		// 缓冲数组
		byte[] b = new byte[1024 * 5];
		int len;
		while ((len = inBuff.read(b)) != -1) {
			outBuff.write(b, 0, len);
		}
		// 刷新此缓冲的输出流
		outBuff.flush();

		// 关闭流
		inBuff.close();
		outBuff.close();
		output.close();
		input.close();
	}
	//code为源编码，num为格式化为几位（前拼接0）
	public static String formatCode(String code,int num){
		String zero="";
		for(int i=0;i<num;i++){
			zero+="0";
		}
		return zero.substring(code.length())+code;
	}
	/**
	 * 转换单元格值的类型
	 * @param cell
	 * @return
	 */
	public static String getCellContent(Cell cell) {  
		if(null == cell)
			return "";
		switch (cell.getCellType()) {  
			case Cell.CELL_TYPE_BLANK:  
				return "";  
			case Cell.CELL_TYPE_BOOLEAN:  
				return String.valueOf(cell.getBooleanCellValue());  
			case Cell.CELL_TYPE_ERROR:  
				return String.valueOf(cell.getErrorCellValue());  
			case Cell.CELL_TYPE_FORMULA: 
				return String.valueOf(cell.getNumericCellValue());  
			case Cell.CELL_TYPE_NUMERIC://假如是数字格式，则有可能是日期格式的数字，需要判断是不是日期，是日期转换为日期字符串，否则返回的是值对应的日期到
	            if(HSSFDateUtil.isCellDateFormatted(cell)) {
	                return sdf.format(HSSFDateUtil.getJavaDate(cell.getNumericCellValue()));
	            }else {
	            	return String.valueOf((new DecimalFormat("0")).format(cell.getNumericCellValue()));
	            }
			case Cell.CELL_TYPE_STRING: 
				return cell.getStringCellValue().trim();  
			default:  
				return "";  
		}  
	}	
	/**
	 * 
	 * @date 2018年01月08日
	 * @author zhaolw
	 * @param request
	 * @return
	 * 描述：获取参数并解密，校验子系统编号和用户名密码（子系统查询用户中心数据用）
	 */
	public static Map<String,String> getParamDecrypt(HttpServletRequest request){
		Map<String,String> params = new HashMap<String,String>();
		Enumeration<?> enu=request.getParameterNames();
		String paramName,paramValue;
		while(enu.hasMoreElements()){
			paramName = (String)enu.nextElement();
			paramValue = request.getParameter(paramName);
			if(null!=paramValue&&!"".equals(paramValue)){
				params.put(paramName, DESUtils.decrypt(paramValue, Constant.secretKey));				
			}
		}
		return params;
	}
}
