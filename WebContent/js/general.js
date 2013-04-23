/**
 * Author: Mariam Mohamed
 * general.js
 * Contains all the javascript used for this application
 * Most of these functions can be reused for different pages (ie: advisor and student)
 */

function checkAll(){
	var elements = document.forms['appointments'].elements;
    for(var i = 0; i < elements.length; i++) {
    	if(elements[i].type == 'checkbox') {
    		elements[i].checked = true;
    	}
    }
}

function unCheckAll() {
	var elements = document.forms['appointments'].elements;
    for(var i = 0; i < elements.length; i++) {
    	if(elements[i].type == 'checkbox') {
    		elements[i].checked = false;
    	}
    }
	
}

function getAvailableTimes() {
	var url = "../Advisor/GetAvailableTimes.jsp";
	var date = document.getElementById("date").value;
	var advisor = document.getElementById("advisor").value;
	
	if(date == "" || advisor == "") {
		return;
	}
	
	document.getElementById("timeContainer").innerHTML= "<select style='width: 200px;'><option value=''>Getting Available Times</option></select>";
	url += "?date=" + date;
	url += "&advisor=" + advisor;
	
	var xmlhttp;
	if (window.XMLHttpRequest) {
	  xmlhttp = new XMLHttpRequest();
	} else {
	  xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
	}
	
	xmlhttp.onreadystatechange = function() {
	  if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
		  document.getElementById("timeContainer").innerHTML = xmlhttp.responseText;
	    }
	};
	
	
	xmlhttp.open("GET",url, true);
	xmlhttp.send();
}

function validateRegistration() {
	var div = document.getElementById("validate");
	var error = "";
	var email = document.getElementById("email").value;
	
	if(email == "") {
		error += "Enter an Email address.<br/>";
	}
	
	var firstName = document.getElementById("firstName").value;
	var lastName = document.getElementById("lastName").value;
	
	if(firstName == "") {
		error += "Enter your First Name.<br/>";
	}
	if(lastName == "") {
		error += "Enter your Last Name.<br/>";
	}
	
	var password = document.getElementById("password").value;
	var confirmPassword = document.getElementById("confirmPassword").value;
	

	if(password == "") {
		error += "Enter a password.<br/>";
	}
	

	if(password != confirmPassword) {
		error += "Your passwords do not match.<br/>";
	}
	
	if(error != "") {
		error = "<div class='error' style=''>" + error + "</div>";
		div.innerHTML = error;
		return false;
	}
	
	return true;
}

function showCancelled() {
	var table = document.getElementById("tbl");
	for (var i = 1, row; row = table.rows[i]; i++) {
	   //iterate through rows
	   var contents = row.cells[8].childNodes[0].childNodes[0].childNodes[0];
	   
	   if(contents.wholeText.indexOf("Cancelled") != -1) {
		   row.style.display = '';
	   }
	}
}

function hideCancelled() {
	var table = document.getElementById("tbl");
	for (var i = 1, row; row = table.rows[i]; i++) {
	   //iterate through rows
	   var contents = row.cells[8].childNodes[0].childNodes[0].childNodes[0];
	  
	   if(contents.wholeText.indexOf("Cancelled") != -1) {
		   row.style.display = 'none';
	   }
	}
}

var js_calendar;
function setup_cal(id, field) {
	if (!js_calendar) {
		js_calendar = Calendar.setup({
				animation: false,
				onSelect: function() { this.hide(); getAvailableTimes(); }
		});
	}
	
	js_calendar.manageFields(id, field, '%m/%d/%Y');
	
}


function validateAppt() {

	var div = document.getElementById("validate");
	var error = "";
	var advisor = document.getElementById("advisor").value;
	
	if(advisor == "" || advisor == "--") {
		error += "Select an advisor.<br/>";
	}
	
	var date = document.getElementById("date").value;
	var time = document.getElementById("time").value;
	
	if(date == "") {
		error += "Enter a date.<br/>";
	}
	if(time == "" || time == "--") {
		error += "Select a time.<br/>";
	}
	
	var reason = document.getElementById("reason").value;
	var major = document.getElementById("major").value;
	var standing = document.getElementById("standing").value;
	

	if(reason == "" || reason == "--") {
		error += "Select a reason.<br/>";
	}
	if(major == "" || major == "--") {
		error += "Select a major.<br/>";
	}
	if(standing == "" || standing == "--") {
		error += "Select a class standing.<br/>";
	}
	
	if(error != "") {
		error = "<div class='error' style=''>" + error + "</div>";
		div.innerHTML = error;
		return false;
	}
	
	
	return true;
}


window.onload = function() {
	var w = window,
    d = document,
    e = d.documentElement,
    g = d.getElementsByTagName('body')[0],
    x = w.innerWidth || e.clientWidth || g.clientWidth,
    y = w.innerHeight|| e.clientHeight|| g.clientHeight;
	
	var cWidth = (x - 270);
	if(document.getElementById("content") && document.getElementById("content").offsetWidth > cWidth) {
		document.getElementById("content").style.width = cWidth + 'px';	
	}
};




