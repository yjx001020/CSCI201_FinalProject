
var socket;
function connectToServer() {
	// backend @ServerEndpoint(value = "/studentPage")
	socket = new WebSocket("ws://localhost:8080/CSCI201_FinalProject/studentPage");
	socket.onopen = function(event) {
		document.getElementById("debug").innerHTML += "Connected!<br />";
	}
	//Server should send JSON info to fronend immediately @OnOpen
	socket.onmessage = function(event) {
		//server sends JSON info like below
		/*{
			"queueStatus": "Running",

			"studentCount": "2",

			"zoomLink": "https://usc.zoom.us/j/3439397859",

			"staffs": [{
					"staffName": "Randall",
					"email": "randall@usc.edu"
				},
				{
					"staffName": "Ryan",
					"email": "varun@usc.edu"
				}
			],

			"announcement": "Please add yourself to the queue when you are ready to be checked off.",

			"students": [{
					"studentName": "Tommy",
					"studentID": "123456789",
					"topic": "checkoff",
					"description": ""
				},

				{
					"studentName": "Trojan",
					"studentID": "234567890",
					"topic": "question",
					"description": "hello world"
				}
			]
		}*/
		displayGeneralInfo(event.data);
	}
	socket.onclose = function(event) {
		document.getElementById("debug").innerHTML += "Disconnected!<br />";
	}
}


function add() {
	// add to queue, send to backend {studentName, studentID, topic, description, leave}
	let student = {
    	"studentName": document.addForm.studentName.value,
    	"studentID": document.addForm.studentId.value,
    	"topic": document.addForm.topic.value,
    	"description": document.addForm.description.value,
    	"leave": "false"
    }

	let studentJson = JSON.stringify(student);
	console.log(studentJson);
	socket.send(studentJson);
	return false;
}

function remove(){
	console.log("remove!");
	var r = confirm("You are about to the leave the queue and lose your current position...");
	if (r == true) {
		let leaveMessage = {
	    	"studentName": document.cookie.split('=')[1],
	    	"studentID": "",
	    	"topic": "",
	    	"description": "",
	    	"leave": "true"
	    }

		let leaveJson = JSON.stringify(leaveMessage);
		console.log(leaveJson);
		socket.send(leaveJson);
	} 				
}

function displayGeneralInfo(results) {

	// Clear out all previous results before showing new ones
	let queue = document.querySelector("#currentStatus");
	while( queue.hasChildNodes() ) {
		queue.removeChild( queue.lastChild )
	}

	console.log(results);
	let convertedResults = JSON.parse(results);

	console.log(convertedResults);

	document.querySelector("#queueStatus").innerHTML = convertedResults.queueStatus;
	if (convertedResults.queueStatus == "Closed") {
		document.querySelector("#submit").setAttribute("disabled", true);
		document.querySelector("#zoomLink").innerHTML = "";
		document.querySelector("#TA").innerHTML = "None";
		document.querySelector("#announcement").innerHTML = "The queue is closed. Check back later!";
	} else {
		document.querySelector("#studentCount").innerHTML = convertedResults.studentCount;
		document.querySelector("#zoomLink").innerHTML = convertedResults.zoomLink;
		document.querySelector("#zoomLink").setAttribute("href", convertedResults.zoomLink);
		document.querySelector("#announcement").innerHTML = convertedResults.announcement;
		document.querySelector("#TA").innerHTML = "";
		
		let staffs = convertedResults.staffs;

		for(let i = 0; i < staffs.length; i++) {
			let staffName = staffs[i].staffName;
			let email = staffs[i].email;
			let div = document.createElement("div");
			let link = document.createElement("a");
	    	link.setAttribute("href", "mailto:"+email);
			link.innerHTML = staffName;
			div.append(link);
			document.querySelector("#TA").appendChild(div);
		}
		
		//fill the current queue status
		let students = convertedResults.students;
		for(let i = 0; i < students.length; i++) {
			let name = students[i].studentName;
			let id = students[i].studentID;
			let topic = students[i].topic;
			let description = students[i].description;

			var card = document.createElement('div');
			var d1 = document.createElement('div');
			var h = document.createElement('h5');
			var p1 = document.createElement('p');
			var p2 = document.createElement('p');
			var p3 = document.createElement('p');

			card.classList.add('card');
			d1.classList.add('card-body');
			d1.classList.add('ma');
			
			d1.appendChild(h);
			d1.appendChild(p1);
			d1.appendChild(p2);
			d1.appendChild(p3);

			//leave button for the current student
			var username = document.cookie.split('=')[1];
			if (name == username) {
				var b = document.createElement('button');
				b.innerHTML = "Leave the Queue";
				b.classList.add('btn');
				b.classList.add('btn-primary');
				b.classList.add('dele');
				b.setAttribute("onclick", "remove()");
				d1.appendChild(b);
			}
			card.appendChild(d1);

			document.querySelector('#currentStatus').appendChild(card);
			h.innerHTML = "Student Name: "+ name;
			p1.innerHTML = "Student ID: "+ id;
			p2.innerHTML = "Question Type: "+ topic;
			if (description.length == 0) {
				d1.removeChild(d1.lastChild);
			} else {
				p3.innerHTML = "Description: "+ description;
			}
		}
	}
}



