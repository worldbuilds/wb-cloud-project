$(document).ready(function(){
     console.log("Dashboard called...........")
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
                  $("html").html(res);
                }
            })
     }
})