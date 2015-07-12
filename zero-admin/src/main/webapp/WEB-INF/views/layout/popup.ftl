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
	<@layout.block name="content"></@layout.block>
	
	<!-- basic scripts -->
	<@layout.block name="footer-javascript-1"><#include "/layout/footer-javascript-1.ftl" /></@layout.block>
	<!-- page specific plugin scripts -->
	<@layout.block name="page-javascript-plugin"></@layout.block>
	<@layout.block name="footer-javascript-2"><#include "/layout/footer-javascript-2.ftl" /></@layout.block>
	<@layout.block name="page-javascript-inline"></@layout.block>
</body>
</html>