$(function(){
	
	$(".close").on("click", close);
	
	//근태 등록할때 이름 자동완성
	$("#searchName").autocomplete({
		
		
		source: function(request, response) {
		
			$.ajax({
				url: "/attendance/auto/name",
				data: {
					searchName: request.term
				},
				success(data) {

					response(
						$.map(data, function(item) {

							return {
								label: item.name + "," + item.email + ","+ item.departmentName +   "," + item.positionName,
								value: item.name
							}
						})
					);
				},

				error(xhr, status, err) {
					console.log(xhr, status, err);
				}
			});//end of ajax   	  
		},//end of source

		focus: function(e, focus) {
			return false;
		},

		select: function(e, select) {
			var label = select.item.label;
			var index = label.split(",");
			label = index[1];
			
			$("#insertEmail").val(label);
		}

	});//end of $("#searchName").autocomplete
	
	$(".memberName").keydown(function(key) {
		if (key.keyCode == 13) {// 엔터
			fnSearch();
		}
	});

});

//검색 버튼 함수
function fnSearch(){
	
	var startDay = $(".startDay").val();
	var endDay = $(".endDay").val();
	var memberName = $(".memberName").val();
	var latenessAt = $("#latenessAt option:selected").val();
	
	$("#s_startDay").val(startDay);
	$("#s_endDay").val(endDay);
	$("#s_name").val(memberName);
	$("#s_latenessAt").val(latenessAt);

	$("#searchForm").attr("action", "/attendance/list");
	$("#searchForm").submit();	
}

//테이블에서 컬럼 클릭하면 나오는 팝업 
function fnPopup(e){
	
	var attendanceNo = $($(e).children()[0]).text();
	var email = $($(e).children()[1]).text();
	var name = $($(e).children()[2]).text();
	var day = $($(e).children()[3]).text();
	var inTime = $($(e).children()[4]).text();
	var outTime = $($(e).children()[5]).text();
	var latenessAt = $($(e).children()[6]).text();
	var reason = $($(e).children()[7]).text();
	
	$(".updateAttendanceNo").val(attendanceNo);
	$(".updateEmail").val(email);
	$(".updateDay").val(day);
	$(".updateName").val(name);
	$(".updateInTime").val(inTime);
	$(".updateOutTime").val(outTime);
	
	if(latenessAt == '예'){
		$("#updateLatenessAtY").attr("selected",true);	
	}else{
		$("#updateLatenessAtN").attr("selected",true);
	}
	
	$(".updateLatenessAtReason").val(reason);
}

//수정팝업에 저장버튼 누를때 이벤트
function fnUpdate(){
	
	var attendance = new Object();
	
	attendance.attendanceNo = $(".updateAttendanceNo").val();
	attendance.email = $(".updateEmail").val();
	attendance.day = $(".updateDay").val();
	attendance.name = $(".updateName").val();
	attendance.inTime = $(".updateInTime").val();
	attendance.outTime = $(".updateOutTime").val();
	attendance.latenessAt = $("#updateLatenessAtSelected option:selected").val();
	
	var isNull = nullCheck(attendance);
	
	if(isNull){
	
		alert("빈 칸이 있습니다.");
		return;
				
	}else{
		attendance.latenessReason = $(".updateLatenessAtReason").val() == null ? "" : $(".updateLatenessAtReason").val();
	
		$.ajax({
	
			type: "post",
			url: "/attendance/update",
			data:{
				"attendanceNo":attendance.attendanceNo,
				"email":attendance.email,
				"day":attendance.day,
				"name":attendance.name,
				"inTime":attendance.inTime,
				"outTime":attendance.outTime,
				"latenessAt":attendance.latenessAt,
				"latenessReason":attendance.latenessReason
			},
			success(result) {
				
				if(result == "ok"){
					
					$('#update-modal').modal('hide')
					alert("수정되었습니다.");
					location.replace(window.location.href);
				
				}else{
					alert("수정을 실패했습니다.");
				}
			},
		});
	}
}

//수정할때 지각여부 변경시 지각 사유 지우고 readonly
function updateLatenessAtSelectedChange(){
	
	var latenessAt = $("#updateLatenessAtSelected option:selected").val();
	
	if(latenessAt == 'N'){
		$(".updateLatenessAtReason").val("");
		$(".updateLatenessAtReason").attr("readonly", true);
	
	}else{
		$(".updateLatenessAtReason").removeAttr("readonly");
	}
}

//등록할때 지각여부 변경시 지각 사유 지우고 readonly
function insertLatenessAtSelectedChange(){

	var latenessAt = $("#insertLatenessAt option:selected").val();
	
	if(latenessAt == 'N'){
		$("#insertLatenessAtReason").val("");
		$("#insertLatenessAtReason").attr("readonly", true);
	
	}else{
		$("#insertLatenessAtReason").removeAttr("readonly");
	}
}


//근태 기록 저장
function insertAttendance(){
	
	var attendance = new Object();
	
	attendance.email = $("#insertEmail").val();
	attendance.day = $("#insertDay").val();
	attendance.name = $("#searchName").val();
	attendance.inTime = $("#insertInTime").val();
	attendance.outTime = $("#insertOutTime").val();
	attendance.latenessAt = $("#insertLatenessAt option:selected").val();
	
	var isNull = nullCheck(attendance);
	
	if(isNull){
		
		if(attendance.email.length == 0){
			alert("존재하지 않는 회원입니다.");
	
		}else{
			alert("입력되지 않은 칸이 있습니다.");
		}
		
		return;
	
	}else{
		var latenessReason = $("#insertLatenessAtReason").val() == null ? "" : $("#insertLatenessAtReason").val();

		$.ajax({

			type: "post",
			url: "/attendance/insert",
			data: {
				"email": attendance.email,
				"day": attendance.day,
				"name": attendance.name,
				"inTime": attendance.inTime,
				"outTime": attendance.outTime,
				"latenessAt": attendance.latenessAt,
				"latenessReason": latenessReason
			},
			success(result) {

				if (result == "ok") {

					alert("등록되었습니다.");
					//location.replace(window.location.href);
					$('#upload-modal').modal('hide')
					fnSearch();

				} else {
					alert("등록을 실패했습니다.");
				}
			},
		});
	}
}

function deleteAttendance(){
	
	var attendanceNo = $(".updateAttendanceNo").val();
	
	$.ajax({

		type: "post",
		url: "/attendance/delete",
		data: {"attendanceNo": attendanceNo,},
		success(result) {

			if (result == "ok") {

				$('#update-modal').modal('hide')
				alert("삭제되었습니다.");
				location.replace(window.location.href);

			} else {
				alert("삭제를 실패했습니다.");
			}
		},
	});
}

//팝업창 닫을때 이벤트
function close(){
	$("input").val("");
}












