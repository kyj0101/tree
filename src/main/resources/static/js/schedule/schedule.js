var calendar = null;

document.addEventListener('DOMContentLoaded', function() {
	
	var calendarEl = document.getElementById('calendar');
	
	calendar = new FullCalendar.Calendar(calendarEl, {
		initialView: 'dayGridMonth',
		locale: "ko",
		dateClick(info) {
			var dateStr = info.dateStr;
			
			$(".addStartDate").val(dateStr);
			$("#addDate").modal("show");
		},
		eventClick(info){
			
			$("#updateDate").modal("show");
			
			$("#scheduleId").val(info.event.id);
			$(".updateTitle").val(info.event.title);
			$(".updateStartDate").val(info.event.startStr.substring(0,10));
			$(".updateStartTime").val(info.event.startStr.substring(11,19));
			$(".updateEndDate").val(info.event.endStr.substring(0,10));
			$(".updateEndTime").val(info.event.endStr.substring(11,19));
			$("#updateColor").val(info.event.backgroundColor);

			if(info.event.allDay){

				$("#updateAllDay").prop("checked", true);
				changeAllday($("#updateAllDay"));
			} 
		}
	});
	
	calendar.render();
	getScheduleList();
	
});

function changeAllday(checked, e){
	
	checked = checked == null ? e : checked;
	
	var isChecked = $(checked).prop("checked");
	
	if(isChecked){
		$(".time").attr("readOnly", true);
	
	}else{
		$(".time").attr("readOnly", false);
	}
}

function addSchedule(){
	
	var schedule = {
		"title":$(".addTitle").val(),
		"color":$("#color").val(),
		"projectId":$("#projectId").val()
	}
	
	if(!$("#addAllDay").prop("checked")){
		
		schedule.start = $(".addStartDate").val() + ' '  + $(".addStartTime").val();
		schedule.end = $(".addEndDate").val() + ' '  + $(".addEndTime").val();
		
		schedule.startTime = $(".addStartTime").val();
		schedule.endTime = $(".addEndTime").val();
		
	}else{
		schedule.start = $(".addStartDate").val();
		schedule.end = $(".addEndDate").val();
		schedule.allDay = 'Y';
	}
	
	var isNull = nullCheck(schedule);
	
	if(isNull){
		alert("빈 칸이 있습니다.");
		return;
	
	}else{
		
		$.ajax({
			type: "POST",
			url: "/schedule/insert",
			data: {
					"projectId":schedule.projectId,
					"title":schedule.title,
					"start":schedule.start,
					"end":schedule.end,
					"allDay":schedule.allDay,
					"color":schedule.color
				},

			success(result) {
				var msg = "추가를 실패했습니다.";
				
				if(result === "ok"){
					
					msg = "추가되었습니다.";
					
					calendar.removeAllEvents();
					getScheduleList();
				}
				
				$("#addDate").modal('hide');
				resetInput();
				alert(msg);
			}
		});
	}
}

function getScheduleList(){
	
	$.ajax({
		type: "get",
		url: "/schedule/get/schedulelist",
		data: {
			"projectId":$("#projectId").val()
		},

		success(result) {
			$.each(result, function(index, elem){
				calendar.addEvent(elem);
			});
		}
	});
}

function resetInput(){
	
	$(".calendarInputDiv > input").val("").valueAsDate = '';
	
	$(".allDay").prop("checked", false);
	changeAllday($("#allDay"));
	
	$(".form-control-color").val("#007bff");
}

function updateShedule(){
	
	var schedule = {
		"title":$(".updateTitle").val(),
		"color":$("#updateColor").val(),
		"id":$("#scheduleId").val()
	}
	
	if(!$("#updateAllDay").prop("checked")){
		
		schedule.start = $(".updateStartDate").val() + ' '  + $(".updateStartTime").val();
		schedule.end = $(".updateEndDate").val() + ' '  + $(".updateEndTime").val();

		schedule.startTime = $(".updateStartTime").val();
		schedule.endTime = $(".updateEndTime").val();
		
	}else{
		schedule.start = $(".updateStartDate").val();
		schedule.end = $(".updateEndDate").val();
		schedule.allDay = 'Y';
	}

	var isNull = nullCheck(schedule);
	
	if(isNull){
		
		alert("빈 칸이 있습니다.");
		return;
	
	}else{
		
		$.ajax({
			type: "POST",
			url: "/schedule/update",
			data: {
					"id":schedule.id,
					"title":schedule.title,
					"start":schedule.start,
					"end":schedule.end,
					"allDay":schedule.allDay,
					"color":schedule.color
				},

			success(result) {
				var msg = "수정을 실패했습니다.";
				
				if(result === "ok"){
					
					msg = "수정되었습니다."
					calendar.removeAllEvents();
					getScheduleList();
				}
				
				$("#updateDate").modal('hide');
				alert(msg);
			}
		});
	}
}

function deleteSchedule(){
	
	var id = $("#scheduleId").val();
	
	$.ajax({
		type: "POST",
		url: "/schedule/delete",
		data: {
			"id":id
		},

		success(result) {
			var msg = "삭제를 실패했습니다.";

			if (result === "ok") {

				msg = "삭제되었습니다."
				
				calendar.removeAllEvents();
				getScheduleList();
			}

			$("#updateDate").modal('hide');
			alert(msg);
		}
	});
	
}
