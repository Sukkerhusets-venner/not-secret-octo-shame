/* 
 * Bruker jquery 2.1.*
 */
function checkGame() {
    if (sjekk === "true") {
        reset();
        alertify.set({labels: {ok: "Fortsett", cancel: "Start et nytt"}});
        alertify.confirm("<b>Vil du fortsette det gamle spillet eller starte på et nytt et?</b><br/><br/>", function (e) {
            if (e) {
                window.location.href = "game";
            } else {
                document.forms["game"].elements["inGame"].value = false;
                document.forms["game"].submit();
            }
        });
    } else {
        window.location.href = "game";
    }
}
function reset() {
    $("#toggleCSS").attr("href", "resources/css/alertify.default.css");
    alertify.set({
        labels: {
            ok: "OK",
            cancel: "Cancel"
        },
        delay: 5000,
        buttonReverse: false,
        buttonFocus: "ok"
    });
}
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

    $("#buttons div a").click(function (event) {
        var elem = $(event.target).html();
        switch (elem) {
            case 'Spillet':
                a.css("visibility", "visible");
                b.css("visibility", "hidden");
                c.css("visibility", "hidden");
                $("#brukernavnform").css("visibility", "hidden");
                $("#passordform").css("visibility", "hidden");
                break;
            case 'Resultater':
                b.css("visibility", "visible");
                a.css("visibility", "hidden");
                c.css("visibility", "hidden");
                $("#brukernavnform").css("visibility", "hidden");
                $("#passordform").css("visibility", "hidden");
                break;
            case 'Profil':
                c.css("visibility", "visible");
                a.css("visibility", "hidden");
                b.css("visibility", "hidden");
                break;
                // slettes
            default:
                alert("Si ifra om du får denne meldingen" + elem.toString());
        }
    });

    $("#byttBrukernavn").click(function () {
        $("#brukernavnform").css("visibility", "visible");
        $("#passordform").css("visibility", "hidden");
    });

    $("#byttPassord").click(function () {
        $("#passordform").css("visibility", "visible");
        $("#brukernavnform").css("visibility", "hidden");
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
