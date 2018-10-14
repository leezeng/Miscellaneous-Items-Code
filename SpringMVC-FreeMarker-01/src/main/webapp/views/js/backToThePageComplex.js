/**测试ajax调用后台，接收后台页面，复杂数据*/

function backToThePageComplex() {

    console.log("测试ajax调用后台，接收后台页面，复杂数据");

    $.ajax({

        type: "get",
        dataType: 'json',
        url: "backToThePageComple.do",
        contentType: "application/json",
        success: function (data) {
            console.log(data);

            if (data.returnCode == "4000") {

                $("#loginForm-Complex-div-id").html("" +
                    "<div class='wrapper cart cart-loading'>" +
                    "<div class='g-loading'>" +
                    "<table>" +
                    "<tr>" +
                    "<tb>" +
                    "<p class='loading-fail'></p>" +
                    "</tb>333333您访问的太过频繁，已造成网络拥堵，请稍后再试哦！" +
                    "</tr>" +
                    "</table>" +
                    "</div>" +
                    "</div>");
            } else {
                $("#loginForm-Complex-div-id").html(data.html);
            }
        }

    });

}
