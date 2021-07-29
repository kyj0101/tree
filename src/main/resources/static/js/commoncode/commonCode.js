$(function() {
	$(".add-btn").click(function() {
		$("input").val("")
	});
	
	$("#code").change(function(){
		
		$(".help").addClass("hide");
		var code = $('#code').val();
		
		$.ajax({
			type: 'get',
			url: 'http://localhost:9090/commoncode/code/duplication/check',
			data: {'code': code},

			success(val) {
				if (val == "true") {
					$(".help").removeClass("hide");
				}
			},
			error(xhr, status, err) {
				console.log(xhr, status, err);
			},
		});
	});

	$(".firstCodeAddBtn").click(function() {
		
		var firstCode = new Object();
		
		firstCode.code = $("#code").val();
		firstCode.codeName = $("#codeName").val();
		firstCode.useAt = $("#useAt").val();
		
		var isNull = nullCheck(firstCode);
		var isHelpDisplay = helpDisplayCheck();
		
		if(isNull){
			alert("입력되지 않은 칸이 있습니다.");
			return;
		
		}else if(isHelpDisplay){
			alert("중복된 값을 저장할 수 없습니다.");
			return;
		
		}else{
			var $firstCodeAddForm = $(".firstCodeAddForm");
	
			$firstCodeAddForm.attr("method", "POST");
			$firstCodeAddForm.attr("action", "/commoncode/list/insert");
			$firstCodeAddForm.submit();
		}


	});
})