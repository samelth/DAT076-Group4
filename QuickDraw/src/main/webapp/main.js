/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/* Time unit in ms*/
var progressBarVal = 0;
/* Time unit in ms*/
var progressBarDelay = 1000;
$("#butt").click(function(){
    console.log("clicked");
 });
function joinGame(){
  var gamecode = document.getElementById("gameCode");
  var username = document.getElementById("playerUsername");
  console.log(gamecode.value);
  console.log(username.value);
}

function jump(page){
  window.location.href = page;
}
function hideElem(tag){
  $(tag).hide();
}
function showElem(tag){
  $(tag).show();
}
function startProgressBar(){
  setInterval(updateProgressBar, progressBarDelay);
}

function updateProgressBar(){
  let progress= document.getElementById("p1");
  var x = $("#p1").attr("aria-valuenow", progressBarVal); 
  var x = $("#p1").css("width", progressBarVal + "%"); 
  progressBarVal += 1;
  console.log(x);
}
function copyToCB(textindentifier){
  var selectedText = document.getElementById(textindentifier);
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

var toastElList = [].slice.call(document.querySelectorAll('.toast'))
var toastList = toastElList.map(function (toastEl) {
  return new bootstrap.Toast(toastEl, option)
})

/*var canvas = document.getElementById("myCanvas");
canvas.addEventListener("mousedown", function(e){
  canvas.onmousemove= function(e){
    var ctx = canvas.getContext("2d");
    ctx.beginPath();
    ctx.moveTo(e.x,e.y); 
     setTimeout(function (){
      ctx.lineTo(e.x,e.y);
      ctx.stroke();
    } , 1000);
    
  }
});

canvas.addEventListener("mouseup", function(e){
  canvas.onmousemove=null;
})
*/

//TODO implement a timer for the counter 20,19...  
//TODO syncronize the progressbar with the counter
