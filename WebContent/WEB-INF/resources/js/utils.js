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

function loadProjectSheet(id,token) {
	$.ajax({
		type:"GET",
		url:"../app/teams/"+token+"/"+id,
        success: function (data) {
        	var obj = eval(data);
        	var tab1 = $("#team-propertys #tabs-1");
        	tab1.find("#createDate").val(obj.createDate);
        	tab1.find("#limit").val(obj.limit ||"");
        	tab1.find("#workedOfDay").text(obj.workedOfDay ||"");
        	tab1.find("#workedOfWeek").text(obj.workedOfWeek ||"");
        	tab1.find("#workedOfMonth").text(obj.workedOfMonth ||"");
        	tab1.find("#workedOfBeginProject").text(obj.workedOfBeginProject ||"");
        	var plist = $("#person-list");
        	$(plist).empty();
        	for(var i=0, len = data.persons.length;i<len;i++) {
        		var p = data.persons[i];
        		$(plist).append($('<li>').addClass("ui-widget-content").text(p.nic+'#'+p.post));
        	}
        }
		
	});
}
