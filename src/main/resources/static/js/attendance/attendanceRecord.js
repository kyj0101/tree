$(function(){
	
	//출근 버튼 클릭시 출근 처리
	$(".attendanceInBtn").click(function(){
		
		$.ajax({
		
			type:"post",
			url:"/attendance/in",
			
			success(result){
				
				if(result == "ok"){
					alert("출근 처리 되었습니다.");
					location.replace("/board/list");
					
				}else{
					alert("출근 처리를 실패했습니다.");
				}
			},	
		});
	}); //end of $(".attendanceInBtn").click()
	
	//퇴근 버튼 클릭시 퇴근 처리
	$(".attendanceOutBtn").click(function(){
		
		$.ajax({
		
			type:"post",
			url:"/attendance/out",
			
			success(result){
				
				if(result == "ok"){
					alert("퇴근 처리 되었습니다.");
					location.replace("/board/list");
					
				}else{
					alert("퇴근 처리를 실패했습니다.");
				}
			},	
		});
	}); // end of $(".attendanceOutBtn").click()
	
});