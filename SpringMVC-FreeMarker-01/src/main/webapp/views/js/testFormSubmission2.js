/**测试表单提交-多个字段提交*/

function testFormSubmission2() {

    console.log("测试表单提交-多个字段提交");

    var name = "cyx";
    var age = "22";
    var address = "nanjing";

    $.ajax({
        type: "post",
        dataType: 'json',
        url: "testFormSubmission2.do",
        contentType: "application/json",
        async: false,
        data: {
            "userName": name,
            "userAge": age,
            "userAddress": address
        },
        success: function (data) {
            console.log(data);
        }
    });


}