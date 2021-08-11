$(function(){

});


//수정버튼 눌렸을 때 팝업 값 설정
function clickPopup(e){
	
	var positionName = $($($(e).parents()[0]).prevAll()[1]).text();
	var departmentName = $($($(e).parents()[0]).prevAll()[2]).text();
	var name = $($($(e).parents()[0]).prevAll()[3]).text();
	var email = $($($(e).parents()[0]).prevAll()[4]).text();
	
	$('#updateEmail').val(email);
	$('#updateName').val(name);
	
	$.each($('.positionNameOption'), function(k,v){
		var text = $(v).text();
		
		if(positionName === text){
			$(v).attr("selected", true);
		}
	});
	
	$.each($('.departmentNameOption'), function(k,v){
		var text = $(v).text();
		
		if(departmentName === text){
			$(v).attr("selected", true);
		}
	});	
}

//팝업에서 수정 버튼 눌렸을 때 이벤트
function clickUpdateBtn(){
	
	var member = {
		email:$("#updateEmail").val(),
		name:$("#updateName").val(),
		department:$('#department option:selected').val(),
		position:$('#position option:selected').val()		
	}
	
	var isNull = nullCheck(member);
	var isHelpDisplay = helpDisplayCheck();
	
	if(isNull){
		alert("빈 칸이 있습니다.");
		return;
		
	}else if(isHelpDisplay){
		alert("잘못 입력된 칸이 있습니다.")
		return;
	
	}else{
		$.ajax({

		type: "post",
		url: "/employee/update",
		data: {
			"email": member.email,
			"name":member.name,
			"department":member.department,
			"position":member.position
		},
		success(result) {

			if (result == "ok") {

				$('#update-member-modal').modal('hide')
				alert("수정 되었습니다.");
				location.replace(window.location.href);

			} else {
				alert("수정을 실패했습니다.");
			}
		},
	});
	}
	
	
}

//팝업에서 회원 탈퇴 눌렸을때 이벤트
function clickWithdrawBtn(){
	
	if(confirm("정말로 회원탈퇴하시겠습니까?")){
		
		var email = $("#updateEmail").val();
		
		$.ajax({
	
			type: "post",
			url: "/employee/withdraw",
			data: {
				"email":email
			},
			success(result) {
	
				if (result == "ok") {
	
					$('#update-member-modal').modal('hide')
					alert("회원 탈퇴되었습니다.");
					location.replace(window.location.href);
	
				} else {
					alert("회원 탈퇴를 실패했습니다.");
				}
			},
		});
	}
}


//팝업에서 이름 입력될 때 이벤트
function changeName(){
	var name = $("#updateName").val();
	
	if(!nameRegExp(name)){
		$("#nameHelp").removeClass("hidden");
	
	}else{
		$("#nameHelp").addClass("hidden");
	}
}
