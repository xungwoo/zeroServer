<#macro textfieldWithTypeAhead inputId dataItems dataSource value="" active=true title="" span="">
<div class="control-group" id="${inputId}Group">
	<label class="control-label" for="${inputId}"><#if title?has_content>${title}<#else>${inputId}</#if></label>
	<div class="controls">
		<#if active==false>
			<div class="plain-value">${value}</div>
			<input type="hidden" id="${inputId}" value="${value}" style="width:150px"/>
		<#else>
			<input type="text" id="${inputId}" value="${value}" data-provide="typeahead" data-items="${dataItems}" data-source='${dataSource}' style="width:150px"/>
		</#if>
		
		<span class="muted">${span!}</span>
	</div>
</div>

</#macro>
