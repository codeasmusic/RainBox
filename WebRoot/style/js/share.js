function formatRow(md5, name, sharer, headsrc, size, uptime, 
		tag1, tag2, tag3, tag4, tag5, downloadText, saveText, playText) {
	var newrow = document.createElement("div");
	var panel = document.createElement("div");
	var panelhead = document.createElement("div");
	var info = document.createElement("div");
	var filenameL = document.createElement("div");
	var filename = document.createElement("a");
	var userhead = document.createElement("div");
	var filesize = document.createElement("div");
	var fileuploadtime = document.createElement("div");
	var panelbody = document.createElement("div");
	var otherinfo = document.createElement("div");
	var tags = document.createElement("div");
	var t1 = document.createElement("span");
	var t2 = document.createElement("span");
	var t3 = document.createElement("span");
	var t4 = document.createElement("span");
	var t5 = document.createElement("span");
	var ops = document.createElement("div");
	var save = document.createElement("button");
	var download = document.createElement("a");
	var play=document.createElement("button");
	newrow.setAttribute("class", "row");
	panel.setAttribute("class", "panel panel-info");
	panel.setAttribute("style", "margin:0px;");
	panelhead.setAttribute("class", "panel-heading");
	info.setAttribute("class", "row");
	filenameL.setAttribute("class", "col-md-5");
	filename.setAttribute("class", "btn btn-link btn-sm");
	filename.setAttribute("data-toggle", "collapse");
	filename.setAttribute("data-target", "#" + md5);
	filename.setAttribute("style", "padding:0px;font-size:14px;");

	filename.innerHTML = "<img src='style/pic/movie.jpg' style='width:14px;height:14px;'>" +"    "+ name;
	userhead.setAttribute("class", "col-md-2");

	userhead.innerHTML = "<img src='" + headsrc + "' style='width:20px;height:20px;'>" + sharer;
	filesize.setAttribute("class", "col-md-1");
	filesize.innerHTML = size + "B";
	fileuploadtime.setAttribute("class", "col-md-2 col-md-offset-2");
	
	var d = new Date();
	d.setTime(uptime);
	fileuploadtime.innerHTML = d.toLocaleString();
	panelbody.setAttribute("class", "panel-body collapse");
	panelbody.setAttribute("id", md5);
	otherinfo.setAttribute("class", "row");
	tags.setAttribute("class", "col-md-9");
	if (tag1 != null) {
		t1.setAttribute("class", "label label-success");
		t1.setAttribute("style","margin:0px 5px;");
		t1.innerHTML = tag1;
	}
	if (tag2 != null) {
		t2.setAttribute("class", "label label-success");
		t2.setAttribute("style","margin:0px 5px;");
		t2.innerHTML = tag2;
	}
	if (tag3 != null) {
		t3.setAttribute("class", "label label-success");
		t3.setAttribute("style","margin:0px 5px;");
		t3.innerHTML = tag3;
	}
	if (tag4 != null) {
		t4.setAttribute("class", "label label-success");
		t4.setAttribute("style","margin:0px 5px;");
		t4.innerHTML = tag4;
	}
	if (tag5 != null) {
		t5.setAttribute("class", "label label-success");
		t5.setAttribute("style","margin:0px 5px;");
		t5.innerHTML = tag5;
	}
	ops.setAttribute("class", "col-md-3");
	
	var time = new Date();
	var transferTime = time.getTime();
	
	save.setAttribute("class", "btn btn-link btn-xs");
	save.setAttribute("style", "float:right;")
	save.setAttribute("onclick", 'transferToMe("'+md5+'","'+ name +'","'+ size +'","'+ transferTime + '")');
	save.innerHTML = saveText;
	
	download.setAttribute("class", "btn btn-link btn-xs");
	download.setAttribute("style", "float:right;");
	download.setAttribute("href", "download.action?MD5="+md5+"&sharer="+sharer);
	download.innerHTML = downloadText;
	
	play.setAttribute("class", "btn btn-link btn-xs");
	play.setAttribute("style", "float:right;");
	play.setAttribute("onclick", 'window.open("playAVI.action?MD5='+md5+'&fileName='+name+'")');
	play.innerHTML = playText;
	var list = document.getElementById("filelist");
	newrow.appendChild(panel);
	panel.appendChild(panelhead);
	panelhead.appendChild(info);
	info.appendChild(filenameL);
	filenameL.appendChild(filename);
	info.appendChild(userhead);
	info.appendChild(filesize);
	info.appendChild(fileuploadtime);
	panel.appendChild(panelbody);
	panelbody.appendChild(otherinfo);
	otherinfo.appendChild(tags);
	if (tag1 != null) {
		tags.appendChild(t1);
	}
	if (tag2 != null) {
		tags.appendChild(t2);
	}
	if (tag3 != null) {
		tags.appendChild(t3);
	}
	if (tag4 != null) {
		tags.appendChild(t4);
	}
	if (tag5 != null) {
		tags.appendChild(t5);
	}
	otherinfo.appendChild(ops);
	ops.appendChild(play);
	ops.appendChild(save);
	ops.appendChild(download);
	list.appendChild(newrow);
}

function transferToMe(md5, name, size, transferTime){
	$.ajax({
		type: "GET",
		url: "transferToMe.action",
		data: {
			MD5: md5,
			fileName: name,
			size: size,
			transferTime: transferTime
		},
		dataType: "json",
		success: function(data) {
			var value = eval("("+data+")");
			Messenger().post({
				  message: value.status,
				  hideAfter: 3
			});
		}
	});
}

function formatMyShareFiles(md5, fileName, size, time){
	var newFile = document.createElement("tr");
	var checkbox = document.createElement("td");
	var Filename = document.createElement("td");
	var Filesize = document.createElement("td");
	var Filetime = document.createElement("td");
	var FileIcon = document.createElement("img");
	var FileLink = document.createElement("a");
	var name = document.createTextNode(fileName);
	var d = new Date();
	d.setTime(time);
	
	FileIcon.setAttribute('src', 'style/pic/movie.jpg');
	FileIcon.setAttribute('style', 'width:14px;height:14px;');
	FileLink.setAttribute('href', 'playAVI.action?MD5='+md5);
	FileLink.setAttribute('id', 'filemd5');
	FileLink.setAttribute('name', time);
	FileLink.setAttribute('value', md5);
	Filename.appendChild(FileIcon);
	Filename.appendChild(FileLink);
	FileLink.appendChild(name);
	newFile.appendChild(checkbox);
	newFile.appendChild(Filename);
	newFile.appendChild(Filesize);
	newFile.appendChild(Filetime);
	checkbox.innerHTML = "<input type='checkbox' name='file'>";
	Filesize.innerHTML = size;
	Filetime.innerHTML = d.toLocaleString();
	document.getElementById("filelist").appendChild(newFile);
}

$(document).ready(function() {
	$("#cancelShare").click(function() {
		$.each($(":checkbox"),function() {
			if ($(this).is(":checked")) {
				var tempElement=this; 
				var MD5=tempElement.parentNode.nextSibling.childNodes[1].getAttribute("value");
				var time=tempElement.parentNode.nextSibling.childNodes[1].getAttribute("name");
				var fn=tempElement.parentNode.nextSibling.childNodes[1].innerHTML;
				$.ajax({
					type: "GET",
					url: "cancelShare.action",
					data: {
						MD5: MD5,
						uploadTime: time,
						fileName: fn
					},
					dataType: "json",
					success: function(data) {
						var value = eval("("+data+")");
						tempElement.click();
						tempElement.parentNode.parentNode.parentNode.removeChild(tempElement.parentNode.parentNode);
						Messenger().post({
							  message: value.status,
							  hideAfter: 3
						});
					}
				});
			}
		});
	});
});

$(document).ready(function() {
	$(document).on("click", ":checkbox", function() {
		if ($(this).is(":checked") == true) {
			if ($("#cancelShare").is(":disabled") == true) {
				$("#cancelShare").removeAttr("disabled");
			}
		} else {
			if ($("input[type='checkbox']:checked").length == 0) {
				$("#cancelShare").attr("disabled", "true");
			}
		}
	});
});
