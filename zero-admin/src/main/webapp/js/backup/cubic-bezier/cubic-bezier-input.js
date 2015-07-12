function makeCurrentBezier(canvasId, curve) {
	var canvas = document.getElementById(canvasId);
	var currentCanvas = new BezierCanvas(canvas, null, .15);
	currentCanvas.bezier = new CubicBezier(curve);
	currentCanvas.plot({
		handleColor: 'rgba(255,255,255,.5)',
		bezierColor: 'white',
		handleThickness: .03,
		bezierThickness: .06
	});
	
	return currentCanvas;
}

function makeCandidateBezier(canvasId, curve) {
	var canvas = document.getElementById(canvasId);
	var candidateCanvas = new BezierCanvas(canvas, null, .15);
	candidateCanvas.bezier = new CubicBezier(curve);
	candidateCanvas.plot({
		handleColor: 'rgba(0,0,0,.5)',
		bezierColor: 'rgba(0,0,0,.4)',
		handleThickness: .03,
		bezierThickness: .06
	});
}

function updateBezier(canvasId, canvas){
	canvas.bezier = new CubicBezier( $("#"+canvasId).val() );
	canvas.plot({
		handleColor: 'rgba(255,255,255,.5)',
		bezierColor: 'white',
		handleThickness: .03,
		bezierThickness: .06
	});
}

function updateBezierText(text,canvas,curve){
	$("#"+text).val(curve);
	updateBezier(text, canvas);
}