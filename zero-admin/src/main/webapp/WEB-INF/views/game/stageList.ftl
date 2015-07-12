<#include "/common/form/textfield.ftl">
<#include "/common/form/textfieldWithRange.ftl">
<#include "/common/form/radioButton.ftl">
<#import "/tpl/admin.ftl" as layout>
 
<@layout.decoration>
<#setting number_format="#">
	<h3>
		스테이지 목록
	</h3>
	<div id="errorNotiPanel" class="alert alert-error hidden">
		<h4>수정 실패</h4>
		<span id="errorReason"></span>
	</div>
	<div id="successNotiPanel" class="alert alert-success hidden">
		<h4>수정 성공</h4>
		스테이지 데이터가 성공적으로 수정되었습니다.
	</div> 
	
	<div class="alert alert-info">
		Chapter :
		<div class="btn-group">
			<button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
		    	<span id="dropdownTitle">All</span> <span class="caret"></span>
		  	</button>
		  	<ul class="dropdown-menu" role="menu">
		  		<li role="presentation"><a role="menuitem" tabindex="-1" href="javascript:searchStage({});">All</a></li>
			  	<#if chapterList?exists>
			  	<#list chapterList as chapter>
			    	<li role="presentation"><a role="menuitem" tabindex="-1" href="javascript:searchStage({chapter : ${chapter!}});">${chapter!}</a></li>
			    </#list>	
			  	</#if>	  
		  	</ul>
		</div>
		<br>	
		편집을 원하는 Cell을 클릭하세요.
	</div>

	<div style="margin-top:10px">
		<a class="btn btn-lg btn-primary" onclick="javascript:window.location.reload();" role="button">Reload</a>
		<a class="btn btn-lg btn-primary" onclick="javascript:saveStage('stageId');" role="button">Save</a>
		<a class="btn btn-lg btn-primary" onclick="javascript:addStage();" role="button">Add Contents</a>
	</div>	
	<br>
	
	<#include "/template/stage.hand">
	
	<div id="stageGridTarget">		
	<table class="table" id="stageGridTable">
		<tr>
			<th>chapter</th>
			<th>stage</th>
			<th>name</th>
			<th>description</th>
			<th>sectionLength</th>
			<th>playMode</th>
			<th>isHidden</th>
			<th>reveal</th>
			<th>limitTime</th>
			<th>star2PlayTime</th>
			<th>star3PlayTime</th>
			<th>goldScaleForEnemyKill</th>
			<th>rewardGold</th>
			<th>rewardCash</th>
			<th>rewardUnit</th>
		</tr>

		<#list resultList as result>
		<tr key="${result.stageId!}" style="cursor:pointer">
			<td width="40" column="chapter">${result.chapter!}</td>
			<td width="40" column="stage">${result.stage!}</td>
			<td class="gridcell" width="80" column="name">${result.name!}</td>
			<td class="gridcell" width="200" column="description">${result.description!}</td>
			<td class="gridcell" width="80" column="sectionLength">${result.sectionLength!}</td>
			<td class="gridcell" width="80" column="playMode">${result.playMode!}</td>
			<td class="gridcell" width="80" column="isHidden">${result.isHidden?is_boolean}</td>
			<td class="gridcell" width="80" column="reveal">${result.reveal!}</td>
			<td class="gridcell" width="80" column="limitTime">${result.limitTime!}</td>
			<td class="gridcell" width="80" column="star2PlayTime">${result.star2PlayTime!}</td>
			<td class="gridcell" width="80" column="star3PlayTime">${result.star3PlayTime!}</td>
			<td class="gridcell" width="80" column="goldScaleForEnemyKill">${result.goldScaleForEnemyKill!}</td>
			<td class="gridcell" width="80" column="goldScaleForEnemyKill">${result.rewardGold!}</td>
			<td class="gridcell" width="80" column="goldScaleForEnemyKill">${result.rewardCash!}</td>
			<td class="gridcell" width="80" column="goldScaleForEnemyKill">${result.rewardUnitType!}</td>
		</tr>
		</#list>
	</table>
	</div>

	<div id="dialog" title="Add Record" style="display:none;">
	<form class="form-horizontal">
		<@textfield inputId="chapter" title="Chapter" value=1/>
		<@textfield inputId="stage" title="Stage" value=1/>
		<@textfield inputId="name" title="Name" value=1/>
		<@textfield inputId="description" title="Sescription" value=1/>
		<@textfieldWithRange inputId="sectionLength" title="SectionLength" value=1 min=1 max=1000/>
		
		
		<@radioButton "playMode" playModeList "KillAll" "PlayMode"/>
		<@radioButton inputId="isHidden" title="히든 스테이지" item=["false","true"] value="false"/>
		<@radioButton "reveal" revealList "AlwaysOpened" "RevealCondition"/>
		<@textfieldWithRange inputId="limitTime" title="limitTime" value=1 min=1 max=600/>
		<@textfieldWithRange inputId="star2PlayTime" title="star2PlayTime" value=1 min=1 max=600/>
		<@textfieldWithRange inputId="star3PlayTime" title="star3PlayTime" value=1 min=1 max=600/>
		<@textfield inputId="goldScaleForEnemyKill" title="goldScaleForEnemyKill" value=1 span="0~1.0"/>
		<@textfieldWithRange inputId="rewardGold" title="rewardGold" value=1 min=1 max=1000/>
		<@textfieldWithRange inputId="rewardCash" title="rewardCash" value=1 min=1 max=1000/>
		<@textfieldWithTypeAhead inputId="rewardUnitType" title="보상유닛타입" dataItems=20 dataSource='${unitTypes}' value="NONE"/>
	</form>
	</div>
	<script>
		$('.dropdown-toggle').dropdown();
		
		ttgames.grid.init('stageGridTable');
		ttgames.handlebars.oConfig.oExcludeKeys = {
			modelList : true, 
			clearLevelLimitTime : true
		};		
		
		var searchStage = function(oData) {
			var jDropTitle = $('#dropdownTitle');
			if(oData.chapter) {
				jDropTitle.text(oData.chapter);
			} else {
				jDropTitle.text('All');	
			}
		
			var sUrl = '${rc.contextPath}/stage/json/search';
			var oOption = {
				sTemplateId:'stageGridTmpl', 
				sTargetId:'stageGridTarget',
				sErrorPanelId: 'errorNotiPanel', 
				callBack: ttgames.grid.init,
				callBackArg:'stageGridTable',
				callBackScope: ttgames.grid
			}
			ttgames.form.ajax(sUrl, oData, oOption);
		}
		
		var saveStage = function(sKey) {
			var sUrl = '${rc.contextPath}/stage/upload';
			var oOption = {
				sSuccessPanelId: 'successNotiPanel', 
				sErrorPanelId: 'errorNotiPanel', 
				sErrorReasonId: 'errorReason'
			}
			ttgames.grid.save(sUrl, sKey, oOption);
			ttgames.grid.init('stageGridTable');
		}
		
		$( "#dialog" ).dialog({
			autoOpen: false,
			width: 900,
			buttons: [{
				text: "Ok",
				click: function() { $( this ).dialog( "close" ); }
			}, {
				text: "Cancel",
				click: function() { $( this ).dialog( "close" ); }
			}]
		});	
		
		var addStage = function() {
			$( "#dialog" ).dialog( "open" );
				
		}

	</script>
 
</@layout.decoration>
