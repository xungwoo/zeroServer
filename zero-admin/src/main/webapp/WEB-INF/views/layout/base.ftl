<#setting number_format="#">
<#setting boolean_format="yes,no">
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="utf-8" />
	<title><#if pageInfo??>${pageInfo.getTitle()}</#if></title>
	<meta name="description" content="overview &amp; stats" />
	<meta name="viewport" content="width=device-width, initial-scale=1.0" />
	<@layout.block name="header-css-1"><#include "/layout/header-css-1.ftl" /></@layout.block>
	<!-- page specific plugin styles -->
	<@layout.block name="page-css"></@layout.block>
	<@layout.block name="header-css-2"><#include "/layout/header-css-2.ftl" /></@layout.block>
</head>

<body>
	<@layout.block name="top"><#include "/layout/top.ftl" /></@layout.block>	
		<div class="main-container" id="main-container">
			<script type="text/javascript">
				try{ace.settings.check('main-container' , 'fixed')}catch(e){}
			</script>
			
			<div class="main-container-inner">
				<@layout.block name="sidebar"><#include "/layout/sidebar.ftl" /></@layout.block>	
				
				<div class="main-content">
					<div class="page-content">
						<!-- page-content -->
						<@layout.block name="content"></@layout.block>
						<!-- /.page-content -->
					</div>
				</div><!-- /.main-content -->
			</div><!-- /.main-container-inner -->

			<a href="#" id="btn-scroll-up" class="btn-scroll-up btn btn-sm btn-inverse">
				<i class="icon-double-angle-up icon-only bigger-110"></i>
			</a>
		</div><!-- /.main-container -->

		<!-- basic scripts -->
		<@layout.block name="footer-javascript-1"><#include "/layout/footer-javascript-1.ftl" /></@layout.block>
		<!-- page specific plugin scripts -->
		<@layout.block name="page-javascript-plugin"></@layout.block>
		<@layout.block name="footer-javascript-2"><#include "/layout/footer-javascript-2.ftl" /></@layout.block>
		<@layout.block name="page-javascript-inline"></@layout.block>
</body>
</html>