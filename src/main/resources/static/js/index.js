var defaultHeight = $("#baseContentDiv").height() - $("#baseContentDiv").children(":eq(0)").outerHeight() - $("#baseContentDiv").children(":eq(1)").outerHeight();
defaultHeight = defaultHeight - 40;
	
$(function() {
	$('#saveBtn').linkbutton({iconCls: 'icon-add'});
	$("#saveBtn").bind('click', function() {
		save();
	});
	$('#exportBtn').linkbutton({iconCls: 'icon-add'});
	$("#exportBtn").bind('click', function() {
		exportFile();
	});
	gridTbInfoInit();
	gridDiffTbInit();
});
function gridTbInfoInit() {
	var datagridBase = {
	    url:'/main/getDbInfoList',
	    width: '400',
	    height: '315',
	    autoLoad:true,
	    rownumbers: true,
	    fitcolumns: true,
	    singleSelect: true,
	    nowrap :false, //高度随内容调整
	    columns:[[
	        {field:'id', title:'', width:10, hidden: true},
	        {field:'dbIp', title:'数据库服务器', width:110, halign:'center', align:'center', formatter: function(value) {
	        	var result = '';
	        	result += '<div class="ip">' + getIp(value.split('.')[0]) + '.</div>';
	        	result += '<div class="ip">' + getIp(value.split('.')[1]) + '.</div>';
	        	result += '<div class="ip">' + getIp(value.split('.')[2]) + '.</div>';
	        	result += '<div class="ip">' + getIp(value.split('.')[3]) + '</div>';
				return result;
			}},
			{field:'memo', title:'备注', width:250, halign:'center', align:'left'}
	    ]]
	};
	$('#srcList').datagrid(datagridBase);
	$('#desList').datagrid(datagridBase);	
}
function gridDiffTbInit() {
	$('#diffTbList').datagrid({
	    url:'/main/getDiffTableInfoList',
	    width: '580',
	    height: '315',
	    autoLoad:true,
	    rownumbers: true,
	    fitcolumns: true,
	    singleSelect: true,
	    nowrap :false, //高度随内容调整
	    columns:[[
	        {field:'tbSchema', title:'库名', width:80, halign:'center', align:'left'},
	        {field:'firstDbIp', title:'来源数据库服务器', width:110, halign:'center', align:'center', formatter: function(value) {
	        	var result = '';
	        	result += '<div class="ip">' + getIp(value.split('.')[0]) + '.</div>';
	        	result += '<div class="ip">' + getIp(value.split('.')[1]) + '.</div>';
	        	result += '<div class="ip">' + getIp(value.split('.')[2]) + '.</div>';
	        	result += '<div class="ip">' + getIp(value.split('.')[3]) + '</div>';
				return result;
			}},
			{field:'secondDbIp', title:'目标数据库服务器', width:110, halign:'center', align:'center', formatter: function(value) {
				var result = '';
				result += '<div class="ip">' + getIp(value.split('.')[0]) + '.</div>';
				result += '<div class="ip">' + getIp(value.split('.')[1]) + '.</div>';
				result += '<div class="ip">' + getIp(value.split('.')[2]) + '.</div>';
				result += '<div class="ip">' + getIp(value.split('.')[3]) + '</div>';
				return result;
			}},
	        {field:'memo', title:'备注', width:140, halign:'center', align:'left'},
	        {field:'id', title:'操作', width:50, halign:'center', align:'left', formatter: function(value) {
	        	return '<a href="javascript:void(0);" style="cursor:pointer" onclick="exeDiff(\'' + value + '\')";><div style="text-align: center">执行</div></a>';
	        }},
	    ]]
	});
}
function exeDiff(id) {
    $.ajax({
		type : 'POST',
		url : '/main/checkDB',
		data : {id:id},
		beforeSend : function() {
			$("#exportDiv").hide();
			$.messager.progress({title : '提示', msg : '比较中..', text : ''});
		},
		complete : function() {
			$.messager.progress('close');
		},
		success : function(data) {
			$("#exportDiv").show();
			$("#result").append(data);
		},
		error: function(XMLHttpRequest, textStatus, errorThrown) {
			$.messager.alert('提示', '操作失败, 请联系管理员');
		}
	});
}
function getIp(value) {
	var space = '';
	if (value.length < 3) {
		for (var index = 0; index < 3 - value.length; index++) {
			space += '&nbsp;';
		}
	}
	return space + value;
}
function save() {
	var srcObj = $("#srcList").datagrid('getSelected');
	var desObj = $("#desList").datagrid('getSelected');
	
	if (srcObj == null) {
		$.messager.alert('提示', '请选择 [来源] 数据库服务器'); 
		return;
	}
	if (desObj == null) {
		$.messager.alert('提示', '请选择 [目标] 数据库服务器'); 
		return;
	}
	
	var data = $("#diffTbList").datagrid('getData');
	for (index in data.rows) {
		if (srcObj.id == data.rows[index].firstDbId && desObj.id == data.rows[index].secondDbId) {
			$.messager.alert('提示', '已有记录');
			$("#diffTbList").datagrid('selectRow', index);
			return;
		}
	}
	
	saveDialog();
}
function saveDialog() {
	$('#DIALOG').dialog({
		title: '相关信息',
		width: 400,
		height: 200,
		modal: true,
		href: "html/diffDialog.html",
		buttons:[
		    {id:'submitFormBtn', text:'提交', handler:function(){
		    	var param = new Object();
		    	param.firstDbId = $("#srcList").datagrid('getSelected').id;
		    	param.secondDbId = $("#desList").datagrid('getSelected').id;
		    	param.tbSchema = $("#actionForm #tbSchema").val();
		    	param.memo = $("#actionForm #memo").val();
		        $.ajax({
		    		type : 'POST',
		    		url : '/main/saveDiffTableInfo',
		    		data : param,
		    		dataType : 'json',
		    		beforeSend : function() {
		    			$.messager.progress({title : '提示', msg : '保存中..', text : ''});
		    		},
		    		complete : function() {
		    			$.messager.progress('close');
		    		},
		    		success : function(data) {
		    			if (data.rtnMesg == '成功') {
		    				$.messager.alert('提示', '保存成功', '', function() {
		    					$("#DIALOG").dialog('close');
		    					$('#diffTbList').datagrid('reload');
		    				});
		    			} else {
		    				$.messager.alert('提示', '保存失败, 请联系管理员');
		    			}
		    		},
		    		error: function(XMLHttpRequest, textStatus, errorThrown) {
		    			$.messager.alert('提示', '保存失败, 请联系管理员');
		    		}
		    	});
		    }}
        ],
        onLoad: function () {
        },
        onOpen:function(){
        	$(this).find("div.dialog-button").find("a.l-btn:first").removeClass("l-btn-small l-btn").addClass("subBtn");
        }
	});
}
function exportFile() {
    $.ajax({
		type : 'POST',
		url : '/main/exportFile',
		beforeSend : function() {
			$.messager.progress({title : '提示', msg : '导出文件中..', text : ''});
		},
		complete : function() {
			$.messager.progress('close');
		},
		success : function(data) {
			$.messager.alert('提示', '导出成功');
		},
		error: function(XMLHttpRequest, textStatus, errorThrown) {
			$.messager.alert('提示', '导出失败, 请联系管理员');
		}
	});
}