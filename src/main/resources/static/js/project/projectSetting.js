$(function(){
	$("#searchKeyword").keydown(function(key) {
		if (key.keyCode == 13) {
			$("#searchForm").submit();
		}
	});
})

function getMemberList(){
	
	$.ajax({

		type: "post",
		url: "/project/memberList",
		data:{"projectId":projectId},
		success(result) {
			insertListGroup(result);
		},
		
	});
}

function memberClick(e){
	var valueArr= $(e).data('esntlid').split(",");
	
	var html = '<p class="memberA" data-esntlid="'
	html += valueArr + '">'
	html += valueArr[0];
	html += '<i class="fas fa-times" onclick="nameCloseClick(this);"></i>';
	html += '</p>' 

	$(e).remove();
	$(".card-body").append(html);
}

function nameCloseClick(e){
	
	var valueArr = $($(e).parent()).data('esntlid').split(",");

	var html = '<a href="#" class="list-group-item list-group-item-action" onclick="memberClick(this);"';
	html += 'data-esntlId="';
	html += valueArr;
	html += '"';
	html += '>';
	html += valueArr[0];
	html += '('
	html += valueArr[2];
	html += ')';
	html += '<span class="badge badge-pill badge-primary">';
	html += valueArr[3];
	html += '</span>';
	html += '<span class="badge badge-pill badge-info">';
	html += valueArr[4];
	html += '</span>';
	html += '</a>';
	
	$(".list-group").append(html);
	$($(e).parent()).remove();
}

function reset(){
	$(".card-body").empty();
}

function addMember(){
	var memberArr = $(".card-body").children();
	var esntlIdArr = [];
	
	$.each(memberArr, function(index, elem){
		
		var valueArr = $(elem).data('esntlid').split(",");
		var esntlId = valueArr[1];
		
		esntlIdArr.push(esntlId);
	});
	
	$.ajax({

		type: "post",
		url: "/project/insert/member",
		data: {
			"esntlIdList": esntlIdArr, 
			"projectId":projectId
			},
		success(result) {
			result == "ok" && alert("추가되었습니다.");
			result == "fail" && alert("추가를 실패하였습니다.");
			
			location.replace("/project/setting/view?projectId="+ projectId);
		},
	});
}

function clickTr(e){
	
	var tagArr = $(e).children();
	
	var esntlId = $(tagArr[0]).text();
	$("#esntlInput").val(esntlId);
	
	var name = $(tagArr[2]).text();
	$("#nameInput").val(name);

	var role = $(tagArr[5]).text();
	$("#roleInput").val(role);
	
	if(role == "일반"){
		$("#selectU").attr("selected","true");
	}else{
		$("#selectM").attr("selected","true");
	}
}

function updateRole(){
	
	var selectVar = $(".updateSelect option:selected").val();
	var role = $("#roleInput").val();
	var esntlId = $("#esntlInput").val();

	if(selectVar === 'selectM'){

		if(role === "매니저"){
			alert("이미 권한이 매니저 입니다.");
			return;

		}else{
			updateRoleManger(esntlId);			
		}
		
	}else if(selectVar === 'selectU'){
		
		if(role === "일반"){
			alert("이미 권한이 일반입니다.");
			return;

		}else{
			updateRoleUser(esntlId);			
		}
	}else{
		confirm("프로젝트에서 탈퇴시키겠습니까?") && deleteMember(esntlId);
	}
}

function updateRoleManger(esntlId){

	$.ajax({

		type: "post",
		url: "/project/update/role/manager",
		data: {
			"projectId": projectId,
			"esntlId": esntlId,
			"role": "M"
		},
		success(result) {
			result == "ok" && alert("수정되었습니다.");
			result == "fail" && alert("수정을 실패했습니다");

			location.replace("/project/setting/view?projectId=" + projectId);
		},
	});
}

function updateRoleUser(esntlId){

	$.ajax({

		type: "post",
		url: "/project/update/role",
		data: {
			"projectId": projectId,
			"esntlId": esntlId,
			"role": "U"
		},
		success(result) {
			result == "ok" && alert("수정되었습니다.");
			result == "fail" && alert("수정을 실패했습니다");

			location.replace("/project/setting/view?projectId=" + projectId);
		},
	});
}

function deleteMember(esntlId){
	
	$.ajax({

		type: "post",
		url: "/project/delete/member",
		data: {
			"projectId": projectId,
			"esntlId": esntlId
		},
		success(result) {
			result == "ok" && alert("탈퇴되었습니다.");
			result == "fail" && alert("탈퇴를 실패했습니다");

			location.replace("/project/setting/view?projectId=" + projectId);
		},
	});
}

function changeSelect(){
	
	var department = $("#departmentSelect option:selected").val()
	var position = $("#positionSelect option:selected").val();
	
	$.ajax({

		type: "post",
		url: "/project/memberList",
		data: {
			"projectId":projectId,
			"department": department,
			"position" : position 
			},
		success(result) {
			insertListGroup(result)
		},
	});
}

function insertListGroup(result){
	console.log(result.length);
	
	$(".list-group").empty();
	
	if(result.length == 0){
		$("#addMemberBtn").css("display","none");
		$(".list-group").append('<p class="center">'+noMsg+'</p>');
		
	}else{
		$("#addMemberBtn").css("display","inline-block");
	}

	result.forEach(function(member) {

		var html = '<a href="#" class="list-group-item list-group-item-action" onclick="memberClick(this);"';
		html += 'data-esntlId="';
		html += member.name + "," + member.esntlId + "," + member.email + "," + member.departmentName + "," + member.positionName;
		html += '"';
		html += '>';
		html += member.name;
		html += '('
		html += member.email;
		html += ')';
		html += '<span class="badge badge-pill badge-primary">'
		html += member.departmentName;
		html += '</span>'
		html += '<span class="badge badge-pill badge-info">'
		html += member.positionName;
		html += '</span>'
		html += '</a>'

		$(".list-group").append(html);
	});
	
	return new Promise(function(resolve, reject) {
		$('#addMember').modal('show');
	});
}

function addNote(){
	
	var note = $("#note").val();
	
	$.ajax({

		type: "post",
		url: "/project/insert/note",
		data:{
			"projectId":projectId,
			"note":note
			},
		success(result) {
			result == "ok" && alert("작성되었습니다.");
			result == "fail" && alert("작성을 실패했습니다.");

			location.replace("/project/setting/view?projectId=" + projectId);			
		},
	});
}

function updateProject(){
	
	var projectNm = $("#projectName").val();
	var startDate = $("#startDate").val();
	var endDate = $("#endDate").val();
	
	$.ajax({

		type: "post",
		url: "/project/update/project",
		data:{
			"projectId" : projectId,
			"projectNm": projectNm,
			"startDate": startDate,
			"endDate" : endDate
			},
		success(result) {
			result == "ok" && alert("수정되었습니다.");
			result == "fail" && alert("수정을 실패했습니다.");

			location.replace("/project/setting/view?projectId=" + projectId);			
		},
	});
}

