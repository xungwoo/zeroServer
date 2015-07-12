<div class="sidebar" id="sidebar">
<ul class="nav nav-list">
	<#list adminMenu.leftMenu.getSubItems() as section>
		<#if section.id == adminMenu.getCategory()>
			<#assign categoryClass='active open'>
		<#else>
			<#assign categoryClass=''>
		</#if>
		<li class="${categoryClass!}">
			<a href="#" class="dropdown-toggle">
				<#switch section.id>
				  <#case "Version">
				  	<i class="normal-icon icon-rss pink bigger-130"></i>
				    <#break>
				  <#case "Event">
				  	<i class="normal-icon icon-bullhorn blue bigger-130"></i>
				    <#break>
				  <#case "Shop">
				  	<i class="normal-icon icon-shopping-cart black bigger-130"></i>
				    <#break>
				  <#case "User">
				  	<i class="normal-icon icon-user red bigger-130"></i>
				    <#break>
				  <#case "Log">
				  	<i class="normal-icon icon-signal orange bigger-130"></i>
				  	<#break>
				  <#case "Ladder">
				  	<i class="normal-icon icon-flag blue bigger-130"></i>
				    <#break>
				  <#case "BalanceEquip">
				  	<i class="normal-icon icon-dashboard green bigger-130"></i>
				    <#break>
				  <#case "BalanceEtc">
				  	<i class="normal-icon icon-edit black bigger-130"></i>
				    <#break>
				  <#case "Boss">
				  	<i class="normal-icon icon-group red bigger-130"></i>
				    <#break>
				  <#case "Castle">
				  	<i class="normal-icon icon-bookmark blue bigger-130"></i>
				    <#break>
				  <#case "Stats">
				  	<i class="normal-icon icon-bar-chart gray bigger-130"></i>
				    <#break>
				  <#case "Post">
				  	<i class="normal-icon icon-tag bule bigger-130"></i>
				    <#break>
				  <#case "QATool">
				  	<i class="normal-icon icon-desktop black bigger-130"></i>
				    <#break>
				  <#default>
				    <i class="normal-icon icon-text blue bigger-130"></i>
				</#switch>
				<span class="menu-text"> ${section.label} </span>
				<b class="arrow icon-angle-down"></b>
			</a>
			<ul class="submenu">
			<#list section.getSubItems() as item>
				<#if item.id == adminMenu.getSelectMenu()>
					<#assign subMenuClass='active'>
				<#else>
					<#assign subMenuClass=''>				
				</#if>
				<li class="${subMenuClass!}">
					<a href="${item.url}" ${item.target}>
						<i class="icon-double-angle-right"></i>
						${item.label}<br>(${item.id})
					</a>
				</li>							
			</#list>
			</ul>
		</li>
	</#list>

</ul><!-- /.nav-list -->

<div class="sidebar-collapse" id="sidebar-collapse">
	<i class="icon-double-angle-left" data-icon1="icon-double-angle-left" data-icon2="icon-double-angle-right"></i>
</div>

<script type="text/javascript">
	try{ace.settings.check('sidebar' , 'collapsed')}catch(e){}
</script>
</div>