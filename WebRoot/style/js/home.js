function createNewFolder() {
	var flag = 0;
	var newone = $("#newFoldername").val();
	
	if(newone==""){
		Messenger().post({
			  message: "文件夹名不能为空！",
			  hideAfter: 3
		});
		return;
	}
	
	$("a#folderId").each(function(){
		if($(this).text()==newone){
			flag=1;
		}
	});
	var newName=document.getElementById("newFoldername").value;
	if(flag==0){
		var createTime = new Date();
		$.ajax({
			type: "GET",
			url: "createFolder.action",
			data: {
				newFolderName: $("#newFoldername").val(),
				upperFolderId: $("#upperFolderId").val(),
				setTime: createTime.getTime()
			},
			dataType: "json",
			success: function(data) {
				var value = eval("(" + data + ")");

				var newFolder = document.createElement("tr");
				var checkbox = document.createElement("td");
				var Foldername = document.createElement("td");
				var Foldersize = document.createElement("td");
				var Foldertime = document.createElement("td");
				var shared = document.createElement("td");
				var FolderIcon = document.createElement("img");
				var FolderLink = document.createElement("a");
				var name = document.createTextNode(newName);
				FolderIcon.setAttribute('src', 'style/pic/folder.jpg');
				FolderIcon.setAttribute('style', 'width:14px;height:14px;');
				FolderLink.setAttribute('href', '#');
				FolderLink.setAttribute('id', 'folderId');
				FolderLink.setAttribute('value', value.newFolderId);
				FolderLink.setAttribute('onclick', 'openFolder(this)');
				Foldername.appendChild(FolderIcon);
				Foldername.appendChild(FolderLink);
				FolderLink.appendChild(name);
				newFolder.appendChild(checkbox);
				newFolder.appendChild(Foldername);
				newFolder.appendChild(Foldersize);
				newFolder.appendChild(Foldertime);
				newFolder.appendChild(shared);
				checkbox.innerHTML = "<input type='checkbox'>";
				Foldersize.innerHTML = "--";
				Foldertime.innerHTML = createTime.toLocaleString();
				shared.innerHTML = "--";
				document.getElementById("filelist").appendChild(newFolder);
				
				Messenger().post({
					  message: "文件夹创建成功！",
					  hideAfter: 3
				});
			}
		});
	}
	document.getElementById("newFoldername").value="";
}

function formatFolder(folderid, foldername, foldertime) {
	var newFolder = document.createElement("tr");
	var checkbox = document.createElement("td");
	var Foldername = document.createElement("td");
	var Foldersize = document.createElement("td");
	var Foldertime = document.createElement("td");
	var shared = document.createElement("td");
	var FolderIcon = document.createElement("img");
	var FolderLink = document.createElement("a");
	var name = document.createTextNode(foldername);
	var d = new Date();
	d.setTime(foldertime);

	FolderIcon.setAttribute('src', 'style/pic/folder.jpg');
	FolderIcon.setAttribute('style', 'width:14px;height:14px;');
	FolderLink.setAttribute('href', '#');
	FolderLink.setAttribute('id', 'folderId');
	FolderLink.setAttribute('value', folderid);
	FolderLink.setAttribute('onclick', 'openFolder(this)');
	Foldername.appendChild(FolderIcon);
	Foldername.appendChild(FolderLink);
	FolderLink.appendChild(name);
	newFolder.appendChild(checkbox);
	newFolder.appendChild(Foldername);
	newFolder.appendChild(Foldersize);
	newFolder.appendChild(Foldertime);
	newFolder.appendChild(shared);
	checkbox.innerHTML = "<input type='checkbox' name='folder'>";
	Foldersize.innerHTML = "--";
	Foldertime.innerHTML = d.toLocaleString();
	shared.innerHTML = "--";
	document.getElementById("filelist").appendChild(newFolder);
}

function formatFile(filemd5, filename, filesize, filetime, fileshared) {
	var newFile = document.createElement("tr");
	var checkbox = document.createElement("td");
	var Filename = document.createElement("td");
	var Filesize = document.createElement("td");
	var Filetime = document.createElement("td");
	var shared = document.createElement("td");
	var FileIcon = document.createElement("img");
	var FileLink = document.createElement("a");
	var name = document.createTextNode(filename);
	var d = new Date();
	d.setTime(filetime);
	
	FileIcon.setAttribute('src', 'style/pic/movie.jpg');
	FileIcon.setAttribute('style', 'width:14px;height:14px;');
	FileLink.setAttribute('href', 'playAVI.action?MD5='+filemd5+"&fileName="+filename);
	FileLink.setAttribute('target', '_blank');
	FileLink.setAttribute('id', 'filemd5');
	FileLink.setAttribute('value', filemd5);
	shared.setAttribute('name', 'shared');
	Filename.appendChild(FileIcon);
	Filename.appendChild(FileLink);
	FileLink.appendChild(name);
	newFile.appendChild(checkbox);
	newFile.appendChild(Filename);
	newFile.appendChild(Filesize);
	newFile.appendChild(Filetime);
	newFile.appendChild(shared);
	checkbox.innerHTML = "<input type='checkbox' name='file'>";
	Filesize.innerHTML = filesize;
	Filetime.innerHTML = d.toLocaleString();
	shared.innerHTML = fileshared;
	document.getElementById("filelist").appendChild(newFile);
}

//新建文件夹命名
function checkFolderName(){
	var strFolderName = document.getElementById("newFoldername").value;
	if(strFolderName.length >60){
		document.getElementById("folderNameError").innerHTML="长度不能超过60个字符！";
		return;
	}
	//命名正则 只能输入英文字母或数字或中文或（）、()、
	if(strFolderName.search(/[^\a-z\A-Z\0-9\u4E00-\u9FA5\(\)\（\）]/g,'') != -1){
		document.getElementById("folderNameIcon").className = "";
		document.getElementById("folderNameError").innerHTML="命名格式错误！不能包含一下字符：·~?*@$￥./{}[]\;";
		document.getElementById("createFolderButton").disabled = true;
		return;
	}
	else{
		document.getElementById("folderNameIcon").className = "glyphicon glyphicon-ok";
		document.getElementById("folderNameError").innerHTML = "";
		document.getElementById("createFolderButton").disabled = false;
		return;
	}
}

//文件夹重命名
function checkNewFloderName(){
	var strNewFolderName = document.getElementById("newname").value;
	if(strNewFolderName.length >60 ){
		document.getElementById("newFolderNameError").innerHTML="长度不能超过60个字符！";
		return;
	}
	//命名正则 只能输入英文字母或数字或中文或（）、()、
	if(strNewFolderName.search(/[^\a-z\A-Z\0-9\u4E00-\u9FA5\(\)\（\）]/g,'') != -1){
		document.getElementById("newFolderNameIcon").className = "";
		document.getElementById("newFolderNameError").innerHTML="命名格式错误！";
		document.getElementById("renameButton").disabled = true;
		return;
	}
	else{
		document.getElementById("newFolderNameIcon").className = "glyphicon glyphicon-ok";
		document.getElementById("newFolderNameError").innerHTML = "";
		document.getElementById("renameButton").disabled = false;
		return;
	}
}


function openFolder(id) {
	$("#upperFolderId").attr("value", id.getAttribute("value"));
	$.ajax({
		type: "GET",
		url: "enterFolder.action",
		data: {
			folderId: id.getAttribute("value")
		},
		dataType: "json",
		success: function(data) {
			var pathlist = document.getElementById("path");
			var newPath = document.createElement("li");
			newPath.setAttribute('class', 'active');
			newPath.innerHTML = id.innerHTML;
			newPath.setAttribute('value', id.getAttribute("value"));
			var temp = $("#path li:last-child").text();
			$("#path li:last-child").empty();
			$("#path li:last-child").append(
				"<a href='#'onclick='olClick(this)' >" + temp + "</a>");
			pathlist.appendChild(newPath);

			$("#filelist").empty();
			var value = eval("(" + data + ")");
			var share = "否";
			$(value.all).each(
				function(i, jsonObj) {
					if (jsonObj.type.indexOf("folder") != -1) {
						formatFolder(jsonObj.folderId, jsonObj.folderName,
							jsonObj.setTime);
					} else {
						if (jsonObj.share.indexOf("1") != -1) {
							share = "是";
						}
						formatFile(jsonObj.md5, jsonObj.fileName,
							jsonObj.size, jsonObj.uploadTime, share);
					}
				});
		},
	});
}

function olClick(id) {
	var folderid = id.parentNode.getAttribute("value");
	$("#upperFolderId").attr("value", folderid);
	
	var folder = id.innerHTML;
	var father = id.parentNode;
	while (father.nextSibling != null) {
		father.parentNode.removeChild(father.nextSibling);
	}
	father.innerHTML = folder;
	$("ol li:last-child").attr("class", "active");
	$("#filelist").empty();

	$.ajax({
		type: "GET",
		url: "enterFolder.action",
		data: {
			folderId: folderid
		},
		dataType: "json",
		success: function(data) {
			var value = eval("(" + data + ")");
			var share = "否";
			$(value.all).each(
				function(i, jsonObj) {
					if (jsonObj.type.indexOf("folder") != -1) {
						formatFolder(jsonObj.folderId, jsonObj.folderName,
							jsonObj.setTime);
					} else {
						if (jsonObj.share.indexOf("1") != -1) {
							share = "是";
						}
						formatFile(jsonObj.md5, jsonObj.fileName,
							jsonObj.size, jsonObj.uploadTime, share);
					}
				});
		}
	});
}

var counter = 0;

function addTags() {
	var newTag = document.createElement("span");
	newTag.setAttribute('class', 'label label-success');
	// newTag.setAttribute('onclick','changeTags(this)');
	if (tagContent.value.length > 0 && tagContent.value.length <= 10 && counter < 5) {
		counter = counter + 1;
		var node = document.createTextNode(tagContent.value);
		newTag.appendChild(node);
		newTag.setAttribute('id', 'tag' + counter);
		newTag.style.margin = "0px 5px";
		var element = document.getElementById("addtags");
		element.appendChild(newTag);
	}
}
function up(){
	var fileReader = new FileReader(),
		md5 = document.getElementById('fileMD5');
		blobSlice = File.prototype.mozSlice || File.prototype.webkitSlice || File.prototype.slice, file = document.getElementById("uploadfile").files[0], chunkSize = 2097152,
		// read in chunks of 2MB
		chunks = Math.ceil(file.size / chunkSize), currentChunk = 0, spark = new SparkMD5();
		fileReader.onload = function(e) {
		//console.log("read chunk nr", currentChunk + 1, "of", chunks);
		spark.appendBinary(e.target.result);
		// append binary string
		currentChunk++;
		if (currentChunk < chunks) {
			loadNext();
		} else {
			md5.value = spark.end();
			$("#upfile").click();
		}
	};
	function loadNext() {
		var start = currentChunk * chunkSize,
			end = start + chunkSize >= file.size ? file.size : start + chunkSize;

		fileReader.readAsBinaryString(blobSlice.call(file, start, end));
	}
	loadNext();
}


$(document).ready(function(){
	$("#upfile").click(function() {
		if(!$("#fileMD5").val()){
			up();
		}
		else{
			var fileObj = document.getElementById("uploadfile").files[0];
			var md5 = $("#fileMD5").val();
			var flag=0;
			var time = new Date();
			var utime = time.getTime();
			var tag1, tag2, tag3, tag4, tag5;
			
			if(fileObj.type != "video/avi"){
				Messenger().post({
					  message: '您上传文件不是avi格式，请上传avi格式的文件',
					  hideAfter: 5
				});
				document.getElementById("uploadfile").value="";
				return;
			}
			
			var M = 1024 * 1024;
			var G = M * 1024;
			var size = fileObj.size;
			
			var usedCapacity = $("#usedCapacity").val();
			var capacity = $("#capacity").val()
			var left = parseFloat(capacity) - parseFloat(usedCapacity);
			if((fileObj.size / ( G * 1.0 )) > left){
				Messenger().post({
					  message: '您上传的文件大小超过剩余空间，无法上传。',
					  hideAfter: 5
				});
				flag = 1;
			}

			if(size < M){
				size = Math.round((size / 1024)*100)/100 + 'K';
			}else if(size > M & size < G){
				size = Math.round((size / M)*100)/100 + 'M';
			}else if(size > G){
				size = Math.round((size / G)*100)/100 + 'G';
			}
			
			if ($("#tag1") != null) {
				tag1 = $("#tag1").text();
			} else {
				tag1 = "";
			}
			if ($("#tag2") != null) {
				tag2 = $("#tag2").text();
			} else {
				tag2 = "";
			}
			if ($("#tag3") != null) {
				tag3 = $("#tag3").text();
			} else {
				tag3 = "";
			}
			if ($("#tag4") != null) {
				tag4 = $("#tag4").text();
			} else {
				tag4 = "";
			}
			if ($("#tag5") != null) {
				tag5 = $("#tag5").text();
			} else {
				tag5 = "";
			}
			$.each($("a#filemd5"),function(){
				if($(this).text()==fileObj.name){
					flag=1;
					Messenger().post({
						  message: '已上传同名文件，请上传其他文件',
						  hideAfter: 5
					});
				}
			});
			if(flag==0){
				var fd = new FormData();
				fd.append("uploadfile", fileObj);
				fd.append("upperFolderId",$("#upperFolderId").val());
				fd.append("MD5",md5);
				fd.append("uploadtime",utime);
				fd.append("size",size);
				fd.append("t1",tag1);
				fd.append("t2",tag2);
				fd.append("t3",tag3);
				fd.append("t4",tag4);
				fd.append("t5",tag5);
				
				var xhr = new XMLHttpRequest();
				xhr.upload.addEventListener("progress", uploadProgress, false);
	            xhr.open("POST", "uploadfile.action");
	            xhr.send(fd);
				
	            formatFile(md5, fileObj.name, size, time.toLocaleString(), "否");
	            document.getElementById("uploadfile").value="";
	            
	            xhr.addEventListener("load", uploadComplete, false);
	            
			}
			$("#addtags").empty();
			counter=0;
			document.getElementById("tagContent").value = "";
			}
	});
});

function uploadComplete(){
	$.ajax({
		type : "GET",
		url : "StartTranscodeing.action",
		data : {
			MD5 : $("#fileMD5").val()
		},
		dataType : "json",
		success : function(data) {
			Messenger().post({
				  message: data,
				  hideAfter: 5
			});
		}
	});
}

function uploadProgress(evt) {
    if (evt.lengthComputable) {

        var percentComplete = Math.round(evt.loaded * 100 / evt.total);

        document.getElementById('bar').setAttribute("style","width:"+ percentComplete.toString() + "%");
        document.getElementById('bar').innerHTML=percentComplete.toString() + "%";
        if(percentComplete == 100){
        	Messenger().post({
				  message: "文件上传成功！请等待视频转码完成后播放。",
				  hideAfter: 5
			});
        }
    }
}
function closeUp()
{
	$("#addtags").empty();
	counter=0;
	document.getElementById("tagContent").value = "";
	}

$(document).ready(
	function() {
		$("#renameButton").click(
			function() {
				$.each($(":checkbox"),
					function() {
						if ($(this).is(":checked")) {
							var tempElement = this;
							var id = tempElement.parentNode.nextSibling.childNodes[1].getAttribute("value");
							if (tempElement.getAttribute("name") == "file") {
								var flag = 0;
								var newone = $("#newname").val();
								$.each($("a#filemd5"),function(){
									if($(this).text()==newone){
										flag=1;
									}
								});
								if(flag == 0){
									$.ajax({
										type: "GET",
										url: "modifyFileName.action",
										data: {
											filemd5: id,
											newfilename: $("#newname").val()
										},
										dataType: "json",
										success: function() {
											tempElement.parentNode.nextSibling.childNodes[1].innerHTML = $("#newname").val();
											document.getElementById("newname").value="";
											Messenger().post({
												  message: "文件重命名成功！",
												  hideAfter: 5
											});
										}
									});
								}
							} else {
								var flag = 0;
								var newone = $("#newname").val();
								$("a#folderId").each(function(){
									if($(this).text()==newone){
										flag=1;
									}
								});
								if(flag == 0){
									$.ajax({
										type: "GET",
										url: "modifyFolderName.action",
										data: {
											folderid: id,
											newfoldername: $("#newname").val()
										},
										dataType: "json",
										success: function() {
											tempElement.parentNode.nextSibling.childNodes[1].innerHTML = $("#newname").val();
											document.getElementById("newname").value="";
											Messenger().post({
												  message: "文件夹重命名成功！",
												  hideAfter: 5
											});
										}
									});
								}
							}
						}
						
					});
			});
	});
$(document).ready(function() {
	$("#share").click(function() {
		$.each($(":checkbox"),function() {
			if ($(this).is(":checked")) {
				var tempElement=this; 
				var id=tempElement.parentNode.nextSibling.childNodes[1].getAttribute("value");
				var fn=tempElement.parentNode.nextSibling.childNodes[1].innerHTML;
					$.ajax({
						type: "GET",
						url: "shareFile.action",
						data: {
							MD5: id,
							fileName:fn
						},
						success: function(data) {
							var value = eval("("+data+")");
							document.getElementById("shareURL").value = value.shareLink;
							
							if(tempElement.parentNode.parentNode.lastChild.innerHTML=="否"){
								tempElement.parentNode.parentNode.lastChild.innerHTML="是";
								$("#shareOrNot").attr("value", "0");
							}
							else{
								Messenger().post({
									message: "文件已经分享，请使用公开链接。",
								  	hideAfter: 3
								});
							}
						}
					});
				
			}
		});
	});
});

function shareMessage(){
	if($("#shareOrNot").val()=='0'){
		Messenger().post({
			message: "文件分享成功！",
		  	hideAfter: 3
		});
		$("#shareOrNot").attr("value", "1");
	}
}

$(document).ready(function() {
	$("#download").click(function() {
		$.each($(":checkbox"),function() {
			if ($(this).is(":checked")) {
				var tempElement=this; 
				var id=tempElement.parentNode.nextSibling.childNodes[1].getAttribute("value");
				var fn=tempElement.parentNode.nextSibling.childNodes[1].innerHTML;
				document.location.href = "http://192.168.1.250:50075/webhdfs/v1/upload/"
					+ id + ".avi?op=OPEN&namenoderpcaddress=czh:9000&offset=0";
				$.ajax({
					type: "GET",
					url: "saveDownloadLogPro.action",
					data: {
						fileName:fn
					},
					success: function() {
					}
				});
			}
		});
	});
});

$(document).ready(function() {
	$("#deleteButton").click(function() {
		$.each($(":checkbox"),function() {
			if ($(this).is(":checked")) {
				var tempElement=this; 
				var id=tempElement.parentNode.nextSibling.childNodes[1].getAttribute("value");
				if (tempElement.getAttribute("name") == "file") {
					var name = tempElement.parentNode.nextSibling.childNodes[1].innerHTML;
					$.ajax({
						type: "GET",
						url: "deleteFile.action",
						data: {
							MD5: id,
							fileName : name,
							folderId : $("#upperFolderId").val()
						},
						success: function(data) {
							tempElement.click();
							tempElement.parentNode.parentNode.parentNode.removeChild(tempElement.parentNode.parentNode);
							Messenger().post({
								  message: data,
								  hideAfter: 5
							});
						}
					});
				} else {
					$.ajax({
						type: "GET",
						url: "deleteFolder.action",
						data: {
							folderId: id,
							upperFolderId : $("#upperFolderId").val()
						},
						success: function(data) {
							tempElement.click();
							tempElement.parentNode.parentNode.parentNode.removeChild(tempElement.parentNode.parentNode);
							Messenger().post({
								  message: data,
								  hideAfter: 5
							});
						}
					});
				}
			}
		});
	});
});

$(document).ready(function() {
	$(document).on("click",":checkbox",function() {
		var count = 0;
		if ($(this).is(":checked") == true) {
			if ($("#delete").is(":disabled") == true) {
				$("#delete").removeAttr("disabled");
				$("#rename").removeAttr("disabled");
				if ($(this).attr("name") == "file") {
					$("#share").removeAttr("disabled");
					$("#download").removeAttr("disabled");
				}
			} else {
				$("#rename").attr("disabled", "true");
				$("#download").attr("disabled", "true");
				$("#share").attr("disabled", "true");
			}
		} else {
			if ($("input[type='checkbox']:checked").length == 1) {
				$("#delete").removeAttr("disabled");
				$("#rename").removeAttr("disabled");
				if ($("input[type='checkbox']:checked").attr("name") == "file") {
					$("#share").removeAttr("disabled");
					$("#download").removeAttr("disabled");
				}
			}
			if ($("input[type='checkbox']:checked").length == 0) {
				$("#rename").attr("disabled", "true");
				$("#download").attr("disabled", "true");
				$("#share").attr("disabled", "true");
				$("#delete").attr("disabled", "true");
			}
		}
	});
});