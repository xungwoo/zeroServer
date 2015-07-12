<#if pageInfo.getPrevEnable() >
	<#assign prevClass=''>
<#else>
	<#assign prevClass='disabled'>
</#if>

<#if pageInfo.getNextEnable() >
	<#assign nextClass=''>
<#else>
	<#assign nextClass='disabled'>
</#if>

<div class="pagination pagination-centered">
	<ul>
		<li class="${prevClass}"><a href="?page=${pageInfo.prevPage}">&laquo;</a></li>
		
		<#list pageInfo.getPages() as page>
			<#if pageInfo.getPage() == page>
				<#assign itemClass='active'>
			<#else>
				<#assign itemClass=''>
			</#if>
			
			<li class="${itemClass}"><a href="?page=${page}">${page}</a></li>
		</#list>
		<li class="${nextClass}"><a href="?page=${pageInfo.nextPage}">&raquo;</a></li>
	</ul>
</div>
