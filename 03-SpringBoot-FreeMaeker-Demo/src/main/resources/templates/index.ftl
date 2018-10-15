<html>

<head>
    <title>SpringBoot - FreeMarker</title>

    <link href="/static/css/index.css">
    <script src="/static/js/jquery-3.3.1.min.js"></script>
    <script src="/static/js/index.js"></script>
</head>

<body>
<h2>首页</h2>
<#include "test/test.ftl">
<#if userList??>
    <#list userList as user>
        ${user}<br/>
    </#list>
</#if>
</body>
</html>