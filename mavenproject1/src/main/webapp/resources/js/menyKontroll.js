/* 
 * Bruker jquery 2.1.*
 */

$(document).ready(function () {
    
    $(".container").css("height", "0");
    $(".container").css("margin-top", "0");
    $(".container div").css("visibility", "hidden");
    $("#stuff").css("height", "15rem");
    
    $("#buttons div a").click(function(){
        $("#stuff").remove();
        $(".container").css("height", "5rem");
        $(".container").css("margin-top", "8rem");
        $(".container div").css("visibility", "visible");
    });
});
