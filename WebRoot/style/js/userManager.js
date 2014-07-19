$(document).ready(function() {
	$(document).on("click", ":checkbox", function() {
		if ($(this).is(":checked") == true) {
			if ($("input[type='checkbox']:checked").length == 1) {
				$("#checkInfo").removeAttr("disabled");
				$("#deleteUser").removeAttr("disabled");
				$("#disableUser").removeAttr("disabled");
				$("#activeUser").removeAttr("disabled");
			}
			if ($("input[type='checkbox']:checked").length > 1) {
				$("#checkInfo").attr("disabled", "true");
				$("#deleteUser").removeAttr("disabled");
				$("#disableUser").removeAttr("disabled");
				$("#activeUser").removeAttr("disabled");
			}
		} else {
			if ($(this).is(":checked") != $("#chooseAll").is(":checked")) {
				$("#chooseAll").attr('checked', false);
			}
			if ($("input[type='checkbox']:checked").length == 1) {
				$("#checkInfo").removeAttr("disabled");
				$("#deleteUser").removeAttr("disabled");
				$("#disableUser").removeAttr("disabled");
				$("#activeUser").removeAttr("disabled");
			}
			if ($("input[type='checkbox']:checked").length == 0) {
				$("#checkInfo").attr("disabled", "true");
				$("#deleteUser").attr("disabled", "true");
				$("#disableUser").attr("disabled", "true");
				$("#activeUser").attr("disabled", "true");
			}
		}
	});
});

$(document).ready(function() {
	$("#chooseAll").click(function() {
		if ($("#chooseAll").is(":checked")) {
			$.each($(":checkbox"), function() {
				if ($(this).is(":checked") == false) {
					$(this).click();
				}
			});
		} else {
			$.each($(":checkbox"), function() {
				if ($(this).is(":checked") == true) {
					$(this).click();
				}
			});
		}
	});
});

function formateUser(email, nickname, level, isDisable) {
	var list = document.getElementById("userlist");
	var infoLine = document.createElement("tr");
	var checkbox = document.createElement("td");
	var checkboxInfo = document.createElement("input");
	var useremail = document.createElement("td");
	var usernickname = document.createElement("td");
	var userlevel = document.createElement("td");
	var disable = document.createElement("td");
	checkboxInfo.setAttribute("type", "checkbox");
	checkboxInfo.setAttribute("value", email);
	useremail.innerHTML = "<img src='style/pic/user.jpg' style='width:14px;height:14px;''><b>" + email + "</b>";
	usernickname.innerHTML = "<b>" + nickname + "</b>";
	userlevel.innerHTML = "VIP" + level;
	disable.innerHTML = isDisable;
	infoLine.appendChild(checkbox);
	checkbox.appendChild(checkboxInfo);
	infoLine.appendChild(useremail);
	infoLine.appendChild(usernickname);
	infoLine.appendChild(userlevel);
	infoLine.appendChild(disable);
	list.appendChild(infoLine);
}

function formateInfobox(email, headName, nickname, sex, birthday, level, introduction) {
	var path;
	if(typeof(headName)!="undefined" && headName!=0){
		path = "style/uploadHead/"+headName;
	}else{
		path = "style/pic/user.png";
	}
	$("#headImg").attr("src", path);
	$("#emailInfo").text(email);
	$("#nicknameInfo").text(nickname);
	$("#sexInfo").text(sex);
	$("#birthdayInfo").text(birthday);
	$("option").each(function(){
		if($(this).text() == ("VIP" + level)) {
			$(this).attr("selected", "true");
		}
	});
	if (introduction != "") {
		$("#introductionInfo").text(introduction);
	}
}

$(document).ready(function(){
	$("#checkInfo").click(function(){
		$.each($(":checkbox"),function(){
			if ($(this).is(":checked")) {
				var tempElement = this;
				var email = tempElement.parentNode.nextSibling.childNodes[1].innerHTML;
				
			  	$.ajax({
			  		type: "GET",
			  		url: "getUserInfo.action",
			  		data: {
			  			userEmail: email
			  		},
			  		dataType: "json",
			  		success: function(data) {
			  		var value = eval("("+data+")");
			  		formateInfobox(value.userEmail, value.headName, value.nickName,
			  				value.sex, value.birthday, value.level, value.introduction);
			  		}
			  	});
			}
		});
	});
});

$(document).ready(function(){
	$("#deleteButton").click(function(){
		$.each($(":checkbox"),function(){
			if ($(this).is(":checked")) {
				var tempElement = this;
				var email = tempElement.parentNode.nextSibling.childNodes[1].innerHTML;
				$.ajax({
					type: "GET",
					url: "deleteUsers.action",
					data: {
						userEmail: email
					},
					dataType: "json",
					success: function(data) {
						tempElement.parentNode.parentNode.parentNode.removeChild(tempElement.parentNode.parentNode);
						Messenger().post({
							  message: data,
							  hideAfter: 5
						});
					}
				});
			}
		});
	});
});

$(document).ready(function(){
	$("#disableButton").click(function(){
		$.each($(":checkbox"),function(){
			if ($(this).is(":checked")) {
				var tempElement = this;
				var email = tempElement.parentNode.nextSibling.childNodes[1].innerHTML;
				$.ajax({
					type: "GET",
					url: "disableUsers.action",
					data: {
						userEmail: email
					},
					dataType: "json",
					success: function(data) {
						tempElement.parentNode.parentNode.childNodes[4].innerHTML="是";
						Messenger().post({
							  message: data,
							  hideAfter: 5
						});
					}
				});
			}
		});
	});
});

$(document).ready(function(){
	$("#activeButton").click(function(){
		$.each($(":checkbox"),function(){
			if ($(this).is(":checked")) {
				var tempElement = this;
				var email = tempElement.parentNode.nextSibling.childNodes[1].innerHTML;
				$.ajax({
					type: "GET",
					url: "activeUsers.action",
					data: {
						userEmail: email
					},
					dataType: "json",
					success: function(data) {
						tempElement.parentNode.parentNode.childNodes[4].innerHTML="否";
						Messenger().post({
							  message: data,
							  hideAfter: 5
						});
					}
				});
			}
		});
	});
});