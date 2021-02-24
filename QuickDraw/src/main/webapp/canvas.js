/* 
 * Copyright (C) 2021 Fawzi Aiboud Nygren & Victor Wallsten <3
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

// When document is ready, execute script
window.addEventListener('load', () =>{
  // Gets element with ID canvas
  const canvas = document.querySelector('#canvas');
  // Defines context to work within, 2D in this case
  const context = canvas.getContext('2d');
  
  // Initialize size
  canvas.height = window.innerHeight*(1/3);
  canvas.width = window.innerWidth*(1/3);
  
  // Initialize paint
  let drawing = false;
  
  function startPosition (e) {
    drawing = true;
    draw(e);
  }
  
  function endPosition () {
    drawing = false;
    context.beginPath();
  }
  
  function draw (e) {
    if (!drawing) return;
    
    // Pen styles
    context.lineWidth = 3.5;
    context.lineCap = 'round';
    
    context.lineTo(e.clientX - canvas.offsetLeft, e.clientY - canvas.offsetTop);
    context.stroke();
    context.beginPath();
    context.moveTo(e.clientX - canvas.offsetLeft, e.clientY - canvas.offsetTop);
  }
  
  // Mouse EventListers
  canvas.addEventListener('mousedown', startPosition);
  canvas.addEventListener('mouseup', endPosition);
  canvas.addEventListener('mousemove', draw);
  canvas.addEventListener('mouseleave', endPosition);
});

window.addEventListener('resize', () =>{
  //Resizing to relative window size
  const canvas = document.querySelector('#canvas');
  canvas.height = window.innerHeight*(1/3);
  canvas.width = window.innerWidth*(1/3);
});

