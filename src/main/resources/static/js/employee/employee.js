$(function(){
	$(".employeeUpdateBtn").on("click", clickUpdateBtn);
	
		
});


//수정버튼 눌렸을 때
function clickUpdateBtn(){
	console.log($(this).prevAll());
	return;
	$.ajax({
		type: "POST",
		url: "/board/file/delete",
		data: {
			"renamedFile": renamedFile,
			"fileStore": fileStore
		},

		success(result) {
			alert("삭제되었습니다.");
		},
	});
}//end of clickUpdateBtn()