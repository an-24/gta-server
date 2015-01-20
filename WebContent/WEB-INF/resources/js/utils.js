window.gtautils = {};
window.gtautils.i18n = {}
window.gtautils.lang = "defaults"
	
window.gtautils.i18n.ru = {
		labelClose:"Закрыть",
		titleError:"Ошибка"
}
window.gtautils.i18n.en = {
		labelClose:"Close",
		titleError:"Error"
}

window.gtautils.i18n.defaults = window.gtautils.i18n.ru; 

//==========================================
function initForm(frm,url,cb,redirect) {
	
    $.validate({
    	validateOnBlur : false,
    	errorMessagePosition : $('#error-place'),
    	modules : 'security',
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
		if($.formUtils.errorFound) return false;
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

function initMultiPartForm(frm,url,cb,redirect) {
	
    $.validate({
    	validateOnBlur : false,
    	errorMessagePosition : $('#error-place'),
    	modules : 'security',
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
		if($.formUtils.errorFound) return false;
		
		var formData = new FormData(this);
		
		$.ajax({
			type: "POST",
	        url: url,
	        mimeType:"multipart/form-data",
	        data: formData,
            contentType: false,
            cache: false,
            processData:false,
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

function selectSelectableElement(selectableContainer, elementsToSelect){
    $(".ui-selected", selectableContainer).not(elementsToSelect).removeClass("ui-selected").addClass("ui-unselecting");
    $(elementsToSelect).addClass("ui-selecting");
    selectableContainer.data().uiSelectable._mouseStop(null);
}

function messageDlg(title,text, onlyPrepare, extbuttons,opt) {
	var suffix = Math.round(100000000*Math.random());
	var formName = "dialog-message-dlg"+suffix;
	$("<div id='"+formName+"'>").appendTo(document.body);
	var closeBtn = gtautils.i18n.getStrings().labelClose;
	var dlgbuttons = {};
	if(extbuttons) {
		dlgbuttons = extbuttons; 
	} else {
		dlgbuttons[closeBtn] = function() {
			 $(this).dialog("close");
		 }; 
	}
	var baseopt = {
			title:title,
			modal: true,
			buttons:dlgbuttons,
			autoOpen: !onlyPrepare
	} 
	if(opt) {
		$.extend(baseopt, opt);
	}
	var dlg = $("#"+formName).dialog(baseopt);
	dlg.html(text);
	return dlg;
}

function messageError(text) {
	var dlg =  messageDlg(gtautils.i18n.getStrings().titleError,text);
	$(dlg[0].parentNode).find(".ui-dialog-titlebar").attr("style","background-color:red !important");
	return dlg;
}


window.gtautils.i18n.getStrings = function() {
	return window.gtautils.i18n[window.gtautils.lang];
};
