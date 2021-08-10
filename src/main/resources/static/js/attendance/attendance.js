$(function(){
	//fnSearch();
});

function fnSearch(){
	
	var startDay = $(".startDay").val();
	var endDay = $(".endDay").val();
	var memberName = $(".memberName").val();
	var latenessAt = $("#latenessAt option:selected").val();
	
	/*	
	if(latenessAt == undefined ){
		latenessAt = 'N';
	}*/
	
	var cPage = 3;
	
	console.log(startDay);
	console.log(endDay);
	console.log(memberName);
	console.log(latenessAt);
	console.log(cPage);
	
	$("#s_startDay").val(startDay);
	$("#s_endDay").val(endDay);
	$("#s_name").val(memberName);
	$("#s_latenessAt").val(latenessAt);
	//$("#cPage").val(cPage);
	
	$("#searchForm").attr("action", "/attendance/list");
	$("#searchForm").submit();
	
	
}