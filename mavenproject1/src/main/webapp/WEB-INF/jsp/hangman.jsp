<%-- 
    Document   : hangman
    Created on : Jan 19, 2015, 11:29:03 PM
    Author     : McDowell
--%>

<%@include file="../../includes/head.jspf" %>
        <title>Hangman</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script src="resources/js/alertify.min.js"></script>
        <link rel="stylesheet" href="resources/css/alertify.core.css" />
	<link rel="stylesheet" href="resources/css/alertify.default.css" id="toggleCSS" />
        <c:url var="nmpUrl" value="/resources/css/hangman.css" />
        <link href="${nmpUrl}" rel="stylesheet" type="text/css"/>
 
        
        <script>
            
            function Hangman(user_id) {
                this.user_id = user_id;
                this.result = false;
                this.attempts_made = 0;
                this.attempts_left = 5;
                this.answer = "";
                this.question = "";
                this.points = 0;
                //TODO: figure out a better way to initialize messages
                this.messages = {
                    "correct": "Your guess was correct. Good going!!",
                    "invalid": "Please guess between A-Z and standard programming characters",
                    "lost": "You lost :-(",
                    "try_other": "Repeated character. Try again.",
                    "won": "Bravo! You won!",
                    "wrong": "Your guess was wrong. Please try again."
                };
            }
            
            Hangman.prototype = {
                init: function () {
                    var self = this;
                    self.attachHandlers();
                },
        
                attachHandlers: function () {
                    var self = this;
                    $(document).ready(function () {
                        timescore = "${assignment.getTimescore()}";
                        setInterval(function(){
                            if(timescore > 0){
                                timescore--;
                            }
                        },20000);
                        self.createGame();
                    });
                },
                
            
                createGame: function (){
                    var self = this;
                    self.answer = "${assignment.getCurrentTask().getAnswerHtml()}";
                    self.question = "${assignment.getCurrentTask().getText()}".split("$");
                    self.processData(self.question, 'question_input');
                    self.processData(Array(self.answer.length+1).join("_"), 'input_fields');
                },
                        
                processData: function (str, block) {
                    var self = this,
                            str_length = str.length;
                    self.buildBlocks(str, str_length, block);
                },
                        
                buildBlocks: function (str, length, block){
                    var self = this,
                        valid_char = $("<span class = 'valid_char'></span>"),
                        input_field_box = $("#" + block);
                    if (block === "input_fields"){
                        var div = document.getElementById(block);
                        div.innerHTLM = "";
                        for (var i=0; i < length; i++){
                            input_field_box.append(valid_char.clone().text(str[i]));
                        }
                        self.single_char = $(".single_char");
                        self.single_char.change(self.checkInput.bind(self));
                        self.attempts_left_txt = $(".attempts_left .number");
                        self.attempts_left_txt.text(self.attempts_left);
                    } else {
                        var div = document.getElementById(block);
                        div.innerHTML = str;
                    }
                },
                        
                checkInput: function (e) {
                    var self = this,
                            target = $(e.target),
                            val = target.val(),
                            code,
                            blank_spots,
                            last_try = $(".last_try"),
                            position = self.answer && self.answer.indexOf(val);
                    if (val.trim() !== ""){
                        last_try.prepend("<span class='valid_char'>" + val + "</span>");
                    }
                    if (position > -1 && val.trim() !== "") {
                        if ($(".valid_char:eq(" + position + ")").text() === val) {
                            self.insertMessage("try_other");
                            self.single_char.val("").removeClass("red");
                        } else {
                            for (var i = 0; i < self.answer.length; i++) {
                                if (self.answer[i] === val)
                                    $(".valid_char:eq(" + i + ")").text(val);
                            }
                            self.single_char.val("").removeClass("red");
                            self.insertMessage("correct");
                            blank_spots = $(".valid_char:contains('_')");
                            if (blank_spots && blank_spots.length === 0) {
                                self.result = true;
                                self.checkGameStatus();
                            }
                        }
                    } else {
                        code = val.charCodeAt(0);
                        if (code >= 33 && code <=126 && code !== 05) {
                            self.insertMessage("wrong");
                        } else if (val.trim() !== "") {
                            self.insertMessage("invalid");
                        }
                        self.single_char.val("").removeClass("red");
                        if (val.trim() !== ""){
                            self.attempts_left--;
                        }
                        self.attempts_left_txt.text(self.attempts_left);
                        self.single_char.addClass("red");
                    }
                    if (self.attempts_left === 0) {
                        self.checkGameStatus();
                    }
                },
                
                insertMessage: function (status) {
                    var self = this,
                            message = $("<li class='message'></li>"),
                            message_board = $(".messages"),
                            display_message = self.messages[status];
                    message_board.prepend(message.clone().text(display_message).addClass(status));
                },
            
                checkGameStatus: function (){
                    var self = this;
                    if (self.result) {
                        self.insertMessage("won");
                        self.points += self.attempts_left * 10;
                        alertify.alert("Supert! Det var riktig svar! Du har f�tt " + self.points + " poeng!", function (e) {
                            if(e){
                                document.forms["nesteOppgave"].elements["randomNumber"].value = Math.random();
                                document.forms["nesteOppgave"].elements["score"].value = self.points;
                                document.forms["nesteOppgave"].submit();
                            }
                        });
                    } else {
                        self.insertMessage("lost");
                        alertify.alert("Beklager! Det var feil svar! Du har f�tt " + self.points + " poeng!", function (e) {
                            if(e){
                                document.forms["nesteOppgave"].elements["randomNumber"].value = Math.random();
                                document.forms["nesteOppgave"].elements["score"].value = self.points;
                                document.forms["nesteOppgave"].submit();
                            }
                        });
                    }
                }
            };
            var game = new Hangman('abcd');
            game.init();
            
     
            function tilHovedmeny(){
                reset();
                alertify.set({ labels: { ok: "Fortsett � spille", cancel: "G� til hovedmeny" } });
                alertify.confirm("<b>Er du sikker p� at du vil returnere til hovedmenyen?<br/>Du vil kunne fortsette spillet, men timescoren din vil bli satt til 0.</b><br/><br/>", function (e) {
                    if (!e) {
                        window.location.href = "hovedside";
                    }
                });
            }
            function pressed(nr){
                var svar = "";
                var poengsum = 0;
                if(nr === riktigSvar){
                    poengsum = toInt(40)+toInt(timescore);
                    svar = "Veldig bra! Du fikk riktig. <br/><br/>Poeng for riktig: 40/40.<br/>Poeng for tid: "+
                    timescore+"/10<br/><br/>Din poengsum ble: "+poengsum+"/50. <br/><br/>Gratulerer!!<br/><br/>";
                } else {
                    poengsum = toInt(timescore);
                    svar = "Synd! Du svarte feil. <br/><br/>Poeng for riktig: 0/40.<br/>Poeng for tid: "+timescore+
                    "/10<br/><br/>Din poengsum ble: "+poengsum+"/50. <br/><br/>Riktig svar skulle v�rt:<br/><br/>"+escapeHtml(svarOppg)+"<br/><br/>";
                }
                reset();
                alertify.alert(svar,function (e) {
                    if(e){
                        document.forms["nesteOppgave"].elements["randomNumber"].value = Math.random();
                        document.forms["nesteOppgave"].elements["score"].value = poengsum;
                        document.forms["nesteOppgave"].submit();
                    }
                });
            }
            function reset () {
                $("#toggleCSS").attr("href", "resources/css/alertify.default.css");
		alertify.set({
			labels : {
				ok     : "OK",
                                cancel : "Cancel"
			},
				delay : 5000,
				buttonReverse : false,
				buttonFocus   : "ok"
		});
            }
            function escapeHtml(text) {
                var map = {
                  '&': '&amp;',
                  '<': '&lt;',
                  '>': '&gt;',
                  '"': '&quot;',
                  "'": '&#039;'
                };

                return text.replace(/[&<>"']/g, function(m) { return map[m]; 
            });
}
        </script>
    </head>
    <body>
        <form:form method="POST" modelAttribute="assignment" action ="nesteOppgave" id="nesteOppgave" name="nesteOppgave">
            <input type="hidden" name="score" value=''>
            <input type="hidden" name="randomNumber" value=''>
        </form:form>
            <div id="wrapper"> 
              <div class="header">
                <div class="mptitle">
                    <div id="smiley"><object type="image/svg+xml" data="resources/img/grin.svg"></object></div>
                    <h1>Spillet</h1>
                </div>
                <div id="buttons">
                    <!-- Ikke formater disse divene! -->
                    <div><a href="chat">Chat</a>
                    </div><div><a href="javascript:tilHovedmeny()">Resultater</a>
                    </div><div><a href="javascript:tilHovedmeny()">Profil</a>
                    </div>
                    <!-- ---------------------------- -->
                </div>
            </div>
        <section id="content">
            <section class="block"> 
                <h1>Oppgave ${assignment.getCurrentTaskNr()}</h3><br/>
                <h3 id="question_input"></h3><br/><br/>
                <div class="user_input">
                    <span class="user_input_text_message">Your Guess: </span>
                    <input type="text" maxlength='1' class="single_char" />
                </div>
                <div id="input_fields"></div>
                <div class="previous_inputs">
                    <span class="last_try_text">Last Try:</span>
                    <span class="last_try"></span>
                </div>
                <div class="attempts_left">
                    <span class="text">Attempts Left:</span>
                    <span class="number"></span>
                </div>

                <div class="message_board">
                    <h2>Message Board</h2>
                    <ol class="messages" reversed="reversed">
                    </ol>
                </div>
   

        </section>
            </div>
    </body>
</html>
