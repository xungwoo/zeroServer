<#macro textfieldWithRange inputId value=0 min=1 max=100 title="" span="">
<div class="control-group" id="${inputId}Group">
	<label class="control-label" for="${inputId}"><#if title?has_content>${title}<#else>${inputId}</#if></label>
	<div class="controls">
		<input type="text" id="${inputId}" value="${value!}" oninput="textfiled4RangeUpdate('${inputId}')"/>
		<span class="muted">${span!}</span>
		<div>
			<input type="range" id="${inputId}-range" value="${value!}" min="${min!}" max="${max!}" step="1" pattern="\d*\.?\d+" size="3" style="width:205px" oninput="ttgames.form.rangeUpdate('${inputId}')"/>
		</div>
	</div>
</div>
</#macro>
