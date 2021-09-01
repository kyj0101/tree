
$(function(){

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


	$(".btnLogout").click(function(){
		
		var $logoutForm = $("#logoutForm");
		
		$logoutForm.attr("method", "POST");
		$logoutForm.attr("action", "/logout");
		$logoutForm.submit();
	})

});

