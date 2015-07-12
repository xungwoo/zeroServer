<#ftl attributes={"content_type":"application/json; charset=utf-8"}>
<#import "/macros/spring.ftl" as spring />
{
    "bSuccess" : true
<#if _result?? && _result?has_content>
    , "htReturnValue" : 
    ${_result}
</#if>
}
