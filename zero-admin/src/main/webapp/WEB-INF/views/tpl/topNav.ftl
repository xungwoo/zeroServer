<#list adminMenu.topMenu.getSubItems() as item>
<ul class="nav">
	<#if item.id == adminMenu.topSelection!>
		<#assign itemClass='active'>
	<#else>
		<#assign itemClass=''>
	</#if>
	<li class="${itemClass!}"><a href="${item.url}">${item.label}</a></li>
</ul>
</#list>
