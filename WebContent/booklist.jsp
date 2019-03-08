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
<meta http-equiv="exbires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

</head>

<body>
    <!-- 定义table, 用于创建easy ui的datagrid控件 -->
	<table id="dg" class="easyui-datagrid"></table>
	
	<!-- 创建datagrid控件的工具栏 -->
	<div id="tb" style="padding:2px 5px;">
		<a href="javascript:void(0)" class="easyui-linkbutton"
			iconCls="icon-add" plain="true" onclick="addBook();">添加</a> <a
			href="javascript:void(0)" class="easyui-linkbutton"
			iconCls="icon-edit" plain="true" onclick="editBook();">修改</a> <a
			href="javascript:void(0)" class="easyui-linkbutton"
			iconCls="icon-remove" onclick="removeBook();" plain="true">删除</a>
	</div>
	
	<!-- 创建查询工具栏 -->
	<div id="searchtb" style="padding:2px 5px;">
		<form id="searchForm">
			<div style="padding:3px">
				图书编号&nbsp;&nbsp;<input class="easyui-textbox" name="bi.code"
					id="bi.code" style="width:110px" />
			</div>
			<div style="padding:3px">
				图书名称&nbsp;&nbsp;<input class="easyui-textbox" name="bi.name"
					id="bi.name" style="width:110px" /> 图书作者&nbsp;&nbsp;<input class="easyui-textbox" name="bi.author"
					id="bi.author" style="width:110px" /> 图书类型&nbsp;&nbsp;<input
					style="width:110px;" id="bi.type.id" class="easyui-combobox"
					name="bi.type.id"
					data-options="valueField:'id',textField:'name',url:'getAllType'">
				图书出版社&nbsp;&nbsp;<input class="easyui-textbox" name="bi.press"
					id="bi.press" style="width:110px" /> 价格: <input
					class="easyui-textbox" name="bi.priceFrom" id="bi.priceFrom"
					style="width:80px;" /> ~ <input class="easyui-textbox"
					name="bi.priceTo" id="bi.priceTo" style="width:80px;" /> <a
					href="javascript:void(0)" class="easyui-linkbutton"
					iconCls="icon-search" plain="true" onclick="searchBook();">查找</a>
					<a href="javascript:void(0)" class="easyui-linkbutton"
					iconCls="icon-clear" plain="true" onclick="clearSearch();">清空</a>
			</div>
		</form>
	</div>
	
	<!-- 添加图书信息对话框 -->
	<div id="dlg" class="easyui-dialog" title="New Topic" closed="true"
		style="width:500px;">
		<div style="padding:10px 60px 20px 60px">
			<form id="ff" method="POST" action="" enctype="multipart/form-data">
				<table cellpadding="5">
					<tr>
						<td>图书状态:</td>
						<td><select id="bi.status" class="easyui-combobox" name="bi.status"	style="width:150px;">
								<option value="1">在售</option>
								<option value="0">下架</option>
						</select></td>
					</tr>
					<tr>
						<td>图书类型:</td>
						<td><input style="width:150px;" id="bi.type.id"
							class="easyui-combobox" name="bi.type.id"
							data-options="valueField:'id',textField:'name',url:'getAllType'"></input>
						</td>
					</tr>
					<tr>
					<tr>
						<td>图书名称:</td>
						<td><input class="easyui-textbox" type="text" id="bi.name"
							name="bi.name" data-options="required:true"></input></td>
					</tr>
					<tr>
						<td>图书作者:</td>
						<td><input class="easyui-textbox" type="text" id="bi.author"
							name="bi.author" data-options="required:true"></input></td>
					</tr>
					<tr>
						<td>图书编码:</td>
						<td><input class="easyui-textbox" type="text" id="bi.code"
							name="bi.code" data-options="required:true"></input></td>
					</tr>
					<tr>
						<td>图书出版社:</td>
						<td><input class="easyui-textbox" type="text" id="bi.press"
							name="bi.press" data-options="required:true"></input></td>
					</tr>
					<tr>
						<td>图书数量:</td>
						<td><input class="easyui-textbox" type="text" id="bi.num"
							name="bi.num" data-options="required:true"></input></td>
					</tr>
					<tr>
						<td>图书价格:</td>
						<td><input class="easyui-textbox" type="text" id="bi.price"
							name="bi.price" data-options="required:true"></input></td>
					</tr>
					<tr>
						<td>图书描述:</td>
						<td><input class="easyui-textbox" name="bi.intro"
							id="bi.intro" data-options="multiline:true" style="height:60px"></input></td>
					</tr>
					<tr>
						<td>图书小图:</td>
						<td><input class="easyui-filebox" id="pic" name="pic"
							style="width:200px" value="选择图片"></input></td>
					</tr>
					<tr>
						<td>图书大图:</td>
						<td><input class="easyui-filebox" id="bigpic" name="bigpic"
							style="width:200px" value="选择图片"></input></td>
					</tr>
				</table>
			</form>
			<div style="text-align:center;padding:5px">
				<a href="javascript:void(0)" class="easyui-linkbutton"
					onclick="saveBook();">保存</a> <a href="javascript:void(0)"
					class="easyui-linkbutton" onclick="clearForm();">清空</a>
			</div>
		</div>
	</div>

	<script type="text/javascript">
		$(function() {
			$('#dg').datagrid({
				singleSelect : false, //设置datagrid为单选
				url : 'bookList', //为datagrid设置数据源
				pagination : true, //启用分页
				pageSize : 10, //设置初始每页记录数（页大小）
				pageList : [ 10, 15, 20 ], //设置可供选择的页大小
				rownumbers : true, //显示行号
				fit : true, //设置自适应
				toolbar : '#tb', //为datagrid添加工具栏
				header : '#searchtb', //为datagrid标头添加搜索栏
				columns : [ [ { //编辑datagrid的列
					title : '图书序号',
					field : 'id',
					align : 'center',
					checkbox : true
				}, {
					field : 'name',
					title : '图书名称',
					width : 100
				}, {
					field : 'author',
					title : '图书作者',
					width : 100
				}, {
					field : 'type',
					title : '图书类型',
					formatter : function(value, row, index) {
						if (row.type) {
							return row.type.name;
						} else {
							return value;
						}
					},
					width : 70
				}, {
					field : 'status',
					title : '图书状态',
					formatter : function(value, row, index) {
						if (row.status==1) {
							return "在售";
						} else {
							return "下架";
						}
					},
					width : 70
				}, {
					field : 'code',
					title : '图书编码',
					width : 100
				}, {
					field : 'author',
					title : '图书作者',
					width : 100
				}, {
					field : 'press',
					title : '图书出版社',
					width : 120
				}, {
					field : 'price',
					title : '图书价格',
					width : 70
				}, {
					field : 'num',
					title : '图书库存',
					width : 70
				}, {
					field : 'intro',
					title : '图书描述',
					width : 450
				} ] ]
			});
		});

		var urls;
		var data;

        // 删除图书
		function removeBook() {
			var rows = $("#dg").datagrid('getSelections');
			if (rows.length > 0) {
				$.messager.confirm('Confirm', '确认要删除么?', function(r) {
					if (r) {
						var ids = "";
						for (var i = 0; i < rows.length; i++) {
							ids += rows[i].id + ",";
						}						
						$.post('updateStatus', {
							id : ids
						}, function(result) {
							if (result.success == 'true') {
								$("#dg").datagrid('reload'); // reload the user data
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
				$.messager.alert('提示', '请选择要删除的书', 'info');
			}
		}

		// 打开新增图书对话框
		function addBook() {
			$('#dlg').dialog('open').dialog('setTitle', '新增图书');
			$('#dlg').form('clear');
			urls = 'addBook';
		}
		
		// 保存图书信息
		function saveBook() {
			//data = convertArray($("#ff").serializeArray());
			//console.log(data);
			$("#ff").form("submit", {
				url : urls, //使用参数				
				success : function(result) {
					var result = eval('(' + result + ')');
					if (result.success == 'true') {
						$("#dg").datagrid("reload");
						$("#dlg").dialog("close");
					}					
					$.messager.show({
						title : "提示信息",
						msg : result.message
					});
				}
			});
		}
		
		function clearForm() {
			$('#ff').form('clear');
		}

		function clearSearch(){
			$('#searchForm').form('clear');
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

		// 修改图书
		function editBook() {
			var row = $("#dg").datagrid("getSelected");
			if (row) {
				$("#dlg").dialog("open").dialog('setTitle', '修改图书信息');
				$("#ff").form("load", {
					"bi.type.id" : row.type.id,
					"bi.type.name" : row.type.name,
					"bi.name" : row.name,
					"bi.code" : row.code,
					"bi.press" : row.press,
					"bi.num" : row.num,
					"bi.author" : row.author,
					"bi.price" : row.price,
					"bi.intro" : row.intro,
					"bi.status" : row.status,
				});
				urls = "updateBook?bi.id=" + row.id;
			}
		}

		// 查询图书
		function searchBook() {
			$("#dg").datagrid('load',convertArray($("#searchForm").serializeArray()));
		}
	</script>
</body>
</html>
