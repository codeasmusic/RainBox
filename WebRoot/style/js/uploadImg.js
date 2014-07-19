
function imgLoaded() {
	//判断图片大小
	var filesInput=document.getElementById("filesInput");
	var imgfile=document.getElementById("filesInput").files[0];
	var fileSize = imgfile.size;
    if(fileSize/1024 > 1024){
    $("#info").text("图片大小超过1M!");
    $("#imageBox").empty();
    filesInput.value = "";
    }
    else{
    	var reader = new FileReader(); 
        reader.readAsDataURL(imgfile); 
        reader.onload = function(e){ 
        	document.getElementById("imageBox").innerHTML = "<li><img src='" + this.result + "'>"  + "</li>"; 
        } 
 }
}

                    