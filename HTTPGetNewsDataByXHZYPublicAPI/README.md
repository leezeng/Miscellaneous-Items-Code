# HTTPGetNewsDataByXHZYPublicAPI
通过HTTP调用开放平台接口获取数据

项目需求：
随着时间的流逝，各种技术层出不穷，比如Hadoop、Hive、HBase、Kafka、Spark、Storm等等....

这些技术在自己学习的时候，都需要一个东西：数据！
(除开生产环境)我们自己在家里学习的时候，往往都是制造假数据，更不用说数据质量的问题了。


so，这个项目是用来获取测试数据的。

在网上查找的时候，找到了一个开放平台，提供新闻接口。
开放平台-新华智云：https://fenfa.shuwen.com/?spm=fenfa.0.0.1.V1N3we
具体调用方式见官方文档、以及代码。

本着学习的态度，作为调用者，最好不要高并发的调用该接口，省的避免不必要的麻烦(细水长流嘛)。

V1.0 :
    发现接口返回中没有新闻的详细内容，然后通过url字段中的地址，访问到了新闻详情页面。
    于是，又写了一个爬取页面的线程，从数据库中content字段为空的新闻简介，缓存中队列中。
    然后通过url，爬取页面中的新闻。

V2.0 :
    新增爬取页面线程，通过一段时间的测试，发现接口调用速度很快，但是爬取页面的速度很慢。
    为了加快速递，决定还是按照之前的方式，将获取新闻简介和新闻详情分开，提高效率、速度。