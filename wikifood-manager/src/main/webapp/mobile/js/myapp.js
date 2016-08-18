var rootURL = "http://localhost:8080/wikifood/rest";
$( document ).on( "pageinit", "#menu", function() {
	 $.ajax({
                type: 'GET',
                url: rootURL + '/article',
                async: false,
                contentType: "application/json",
                dataType: 'jsonp',
                success: function(json) {
                   console.log("yes");
                },
                error: function(e) {
                   console.log("no");
                }
     });
});
