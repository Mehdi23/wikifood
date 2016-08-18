//Chargement de la page du CRUD
$( ".treeview-menu-entity" ).click(function() {
     $("#content-header-text").html("Référenciel");         
     $("#page-header").load("pages/crud.html");
     $("#page-content" ).load( "entite/"+$(this).attr('name')+".html" );
     $("#entity" ).text($(this).attr('name'));
    /* $( ".sidebar-toggle" ).trigger( "click" );*/
	 $(".btn-crud").prop('disabled', true);

})

//Chargement de la page du Menu
$( ".treeview-gesmenu" ).click(function() {
     $("#content-header-text").html("Gestion du Menu"); 
     $("#page-header").html("");	 
     $("#page-content").load("pages/menu.html");
     $("#entity" ).text($(this).attr('name'));

})
