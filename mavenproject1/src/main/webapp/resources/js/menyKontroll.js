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

function removePH(){
    $("#cnPlaceholder").css("visibility", "hidden");
}

$(document).ready(function () {

    /*
     * Navigering (hovedside)
     */

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

    /*
     * Profiltab
     */

    var pi = $("#profilInfo");
    var bnf = $("#brukernavnform");
    var pnf = $("#passordform");
    var it = $("#infotab");
    var et = $("#endretab");
    var pt = $("#passordtab");
    var lastTab = 0;

    function hideAllTabs() {
        pi.css("visibility", "hidden");
        bnf.css("visibility", "hidden");
        pnf.css("visibility", "hidden");
    }

    function showLastTab() {
        if (lastTab === 0) {
            showInfoTab();
        } else if (lastTab === 1) {
            showEndreTab();
        } else {
            showPassordTab();
        }
    }
    showLastTab();
    function showInfoTab() {
        it.css("box-shadow", "none");
        it.css("-moz-box-shadow", "none");
        it.css("-webkit-box-shadow", "none");
        it.css("background", "#f3f3f3");
        et.css("box-shadow", "inset 0 -1px 4px rgba(0,0,0,0.4)");
        et.css("-moz-box-shadow", "inset 0 -1px 4px rgba(0,0,0,0.4)");
        et.css("-webkit-box-shadow", "inset 0 -1px 4px rgba(0,0,0,0.4)");
        et.css("background", "-webkit-radial-gradient(top,#dee8e9,#d2dcdd),radial-gradient(at top,#dee8e9,#d2dcdd)");
        pt.css("box-shadow", "inset 0 -1px 4px rgba(0,0,0,0.4)");
        pt.css("-moz-box-shadow", "inset 0 -1px 4px rgba(0,0,0,0.4)");
        pt.css("-webkit-box-shadow", "inset 0 -1px 4px rgba(0,0,0,0.4)");
        pt.css("background", "-webkit-radial-gradient(top,#dee8e9,#d2dcdd),radial-gradient(at top,#dee8e9,#d2dcdd)");

        pi.css("visibility", "visible");
        bnf.css("visibility", "hidden");
        pnf.css("visibility", "hidden");

        lastTab = 0;
    }

    function showEndreTab() {
        et.css("box-shadow", "none");
        et.css("-moz-box-shadow", "none");
        et.css("-webkit-box-shadow", "none");
        et.css("background", "#f3f3f3");
        it.css("box-shadow", "inset 0 -1px 4px rgba(0,0,0,0.4)");
        it.css("-moz-box-shadow", "inset 0 -1px 4px rgba(0,0,0,0.4)");
        it.css("-webkit-box-shadow", "inset 0 -1px 4px rgba(0,0,0,0.4)");
        it.css("background", "-webkit-radial-gradient(top,#dee8e9,#d2dcdd),radial-gradient(at top,#dee8e9,#d2dcdd)");
        pt.css("box-shadow", "inset 0 -1px 4px rgba(0,0,0,0.4)");
        pt.css("-moz-box-shadow", "inset 0 -1px 4px rgba(0,0,0,0.4)");
        pt.css("-webkit-box-shadow", "inset 0 -1px 4px rgba(0,0,0,0.4)");
        pt.css("background", "-webkit-radial-gradient(top,#dee8e9,#d2dcdd),radial-gradient(at top,#dee8e9,#d2dcdd)");

        pi.css("visibility", "hidden");
        bnf.css("visibility", "visible");
        pnf.css("visibility", "hidden");

        lastTab = 1;
    }

    function showPassordTab() {
        pt.css("box-shadow", "none");
        pt.css("-moz-box-shadow", "none");
        pt.css("-webkit-box-shadow", "none");
        pt.css("background", "#f3f3f3");
        it.css("box-shadow", "inset 0 -1px 4px rgba(0,0,0,0.4)");
        it.css("-moz-box-shadow", "inset 0 -1px 4px rgba(0,0,0,0.4)");
        it.css("-webkit-box-shadow", "inset 0 -1px 4px rgba(0,0,0,0.4)");
        it.css("background", "-webkit-radial-gradient(top,#dee8e9,#d2dcdd),radial-gradient(at top,#dee8e9,#d2dcdd)");
        et.css("box-shadow", "inset 0 -1px 4px rgba(0,0,0,0.4)");
        et.css("-moz-box-shadow", "inset 0 -1px 4px rgba(0,0,0,0.4)");
        et.css("-webkit-box-shadow", "inset 0 -1px 4px rgba(0,0,0,0.4)");
        et.css("background", "-webkit-radial-gradient(top,#dee8e9,#d2dcdd),radial-gradient(at top,#dee8e9,#d2dcdd)");

        pi.css("visibility", "hidden");
        bnf.css("visibility", "hidden");
        pnf.css("visibility", "visible");

        lastTab = 3;
    }


    $("#profiltabs div").click(function (event) {
        var qo = $(event.target);
        switch (qo.html()) {
            case it.html():
                if (it.css("background") !== "#f3f3f3") {
                    showInfoTab();
                }
                break;
            case et.html():
                if (et.css("visibility") !== "#f3f3f3") {
                    showEndreTab();
                }
                break;
            case pt.html():
                if (pt.css("visibility") !== "#f3f3f3") {
                    showPassordTab();
                }
                break;
        }

        switch (qo.html().trim()) {
            case "Info":
                if (it.css("background") !== "#f3f3f3") {
                    showInfoTab();
                }
                break;
            case "Endre":
                if (et.css("visibility") !== "#f3f3f3") {
                    showEndreTab();
                }
                break;
            case "Passord":
                if (pt.css("visibility") !== "#f3f3f3") {
                    showPassordTab();
                }
                break;
        }
    });

    /*
     * Chatbox
     */
    
    // vi må legge til en 'overlay' fordi vi har dokumenter som fremtrer forran selve beholderen
    var chatOverlay = "<div id='chatOverlay' style='cursor: pointer; position:fixed; bottom:0; right:0; width: 8rem; height: 1.5rem;'></div>";
    $('body').append(chatOverlay);
    chatOverlay = $("#chatOverlay");

    var chat = $("#chatRamme");
    var showChatB = $("#chatWrap");
    
    // animasjon på chat bobblen
    chatOverlay.mouseover(function(){
        $("#chatImg").css('-webkit-animation-play-state', 'running');
        $("#chatImg").css('animation-play-state', 'running');
    });
    
    chatOverlay.mouseout(function(){
        $("#chatImg").css('-webkit-animation-play-state', 'paused');
        $("#chatImg").css('animation-play-state', 'paused');
    });
    
    // trykker på chat knappen
    chatOverlay.click(function () {
        var s = chat.css("display");
        chat.css("visibility", "visible");
        if (s == "none") {
            showChatB.animate({width: "32.17rem"}, 300);
            chatOverlay.css("width",  "32.17rem");
            chat.fadeIn("slow");
        } else {
            showChatB.animate({width: "8rem"}, 300);
            chatOverlay.css("width",  "8rem");
            chat.fadeOut("slow");
        }
    });

    /*
     * Navigering (hovedside) 
     *  p.s må komme sist fordi den kaller metoder over
     */
    hideAllTabs();
    $("#buttons div a").click(function (event) {
        var elem = $(event.target).html();
        switch (elem) {
            case 'Spillet':
                a.css("visibility", "visible");
                b.css("visibility", "hidden");
                c.css("visibility", "hidden");
                hideAllTabs();
                break;
            case 'Resultater':
                b.css("visibility", "visible");
                a.css("visibility", "hidden");
                c.css("visibility", "hidden");
                hideAllTabs();
                break;
            case 'Profil':
                c.css("visibility", "visible");
                a.css("visibility", "hidden");
                b.css("visibility", "hidden");
                showLastTab();
                break;
                // slettes
            default:
                alert("Si ifra om du får denne meldingen" + elem.toString());
        }
    });

    setTimeout(function () {
       $("#chatNotifier").attr('src', 'chatNotifier');
       $("#cnPlaceholder").css("visibility", "visible");
    }, 5000);
});
