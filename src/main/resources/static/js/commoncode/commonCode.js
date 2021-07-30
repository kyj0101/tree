var cPage = 1; //페이지바 현재 페이지
var pageCode = ""; //페이지바 이동시 코드 저장할 변수
var total = 0;
var commonCodeClickNum = 0; //연속으로 공통코드 테이블 누르면 pageCode 공백대입

$(function() {
	
	$(".add-btn").click(function() {
		$(".firstCodeAddBtn").text("저장");
		$("#code").removeAttr("readonly");
		$("#code").css("background","none");
		$("input").val("")
	});
	
	$("#code").change(function(){
		
		$(".help").addClass("hide");
		var code = $('#code').val();
		
		$.ajax({
			type: 'get',
			url: '/commoncode/code/duplication/check',
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
		
		var $commonCodeForm = $(".commonCodeForm");
		var firstCode = new Object();
		
		firstCode.code = $("#code").val();
		firstCode.codeName = $("#codeName").val();
		firstCode.useAt = $("input[name='useAt']:checked").val();

		var isNull = nullCheck(firstCode);
		var isHelpDisplay = helpDisplayCheck();
		var isUpdate = $(".firstCodeAddBtn").text() == "수정" ? true : false;
		
		if(confirm($(".firstCodeAddBtn").text() + "하시겠습니까?")){
			
			if(isNull){
				alert("입력되지 않은 칸이 있습니다.");
				return;
				
			}else if(isUpdate){
				
				$commonCodeForm.attr("method", "POST");
				$commonCodeForm.attr("action", "/commoncode/code/update");
				$commonCodeForm.submit();
			
			}else if(isHelpDisplay){
				alert("중복된 값을 저장할 수 없습니다.");
				return;
			
			}else{
			
				$commonCodeForm.attr("method", "POST");
				$commonCodeForm.attr("action", "/commoncode/code/insert");
				$commonCodeForm.submit();
			}
		}
		

	});
	
	$(".detailCodeAdd").click(function(){
		
		var detailCodeObj = new Object();
		var $detailCodeAddForm = $(".detailCodeAddForm");
		
		detailCodeObj.detailCode = $("#detailCode").val();
		detailCodeObj.detailCodeName = $("#detailCodeName").val();
		detailCodeObj.detailCodeUseAt = $("#detailCodeUseAt").val();
		
		var isNull = nullCheck(detailCodeObj);
		
		if(isNull){
			alert("입력되지 않은 칸이 있습니다.");
			return;
			
		}else{
			$detailCodeAddForm.attr("method", "POST");
			$detailCodeAddForm.attr("action", "/commoncode/detail/code/insert");
			$detailCodeAddForm.submit();
		}
	});
	
	
	$(".commonCodeTr").click(function(){
		commonCodeClickNum += 1;
		
		if(commonCodeClickNum >= 6){
			cPage = 1;
			pageCode = "";
		}
		
		$(".help").addClass("hide");
		$(".firstCodeAddBtn").text("수정");
		
		var $code = $($(this).children()[0]);
		var $codeName = $($(this).children()[1]);
		var $useAt = $($(this).children()[2]);
		
		code = $code.text();
		
		if(pageCode != ""){
			code = pageCode;
		}

		var codeName = $codeName.text();
		var useAt = $useAt.text();
		
		$("#code").val(code);
		$("#code").attr("readonly", "readonly");
		$("#code").css("background","#c0c0c0");
		$("#codeName").val(codeName);
		$(".commonCode").val(code);
		
		if(useAt == 'Y'){
			$("#useAtY").prop("checked", true);
		
		}else{
			$("#useAtN").prop("checked", true);
		}
	
		$.ajax({
			type:"get",
			url:"/commoncode/detail/code/list",
			data:{"code":code, "cPage":cPage},
			dataType:"json",
			success(result){

				$(".detailCodeTbody").empty();
				$(".detailCodePageBar").empty();

				$.each(result, function(i, item){
					
					if(i == result.length -1 ){
						total = item.total;

					}else{
											
						var html = "";
						
						html += "<tr class='detailCodeTr'>";
						html += '<td>' + item.detailCode + '</td>';
						html += '<td>' + item.detailCodeName + '</td>';
						html += '<td>' + item.useAt + '</td>';
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
				
				if(cPage > totalPage) {
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
	});
	
	$(".deleteCommonCode").click(function(){
		var $commonCodeForm = $(".commonCodeForm");

		$commonCodeForm.attr("method", "POST");
		$commonCodeForm.attr("action", "/commoncode/code/delete");
		$commonCodeForm.submit();
		
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
	var detailCode = $($(this).children()[0]).text();
	
	$.ajax({
		type: "get",
		url: "/commoncode/detail/code/detail",
		data: { "detailCode": detailCode,},
		dataType: "json",
		
		success(result) {
			console.log(result);
			$(".commonCode").val(result.code);
			$("#detailCode").val(result.detailCode);
			$("#detailCodeName").val(result.detailCodeName);
			$("#sortOrdr").val(result.sortOrdr);
			
			if(result.useAt == 'Y'){
				$("#detailCodeUseAtY").prop("checked", true);
		
			}else{
				$("#detailCodeUseAtN").prop("checked", true);
			}
	
		},

		error(xhr, status, err) {
			console.log(xhr, status, err);
		}
	});
}
