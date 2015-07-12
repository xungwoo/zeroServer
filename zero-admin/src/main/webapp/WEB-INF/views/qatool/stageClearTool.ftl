<#include "/common/form/textField.ftl">
<#include "/common/form/selectBox.ftl">

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
								모험 클리어 정보 수정 Tool (Modify stage clear info)
								<small style="color:red;">
									<i class="icon-double-angle-right"></i>
									※ 모험클리어 정보 수정시 해당 유저의 기존 모험클리어 정보는 모두 삭제됩니다.<br><br>
								</small>
							</h1>
						</div><!-- /.page-header -->

						<div class="row">
							<div class="col-xs-6">
							
										<div class="alert alert-success" style="display:none;width:900px;height:300px;z-index:1;" id="loading">
											처리 중입니다. 많은 스테이지 처리 시 오래 걸릴 수 있습니다.
											<br /> 잠시 기다려주세요!!
											<br/> (Processing... wait a minute!!)
										</div>
										
										
									<div class="form-group">
										
										<label style="float:left;width:70px;" for="accountId">UserID</label>
										<div class="col-sm-9">
											<input type="text" id="accountId" name="accountId" class="col-xs-9 col-sm-6" value=""/>
										</div>
										
										<br><br><br> 
										<#assign modeOptions = {"Easy":"1000", "Normal":"2000", "Hard":"3000", "Hell":"4000"}>
										<label for="mode" style="float:left;width:80px;">ClearMode </label>
										<select class="form-control" id="clearMode" style="width:80px;">
										<#assign keys = modeOptions?keys>
										<#list keys as key>
											<option id="mode_${key}" value="${modeOptions[key]}">${key}</option>
										</#list>											
										</select>
										
										<br>
										<#assign chapterOptions = ["1", "2", "3", "4"]>
										<label for="chapter" style="float:left;width:80px;">Chapter</label>
										<select class="form-control" id="chapter" style="width:80px;">
										<#list chapterOptions as chapterOpt>
											<option id="chapter_${chapterOpt}" value="${chapterOpt}">${chapterOpt}</option>
										</#list>											
										</select>
										
										<br>
										<#assign stageOptions = [1, 2, 3, 4, 5, 6, 6, 7, 8, 9, 10]>
										<label for="stage" style="float:left;width:80px;">Stage</label>
										<select class="form-control" id="stage" style="width:80px;">
										<#list stageOptions as stageOpt>
											<option id="stage_${stageOpt}" value="${stageOpt}">${stageOpt}</option>
										</#list>											
										</select>
									</div>
									
									<div class="clearfix form-actions">
										<div class="col-md-offset-4 col-md-9">
											<button class="btn btn-info" type="button" id="ajaxStart" onclick="javascript:stageClear();">
												<i class="icon-ok bigger-110"></i>
												모험클리어 정보 수정 (Stage clear modification)
											</button>
									</div>
													
									
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
			function stageClear() {
				$("#ajaxStart").attr("disabled", true);
		        $('#loading').css('position', 'absolute');
		        $('#loading').css('left', $('#ajaxStart').left);
		        $('#loading').css('top', $('#ajaxStart').top);
		        //$('#loading').show();
		        $('#loading').fadeIn(500);				
				
				var accountId = $('#accountId').val();
				var clearMode = $('#clearMode').val();
				var chapter = $('#chapter').val();
				var stage = $('#stage').val();
				
				if (accountId == '') {
					alert('Input UserID!!');
					return false;
				}
				if (clearMode == null || clearMode == '') {
					alert('Select ClearMode!!');
					return false;
				}
				if (chapter == '') {
					alert('Select Chapter!!');
					return false;
				}
				if (stage == '') {
					alert('Select Stage!!');
					return false;
				}
				
				
				$.ajax({
					  url: "${rc.contextPath}/qatool/stage-clear-tool/",
					  type: "POST",
					  dataType:"json",
					  context: document.body,
					  data: { accountId: accountId, chapter: chapter, stage: stage, clearMode: clearMode },
					  success: function(response){
					  		$("#loading").text('Processing Complete!!');
					        $("#loading").delay(1000).fadeOut(800);
					        //$("#loading").hide();
					        $("#ajaxStart").attr("disabled", false);
					  },
					error:function(request,status,error){
						if (request.status==400) {
							$("#loading").text('Invalid UserID.');
						} else if (request.status == 500) {
							$("#loading").text('Unknown Error!!');
						}
						$("#loading").delay(1000).fadeOut(800);
						$("#ajaxStart").attr("disabled", false);
					}			      
				});
			}
		
		</script>
	</@layout.put>
</@layout.extends>
