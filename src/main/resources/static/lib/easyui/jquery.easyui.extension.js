$.extend($.fn.textbox.defaults.rules, {
	maxLength: {
		validator: function(value, param) {
			return value.length >= param[0];
		},
		message : '长度不能大于{0}'
	}
});