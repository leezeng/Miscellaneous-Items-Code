# Java-Language-Code
Java语言相关项目代码(包含个人项目、测试代码)

1、01-SpringBoot-Demo项目
    SpringBoot初始项目，用于测试服务、加载自定义logback配置文件、自定义配置参数

2、02-SpringBoot-Demo项目
    使用JPA,用于测试连接、操作MySQL数据库、测试事务回滚状态

    RESTful API设计
    **请求类型	      请求路径	                 功能**
    POST          /girls                    添加、创建女生信息
    PUT           /girls/{id}               通过id更新一个女生
    GET           /girls                    获取女生列表信息
    GET           /girls/{id}               通过id查询一个女生
    DELETE        /girls/{id}               通过id删除一个女生

    特殊业务(破坏了RESTful API的原则,单独列出来)
    GET         /girls/byAge/{age}          通过年龄查询女生信息
    GET         /girls/byAgeAndCupSize      通过年龄和cupSize查询女生信息
    GET         /girls/sortByAge            获取女生信息列表-(年龄排序)
    GET         /girls/byPage               获取女生信息列表-分页
    GET         /girls/byCupSize            通过cupSize查询女生信息-自定义SQL
    
    事务测试
    POST        /addGirlInfoTestRollBack    添加一个女生信息,用来测试事务(测试事务回滚情况)
    POST        /addGirlInfoTestNoRollBack  添加一个女生信息,用来测试事务(测试事务不回滚情况)