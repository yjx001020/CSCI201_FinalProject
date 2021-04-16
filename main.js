//get general info
let endpoint = "test.txt";
let ids = []; //get refreshed?
let currentStudentId = 123456789;
ajax(endpoint, displayGeneralInfo);


function ajax(endpoint, returnFunction) {
	let httpRequest = new XMLHttpRequest();

	httpRequest.open("GET", endpoint);
	httpRequest.send();
	httpRequest.onreadystatechange = function() {
		console.log(httpRequest.readyState);

		if( httpRequest.readyState == 4) {

			if (httpRequest.status == 200) {
				returnFunction(httpRequest.responseText);
			} else {
				console.log("ERRORRRR!!");
				console.log(httpRequest.status);
			}
		}
	}
}

function displayGeneralInfo(results) {

	// Clear out all previous results before showing new ones

	let queue = document.querySelector("#currentStatus");
	while( queue.hasChildNodes() ) {
		queue.removeChild( queue.lastChild )
	}


	// console.log(results);

	let convertedResults = JSON.parse(results);

	console.log(convertedResults);

	document.querySelector("#queueStatus").innerHTML = convertedResults.queueStatus;
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

		ids.push(id);

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
		if (id == currentStudentId) {
			var b = document.createElement('button');
			b.innerHTML = "Leave the Queue";
			b.classList.add('btn');
			b.classList.add('btn-primary');
			b.classList.add('dele');
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

// document.querySelector("#leave").onclick = function(event) {
//send {studentName, studentID, leave}
/*
var remove = document.querySelectorAll('.dele');
			for(var i = 0; i < remove.length; i++){
				remove[i].onclick = function(){
					var tem = this.previousSibling.previousSibling.previousSibling.innerHTML;
					var nn = tem.replace("Student ID: ", "");
					console.log(nn);
					var r = confirm("You are about the leave the queue and lose your current position...");
					if (r == true) {
						//delete id in the queue
						for( var j = 0; j < ids.length; j++){ 
        					if (ids[j] == nn) { 
        						ids.splice(j, 1); 
        					}
        				}
        				//reduce the number of students in the queue
						var temp  = parseInt(document.querySelector('#num').innerHTML);
						temp -= 1;
						document.querySelector('#num').innerHTML = temp;
  						this.parentNode.remove();
					} 				
				}
			}
*/
// }

document.querySelector("#addToQueue").onsubmit = function(event) {

	event.preventDefault();

	console.log("submitted!");


	var name = document.querySelector('#inputName').value.trim();
	var id = document.querySelector('#studentId').value.trim();
	var description = document.querySelector('#comment').value.trim();
	var topic = displayRadioValue();
	
	//check student is not already in queue
	if(name.length > 0 && id.length > 0){
		// check if student is already on list
		for(var i = 0; i < ids.length; i++){
			if(id == ids[i]){
				alert("Student with ID: (" + id + ") is already in the Office Hour queue.");
				return false;
			}
		}

		//add the student to the queue
		document.querySelector('#name-error').innerHTML = "";
		document.querySelector('#id-error').innerHTML = "";
		var card = document.createElement('div');
		var d1 = document.createElement('div');
		var h = document.createElement('h5');
		var p1 = document.createElement('p');
		var p2 = document.createElement('p');
		var p3 = document.createElement('p');
		var b = document.createElement('button');

		card.classList.add('card');
		d1.classList.add('card-body');
		d1.classList.add('ma');
		b.classList.add('btn');
		b.classList.add('btn-primary');
		b.classList.add('dele');
		
		d1.appendChild(h);
		d1.appendChild(p1);
		d1.appendChild(p2);
		d1.appendChild(p3);
		d1.appendChild(b);
		card.appendChild(d1);

		document.querySelector('#currentStatus').appendChild(card);
		h.innerHTML = "Student Name: "+ name;
		p1.innerHTML = "Student ID: "+ id;
		p2.innerHTML = "Question Type: "+ type;
		p3.innerHTML = "Description: "+ des;
		b.innerHTML = "Leave the Queue";
		// document.querySelector("#studentCount").innerHTML += 1;

		ids.push(id);
		document.querySelector('#inputName').value = "";
		document.querySelector('#studentId').value = "";
		document.querySelector('#comment').value = "";

	}else if(name.length == 0 && id.length > 0){
		document.querySelector('#name-error').innerHTML = "This field is required.";   //field missing
	}else if(name.length > 0 && id.length == 0){
		document.querySelector('#id-error').innerHTML = "This field is required.";     //field missing
	}else{
		document.querySelector('#name-error').innerHTML = "This field is required.";   //field missing
		document.querySelector('#id-error').innerHTML = "This field is required.";
	}


}

function displayRadioValue() {
	var ele = document.getElementsByName('topic');  
	for(i = 0; i < ele.length; i++) {
    	if(ele[i].checked){
    		return ele[i].value;
    	}
    }
}
