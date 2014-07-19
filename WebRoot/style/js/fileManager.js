var nowid;

function formatFile(fileMD5, name, uploader, time) {
	var filelist = document.getElementById("filelist");
	var line = document.createElement("tr");
	var filename = document.createElement("td");
	var fileuploader = document.createElement("td");
	var filetime = document.createElement("td");
	var buttons = document.createElement("td");
	var info = document.createElement("button");
	var filedelete = document.createElement("button");
	filename.setAttribute("id", "md5");
	filename.setAttribute("value", fileMD5);
	filename.innerHTML = name;
	line.setAttribute("style", "display:none");
	fileuploader.innerHTML = uploader;
	var d = new Date();
	d.setTime(time);
	filetime.innerHTML = d.toLocaleString();
	info.setAttribute("data-toggle", "modal");
	info.setAttribute("data-target", "#checkInfo");
	info.setAttribute("class", "btn btn-sm btn-info");
	info.setAttribute("onclick", "getInfo(this)");
	info.setAttribute("style", "margin-right:30px");
	info.innerHTML = "查看详细信息";
	filedelete.setAttribute("data-toggle", "modal");
	filedelete.setAttribute("data-target", "#deleteVideo");
	filedelete.setAttribute("class", "btn btn-sm btn-danger");
	filedelete.setAttribute("onclick", "changeHiddenValue(this)");
	filedelete.innerHTML = "删除文件";
	filetime.setAttribute("id", "uptime");
	filetime.setAttribute("value", time);
	line.appendChild(filename);
	line.appendChild(fileuploader);
	line.appendChild(filetime);
	line.appendChild(buttons);
	buttons.appendChild(info);
	buttons.appendChild(filedelete);
	filelist.appendChild(line);
}

function changeHiddenValue(id) {
	var filemd5 = id.parentNode.parentNode.childNodes[0].getAttribute("value");
	var uploader = id.parentNode.parentNode.childNodes[1].innerHTML;
	var time = id.parentNode.parentNode.childNodes[2].getAttribute("value");

	document.getElementById("filemd5").value=filemd5;
	document.getElementById("uploader").value=uploader;
	document.getElementById("time").value=time;
	nowid = id;
}

function deleteFile(){
	$.ajax({
		type: "GET",
		url: "managerDeleteFile.action",
		data: {
			MD5: $("#filemd5").val(),
			userEmail: $("#uploader").val(),
			uploadTime: $("#time").val()
		},
		dataType: "json",
		success: function(data) {
			nowid.parentNode.parentNode.parentNode.removeChild(nowid.parentNode.parentNode);
			Messenger().post({
				  message: data,
				  hideAfter: 5
			});
		}
	});
}

function getInfo(id) {
	var filemd5 = id.parentNode.parentNode.childNodes[0].getAttribute("value");
	var fileName = id.parentNode.parentNode.childNodes[0].innerHTML;
	var uploader = id.parentNode.parentNode.childNodes[1].innerHTML;
	var time = id.parentNode.parentNode.childNodes[2].getAttribute("value");
	$.ajax({
		type: "GET",
		url: "getFileInfo.action",
		data: {
			MD5: filemd5,
			userEmail: uploader,
			uploadTime: time
		},
		dataType: "json",
		success: function(data) {
			var value = eval("("+data+")");
			formatInfo(fileName, uploader, value.size, time,
					value.tag1, value.tag2, value.tag3, value.tag4, value.tag5);
		}
	});
}

function formatInfo(name, uploader, size, time, tag1, tag2, tag3, tag4, tag5) {
	var d = new Date();
	d.setTime(time);
	$("#nameInfo").text(name);
	$("#uploaderInfo").text(uploader);
	$("#sizeInfo").text(size);
	$("#timeInfo").text(d.toLocaleString());
	
	var flag = 0;
	if (typeof(tag1)!="undefined" && tag1!=0) {
		$("#tag1").text(tag1);
		flag = 1;
	}
	if (typeof(tag2)!="undefined" && tag2!=0) {
		$("#tag2").text(tag2);
		flag = 1;
	}
	if (typeof(tag3)!="undefined" && tag3!=0) {
		$("#tag3").text(tag3);
		flag = 1;
	}
	if (typeof(tag4)!="undefined" && tag4!=0) {
		$("#tag4").text(tag4);
		flag = 1;
	}
	if (typeof(tag5)!="undefined" && tag5!=0) {
		$("#tag5").text(tag5);
		flag = 1;
	}
	if (flag == 0) {
		$("#tags").text('"这家伙很懒，什么标签都没有。"'); 
	}

}