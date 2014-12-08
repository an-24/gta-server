function submitFunc(url,redirect,frm,cb) {
    $.ajax({
        type: "POST",
        url: url,
        data: frm.serialize(),
        success: function (data) {
        	var obj = eval(data);
        	if(obj.error) this.error({responseText:obj.message});else {
        		cb(data);
                frm.attr("action",redirect);
                frm.submit();
        	}
        },
        error: function (request, status, error,data) {
            alert(request.responseText);
        }
    });
}

function initForm(frm) {
	frm.keyup(function(event){
	    if(event.keyCode == 13){
	    	var btn = $("#default");
	        if(btn) btn.click();
	    }
	});
}
