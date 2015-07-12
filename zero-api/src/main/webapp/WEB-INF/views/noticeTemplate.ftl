<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<title>NOTICE</title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta name="description" content="">

	<script src="/api/js/jquery-1.10.1.js"></script>
	<script src="/api/js/bootstrap-transition.js"></script>
	<script src="/api/js/bootstrap-collapse.js"></script>
	
	<link href="/api/css/bootstrap.css" rel="stylesheet">
	<link href="/api/css/bootstrap-responsive.css" rel="stylesheet">
	<style>
	/*
		.accordion { margin-top:-1px;}
		.accordion-group{
			border:0;
			border-top:1px solid #AAA;
			-webkit-border-radius: 0px;
			-moz-border-radius: 0px;
			border-radius: 0px;
		}

		*/
		.accordion-inner {
			padding-top:10px;
			padding-bottom:30px;
			/*background-color:#f7f7f9;*/
		}
		
		/*
		.accordion-heading{
			padding-top:3px;
			padding-bottom:3px;
		}
		*/
		
		.accordion-heading a{
			text-decoration:none;
			font-weight:bold;
		}

		.accordion-heading a:hover{
			text-decoration:none;
		}
		.date{
			color:#AAA;
			padding:10px;
		}
		
		body{padding:10px;}
	</style>
</head>

<body>

	<div class="accordion" id="accordion">
		<#list notice as item>	
		<div class="accordion-group">
	    	<div class="accordion-heading">
	        	<div class="pull-right date">
		        	${item.getFormattedCreatedAt()}
		        </div>
	     	 	<a class="accordion-toggle" data-toggle="collapse" data-parent="#accordion" href="#collapse${item.createdAt?c}">
	        		${item.title}
	    		</a>
	    	</div>
	    	<div class="clearfix"></div>
	    	
	    	<#if item_index==0>
	    		<#assign addtionalClass="in">
	    	<#else>
	    		<#assign addtionalClass="">
	    	</#if>
	    	
	    	<div id="collapse${item.createdAt?c}" class="accordion-body collapse ${addtionalClass}">
	      		<div class="accordion-inner">
	       			${item.content}
	      		</div>
	    	</div>
		</div>
		</#list>
	</div>

</body>
</html>
