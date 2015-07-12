<#macro decoration>
<#setting number_format="#">
<#setting boolean_format="yes,no">
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<title><#if pageInfo??>${pageInfo.getTitle()}</#if></title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="">
<meta name="author" content="">

<link href="${rc.contextPath}/css/jquery-smoothness/jquery-ui-1.10.3.min.css" rel="stylesheet">
<link href="${rc.contextPath}/css/bootstrap/bootstrap.css" rel="stylesheet">
<link href="${rc.contextPath}/css/bootstrap/bootstrap-responsive.css" rel="stylesheet">
<link href="${rc.contextPath}/css/admin.css" rel="stylesheet">

<script src="${rc.contextPath}/js/jquery/jquery-1.10.1.js"></script>
<script src="${rc.contextPath}/js/jquery/jquery-ui-1.10.3.min.js"></script>
<script src="${rc.contextPath}/js/bootstrap/bootstrap.min.js"></script>
<script src="${rc.contextPath}/js/bootstrap/bootstrap-typeahead.js"></script>
<script src="${rc.contextPath}/js/bootstrap/bootstrap-button.js"></script>
<script src="${rc.contextPath}/js/handlebars/handlebars-v1.3.0.js"></script>
<script src="${rc.contextPath}/js/common.js"></script>
</head>

<body>
	<div class="navbar navbar-inverse navbar-fixed-top">
		<div class="navbar-inner">
			<div class="container-fluid">
				<button type="button" class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
					<span class="icon-bar"></span> 
					<span class="icon-bar"></span> 
					<span class="icon-bar"></span>
				</button>
				<a class="brand" href="${rc.contextPath}/console">Zero Administrator Tool</a>

				<#include "./topNav.ftl">
				
				<div class="nav-collapse collapse">
					<p class="navbar-text pull-right">
						로그인 <a href="#" class="navbar-link">Username</a>
					</p>
				</div>
			</div>
		</div>
	</div>
	
	
	<div class="container-fluid">
		<div class="row-fluid">
			<div class="span2">
				 <#include "./leftNav.ftl">
			</div>
			<div class="span10">
		 		<#nested/>
			</div>
		</div>
	</div>
</body>
</html>
</#macro>
