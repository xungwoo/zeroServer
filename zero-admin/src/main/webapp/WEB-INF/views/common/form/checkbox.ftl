<#macro checkbox inputId value=false active=true>
<div class="control-group" id="${inputId}Group">
	<label class="control-label" for="${inputId}">${inputId}</label>
	<div class="controls">
		<#if value == true>
			<#assign checked='checked="checked"'>
		<#else>
			<#assign checked=''>
		</#if>

		<#if active == false>
			<#assign disabled='disabled="disabled"'>
		<#else>
			<#assign disabled=''>
		</#if>
		
		<input type="checkbox" id="${inputId}" ${checked} ${disabled}/>
	</div>
</div>
</#macro>
