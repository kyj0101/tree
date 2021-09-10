$(function(){
	
	$("#projectManager").autocomplete({	
		
		source: function(request, response) {
		
			$.ajax({
				url: "/attendance/auto/name",
				data: {
					searchName: request.term
				},
				success(data) {

					response(
						$.map(data, function(item) {

							return {
								label: item.name + "," + item.email + ","+ item.departmentName +   "," + item.positionName + "," + item.esntlId,
								value: item.name
							}
						})
					);
				},

				error(xhr, status, err) {
					console.log(xhr, status, err);
				}
			});//end of ajax   	  
		},//end of source

		focus: function(e, focus) {
			return false;
		},

		select: function(e, select) {
			var label = select.item.label;
			var index = label.split(",");
			label = index[4];
			
			$("#projectManagerEsntlId").val(label);
		}
	});
})

function addProject(){
	var project = new Object();
	
	project.name = $("#projectName").val();
	project.startDate = $("#startDate").val();
	project.endDate = $("#endDate").val();
	
	var isNull = nullCheck(project);
	
	if(isNull){
		alert("입력되지 않은 칸이 있습니다.");
		return;
	
	}else{
		$.ajax({
			type: "POST",
			url: "/project/insert",
			data: {
				"projectNm": project.name,
				"projectManager" : $("#projectManagerEsntlId").val(),
				"startDate" : project.startDate,
				"endDate" : project.endDate,
				"note" : $("#note").val()
			},
			success(result) {

				if (result == "ok") {
					alert("프로젝트가 추가되었습니다.");
					$("#createProject").modal('hide');
					location.replace("/project/list");
					
				} else {
					alert("프로젝트 추가를 실패했습니다.");
				}
			},

			error(xhr, status, err) {
				console.log(xhr, status, err);
			}
		});		
	}	
}

function deleteProject(e){
	
	var projectId = $($(e).parent().next().children()).text();
	
	if(confirm("삭제하시겠습니까?")){
		
		$.ajax({
			type: "POST",
			url: "/project/delete/project",
			data: {
				"projectId": projectId
			},
			success(result) {

				if (result == "ok") {
					
					alert("삭제되었습니다.");
					location.replace("/project/list");
					
				} else {
					alert("삭제를 실패했습니다.");
				}
			}
		});		
	}
}