$(function() {
	$(".boardBtn").click(function() {

		var board = new Object()
		board.title = $("#title").val();
		board.content = $("#content").val();

		var formData = new FormData();
		var inputFile = $("input[name='uploadFile']");
		var count = 0;

		$.each(inputFile, function(index, elem) {

			if (elem.files.length > 0) {
				count++;
				formData.append("uploadFile", $(elem)[0].files[0]);
			}
		});

		board.noticeAt = $("#noticeAt:checked").val();

		if (count > 0) {
			$.ajax({
				url: "/board/file/insert",
				processData: false,
				contentType: false,
				data: formData,
				type: 'POST',
				success: function(result) {
					insertBoard(board, result);
				}
			});
		} else {
			insertBoard(board, null);
		}
	});

	$(".boardTitle").click(function() {
		var siblings = $(this).siblings();
		var boardNo = 0;
		
		//3개라면 일반 게시글 이상이라면 공지
		if (siblings.length == 3) {
			boardNo = $($(siblings[0])).text();

		} else {
			boardNo = $($(siblings[1])).val();
		}

		$.ajax({
			type: "POST",
			url: "/board/detail",
			data: {
				"title": board.title,
				"content": board.content,
				"fileId": result,
				"noticeAt": board.noticeAt
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
	});
});

function insertBoard(board, result) {
	$.ajax({
		type: "POST",
		url: "/board/insert",
		data: {
			"title": board.title,
			"content": board.content,
			"fileId": result,
			"noticeAt": board.noticeAt
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