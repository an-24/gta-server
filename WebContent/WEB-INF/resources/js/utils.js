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
		$.formUtils.reset();
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
	        	$.formUtils.showError(request.responseText);
	        }
	    });
		return false;
	});
    $('form:first *:input[type!=hidden]:first').focus();
}

function loadProjectSheet(teamId,token) {
	$.ajax({
		type:"GET",
		url:"../app/teams/"+token+"/"+teamId,
        success: function (data) {
        	var obj = eval(data);
        	var tab1 = $("#team-propertys #tabs-1");
        	tab1.find("#createDate").val(obj.createDate);
        	tab1.find("#limit").val(obj.limit ||"");
        	tab1.find("#workedOfDay").text(obj.workedOfDay ||"");
        	tab1.find("#workedOfWeek").text(obj.workedOfWeek ||"");
        	tab1.find("#workedOfMonth").text(obj.workedOfMonth ||"");
        	tab1.find("#workedOfBeginProject").text(obj.workedOfBeginProject ||"");
        	if(obj.managerCurrentUser) {
        		tab1.find("#limit").removeAttr("readonly");
            	tab1.find("#updTeamBtn button").click(function(){
            		updateTeam(obj.id,tab1.find("#limit").val());
            		console.log("test");
            	});
        	} else
        		tab1.find("#updTeamBtn").remove();
        	
        	var table = $("#person-list");
        	if(!window._dataTablePersons) {
        		window._dataTablePersons = table.dataTable({
	        		pageLength:4,
	        		lengthChange:false,
	        		info: false
	        	});
        	}
        	window._teamId = teamId;
        	window._dataTablePersons.DataTable().clear();
        	window._personList = {};
        	for(var i=0, len = data.persons.length;i<len;i++) {
        		var p = data.persons[i];
        		var s = p.nic;
        		if(p.manager) s = '<b>'+s+'</b>';
        		var alink = "<a onclick='openPerson("+p.id+");return false;' href='#"+p.id+"'>"+s+"</a>";
        		window._dataTablePersons
        			.DataTable()
        			.row.add([alink,p.postDict?p.postDict.name:p.post,(p.limit||""),p]);
        		window._personList[p.id] = p;
        	}
    		window._dataTablePersons
					.DataTable().draw();
    		backFormPerson(0);
        	
        }
		
	});
}

function openPerson(id) {
	var p; 
	if(id) p = window._personList[id];else 
		p = {nic:"New member"};
	var tabs = $("#tabs");
	var tabContentHtml = "<input value='back' type='button' onclick='backFormPerson(1)'/>" +
			"<div id='error-place' class='form-error getperson-error'/>" +
			"<div id='personForm'></div>";
	$(tabs).find("ul").append(
            "<li><a href='#tabs-3'>"+p.nic+"</a></li>"
        );
	tabs.append( "<div id='tabs-3'>" + tabContentHtml + "</div>");
	// скрыть
	$(tabs).find("ul li a[href='#tabs-1']").css("display","none");
	$(tabs).find("ul li a[href='#tabs-2']").css("display","none");
	
	tabs.tabs("refresh");
	tabs.tabs({active:2});
	if(id) $("#personForm").load("inner/person/edit/"+id);else
		 $("#personForm").load("inner/person/add/"+window._teamId);
}

function backFormPerson(tabid) {
	var tabs = $("#tabs");
	// открыть
	$(tabs).find("ul li a[href='#tabs-1']").css("display","block");
	$(tabs).find("ul li a[href='#tabs-2']").css("display","block");
	// удалим
	$(tabs).find("ul li a[href='#tabs-3']").parent().remove();
	$(tabs).find("div#tabs-3").remove();
	
	tabs.tabs("refresh");
	tabs.tabs({active:tabid});
}

function isInt(n){
    return Number(n)===n && n%1===0;
};

function updateTeam(id,limit) {
	
}