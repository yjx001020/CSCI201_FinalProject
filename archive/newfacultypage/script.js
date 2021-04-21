var socket;
socket = new WebSocket("ws://localhost:8080/CSCI201_FinalProject/ws");
var count = document.querySelectorAll(".ma").size();
document.querySelector("#num").innerHTML = count;
document.querySelector("#b1").onclick() = function(){
    var name = document.querySelector("#fname").innerHTML;
    var data = JSON.stringify({"username": name, "action": "1"});
    document.getElementById("#b1").disabled = true;
    document.getElementById("#cast").disabled = false;
    document.getElementById("#checkoff").disabled = false;
    document.getElementById("#b2").disabled = false;
    socket.send(data);
}
document.querySelector("#b2").onclick() = function(){
    var name = document.querySelector("#fname").innerHTML;
    var data = JSON.stringify({"username": name, "action": "0"});
    document.getElementById("#b1").disabled = false;
    document.getElementById("#cast").disabled = true;
    document.getElementById("#checkoff").disabled = true;
    document.getElementById("#b2").disabled = true;
    socket.send(data);
}
document.querySelector("#cast").onclick() = function(){
    var msg = document.querySelector("#castdata").value();
    var data = JSON.stringify({"message": msg});
    socket.send(data);
}
document.querySelector("#checkoff").onclick = function(){
    var name = document.querySelector("h5").innerHTML;
    var id = document.querySelector(".sid").innerHTML;
    var type = document.querySelector(".qy").innerHTML;
    var description = document.querySelector(".des").innerHTML;
    var data = JSON.stringify({"name": name, "id": id, "type": type, "description": description});
    document.querySelector("#checkoff").nextElementSibling.remove();
    socket.send(data);

}
socket.onmessage = function(e) {
    var obj = JSON.parse(e.data);
    var card = document.createElement('div');
    var d1 = document.createElement('div');
    var h = document.createElement('h5');
    var p1 = document.createElement('span');
    var p2 = document.createElement('span');
    var p3 = document.createElement('span');

    card.classList.add('card');
    d1.classList.add('card-body');
    d1.classList.add('ma');
    p1.classList.add('sid');
    p2.classList.add('qy');
    p3.classList.add('des');
                
    d1.appendChild(h);
    d1.appendChild(p1);
    d1.appendChild(p2);
    d1.appendChild(p3);
    card.appendChild(d1);

    document.querySelector('#info').appendChild(card);
    h.innerHTML = "Student Name: "+ obj.name;
    p1.innerHTML = "Student ID: "+ obj.id;
    p2.innerHTML = "Question Type: "+ obj.type;
    p3.innerHTML = "Description: "+ obj.description;
}

socket.onclose = function(e) {
    document.getElementById("#b1").disabled = true;
    document.getElementById("#cast").disabled = true;
    document.getElementById("#checkoff").disabled = true;
    document.getElementById("#b2").disabled = true;
}







