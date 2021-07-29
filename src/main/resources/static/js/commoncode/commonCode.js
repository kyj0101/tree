$(function() {
	
	$(".add-btn").click(function() {
		$(".firstCodeAddBtn").text("저장");
		$("#code").removeAttr("readonly");
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
		
		var $firstCodeAddForm = $(".firstCodeAddForm");
		var firstCode = new Object();
		
		firstCode.code = $("#code").val();
		firstCode.codeName = $("#codeName").val();
		firstCode.useAt = $("#useAt").val();
		
		var isNull = nullCheck(firstCode);
		var isHelpDisplay = helpDisplayCheck();
		var isUpdate = $(".firstCodeAddBtn").text() == "수정" ? true : false;
		
		
		if(isNull){
			alert("입력되지 않은 칸이 있습니다.");
			return;
		}else if(isUpdate){
			
			$firstCodeAddForm.attr("method", "POST");
			$firstCodeAddForm.attr("action", "/commoncode/code/update");
			$firstCodeAddForm.submit();
		
		}else if(isHelpDisplay){
			alert("중복된 값을 저장할 수 없습니다.");
			return;
		
		}else{
		
			$firstCodeAddForm.attr("method", "POST");
			$firstCodeAddForm.attr("action", "/commoncode/code/insert");
			$firstCodeAddForm.submit();
		}
	});
	
	$(".firstCode").click(function(){
		
		$(".help").addClass("hide");
		$(".firstCodeAddBtn").text("수정");
		
		var $code = $($(this).children()[0]);
		var $codeName = $($(this).children()[1]);
		var $useAt = $($(this).children()[2]);
		
		var code = $code.text();
		var codeName = $codeName.text();
		var useAt = $useAt.text();
		
		$("#code").val(code);
		$("#code").attr("readonly", "readonly");
		$("#codeName").val(codeName);
		$("#useAt").val(useAt);
		
	})
})