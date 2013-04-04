/**
 * 
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
	var url = "http://localhost:5485/CSC4996-4997-Winter2013-MJK/faces/Advisor/GetAvailableTimes.jsp";
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
	}
	
	
	xmlhttp.open("GET",url, true);
	xmlhttp.send();
}
