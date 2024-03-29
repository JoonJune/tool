if ($.fn.pagination) {
	$.fn.pagination.defaults.beforePageText = '第';
	$.fn.pagination.defaults.afterPageText = '页    共{pages}页';
	$.fn.pagination.defaults.displayMsg = '当前显示 {from} 到 {to} 条记录, 共 {total} 记录';
}
if ($.fn.datagrid) {
	$.fn.datagrid.defaults.loadMsg = '正在处理，请稍等...';
}
if ($.fn.treegrid && $.fn.datagrid) {
	$.fn.treegrid.defaults.loadMsg = $.fn.datagrid.defaults.loadMsg;
}
if ($.messager) {
	$.messager.defaults.ok = '确定';
	$.messager.defaults.cancel = '取消';
}
//日期插件汉化
if ($.fn.calendar){
	$.fn.calendar.defaults.weeks = ['日','一','二','三','四','五','六'];
	$.fn.calendar.defaults.months = ['一月','二月','三月','四月','五月','六月','七月','八月','九月','十月','十一月','十二月'];
}
if ($.fn.datebox){
	$.fn.datebox.defaults.currentText = '今天';
	$.fn.datebox.defaults.closeText = '关闭';
	$.fn.datebox.defaults.okText = '确定';
	$.fn.datebox.defaults.formatter = function(date){
		var y = date.getFullYear();
		var m = date.getMonth()+1;
		var d = date.getDate();
		return y+'-'+(m<10?('0'+m):m)+'-'+(d<10?('0'+d):d);
	};
	$.fn.datebox.defaults.parser = function(s){
		if (!s) return new Date();
		var ss = s.split('-');
		var y = parseInt(ss[0],10);
		var m = parseInt(ss[1],10);
		var d = parseInt(ss[2],10);
		if (!isNaN(y) && !isNaN(m) && !isNaN(d)){
			return new Date(y,m-1,d);
		} else {
			return new Date();
		}
	};
}
if ($.fn.datetimebox && $.fn.datebox){
	$.extend($.fn.datetimebox.defaults,{
		currentText: $.fn.datebox.defaults.currentText,
		closeText: $.fn.datebox.defaults.closeText,
		okText: $.fn.datebox.defaults.okText
	});
}
if ($.fn.datetimespinner){
	$.fn.datetimespinner.defaults.selections = [[0,4],[5,7],[8,10],[11,13],[14,16],[17,19]]
}
//给时间控件(datebox)增加清空按钮
if ($.fn.datebox) {
	var _datebox_buttons = $.extend([], $.fn.datebox.defaults.buttons);
	_datebox_buttons.splice(1, 0, {
		text : '清空',
		handler : function(_datebox_target) {
			$(_datebox_target).combo("setValue", "").combo("setText", ""); // 设置空值
			$(_datebox_target).combo("hidePanel"); // 点击清空按钮之后关闭日期选择面板
		}
	});
	$.fn.datebox.defaults.buttons = _datebox_buttons; 
}

//给时间控件(datetimebox)增加清空按钮
if ($.fn.datetimebox) {
	var _datebox_buttons = $.extend([], $.fn.datetimebox.defaults.buttons);
	_datebox_buttons.splice(2, 0, {
		text : '清空',
		handler : function(_datebox_target) {
			$(_datebox_target).combo("setValue", "").combo("setText", ""); // 设置空值
			$(_datebox_target).combo("hidePanel"); // 点击清空按钮之后关闭日期选择面板
		}
	});
	$.fn.datetimebox.defaults.buttons = _datebox_buttons; 
}