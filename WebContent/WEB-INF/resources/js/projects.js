
function loadProjectSheet(teamId,token,tabActive) {
	$.ajax({
		type:"GET",
		url:"inner/team/"+teamId,
        success: function (data) {
        	var obj = eval(data);
    		$.formUtils.clean($("#frmTeam"));
        	var tab1 = $("#team-propertys #tabs-1");
        	tab1.find("#id").val(obj.id);
        	tab1.find("#createDate").val(obj.createDate);
        	tab1.find("#limit").val(obj.limit ||"");
        	tab1.find("#workedOfDay").text(obj.workedOfDay ||"");
        	tab1.find("#workedOfWeek").text(obj.workedOfWeek ||"");
        	tab1.find("#workedOfMonth").text(obj.workedOfMonth ||"");
        	tab1.find("#workedOfBeginProject").text(obj.workedOfBeginProject ||"");
        	if(obj.managerCurrentUser) {
        		tab1.find("#limit").removeAttr("readonly");
        		tab1.find("#updTeamBtn").css("display","table-cell");
            	$("#addPerson").css("display","block");
        	} else {
        		tab1.find("#limit").attr("readonly","");
        		tab1.find("#updTeamBtn").css("display","none");
            	$("#addPerson").css("display","none");
        	}
        	
        	
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
        			.row.add([alink,p.postName,(p.limit||""),p]);
        		window._personList[p.id] = p;
        	}
    		window._dataTablePersons
					.DataTable().draw();
    		backFormPerson(tabActive||0);
        	
        }
		
	});
}

function refreshProjectSheet(tabActive) {
	var teamId = $("#project-list .ui-selected").attr("team-id");
	loadProjectSheet(teamId,$.cookie("token"),tabActive);
}

