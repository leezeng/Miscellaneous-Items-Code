function getInformation() {

    console.log("测试ajax,接收后台数据-替换HTML标签");

    $.ajax({
        type: "get",
        dataType: "json",
        url: "getInformation",
        crossDomain: false,
        data: {"userName": "cyx"},
        success: function (data) {

            console.log(data);
            console.log("data.returnCode : "+data.returnCode);

            if (data.returnCode == "4000") {
                $("#cart-bg").html("" +
                    "<div class='wrapper cart cart-loading'>" +
                        "<div class='g-loading'>" +
                            "<table>" +
                                "<tr>" +
                                    "<tb>" +
                                        "<p class='loading-fail'></p>" +
                                    "</tb>测试ajax,接收后台数据-替换HTML标签--您访问的太过频繁，已造成网络拥堵，请稍后再试哦！" +
                                "</tr>" +
                            "</table>" +
                        "</div>" +
                    "</div>");
            }
        }

    });

}
