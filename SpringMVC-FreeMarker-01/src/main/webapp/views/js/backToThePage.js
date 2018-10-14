/**测试ajax调用后台，接收后台页面*/

function backToThePage() {

    console.log("测试ajax调用后台，接收后台页面");

    $.ajax({

        type: "get",
        dataType: 'json',
        url: "backToThePage.do",
        contentType: "application/json",
        success: function (data) {
            console.log(data);

            if (data.returnCode == "4000") {

                $("#loginForm-div-id").html("" +
                    "<div class='wrapper cart cart-loading'>" +
                    "<div class='g-loading'>" +
                    "<table>" +
                    "<tr>" +
                    "<tb>" +
                    "<p class='loading-fail'></p>" +
                    "</tb>2222222您访问的太过频繁，已造成网络拥堵，请稍后再试哦！" +
                    "</tr>" +
                    "</table>" +
                    "</div>" +
                    "</div>");
            }else{
                $("#loginForm-div-id").html(data.html);
            }


        }

    });

}
