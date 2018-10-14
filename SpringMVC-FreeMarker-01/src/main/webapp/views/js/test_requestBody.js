/**
 * Created by CYX on 2018/6/14.
 */

function test_requestBody_1() {
    console.log("test_requestBody_1");
    var inputData = {name: "CYX1", age: "20", address: "南京1", six: "男1"};
    $.ajax({
        type: "POST",
        dataType: 'json',
        url: "test_requestBody_1.do",
        contentType: "application/json; charset=UTF-8",
        data: JSON.stringify(inputData),
        success: function (data) {
            console.log(data);
        }
    });
}

function test_requestBody_2() {
    console.log("test_requestBody_2");
    var inputData = {name: "CYX2", age: "22", address: "nanjing2", six: "man2"};
    $.ajax({
        type: "GET",
        dataType: 'json',
        url: "test_requestBody_2.do",
        contentType: "application/x-www-form-urlencoded; charset=UTF-8",
        data: inputData,
        success: function (data) {
            console.log(data);
        }
    });

}

function test_requestBody_3() {
    console.log("test_requestBody_2");
    $.ajax({
        type: "GET",
        dataType: 'json',
        url: "test_requestBody_3.do",
        data: {
            "userName": "CYX-3",
            "userAge": "23",
            "userAddress": "nanjing-3"
        },
        success: function (data) {
            console.log(data);
        }
    });
}

function test_requestBody_4() {

    console.log("test_requestBody_4");
    var data = {name: "CYX4", age: "44", address: "nanjing4", six: "man4"};
    $.ajax({
        type: "POST",
        dataType: 'json',
        url: "test_requestBody_4.do",
        data: {"data": JSON.stringify(data)},
        success: function (data) {
            console.log(data);
        }
    });

}
