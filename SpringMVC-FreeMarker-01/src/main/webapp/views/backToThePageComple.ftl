<#--<#if someThingVO??&&someThingVO.peopleVOs??&&someThingVO.peopleVOs?size &gt; 0>-->
<#if someThingVO??>

${someThingVO.id}
<br>
    <#list someThingVO.peopleVO as people>
    ${people.name}
    ${people.age}
    ${people.address}
    ${people.six}
    <h5> | </h5>
    </#list>

</#if>