"use strict";

function Hangman(user_id) {
    this.user_id = user_id;
    this.result = false;
    this.attempts_made = 0;
    this.attempts_left = 5;
    this.answer = null;
    this.question = null;
    this.current_task = null;
    this.task_done = [];
    this.all_questions = [];
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
            self.createGame();
        });
    },
    createGame: function () {
        var self = this;
        self.answer = null;
        self.question = null;
        var rawFile = new XMLHttpRequest();
        var rawfile = new XMLHttpRequest();
        var allA = "";
        var listA = [];
        var allQ = "";
        var listQ = [];
        //specify questing txt file here
        rawfile.open("GET", "resources/txt/question.txt", false);
        rawfile.onreadystatechange = function()
        {
            if(rawfile.readyState === 4)
            {
                if(rawfile.status === 200 || rawfile.status === 0)
                {
                    allQ = rawfile.responseText;
                    listQ = allQ.split('\n');
                }
            }
        };
        rawfile.send(null);
        //specify answer txt file here
        rawFile.open("GET", "resources/txt/answer.txt", false);
        rawFile.onreadystatechange = function()
        {
            if(rawFile.readyState === 4)
            {
                if(rawFile.status === 200 || rawFile.status === 0)
                {
                    allA = rawFile.responseText;
                    listA = allA.split('\n');
                }
            }
        };
        rawFile.send(null);
        //replace number with the amount of tasks it can choose from
        var n = Math.floor(Math.random() * 4);
        while (self.task_done.indexOf(n) !== -1) {
            n = Math.floor(Math.random() * 4);
        }
        self.all_questions = listQ;
        self.current_task = n;
        self.question = listQ[n];
        self.processData(listQ[n], 'question_input');
        self.processData(self.points, 'point_input');
        self.answer = listA[n];
        self.processData(Array(listA[n].length+1).join("_"), 'input_fields');
    },
    processData: function (str, block) {
        var self = this,
            str_length = str.length;
        self.buildBlocks(str, str_length, block);
    },
    buildBlocks: function (str, length, block) {
        var self = this,
            valid_char = $("<span class = 'valid_char'></span>"),
            input_field_box = $("#" + block);
        if(block === "input_fields"){
            var div = document.getElementById(block);
            div.innerHTML = "";
            for (var i = 0; i < length; i++) {
                //TODO: remove hard-coding
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
            //TODO: check the input validity over here. Make the AJAX call here
        if (position > -1 && val.trim() !== "") {
            if ($(".valid_char:eq(" + position + ")").text() === val) {
                self.insertMessage("try_other");
                self.single_char.val("").removeClass("red");
            } else {
                //replace _ with the guess
                for (var i = 0; i < self.answer.length; i++) {
                    if (self.answer[i] === val)
                        $(".valid_char:eq(" + i + ")").text(val);
                }
                
                //empty the guess
                self.single_char.val("").removeClass("red");
                self.insertMessage("correct");
                blank_spots = $(".valid_char:contains('_')");
                if (blank_spots && blank_spots.length === 0) {
                    self.result = true;
                    self.checkGameStatus();
                    self.result = false;
                }                
            }
        } else {
            code = val.charCodeAt(0);
            if (code >= 33 && code <= 126 && code !== 95) {
                self.insertMessage("wrong");
            } else if (val.trim() !== "") {
                self.insertMessage("invalid");
            }
            if(val.trim() !== ""){
                self.attempts_left--;
            }
            self.attempts_left_txt.text(self.attempts_left);
            self.single_char.addClass("red");
            self.single_char.val("").removeClass("red");
        }
        if(self.attempts_left === 0) {
            //TODO: show correct value instead of _ when the user failed to guess the answer
            self.checkGameStatus();
            self.attempts_left = 5;
            self.attempts_left_txt.text(self.attempts_left);
            
        }
    },
    insertMessage: function (status) {
        var self = this,
            message = $("<li class='message'></li>"),
            message_board = $(".messages"),
            display_message = self.messages[status];
        message_board.prepend(message.clone().text(display_message).addClass(status));
    },
    checkGameStatus: function () {
        var self = this;
        if (self.result) {
            self.insertMessage("won");
            self.points += self.attempts_left * 10;
            self.task_done.push(self.current_task);
            if (self.task_done.length === self.all_questions.length){
                self.single_char.val("").attr("disabled", "disabled").removeClass("red");
            } else {
                self.init();
            }
        } else {
            self.insertMessage("lost");
            self.points -= 25;
            self.init();
        }
    }
};

var game = new Hangman('abcd');
game.init();