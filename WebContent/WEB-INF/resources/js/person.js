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
