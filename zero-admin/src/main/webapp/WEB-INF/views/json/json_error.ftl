<#ftl attributes={"content_type":"application/json; charset=utf-8"}>
{
    "bSuccess" : false
    <#if _errorCode?? > ,"sErrorCode" : "${_errorCode?js_string}"</#if>
    <#if _errorMessage?? > ,"sErrorMessage" : "${_errorMessage?js_string}"</#if>
	<#if _result?? && _result?has_content>
    , "htReturnValue" : 
    ${_result}
	</#if>
}
