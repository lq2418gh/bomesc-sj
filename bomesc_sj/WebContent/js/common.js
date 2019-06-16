var store = window.sessionStorage;
function checkRole(){
	var auths = store.getItem("auths_sj") || "";
	$("a").each(function(){
		//根据角色判断是否可以审核
		var dkdCheck = $(this).attr("dkd-check");
		if(typeof(dkdCheck) != "undefined"){
			var $this = $(this);
			$.post(rootPath + "/flowset/checkRole.do",{"work_no":dkdCheck},function(result){
				if(!result.success){
					$this.remove();
				}
			});
		}
		//权限判断
		var dkdAuth = $(this).attr("dkd-auth");
		if(typeof(dkdAuth) != "undefined"){
			if(auths.indexOf("," + dkdAuth + ",") == -1){
				$(this).remove();
			}
		}
	});
}
function loadSuccess(){
	var auths = store.getItem("auths_sj") || "";
	$("div.datagrid-cell a").each(function(){
		//权限判断
		var dkdAuth = $(this).attr("dkd-auth");
		if(typeof(dkdAuth) != "undefined"){
			if(auths.indexOf("," + dkdAuth + ",") == -1){
				$(this).remove();
			}
		}
	});
	$(".editButton").linkbutton();
}
function loadFn(){
	$(".datebox_month").each(function(){
		var $this = $(this);
		$this.datebox({
            onShowPanel : function() {// 显示日趋选择对象后再触发弹出月份层的事件，初始化时没有生成月份层    
                span.trigger("click"); // 触发click事件弹出月份层    
                setTimeout(function() {// 延时触发获取月份对象，因为上面的事件触发和对象生成有时间间隔    
                    tds = p.find("div.calendar-menu-month-inner td");    
                    tds.click(function(e) {
                        e.stopPropagation(); // 禁止冒泡执行easyui给月份绑定的事件    
                        var year = /\d{4}/.exec(span.html())[0]// 得到年份    
                        , month = parseInt($(this).attr("abbr"), 10); // 月份    
                        $this.datebox("hidePanel")// 隐藏日期对象    
                        .datebox("setValue", year + "-" + month); // 设置日期的值    
                    });    
                }, 0);
            },    
            parser : myparser_month,    
            formatter : myformatter_month    
        });
		var p = $this.datebox('panel'); // 日期选择对象    
        var span = p.find('span.calendar-text'); // 显示月份层的触发控件 
	});
	$(".edit_combobox").each(function(){
		var $this = $(this);
		var text = $this.next('.combo').find("input[type!='hidden']").val();
		var value = $this.next('.combo').find("input[type='hidden']").val();
		$this.combobox({
			panelHeight:"200",
			onLoadSuccess:function(){
				if(!isEmpty(value)){
					$this.combobox("setValue",value);
					$this.next('.combo').find("input[type!='hidden']").val(text);
				}
				$this.next('.combo').find("input[type!='hidden']").blur(function (){
					checkValidate($this);
            	});
			}
		});
	});
	$.fn.datagrid.defaults=$.extend({},$.fn.datagrid.defaults,{pageSize:20,pageList:[20,50,100]});
	//重置序号宽度，防止数据过多序号显示不全，调用方法：onLoadSuccess:function(){$(this).datagrid("fixRownumber");}
	$.extend($.fn.datagrid.methods, {  
	    fixRownumber : function (jq) {  
	        return jq.each(function () {  
	            var panel = $(this).datagrid("getPanel");  
                $(".datagrid-header-rownumber,.datagrid-cell-rownumber", panel).width(50);  
                $(this).datagrid("resize");  
	        });  
	    }  
	});
}
//页面加载时判断权限并得到主页面标签对象
var centerTab,currentTabId;
$(function(){
	checkRole();
	centerTab = window.top.$("#centerTab");
	loadFn();
});
//根据标签页id循环得到标签页的title，用于判断页面是否存在和选中用
function findSelectTabById(id){
	var tabPanels = centerTab.tabs("tabs");
	var options;
	for(var i = 0;i < tabPanels.length;i++){
		options = tabPanels[i].panel("options");
		if(options.id == id){
			return options.title;
		}
	}
	return null;
}
//增加（不存在）或切换标签页（存在，执行选和刷新操作）
function addTab(tabId,title,url){
	var id = "iframe_" + tabId;
	var findTitle = findSelectTabById("div_" + id);
	//如果当前id的tab不存在则创建一个tab
	if(findTitle == null){
		centerTab.tabs('add',{
			id: "div_" + id,
			title: title,         
			closable:true,
			cache : false,
			//注：使用iframe即可防止同一个页面出现js和css冲突的问题
			content : "<iframe id='" + id + "' name='" + id + "' src='" + url + "' width='100%' height='100%' frameborder='0' scrolling='auto'></iframe>"
		});
	}else{
		centerTab.tabs("select",findTitle);
		$("#" + id).attr("src",url);
	}
}
//选项卡内跳转页面
function jumpPage(newTitle,url){
	var tab = centerTab.tabs("getSelected");
	var options = tab.panel("options");
	centerTab.tabs("update", {
		tab: tab,
		options: {
			title: newTitle,
			content : "<iframe id='" + options.id.substring(4) + "' name='" + options.id.substring(4) + "' src='" + url + "' width='100%' height='100%' frameborder='0' scrolling='auto'></iframe>"
		}
	});
}
//刷新当前页面
function refreshTab(){
	var tab = centerTab.tabs("getSelected");
	var options = tab.panel("options");
	$("#" + options.id.substring(4)).attr("src",url);
}
//弹出窗口
function showWindow(options){
	if(options && !options.onLoad){
		options.onLoad = window.top.loadFn;
	}
	window.top.$("#MyPopWindow").window(options);
}
//弹出自身页面窗口
function showSelfWindow(options){
	if(options && !options.onLoad){
		options.onLoad = window.self.loadFn;
	}
	window.self.$("#MySelfWindow").window(options);
}
//弹出二级窗口
function showWindowSecond(options){
	if(options && !options.onLoad){
		options.onLoad = window.top.loadFn;
	}
	window.top.$("#MyPopWindowSecond").window(options);
}
//关闭自身弹出窗口
function closeSelfWindow(){
	window.self.$("#MySelfWindow").window("close");
}
//关闭弹出窗口
function closeWindow(){
	window.top.$("#MyPopWindow").window("close");
}
//关闭二级窗口
function closeWindowSecond(){
	window.top.$("#MyPopWindowSecond").window("close");
}
/*公用js*/
$.ajaxSetup({
	type:"post",
	dataType:"json",
	//若不定义success属性，则默认按此执行，成功后调用ajaxSuccessFn函数，失败后调用ajaxFailFn函数
	success:function(result){
		if(result.success){
			$.messager.show({
				title:"成功",
				msg:result.msg
			});
			if(ajaxSuccessFn && typeof(ajaxSuccessFn) == "function"){
				ajaxSuccessFn.apply();
			}
		}else{
			$.messager.show({
				title:'警告',
				msg:result.msg
			});
			if(ajaxFailFn && typeof(ajaxFailFn) == "function"){
				ajaxFailFn.apply();
			}
		}
	},statusCode:{
		403:function() {
			alert("用户没有操作权限，请重新登录");
			window.top.location.href = ssoServerLoginUrl;
        }
	}
});
/*日期转换*/
function myformatter(date) {
	var y = date.getFullYear();
	var m = date.getMonth() + 1;
	var d = date.getDate();
	return y + '-' + (m < 10 ? ('0' + m) : m) + '-' + (d < 10 ? ('0' + d) : d);
}
function myformatter_month(d) {    
    var month = d.getMonth()+1;  
    if(month<10){  
        month = "0"+month;  
    }  
    if (d.getMonth() == 0) {    
        return d.getFullYear()-1 + '-' + 12;    
    } else {    
        return d.getFullYear() + '-' + month;    
    }    
}
function myparser(s) {
	if (!s) return new Date();
	var ss = (s.split('-'));
	var y = parseInt(ss[0], 10);
	var m = parseInt(ss[1], 10);
	var d = parseInt(ss[2], 10);
	if (!isNaN(y) && !isNaN(m) && !isNaN(d)) {
		return new Date(y, m - 1, d);
	} else {
		return new Date();
	}
}
function myparser_month(s) {// 配置parser，返回选择的日期    
    if (!s)    
        return new Date();    
    var arr = s.split('-');    
    return new Date(parseInt(arr[0], 10), parseInt(arr[1], 10) - 1, 1);    
}
/*拼接参数：{name:"test"}，可自定义参数传入*/
function mosaicParams(params){
	params = params || {};
	var name;
	$(".dkd-search input,select").each(function(){
		if(typeof($(this).attr("dkd-search-element")) != "undefined"){
			name = $.trim($(this).attr("dkd-search-element"));
			if((name.indexOf("entity_createdate")!=-1&&(name.indexOf("<")!=-1||name.indexOf("le")!=-1||name.indexOf("lt")!=-1))||name.indexOf("update_date")!=-1){
				if(!isEmpty(params[name] = $(this).val())){
					params[name] = $(this).val()+" 23:59:59";
				}else{
					params[name] = $(this).val();
				}
			}else{
				params[name] = $(this).val();
			}
		}
	});
	return params;
}
//动态增加条件，参数beanName（bean名称），defaulutName（增加条件的字段名称的默认值）
function addCondition(beanName,professional,jobNo,defaulutName){
	var div = $("<div class='search_item dynamic'><input class='w100 search_key'/><span class='ml13'><input type='text' class='search_value w200'/></span></div>");
	$(".search_all_div").append(div);
	div.find(".search_key").combobox({
		panelHeight:"auto",
		editable:true,
		url:rootPath + "/login/searchItems.do?beanName=" + beanName+"&professional="+professional+"&jobNo="+jobNo,
		mode:"local",
		onLoadSuccess:function(){
			 var data = div.find(".search_key").combobox("getData");
			 var isNumber = false;
			 if (data.length > 0) {
				 if(isEmpty(defaulutName)){
					 div.find(".search_key").combobox("select", data[0].value);
					 if(data[0].value.endWith("number")){
						 isNumber = true;
					 }
				 }else{
					 for(var i = 0;i < data.length;i++){
						 if(data[i].text === defaulutName){
							 div.find(".search_key").combobox("select", data[i].value);
							 if(data[i].value.endWith("number")){
								 isNumber = true;
							 }
							 break;
						 }
					 }
				 }
			 }
			 if(isNumber){
				 div.find(".search_value").numberbox();
			 }else{
				 div.find(".search_value").textbox();				 
			 }
			 var $this = $(this);
			 $this.combobox({
				panelHeight:"200",
				onLoadSuccess:function(){
					div.find(".search_key").combobox("select", data[0].value);
					$this.next('.combo').find('input').blur(function (){
						if(!checkValidate($this)){
							div.find(".search_key").combobox("select", data[0].value);
						}
	            	});
				}
			 });
		}
	});
}
//删除查询条件（默认删除最后一个）
function removeCondition(){
	$(".search_all_div div.dynamic:last").remove();
}
//初始化查询条件，传递名称数组
function initCondition(beanName,nameArray){
	if(!isEmpty(nameArray) && Array.isArray(nameArray) && nameArray.length > 0){
		for(var i = 0;i < nameArray.length;i++){
			addCondition(beanName,nameArray[i]);
		}
	}
}
//拼接动态查询条件，类似mosaicParams
function mosaicActivityParams(params){
	var item,items,value,select,itemStr,ignore;
	params = params || {};
	$(".search_all_div div[class!='search_button']").each(function(){
		ignore = $(this).attr("ignore");
		if(typeof(ignore) == "undefined"){
			select = $(this).find(".search_key");
			if(!itemStr){
				itemStr = ",";
				items = select.combobox("getData");
				for(var i = 0;i < items.length;i++){
					itemStr += items[i].value + ",";
				}
			}
			item = select.combobox("getValue");
			value = $(this).find(".search_value").textbox("getValue");
			if(!isEmpty(value) && itemStr.indexOf("," + item + ",") >= 0){
				params[item] = value;
			}
		}
	});
	return params;
}
/**
 * 获取表单数据
 * formId：页面中表单id
 * oldData：原始数据
 * detailName：集合字段名称
 * detail：集合字段内容
 * parent：父节点
 */
function getDataBase(formId,oldData,detailName,detail){
	var data = {};
	var type,name,selectTemp,inputTemp,ignore;
	$("#" + formId).find("input").each(function(){
		name = $(this).attr("name");
		if(typeof(name) != "undefined"){
			inputTemp = $("input[textboxname='" + name + "']");
			if(inputTemp && inputTemp.length > 0){
				ignore = inputTemp.attr("ignore");
			}else{
				ignore = $(this).attr("ignore");
			}
			if(typeof(ignore) == "undefined"){
				//input，输入框、单选按钮、复选框等
				type = $(this).attr("type") || "text";
				if(type == "hidden"){
					//处理字典下拉列表
					selectTemp = $(this).parent().siblings("select");
					if(selectTemp){
						if(typeof(selectTemp.attr("is-object")) == "undefined"){
							data[name] = $(this).val();
						}else{
							//若是字典等对象类型需特殊处理
							if(!isEmpty($(this).val())){
								data[name] = {"id":$(this).val()};
							}
							delete oldData[name + "_name"];
						}
					}else{
						data[name] = $(this).val();
					}
				}else if(type == "text"){
		 			data[name] = $(this).val(); 				
		 		}else if(type == "radio"){
		 			//单选按钮
		 			data[name] = $("input[name=" + name + "]:checked").val();
		 		}else if(type == "checkbox"){
		 			//暂不处理
		 		}
		 		if(typeof($(this).attr("int")) != "undefined"){
					data[name] = parseInt(data[name]);
				}else if(typeof($(this).attr("number")) != "undefined"){
					data[name] = parseFloat(data[name]);
				}
			}
		}
		
	});
	if(detailName){
		data[detailName] = detail;		
	}
	return $.extend(oldData,data);
}
function getData(formId,oldData,detailName,detail){
	return JSON.stringify(getDataBase(formId,oldData,detailName,detail));
}
function isEmpty(obj){
	if(typeof(obj) == "undefined" || obj == null || obj == ""){
		return true;
	}else{
		return false;
	}
}
/*ajax回调函数，参数：1.返回值 2.成功函数 3.失败函数*/
function handleReturn(result,ajaxSuccessFn,ajaxFailFn){
	if(result.success){
		$.messager.show({
			title:"成功",
			msg:result.msg
		});
		if(ajaxSuccessFn && typeof(ajaxSuccessFn) == "function"){
			ajaxSuccessFn.apply();
		}
	}else{
		$.messager.show({
			title:"警告",
			msg:result.msg
		});
		if(!isEmpty(result.field)){
			$("input[name='" + result.field + "']").eq(result.index < 0 ? 0 : result.index).focus();
		}
		if(ajaxFailFn && typeof(ajaxFailFn) == "function"){
			ajaxFailFn.apply();
		}
	}
}
function resetSearch(){
	$(".search_value").textbox("reset");
	$(".search_item").find("input[id$='search']:not([class*='easyui-combobox'])").textbox("reset");
	$(".search_item").find("input[id$='search'][class*='easyui-combobox']").combobox("reset");
	$(".search_item").find("select[class*='easyui-combobox']").combobox("reset");
}
function loadData(data){
	$(".view_table tr td").each(function(){
		if(typeof($(this).attr("column")) != "undefined"){
			if("entity_createdate"==$(this).attr("column")&&!isEmpty(data[$(this).attr("column")])){
				$(this).html(myformatter(new Date(data[$(this).attr("column")])));
			}else if("entity_modifydate"==$(this).attr("column")&&!isEmpty(data[$(this).attr("column")])){
				$(this).html(myformatter(new Date(data[$(this).attr("column")])));
			}else{
				$(this).html(data[$(this).attr("column")]);
			}
		}
	});
}
function checkBill(work_no){
	showWindow({
		title:'审批',
		href:rootPath + '/flowset/checkBill.do?work_no=' + work_no,
		width:600,
		height:200
	});
}
function check(work_no,is_pass){
	$.messager.confirm("确认", "您确定要审核么？", function(r){
		if (r){
			var check_opinion = $("#check_opinion").val();
			$.post(rootPath + "/flowset/change.do",{"work_no":work_no,"is_pass":is_pass,"check_opinion":check_opinion},function(result){
				handleReturn(result,function(){
					closeWindow();
					refreshTab();
				});
			});
		}
	});
}
function loadCheckHistory(work_no){
	showWindow({
		title:'查看审核记录',
		href:rootPath + '/flowset/loadCheckHistory.do?work_no=' + work_no,
		width:600,
		height:500
	});
}
function logout(){
	store.clear();
	currentUser = null;
	window.location = rootPath + "/login/logout.do";
}
function getCurrentUser(){
	return JSON.parse(store.getItem("current_user_sj")).user;
}
//得到回调函数
function getHandleFn(fnName){
	var fn = window.top[fnName];
	//弹出页面的回调
	if(fn && typeof(fn) == "function"){
		return fn;
	}else{
		var tab = centerTab.tabs("getSelected");
		var options = tab.panel("options");
		//当前标签页内的回调
		fn = window.top[options.id.substring(4)][fnName];
		if(fn && typeof(fn) == "function"){
			return fn;
		}
	}
}
String.prototype.startWith = function(str){     
	var reg = new RegExp("^" + str);     
	return reg.test(this);        
};  

String.prototype.endWith = function(str){     
	var reg = new RegExp(str + "$");     
	return reg.test(this);        
};
function fileInfo(entity_code,entity_id){
	showWindow({
		title:"文件管理",
		href:rootPath + "/login/showList.do?url=system/fileInfo&entity_code=" + entity_code + "&entity_id=" + entity_id,
		width:900,
		height:550
	});
}
function validateImage(entityId,val){
	//新建必须上传封面
	if(isEmpty(entityId) && isEmpty(val)){
		$.messager.show({
			title:"警告",
			msg:"封面必须上传"
		});
		return false;
	}
	//封面格式必须为jpg或png
	if(!isEmpty(val) && !val.endWith("\\.JPG") && !val.endWith("\\.PNG")){
		$.messager.show({
			title:"警告",
			msg:"请上传jpg或者png格式的图片"
		});
		return false;
	}
	return true;
}
//----------------分割线，暂时用不到的方法
//页面加载时判断审批按钮是否可以显示，要求格式：必须要有dkd-check属性，该属性的值是该单据提交时使用的业务主键字段值
function setMain(title,href,$this){
	if($this){
		$(".menuLi").css("background-color","");
		$($this).css("background-color","#E2E2E2");
	}
	//切换页面时清除dialog缓存，页面中所有dialog都要定义pop样式
	$(".pop").each(function(){
		$(this).dialog("destroy", false);
	});
	$("body").layout("panel","center").panel({
		title:"所在位置：" + title,
		href:href,
		onLoad:checkRole
	});
}
function refreshMain(){
	//切换页面时清除dialog缓存，页面中所有dialog都要定义pop样式
	$(".pop").each(function(){
		$(this).dialog("destroy", false);
	});
	var options = $("body").layout("panel","center").panel("options");
	$("body").layout("panel","center").panel({
		title:options.title,
		href:options.href,
		onLoad:checkRole
	});
}
//传入控件ID（下拉框），失去焦点事件，若匹配不到清空数据
function checkValidate($this){
	//获取当前值
	var value = $this.combobox("getValue");
	var items = $this.combobox("getData");
	for(var i = 0;i < items.length;i++){
		if(!isEmpty(value) && value == items[i].value){
			return true;
		}
	}
	$this.combobox("setValue","");
	return false;
}
//判断日期比较大小  格式为yyyy-MM-dd type为类型（<,<=,>,>=）
function compareDate(DateOne,DateTwo,type) {
	var condition=true;
	var OneMonth = DateOne.substring(5, DateOne.lastIndexOf("-"));
	var OneDay = DateOne.substring(DateOne.length, DateOne.lastIndexOf("-") + 1);
	var OneYear = DateOne.substring(0, DateOne.indexOf("-"));
	var TwoMonth = DateTwo.substring(5, DateTwo.lastIndexOf("-"));
	var TwoDay = DateTwo.substring(DateTwo.length, DateTwo.lastIndexOf("-") + 1);
	var TwoYear = DateTwo.substring(0, DateTwo.indexOf("-"));
	if(">"==type){
		condition=Date.parse(OneMonth+"/"+OneDay+"/"+OneYear)>Date.parse(TwoMonth+"/"+TwoDay+"/"+TwoYear);
	}else if(">="==type){
		condition=Date.parse(OneMonth+"/"+OneDay+"/"+OneYear)>=Date.parse(TwoMonth+"/"+TwoDay+"/"+TwoYear);
	}else if("<"==type){
		condition=Date.parse(OneMonth+"/"+OneDay+"/"+OneYear)<Date.parse(TwoMonth+"/"+TwoDay+"/"+TwoYear);
	}else if("<="==type){
		condition=Date.parse(OneMonth+"/"+OneDay+"/"+OneYear)<=Date.parse(TwoMonth+"/"+TwoDay+"/"+TwoYear);
	}
	if (condition){     
		return true;   
	}else{     
		return false;   
	} 
} 
//时间  格式化时间yyyy-MM-dd
function formatterDate(date) {
	var day = date.getDate() > 9 ? date.getDate() : "0" + date.getDate();
	var month = (date.getMonth() + 1) > 9 ? (date.getMonth() + 1) : "0"
		+ (date.getMonth() + 1);
	var hor = date.getHours();
	return date.getFullYear() + '-' + month + '-' + day;
}