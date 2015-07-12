<#assign defaultBezier='.5,.2,.8,.5'>
<#assign linearBezier='0,0,1,1'>
<#assign easeInBezier='.42,0,1,1'>
<#assign easeOutBezier='0,0,.58,1'>
<#assign easeInOutBezier='.42,0,.58,1'>

<#macro textfieldWithCubicBezier inputId value=defaultBezier title="" span="">
<div class="control-group" id="${inputId}Group">
	<label class="control-label" for="${inputId}"><#if title?has_content>${title}<#else>${inputId}</#if></label>
	<div class="controls">
		<input type="text" id="${inputId}" value="${value}" oninput="updateBezier('${inputId}',${inputId}Canvas)"/>
		<span class="muted">${span!}</span>

		<div id="library">
			<div class="item">
				<canvas class="current" id="${inputId}_current"></canvas>
				<span>current</span>
			</div>
			<a class="item" onclick="updateBezierText('${inputId}',${inputId}Canvas,'${defaultBezier}')">
				<canvas id="${inputId}_default"></canvas>
				<span>default</span>
			</a>
			<a class="item" onclick="updateBezierText('${inputId}',${inputId}Canvas,'${linearBezier}')">
				<canvas id="${inputId}_linear"></canvas>
				<span>linear</span>
			</a>
			<a class="item" onclick="updateBezierText('${inputId}',${inputId}Canvas,'${easeInBezier}')">
				<canvas id="${inputId}_easeIn"></canvas>
				<span>ease-in</span>
			</a>
			<a class="item" onclick="updateBezierText('${inputId}',${inputId}Canvas,'${easeOutBezier}')">
				<canvas id="${inputId}_easeOut"></canvas>
				<span>ease-out</span>
			</a>
			<a class="item" onclick="updateBezierText('${inputId}',${inputId}Canvas,'${easeInOutBezier}')">
				<canvas id="${inputId}_easeInOut"></canvas>
				<span>ease-in-out</span>
			</a>
		</div>
		<script>
			${inputId}Canvas = makeCurrentBezier('${inputId}_current','${value}');
			makeCandidateBezier('${inputId}_default', '${defaultBezier}');
			makeCandidateBezier('${inputId}_linear', '${linearBezier}');
			makeCandidateBezier('${inputId}_easeIn', '${easeInBezier}');
			makeCandidateBezier('${inputId}_easeOut', '${easeOutBezier}');
			makeCandidateBezier('${inputId}_easeInOut', '${easeInOutBezier}');
		</script>
	</div>
</div>
</#macro>
