

var origin = $.validate;
$.validate = function(conf) {
	conf.errorMessageClass = "s";
	origin(conf);
    conf = $.extend({}, $.formUtils.defaultConfig(), conf || {});
	$.formUtils.showError = function(msg) {
		if($.validate._lastmessage)
			$.validate._lastmessage.remove();
		var mess = $('<div class="'+conf.errorMessageClass+'">'+msg+'</div>');
		$.validate._lastmessage = mess;
		conf.errorMessagePosition.append(mess);
	};
	$.formUtils.reset = function(frm) {
		$(conf.errorMessagePosition).empty();
	};
	
	$.formUtils.clean = function(frm) {
		$(frm).trigger('reset.validation');
	};
	
    $.formUtils.addValidator({
        name : 'key',
        validatorFunction : function(val, $el, config, language, $form) {
        	if (typeof val == 'string' || val instanceof String)
        		val = parseInt(val);
        	return isInt(val) && val>0;
        },
        errorMessage : '',
        errorMessageKey: 'requiredFields'
    });
	
};

toastr.options = {
	closeButton: true,
	debug: false,
	progressBar: true,
	positionClass: "toast-top-center",
	onclick: null,
	showDuration: "300",
	hideDuration: "1000",
	timeOut: "5000",
	extendedTimeOut: "1000",
	showEasing: "swing",
	hideEasing: "linear",
	showMethod: "fadeIn",
	hideMethod: "fadeOut"
};
