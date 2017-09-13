<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html id="indexhtml">
<head>
<title>Add Bulk Students</title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<link type="text/css" rel="stylesheet" media="all" 
		href="${pageContext.request.contextPath}/css/schoolStyles.css" >
<script type="text/javascript" 
			src="${pageContext.request.contextPath}/js/jquery-1.8.2.js"></script>

<script language="javascript">
	var RegExes = {
		"name" : "^[A-Z].*",
		"phoneNumber" : "^[0-9]{3}[\\s\\-][0-9]{3}[\\s\\-][0-9]{4}$"
	};

	var validate = function(elem) {
		var name = elem["name"];
		var re = RegExes[name];

		var value = checkElem(elem, re);
	}

	var doAdd = function(event) {
		event = event ? event : window.event;
		var allGood = true;
		var name, status, phoneNumber;
		//Validation REs
		var nameRe = "^[A-Z].*";
		var phoneRe = "^[0-9]{3}[\\s\\-][0-9]{3}[\\s\\-][0-9]{4}$";

		var name = checkElem($('#nameInput'), nameRe);
		var phoneNumber = checkElem($('#phoneNumberInput'), phoneRe);
		var status = $('#statusInput').val();

		if (!name || !status || !phoneNumber) {
			allGood = false;
		}

		if (allGood) {
			//Set up the JSON object to send in the request
			var student = {
				"name" : name,
				"status" : status,
				"phoneNumber" : phoneNumber,
			}

			$.ajax({
				//url : "http://localhost:8080/LarkU/registration/v1/admin/students",
				url : "/LarkU/registration/v1/admin/students",
				//url : "https://localhost:8443/LarkU/registration/admin/students",
				type : "post",
				dataType : "json",
				data : student,
				data : JSON.stringify(student),
				contentType : "application/json",
				processData : false,
				success : function(data) {
					//Add it to the list of students
					var e = $('#studentList');
					e.append($('<li>').append(data.name + " - " + data.status + " - " + data.phoneNumber));

				},

				error : function(jqXHR, textStatus, errorThrown) {
					alert("textStatus is " + textStatus + ", error is " + errorThrown);
				}
			});
		}

	}

	function checkElem(elem, validRe) {
		var result = null;

		if (!$(elem).val().match(validRe)) {
			console.log("bad " + $(elem).attr("name"));
			$(elem).addClass("error");
		} else {
			$(elem).removeClass("error");
			result = $(elem).val();
		}

		return result;
	}

	$(document).ready(function() {
		//Fetch the set of current students and populate the list
		//var href = "http://localhost:8080/LarkU/registration/v1/students";
		var href = "/LarkU/registration/v1/students";
		//var href = "https://localhost:8443/LarkU/registration/students";
		$.get(href, function(values) {
			var students = values;
			//var students = values.student;
			//console.log("data = " + data);
			var e = $('#studentList');
			$.each(students, (function(index, data) {
				e.append($('<li>').append(data.name + " - " + data.status + " - " + data.phoneNumber));
			}))
		}, "json").error(function(jqXHR, textStatus, errorThrown) {
			alert("Error in Fetching Students: textStatus is " + textStatus + ", error is " + errorThrown);
		});
	});
</script>
</head>
<body>
	<h1>Add Students in Bulk</h1>
	<form id="addJQuerForm" action="someAction" method="post">
		<h2>Student Info</h2>
		<div class="table">
			<div class="tableRow">
				<label for="name" class="tableCell">Name</label> <input
					id="nameInput" class="tableCell" type="text" name="name"
					onkeyup="validate(this)" />
			</div>
			<div class="tableRow">
				<label for="phoneNumber" class="tableCell">Phone Number</label> <input
					id="phoneNumberInput" class="tableCell" type="text"
					name="phoneNumber" onkeyup="validate(this)" />
			</div>
			<div class="tableRow">
				<label for="status" class="tableCell">Status</label> <select
					id="statusInput" name="status" class="tableCell" name="status">
					<option value="FULL_TIME">Full Time</option>
					<option value="PART_TIME">Part time</option>
					<option value="HIBERNATING">Hibernating</option>
				</select>
			</div>
			<div class="tableRow">
				<div class="tableCell">
					<input type="button" value="Add" onClick="doAdd(event)" />
				</div>
			</div>
		</div>
	</form>
	<div id="listDiv" style="width:100%">
		<h2>List of Students Added</h2>
		<ol id="studentList">
		</ol>
	</div>
	<br/> <a href="${pageContext.request.contextPath}/index.jsp">Home</a></td>
</body>
</html>