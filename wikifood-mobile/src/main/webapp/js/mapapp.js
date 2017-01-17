var mapCenter = new google.maps.LatLng(40.5968, 22.9683); //Google map Coordinates
var map;

$( document ).on( "pageinit", "#aboutus", function() {
	map_initialize(); // initialize google map
	google.maps.event.addDomListener(window, 'load', map_initialize);
});

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

  map = new google.maps.Map(document.getElementById("map"), googleMapOptions);
}

