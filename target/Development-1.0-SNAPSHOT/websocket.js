/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

window.onload = init;

var socket = new WebSocket("ws://localhost:8082/development/websockets/notificationservice");
socket.onmessage = onMessage;
socket.onopen = onOpen;
socket.onclose = onClose;

function onOpen(event) {
    alert("Conectado");
}

function onClose(event) {
    alert("Desconectado");
}

function onMessage(event) {
    alert(event.data);
}

function init() {
}
