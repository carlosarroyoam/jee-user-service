/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

window.onload = init;

function onOpen(event) {
    document.getElementById("messageContainer").innerHTML = "Conectado";
}

function onClose(event) {
    document.getElementById("messageContainer").innerHTML = "Desconectado";
}

function onMessage(event) {
    document.getElementById("messageContainer").innerHTML = event.data;
}

function init() {
    var socket = new WebSocket("ws://localhost:8082/development/websockets/notificationservice");
    socket.onmessage = onMessage;
    socket.onopen = onOpen;
    socket.onclose = onClose;
}
