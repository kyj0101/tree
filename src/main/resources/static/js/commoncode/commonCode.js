var cPage = 1; //페이지바 현재 페이지
var pageCode = ""; //페이지바 이동시 코드 저장할 변수
var total = 0;
var commonCodeClickNum = 0; //연속으로 공통코드 테이블 누르면 pageCode 공백대입

$(function() {
	
	//공통코드 추가 버튼 이벤트
	$(".add-btn").click(function() {
		
		$(".commonCodeAddBtn").text("저장");
		
		$("#code").removeAttr("readonly");
		$("#code").css("background","none");
		
		commonCodeEmpty();
		detailCodeEmpty();
		
		$(".detailCodeTbody").empty();
		$(".detailCodePageBar").empty();
	});
	
	//공통코드에 코드 입력할때 중복 검사 
	$("#code").change(function(){
		
		var url = '/commoncode/code/duplication/check';
		var code = $('#code').val();
		
		codeDuplicationCheck(url, $(".commonCodeHelp"), code)

	});

	$(".commonCodeAddBtn").click(function() {
		
		var isUpdate = $(".commonCodeAddBtn").text() == "수정" ? true : false;
		var commonCode = new Object();
		
		commonCode.code = $("#code").val();
		commonCode.codeName = $("#codeName").val();
		commonCode.useAt = $('input[name="useAt"]:checked').val();

		if(confirm($(".commonCodeAddBtn").text() + "하시겠습니까?")){

			var isNull = nullCheck(commonCode);
			var isHelpDisplay = helpDisplayCheck();
		
			if(isNull){
				alert("입력되지 않은 칸이 있습니다.");
				return;
				
			}else if(isUpdate){
				
				$.ajax({
					type: "POST",
					url: "/commoncode/code/update",
					data: {
						"code":commonCode.code,
						"codeName":commonCode.codeName,
						"useAt":commonCode.useAt
					},
					dataType:"json",
					success(result) {

						if(result.resultCode == 0){
							alert("수정하였습니다.");
							location.href="/commoncode/list";

						}else{
							alert("수정에 실패하였습니다.");
							return;	
						}
					},
			
					error(xhr, status, err) {
						console.log(xhr, status, err);
					}
				});
				
			}else if(isHelpDisplay){
				alert("중복된 값을 저장할 수 없습니다.");
				return;
			
			}else{

				$.ajax({
					type: "POST",
					url: "/commoncode/code/insert",
					data: {
						"code":commonCode.code,
						"codeName":commonCode.codeName,
						"useAt":commonCode.useAt
					},
					dataType:"json",
					success(result) {

						if(result.resultCode == 0){
							alert("저장하였습니다.");
							location.href="/commoncode/list";
						
						}else{
							alert("저장에 실패하였습니다.");
							return;	
						}
					},
			
					error(xhr, status, err) {
						console.log(xhr, status, err);
					}
				});
			}
		}
	});
	
	$(".detailCodeAdd").click(function(){
	
		var detailCode = new Object();
		
		detailCode.code = $(".commonCode").val();
		detailCode.detailCode = $("#detailCode").val();
		detailCode.detailCodeName = $("#detailCodeName").val();
		detailCode.sortOrdr = $("#sortOrdr").val();
		detailCode.detailCodeUseAt = $('input[name="detailCodeUseAt"]:checked').val();

		if(confirm($(".detailCodeAdd").text() + "하시겠습니까?")){
			
			var isUpdate = $(".detailCodeAdd").text() == "수정" ? true : false;
			var isNull = nullCheck(detailCode);
			var isHelpDisplay = helpDisplayCheck();
			
			if(isNull){
				alert("입력되지 않은 칸이 있습니다.");
				return;
				
			}else if(isUpdate){
				
				$.ajax({
					type: "get",
					url: "/commoncode/detail/code/update",
					data: {
						"commonCode":detailCode.code,
						"detailCode":detailCode.detailCode,
						"detailCodeName":detailCode.detailCodeName,
						"sortOrdr":detailCode.sortOrdr,
						"detailCodeUseAt":detailCode.detailCodeUseAt
					},
	
					success(result) {
						
						pageCode = detailCode.code;
						commonCodeClickNum = 0;
						
						$(".commonCodeTr").click();
						
						if(result == 'ok'){
							alert("수정했습니다.");
						
						}else{
							alert("수정에 실패했습니다.")
						}
					},
			
					error(xhr, status, err) {
						console.log(xhr, status, err);
					}
				});
			
			}else if(isHelpDisplay){
				alert("중복된 값을 저장할 수 없습니다.");
				return;
				
			}else{
				$.ajax({
					type: "get",
					url: "/commoncode/detail/code/insert",
					data: {
						"commonCode":detailCode.code,
						"detailCode":detailCode.detailCode,
						"detailCodeName":detailCode.detailCodeName,
						"sortOrdr":detailCode.sortOrdr,
						"detailCodeUseAt":detailCode.detailCodeUseAt
					},
	
					success(result) {
						
						pageCode = detailCode.code;
						commonCodeClickNum = 0;
						
						$(".commonCodeTr").click();
						
						if(result == 'ok'){
							alert("저장했습니다.");
						
						}else{
							alert("저장에 실패했습니다.")
						}
					},
			
					error(xhr, status, err) {
						console.log(xhr, status, err);
					}
				});
			}
		}
	}); //end of $(".detailCodeAdd").click()

	
	
	$(".commonCodeTr").click(function(){
		
		commonCodeClickNum += 1;

		if(commonCodeClickNum >= 6){
			cPage = 1;
			pageCode = "";
		}
		
		$(".help").addClass("hide");
		$(".commonCodeAddBtn").text("수정");

		code = $($(this).children()[0]).text();

		if(pageCode != ""){
			code = pageCode;
		}
		
		$.ajax({
			type: "get",
			url: "/commoncode/code/detail",
			data: { "code": code,},
			dataType: "json",
			
			success(result) {

				$("#codeName").val(result.codeName);

				if(result.useAt == 'Y'){
					$(".useAtY").prop("checked", true);
				
				}else{
					$(".useAtN").prop("checked", true);
				}
			},
	
			error(xhr, status, err) {
				console.log(xhr, status, err);
			}
		});

		$("#code").val(code);
		$("#code").attr("readonly", "readonly");
		$("#code").css("background","#c0c0c0");
		$(".commonCode").val(code);

		$.ajax({
			type:"get",
			url:"/commoncode/detail/code/list",
			data:{"code":code, "cPage":cPage},
			dataType:"json",
			success(result){

				$(".detailCodeTbody").empty();
				$(".detailCodePageBar").empty();

				$.each(result, function(i, item){
					
					console.log(item);
					if(i == result.length -1 ){
						total = item.total;

					}else{
											
						var html = "";
						
						html += "<tr class='detailCodeTr'>";
						html += '<td>' + item.detailCode + '</td>';
						html += '<td>' + item.detailCodeName + '</td>';
						html += '<td>' + item.useAt + '</td>';
						html += '<td>' + item.sortOrdr + '</td>';
						html += '</tr>';
						
						$(".detailCodeTbody").append(html);
					}
				});
				
				//페이지바
				var pageBarSize = 5;
				var pageStart = Math.floor(((cPage - 1) / pageBarSize) * (pageBarSize + 1))
				
				if(pageStart == 0){
					pageStart += 1;
				}
				
				var pageEnd = pageStart + pageBarSize - 1
				var pageNo = pageStart;
				var pageBar = "";
				var totalPage = Math.ceil(total / 5);
				 
				pageBar += '<ul class="pagination justify-content-center">';
				
				if(cPage == 1){
					pageBar += '<li class="page-item" disabled>'
					
				}else{
					pageBar += '<li class="page-item">'
				}
				
				pageBar += '<a class="page-link" href="#" aria-label="Previous">'
				pageBar += '<span aria-hidden="true">&laquo;</span>';
				pageBar += '</a>'
				pageBar += '</li>'			
				
				while(pageNo <= pageEnd && pageNo <= totalPage) {
					if(pageNo == cPage) {
						pageBar += "<li class=\"page-item active\"><a class=\"page-link\" href=\"#\">" + pageNo + "</a></li>\n";
					}
					else {
						pageBar += "<li class=\"page-item\"><a class=\"page-link\" href=\"#\">" + pageNo + "</a></li>\n";
					}
	
					pageNo++;
				}
				
				if(cPage >= totalPage) {
					pageBar += "<li disabled class=\"page-item\">\r\n" + 
							"      <a class=\"page-link\" href=\"#\">&raquo;</a>\r\n" + 
							"    </li>\n";
				}
				else {
					pageBar += "<li class=\"page-item\">\r\n" + 
							"      <a class=\"page-link\" href=\"#\">&raquo;</a>\r\n" + 
							"    </li>\n";
				}
				
				$(".detailCodePageBar").html(pageBar);
				$(".page-item").on("click", clickPageBar);
				$(".detailCodeTr").on("click", clickDetailCode);
			},
			
			error(xhr, status, err){
				console.log(xhr, status, err);
			}
		});
	}); //end of $(".commonCodeTr").click()
	 
	$(".deleteCommonCode").click(function(){

		var commonCode = new Object();
		
		commonCode.code = $("#code").val();
		commonCode.codeName = $("#codeName").val();
		commonCode.useAt = $('input[name="useAt"]:checked').val();
		
		if(confirm("삭제하시겠습니까?")){
			var isNull = nullCheck(commonCode);
			
			if(isNull){
				alert("삭제할 코드를 선택하세요.");
				return;
			}else{
				$.ajax({
					type: "POST",
					url: "/commoncode/code/delete",
					data: {
						"code": commonCode.code,
					},
					dataType: "json",
					success(result) {

						if (result.resultCode == 0) {
							alert("삭제하였습니다.");
							location.href = "/commoncode/list";

						} else {
							alert("삭제를 실패하였습니다.");
							return;
						}
					},

					error(xhr, status, err) {
						console.log(xhr, status, err);
					}
				});
			}
		}
		
	});
	
	//공통 상세 코드 추가 버튼 이벤트
	$(".addDetailCode").click(function(){
		$(".detailCodeAdd").text("저장");
		$("#detailCode").removeAttr("readonly");
		$("#detailCode").css("background","none");
		
		
		detailCodeEmpty();
		
	});
	
	$("#detailCode").change(function(){
		
		url = "/commoncode/detail/code/duplication/check";
		var code = $('#detailCode').val();
		
		codeDuplicationCheck(url, $(".detailCodeHelp"), code)
	});
	
	$(".deleteDetailCode").click(function(){
		if(confirm("삭제하시겠습니까?")){
			
			var detailCode = new Object();

			detailCode.code = $(".commonCode").val();
			detailCode.detailCode = $("#detailCode").val();
			detailCode.detailCodeName = $("#detailCodeName").val();
			detailCode.sortOrdr = $("#sortOrdr").val();
			detailCode.detailCodeUseAt = $('input[name="detailCodeUseAt"]:checked').val();
			
			var isNull = nullCheck(detailCode);
			
			if(isNull){
				
				alert("빈칸은 삭제할 수 없습니다.");
				return;
				
			}else{
				$.ajax({
					type: "get",
					url: "/commoncode/detail/code/delete",
					data: { "detailCode": detailCode.detailCode},

					success(result) {
						pageCode = detailCode.code;
						commonCodeClickNum = 0;
						
						$(".commonCodeTr").click();
						
						if(result == 'ok'){
							alert("삭제했습니다.");
						
						}else{
							alert("삭제에 실패했습니다.")
						}
					},

					error(xhr, status, err) {
						console.log(xhr, status, err);
					}
				});
			}
		}
	});
	

})

function clickPageBar(){
	var number = $(this).text(); 
	var totalPage = Math.ceil(total / 5);
	pageCode = $(".commonCode").val();
	commonCodeClickNum = 0;
	
	if(cPage != totalPage && number.trim() == "»"){
		cPage += 1;
		
	}else if(cPage != 1 && number.trim() == "«"){
		cPage -= 1;
	
	}else if(!isNaN(number)){
		cPage = number
	
	}else{
		return;
	}
	
	$(".commonCodeTr").click();
}

function clickDetailCode(){
	$(".detailCodeAdd").text("수정");
	var detailCode = $($(this).children()[0]).text();
	
	$.ajax({
		type: "get",
		url: "/commoncode/detail/code/detail",
		data: { "detailCode": detailCode,},
		dataType: "json",
		
		success(result) {

			$(".commonCode").val(result.code);
			$("#detailCode").val(result.detailCode);
			$("#detailCodeName").val(result.detailCodeName);
			$("#sortOrdr").val(result.sortOrdr);
			$("#detailCode").attr("readonly", "readonly");
			$("#detailCode").css("background","#c0c0c0");
			
			if(result.useAt == 'Y'){
				$(".detailUseAtY").prop("checked", true);
		
			}else{
				$(".detailUseAtN").prop("checked", true);
			}
	
		},

		error(xhr, status, err) {
			console.log(xhr, status, err);
		}
	});
}

function codeDuplicationCheck(url, help, code){
	var $help = $(help);
	$help.addClass("hide");
	
	$.ajax({
			type:'get',
			url: url,
			data: {'code': code},

			success(val) {
				if (val == "true") {
					$help.removeClass("hide");
				}
			},
			error(xhr, status, err) {
				console.log(xhr, status, err);
			},
	});
}

function commonCodeEmpty(){

	$("#code").val("");
	$("#codeName").val("");
	$("#useAt_Y").prop("checked", true);
	
	$(".commonCode").val("");
}

function detailCodeEmpty(){
	
	$("#detailCode").val("");
	$("#detailCodeName").val("");
	$("#sortOrdr").val("");
	$("#detailCodeUseAtY").prop("checked", true);
}