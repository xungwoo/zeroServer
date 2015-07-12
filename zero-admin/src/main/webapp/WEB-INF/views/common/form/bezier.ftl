<#macro bezier name title value>
	<#if value??>
	
<div class="panel panel-info">
  <div class="panel-heading">${title}</div>
  <div class="panel-body">
		<@textfieldWithRange inputId="${name}Lv1" title="${title} Lv1" value="${value.lv1!}" min=1 max=2000/>
		<@textfieldWithRange inputId="${name}LvMax" title="${title} LvMax" value="${value.lvMax!}" min=1 max=25000/>
		<@textfieldWithCubicBezier inputId="${name}Bezier" title="${title} 성장" value="${value.bezier!}"/>
		<@textfield inputId="${name}LimitExceedRate" title="${title} 한계돌파" value="${value.limitExceedRate!}" span="0~1.0"/>
  </div>
</div>	
	
	</#if>
</#macro>		
		