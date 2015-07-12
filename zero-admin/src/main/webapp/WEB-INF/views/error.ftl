<@layout.extends name="/layout/base.ftl">
	<@layout.put block="page-css">
		<link rel="stylesheet" href="${rc.contextPath}/css/rss/FeedEk.css" />	
	</@layout.put>

	<@layout.put block="content">
						<div class="row">
							<div class="col-xs-12">
								<!-- PAGE CONTENT BEGINS -->
								<div class="error-container">
									<div class="well">
										<h1 class="grey lighter smaller">
											<span class="blue bigger-125">
												<i class="icon-random"></i>
												Access Deny <i class="icon-wrench icon-animated-wrench black bigger-125"></i>
											</span>
										</h1>

										<hr />
										<h3 class="lighter smaller">
										</h3>

										<div class="space"></div>
										<div>
											<h4 class="lighter smaller">현재 접속중인 계정으로는 접근할 수 없는 페이지입니다!:</h4>

											<ul class="list-unstyled spaced inline bigger-110 margin-15">
												<li>
													<!--<i class="icon-hand-right blue"></i>-->
												</li>
<br><br><br><br><br><br>
												<li>
													<!--<i class="icon-hand-right blue"></i>
													Give us more info on how this specific error occurred!-->
												</li>
											</ul>
										</div>

										<hr />
										<div class="space"></div>

										<div class="center">
											<a href="${rc.contextPath}/home" class="btn btn-grey">
												<i class="icon-arrow-left"></i>
												Go Home
											</a>

											<a href="${rc.contextPath}/" class="btn btn-primary">
												<i class="icon-dashboard"></i>
												Login
											</a>
										</div>
									</div>
								</div>

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