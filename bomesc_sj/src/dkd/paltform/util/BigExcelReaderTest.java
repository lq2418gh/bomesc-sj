package dkd.paltform.util;

import java.util.Map;



public class BigExcelReaderTest {
	public static void main(String[] args) throws Exception {
		String filepath = "D:\\ST1212.xlsx";
		BigExcelReader reader = new BigExcelReader(filepath,"ST") {
			@Override
			protected void outputRow(Map<String,String> mapData, int[] rowTypes,
					int rowIndex) {
					if(rowIndex==5){
						for (Map.Entry<String, String> entry : mapData.entrySet()) {
							System.out.println(entry.getKey()+":"+entry.getValue());
						}
					}
			}
		};
		// 执行解析
		reader.parse();
	}	
}
