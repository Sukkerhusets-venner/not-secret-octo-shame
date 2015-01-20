<%-- 
    Document   : index
    Created on : Jan 8, 2015, 10:31:51 AM
    Author     : root
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<!-- import all the scripts here-->
  <meta name="viewport" content="width=device-width, user-scalable=yes">
  <link href="resources/css/hangman.css" rel="stylesheet" type="text/css" />  
</head>
<body>
  <div class="header">
    <h1>Hangman</h1>
  </div>
  <div id="question_input"></div>
  <div class="point_input"> 
      <span class="points_input_text">Points: <div id="point_input"></div></span>
      <span class="point_input"></span>
  </div>
  <div class="content">
    <div class="left_part">
      <div class="user_input">
        <span class="user_input_text message">Your Guess: </span>
        <input type="text" class="single_char" />
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
    </div>
    <div class="right_part">
      <div class="message_board">
        <h2>Message Board</h2>
        <ol class="messages" reversed="reversed">
        </ol>
      </div>
    </div>
  </div>
  <div class="footer">
  </div>
</body>
<!-- import all the scripts here-->
<script src="http://ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>
<script type="text/javascript" src="resources/js/hangman.js"></script>
</html>
