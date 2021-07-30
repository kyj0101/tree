function loginAction(){
	
	var form = $('#loginForm');
	var email = $('#email').val();
	var password = $('#password').val();
 	
	if(email.length < 1){
		
		alert("이메일을 입력하세요.");
		return false;
	
	}else if(password.length < 1){
		
		alert("비밀번호를 입력하세요.");
		return false;
		
	}else{
		$.ajax({
			type:"post",
			url:"http://localhost:9090/login/check",
			data:{"email":email, "password":password},
			
			success(val){
				console.log(val);
				if(val == "OK"){
					form.attr('method', 'POST');
					form.attr('action', '/board/list');
					form.submit();
				
				}else if(val == "EMAIL"){
					alert("이메일 인증을 완료하세요.");
				
				}else{
					alert("로그인에 실패했습니다.");
				}
				
			},
			error(xhr, status, err){
				console.log(xhr, status, err);
			},
		});
	}
}

