<%@ page import="java.util.List"%>
<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
<link rel="stylesheet" href="http://192.168.20.112:8080/Railring_v18/bootstrap/css/jquery.timepicker.css" />
<style>
#detailImg_1{
	width: auto;
    height: auto;
}
<!-- 16.06.21 추가 css -->
.media-object{display: block;min-width: 100%;min-height: 100%;}
.pull-left{display: block;overflow: hidden;height: 60px;width: 60px;}
<!-- 16.06.17 추가 css -->
.sort { list-style-type: none; margin: 0; padding: 0; width: 60%; }
.sort li { width:150px; margin: 0 3px 3px 3px; padding: 0.4em; padding-left: 1.5em; font-size: 1.4em; height: 18px; }
.sort li span { position: absolute; margin-left: -1.3em; }
.sort li a { position: absolute; display:inline-block; }
.sort li .del { left: 190px; }
.sort li .train { left: 170px; }
<!-- ----------------------------------원본css--------------------------------------------  -->
.media-object{width:64px;height:64px;}
#delete_markers {position: absolute;top: 230px;left: 73%;z-index: 5;background-color: #fff;padding: 5px;border: 1px solid #999;}
.nav-li {width:172px;text-align: center;}
#navDiv{display: table; margin-left: auto; margin-right: auto; width:940px; align:center}	
#timeTableDiv{background-color: BurlyWood;height:25%;vertical-align: bottom;}
.mapDiv{ height:75%;overflow-y: hidden;vertical-align: top;}	
#dragbar{background-color:black;height: 10px;width: 100%;cursor: row-resize;vertical-align: middle;}	
#ghostbar{height:3px;width: 100%;background-color:#000;opacity:0.5;position:absolute;cursor: row-resize;z-index:999}
/* #map{visibility: hidden;} */
</style>
<script>
var sch_no = "";
// 새로 만들기 : 이때 sch_no 정해짐
	function newSchedule(){
		 $.ajax({
			   type : "POST",
			   url : "schedule.action?cmd=NEW",
			   cache : false,
			   data : null,
			   success : function (data) {
				   data = $.parseJSON(data);
				   sch_no = data.sch_no;
				   alert("새 스케쥴 만들기 시작!");
			   },
			   fail : function (e) {
				   alert('스케줄 등록에 실패하였습니다.');
			   }
			});
	}


// 스케줄저장
	function addForm(){
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
	
	// 스케쥴 리스트
	$(window).load(function(){
		$.ajax({
			   type : "POST",
			   url : "schedule.action?cmd=LIST",
			   cache : false,
			   data : null,
			   success : function (data) {
				   data = $.parseJSON(data);
				   
				   var str = "";
				   var $scheduleList = $("#scheduleList");
				   $scheduleList.empty();
				   $scheduleList.append("<option selected='selected'>--내 스케줄 불러오기--</option>");
				   $(data).each(function(i){
					   str = "<option value='"+data[i].schedule_no+"'>"+data[i].schedule_no+"번째 스케쥴</option>";
					$scheduleList.append(str);
				   });
			   },
			   fail : function () {
				   alert('등록된 스케줄이 없습니다.');
			   }
			});
	});
	
	// marker_tot에서  title이 같은 마커 리턴
	function getMarkerFromTot(title){
		for(var i=0; i<marker_tot[0].length; i++){
			if(marker_tot[0][i].getTitle() == title){
				return marker_tot[0][i];
			}
		}
	}
	// 스케쥴 리스트(클릭)
	function getScheduleList(){
		$.ajax({
			   type : "POST",
			   url : "schedule.action?cmd=LIST",
			   cache : false,
			   data : null,
			   success : function (data) {
				   data = $.parseJSON(data);
				   
				   var str = "";
				   var $scheduleList = $("#scheduleList");
				   $scheduleList.empty();
				   $scheduleList.append("<option selected='selected'>--내 스케줄 불러오기--</option>");
				   $(data).each(function(i){
					   str = "<option value='"+data[i].schedule_no+"'>"+data[i].schedule_no+"번째 스케쥴</option>";
					$scheduleList.append(str);
				   });
			   },
			   fail : function () {
				   alert('등록된 스케줄이 없습니다.');
			   }
			});
	}
	
	// 스케쥴 불러오기
	function loadSchedule(){
	   var $scheduleList = $("#scheduleList");
	   var params={
			   "sch_no":$("#scheduleList option:selected").val()
	   }
	   sch_no=$("#scheduleList option:selected").val();
		$.ajax({
			type : "POST",
			url : "schedule.action?cmd=LOAD",
			cache : false,
			data : params,
			success : function (data) {
				data = $.parseJSON(data);
				console.dir(data);
				// 현재 등록된 마커 제거
				for(var i=0; i<marker_arr.length; i++){
					// 지울 마커 이미지를 기본이미지로 바꾸고, 해당 플래그 false로 변환
					fnSetDefaultMarker(marker_arr[i]);
					// 지울 마커 sortable리스트에서 제거
 					$("#sort_li_" + marker_arr[i].getTitle()).remove();
				}
				// 등록배열에서 마커 제거
				marker_arr.splice(0, marker_arr.length);
				// 등록된 열차정보 제거
				train_arr.splice(0, marker_arr.length);
				
				// 선택한 스케쥴로 마커 등록
				for(var i=0; i<data.citys.length; i++){
					// title값으로 마커를 가져옴
					var tempMarker = getMarkerFromTot(data.citys[i].city_title);
					// 등록배열에 마커 추가
					marker_arr.push(tempMarker);
					// 열차정보 추가
					train_arr.push(new Train(tempMarker.getTitle(), 0, 0, 0, false));
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
	
	// 시간표 불러오기
	function loadTimeTable(){
		   var $timeTable = $("#timeTable");
		   var params={
				   "sch_no":sch_no,
		   }
			$.ajax({
				   type : "POST",
				   url : "schedule.action?cmd=TIMETABLE",
				   cache : false,
				   data : params,
				   success : function (data) {
					   data = $.parseJSON(data);
					  console.dir(data);
					  
					  for(var i=0; i<data.length; i++){
						  $timeTable.append("<tr><td>"+(i+1)+"번째 - "+data[i].city_title_kor+"</td></tr>");
						  for(var j=0; j<data[i].infos.length; j++){
							  var cityTitle="'"+data[i].city_title+"'";
							  var Title="'"+data[i].infos[j].info_title+"'";
							  
							  $timeTable.append('<tr><td>&nbsp;&nbsp;&nbsp;&nbsp;'+(j+1)+'번째 -'+data[i].infos[j].info_title_kor+'<a href="javascript:listClickEvent('+Title+','+cityTitle+')">'+'<i class="icon-info-sign"></i></a></td></tr>');
						  }
					  }
					  
				   },
				   fail : function (e) {
					   alert('등록된 스케줄이 없습니다.');
				   }
				});
		}
	
	//스케쥴 삭제
	function deleteSchedule(){
		alert("스케쥴 삭제");
			 var params={
					   "sch_no":sch_no,
			   }
			$.ajax({
				   type : "POST",
				   url : "schedule.action?cmd=DELETE",
				   cache : false,
				   data : params,
				   success : function (data) {
					   data = $.parseJSON(data);
					   console.dir(data);
					   if(data.result) {
							alert('스케줄 삭제에 성공하였습니다.');
							for(var i=0; i<marker_arr.length; i++){
								fnSetDefaultMarker(marker_arr[i]);
								$("#sort_li_" + marker_arr[i].getTitle()).remove();
							}
							marker_arr.splice(i, marker_arr.length);
							train_arr.splice(i, marker_arr.length);
							poly.setPath([]);
							poly.setMap(map);
					   } else {
						   alert(data.message);
					   }
				   },
				   fail : function (e) {
					   alert('스케줄 삭제에 실패하였습니다.');
				   }
			});
	}
</script>
</head>
<body>
<jsp:include page="../../../bannerNavBar.jsp"></jsp:include>
<div id="GapForNavBar" style="height:45px;"></div>
<div class="container-fluid">
   <div class="row-fluid">
				<!------------------- 스케줄러 사이드 메뉴 바 ------------------->
      <div class="span2">
  		<div align="right">
         <ul class="nav nav-tabs nav-stacked" style="width:200px; text-align:left">
         	<h2>Scheduling</h2>
         	<li id="select_schedule">
				<select name="scheduleList" id="scheduleList" style="margin-bottom: 20px; width:200px" onchange="loadSchedule()" onclick="getScheduleList()">
					<option>--내 스케줄 불러오기--</option>
				</select>
			</li>
            <li><a href="#" onclick="newSchedule()">새 스케줄 만들기</a></li>
            <li><a href="#" onclick="deleteSchedule()">스케줄 삭제</a></li>
            <li><a href="#" onclick="myPlace()">현재 위치</a></li>
            <li style="margin-bottom: 20px"><a href="#" onclick="addForm()">저장하기</a></li>
            <div class="input-append" align="center">
            	<input type="text" class="span10" placeholder="Search" id="city_search"/>
				<a class="btn" href="#" onclick="citySearch()"><i class="icon-search"></i></a>
			</div>
         </ul>
		</div>
      </div>
				<!------------------- 지도 출력 ------------------->
      <div class="span8" id="mapSize" style="height: 1020px">
<!--       	<div id="mapHidden" style="text-align: center;"><h3>"새 스케쥴 만들기" 또는 "내 스케쥴 불러오기"를 눌러주세요</h3></div> -->
      	<div id="sortable_div" style="position:absolute; top:5%; left:20%; z-index:5;">
			<form id="scheduleForm" action="schedule.action?cmd=POST" method="post">
				<ul id="sortable"></ul>
			</form>
      	</div>
      	<div id="map" class="mapDiv" style="width:100%; align:center;"></div>
		<div id="dragbar"></div>
		<div id="timeTableDiv" style="background-color: skyblue; border-width:3px;border-style:solid;border-color:black; overflow-y: scroll;">
			<input type="button" value="새로고침" onclick="loadTimeTable()"/>
<!--  			<h1 id="console" style="color: black; text-align: center"></h1> -->
 			<h4 style="text-align: center;">시간표</h4>
 			<table id="timeTable" class="table" align="center"></table>
		</div>
      </div>
				<!------------------- 사이드 탭 메뉴(오른쪽) 관광지 정보 ------------------->      
      <div class="span2">
      		<div class="tabbable" id="sideMenu">
				<ul class="nav nav-tabs" id="category">
					<li id="AT4" class="active" data-order="0"><a href="#tab1" data-toggle="tab">명소</a></li>
					<li id="FD6" data-order="1"><a href="#tab2" data-toggle="tab">숙박</a></li>
					<li id="AD5" data-order="2"><a href="#tab3" data-toggle="tab">맛집</a></li>
				</ul>
				<div class="tab-content">
						<div class="tab-pane active" id="tab1">
							<!-- li태그들어갈곳  -->
							<ul id="placesList"></ul>
						</div>
						<div class="tab-pane" id="tab2">
							<ul id="placesList2"></ul>
						</div>
						<div class="tab-pane" id="tab3">
							<ul id="placesList3"></ul>
						</div>
					</div>
			</div>
	  </div>
   </div>
</div>

<!-- 열차 예약 모달창 -->
<div id="train_modal" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-header">
		<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
		<h3 id="myModalLabel">열차 시간표 검색</h3>
	</div>
	<div class="modal-body" align="center">
		<input type="hidden" id="train_title"/>
		<div>
		출발역&nbsp;<input class="span1" id="train_depart" type="text" disabled/>
		도착역&nbsp;<input class="span1" id="train_arrival" type="text" disabled/>
		<br/>
		출발일자&nbsp;<input type="text" style="width:80px;" name="train_date" id="datepicker"/>
		출발시간&nbsp;<input type="text" style="width:80px;" name="train_time" id="timepicker"/>
		<button class="btn" onclick="fnSearchTrain()">검색</button>
		</div>
		<br/>
		<select id="train_select" style="width:400px"></select>
	</div>
	<div class="modal-footer">
		<button class="btn btn-primary" onclick="fnSetTrain()">Save changes</button>
		<button class="btn" data-dismiss="modal" aria-hidden="true">Close</button>
	</div>
</div>

<!-- 리스트 아이콘(여행지 상세정보) -->
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
	<!------------------- Footer 출력 ------------------->
   <jsp:include page="../../../footer.jsp"></jsp:include>
<script src="http://192.168.20.112:8080/Railring_v18/bootstrap/js/jquery.timepicker.min.js"></script>
<script>
var i = 0;
var dragging = false;

var obj = $("#mapSize").offset();
var obj2 = $("#dragbar").offset();
var obj3 = $("#timeTableDiv").offset();

$('#console').text("dragbar : " + obj2.left + ", " + obj2.top + " timeTableDiv : " + obj3.left + ", " + obj3.top);
//	화면 분할 jquery
$('#dragbar').mousedown(function(e){
       e.preventDefault();
       dragging = true;
       
       var timeTableDiv = $('#timeTableDiv');
       var ghostbar = $('<div>',
                        {id:'ghostbar',
                         css: {
                                width: timeTableDiv.outerWidth(),
                                top: timeTableDiv.offset().top,
                                left: timeTableDiv.offset().left
                               }
                        }).appendTo('body');
       
        $(document).mousemove(function(e){
          ghostbar.css("top",e.pageY+2);
          
          var percentage = ((e.pageY - 212)/ $('#mapSize').height()) * 100;
          var timeTableDivPercentage = 100 - percentage;
          $('#console').text("y좌표 : " + e.pageY + ", " + "% : " + percentage + "%, " + timeTableDivPercentage +"%");
       });
    });
	
   $(document).mouseup(function(e){
       if (dragging) 
       {
    	   var percentage = ((e.pageY - 212)/ $('#mapSize').height()) * 100;
           var timeTableDivPercentage = 100 - percentage;
           
           if((percentage < 10) && (timeTableDivPercentage > 90)){
        	   percentage = 10;
        	   timeTableDivPercentage = 90;
           }else if((timeTableDivPercentage < 10) && (percentage > 90)){
        	   timeTableDivPercentage = 10;
        	   percentage = 90;
           }
           $('.mapDiv').css("height", percentage + "%");
           $('#timeTableDiv').css("height",timeTableDivPercentage + "%");
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
// 마커를 클릭했을 때 해당 장소의 상세정보를 보여줄 커스텀오버레이입니다
var placeOverlay = new daum.maps.CustomOverlay({zIndex:1}), 
    contentNode = document.createElement('div'), // 커스텀 오버레이의 컨텐츠 엘리먼트 입니다 
    markers = [], // 마커를 담을 배열입니다
    currCategory = ''; // 현재 선택된 카테고리를 가지고 있을 변수입니다
    
var mapContainer = document.getElementById('map'), // 지도를 표시할 div 
    mapOption = {
        center: new daum.maps.LatLng(36.49409095332657, 127.8616176737458), // 지도의 중심좌표
        level: 12 // 지도의 확대 레벨
    };  

var imageSrc = "http://i1.daumcdn.net/localimg/localimages/07/mapapidoc/markerStar.png"; 
var imageSize = new daum.maps.Size(30, 40); 
var markerLocImage = new daum.maps.MarkerImage(imageSrc, imageSize); 

// 지도를 생성합니다    
var map = new daum.maps.Map(mapContainer, mapOption); 

function myFunction(cityNo){
	var url = 'schedule.action?cmd=DETAIL&cityNo='+cityNo+'&sch_no='+sch_no;
	window.open(url, "세부일정", "width=1000, height=750, toolbar=no, menubar=no, scrollbars=no, resizable=no");
}
//***************** 2016-06-17 추가 부분 시작**********************
//polyline 관련 변수 by 소영
var poly = new daum.maps.Polyline({
	endArrow:true,
	strokeWeight: 2, // 선의 두께 입니다
    strokeColor: '#FF0000', // 선의 색깔입니다
    strokeOpacity: 0.7, // 선의 불투명도 입니다 1에서 0 사이의 값이며 0에 가까울수록 투명합니다
    strokeStyle: 'dash' // 선의 스타일입니다
});

//등록 관련 변수 by 수항
var marker_count = 0,		// 등록된 마커 포인터
	marker_arr = [],		// 등록된 마커를 저장할 배열
	marker_temp = null,		// 등록된 마커를 스왑 할 템프
	marker_origImg = null;	// 기본 이미지 저장 변수
	
//marker 2차원 배열 선언 by 수항
var	marker_tot = new Array();
	marker_tot[0] = new Array();	// 모든 마커
	marker_tot[1] = new Array();	// 모든 마커에 따른 플래그
	marker_tot[2] = new Array();	// 모든 마커의 한글명

//열차 시간표 저장 배열 by 수항
var train_arr = []; // marker_arr와 index를 같이한다.	

//열차 시간표 저장 객체 by 수항
Train = function(title, date, time, dep, arr, flag){
	this.title = title;	// marker의 타이틀(도시영문명)
	this.date = date;	// 열차출발일자
	this.time = time;	// 열차출발시각
	this.dep = dep;		// 출발역
	this.arr = arr;		// 도착역
	this.flag = flag;	// 열차시간 저장여부(true=저장)
}
Train.prototype = {
	setValue: function(newTitle, newDate, newTime, newDep, newArr, newFlag){
		this.title = newTitle;
		this.date = newDate;
		this.time = newTime;
		this.dep = newDep;
		this.arr = newArr;
		this.flag = newFlag;
	},
	
	getTitle: function(){return this.title;},
	getDate: function(){return this.date;},
	getTime: function(){return this.time;},
	getDep: function(){return this.dep;},
	getArr: function(){return this.arr;},
	getFlag: function(){return this.flag;}
}

// 마커 등록 함수 by 수항, 소영
function fnPlanMarkers(marker){
	var $sortable = $("#sortable");
	var param = '"' + marker.getTitle() + '"';
	var liContents = "<li id='sort_li_"+ marker.getTitle() +"' class='ui-state-default' style='list-style:none;'>"
						+ "<span id='sort_span' class='ui-icon ui-icon-arrowthick-2-n-s'></span>"
						+ marker.getTitle() + "<input type='hidden' name='city_title' value='"+marker.getTitle()+"' />"
						+ "<a class='train' id='train_"+marker.getTitle()+"' data-toggle='tooltip' data-placement='top' data-original-title='기차' href='javascript:fnShowTrain("+ param +")'>"
						+ "<i class='ui-icon ui-icon-circle-zoomin'></i></a>"
						+ "<a class='del' id='del_"+marker.getTitle()+"' data-toggle='tooltip' data-placement='top' data-original-title='삭제' href='javascript:fnDelMarker("+ param +")'>"
						+ "<i class='icon-user icon-remove'></i></a></li>";
	var hiddenSch_no = "<input name='sch_no' type='hidden' value='"+sch_no+"'/>";
	$sortable.append(hiddenSch_no);
	$sortable.append(liContents);
	$sortable.removeClass("sort");
	$sortable.addClass("sort");
	$("#train_" + marker.getTitle()).tooltip();
	$("#del_" + marker.getTitle()).tooltip();
}

//선택한 기차 시간표 저장 by 수항
function fnSetTrain(){
	if(true){
		//기존값이 있으면 띄워줌
	}
	else{
		//없으면 안띄워줌
	}
	var title = $("#train_title").val(),
		dep = $("#train_depart").val(),
		arr = $("#train_arrival").val(),
		datepick = $("#datepicker").val(),
		timepick = $("#timepicker").val();
	
	var idx = fnGetIndex(title);
	train_arr[idx].setValue(title, datepick, timepick, dep, arr, true);
	
	$("#train_modal").modal("hide");
}

// 도시이름 -> 역 코드로 변경 함수 by 수항
function getPlaceId(title){
	var result = "";
	if(title == "서울"){
		result = "NAT010032";
	}
	else if(title == "전주"){
		result = "NAT040257";
	}
	else if(title == "여수"){
		result = "NAT041993";
	}
	else if(title == "부산"){
		result = "NAT750046";
	}
	return result;
}

// 기차 시간 조회 함수 by 수항
function fnSearchTrain(){
	var title = $("#train_title").val();
	var depart = $("#train_depart").val();
	var arrival = $("#train_arrival").val();
	if(depart == null || depart == ""){
		alert("다음 여행지가 없는 경우, 열차정보 조회서비스를 제공하지 않습니다.");
		return;
	}
	
	var date = $("#datepicker").val();
	var time = $("#timepicker").val();
	
	if(date == null || date == ""){
		alert("출발일자를 입력하세요");
		return;
	}
	else if(time == null || time == ""){
		alert("출발시간을 입력하세요");
		return;
	}
	
	var param = "dep=" + getPlaceId(depart) + "&"
				+ "arr=" + getPlaceId(arrival) + "&"
				+ "date=" + date + "&"
				+ "time=" + time;
	var $select = $("#train_select");
	$select.children().remove();
	$.post(
		"train.action",
		param,
		function(result){
			var contents = null;
			var obj = jQuery.parseJSON( result );
			var $items = obj.result;
			$.each($items, function(i, item){
				if(item.item.dep == "no"){
					contents = "<option id='default' value='none'>결과가 없습니다.</option>";
				}
				else{
					if(i == 0){
						contents = "<option id='default' value='none'>열차를 선택하세요</option>";
						$select.append(contents);
					}
					contents = "<option id='opt" + i + "' value='"+ item.item.date + ":" + item.item.time +"'>일시: " + item.item.date + " / 시간: " + item.item.time + " 출발 / " + item.item.dep + "역 </option>";
				}
				$select.append(contents);
			});
			console.log("done");
		}
	);
}

// 기차 시간표 모달로 띄워주는 함수 by 수항
function fnShowTrain(title){
	$("#train_depart").val("");
	$("#train_arrival").val("");
	$("#train_title").val(title);
	$("#datepicker").val("");
	$("#timepicker").timepicker('setTime', '');
	$("#train_select").children().remove();
	
	var idx = fnGetIndex(title);
	if(idx < marker_arr.length-1){
		var departure = marker_arr[idx];
		var arrival = marker_arr[idx+1];
		
		var idx_depart = 0;
		var idx_arrival = 0;
		var checker = 0;
		for(var i=0; i<marker_tot[0].length; i++){
			if(marker_tot[0][i].getTitle() == departure.getTitle()){
				idx_depart = i;
				checker++;
			}
			else if(marker_tot[0][i].getTitle() == arrival.getTitle()){
				idx_arrival = i;
				checker++;
			}
			
			if(checker == 2){
				break;
			}
		}
		
		$("#train_depart").val(marker_tot[2][idx_depart]);
		$("#train_arrival").val(marker_tot[2][idx_arrival]);
	}
	
	$("#datepicker").datepicker(
		{
			dateFormat: "yymmdd",
			changeMonth: true,
			changeYear: true,
			minDate: "0",
			maxDate: "+1y",
		}		
	);
	$("#timepicker").timepicker(
		{
			'timeFormat': 'H:i',
			'scrollDefault': 'now'
		}		
	);
	
	if(train_arr[idx].getFlag() == true){
		var date = train_arr[idx].getDate();
		var time = train_arr[idx].getTime();
		var contents = "<option id='opt" + idx + "' value='"+ train_arr[idx].getDate() + ":" + train_arr[idx].getTime() +"'>일시: " + train_arr[idx].getDate() + " / 시간: " + train_arr[idx].getTime() + " 출발 / " + train_arr[idx].getDep() + "역 </option>";
		
		$("#datepicker").val(date);
		$("#timepicker").timepicker('setTime', time);
		$("#train_select").append(contents);
	}
	else{
		var contents = "<option id='default' value='none'>열차정보를 검색하세요</option>";
		$("#train_select").append(contents);
	}
	
	$("#train_modal").modal("show");
	
}
	
//일정 삭제한 마커의 이미지를 기본이미지로 변환 by 수항
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

//marker_arr 에서 타이틀로 검색, 해당 인덱스 반환 by 수항
function fnGetIndex(title){
	var index = 0;
	for(var i=0; i<marker_arr.length; i++){
		if(marker_arr[i].getTitle() == title){
			index = i;
			return index;
		}
	}
}

//순서 업데이트 함수 by 수항, 소영
function fnUpdateQueue(array){
	var size = array.length;
	var $sort_li = $("#sortable li");
	var text = null;
	var tempArr = new Array();
	for(var i=0; i<size; i++){
		text = $sort_li.eq(i).text();
		tempArr.push(text);
	}
	
	var train_temp = null;
	var idx = 0;	// marker_arr에서 바뀔 값의 새 index
	for(var i=0; i<size; i++){
		if(array[i].getTitle() != tempArr[i]){
			idx = fnGetIndex(tempArr[i]);
			
			// 스왑
			marker_temp = array[i];
			array[i] = array[idx];
			array[idx] = marker_temp;
			
			// 마커 순서대로 열차정보객체 스왑
			train_temp = train_arr[i];
			train_arr[i] = train_arr[idx];
			train_arr[idx] = train_temp;
		}
	}
	
	fnSetPoly();
	fnSetMarkerImage(array);
}

//제거 함수 by 수항, 소영
function fnDelMarker(title){
	var index = fnGetIndex(title);
	
	fnSetDefaultMarker(marker_arr[index]);
	$("#sort_li_" + title).remove();
	marker_arr.splice(index, 1);
	train_arr.splice(index, 1);
	fnSetMarkerImage(marker_arr);
	fnSetPoly();
}

//polyline 세팅 함수 by 소영
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
	
//마커 번호 이미지 저장 by 소영
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

//***************** 2016-06-17 추가 부분 끝 **********************

var last_infowindow2 = null,	// 마지막에 열었던 인포윈도우2
	last_infowindow3 = null;	// 마지막에 열었던 인포윈도우3

// **************************전체 일정 : 도시 마커 뿌리기 **************************		
//전체일정: json파일 가져온 후 해당 좌표에 마커 뿌리기
	$.getJSON("json/city.json",function (data){
		$(data.positions).map(function(i, position) {
    		var marker = new daum.maps.Marker({
    			map: map,
    			title: position.title,
    			position: new daum.maps.LatLng(position.lat, position.lng)
    		});
    		
       		// marker_tot : marker와 플래그를 이차원배열에 저장 by 수항
       		marker_tot[0].push(marker);
       		marker_tot[1].push(false);
       		marker_tot[2].push(position.title_kor);
   	   		// 기본 마커 이미지 저장 
       		if(i == 0){
       			marker_origImg = marker.getImage();
       		}
    		
    		//마커에 커서가 오버됐을 때 마커 위에 표시할 인포윈도우를 생성합니다
    		var iwContent = '<div style="padding:5px;">'+position.title_kor+'</div>'; // 인포윈도우에 표출될 내용으로 HTML 문자열이나 document element가 가능합니다
    		//등록 안되어 있을떄scheduleDetail.action?cmd=DETAIL
    		var iwContent2 = '<div style="padding:5px;"><a href="#" onclick="myFunction('+position.no+')" style="text-align: center;">'+position.title_kor+' 세부일정</a></div>'; // 인포윈도우에 표출될 내용으로 HTML 문자열이나 document element가 가능합니다
//     		var iwContent2 = '<div style="padding:5px;"><a href="#theModal" style="text-align: center;" data-toggle="modal">'+position.title+' 등록하기</a></div>';
    		//등록 되어 있을때
    		var iwContent3 = '<div style="padding:5px;"><a href="#theModal" id="register" style="text-align: center;" data-toggle="modal">'+position.title+' 등록하기</a></div>', // 인포윈도우에 표출될 내용으로 HTML 문자열이나 document element가 가능합니다
//     		var iwContent3 = '<div style="padding:5px;"><a href="#" onclick="myFunction()" style="text-align: center;" data-toggle="modal">'+position.title+' 세부일정</a></div>', // 인포윈도우에 표출될 내용으로 HTML 문자열이나 document element가 가능합니다
    			iwRemovable = true; // removeable 속성을 ture 로 설정하면 인포윈도우를 닫을 수 있는 x버튼이 표시됩니다
    		
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
    		
    		// 마커에 클릭이벤트를 등록합니다 by 소영
    		daum.maps.event.addListener(marker, 'click', function() {
       			// 닫지 않은 인포윈도우가 있을 경우 닫아줌
       			if(last_infowindow2!=null){	
       				last_infowindow2.close();	
       			}
       			if(last_infowindow3!=null){	
       				last_infowindow3.close();	
       			}    			
       			
    			// 지도의 중심좌표 재설정
    	        map.setCenter(new daum.maps.LatLng(position.lat, position.lng));
       			
       			// 현재 마커의 플래그 인덱스 찾아옴
       			var idx = 0;
       			for(var i=0; i<marker_tot[0].length; i++){
       				if(marker_tot[0][i].getTitle() == marker.getTitle()){
       					idx = i;
       					break;
       				}
       			}
       			
       			// 등록이 안됐을 경우 by 소영
       			if(!marker_tot[1][idx]){
       			   	// 마커 위에 인포윈도우를 표시합니다
       				infowindow3.open(map, marker);
       				last_infowindow3 = infowindow3;
       				
       				// 등록하기 클릭 시 폴리라인 생성
       				$('#register').click(
       			    	function(){
       			    		for(var i=0; i<marker_arr.length; i++){
       			    			if(marker_arr[i].getTitle() == marker.getTitle()){
       			    				return;
       			    			}
       			    		}
       			    		// 등록마커 배열에 마커 추가
       			    		marker_arr.push(marker);
       			    		// 등록마커의 열차시간 정보 객체 생성
       			    		train_arr.push(new Train(marker.getTitle(), 0, 0, 0, 0, false));
       			    		// 일정등록된 마커 띄워줌
       			    		fnPlanMarkers(marker);
       			    		// 일정등록된 마커에 polyline 연결
       			    		fnSetPoly();
       			    		// 인포윈도우2 닫기
       			    		infowindow3.close();
       			    	 	// 마커 이미지 변경(순서대로 라벨생성)
                            fnSetMarkerImage(marker_arr);
       			    		// 등록 시 해당 마커 플래그 true로 변환
       			    		marker_tot[1][idx] = true;
       			    	}	
       			    );
       			}
       			else{
       			   	//등록 되어 있을때
       				infowindow2.open(map, marker);
       				last_infowindow2 = infowindow2;
       			}
    			
       			// 다른 마커 클릭시 에러 발생으로 부산 클릭 시에만 리스트 출력해주는 임시 분기문
       			var title = position.title;
       			var city="'"+position.title+"'";
//        			if(title == "Busan" || title == "Jeonju" || title == "Yeosu"){
    	         //해당지역마커 클릭시
    	          $.getJSON("json/"+position.title+"_Loc2.json",function(data){
				//alert(data.locations.length);
    	                 //placesList의 childnode 삭제
    	                 $('#placesList').empty();
    	                //마커추가

    	                $(data.locations).each(function(i,location){
    	                   //리스트출력시 html태그
    	                   	var locationTitle="'"+location.title+"'";
							var itemStr =  '<li class="media">'+
							'<a class="pull-left"><img class="media-object" src="./images/placeImg/'+location.tel+'.jpg"></a>'+
							'<div class="media-body">'+
							'<h6 class="media-heading">'+location.title_kor+'<a href="javascript:listClickEvent('+locationTitle+','+city+')"><i class="icon-info-sign"></i></a></h6>'+
							'<div style="font-size: 8pt;">'+location.addr+'<div>'
							'</div>'+
							'</li>';

    	                   $('#placesList').append(itemStr);
    	                });

    	          }).done(function(){
	    	          $.getJSON("json/"+position.title+"_Hot2.json",function(data){
	    	                 //placesList의 childnode 삭제
	    	                 $('#placesList2').empty();
	    	                $(data.motels).each(function(i, motel){
	    	                   //리스트출력시 html태그
		    	              	var motelTitle="'"+motel.title+"'";
		    	                   //리스트출력시 html태그
								var itemStr2 =  '<li class="media">'+
								'<a class="pull-left"><img class="media-object" src="./images/placeImg/'+motel.tel+'.jpg"></a>'+
								'<div class="media-body">'+
								'<h6 class="media-heading">'+motel.title_kor+'<a href="#" onclick="listClickEvent('+motelTitle+','+city+')"><i class="icon-info-sign"></i></a></h6>'+
								'<div style="font-size: 8pt;">'+motel.addr+'<div>'
								'</div>'+
								'</li>';
	
	    	                   $('#placesList2').append(itemStr2);
	    	                });
	    	          });
    	          }).done(function(){
	    	          $.getJSON("json/"+position.title+"_Res2.json",function(data){
	    	                 //placesList의 childnode 삭제
	    	                 $('#placesList3').empty();
	    	                $(data.restaurant).each(function(i,res){
	    	                   //리스트출력시 html태그
	    	                	var restaurantTitle="'"+res.title+"'";
	    	                   //리스트출력시 html태그
								var itemStr3 =  '<li id="check" class="media">'+
								'<a class="pull-left"><img class="media-object" src="./images/placeImg/'+res.tel+'.jpg"></a>'+
								'<div class="media-body">'+
								'<h6 class="media-heading">'+res.title_kor+'<a href="#" onclick="listClickEvent('+restaurantTitle+','+city+')"><i class="icon-info-sign"></i></a></h6>'+
								'<div style="font-size: 8pt;">'+res.addr+'<div>'
								'</div>'+
								'</li>';
	    	                   $('#placesList3').append(itemStr3);
	    	                });
	    	          });
    	          });
//        			}
//        			else{
//        				$("#placesList").empty();
//        				$("#placesList2").empty();
//        				$("#placesList3").empty();
//        			}
    		});
    		
    		//마커에 마우스오버 이벤트를 등록합니다
    		daum.maps.event.addListener(marker, 'mouseover', function() {
    		// 마커에 마우스오버 이벤트가 발생하면 인포윈도우를 마커위에 표시합니다
    		 infowindow.open(map, marker);
    		});

    		//마커에 마우스아웃 이벤트를 등록합니다
    		daum.maps.event.addListener(marker, 'mouseout', function() {
    		 // 마커에 마우스아웃 이벤트가 발생하면 인포윈도우를 제거합니다
    		 infowindow.close();
    		});
    	});
	});	

//위도,경도 출력 이벤트
daum.maps.event.addListener(map, 'click', function(mouseEvent) {        
	// 클릭한 위도, 경도 정보를 가져옵니다 
	var latlng = mouseEvent.latLng;
	    
	var message = latlng.getLat() + ', ' + latlng.getLng();
	    
	console.log(message);
});

//리스트클릭시 상세정보 모달
	function listClickEvent(title,citytitle){
		$.getJSON("json/"+citytitle+"_Res2.json", function(data) {
			$(data.restaurant).each(function(i, res) {
				if(title==res.title){
					$('#detailImg_1').remove();
					$('#detailTitle_1').remove();
					$('#detailAddr_1').remove();
					$('#detailTel_1').remove();
					$('#detailIntroduction_1').remove();
					$('#detailImg').append('<img id="detailImg_1" src ="./images/placeImg/'+res.tel+'.jpg"/>');
					$('#detailTitle').append('<p id="detailTitle_1">'+res.title_kor+'</p>');
					$('#detailAddr').append('<p id="detailAddr_1">'+res.addr+'</p>');
					$('#detailTel').append('<p id="detailTel_1">'+res.tel+'</p>');
					$('#detailIntroduction').append('<p id="detailIntroduction_1">'+res.introduction+'</p>');
				}
			});	
		});
		$.getJSON("json/"+citytitle+"_Hot2.json", function(data) {
			$(data.motels).each(function(i, motel) {
				if(title==motel.title){	
					$('#detailImg_1').remove();
					$('#detailTitle_1').remove();
					$('#detailAddr_1').remove();
					$('#detailTel_1').remove();
					$('#detailIntroduction_1').remove();
					$('#detailImg').append('<img id="detailImg_1" src ="./images/placeImg/'+motel.tel+'.jpg"/>');
					$('#detailTitle').append('<p id="detailTitle_1">'+motel.title_kor+'</p>');
					$('#detailAddr').append('<p id="detailAddr_1">'+motel.addr+'</p>');
					$('#detailTel').append('<p id="detailTel_1">'+motel.tel+'</p>');
					$('#detailIntroduction').append('<p id="detailIntroduction_1">'+motel.introduction+'</p>');;
				}
			});	
		});
		$.getJSON("json/"+citytitle+"_Loc2.json", function(data) {
			$(data.locations).each(function(i, location) {
				if(title==location.title){	
					$('#detailImg_1').remove();
					$('#detailTitle_1').remove();
					$('#detailAddr_1').remove();
					$('#detailTel_1').remove();
					$('#detailIntroduction_1').remove();
					$('#detailImg').append('<img id="detailImg_1" src ="./images/placeImg/'+location.tel+'.jpg"/>');
					$('#detailTitle').append('<p id="detailTitle_1">'+location.title_kor+'</p>');
					$('#detailAddr').append('<p id="detailAddr_1">'+location.addr+'</p>');
					$('#detailTel').append('<p id="detailTel_1">'+location.tel+'</p>');
					$('#detailIntroduction').append('<p id="detailIntroduction_1">'+location.introduction+'</p>');
				}
			});	
		});
		$("#myModal").modal('show');
	}
//**************************전체일정 : 도시 검색**************************
//전체일정 : 도시 검색
function citySearch(){
	var keyword = $("#city_search").val();
	var markerSearch;
	$.getJSON("json/city.json",function (data){
		$(data.positions).map(function(i, position) {
			if(position.title_kor == keyword){
				panTo(position.lat, position.lng);
				
				markerSearch = new daum.maps.Marker({
	    			map: map,
	    			title: position.title,
	    			position: new daum.maps.LatLng(position.lat, position.lng),
					image : markerLocImage
	    		});
			}
			
			 daum.maps.event.addListener(map, 'click', function() {
		            markerSearch.setMap(null);
		         });

    	});
	});
	
}

//전체일정: 중심좌표변경
function panTo(lat, lng) {
    // 이동할 위도 경도 위치를 생성합니다 
    var moveLatLon = new daum.maps.LatLng(lat, lng);
    
    // 지도 중심을 부드럽게 이동시킵니다
    // 만약 이동할 거리가 지도 화면보다 크면 부드러운 효과 없이 이동합니다
    map.panTo(moveLatLon);            
}   

//**************************현재위치**************************
//추가 현재위치보기 클릭시 지도상에 자신의 위치가 표시가 됩니다.
//HTML5의 geolocation으로 사용할 수 있는지 확인합니다 
// 규채 : 버튼을 누르면 현재 위치가 지도에 마커로 표시됨
function myPlace() {
	 if (navigator.geolocation) {
	  
	  // GeoLocation을 이용해서 접속 위치를 얻어옵니다
	  navigator.geolocation.getCurrentPosition(function(position) {
	      
	      var latLoc = position.coords.latitude, // 위도
	          lonLoc = position.coords.longitude; // 경도
	      
	      var locPosition = new daum.maps.LatLng(latLoc, lonLoc), // 마커가 표시될 위치를 geolocation으로 얻어온 좌표로 생성합니다
	          messageLoc = '<div style="padding:5px;">여기에 숨어있나요??</div>'; // 인포윈도우에 표시될 내용입니다
	      
	      // 마커와 인포윈도우를 표시합니다
	      displayMarker(locPosition, messageLoc);
	          
	    });
	  
	 } else { // HTML5의 GeoLocation을 사용할 수 없을때 마커 표시 위치와 인포윈도우 내용을 설정합니다
	  
	  var locPosition = new daum.maps.LatLng(33.450701, 126.570667),    
	 	 messageLoc = 'geolocation을 사용할수 없어요..'
	      
	  displayMarker(locPosition, messageLoc);
	 }
}

//지도에 마커와 인포윈도우를 표시하는 함수입니다
function displayMarker(locPosition, messageLoc) {
	// 마커를 생성합니다
	var markerLoc = new daum.maps.Marker({  
	   map: map, 
	   position: locPosition,
	   image : markerLocImage
	}); 
	
	var iwContentLoc = messageLoc, // 인포윈도우에 표시할 내용
	   iwRemoveable = true;
	
	// 인포윈도우를 생성합니다
	var infowindowLoc = new daum.maps.InfoWindow({
	   content : iwContentLoc,
	   removable : iwRemoveable
	});

	// 인포윈도우를 마커위에 표시합니다 
	infowindowLoc.open(map, markerLoc);
	
	// 지도 중심좌표를 접속위치로 변경합니다
	map.setCenter(locPosition);      
	
	daum.maps.event.addListener(map, 'click', function() {
		markerLoc.setMap(null);
		infowindowLoc.close();
	});
}    

// 사용자가 sortable li 순서 변환 시 관련 모든 배열 업데이트  by 수항, 소영
$(function() {
	$( "#sortable" ).sortable({
		stop: function( event, ui ) {
			fnUpdateQueue(marker_arr);
		}
	});
	$( "#sortable" ).disableSelection();
});
</script>
<script src="http://192.168.20.112:8080/Railring_v18/bootstrap/js/bootstrap.min.js"></script>
</body>
</html>
