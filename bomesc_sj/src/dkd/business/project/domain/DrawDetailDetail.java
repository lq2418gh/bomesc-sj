package dkd.business.project.domain;

import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import dkd.paltform.base.domain.BusinessEntity;
import dkd.paltform.dictionary.domain.Dictionary;

@Entity
@Table(name="b_draw_list_detail")
public class DrawDetailDetail extends BusinessEntity{
	
	private static final long serialVersionUID = 1L;
	
	@Column(nullable=false,length=80)
	private String list_head;//图纸明细头部编号
	
	@ManyToOne
	@JoinColumn(name="draw_type",nullable=false)
	private Dictionary draw_type;//图纸类型
	
	@Column(nullable=false)
	private Integer total_draw_quantity;//图纸总数量
	
	@Column(nullable=false)
	private Integer pre_draw_forecast;//上月图纸计划完成数量
	
	@Column(nullable=false)
	private Integer pre_draw_actual;//上月图纸实际完成数量
	
	@Column(nullable=false,precision=20,scale=2)
	private BigDecimal pre_discrepancy;//上月偏差值
	
	@Column(nullable=false)
	private Integer this_draw_forecast;//当月图纸计划完成数量
	
	@Column(nullable=false)
	private Integer this_draw_actual;//当月图纸实际完成数量
	
	@Column(nullable=false,precision=20,scale=2)
	private BigDecimal this_discrepancy;//当月偏差值
	
	@Column(nullable=false)
	private Integer cumulative_draw_forecast;//图纸计划完成数量
	
	@Column(nullable=false)
	private Integer cumulative_draw_actual;//图纸实际完成数量
	
	@Column(nullable=false,precision=20,scale=2)
	private BigDecimal cumulative_discrepancy;//当前偏差值

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "drawDetailHead", nullable = false)
	private DrawDetailHead drawDetailHead;
	
	public String getList_head() {
		return list_head;
	}

	public void setList_head(String list_head) {
		this.list_head = list_head;
	}

	public Dictionary getDraw_type() {
		return draw_type;
	}

	public void setDraw_type(Dictionary draw_type) {
		this.draw_type = draw_type;
	}

	public Integer getTotal_draw_quantity() {
		return total_draw_quantity;
	}

	public void setTotal_draw_quantity(Integer total_draw_quantity) {
		this.total_draw_quantity = total_draw_quantity;
	}

	public Integer getPre_draw_forecast() {
		return pre_draw_forecast;
	}

	public void setPre_draw_forecast(Integer pre_draw_forecast) {
		this.pre_draw_forecast = pre_draw_forecast;
	}

	public Integer getPre_draw_actual() {
		return pre_draw_actual;
	}

	public void setPre_draw_actual(Integer pre_draw_actual) {
		this.pre_draw_actual = pre_draw_actual;
	}

	public BigDecimal getPre_discrepancy() {
		return pre_discrepancy;
	}

	public void setPre_discrepancy(BigDecimal pre_discrepancy) {
		this.pre_discrepancy = pre_discrepancy;
	}

	public Integer getThis_draw_forecast() {
		return this_draw_forecast;
	}

	public void setThis_draw_forecast(Integer this_draw_forecast) {
		this.this_draw_forecast = this_draw_forecast;
	}

	public Integer getThis_draw_actual() {
		return this_draw_actual;
	}

	public void setThis_draw_actual(Integer this_draw_actual) {
		this.this_draw_actual = this_draw_actual;
	}

	public BigDecimal getThis_discrepancy() {
		return this_discrepancy;
	}

	public void setThis_discrepancy(BigDecimal this_discrepancy) {
		this.this_discrepancy = this_discrepancy;
	}

	public Integer getCumulative_draw_forecast() {
		return cumulative_draw_forecast;
	}

	public void setCumulative_draw_forecast(Integer cumulative_draw_forecast) {
		this.cumulative_draw_forecast = cumulative_draw_forecast;
	}

	public Integer getCumulative_draw_actual() {
		return cumulative_draw_actual;
	}

	public void setCumulative_draw_actual(Integer cumulative_draw_actual) {
		this.cumulative_draw_actual = cumulative_draw_actual;
	}

	public BigDecimal getCumulative_discrepancy() {
		return cumulative_discrepancy;
	}

	public void setCumulative_discrepancy(BigDecimal cumulative_discrepancy) {
		this.cumulative_discrepancy = cumulative_discrepancy;
	}

	public DrawDetailHead getDrawDetailHead() {
		return drawDetailHead;
	}

	public void setDrawDetailHead(DrawDetailHead drawDetailHead) {
		this.drawDetailHead = drawDetailHead;
	}

}
