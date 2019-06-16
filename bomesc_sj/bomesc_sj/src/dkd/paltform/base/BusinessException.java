package dkd.paltform.base;

public class BusinessException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;
	//序号,异常信息提示到单据的明细行上时,指定对应的序号，若没有明细行，则为-1
	private int index;
	//异常信息对应的字段
	private String field;
		
	/**
	 * 
	 * @param index
	 * @param field
	 * @param message
	 */
	public BusinessException(int index, String field, String message) {
		super(message);
		this.index = index;
		this.field = field;
		
	}
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	public String getField() {
		return field;
	}
	public void setField(String field) {
		this.field = field;
	}
}
