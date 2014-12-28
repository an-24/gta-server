function initForm(frm,url,cb,redirect) {
	
    $.validate({
    	validateOnBlur : false,
    	errorMessagePosition : $('#error-place'),
    	scrollToTopOnError : false,
    	onError : function() {
            if(!$.formUtils.haltValidation) {
            	$.formUtils.errorFound = true;
            }
        }
    });
    $(frm).attr("onsubmit","return false;");
    $(frm).attr("action","");
    $(frm).attr("method","POST");
    
	$(frm).submit(function(){
		$.formUtils.reset(frm);
		$.formUtils.errorFound = false;
		$(frm).trigger('submit.validation');
		if($.formUtils.errorFound)
			return false;
		$.ajax({
			type: "POST",
	        url: url,
	        data: $(frm).serialize(),
	        success: function(data){
	        	var obj = eval(data);
	        	if(obj.error) this.error({responseText:obj.message});else {
	        		if(cb) cb(data);
	        		redirect = redirect || data.redirect;
	            	if(redirect) {
	            		window.location.href=redirect;
	            	};
	        	}
	        },
	        error: function (request, status, error,data) {
	        	if($('#error-place').length==0) {
	            	toastr["error"](request.responseText, "Error");
	        	} else
	        	$.formUtils.showError(request.responseText);
	        }
	    });
		return false;
	});
    $('form:first *:input[type!=hidden]:first').focus();
}

function isInt(n){
    return Number(n)===n && n%1===0;
};

