;(function($){
/**
 * jqGrid English Translation
 * Tony Tomov tony@trirand.com
 * http://trirand.com/blog/ 
 * Dual licensed under the MIT and GPL licenses:
 * http://www.opensource.org/licenses/mit-license.php
 * http://www.gnu.org/licenses/gpl.html
**/
$.jgrid = $.jgrid || {};
$.extend($.jgrid,{
	defaults : {
		recordtext: "보기 {0} - {1} / {2}",
		emptyrecords: "표시할 행이 없습니다",
		loadtext: "조회중...",
		pgtext : "페이지 {0} / {1}"
	},
	search : {
		caption: "Search...",
		Find: "Find",
		Reset: "Reset",
		odata: [{ oper:'eq', text:"equal"},{ oper:'ne', text:"not equal"},{ oper:'lt', text:"<"},{ oper:'le', text:"<="},{ oper:'gt', text:">"},{ oper:'ge', text:">="},{ oper:'bw', text:"로 시작한다"},{ oper:'bn', text:"로 시작하지 않는다"},{ oper:'in', text:"내에 있다"},{ oper:'ni', text:"내에 있지 않다"},{ oper:'ew', text:"로 끝난다"},{ oper:'en', text:"로 끝나지 않는다"},{ oper:'cn', text:"내에 존재한다"},{ oper:'nc', text:"내에 존재하지 않는다"}],
		groupOps: [	{ op: "AND", text: "모두 만족 (AND)" },	{ op: "OR",  text: "한가지라도 만족 (OR)" }	]
	},
	edit : {
		addCaption: "행 추가 (Add)",
		editCaption: "행 수정 (Edit)",
		bSubmit: "Submit",
		bCancel: "Cancel",
		bClose: "Close",
		saveData: "자료가 변경되었습니다! 저장하시겠습니까? (Save?)",
		bYes : "Yes",
		bNo : "No",
		bExit : "Cancel",
		msg: {
			required:"필수항목입니다 (Required)",
			number:"유효한 번호를 입력해 주세요 (Invalid Number)",
			minValue:"입력값은 크거나 같아야 합니다 (Min Limit)",
			maxValue:"입력값은 작거나 같아야 합니다 (Max Limit)",
			email: "유효하지 않은 이메일주소입니다 (Invalid Email)",
			integer: "유효한 숫자를 입력하세요 (Invalid Number)",
			date: "유효한 날짜를 입력하세요 (Invalid Date)",
			url: "은 유효하지 않은 URL입니다. 문장앞에 다음단어가 필요합니다('http://' or 'https://')",
			nodefined : " 은 정의도지 않았습니다!",
			novalue : " 반환값이 필요합니다!",
			customarray : "사용자정의 함수는 배열을 반환해야 합니다!",
			customfcheck : "Custom function should be present in case of custom checking!"
			
		}
	},
	view : {
		caption: "행 조회",
		bClose: "Close"
	},
	del : {
		caption: "삭제 (Del)",
		msg: "선택된 행을 삭제하시겠습니까? (Delete ?)",
		bSubmit: "Delete",
		bCancel: "Cancel"
	},
	nav : {
		edittext: "",
		edittitle: "선택된 행 편집 (Selected row edit)",
		addtext:"",
		addtitle: "행 삽입 (Add row)",
		deltext: "",
		deltitle: "선택된 행 삭제 (Selected row delete)",
		searchtext: "",
		searchtitle: "행 찾기 (Find row)",
		refreshtext: "",
		refreshtitle: "그리드 갱신 (Refresh)",
		alertcap: "경고",
		alerttext: "행을 선택하세요 (Select row!)",
		viewtext: "",
		viewtitle: "선택된 행 조회 (Selected row data)"
	},
	col : {
		caption: "열을 선택하세요",
		bSubmit: "OK",
		bCancel: "Cancel"
	},
	errors : {
		errcap : "오류",
		nourl : "설정된 url이 없습니다",
		norecords: "처리할 행이 없습니다",
		model : "colNames의 길이가 colModel과 일치하지 않습니다!"
	},
	formatter : {
		integer : {thousandsSeparator: ",", defaultValue: '0'},
		number : {decimalSeparator:".", thousandsSeparator: ",", decimalPlaces: 2, defaultValue: '0.00'},
		currency : {decimalSeparator:".", thousandsSeparator: ",", decimalPlaces: 0, prefix: "", suffix:" 원", defaultValue: '0'},
		date : {
			dayNames:   [
				"Sun", "Mon", "Tue", "Wed", "Thr", "Fri", "Sat",
				"일", "월", "화", "수", "목", "금", "토"
			],
			monthNames: [
				"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec",
				"1월", "2월", "3월", "4월", "5월", "6월", "7월", "8월", "9월", "10월", "11월", "12월"
			],
			AmPm : ["am","pm","AM","PM"],
			S: function (j) {return j < 11 || j > 13 ? ['st', 'nd', 'rd', 'th'][Math.min((j - 1) % 10, 3)] : 'th'},
			srcformat: 'Y-m-d',
			newformat: 'm-d-Y',
			parseRe : /[Tt\\\/:_;.,\t\s-]/,
			masks : {
				ISO8601Long:"Y-m-d H:i:s",
				ISO8601Short:"Y-m-d",
				ShortDate: "Y/j/n",
				LongDate: "l, F d, Y",
				FullDateTime: "l, F d, Y g:i:s A",
				MonthDay: "F d",
				ShortTime: "g:i A",
				LongTime: "g:i:s A",
				SortableDateTime: "Y-m-d\\TH:i:s",
				UniversalSortableDateTime: "Y-m-d H:i:sO",
				YearMonth: "F, Y"
			},
			reformatAfterEdit : false
		},
		baseLinkUrl: '',
		showAction: '',
		target: '',
		checkbox : {disabled:true},
		idName : 'id'
	}
});
})(jQuery);
