function checkUserName(){
    if(document.getElementById("userName").value ==""){
    	document.getElementById("saveBtn").disabled = true;
    	document.getElementById("error").innerHTML="<a>昵称不能为空！</a>";
    	return false;
    }
    if(document.getElementById("userName").value.length>30){
    	document.getElementById("saveBtn").disabled = true;
    	document.getElementById("error").innerHTML = "<a>昵称长度不能超过30！</a>";
    	return false;
    }
    else{
    	document.getElementById("user").className = "glyphicon glyphicon-ok";
    	document.getElementById("error").innerHTML = "";
    	document.getElementById("saveBtn").disabled = false;
    	return true;
    }
}