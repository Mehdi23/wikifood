var rootURL = "http://localhost:8080/wikifood/rest"
//var rootURL = "http://mehdiappk.appspot.com/rest"
var datyp = "json";
var index = 0;
var crud = 0;
var dataresult;

//Find
$(document.body).on('click', '#r-crud' ,function(){
	crud=0;
	$("#c-crud").prop('disabled', true);$("#r-crud").prop('disabled', true);$("#u-crud").prop('disabled', false);$("#d-crud").prop('disabled', false); 
    //$('#page-content').find('input, textarea, button, select').attr('disabled', true);	
	//Search();
	$('#page-content').find('input, textarea, button, select').attr('disabled', false);
    //$('#page-content').find('input, textarea, button, select').val("");
});

//Add
$(document.body).on('click', '#c-crud' ,function(){
	crud=1;
	$("#c-crud").prop('disabled', true);$("#r-crud").prop('disabled', true);$("#u-crud").prop('disabled', true);$("#d-crud").prop('disabled', true); 
	$('#page-content').find('input, textarea, button, select').attr('disabled', false);
    $('#page-content').find('input, textarea, button, select').val("");
	initImage();
});

//Update
$(document.body).on('click', '#u-crud' ,function(){
	crud=2;
	$("#c-crud").prop('disabled', true);$("#r-crud").prop('disabled', true);$("#u-crud").prop('disabled', true);$("#d-crud").prop('disabled', true);  
	$('#page-content').find('input, textarea, button, select').attr('disabled', false);
});

//Delete
$(document.body).on('click', '#d-crud' ,function(){
	crud=3;
	$("#c-crud").prop('disabled', true);$("#r-crud").prop('disabled', true);$("#u-crud").prop('disabled', true);$("#d-crud").prop('disabled', true);
    $('#page-content').find('input, textarea, button, select').attr('disabled', true);	
	Delete();
	echape();
});

$(document.body).on('click', '#next' ,function(){

  index = parseInt($("#current").text());
  index++;
  $("#current").text(index);
  $("#total").text(dataresult.length);
  if(index == dataresult.length) $("#next").prop('disabled', true); 
  if(index == 2) $("#prev").prop('disabled', false); 
  $('#entityform').loadJSON(dataresult[index-1]);
  setImage(dataresult[index-1].img);
});

$(document.body).on('click', '#prev' ,function(){

  index = parseInt($("#current").text());
  index--;
   $("#current").text(index);
   $("#total").text(dataresult.length);
   if(index == dataresult.length-1) $("#next").prop('disabled', false); 
   if(index == 1) $("#prev").prop('disabled', true); 
   $('#entityform').loadJSON(dataresult[index-1]);
   setImage(dataresult[index-1].img);
});

$(document.body).on('click', '#first' ,function(){

    index = 0;
    $("#current").text(1);
	$("#total").text(dataresult.length);
	$("#prev").prop('disabled', true);
	$("#next").prop('disabled', false);
	$('#entityform').loadJSON(dataresult[0]);
	setImage(dataresult[0].img);
  
});

$(document.body).on('click', '#last' ,function(){
	$("#current").text(dataresult.length);
	$("#total").text(dataresult.length);
	$("#next").prop('disabled', true);
	$("#prev").prop('disabled', false);
	$('#entityform').loadJSON(dataresult[dataresult.length-1]);
	setImage(dataresult[dataresult.length-1].img);
});
	
//Enter/Escape
$(document).keyup(function(e) {
  if (e.keyCode === 13) { // enter
    switch(crud) {
		case 0:
	       Search();		
		   $('#page-content').find('input, textarea, button, select').attr('disabled', true);	
		break;
		case 1:
		   Create();
		   echape();
		break;
		case 2:
		   Update();
		   echape();
		break;
		case 3:
		   
		break;		
	}
  }     
  if (e.keyCode === 27) echape(); // esc  
});

function echape() {
	$("#c-crud").prop('disabled', false);$("#r-crud").prop('disabled', false);$("#u-crud").prop('disabled', true);$("#d-crud").prop('disabled', true);
	$('#page-content').find('input, textarea, button, select').attr('disabled', true);
    $('#page-content').find('input, textarea, button, select').val("");
	$(".btn-crud").prop('disabled', true);
    $("#current").text(".");
    $("#total").text(".");	
	initImage();
};

function Create(){
  var jsonObject = JSON.parse($('#entityform').serializeJSON());
  var imgElem = document.getElementById('blah');
  var imgData = getBase64Image(imgElem);
  //console.log(imgData);
  var imageObject={"img" : ""};
  imageObject["img"] = imgData;
  mix(jsonObject, imageObject);

  $.ajax({
		type : 'POST',
		url : rootURL + '/' + $('#entity').text() + '/save',
		contentType: 'application/json',
		dataType : datyp,
		data : JSON.stringify(imageObject),
		success : function(data) {
			alert('Saved ...');
			$('#entityform')[0].reset();
		},
		error : function(jqXHR, textStatus, errorThrown) {
			alert('Add error');
			$('#entityform')[0].reset();
		}
	});
	
};


function Search(){
    index = 0;
    $.ajax({
		type : 'POST',
		url : rootURL + '/' + $('#entity').text() + '/get',
		contentType: 'application/json',
		dataType : datyp,
		data : $('#entityform').serializeJSON(),
		success : function(data) {
			dataresult=data;
		    $(".btn-crud").prop('disabled', true);
		    if(data.length>1) $(".btn-crud").prop('disabled', false);   
		    $("#current").text(1);
		    $("#total").text(data.length);
			$('#entityform').loadJSON(data[index]);
			$('#entityform')[0].reset();
			setImage(dataresult[index].img);		    
		},
		error: function(){
            alert('search error ');
			$('#entityform')[0].reset();
        }
	});
	$('#page-content').find('input, textarea, button, select').attr('disabled', true);
};

function Update(){
    if (confirm('Vous confirmez la modification?')) { 
	var jsonObject = JSON.parse($('#entityform').serializeJSON());
    var imgElem = document.getElementById('blah');
    var imgData = getBase64Image(imgElem);
    //console.log(imgData);
    var imageObject={"img" : ""};
    imageObject["img"] = imgData;
    mix(jsonObject, imageObject);
    $.ajax({
        type: 'PUT',
        contentType: 'application/json',
        url: rootURL + '/' + $('#entity').text(),
        dataType: datyp,
        data: JSON.stringify(imageObject),
        success: function(textStatus, jqXHR){
            alert('updated successfully');
            $('#entityform')[0].reset();
        },
        error: function(jqXHR, textStatus, errorThrown){
            alert('update error: ');
        }
    });
    }
    
    else {
        $('#entityform')[0].reset();
        Search();
    }
};

function Delete(){ 
    if (confirm('Vous confirmez la suppression?')) { 
        $.ajax({
            type: 'DELETE',
            contentType: 'application/json',
		    dataType : datyp,
            url: rootURL + '/' + $('#entity').text() + '/' + $("#id").val(),
            success: function(textStatus, jqXHR){
                alert('enregistrement supprimé ');
            },
            error: function(jqXHR, textStatus, errorThrown){
                alert('delete error ');
            }
        });
    }    
};
function readURL(input) {
        if (input.files && input.files[0]) {
            var reader = new FileReader();

            reader.onload = function (e) {
                $('#blah')
                    .attr('src', e.target.result);
                    //.width(100)
                    //.height(120);
            };

            reader.readAsDataURL(input.files[0]);
        }
}
function getBase64Image(imgElem) {
// imgElem must be on the same server otherwise a cross-origin error will be thrown "SECURITY_ERR: DOM Exception 18"
    var canvas = document.createElement("canvas");
    canvas.width = imgElem.clientWidth;
    canvas.height = imgElem.clientHeight;
    var ctx = canvas.getContext("2d");
    ctx.drawImage(imgElem, 0, 0);
    var dataURL = canvas.toDataURL("image/png");
    return dataURL.replace(/^data:image\/(png|jpg);base64,/, "");
}
function mix(source, target) {
   for(var key in source) {
     if (source.hasOwnProperty(key)) {
        target[key] = source[key];
     }
   }

}

function setImage(source) {
	var newim = document.createElement('img');
	newim.style = "width:250px; height:250px;";
	newim.setAttribute( 'src', 'data:image/png;base64,'+ source);
	newim.setAttribute( 'id', 'blah');
	$( "#base64image" ).html( newim );
}

function initImage() {
	var newim = document.createElement('img');
	newim.style = "width:250px; height:250px;";
	newim.setAttribute( 'src', 'image/profile.png');
	newim.setAttribute( 'id', 'blah');
	$( "#base64image" ).html( newim );
}
