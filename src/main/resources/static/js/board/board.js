$(function(){
	$(".boardBtn").click(function(){
		$.ajax({
			type: "POST",
			url: "/commoncode/code/update",
			data: {
				"code": commonCode.code,
				"codeName": commonCode.codeName,
				"useAt": commonCode.useAt
			},
			dataType: "json",
			success(result) {

				if (result.resultCode == 0) {
					alert("수정하였습니다.");
					location.href = "/commoncode/list";

				} else {
					alert("수정에 실패하였습니다.");
					return;
				}
			},

			error(xhr, status, err) {
				console.log(xhr, status, err);
			}
		});

	})
});