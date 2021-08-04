$(function(){
	$(".boardBtn").click(function(){

		var board = new Object()
		board.title = $("#title").val();
		board.content = $("#content").val();
		
		var formData = new FormData();
		var inputFile = $("input[name='uploadFile']");
		var count = 0;
		
		$.each(inputFile, function(index, elem){
            
			if(elem.files.length > 0){
				count++;
				formData.append("uploadFile",$(elem)[0].files[0]);
			}
        });
		
		if(count > 0){
			$.ajax({
				url: "/board/file/insert",
				processData: false,
				contentType: false,
				data: formData,
				type: 'POST',
				success: function(result) {
					insertBoard(board,result);
				}
			});
		}else{
			insertBoard(board,null);
		}


		

	})
});

function insertBoard(board,result){
	$.ajax({
		type: "POST",
		url: "/board/insert",
		data: {
			"title": board.title,
			"content": board.content,
			"fileId": result
		},
		success(result) {
			console.log(result);
			if (result == "ok") {
				alert("작성되었습니다.");
				location.replace('/board/list') 

			} else {
				alert("작성에 실패하였습니다.");
				return;
			}
		},
	});
}