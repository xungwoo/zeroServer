<@layout.extends name="/layout/base.ftl">
	<@layout.put block="page-css">
		<link rel="stylesheet" href="${rc.contextPath}/css/rss/FeedEk.css" />	
	</@layout.put>

	<@layout.put block="content">
						<div class="page-header">
							<h1>
								Game Admin
								<small>
									<i class="icon-double-angle-right"></i>
									Game 정보들을 관리합니다.
								</small>
							</h1>
						</div><!-- /.page-header -->

						<div class="row">
						
							<div class="col-xs-12">
								<!-- PAGE CONTENT BEGINS -->


								<!-- PAGE CONTENT ENDS -->
							</div><!-- /.col -->
						</div><!-- /.row -->

	</@layout.put>
	<@layout.put block="page-javascript-plugin">
		<!-- page specific plugin scripts -->

		<script src="${rc.contextPath}/js/jquery-ui-1.10.3.custom.min.js"></script>
		<script src="${rc.contextPath}/js/jquery.ui.touch-punch.min.js"></script>
		<script src="${rc.contextPath}/js/jquery.slimscroll.min.js"></script>
		<script src="${rc.contextPath}/js/lib/moment-with-langs.js"></script>
		<script src="${rc.contextPath}/js/rss/FeedEk.js"></script>
		
		<!-- Chart -->
		<script src="${rc.contextPath}/js/jquery.sparkline.min.js"></script>
		<script src="${rc.contextPath}/js/flot/jquery.flot.min.js"></script>
		<script src="${rc.contextPath}/js/flot/jquery.flot.pie.min.js"></script>
		<script src="${rc.contextPath}/js/flot/jquery.flot.resize.min.js"></script>		
	</@layout.put>

	<@layout.put block="page-javascript-inline">	
	</@layout.put>
</@layout.extends>