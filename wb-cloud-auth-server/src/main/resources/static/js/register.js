$(document).ready(function(){
    $("#registration-form").submit(function(e){
        e.preventDefault();
       	e.stopPropagation();
        $.ajax({
            type: "POST",
            url: "/register",
            data: $("#registration-form").serialize(),
            error: function(){
                alert("Something went worng")
            },
            success:function(res){
                console.log(res)
                if(res.googleAuth){
                   $("#registration-div").hide()
                   $("#tokenQr").attr("src", "https://zxing.org/w/chart?cht=qr&chs=250x250&chld=M&choe=UTF-8&chl=otpauth://totp/trackobit.com?secret=" + res.secretKey + "&issuer=Trackobit");
                   $("#tokenValue").text(res.secretKey);
                   $("#2fa-modal").modal("show");
                }else{
                   window.location="/";
                }
            }
        })
    })

    $("#2fa-btn").click(function () {
        window.location.replace("/");
    });
})