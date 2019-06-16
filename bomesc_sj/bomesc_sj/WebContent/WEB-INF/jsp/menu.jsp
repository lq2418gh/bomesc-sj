<%@ page language="java" contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/common/common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>菜单</title>
</head>
<body>
<div id="menuDiv" class="easyui-accordion" fit="false" border="false" animate="false">
	<div title="系统管理" style="overflow:hidden;" class="menuDiv" iconCls="pic_2" dkd-auth="AUTH_SYS_TOPMENU">
		<ul class="menuUl">
			<li onclick="addTab('flowset','审批流程管理','${ctx}/login/showList.do?url=system/flowset')" class="menuLi" dkd-auth="AUTH_SYS_FLOWSET">
				<span class="pic_113 vSub"></span>审批流程管理
			</li>
			<li onclick="addTab('dic','字典管理','${ctx}/login/showList.do?url=system/dictionary')" class="menuLi" dkd-auth="AUTH_SYS_DIC">
				<span class="pic_28 vSub"></span>字典管理
			</li>
			<li onclick="addTab('log','日志管理','${ctx}/login/showList.do?url=system/log')" class="menuLi" dkd-auth="AUTH_SYS_LOG">
				<span class="pic_186 vSub"></span>日志管理
			</li>
		</ul>
	</div>
	<div title="基础信息管理" style="overflow:hidden;" class="menuDiv" iconCls="pic_3" dkd-auth="AUTH_PUR_TOPMENU">
		<ul class="menuUl">
			<li onclick="addTab('materialclass','物料分类','${ctx}/login/showList.do?url=base/material/materialClass')" class="menuLi" dkd-auth="AUTH_SYS_LOG">
				<span class="pic_114 vSub"></span>物料分类
			</li>
			<li onclick="addTab('materialcode','物料档案','${ctx}/login/showList.do?url=base/material/materialArchives')" class="menuLi" dkd-auth="AUTH_SYS_LOG">
				<span class="pic_29 vSub"></span>物料档案
			</li>
		</ul>
	</div>
	<div title="采购管理" style="overflow:hidden;" class="menuDiv" iconCls="pic_90" dkd-auth="AUTH_PUR_TOPMENU">
		<ul class="menuUl">
			<li onclick="addTab('order','订单管理','${ctx}/login/showList.do?url=business/order/saleOrder')" class="menuLi" dkd-auth="AUTH_PUR_ORDER">
				<span class="pic_138 vSub"></span>订单管理
			</li>
			<li onclick="addTab('mto','材料申购单管理','${ctx}/login/showList.do?url=business/mto/mtoHead')" class="menuLi" dkd-auth="AUTH_PUR_ORDER">
				<span class="pic_146 vSub"></span>材料申购单管理
			</li>
		</ul>
	</div>
	<div title="项目信息管理" style="overflow:hidden;" class="menuDiv" iconCls="pic_91" dkd_auth="AUTH_PUR_TOPMENU">
		<ul class="menuUl">
			<li onclick="addTab('project','项目信息管理','${ctx}/login/showList.do?url=business/project/project')" class="menuLi" dkd-auth="AUTH_PUR_ORDER">
				<span class="pic_137 vSub"></span>项目信息管理
			</li>
			<li onclick="addTab('draw','图纸数量管理','${ctx}/login/showList.do?url=business/project/draw')" class="menuLi" dkd-auth="AUTH_PUR_ORDER">
				<span class="pic_139 vSub"></span>图纸数量管理
			</li>
			<li onclick="addTab('drawDetail','图纸明细管理','${ctx}/login/showList.do?url=business/project/drawDetail')" class="menuLi" dkd-auth="AUTH_PUR_ORDER">
				<span class="pic_140 vSub"></span>图纸明细管理
			</li>
			<li onclick="addTab('workHours','项目工时维护','${ctx}/login/showList.do?url=business/project/workHours')" class="menuLi" dkd-auth="AUTH_WORKHORURS">
				<span class="pic_141 vSub"></span>项目工时维护
			</li>
		</ul>
	</div>
	<div title="数据参数配置" style="overflow:hidden;" class="menuDiv" iconCls="pic_92" dkd_auth="AUTH_DPC_TOPMENU">
		<ul class="menuUl">
			<li onclick="addTab('dataManager','数据负责人','${ctx}/login/showList.do?url=business/dataParamConfig/dataManager')" class="menuLi" dkd-auth="AUTH_ALLOCATIONPRIORITY">
				<span class="pic_143 vSub"></span>数据负责人
			</li>
			<li onclick="addTab('columnDisplaySet','补充列显示名称设置','${ctx}/login/showList.do?url=business/dataParamConfig/columnDisplaySet')" class="menuLi" dkd-auth="AUTH_COLUMNDISPLAYSET">
				<span class="pic_144 vSub"></span>补充列显示名称设置
			</li>
			<li onclick="addTab('allocationPriority','物资分配优先级','${ctx}/login/showList.do?url=business/dataParamConfig/allocationPriority')" class="menuLi" dkd-auth="AUTH_ALLOCATIONPRIORITY">
				<span class="pic_142 vSub"></span>物资分配优先级
			</li>
			<li onclick="addTab('majorAllocation','多专业公用优先级配置','${ctx}/login/showList.do?url=business/dataParamConfig/majorAllocation')" class="menuLi" dkd-auth="AUTH_MAJORALLOCATION">
				<span class="pic_145 vSub"></span>多专业公用优先级配置
			</li>
		</ul>
		<ul style="padding-left:20px">
			<li dkd_auth="AUTH_DPC_TOPMENU" class="open">
				<div class="pointer"><span class="pic_93 vSub"></span>系统日志</div>
				<ul class="menuUl">
					<li onclick="addTab('promptRecord','导入失败提醒','${ctx}/login/showList.do?url=business/promptRecord/importFailureRecord')" class="menuLi" dkd-auth="AUTH_ALLOCATIONPRIORITY">
						<span class="pic_146 vSub"></span>导入失败提醒记录
					</li>
					<li onclick="addTab('promptRecord','缺料提醒','${ctx}/login/showList.do?url=business/promptRecord/lackMaterialRecord')" class="menuLi" dkd-auth="AUTH_ALLOCATIONPRIORITY">
						<span class="pic_147 vSub"></span>缺料提醒记录
					</li>
				</ul>
			</li>
		</ul>	
	</div>
	<div title="设计数据管理" style="overflow:hidden;" class="menuDiv" iconCls="pic_93" dkd_auth="AUTH_DPC_TOPMENU">
		<ul style="padding-left:20px">
			<li dkd_auth="AUTH_DPC_TOPMENU" class="open">
				<div class="pointer"><span class="pic_93 vSub"></span>数据跟踪表</div>
				<ul class="menuUl">
					<li onclick="addTab('stDesignDataManage','结构数据跟踪表','${ctx}/login/showList.do?url=business/designDataManager/stDataTrackTable')" class="menuLi" dkd-auth="AUTH_ALLOCATIONPRIORITY">
						<span class="pic_146 vSub"></span>结构数据跟踪表
					</li>
					<li onclick="addTab('stDataSuppManage','结构补料数据跟踪表','${ctx}/login/showList.do?url=business/designDataManager/stDataTrackSupp')" class="menuLi" dkd-auth="AUTH_ALLOCATIONPRIORITY">
						<span class="pic_161 vSub"></span>结构补料数据跟踪表
					</li>
					<li onclick="addTab('piDesignDataManage','管线数据跟踪表','${ctx}/login/showList.do?url=business/designDataManager/piDataTrackTable')" class="menuLi" dkd-auth="AUTH_ALLOCATIONPRIORITY">
						<span class="pic_148 vSub"></span>管线数据跟踪表
					</li>
					<li onclick="addTab('piDataSuppManage','管线补料数据跟踪表','${ctx}/login/showList.do?url=business/designDataManager/piDataTrackSupp')" class="menuLi" dkd-auth="AUTH_ALLOCATIONPRIORITY">
						<span class="pic_188 vSub"></span>管线补料数据跟踪表
					</li>
				</ul>
			</li>
			<li dkd_auth="AUTH_DPC_TOPMENU" class="open">
				<div class="pointer"><span class="pic_95 vSub"></span>数据变更记录</div>
				<ul class="menuUl">
					<li onclick="addTab('stDesignDataChange','结构数据变更记录','${ctx}/login/showList.do?url=business/designDataManager/stDataTrackChange&module=change')" class="menuLi" dkd-auth="AUTH_ALLOCATIONPRIORITY">
						<span class="pic_147 vSub"></span>结构数据变更记录
					</li>
					<!--复用变更记录页面，查询的为因图纸升版作废、因图纸升版增加的数据-->
					<li onclick="addTab('stDesignDataRemind','结构数据变更提醒','${ctx}/login/showList.do?url=business/designDataManager/stDataTrackChange&module=remind')" class="menuLi" dkd-auth="AUTH_ALLOCATIONPRIORITY">
						<span class="pic_151 vSub"></span>结构数据变更提醒
					</li>
					<li onclick="addTab('piDesignDataChange','管线数据变更记录','${ctx}/login/showList.do?url=business/designDataManager/piDataTrackChange')" class="menuLi" dkd-auth="AUTH_ALLOCATIONPRIORITY">
						<span class="pic_156 vSub"></span>管线数据变更记录
					</li>
				</ul>
			</li>
			<li dkd_auth="AUTH_DPC_TOPMENU" class="open">
				<div class="pointer"><span class="pic_96 vSub"></span>生成领料单</div>
				<ul class="menuUl">
					<li onclick="addTab('stDataPicking','结构数据领料单','${ctx}/login/showList.do?url=business/designDataManager/stDataPicking')" class="menuLi" dkd-auth="AUTH_ALLOCATIONPRIORITY">
						<span class="pic_149 vSub"></span>结构数据领料单
					</li>
				</ul>
			</li>
		</ul>
	</div>
</div>
<script type="text/javascript">
	$(function(){
		var auths = store.getItem("auths_sj") || "";
		var dkdAuth;
		$("div.menuDiv").each(function(){
			dkdAuth = $(this).attr("dkd-auth");
			if(typeof(dkdAuth) != "undefined"){
				if(auths.indexOf("," + dkdAuth + ",") == -1){
					$(this).remove();
				}else{
					$(this).find("li.menuLi").each(function(){
						dkdAuth = $(this).attr("dkd-auth");
						if(typeof(dkdAuth) != "undefined"){
							if(auths.indexOf("," + dkdAuth + ",") == -1){
								$(this).remove();
							}
						}
					});
				}
			}
		});
		$("div.pointer").each(function(){
			var $this = $(this);
			$this.click(function(){
				var ul = $this.next("ul");
				ul.toggle("fast",function(){
					$this.parent().toggleClass("fold open");			
				});
			});
		});
	});
</script>
</body>
</html>