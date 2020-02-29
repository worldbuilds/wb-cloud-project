$(document).ready(function(){
    $("#login-form").submit(function(e){
        e.preventDefault();
       	e.stopPropagation();
        $.ajax({
            type: "POST",
            url: "/login",
            data: $("#login-form").serialize(),
            error: function(){
                alert("Something went worng")
            },
            success:function(res){
               localStorage.setItem("token",res.access_token)
               $("#formContent").hide()
               $("#2fa-verify-modal").modal("show")
            }
        })
    })

    $("#2fa-verify-btn").on('click',function(e){
            var authKey=$("#totp-id").val();
            if(authKey==undefined||authKey==null||authKey==""){
                alert("Please enter valid TOTP")
                return;
            }
            $.ajax({
                type: "GET",
                url: "/rest/authenticate/"+authKey,
                  headers:{
                    "Authorization": "Bearer "+localStorage.getItem("token")
                },
                error: function(){
                    alert("Something went worng")
                },
                success:function(res){
                   if(res=="MATCHED"){
                       getDashBoard();
                   }else{
                       $("#totp-id").val("")
                       alert("Please enter valid TOTP");
                   }
                }
            })
    })

     function getDashBoard(){
            $.ajax({
                type: "GET",
                url: "/rest/home",
                headers:{
                  "Authorization": "Bearer "+localStorage.getItem("token")
                },
                error: function(){
                    alert("Something went worng")
                },
                success:function(res){
                  var parseData=$.parseHTML(res);
                  var body = res.substring(res.indexOf("<body>")+6,res.indexOf("</body>"));
                  $("body").html(body);
                }
            })
     }
})