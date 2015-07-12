<#include "/common/form/textField.ftl">

<@layout.extends name="/layout/base.ftl">
	<@layout.put block="page-css">
		<link rel="stylesheet" href="${rc.contextPath}/css/jquery-ui-1.10.3.full.min.css" />
		<link rel="stylesheet" href="${rc.contextPath}/css/datepicker.css" />
		<link rel="stylesheet" href="${rc.contextPath}/css/ui.jqgrid.css" />	
	</@layout.put>
	<@layout.put block="content">

					<div class="page-content">
						<div class="page-header">
							<h1>
								Equipment DecoStat Rate Meta
								<small>
									<i class="icon-double-angle-right"></i>
									장비생성 시 장식속성이 부여될 확률정보를 관리합니다.<br><br>
									모든 확률의 합은 반드시 1이 되야 합니다.<br>
								</small>
							</h1>
						</div><!-- /.page-header -->

						<div class="row">
							<div class="col-xs-6">
							
										<div class="alert alert-danger" style="display:none;">
											<button type="button" class="close" data-dismiss="alert">
												<i class="icon-remove"></i>
											</button>

											<strong>
												<i class="icon-remove"></i>
												Oh snap!
											</strong>

											Change a few things up and try submitting again.
											<br />
										</div>
							
							
								<div class="clearfix form-actions">
								<form name="rateForm" id="rateForm">
									<!-- PAGE CONTENT BEGINS -->
									<@textField inputId="maxSize" title="최대장식속성개수" value="${result.maxSize}" span="0~5" placeholder="Size"/>
									<@textField inputId="zero" title="장식속성 0개일 확률" value="${result.zero}" span="0.000~1.000" />
									<@textField inputId="one" title="장식속성 1개일 확률" value="${result.one}" span="0.000~1.000" />
									<@textField inputId="two" title="장식속성 2개일 확률" value="${result.two}" span="0.000~1.000" />
									<@textField inputId="three" title="장식속성 3개일 확률" value="${result.three}" span="0.000~1.000" />
									<@textField inputId="four" title="장식속성 4개일 확률" value="${result.four}" span="0.000~1.000" />
									<@textField inputId="five" title="장식속성 5개일 확률" value="${result.five}" span="0.000~1.000" />
									<input type="hidden" id="metaKey" name="metaKey" value="${result.metaKey}"/>
								
										<div class="col-md-offset-3 col-md-9">
										<br><br>
											<button class="btn btn-info" type="button" onClick="javascript:save();">
												<i class="icon-ok bigger-110"></i>
												Save
											</button>

											&nbsp; &nbsp; &nbsp;
											<button class="btn" type="reset">
												<i class="icon-undo bigger-110"></i>
												Reset
											</button>
										</div>
									</div>
								</form>
								<!-- PAGE CONTENT ENDS -->
							</div><!-- /.col -->
						</div><!-- /.row -->
					</div><!-- /.page-content -->
	</@layout.put>

	<@layout.put block="page-javascript-plugin">
		<!-- page specific plugin scripts -->
		<script src="${rc.contextPath}/js/jquery-validate/jquery.validate.js"></script>
		<script src="${rc.contextPath}/js/jquery-validate/additional-methods.js"></script>
	</@layout.put>

	<@layout.put block="page-javascript-inline">
		<!-- inline scripts related to this page -->
		<script type="text/javascript">
			$(document).ready(function(){
			    formElementsReadOnly(${result.maxSize});
			    
 				$('#rateForm').bind('reset', function(){ 
			        formElementsReadOnly(${result.maxSize});
			    });	
			    
			    $('#rateForm').validate({
		            rules: {
		                maxSize: { required: true, min:0, max:5 },
		                zero: { required: true, min:0, max:1.0 },
		                one: { required: true, min:0, max:1.0 },
		                two: { required: true, min:0, max:1.0 },
		                three: { required: true, min:0, max:1.0 },
		                four: { required: true, min:0, max:1.0 },
		                five: { required: true, min:0, max:1.0 },
		            },
	                errorPlacement: function(error, element){
                    	error.appendTo(element);
                   },
        		});
							    
			});
			
			$('#maxSize').keyup(function(event) {
				var size = parseInt(event.target.value);
				formElementsReadOnly(size);
			})
			
			function save() {
				$('#rateForm').attr({action:'${rc.contextPath}/${baseUrl}', method:'post'}).submit();
			}
			
			function formElementsReadOnly(size) {
				$("#rateForm input").each(function(i, v){
					if(i > (size + 1)) {
						$(this).attr('readonly', true);
					} else {
						$(this).attr('readonly', false);
					}
				});				
			}
		</script>
	</@layout.put>
</@layout.extends>
