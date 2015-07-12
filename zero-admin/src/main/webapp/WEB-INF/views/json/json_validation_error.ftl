<#ftl attributes={"content_type":"application/json; charset=utf-8"}>
<#import "../macros/spring.ftl" as spring />
{
    "bSuccess" : false,
    "sErrorCode" : "INVALID_FORM_DATA",
    "htReturnValue" : {
    <#if _result??>
        "invalidItemList" : [
            <#list _result.fieldErrors as error>
            <#--@spring.bind "${error.objectName}.${error.field}" /-->
            {
                "name" : "${error.field}",
                "type" : "${error.code}",
                "message" : "<@spring.errorMessage error />"
            }<#if error_has_next>,</#if>
            </#list><#if _result.fieldErrors?has_content && _result.globalErrors?has_content>,</#if>
			<#list _result.globalErrors as error>
			{
				<#-- globalError를 사용하므로 name 속성을 제거  
				     spring.bind를 사용하지 않기때문에 반드시 reject메소드를 통해서 에러코드를 명시해 주어야함-->
				"type" : "${error.code}",
				"message" : "<#if error.arguments??><@spring.messageArgsText "${error.code}", error.arguments, "${(error.defaultMessage!)?js_string}"/><#else><@spring.messageText "${error.code}", "${(error.defaultMessage!)?js_string}"/></#if>"
			}<#if error_has_next>,</#if>
			</#list>
        ]
    </#if>
    }
}