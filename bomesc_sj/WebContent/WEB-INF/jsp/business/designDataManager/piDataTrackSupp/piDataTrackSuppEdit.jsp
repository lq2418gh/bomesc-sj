<%@ page language="java" contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/common/common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>管线补料数据跟踪表</title>
</head>
<body>
	<form id="piDataTrackSupp_form" method="post" action="">
		<table class="lines-both">
			<tr>
				<td>录入人:</td>
				<td><input id="entity_createuser_edit" name="entity_createuser" size="13" class="easyui-textbox" readonly="readonly"/></td>
				<td>录入日期:</td>
				<td><input id="entity_createdate_edit" name="entity_createdate" size="13" class="easyui-datebox" readonly="readonly"/></td>
				<td>项目名称:</td>
				<td>
					<input id="project_edit" name="project" class="edit_combobox" data-options="width:120,url:'${ctx}/project/show.do'" dkd-search-element="job_no = text"/>
					<input type="hidden" id="project_name_edit" name="project_name" value="" />
				</td>
				<td>项目工作号:</td>
				<td><input id="job_no_edit" name="job_no" size="13" class="easyui-validatebox textbox"  /></td>
			</tr>
			<tr>	
				<td>模块号:</td>
				<td>
					<input id="module_no_edit" name="module_no" size="13" class="easyui-textbox"   data-options="required:true"/>
				</td>
				<td>模块工作包号:</td>
				<td>
					<input id="work_package_no_edit" name="work_package_no" size="13" class="easyui-textbox"   />
				</td>
				<td>试压包号:</td>
				<td>
					<input id="test_package_no_edit" name="test_package_no" size="13" class="easyui-textbox"   data-options="required:true"/>
				</td>
				<td>ISO图纸编号:</td>
				<td>
					<input id="iso_drawing_no_edit" name="iso_drawing_no" size="13" class="easyui-textbox"   data-options="required:true"/>
				</td>
			</tr>
			<tr>
				<td>页码:</td>
				<td>
					<input id="page_no_edit" name="page_no" size="13" class="easyui-textbox"   data-options="required:true"/>
				</td>
				<td>总页数:</td>
				<td>
					<input id="total_page_edit" name="total_page" size="13" class="easyui-textbox"   data-options="required:true"/>
				</td>
				<td>ISO图纸版本:</td>
				<td>
					<input id="iso_drawing_rev_edit" name="iso_drawing_rev" size="13" class="easyui-textbox"   data-options="required:true"/>
				</td>
				<td>加设图纸编号:</td>
				<td>
					<input id="shop_draw_no_edit" name="shop_draw_no" size="13" class="easyui-textbox"   data-options="required:true"/>
				</td>
			</tr>
			<tr>
				<td>加设图纸版本:</td>
				<td>
					<input id="shop_draw_rev_edit" name="shop_draw_rev" size="13" class="easyui-textbox"   data-options="required:true"/>
				</td>
				<td>系统代码:</td>
				<td>
					<input id="system_code_edit" name="system_code" size="13" class="easyui-textbox"   data-options="required:true"/>
				</td>
				<td>管线级别:</td>
				<td>
					<input id="pipe_class_edit" name="pipe_class" size="13" class="easyui-textbox"   data-options="required:true"/>
				</td>
				<td>管线号:</td>
				<td>
					<input id="line_no_edit" name="line_no" size="13" class="easyui-textbox"   data-options="required:true"/>
				</td>
			</tr>
			<tr>
				<td>管段号:</td>
				<td>
					<input id="spool_no_edit" name="spool_no" size="13" class="easyui-textbox"   data-options="required:true"/>
				</td>
				<td>零件编号:</td>
				<td>
					<input id="part_no_edit" name="part_no" size="13" class="easyui-textbox"  readonly="readonly" data-options="required:true"/>
				</td>
				<td>零件名称:</td>
				<td>
					<input id="part_name_edit" name="part_name" size="13" class="easyui-combobox" data-options="editable:false,url:'${ctx}/dictionary/searchDictionary.do?code=part_name'" dkd-search-element="major = text"/>
				</td>
				<td>零件类型:</td>
				<td>
					<input id="part_type_edit" name="part_type" size="13" class="easyui-combobox" data-options="editable:false,url:'${ctx}/dictionary/searchDictionary.do?code=part_type'" dkd-search-element="major = text"/>
				</td>
			</tr>
			<tr>
				<td>业主零件编号:</td>
				<td>
					<input id="client_part_no_edit" name="client_part_no" size="13" class="easyui-textbox"   data-options="required:true"/>
				</td>
				<td>识别编号:</td>
				<td>
					<input id="ident_code_edit" name="ident_code" size="13" class="easyui-textbox"  />
				</td>
				<td>物料编码:</td>
				<td>
					<input id="material_code_edit" name="material_code" size="13" class="easyui-textbox"    />
				</td>
				<td>位号:</td>
				<td>
					<input id="tag_no_edit" name="tag_no" size="13" class="easyui-textbox"    />
				</td>
			</tr>
			<tr>
				<td>零件主尺寸:</td>
				<td>
					<input id="part_main_size_edit" name="part_main_size" size="13" class="easyui-numberbox" data-options="min:0.00,precision:2,max:9999999999" data-options="required:true" />
				</td>
				<td>零件副尺寸:</td>
				<td>
					<input id="part_vice_size_edit" name="part_vice_size" size="13" class="easyui-numberbox" data-options="min:0.00,precision:2,max:9999999999"  />
				</td>
				<td>压力级别:</td>
				<td>
					<input id="pressure_class_edit" name="pressure_class" size="13" class="easyui-textbox"   />
				</td>
				<td>主壁厚级别:</td>
				<td>
					<input id="main_thickness_grade_edit" name="main_thickness_grade" size="13" class="easyui-textbox"  data-options="required:true" />
				</td>
			</tr>
			<tr>
				<td>次壁厚级别:</td>
				<td>
					<input id="vice_thickness_grade_edit" name="vice_thickness_grade" size="13" class="easyui-textbox"  data-options="required:true" />
				</td>
				<td>主连接形式:</td>
				<td>
					<input id="main_connection_type_edit" name="main_connection_type" size="13" class="easyui-combobox" data-options="editable:false,url:'${ctx}/dictionary/searchDictionary.do?code=main_connection_type'" dkd-search-element="major = text"/>
				</td>
				<td>次连接形式:</td>
				<td>
					<input id="vice_connection_type_edit" name="vice_connection_type" size="13" class="easyui-combobox" data-options="editable:false,url:'${ctx}/dictionary/searchDictionary.do?code=vice_connection_type'" dkd-search-element="major = text"/>
				</td>
				<td>材质:</td>
				<td>
					<input id="grade_edit" name="grade" size="13" class="easyui-textbox"  />
				</td>
			</tr>
			<tr>
				<td>特殊要求:</td>
				<td>
					<input id="special_requirement_edit" name="special_requirement" size="13" class="easyui-textbox"  />
				</td>
				<td>焊接编号1:</td>
				<td>
					<input id="welding_no_one_edit" name="welding_no_one" size="13" class="easyui-textbox"   data-options="required:true"  />
				</td>
				<td>焊接编号2:</td>
				<td>
					<input id="welding_no_two_edit" name="welding_no_two" size="13" class="easyui-textbox"  />
				</td>
				<td>焊接编号3:</td>
				<td>
					<input id="welding_no_three_edit" name="welding_no_three" size="13" class="easyui-textbox"  data-options="required:true" />
				</td>
			</tr>
			<tr>
				<td>焊接编号4:</td>
				<td>
					<input id="welding_no_four_edit" name="welding_no_four" size="13" class="easyui-textbox"  data-options="required:true"  />
				</td>
				<td>数量:</td>
				<td>
					<input id="qty_edit" name="qty" size="13" class="easyui-numberbox" data-options="min:0,precision:0,max:9999999999"  data-options="required:true" />
				</td>
				<td>设计料单数量:</td>
				<td>
					<input id="quantity_in_dm_mto_edit" name="quantity_in_dm_mto" size="13" class="easyui-numberbox" data-options="min:0,precision:0,max:9999999999"  readonly="readonly" />
				</td>
				<td>采办料单数量:</td>
				<td>
					<input id="quantity_in_pd_mto_edit" name="quantity_in_pd_mto" size="13" class="easyui-numberbox"   data-options="min:0,precision:0,max:9999999999"  readonly="readonly" />
				</td>
			</tr>
			<tr>
				<td>净长度:</td>
				<td>
					<input id="net_length_edit" name="net_length" size="13" class="easyui-numberbox"   data-options="min:0,precision:0,max:9999999999"  data-options="required:true" />
				</td>
				<td>余量:</td>
				<td>
					<input id="additional_length_edit" name="additional_length" size="13" class="easyui-numberbox"  data-options="min:0,precision:0,max:9999999999"  data-options="required:true" />
				</td>
				<td>总长度:</td>
				<td>
					<input id="final_length_edit" name="final_length" size="13" class="easyui-numberbox"  data-options="min:0,precision:0,max:9999999999"  data-options="required:true" />
				</td>
				<td>施工领料单编号:</td>
				<td>
					<input id="material_requisition_no_edit" name=material_requisition_no size="13" class="easyui-textbox"  data-options="required:true" />
				</td>
			</tr>
			<tr>
				<td>套料图纸编号:</td>
				<td>
					<input id="nesting_draw_no_edit" name="nesting_draw_no" size="13" class="easyui-textbox"   />
				</td>
				<td>套料图纸版本:</td>
				<td>
					<input id="nesting_draw_rev_edit" name="nesting_draw_rev" size="13" class="easyui-textbox"   />
				</td>
				<td>对应母材编号:</td>
				<td>
					<input id="bulk_material_no_edit" name="bulk_material_no" size="13" class="easyui-textbox"  />
				</td>
				<td>余料编号:</td>
				<td>
					<input id="remnant_part_no_edit" name="remnant_part_no" size="13" class="easyui-textbox"    />
				</td>
			</tr>
			<tr>
				<td>余料尺寸:</td>
				<td>
					<input id="remnant_part_size_edit" name=remnant_part_size size="13" class="easyui-numberbox" data-options="min:0,precision:0,max:9999999999"   />
				</td>
				<td>条形码编号:</td>
				<td>
					<input id="bar_code_no_edit" name=bar_code_no size="13" class="easyui-textbox" />
				</td>
				<td>单位净重:</td>
				<td>
					<input id="unit_net_weight_edit" name=unit_net_weight size="13" class="easyui-numberbox" data-options="min:0.00,precision:2,max:9999999999"   />
				</td>
				<td>净重:</td>
				<td>
					<input id="net_weight_edit" name=net_weight size="13" class="easyui-numberbox" data-options="min:0.00,precision:2,max:9999999999"   />
				</td>
			</tr>
			<tr>
				<td>表面处理:</td>
				<td>
					<input id="surface_treatment_edit" name=surface_treatment size="13" class="easyui-textbox"  />
				</td>
				<td>油漆配套:</td>
				<td>
					<input id="coating_system_edit" name=coating_system size="13" class="easyui-textbox"  />
				</td>
				<td>单位净表面积:</td>
				<td>
					<input id="unit_net_area_edit" name=unit_net_area size="13" class="easyui-numberbox" data-options="min:0.00,precision:2,max:9999999999"   />
				</td>
				<td>净表面积:</td>
				<td>
					<input id="net_area_edit" name=net_area size="13" class="easyui-numberbox" data-options="min:0.00,precision:2,max:9999999999"   />
				</td>
			</tr>
			<tr>
				<td>保温类型:</td>
				<td>
					<input id="insultation_type_edit" name=insultation_type size="13" class="easyui-numberbox" data-options="min:0.00,precision:2,max:9999999999"   />
				</td>
				<td>保温厚度:</td>
				<td>
					<input id="insultation_thk_edit" name=insultation_thk size="13" class="easyui-numberbox" data-options="min:0,precision:0,max:9999999999"   />
				</td>
				<td>保温表面积:</td>
				<td>
					<input id="insultation_surface_area_edit" name=insultation_surface_area size="13" class="easyui-numberbox" data-options="min:0.00,precision:2,max:9999999999"   />
				</td>
				<td>保温延米:</td>
				<td>
					<input id="insultation_equivalent_length_edit" name=insultation_equivalent_length size="13" class="easyui-numberbox" data-options="min:0.00,precision:2,max:9999999999"   />
				</td>
			</tr>
			<tr>
				<td>供货方:</td>
				<td>
					<select id="supplier_edit" class="easyui-combobox"  name="supplier" data-options="width:120,editable:false,panelHeight:'auto'">  
						<option value="">-请选择-</option>  
						<option value="Free Issue">Free Issue</option>  
						<option value="BOMESC">BOMESC</option>  
					</select>  
				</td>
				<td>料单编号:</td>
				<td>
					<input id="mto_no_edit" name="mto_no" size="13" class="easyui-textbox"    />
				</td>
				<td>料单行号:</td>
				<td>
					<input id="mto_row_no_edit" name="mto_row_no" size="13" class="easyui-textbox"    />
				</td>
				<td>采购余量:</td>
				<td>
					<input id="recommend_surplus_edit" name="recommend_surplus" size="13"  class="easyui-numberbox" data-options="min:0.00,precision:2,max:9999999999"    />
				</td>
			</tr>
			<tr>
				<td>材料是否因升版作废:</td>
				<td>
					<select id="wasted_by_drawing_update_edit" class="easyui-combobox"  name="wasted_by_drawing_update" data-options="width:120,editable:false,panelHeight:'auto'">  
						<option value="Y">是</option>  
						<option value="N">否</option>  
					</select>  
				</td>
				<td>材料是否因升版增加:</td>
				<td>
					<select id="added_by_drawing_update_edit" class="easyui-combobox"  name="added_by_drawing_update" data-options="width:120,editable:false,panelHeight:'auto'">  
						<option value="Y">是</option>  
						<option value="N">否</option>  
					</select>  
				</td>
				<td>业主付款编号:</td>
				<td>
					<input id="pay_item_no_edit" name="pay_item_no" size="13" class="easyui-textbox"    />
				</td>
				<td>备注:</td>
				<td>
					<input id="remark_edit" name="remark" size="13" class="easyui-textbox"    />
				</td>
			</tr>
			<tr>
				<td>厂家:</td>
				<td>
					<input id="vendor_edit" name="vendor" size="13" class="easyui-textbox"    />
				</td>
				<td>设备厂址:</td>
				<td>
					<input id="site_edit" name="site" size="13" class="easyui-textbox"    />
				</td>
				<td>设备网址:</td>
				<td>
					<input id="website_edit" name="website" size="13" class="easyui-textbox"    />
				</td>
				<td>联系电话:</td>
				<td>
					<input id="tellphone_no_edit" name="tellphone_no" size="13" class="easyui-textbox"    />
				</td>
			</tr>
			<tr>
				<td name="col1">补充列1:</td>
				<td>
					<input id="column1_edit" name="column1" size="13" class="easyui-textbox"    />
				</td>
				<td name="col2">补充列2:</td>
				<td>
					<input id="column2_edit" name="column2" size="13" class="easyui-textbox"   />
				</td>
				<td name="col3">补充列3:</td>
				<td>
					<input id="column3_edit" name="column3" size="13" class="easyui-textbox"    />
				</td>
				<td name="col4">补充列4:</td>
				<td>
					<input id="column4_edit" name="column4" size="13" class="easyui-textbox"    />
				</td>
			</tr>
			<tr>
				<td name="col5">补充列5:</td>
				<td>
					<input id="column5_edit" name="column5" size="13" class="easyui-textbox"    />
				</td>
				<td name="col6">补充列6:</td>
				<td>
					<input id="column6_edit" name="column6" size="13" class="easyui-textbox"    />
				</td>
				<td name="col7">补充列7:</td>
				<td>
					<input id="column7_edit" name="column7" size="13" class="easyui-textbox"    />
				</td>
				<td name="col8">补充列8:</td>
				<td>
					<input id="column8_edit" name="column8" size="13" class="easyui-textbox"    />
				</td>
			</tr>
			<tr>
				<td name="col9">补充列9:</td>
				<td>
					<input id="column9_edit" name="column9" size="13" class="easyui-textbox"    />
				</td>
				<td name="col10">补充列10:</td>
				<td>
					<input id="column10_edit" name="column10" size="13" class="easyui-textbox"    />
				</td>
				<td name="col11">补充列11:</td>
				<td>
					<input id="column11_edit" name="column11" size="13" class="easyui-textbox"    />
				</td>
				<td name="col12">补充列12:</td>
				<td>
					<input id="column12_edit" name="column12" size="13" class="easyui-textbox"    />
				</td>
			</tr>
			<tr>
				<td name="col13">补充列13:</td>
				<td>
					<input id="column13_edit" name="column13" size="13" class="easyui-textbox"    />
				</td>
				<td name="col14">补充列14:</td>
				<td>
					<input id="column14_edit" name="column14" size="13" class="easyui-textbox"    />
				</td>
				<td name="col15">补充列15:</td>
				<td>
					<input id="column15_edit" name="column15" size="13" class="easyui-textbox"    />
				</td>
				<td name="col16">补充列16:</td>
				<td>
					<input id="column16_edit" name="column16" size="13" class="easyui-textbox"    />
				</td>
			</tr>
			<tr>
				<td name="col17">补充列17:</td>
				<td>
					<input id="column17_edit" name="column17" size="13" class="easyui-textbox"    />
				</td>
				<td name="co18">补充列18:</td>
				<td>
					<input id="column18_edit" name="column18" size="13" class="easyui-textbox"    />
				</td>
				<td name="col19">补充列19:</td>
				<td>
					<input id="column19_edit" name="column19" size="13" class="easyui-textbox"    />
				</td>
				<td name="col20">补充列20:</td>
				<td>
					<input id="column20_edit" name="column20" size="13" class="easyui-textbox"    />
				</td>
			</tr>
			<tr>
				<td colspan="10" style="text-align: center">
					<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" onclick="updatePiDTS()">保存</a>
					<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-back" onclick="jumpPage('管线补料数据跟踪表','${ctx}/login/showList.do?url=business/designDataManager/piDataTrackSupp')">返回</a>
				</td>
			</tr>
		</table>
	</form>
	<script type="text/javascript">
	//初始化方法
	var data={} ,columns={};
	$(function(){
		$.ajax({
			url:"${ctx}/piDataTrackSupp/findPiDTS.do",
			data:{"id":"${id}"},
			dataType:"json",
			success:function(result){
				data=result;
				$("#piDataTrackSupp_form").form("load",result);
				$('#project_edit').combobox('setValue',result.job_no);
		        $('#project_name_edit').val(result.project_name);
		       	findColumns("professional_pi",result.job_no);
			}
		})
 	});
	
  	//1选择项目名称 onchange事件
	$("#project_edit").combobox({
		onChange: function (newValue, oldValue) {
			fillProjectNo(newValue, oldValue);
		}
	});
 	function fillProjectNo(newValue, oldValue){
 		$("#job_no_edit").val(newValue);
 		$("#project_name_edit").val($("#project_edit").combobox("getText"));
 	}
	//根据结构专业和工作号 查询补充列的column字段定义的名称
	function findColumns(code,jobNo){
		$.ajax({
 			type:"post",
 			url:"${ctx}/columnDisplaySet/getColumnsByMajor.do",
 			async:false,
 			contentType : "application/json;charset=utf-8",
 			data:JSON.stringify({"code":code,"jobNo":jobNo}),
 			success:function(result){
 				columns=result;
			}
 		});
		if(!isEmpty(columns)){
			if(!isEmpty(columns["column"+i])){
				for(var i =1 ; i< 21 ;i++){
					$("td[name=col"+i+"]").html(columns["column"+i]);
				}
			}
		}
	}
		
	//供应方 发生变化处理
	$("#supplier_edit").combobox({
		onChange:function(newValue,oldValue){
			if(newValue=="BOMESC"){
				$("#ident_code_edit").textbox("setValue","");
			}else if(newValue=="Free Issue"){
				$("#mto_no_edit").textbox("setValue","");
				$("#mto_row_no_edit").textbox("setValue","");
				$("#material_code_edit").textbox("setValue","");
			}
		}
	});
	//更新方法
	function updatePiDTS(){
		if(!$("#piDataTrackSupp_form").form("validate")){
			return false;
		}
		var supplier=$("#supplier_edit").combobox("getValue");
		if(isEmpty(supplier)){
			$.messager.show({
				title:"警告",
				msg:"请选择供货方"
			});
			return false;
		}
		if("Free Issue"==supplier){
			var ident_code=$("#ident_code_edit").textbox("getValue");
			if(isEmpty(ident_code)){
				$.messager.show({
					title:"警告",
					msg:"请填写标签编号"
				});
				return false;
			}
		}else if("BOMESC"==supplier){
			var mto_no=$("#mto_no_edit").textbox("getValue");
			var mto_row_no=$("#mto_row_no_edit").textbox("getValue");
			var material_code=$("#material_code_edit").textbox("getValue");
			if(isEmpty(mto_no)){
				$.messager.show({
					title:"警告",
					msg:"请填写料单编号"
				});
				return false;
			}
			if(isEmpty(mto_row_no)){
				$.messager.show({
					title:"警告",
					msg:"请填写料单行号"
				});
				return false;
			}
			if(isEmpty(material_code)){
				$.messager.show({
					title:"警告",
					msg:"请填写物资编号"
				});
				return false;
			}
		}
		$.ajax({
			url:"${ctx}/piDataTrackSupp/updatePiDTS.do",
			data:getData("piDataTrackSupp_form", data),
			contentType : "application/json;charset=utf-8",
			success:function(result){
				handleReturn(result,function(){
					jumpPage("管线补料数据跟踪表编辑页","${ctx}/login/showList.do?url=business/designDataManager/piDataTrackSupp");
				});
			}
		})
	}
	</script>
</body>
</html>