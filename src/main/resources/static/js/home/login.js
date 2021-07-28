$(function(){
	$(".btn-primary").click(function(){
		var email = $("#email").val();
		var password = $("#password").val();
		
		if(email.length < 1){
			
			alert("이메일을 입력하세요.");
			return false;
		
		}else if(password.length < 1){
			
			alert("비밀번호를 입력하세요.");
			return false;
		
		}else{
			$(".login-form").submit();
		}
		
		
	});
})