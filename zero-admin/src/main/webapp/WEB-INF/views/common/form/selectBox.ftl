<#macro selectBox inputId title="" options="" value="">
	<div class="btn-group" id="${inputId}Group">
		<button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
	    	<#if title?has_content>${title}<#else>${inputId}</#if> <span class="caret"></span>
	  	</button>
		<ul class="dropdown-menu" role="menu">
		<#list options as option>
			<li id="${inputId}_${option}" value="${option.unitTypeCode}">${option.unitTypeName}</li>
		</#list>
		</ul>
	</div>	
	<#if value?has_content==true>
		<script>
			$('#${inputId}_${value}').dropdown('toggle')
		</script>
	</#if>	
</#macro>
