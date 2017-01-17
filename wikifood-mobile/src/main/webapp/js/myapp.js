var rootURL = "http://localhost:8080/wikifood/rest";
var datyp = "json";
var dataresult = "";
var typearticle;
var typearticleid=0;
var mapCenter = new google.maps.LatLng(40.5968, 22.9683); //Google map Coordinates
var map;

$(document).bind('mobileinit',function(){
    $.extend(  $.mobile , {
      defaultPageTransition: "none"
    });
});

$( document ).on( "pageinit", "#home", function() {
	$('.my-slider').unslider({
         autoplay: true,
         arrows: false
    });
	
});

$( document ).on( "pageinit", "#menu", function() {
	getMenu("typearticle");
	
});

/*$( document ).on( "pageinit", "#aboutus", function() {
	map_initialize(); // initialize google map
	google.maps.event.addDomListener(window, 'load', map_initialize);
	google.maps.event.trigger(map, 'resize');
});*/

$( document ).on( "pageshow", "#aboutus", function() {
	map_initialize(); // initialize google map
	google.maps.event.addDomListener(window, 'load', map_initialize);
	google.maps.event.trigger(map, 'resize');
});

$('#menu-listview').on('click', 'li', function() {
	typearticleid=this.id;
	getMenuItem("article",typearticle);
	$.mobile.changePage("#menuitem");
});

$( "#menuitem" ).on( "pageinit", function() {
	console.log(typearticle);
	getMenuItem("article",typearticle);
});

function getMenu(entity){
	var output="";
    $.ajax({
		type : 'GET',
		url : rootURL + '/' + entity,
		contentType: 'application/json',
		dataType : datyp,
		success : function(data) {
			typearticle=data;
			$.each(data, function (index, value) {
				 output += '<li id='+value.id+'><a href="#">'+
                           '<img class="ui-li-thumb" src="data:image/png;base64,'+ value.img+'">'+
                           '<h2>'+value.itemLabelFr+'</h2>'+
                           '<p>'+value.itemDescFr+'</p>'+
                           '<!--p class="ui-li-aside">BlackBerry</p-->'+
                           '</a></li>';
            });		
			$('#menu-listview').html(output).listview("refresh");
		},
		error: function(){
            alert('search error ');
        }
	});
};

function getMenuItem(entity,typearticle){
	var output="";
    $.ajax({
		type : 'GET',
		url : rootURL + '/' + entity + '?id=' + typearticleid,
		contentType: 'application/json',
		dataType : 'json',
		success : function(data) {
			$("#menuitem-h2").html(typearticle[typearticleid-1].itemLabelFr);
			$.each(data, function (index, value) {
				 output += '<li id='+value.id+'><a href="#">'+
                           '<img class="ui-li-thumb" src="data:image/png;base64,'+ value.img+'">'+
                           '<h2>'+ value.itemLabelFr +'</h2>'+
                           '<p>'+value.itemDescFr+'</p>'+
                           '<!--p class="ui-li-aside">BlackBerry</p-->'+
						   '<p class="ui-li-aside">'+value.prix+'</p>'+
                           '</a></li>';
            });		
			
			$('#menuitem-listview').html(output).listview("refresh");
		},
		error: function(){
            alert('search error ');
        }
	});
};

//############### Google Map Initialize ##############
function map_initialize() {
  var googleMapOptions = {
    center: mapCenter, // map center
    zoom: 15, //zoom level, 0 = earth view to higher value
    maxZoom: 18,
    minZoom: 12,
    zoomControlOptions: {
      style: google.maps.ZoomControlStyle.SMALL //zoom control size
    },
    scaleControl: true, // enable scale control
    mapTypeId: google.maps.MapTypeId.ROADMAP // google map type
  };
  
  var marker = new google.maps.Marker({
    position: mapCenter,
    title:"Hello World!"
  });


  map = new google.maps.Map(document.getElementById("map"), googleMapOptions);
  // To add the marker to the map, call setMap();
  marker.setMap(map);
}
