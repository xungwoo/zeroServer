<#macro textField inputId value="" title="" span="" placeholder="" active=true>
<div class="form-group">
	<label class="col-sm-3 control-label no-padding-right" for="${inputId}">${title}</label>

	<div class="col-sm-9">
		<input type="text" id="${inputId}" name="${inputId}" placeholder="${placeholder}" class="col-xs-5 col-sm-2" value="${value}" <#if active==false>readonly</#if>/>
		<#if span?has_content==true>
		<span class="help-inline col-xs-12 col-sm-7">
			<span class="middle">${span}</span><span class="error-jquery" style="color:red;"></span>
		</span>
		</#if>
	</div>
</div>
</#macro>