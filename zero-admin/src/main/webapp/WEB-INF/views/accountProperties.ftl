<#include "form/textfield.ftl">
<#import "tpl/admin.ftl" as layout>

<@layout.decoration>
	<h3>사용자 정보 수정</h3>
	<div id="errorNotiPanel" class="alert alert-error hidden">
		<h4>사용자 정보 수정 실패</h4>
		<span id="errorReason"></span>
	</div>
	<div id="successNotiPanel" class="alert alert-success hidden">
		<h4>사용자 정보 수정 성공</h4>
		정상적으로 사용자 정보가 수정되었습니다.
	</div>
	<form class="form-horizontal">
		<@textfield "accountId" account.accountId false/>
		<@textfield "nickName" account.nickName/>
		<@textfield "channelId" account.channelId false/>
		<@textfield "channelType" account.channelType false/>
		<@textfield "channelToken" account.channelToken false/>
		<@textfield "profileType" account.profileType/>
		<@textfield "profileUrl" account.profileUrl/>
		
		<@textfield "level" account.level/>
		<@textfield "exp" account.exp/>
		<@textfield "apLastValue" account.apLastValue/>
		<@textfield "apMax" account.apMax/>
		<@textfield "gold" account.gold/>
		<@textfield "cash" account.cash/>
		
		<@textfield "achievementId" account.achievementId false/>
		<@textfield "authToken" account.authToken false/>
		
		<@textfield "deviceId" account.deviceId false/>
		<@textfield "osType" account.osType false/>
		<@textfield "osVersion" account.osVersion false/>
		<@textfield "language" account.language/>
		<@textfield "country" account.country/>
		<@textfield "localTimezone" account.localTimezone/>

		<div class="control-group">
			<div class="controls">
				<button id="btnModify" type="button" class="btn" onclick="modifyAccount()">수정</button>
			</div>
		</div>
	</form>
</@layout.decoration>
