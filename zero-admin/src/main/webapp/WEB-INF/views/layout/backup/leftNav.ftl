<#if !selection?exists><#assign selection=""></#if>
<div class="well sidebar-nav">
	<ul class="nav nav-list">
		<#list adminMenu.myLeftMenu.getSubItems() as section>
			<li class="nav-header">${section.label}</li>
			<#list section.getSubItems() as item>
				<#if item.id == selection>
					<#assign itemClass='active'>
				<#else>
					<#assign itemClass=''>
				</#if>
				<li class="${itemClass!}"><a href="${item.url}" target=_blank>${item.label}</a></li>
			</#list>
		</#list>
	</ul>
</div>
