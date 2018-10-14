/**
 * 测试数据提交-多个字段封装对象提交
 * Created by CYX on 2018/5/24.
 */

function testFormSubmission() {

    console.log("测试数据提交-多个字段封装对象提交");

    var id = "9527";
    var name = "CYX";
    var age = "25";
    var address = "nanjing";

    var inputData = {id: id, name: name, age: age, address: address};

    $.ajax({
        type: "post",
        dataType: 'json',
        url: "testFormSubmission.do",
        contentType: "application/json",
        data: JSON.stringify(inputData),
        success: function (data) {
            console.log(data);
        }
    });
}

