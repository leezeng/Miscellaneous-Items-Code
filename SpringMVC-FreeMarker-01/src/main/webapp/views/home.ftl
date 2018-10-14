<html>
<head>
    <title>Insert title here</title>
</head>

<body>
<#--引入jquery文件-->
<script src="/views/js/jquery/jquery-3.3.1.min.js"></script>
<script src="/views/js/getInformation.js"></script>
<script src="/views/js/testFormSubmission.js"></script>
<script src="/views/js/testFormSubmission2.js"></script>
<script src="/views/js/backToThePage.js"></script>
<script src="/views/js/backToThePageComplex.js"></script>
<script src="/views/js/test_requestBody.js"></script>


${information}

<#--测试ajax,替换标签-->
<div class="cart-bg" id="cart-bg">
    <div class="wrapper cart cart-wrapper" id="cart-wrapper"></div>
</div>

<#include "loginForm.ftl" >
<#include "fileUpdate.ftl">

<script type="application/javascript">

    /**测试ajax*/
    getInformation();

    /**测试数据提交-多个字段封装对象提交*/
    testFormSubmission();

    /**测试表单提交-多个字段提交*/
    testFormSubmission2();

    /**测试ajax数据提交*/
    test_requestBody_1();
    test_requestBody_2();
    test_requestBody_3();
    test_requestBody_4();

</script>

</body>
</html>