var rootURL = "http://localhost:8080/wikifood/rest";
var datyp = "json";
var dataresult = "";
var selectedMenu=0;
$( document ).on( "pageinit", "#menu", function() {
	getMenu("typearticle");
	$('#menu-listview').on('click', 'li', function() {
		selectedMenu=this.id;
		$.mobile.changePage("#menuitem");
    });
});

$( "#menuitem" ).on( "pageinit", function() {
	console.log(selectedMenu);
	getMenuItem("article");
});

function getMenu(entity){
	var output="";
    $.ajax({
		type : 'GET',
		url : rootURL + '/' + entity,
		contentType: 'application/json',
		dataType : datyp,
		success : function(data) {
			$.each(data, function (index, value) {
				 output += '<li id='+value.id+'><a href="#">'+
                           '<img class="ui-li-thumb" src="data:image/png;base64,'+ value.img+'">'+
                           '<h2>'+ value.itemLabelFr +'</h2>'+
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

function getMenuItem(entity){
	var output="";
    $.ajax({
		type : 'GET',
		url : rootURL + '/' + entity,
		contentType: 'application/json',
		dataType : datyp,
		success : function(data) {
			$.each(data, function (index, value) {
				 output += '<li id='+value.id+'><a href="#">'+
                           '<img class="ui-li-thumb" src="data:image/png;base64,'+ value.img+'">'+
                           '<h2>'+ value.itemLabelFr +'</h2>'+
                           '<p>'+value.itemDescFr+'</p>'+
                           '<!--p class="ui-li-aside">BlackBerry</p-->'+
						   '<p class="ui-li-aside">30 DH</p>'+
                           '</a></li>';
            });		
			
			$('#menuitem-listview').html(output).listview("refresh");
		},
		error: function(){
            alert('search error ');
        }
	});
};
