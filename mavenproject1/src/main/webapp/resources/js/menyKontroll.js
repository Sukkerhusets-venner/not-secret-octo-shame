/* 
 * Bruker jquery 2.1.*
 */

$(document).ready(function () {
    
    $(".container").css("height", "0");
    $(".container").css("margin-top", "0");
    $(".container div").css("visibility", "hidden");
    $("#stuff").css("height", "15rem");
    
    var a = $("#spill");
    var b = $("#hiscore");
    var c = $("#profil");
    
    a.css("visibility", "visible");
    b.css("visibility", "hidden");
    c.css("visibility", "hidden");
    
    $("#buttons div a").click(function(event){
        var elem = $(event.target).html();
        switch(elem){
            case 'Spillet': a.css("visibility", "visible");
                    b.css("visibility", "hidden");
                    c.css("visibility", "hidden"); break;
            case 'Resultater': b.css("visibility", "visible");
                    a.css("visibility", "hidden");
                    c.css("visibility", "hidden"); break;
            case 'Profil': c.css("visibility", "visible");
                    a.css("visibility", "hidden");
                    b.css("visibility", "hidden"); break;    
                    // slettes
            default: alert("Si ifra om du får denne meldingen" + elem.toString());
        }
    });
    
    /*
    $("Link ut fra siden").click(function(event){
        $("#stuff").remove();
        $(".container").css("height", "5rem");
        $(".container").css("margin-top", "8rem");
        $(".container div").css("visibility", "visible");
    });
    */
});
