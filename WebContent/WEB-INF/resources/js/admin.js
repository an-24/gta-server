
function openUser(id) {
	var url; 
	if(id)	url = "inner/admin/users/edit/"+id; else
			url = "inner/admin/users/add";
	$("#admin-content").load(url);
}

function openTeam(id) {
	var url; 
	if(id)	url = "inner/admin/teams/edit/"+id; else
			url = "inner/admin/teams/add";
	$("#admin-content").load(url);
}