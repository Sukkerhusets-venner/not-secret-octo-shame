/* 
 * Bruker jquery 2.1.*
 */

var REFRESH_TIMER = 3000;

$(document).ready(function () {
    
    $(".container").css("height", "0");
    $(".container").css("margin-top", "0");
    $(".container div").css("visibility", "hidden");
    $("#stuff").css("height", "15rem");
    
    
    var a = $("#spill");
    var b = $("#hiscore");
    var c = $("#profil");
    a.css("visibility", "visible");
    
    function showHiScore() {
        if (a.css("visibility").toString() === ("visible")) {
            a.fadeTo(500, 0, function () {
                a.css("visibility", "hidden");
                b.css("visibility", "visible");
                b.fadeTo(500, 1);
            });
        } else if (c.css("visibility").toString() === ("visible")) {
            c.fadeTo(500, 0, function () {
                c.css("visibility", "hidden");
                b.css("visibility", "visible");
                b.fadeTo(500, 1);
            });
        }
    }
    function showSpill() {
        if (c.css("visibility").toString() === ("visible")) {
            c.fadeTo(500, 0, function () {
                c.css("visibility", "hidden");
                a.css("visibility", "visible");
                a.fadeTo(500, 1);
            });
        } else if (b.css("visibility").toString() === ("visible")) {
            b.fadeTo(500, 0, function () {
                b.css("visibility", "hidden");
                a.css("visibility", "visible");
                a.fadeTo(500, 1);
            });
        }
    }
    function showProfil() {
        if (a.css("visibility").toString() === ("visible")) {
            a.fadeTo(500, 0, function () {
                a.css("visibility", "hidden");
                c.css("visibility", "visible");
                c.fadeTo(500, 1);
            });
        } else if (b.css("visibility").toString() === ("visible")) {
            b.fadeTo(500, 0, function () {
                b.css("visibility", "hidden");
                c.css("visibility", "visible");
                c.fadeTo(500, 1);
            });
        }
    }
    var divVist = 0;
    var timer = setInterval(function () {
        divVist++;
        if (divVist >= 3) {
            divVist = 0;
        }
        switch (divVist) {
            case 0:
                showSpill();
                break;
            case 1:
                showHiScore();
                break;
            case 2:
                showProfil();
                break;
        }
    }, REFRESH_TIMER);

    $("#buttons div a").mouseover(function (event) {
        var elem = $(event.target).html();
        clearInterval(timer);
        if (elem === ("Spillet") && a.css("visibility").toString() === ("hidden")) {
            showSpill();
        } else if (elem === ("HiScore") && b.css("visibility").toString() === ("hidden")) {
            showHiScore();
        } else if (elem === ("Profil") && c.css("visibility").toString() === ("hidden")) {
            showProfil();
        }
    });
    $("#buttons div a").click(function(){
        $("#stuff").remove();
        $(".container").css("height", "5rem");
        $(".container").css("margin-top", "8rem");
        $(".container div").css("visibility", "visible");
    });
});
