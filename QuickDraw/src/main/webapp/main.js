/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/* Time unit in ms*/
let progressBarVal = 0;
/* Time unit in ms*/
let progressBarDelay = 1000;

function joinGame(){
  $(document).ready(function(){
    const gamecode = document.getElementById("gameCode");
    const username = document.getElementById("playerUsername");
   
    console.log(gamecode.value);
    console.log(username.value);
  }); 
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
function startProgressBar(indentifier){
  setInterval( function(){
    updateProgressBar(indentifier);
  } , progressBarDelay);
}

function updateProgressBar(){
  $("#p1").attr("aria-valuenow", progressBarVal); 
  $("#p1").css("width", progressBarVal + "%"); 
  progressBarVal += 1;
  console.log(x);
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

/*let canvas = document.getElementById("myCanvas");
canvas.addEventListener("mousedown", function(e){
  canvas.onmousemove= function(e){
    let ctx = canvas.getContext("2d");
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
