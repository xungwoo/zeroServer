<#macro radioButton inputId item value="" title="">
<div class="control-group" id="${inputId}Group">
	<label class="control-label" for="${inputId}"><#if title?has_content>${title}<#else>${inputId}</#if></label>
	<div class="controls">

		<div id="${inputId}" class="btn-group" data-toggle="buttons-radio">
			<#list item as it>
				<button name="${inputId}" id="${inputId}_${it}" type="button" class="btn" value="${it}">${it}</button>
			</#list>
		</div>

		<#if value?has_content==true>
			<script>
					$('#${inputId}_${value}').button('toggle')
			</script>
		</#if>
	
	</div>
</div>
</#macro>
