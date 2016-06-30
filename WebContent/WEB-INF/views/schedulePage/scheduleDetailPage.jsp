<%@ page contentType="text/html; charset=utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
<title>Scheduling Page</title>
<link rel="stylesheet" href="http://192.168.20.112:8080/Railring_v18/bootstrap/css/bootstrap.min.css" />
<script src="http://192.168.20.112:8080/Railring_v18/bootstrap/js/jquery-2.2.3.min.js"></script>
<link rel="stylesheet" href="http://code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">
<script src="http://code.jquery.com/jquery-1.10.2.js"></script>
<script src="http://code.jquery.com/ui/1.11.4/jquery-ui.js"></script>
<style>
<!-- 16.06.22 추가 css -->
.route{ list-style-type: none; margin: 0; padding: 0;}
.route li{ list-style:none; font-size: 1.0em; background-color: #FFFFFF}
<!-- 16.06.21 추가 css -->
.media-object{display: block;min-width: 100%;min-height: 100%;}
.pull-left{display: block;overflow: hidden;height: 60px;width: 60px;}
<!-- 16.06.17 추가 css -->
.sort { list-style-type: none; margin: 0; padding: 0; width: 60%; }
.sort li { width:150px; margin: 0 3px 3px 3px; padding: 0.4em; padding-left: 1.5em; font-size: 1.4em; height: 18px; }
.sort li span { position: absolute; margin-left: -1.3em; }
.sort li a { position: absolute; left: 190px; }
<!-- ----------------------------------원본css--------------------------------------------  -->
.nav-li {width:172px;text-align: center;}
#timeTableDiv{background-color: BurlyWood;height:25%;vertical-align: bottom;}
.mapDiv{ height:100%;overflow-y: hidden;vertical-align: top;}   
#dragbar{background-color:black;height: 10px;width: 100%;cursor: row-resize;vertical-align: middle;}   
#ghostbar{height:3px;width: 100%;background-color:#000;opacity:0.5;position:absolute;cursor: row-resize;z-index:999}
</style>
<script>
	function addDetail(){
		var formData = $("#scheduleForm").serialize();
		 console.dir(formData);
		  $.ajax({
		   type : "POST",
		   url : $("#scheduleForm").attr('action'),
		   cache : false,
		   data : formData,
		   success : function (data) {
			   data = $.parseJSON(data);
			   console.dir(data);
			   if(data.result) {
				   alert('스케줄 등록에 성공하였습니다.');
			   } else {
				   alert(data.message);
			   }
		   },
		   fail : function (e) {
			   alert('스케줄 등록에 실패하였습니다.');
		   }
		});
	}
	
	// marker_tot에서  title이 같은 마커 리턴
	function getMarkerFromTot(title){
		for(var i=0; i<marker_tot[0].length; i++){
			if(marker_tot[0][i].getTitle() == title){
				return marker_tot[0][i];
			}
		}
	}	
	
	function loadDetail(){
// 		if(sch_no != null && cityNo != null){
// 			alert("로드함수: 스케쥴 "+sch_no+", 도시 "+cityNo);
// 		}
		   var params={
				   "sch_no":sch_no,
				   "city_no":cityNo
		   }
			$.ajax({
				   type : "POST",
				   url : "schedule.action?cmd=DETAILLOAD",
				   cache : false,
				   data : params,
				   success : function (data) {
						data = $.parseJSON(data);
						// 현재 등록된 마커 제거
						for(var i=0; i<marker_arr.length; i++){
							// 지울 마커 이미지를 기본이미지로 바꾸고, 해당 플래그 false로 변환
							fnSetDefaultMarker(marker_arr[i]);
							// 지울 마커 sortable리스트에서 제거
		 					$("#sort_li_" + marker_arr[i].getTitle()).remove();
						}
						// 등록배열에서 마커 제거
						marker_arr.splice(0, marker_arr.length);
						
						// 선택한 스케쥴로 마커 등록
						for(var i=0; i<data.infos.length; i++){
							// title값으로 마커를 가져옴
							console.log(data.infos[i].info_title);
							var tempMarker = getMarkerFromTot(data.infos[i].info_title);
							// 등록배열에 마커 추가
							marker_arr.push(tempMarker);
							// 마커 추가
							fnPlanMarkers(tempMarker);
							
							// marker_tot 배열에서 등록한 마커의 index값 가져옴
							var idx = 0;
							for(var j=0; j<marker_tot[0].length; j++){
								if(marker_tot[0][j].getTitle() == tempMarker.getTitle()){
									idx = j;
									break;
								}
							}
							// 등록한 마커의 플래그 true로 변환(등록표시)
							marker_tot[1][idx] = true;
							//alert("title : " + marker_tot[0][idx].getTitle() + ", idx : " + marker_tot[1][idx]);
						}
						// 등록한 마커 이미지 등록이미지로 변환
						fnSetMarkerImage(marker_arr);
						// polyline 그리기
						fnSetPoly();
				   },
				   fail : function (e) {
					   alert('등록된 스케줄이 없습니다.');
				   }
				});
		}
</script>
</head>
<body>
	<div class="container">
		<div class="row-fluid">
			<div class="span12">
				<!------------------- 지도 출력 ------------------->
				<div class="span9" id="mapSize" style="height: 700px">
					<div id="sortable_div" style="position: absolute; top: 10%; left: 20px; z-index: 5;">
						<form id="scheduleForm" action="schedule.action?cmd=DETAILPOST" method="post">
							<ul id="sortable"></ul>
						</form>
					</div>
					<div id="route_control" style="position: absolute; top: 20px; left: 50px; z-index: 5;">
						<ul id="route_ul" class="route nav nav-tabs">
							<li><a href="javascript:loadDetail()">불러오기</a></li>
							<li><a href="javascript:addDetail()">저장하기</a></li>
						</ul>
					</div>
					<div id="map" class="mapDiv" style="width: 100%; align: center;"></div>
				</div>
				<!------------------- 사이드 탭 메뉴(오른쪽) 관광지 정보 ------------------->
				
				<div class="span3">
					<div class="tabbable" id="sideMenu">
						<ul class="nav nav-tabs" id="category">
							<li id="loc" class="active" data-order="0"><a href="#tab1"
								data-toggle="tab">명소</a></li>
							<li id="hot" data-order="1"><a href="#tab2"
								data-toggle="tab">숙박</a></li>
							<li id="res" data-order="2"><a href="#tab3"
								data-toggle="tab">맛집</a></li>
						</ul>
						<div class="tab-content" style="overflow: scroll; overflow-x:hidden; overflow-y:auto;height: 587px;">
							<div class="tab-pane active" id="tab1">
<!-- 								li태그들어갈곳  -->
								<ul class="media-List" id="placesList"></ul>
							</div>
							<div class="tab-pane" id="tab2">
								<ul class="media-List" id="placesList2"></ul>
							</div>
							<div class="tab-pane" id="tab3">
								<ul class="media-List" id="placesList3"></ul>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="modal fade" id="myModal" role="dialog">
    	<div class="modal-dialog">
     		 <!-- Modal content-->
     		 <div class="modal-content">
			    <div class="modal-header">
         			<button type="button" class="close" data-dismiss="modal">&times;</button>
          			<h4 class="modal-title"></h4>
       		 	</div>
        	 	<div class="modal-body">
          			<table class="table-hover" style="width: 100%">
<!--           					<td id="first"></td> -->
<!--           					<td id="second"></td> -->
<!--           					<td id="thrid"></td> -->
          				<tr>
          					<td colspan="6" id="detailImg"></td>
          				</tr>
          				<tr>
          					<td colspan="2" width="20%">상호명</td>
          					<td colspan="4" id="detailTitle"></td>
          				</tr>
          				<tr>

          					<td colspan="2">주소</td>
          					<td colspan="4" id="detailAddr"></td>
          				</tr>
          				<tr>
          					<td colspan="2">전화번호</td>
          					<td colspan="4" id="detailTel"></td>
          				</tr>
          				<tr>
          					<td colspan="2">소개</td>
          					<td colspan="4" id="detailIntroduction"></td>
          				</tr>
          			</table>
        	 	</div>
        		<div class="modal-footer">
          			<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
        		</div>
      		</div>
    	</div>
  </div>
<script>
	var i = 0;
	var dragging = false;

	var obj = $("#mapSize").offset();
	var obj2 = $("#dragbar").offset();
	var obj3 = $("#timeTableDiv").offset();

	$('#console').text(	"dragbar : " + obj2.left + ", " + obj2.top + " timeTableDiv : " + obj3.left + ", " + obj3.top);
	//   화면 분할 jquery
	$('#dragbar').mousedown(function(e) {
		e.preventDefault();
		dragging = true;
		
		var timeTableDiv = $('#timeTableDiv');
		var ghostbar = $('<div>', {
			id : 'ghostbar',
			css : {
				width : timeTableDiv.outerWidth(),
				top : timeTableDiv.offset().top,
				left : timeTableDiv.offset().left
			}
		}).appendTo('body');

		$(document).mousemove(function(e) {
			ghostbar.css("top", e.pageY + 2);
			var percentage = ((e.pageY - 212) / $('#mapSize').height()) * 100;
			var timeTableDivPercentage = 100 - percentage;
			$('#console').text("y좌표 : "	+ e.pageY + ", " + "% : " + percentage + "%, " + timeTableDivPercentage + "%");
		});
	});

	$(document).mouseup(function(e) {
		if (dragging) {
			var percentage = ((e.pageY - 212) / $('#mapSize').height()) * 100;
			var timeTableDivPercentage = 100 - percentage;

			if ((percentage < 10) && (timeTableDivPercentage > 90)) {
				percentage = 10;
				timeTableDivPercentage = 90;
			}
			else if ((timeTableDivPercentage < 10) && (percentage > 90)) {
				timeTableDivPercentage = 10;
				percentage = 90;
			}
			$('.mapDiv').css("height", percentage + "%");
			$('#timeTableDiv').css("height", timeTableDivPercentage + "%");
			$('#ghostbar').remove();
			$(document).unbind('mousemove');
			dragging = false;
			
			$('#console').text(percentage + "%, " + timeTableDivPercentage + "%");
		}
	});
</script>   
<!-- ---------------------------------------다음------------------------------------------------- -->
<!-- ---------------------------------------지도------------------------------------------------- -->
<!-- ---------------------------------------지도------------------------------------------------- -->
<!-- ---------------------------------------지도------------------------------------------------- -->
<!-- ---------------------------------------지도------------------------------------------------- -->
<!-- ---------------------------------------지도------------------------------------------------- -->
<script type="text/javascript" src="//apis.daum.net/maps/maps3.js?apikey=5200457bd85ff57067a5911709d2b3e5&libraries=services,clusterer"></script>
<script>
var cityNo=<%=request.getParameter("cityNo")%>;
var sch_no=<%=request.getParameter("sch_no")%>;

// alert("지금 짜고 있는 스케쥴 번호 : " +sch_no);
// alert("받아온 도시 번호: "+cityNo);
//번호확인
var cityTitle= "";
var mapLat="";
var mapLng="";
var map;
var markerList=[];//전체마커배열
var markerLoc=[],markerHot=[],markerRes=[];//부분마커배열
// var markerList=[];

//********************** 160619 변수 추가분 시작 ***************************************
//by 수항
//등록 관련 변수
var marker_arr = [],		// 등록된 마커를 저장할 배열
	marker_temp = null,		// 등록된 마커를 스왑 할 템프
	marker_origImg = null;	// 기본 이미지 저장 변수


//polyline 관련 변수
var poly = new daum.maps.Polyline({
	endArrow:true,
	strokeWeight: 2, // 선의 두께 입니다
	strokeColor: '#FF0000', // 선의 색깔입니다
	strokeOpacity: 0.7, // 선의 불투명도 입니다 1에서 0 사이의 값이며 0에 가까울수록 투명합니다
	strokeStyle: 'dash' // 선의 스타일입니다
});

//marker 2차원 배열 선언
var	marker_tot = new Array();
	marker_tot[0] = new Array();	// 모든 마커
	marker_tot[1] = new Array();	// 모든 마커에 따른 플래그
	
//등록 함수
function fnPlanMarkers(marker){
	var $sortable = $("#sortable");
	var param = '"' + marker.getTitle() + '"';
	//var liContents = "<li class='ui-state-default' id='li_"+ marker.getTitle() +"'><span class='ui-icon ui-icon-arrowthick-2-n-s'></span>"+ marker.getTitle() +"<a style='display:inline-block;' href='javascript:fnDelMarker("+ param +")'><i class='icon-user icon-remove'></i></a></li>";
	var liContents = "<li id='sort_li_"+ marker.getTitle() +"' class='ui-state-default' style='list-style:none;'><span id='sort_span' class='ui-icon ui-icon-arrowthick-2-n-s'></span>"
				+ marker.getTitle() + "<input type='hidden' name='city_title' value='"+marker.getTitle()+"' />"
				+"<a style='display:inline-block;' href='javascript:fnDelMarker("+ param +")'><i class='icon-user icon-remove'></i></a></li>";
	var hiddenSch_no = "<input name='sch_no' type='hidden' value='"+sch_no+"'/>";
	var hiddenCity_no = "<input name='maincity_no' type='hidden' value='"+cityNo+"'/>";
	$sortable.append(hiddenSch_no);
	$sortable.append(hiddenCity_no);
	$sortable.append(liContents);
	$sortable.removeClass("sort");
	$sortable.addClass("sort");
}
	
//일정 삭제한 마커의 이미지를 기본이미지로 변환
function fnSetDefaultMarker(marker){
	marker.setImage(marker_origImg);
	var idx = 0;
	for(var i=0; i<marker_tot[0].length; i++){
		if(marker_tot[0][i].getTitle() == marker.getTitle()){
			idx = i;
			break;
		}
	}
	marker_tot[1][idx] = false;
}

//marker_arr 에서 타이틀로 검색, 해당 인덱스 반환
function fnGetIndex(title){
	var index = 0;
	for(var i=0; i<marker_arr.length; i++){
		if(marker_arr[i].getTitle() == title){
			index = i;
			return index;
		}
	}
}

//순서 업데이트 함수
function fnUpdateQueue(array){
	var size = array.length;
	var $sort_li = $("#sortable li");
	var text = null;
	var tempArr = new Array();
	for(var i=0; i<size; i++){
		text = $sort_li.eq(i).text();
		tempArr.push(text);
	}
	
	var idx = 0;	// marker_arr에서 바뀔 값의 새 index
	for(var i=0; i<size; i++){
		if(array[i].getTitle() != tempArr[i]){
			idx = fnGetIndex(tempArr[i]);
			
			// 스왑
			marker_temp = array[i];
			array[i] = array[idx];
			array[idx] = marker_temp;
		}
	}
	
	fnSetPoly();
	fnSetMarkerImage(array);
}

//제거 함수
function fnDelMarker(title){
	var index = fnGetIndex(title);
	
	fnSetDefaultMarker(marker_arr[index]);
	$("#sort_li_" + title).remove();
	marker_arr.splice(index, 1);
	fnSetMarkerImage(marker_arr);
	fnSetPoly();
}

//polyline 세팅 함수
function fnSetPoly(){
	var size = marker_arr.length;
	var resultPaths = [];
	if(size > 1){
		for(var i=0; i<size; i++){
			resultPaths[i] = marker_arr[i].getPosition();
		}
	}
	poly.setPath(resultPaths);
	poly.setMap(map);
}

//마커 번호 이미지 저장
function fnSetMarkerImage(marker_arr){
	var size = marker_arr.length;
	for(var i=0; i<size; i++){
		var imageSrc = 'http://192.168.20.112:8080/Railring_v18/images/marker_number_pink.png', // 마커 이미지 url, 스프라이트 이미지를 씁니다
			imageSize = new daum.maps.Size(36, 37),  // 마커 이미지의 크기
			imgOptions =  {
				spriteSize : new daum.maps.Size(36, 691), // 스프라이트 이미지의 크기
				spriteOrigin : new daum.maps.Point(0, (i*46)+10), // 스프라이트 이미지 중 사용할 영역의 좌상단 좌표
				offset: new daum.maps.Point(13, 37) // 마커 좌표에 일치시킬 이미지 내에서의 좌표
			},
			markerImage = new daum.maps.MarkerImage(imageSrc, imageSize, imgOptions);
		marker_arr[i].setImage(markerImage);
	}    
}

//check
var last_infowindow2 = null,
	last_infowindow3 = null;

//********************** 160619 변수 추가분 끝 ***************************************

$.getJSON("json/city.json",function (data){
	$(data.positions).map(function(i, position) {
		if(position.no == cityNo){
			cityTitle = position.title;
			mapLat = position.station_lat;
			mapLng = position.station_lng;
		}
	});
}).done(function() {
	// 마커를 클릭했을 때 해당 장소의 상세정보를 보여줄 커스텀오버레이입니다
	var placeOverlay = new daum.maps.CustomOverlay({zIndex : 1}),
		contentNode = document.createElement('div'), // 커스텀 오버레이의 컨텐츠 엘리먼트 입니다 
		markers = [], // 마커를 담을 배열입니다
		currCategory = ''; // 현재 선택된 카테고리를 가지고 있을 변수입니다

	var mapContainer = document.getElementById('map'), // 지도를 표시할 div 
		mapOption = {
			center : new daum.maps.LatLng(mapLat, mapLng), // 지도의 중심좌표
			level : 9
			// 지도의 확대 레벨
		};

	// 지도를 생성합니다    
	map = new daum.maps.Map(mapContainer, mapOption);

	//관광명소
	$.getJSON("json/" + cityTitle + "_Loc2.json", function(data) {
		$(data.locations).each(function(i, location) {
			//리스트출력시 html태그
			//기존코드
			//06.21 수정
// 			var itemStr = '<div class="info" onclick="listClickEvent('+location.lng+','+location.lat+')">' + '<h5>'+
// 						 location.title +'</h5>'
// // 						'<a href="#" data-toggle="modal" data-target="#myModal" onclick="listClickEvent('+location.title+')">'+ location.title + '</a>'+'</h5>'
// 					+ ' <span>' + location.lat
// 					+ '</span><br/>' + '  <span>'
// 					+ location.lng + '</span><br/>'
// 					+ '</div>';

			var itemStr =  '<li class="media" onclick="listClickEvent2('+location.lng+','+location.lat+')">'+
							'<a class="pull-left"><img class="media-object" src="./images/placeImg/'+location.tel+'.jpg"></a>'+
							'<div class="media-body">'+
							'<h6 class="media-heading">'+location.title_kor+'<a href="#" onclick="listClickEvent('+location.lng+','+location.lat+')"><i class="icon-info-sign"></i></a></h6>'+
							'<div style="font-size: 8pt;">'+location.addr+'<div>'
							'</div>'+
							'</li>';

			$('#placesList').append(itemStr);
		});
		//시작시 마커
		$(data.locations).map(function(i, location) {
			var marker = new daum.maps.Marker({
				map : map,
				title : location.title,
				position : new daum.maps.LatLng(location.lat, location.lng)
			});
			
			// 마커 기본이미지 저장
			if(i == 0){
       			marker_origImg = marker.getImage();
       		}
			
			//마커를 배열에 담아둠
			marker_tot[0].push(marker);
			marker_tot[1].push(false);
			//뽑아올 길이를 알기위해서 각각 다른배열에 저장
			markerLoc.push(marker);
			//로그창으로 확인
			console.log(marker_tot[0].length);
			
			fnAddEvent(marker_tot[0]);
		});
		//관광지 탭 클릭이벤트
		$("#loc").click(function() {
			insertMarker(markerLoc);
			removeMarker(markerHot);
			removeMarker(markerRes);
			insertMarker(marker_arr);
			
			fnCloseLastIw();
		});
	});
	
	//숙박업소
	$.getJSON("json/" + cityTitle + "_Hot2.json", function(data) {
		//placesList의 childnode 삭제
		$(data.motels).each(function(i, motel) {
			//리스트출력시 html태그
			var itemStr2 =  '<li class="media" onclick="listClickEvent2('+motel.lng+','+motel.lat+')">'+
							'<a class="pull-left"><img class="media-object" src="./images/placeImg/'+motel.tel+'.jpg"></a>'+
							'<div class="media-body">'+
							'<h6 class="media-heading">'+motel.title_kor+'<a href="#" onclick="listClickEvent('+motel.lng+','+motel.lat+')"><i class="icon-info-sign"></i></a></h6>'+
							'<div style="font-size: 8pt;">'+motel.addr+'<div>'
							'</div>'+
							'</li>';

			$('#placesList2').append(itemStr2);
		});
		
		$(data.motels).map(function(i, motel) {
			var marker = new daum.maps.Marker({
				map : map,
				title : motel.title,
				position : new daum.maps.LatLng(motel.lat, motel.lng)
			});
			//마커를 배열에 담아둠
			marker_tot[0].push(marker);
			marker_tot[1].push(false);
			//뽑아올 길이를 알기위해서 각각 다른배열에 저장
			markerHot.push(marker);
			//로그창으로 확인
			console.log(marker_tot[0].length);
			fnAddEvent(marker_tot[0]);
		});
		//숙박업소 탭 클릭이벤트        
		$("#hot").click(function() {
			insertMarker(markerHot);
			removeMarker(markerLoc);
			removeMarker(markerRes);
			insertMarker(marker_arr);
			
			fnCloseLastIw();
		});
	});

	//맛집
	$.getJSON("json/" + cityTitle + "_Res2.json", function(data) {
		//placesList의 childnode 삭제
		$(data.restaurant).each(function(i, res) {
			//리스트출력시 html태그
			var itemStr3 =  '<li id="check" class="media" onclick="listClickEvent2('+res.lng+','+res.lat+')">'+
							'<a class="pull-left"><img class="media-object" src="./images/placeImg/'+res.tel+'.jpg"></a>'+
							'<div class="media-body">'+
							'<h6 class="media-heading">'+res.title_kor+'<a href="#" onclick="listClickEvent('+res.lng+','+res.lat+')"><i class="icon-info-sign"></i></a></h6>'+
							'<div style="font-size: 8pt;">'+res.addr+'<div>'
							'</div>'+
							'</li>';
			$('#placesList3').append(itemStr3);
		});

		
		$(data.restaurant).map(function(i, res) {
			var marker = new daum.maps.Marker({
				map : map,
				title : res.title,
				position : new daum.maps.LatLng(res.lat, res.lng)
			});
			//마커를 배열에 담아둠
			marker_tot[0].push(marker);
			marker_tot[1].push(false);
			//뽑아올 길이를 알기위해서 각각 다른배열에 저장
			markerRes.push(marker);
			//로그창으로 확인
			console.log(marker_tot[0].length);
			fnAddEvent(marker_tot[0]);
		});

		//맛집 탭 클릭이벤트
		$("#res").click(function() {
			insertMarker(markerRes);
			removeMarker(markerHot);
			removeMarker(markerLoc);
			insertMarker(marker_arr);
			
			fnCloseLastIw();
		});
		//마우스 온오버 css변경
		$(".media").mouseover(function(){
// 			alert("a");
			$(this).css("background-color","#f2f2f2");
		});
		$(".media").mouseout(function(){
			$(".media").css("background-color","#ffffff");
		});
	});
});

//리스트클릭시 상세정보 모달
function listClickEvent(loc,lat){
	$.getJSON("json/"+cityTitle+"_Res2.json", function(data) {
		$(data.restaurant).each(function(i, res) {
			if(lat==res.lat){	
				$('#detailImg_1').remove();
				$('#detailTitle_1').remove();
				$('#detailAddr_1').remove();
				$('#detailTel_1').remove();
				$('#detailIntroduction_1').remove();
				$('#detailImg').append('<img id="detailImg_1" src ="./images/placeImg/'+res.tel+'.jpg"/>');
				$('#detailTitle').append('<p id="detailTitle_1">'+res.title+'</p>');
				$('#detailAddr').append('<p id="detailAddr_1">'+res.addr+'</p>');
				$('#detailTel').append('<p id="detailTel_1">'+res.tel+'</p>');
				$('#detailIntroduction').append('<p id="detailIntroduction_1">'+res.introduction+'</p>');
			}
		});	
	});
	$.getJSON("json/"+cityTitle+"_Hot2.json", function(data) {
		$(data.motels).each(function(i, motel) {
			if(lat==motel.lat){	
				$('#detailImg_1').remove();
				$('#detailTitle_1').remove();
				$('#detailAddr_1').remove();
				$('#detailTel_1').remove();
				$('#detailIntroduction_1').remove();
				$('#detailImg').append('<img id="detailImg_1" src ="./images/placeImg/'+motel.tel+'.jpg"/>');
				$('#detailTitle').append('<p id="detailTitle_1">'+motel.title+'</p>');
				$('#detailAddr').append('<p id="detailAddr_1">'+motel.addr+'</p>');
				$('#detailTel').append('<p id="detailTel_1">'+motel.tel+'</p>');
				$('#detailIntroduction').append('<p id="detailIntroduction_1">'+motel.introduction+'</p>');;
			}
		});	
	});
	$.getJSON("json/"+cityTitle+"_Loc2.json", function(data) {
		$(data.locations).each(function(i, location) {
			if(lat==location.lat){	
				$('#detailImg_1').remove();
				$('#detailTitle_1').remove();
				$('#detailAddr_1').remove();
				$('#detailTel_1').remove();
				$('#detailIntroduction_1').remove();
				$('#detailImg').append('<img id="detailImg_1" src ="./images/placeImg/'+location.tel+'.jpg"/>');
				$('#detailTitle').append('<p id="detailTitle_1">'+location.title+'</p>');
				$('#detailAddr').append('<p id="detailAddr_1">'+location.addr+'</p>');
				$('#detailTel').append('<p id="detailTel_1">'+location.tel+'</p>');
				$('#detailIntroduction').append('<p id="detailIntroduction_1">'+location.introduction+'</p>');
			}
		});	
	});
	$("#myModal").modal('show');
}

function listClickEvent2(lng,lat){
	map.setCenter(new daum.maps.LatLng(lat,lng));
}

//모든 마커에 이벤트 등록 함수
function fnAddEvent(markers){
	$(markers).each(function(index){
		var marker = this;
		var map = this.getMap();
		
		//마커에 커서가 오버됐을 때 마커 위에 표시할 인포윈도우를 생성합니다
		var iwContent = '<div style="padding:5px;">'+marker.getTitle()+'</div>'; // 인포윈도우에 표출될 내용으로 HTML 문자열이나 document element가 가능합니다
		//등록 안되어 있을떄scheduleDetail.action?cmd=DETAIL
		var iwContent2 = '<div style="padding:5px;"><a href="#" style="text-align: center;">'+marker.getTitle()+' 등록됨</a></div>'; // 인포윈도우에 표출될 내용으로 HTML 문자열이나 document element가 가능합니다
		//등록 되어 있을때
		var iwContent3 = '<div style="padding:5px;text-align:center;">'
						+'<a href="#" id="register">'+marker.getTitle()+'등록하기 </a>'
						+'<a href="#" onclick="listClickEvent('+marker.getPosition().getLng()+','+marker.getPosition().getLat()+')"><i class="icon-info-sign"></i></a>&nbsp;&nbsp;&nbsp;&nbsp;'
						+'</div>'; // 인포윈도우에 표출될 내용으로 HTML 문자열이나 document element가 가능합니다
		var iwRemovable = true;
		//인포윈도우를 생성합니다
		var infowindow = new daum.maps.InfoWindow({
			content : iwContent
		});
		var infowindow2 = new daum.maps.InfoWindow({
		    content : iwContent2,
		    removable : iwRemovable
		});
		var infowindow3 = new daum.maps.InfoWindow({
		    content : iwContent3,
		    removable : iwRemovable
		});
		
		daum.maps.event.addListener(marker, "click", function() {
			// 닫지 않은 인포윈도우가 있을 경우 닫아줌
			fnCloseLastIw();
				
	   		// 지도의 중심좌표 재설정
	        map.setCenter(new daum.maps.LatLng(marker.getPosition().getLat(), marker.getPosition().getLng()));
	   		
			// 현재 마커의 플래그 인덱스 찾아옴
			var idx = 0;
			for(var i=0; i<marker_tot[0].length; i++){
				if(marker_tot[0][i].getTitle() == marker.getTitle()){
					idx = i;
					break;
				}
			}
				
			if(!marker_tot[1][idx]){
				infowindow3.open(map, marker);
				last_infowindow3 = infowindow3;
				
				$("#register").click(function(){
					for(var i=0; i<marker_arr.length; i++){
						if(marker_arr[i].getTitle() == marker.getTitle()){
							return;
						}
					}
					marker_arr.push(marker);
					fnPlanMarkers(marker);
						
					var path = poly.getPath();
					path.push(marker.getPosition());
					poly.setPath(path);
					poly.setMap(map);
						
					infowindow3.close();
						
					fnSetMarkerImage(marker_arr);
						
					marker_tot[1][idx] = true;
				});
			}
			else{
				infowindow2.open(map, marker);
				last_infowindow2 = infowindow2;
			}
		});
		
		//마커에 마우스오버 이벤트를 등록합니다
		daum.maps.event.addListener(marker, 'mouseover', function() {
		// 마커에 마우스오버 이벤트가 발생하면 인포윈도우를 마커위에 표시합니다
			infowindow.open(map, marker);
		});
	
		//마커에 마우스아웃 이벤트를 등록합니다
		daum.maps.event.addListener(this, 'mouseout', function() {
		// 마커에 마우스아웃 이벤트가 발생하면 인포윈도우를 제거합니다
			infowindow.close();
		});
	});
}

function fnCloseLastIw(){
	// 닫지 않은 인포윈도우가 있을 경우 닫아줌
	if(last_infowindow2!=null){	
		last_infowindow2.close();	
	}
	if(last_infowindow3!=null){	
		last_infowindow3.close();	
	}
}

//마커를 배열에 담아서 클릭이벤트시 삭제해주는 메서드
function removeMarker(mkList) {
	for (var i = 0; i < mkList.length; i++) {
		mkList[i].setMap(null);
		//시작위치 i요소를 시작점으로 , 1개의 요소 삭제 
	}
}
//마커 보이기
function insertMarker(mkList) {
	for (var i = 0; i < mkList.length; i++) {
		mkList[i].setMap(map);
	}
}
//전체일정: 중심좌표변경
function panTo(lat, lng) {
	// 이동할 위도 경도 위치를 생성합니다 
	var moveLatLon = new daum.maps.LatLng(lat, lng);
	// 지도 중심을 부드럽게 이동시킵니다
	// 만약 이동할 거리가 지도 화면보다 크면 부드러운 효과 없이 이동합니다
	map.panTo(moveLatLon);
}

//jQueryUI sortable
$(function() {
	$("#sortable").sortable({
		stop : function(event, ui) {
			fnUpdateQueue(marker_arr);
		}
	});
	$("#sortable").disableSelection();
});
</script>
<script   src="http://192.168.20.112:8080/Railring_v18/bootstrap/js/bootstrap.min.js"></script>
</body>
</html>