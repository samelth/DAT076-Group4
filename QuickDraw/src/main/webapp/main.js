/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/* Time units in ms*/
let progressBarVal = 0;
var progressBarDelay = 1000;
let totalPlayTime = 20;

$("#butt").click(function(){
    console.log("clicked");
 });
 
  $(document).ready(function(){
    $("#startGame").click(function(){
      var gamecode = document.getElementById("gameCode");
      var username = document.getElementById("playerUsername");
      var countdown = document.getElementById("countdown").innerHTML; 
      
      console.log(gamecode.value);
      console.log(username.value);
    })
  }); 

function jump(page){
  window.location.href = page;
}
function hideElem(tag){
  $(tag).hide();
}
function showElem(tag){
  $(tag).show();
}
function startProgressBar(indentifier){
  setInterval( function(){
    updateProgressBar(indentifier);
  } , progressBarDelay);
}

function playTime (countdown) {
  setInterval( function(){
  if(totalPlayTime <= 0){
    clearInterval(playTime);
    $(countdown).text("TIME!");
  } else {
    $(countdown).text(totalPlayTime);
  }
  totalPlayTime -= 1;
  }, progressBarDelay);
}

function updateProgressBar(){
  $("#p1").attr("aria-valuenow", progressBarVal); 
  $("#p1").css("width", progressBarVal + "%"); 
  progressBarVal += 5; // Using 5 here as we're using 20 seconds playtime, 100/5 = 20
  //console.log(x);
}

function copyToCB(textToCopyID){
  let selectedText = document.getElementById(textToCopyID);
  selectedText.select();
  document.execCommand("copy");
  $(document).ready(function() {
        $(".toast").toast("show");
      });
  $("#alert").animate({
      opacity: 1,
      top: "-=5vh"
    },1000);
  setTimeout(function (){
    $("#alert").animate({
      opacity: 0,
      top: "+=5vh"
    },1000)}
  ,2500);
}

let toastElList = [].slice.call(document.querySelectorAll('.toast'))
let toastList = toastElList.map(function (toastEl) {
  return new bootstrap.Toast(toastEl, option)
})

