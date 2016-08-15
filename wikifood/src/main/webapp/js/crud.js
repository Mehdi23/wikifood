var rootURL = "http://localhost:8080/wikifood/rest"
//var rootURL = "http://mehdiappk.appspot.com/rest"
var datyp = "json";
var index = 0;
var crud = 0;
var dataresult;
var currentobj;
var imagebase64;

//Find
$(document.body).on('click', '#r-crud' ,function(){
	crud=0;
	$("#c-crud").prop('disabled', true);$("#r-crud").prop('disabled', true);$("#u-crud").prop('disabled', false);$("#d-crud").prop('disabled', false); 
    //$('#page-content').find('input, textarea, button, select').attr('disabled', true);	
	//Search();
	$('#page-content').find('input, textarea, button, select').attr('disabled', false);
    //$('#page-content').find('input, textarea, button, select').val("");
	$("#exit-crud").prop('disabled', false);
	$("#valid-crud").prop('disabled', false);
});

//Add
$(document.body).on('click', '#c-crud' ,function(){
	crud=1;
	$("#c-crud").prop('disabled', true);$("#r-crud").prop('disabled', true);$("#u-crud").prop('disabled', true);$("#d-crud").prop('disabled', true); 
	$('#page-content').find('input, textarea, button, select').attr('disabled', false);
    $('#page-content').find('input, textarea, button, select').val("");
	$("#exit-crud").prop('disabled', false);
	$("#valid-crud").prop('disabled', false);
	initImage();
});

//Update
$(document.body).on('click', '#u-crud' ,function(){
	crud=2;
	$("#c-crud").prop('disabled', true);$("#r-crud").prop('disabled', true);$("#u-crud").prop('disabled', true);$("#d-crud").prop('disabled', true);  
	$('#page-content').find('input, textarea, button, select').attr('disabled', false);
	$("#exit-crud").prop('disabled', false);
	$("#valid-crud").prop('disabled', false);
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
  currentobj = dataresult[index-1];
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
   currentobj = dataresult[index-1];
});

$(document.body).on('click', '#first' ,function(){

    index = 0;
    $("#current").text(1);
	$("#total").text(dataresult.length);
	$("#prev").prop('disabled', true);
	$("#next").prop('disabled', false);
	$('#entityform').loadJSON(dataresult[0]);
	setImage(dataresult[0].img);
	currentobj = dataresult[0];
  
});

$(document.body).on('click', '#last' ,function(){
	$("#current").text(dataresult.length);
	$("#total").text(dataresult.length);
	$("#next").prop('disabled', true);
	$("#prev").prop('disabled', false);
	$('#entityform').loadJSON(dataresult[dataresult.length-1]);
	setImage(dataresult[dataresult.length-1].img);
	currentobj = dataresult[dataresult.length-1];
});
	
$(document.body).on('click', '#valid-crud' ,function(){
	
    switch(crud) {
		case 0:
	       Search();		
		   $('#page-content').find('input, textarea, button, select').attr('disabled', true);	
		   $("#valid-crud").prop('disabled', true);
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
});   
$(document.body).on('click', '#exit-crud' ,function(){ 
    echape(); // esc  
});

function echape() {
	$("#c-crud").prop('disabled', false);$("#r-crud").prop('disabled', false);$("#u-crud").prop('disabled', true);$("#d-crud").prop('disabled', true);
	$('#page-content').find('input, textarea, button, select').val("");
	$('#page-content').find('input, textarea, button, select').attr('disabled', true);
	$(".btn-crud").prop('disabled', true);
    $("#current").text(".");
    $("#total").text(".");	
	$("#exit-crud").prop('disabled', true);
	$("#valid-crud").prop('disabled', true);
	initImage();
};

function Create(){
  var jsonObject = JSON.parse($('#entityform').serializeJSON());
  //console.log(imgData);
  var imageObject={"img" : ""};
  imageObject["img"] = getImage();
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
            currentobj = dataresult[index];		    
		},
		error: function(){
            alert('search error ');
			$('#entityform')[0].reset();
        }
	});
	$('#page-content').find('input, textarea, button, select').attr('disabled', true);
};

function Update(){
	var imageObject={"img" : ""};
	imageObject["id"] = currentobj.id;
    if (confirm('Vous confirmez la modification?')) { 
	var jsonObject = JSON.parse($('#entityform').serializeJSON());
    imageObject["img"] = getImage();
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
    var imageObject={"id" : 0};
	imageObject["id"] = currentobj.id;
    if (confirm('Vous confirmez la suppression?')) { 
	    var jsonObject = JSON.parse($('#entityform').serializeJSON());
        mix(jsonObject, imageObject);
        $.ajax({
            type: 'DELETE',
            contentType: 'application/json',
		    dataType : datyp,
			data: JSON.stringify(imageObject),
            url: rootURL + '/' + $('#entity').text(),
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
                $('#blah').attr('src', e.target.result);
                    //.width(100)
                    //.height(120);	
				var imgElem = document.getElementById('blah');
				imgElem.style = "width:250px; height:250px;";
			    imagebase64 = e.target.result;
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

function getImage() {
    var imgData = imagebase64.replace(/^data:image\/(png|jpg|jpeg);base64,/, "");
	return imgData;
	
	//var imgElem = document.getElementById('blah');
    //var imgData = getBase64Image(imgElem); 
	//return imgData;
}

function setImage(source, width, height) {
	var newim = document.createElement('img');
	newim.setAttribute( 'src', 'data:image/png;base64,'+ source);
	newim.setAttribute( 'id', 'blah');
	$( "#base64image" ).html( newim );  
    newim.style = "width:250px; height:250px;";	
}

function initImage() {
	
	var newim = document.createElement('img');
	newim.setAttribute( 'src', 'image/profile.png');
	newim.setAttribute( 'id', 'blah');
	$( "#base64image" ).html( newim ); 
	newim.style = "width:250px; height:250px;";
}
