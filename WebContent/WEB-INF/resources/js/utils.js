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
        	var table = $("#person-list");
        	if(!window._dataTablePersons) {
        		window._dataTablePersons = table.dataTable({
	        		pageLength:4,
	        		lengthChange:false,
	        		info: false
	        	});
        	}
        	window._dataTablePersons.DataTable().clear();
        	for(var i=0, len = data.persons.length;i<len;i++) {
        		var p = data.persons[i];
        		var s = p.nic;
        		if(p.manager) s = '<b>'+s+'</b>';
        		var alink = "<a href='#"+p.id+"'>"+s+"</a>";
        		window._dataTablePersons
        			.DataTable()
        			.row.add([alink,p.postDict?p.postDict.name:p.post,(p.limit||""),p]);
        	}
    		window._dataTablePersons
					.DataTable().draw();
        	
        }
		
	});
}
