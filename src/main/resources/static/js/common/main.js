
$(function(){

    $(".add-board").click(function(){

        $('#addModal').trigger('focus');
        $('#add-modal-label').text("게시판 추가");
        $(".add-chat-btn").css("display","none")
        $(".add-board-btn").css("display","inline-block")
        (".form-check").css("display","block")
    });
  
    $(".add-chat").click(function(){

        $('#addModal').trigger('focus');
        $('#add-modal-label').text("채팅방 추가");
        $(".add-chat-btn").css("display","inline-block")
        $(".add-board-btn").css("display","none")
        $(".form-check").css("display","none")
    });

    $(".add-board-btn").on('click', function(){
        const title = $(".title-input").val(); 
        $(".board-list-ul").append("<li><a href='#'>"+ title + "</a></li>")
    });

    $(".add-chat-btn").on('click', function(){
        const title = $(".title-input").val(); 
        $(".chat-list-ul").append("<li><a href='#'>"+ title + "</a></li>")
    });

    $(".fa-bars").click(function(){
        $("#manager-menu").toggleClass("manager-menu");
        $("#manager-menu").toggleClass("manager-menu-hidden");
    })

    $(".fa-user-circle").click(function(){
        $("#user-profile").toggleClass("user-profile");
        $("#user-profile").toggleClass("user-profile-hidden");
    })

    $(".modal-footer").on("change","input",function(){

        var $this = $(this);
        var file = $this[0].files[0];
        var fileName = file.name;

        $this.prev().text(fileName);
    });


    $(".add-file-input").click(function(){

        var num = $(":file").length;

        // 이유는 모르겠는데 input file 5개일때 num이 10이 됨
        if(num > 10){
            alert("파일 첨부는 최대 5개 입니다.");
            return;

        }else{
            var fileinput = "<div class='input-group input-file'><div class='custom-file'><label class='custom-file-label file1'>파일을 첨부하세요.</label> <input type='file' class='custom-file-input file-input'name='uploadFile'><hr /></div><div class='input-group-append' name='file-del-btn'><button class='btn btn-outline-secondary' type='button' id='inputGroupFileAddon04'>파일삭제</button></div></div>";
            $(".modal-footer").append(fileinput);
        }
    });

	$(".btnLogout").click(function(){
		
		var $logoutForm = $("#logoutForm");
		
		$logoutForm.attr("method", "POST");
		$logoutForm.attr("action", "/logout");
		$logoutForm.submit();
	})

});

