package dkd.paltform.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xssf.eventusermodel.ReadOnlySharedStringsTable;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.model.StylesTable;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

public abstract class BigExcelReader {
	
	enum xssfDataType {
		BOOL, ERROR, FORMULA, INLINESTR, SSTINDEX, NUMBER,
	}

	public static final int ERROR = 1;
	public static final int BOOLEAN = 1;
	public static final int NUMBER = 2;
	public static final int STRING = 3;
	public static final int DATE = 4;
	public static final String DATE_FORMAT_STR = "yyyy-MM-dd HH:mm:ss";
	public static final DecimalFormat df = new DecimalFormat("00.00");
	private static final Logger log = Logger.getLogger("scheduler");
	// private DataFormatter formatter = new DataFormatter();
	private InputStream sheet;
	private XMLReader parser;
	private InputSource sheetSource;
	private int index = 0;
	private String[] columns;
	private OPCPackage pkg;
	private static String fileName;
	private static String professional;
	private void choiceColumns(String major){
		switch(major){
		case "ST" : 
			columns = Constant.st_columns;
			break;
		case "EI" :
			break;
		case "PI" :
			columns = Constant.pi_columns;
			break;
	}
	}
	/**
	 * 读大数据量Excel
	 * 
	 * @param filename
	 *            文件名
	 * @param maxColNum
	 *            读取的最大列数
	 * @throws IOException
	 * @throws OpenXML4JException
	 * @throws SAXException
	 */
	public BigExcelReader(String filename,String major) throws IOException,
			OpenXML4JException, SAXException {
		fileName=filename;
		professional=major;
		choiceColumns(major);
		pkg = OPCPackage.open(filename);
		init(pkg);
	}

	/**
	 * 读大数据量Excel
	 * 
	 * @param file
	 *            Excel文件
	 * @param maxColNum
	 *            读取的最大列数
	 * @throws IOException
	 * @throws OpenXML4JException
	 * @throws SAXException
	 */
	public BigExcelReader(File file,String major) throws IOException, OpenXML4JException,
			SAXException {
		fileName= file.getAbsolutePath();
		professional = major;
		choiceColumns(major);
		pkg = OPCPackage.open(file);
		init(pkg);
	}

	/**
	 * 读大数据量Excel
	 * 
	 * @param in
	 *            Excel文件输入流
	 * @throws IOException
	 * @throws OpenXML4JException
	 * @throws SAXException
	 */
	public BigExcelReader(InputStream in,String major,String path) throws IOException,
			OpenXML4JException, SAXException {
		fileName=path;
		professional=major;
		choiceColumns(major);
		pkg = OPCPackage.open(in);
		init(pkg);
	}

	/**
	 * 初始化 将Excel转换为XML
	 * 
	 * @param pkg
	 * @throws IOException
	 * @throws OpenXML4JException
	 * @throws SAXException
	 */
	private void init(OPCPackage pkg) throws IOException, OpenXML4JException,
			SAXException {
		XSSFReader xssfReader = new XSSFReader(pkg);
		// SharedStringsTable sharedStringsTable =
		// xssfReader.getSharedStringsTable();
		ReadOnlySharedStringsTable sharedStringsTable = new ReadOnlySharedStringsTable(pkg);
		StylesTable stylesTable = xssfReader.getStylesTable();
		sheet = xssfReader.getSheet("rId1");
		sheetSource = new InputSource(sheet);
		parser = fetchSheetParser(sharedStringsTable, stylesTable);
	}

	/**
	 * 执行解析操作
	 * 
	 * @return 读取的Excel行数
	 */
	public int parse() {
		try {
			parser.parse(sheetSource);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} finally {
			if (sheet != null) {
				try {
					sheet.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return index;
	}

	private XMLReader fetchSheetParser(ReadOnlySharedStringsTable sharedStringsTable,
			StylesTable stylesTable) throws SAXException {
		XMLReader parser = XMLReaderFactory.createXMLReader("org.apache.xerces.parsers.SAXParser");
		ContentHandler handler = new SheetHandler(sharedStringsTable,stylesTable);
		parser.setContentHandler(handler);
		return parser;
	}

	/**
	 * SAX解析的处理类 每解析一行数据后通过outputRow(String[] datas, int[] rowTypes, int
	 * rowIndex)方法进行输出
	 * 
	 * @author zpin
	 */
	private class SheetHandler extends DefaultHandler {
		private ReadOnlySharedStringsTable sharedStringsTable; // 存放映射字符串
		private StylesTable stylesTable;// 存放单元格样式
		private String readValue;// 存放读取值
		private xssfDataType dataType;// 单元格类型
		//private String[] rowDatas;// 存放一行中的所有数据
		private Map<String,String> mapData;
		private int[] rowTypes;// 存放一行中所有数据类型
		private int colIdx;// 当前所在列

		private short formatIndex;

		// private String formatString;// 对数值型的数据直接读为数值，不对其格式化，所以隐掉此处

		private SheetHandler(ReadOnlySharedStringsTable sst,
				StylesTable stylesTable) {
			this.sharedStringsTable = sst;
			this.stylesTable = stylesTable;
		}
		@Override
		public void endDocument() throws SAXException {
			super.endDocument();
			endParse();
		}
		public void startElement(String uri, String localName, String name,
				Attributes attributes) throws SAXException {
			if (name.equals("c")) {// c > 单元格
				colIdx = getColumn(attributes);
				String cellType = attributes.getValue("t");
				String cellStyle = attributes.getValue("s");

				this.dataType = xssfDataType.NUMBER;
				if ("b".equals(cellType)) {
					this.dataType = xssfDataType.BOOL;
				} else if ("e".equals(cellType)) {
					this.dataType = xssfDataType.ERROR;
				} else if ("inlineStr".equals(cellType)) {
					this.dataType = xssfDataType.INLINESTR;
				} else if ("s".equals(cellType)) {
					this.dataType = xssfDataType.SSTINDEX;
				} else if ("str".equals(cellType)) {
					this.dataType = xssfDataType.FORMULA;
				} else if (cellStyle != null) {
					int styleIndex = Integer.parseInt(cellStyle);
					XSSFCellStyle style = stylesTable.getStyleAt(styleIndex);
					this.formatIndex = style.getDataFormat();
					// this.formatString = style.getDataFormatString();
				}
			}
			// 解析到一行的开始处时，初始化数组
			else if (name.equals("row")) {
				int cols = getColsNum(attributes);// 获取该行的单元格数
				mapData = new HashMap<String,String>();
				//rowDatas = new String[cols];
				rowTypes = new int[cols];
			}
			readValue = "";
		}

		public void endElement(String uri, String localName, String name)
				throws SAXException {
			if (name.equals("v")) { // 单元格的值
				switch (this.dataType) {
					case BOOL: {
						char first = readValue.charAt(0);
						mapData.put(columns[colIdx], first == '0' ? "FALSE" : "TRUE");
						//rowDatas[colIdx] = first == '0' ? "FALSE" : "TRUE";
						rowTypes[colIdx] = BOOLEAN;
						break;
					}
					case ERROR: {
						mapData.put(columns[colIdx], "ERROR:" + readValue.toString());
						//rowDatas[colIdx] = "ERROR:" + readValue.toString();
						rowTypes[colIdx] = ERROR;
						break;
					}
					case INLINESTR: {
						mapData.put(columns[colIdx], new XSSFRichTextString(readValue).toString());
						//rowDatas[colIdx] = new XSSFRichTextString(readValue).toString();
						rowTypes[colIdx] = STRING;
						break;
					}
					case SSTINDEX: {
						int idx = Integer.parseInt(readValue);
						 String str = new XSSFRichTextString(sharedStringsTable.getEntryAt(idx)).toString();
						mapData.put(columns[colIdx], str);
						//rowDatas[colIdx] = new XSSFRichTextString(sharedStringsTable.getEntryAt(idx)).toString();
						rowTypes[colIdx] = STRING;
						break;
					}
					case FORMULA: {
						mapData.put(columns[colIdx], readValue);
						//rowDatas[colIdx] = readValue;
						rowTypes[colIdx] = STRING;
						break;
					}
					case NUMBER: {
						// 判断是否是日期格式
						if (HSSFDateUtil.isADateFormat(formatIndex, readValue)) {
							Double d = Double.parseDouble(readValue);
							Date date = HSSFDateUtil.getJavaDate(d);
							mapData.put(columns[colIdx], DateFormatUtils.format(date,DATE_FORMAT_STR));
							//rowDatas[colIdx] = DateFormatUtils.format(date,DATE_FORMAT_STR);
							rowTypes[colIdx] = DATE;
						}
						// else if (formatString != null){
						// cellData.value =
						// formatter.formatRawCellContents(Double.parseDouble(cellValue),
						// formatIndex, formatString);
						// cellData.dataType = NUMBER;
						// }
						else {
							if(readValue.length()>10){
								readValue=df.format(new BigDecimal(readValue).setScale(2, BigDecimal.ROUND_HALF_UP));
							}
							mapData.put(columns[colIdx], readValue);
							//rowDatas[colIdx] = readValue;
							rowTypes[colIdx] = NUMBER;
						}
						break;
					}
				}
			}
			// 当解析的一行的末尾时，输出数组中的数据
			else if (name.equals("row")) {
				outputRow(mapData, rowTypes, index++);
			}
		}

		public void characters(char[] ch, int start, int length)
				throws SAXException {
			readValue += new String(ch, start, length);
		}
	}

	/**
	 * 输出每一行的数据
	 * 
	 * @param datas
	 *            数据
	 * @param rowTypes
	 *            数据类型
	 * @param rowIndex
	 *            所在行
	 */
	protected abstract void outputRow(Map<String,String> mapData, int[] rowTypes,
			int rowIndex);
	protected void endParse(){
		try {
			pkg.close();
		} catch (IOException e) {
			log.error("专业为"+professional+",时间为"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())+",文件名为"+fileName+"的文件关闭失败!");
		}
		System.out.println("解析完成");
	}
	private int getColumn(Attributes attrubuts) {
		String name = attrubuts.getValue("r");
		int column = -1;
		for (int i = 0; i < name.length(); ++i) {
			if (Character.isDigit(name.charAt(i))) {
				break;
			}
			int c = name.charAt(i);
			column = (column + 1) * 26 + c - 'A';
		}
		return column;
	}

	private int getColsNum(Attributes attrubuts) {
		String spans = attrubuts.getValue("spans");
		String cols = spans.substring(spans.indexOf(":") + 1);
		return Integer.parseInt(cols);
	}
}