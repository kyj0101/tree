$(function(){
	
	$(".btnUpdate").click(function(){
		
		var member = new Object();
		
		member.email = $("#email").val();
		member.name = $("#name").val();
		member.phone = $("#phone").val();
		member.birth = $("#birth").val();
		member.department = $("#department").val();
		member.position = $("#position").val();
		
		var isNull = nullCheck(member);
		var isHelpDisplay = helpDisplayCheck();
			
		if(confirm("수정하시겠습니까?")){
			
			if(isNull){
				alert("빈 칸이 있습니다.");
				return;
			
			}else if(isHelpDisplay){
				alert("잘못입력된 칸이 있습니다.");
				return;
				
			}else{
				$("#updateForm").attr("method", "POST");
				$("#updateForm").attr("action", "/member/mypage/update/update");
				$("#updateForm").submit();
			}
		}
	});
	
	$("#name").change(function(){
		
		var name = $(this).val();
		
		$("#nameHelp").addClass("hide");
		
		if(!nameRegExp(name)){
			$("#nameHelp").removeClass("hide");
		}
	});
	
	$("#phone").change(function(){
		
		var phone = $(this).val();
		
		$("#phoneHelp").addClass("hide");
		
		if(!phoneRegExp(phone)){
			$("#phoneHelp").removeClass("hide");
		}
	});
});