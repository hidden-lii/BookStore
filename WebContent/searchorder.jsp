<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<title>My JSP 'newslist.jsp' starting page</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

</head>

<body>
	<!-- 定义table, 用于创建Easy UI的datagrid控件 -->
	<table id="orderDg" class="easyui-datagrid"></table>
	
	<!-- 工具栏 -->
	<div id="orderTb" style="padding:2px 5px;">
	   <a href="javascript:void(0)" class="easyui-linkbutton" 
	      iconCls="icon-edit" plain="true" onclick="editOrder();">修改订单/查看明细</a> 
	   <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove"
			onclick="removeOrder();" plain="true">删除订单</a>
	</div>
	
	<!-- 查询窗体 -->
	<div id="searchOrderTb" style="padding:2px 5px;">
		<form id="searchOrderForm">
			<div style="padding:3px">
				订单编号&nbsp;<input class="easyui-textbox" name="oi.id"
					id="oi.id" style="width:110px" />
			</div>
			<div style="padding:3px">
				客户名称&nbsp;<input style="width:115px;" id="search_uid"
					class="easyui-combobox" value="0" name="search_uid"
					data-options="valueField:'id',textField:'userName',url:'getValidUser'">&nbsp;&nbsp;&nbsp;
				订单状态&nbsp;<select id="oi.status" class="easyui-combobox" name="oi.status" style="width:115px;">
					<option value="请选择" selected>请选择</option>
					<option value="未付款">未付款</option>
					<option value="已付款">已付款</option>
					<option value="待发货">待发货</option>
					<option value="已发货">已发货</option>
					<option value="已完成">已完成</option>
				</select>&nbsp;&nbsp;&nbsp; 订单时间 &nbsp;<input class="easyui-datebox"
					name="oi.orderTimeFrom" id="oi.orderTimeFrom" style="width:115px;" /> ~
				<input class="easyui-datebox" name="oi.orderTimeTo" id="oi.orderTimeTo"
					style="width:115px;" /> <a href="javascript:void(0)" class="easyui-linkbutton"
					iconCls="icon-search" plain="true" onclick="searchOrderInfo();">查找</a>
					<a href="javascript:void(0)" class="easyui-linkbutton"
					iconCls="icon-clear" plain="true" onclick="clearSearch();">清空</a>
			</div>
		</form>
	</div>


	<script type="text/javascript">
		$(function() {
			$('#orderDg').datagrid({
				singleSelect : false,
				url : 'getAllOrderInfo', //为datagrid设置数据源
				pagination : true, //启用分页
				pageSize : 5, //设置初始每页记录数（页大小）
				pageList : [ 5, 10, 15 ], //设置可供选择的页大小
				rownumbers : true, //显示行号
				fit : true, //设置自适应
				toolbar : '#orderTb', //为datagrid添加工具栏
				header : '#searchOrderTb', //为datagrid标头添加搜索栏
				columns : [ [ { //编辑datagrid的列
					title : '序号',
					field : 'id',
					align : 'center',
					checkbox : true
				}, {
					field : 'ui',
					title : '订单客户',
					formatter : function(value, row, index) {
						if (row.ui) {
							return row.ui.userName;
						} else {
							return value;
						}
					},
					width : 100
				}, {
					field : 'status',
					title : '订单状态',
					width : 80
				}, {
					field : 'ordertime',
					title : '订单时间',
					width : 100
				}, {
					field : 'orderprice',
					title : '订单金额',
					width : 100
				} ] ]
			});
		});

		function clearSearch(){
			$('#searchOrderForm').form('clear');
		}

		var urls;
		var data;
		
		// 删除订单
		function removeOrder() {
			// 获取选中的订单记录行
			var rows = $("#orderDg").datagrid('getSelections');
			if (rows.length > 0) {
				$.messager.confirm('Confirm', '确认要删除么?', function(r) {
					if (r) {
						var ids = "";
						// 获取选中订单记录的订单id, 保存到ids中
						for (var i = 0; i < rows.length; i++) {
							ids += rows[i].id + ",";
						}
						// 发送请求
						$.post('deleteOrder', {
							oids : ids
						}, function(result) {
							if (result.success == 'true') {
								$("#orderDg").datagrid('reload'); 
								$.messager.show({
									title : '提示信息',
									msg : result.message
								});
							} else {
								$.messager.show({
									title : '提示信息',
									msg : result.message
								});
							}
						}, 'json');

					}
				});
			} else {
				$.messager.alert('提示', '请选择要删除的行', 'info');
			}
		}

		function convertArray(o) { //主要是推荐这个函数。它将jquery系列化后的值转为name:value的形式。 
			var v = {};
			for ( var i in o) {
				if (typeof (v[o[i].name]) == 'undefined')
					v[o[i].name] = o[i].value;
				else
					v[o[i].name] += "," + o[i].value;
			}
			return v;
		}

		// 修改订单/查看明细
		function editOrder() {
		    var rows = $("#orderDg").datagrid('getSelections');
			if (rows.length > 0) {
				var row = $("#orderDg").datagrid("getSelected");
				if ($('#tabs').tabs('exists', '修改订单')) {
					$('#tabs').tabs('close', '修改订单');
				}
				$('#tabs').tabs('add', {
					title : "修改订单",
					href : 'getOrderInfo?oid=' + row.id,
					closable : true
				});
			}else {
				$.messager.alert('提示', '请选择要修改的订单', 'info');
			}
		}

		// 查询订单
		function searchOrderInfo() {
			$("#orderDg").datagrid('load',convertArray($("#searchOrderForm").serializeArray()));
		}
	</script>
</body>
</html>
