var code ; //在全局 定义验证码
function createCode(){ 
code = new Array();
var codeLength = 4;//验证码的长度
var checkCode = document.getElementById("checkCode");
checkCode.value = "";

var selectChar = new Array(0,1,2,3,4,5,6,7,8,9,'A','B','C','D','E','F','G','H','J','K','L','M','N','P','Q','R','S','T','U','V','W','X','Y','Z');

for(var i=0;i<codeLength;i++) {
   var charIndex = Math.floor(Math.random()*36);
   code +=selectChar[charIndex];
}
if(code.length != codeLength){
   createCode();
}
checkCode.value = code;
}

function validate () {
var inputCode = document.getElementById("input1").value.toUpperCase();

if(inputCode.length <=0) {
   return false;
}
else if(inputCode != code ){
   document.getElementById("aaa").innerHTML="<span class='glyphicon glyphicon-remove'></span>";
   createCode();
   return false;
}
else {
   document.getElementById("aaa").innerHTML="<span class='glyphicon glyphicon-ok'></span>";
   return true;
}
}


function isEmail() { 
  var str=document.getElementById("email").value;
if (str.search(/^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/) != -1){
document.getElementById("e").className="glyphicon glyphicon-ok";
document.getElementById("text1").innerHTML="";
 return true; 
}
else  {

  document.getElementById("text1").innerHTML="<a>请输入正确的邮箱地址！</a>";
  document.getElementById("btnSubmit").disabled=true;
  document.getElementById("e").className= "";
return false;
}
}



  function check() {
      if(pass1.value.length<6 || pass1.value.length>20){
        document.getElementById("text2").innerHTML="<a>密码长度不符</a>";
        document.getElementById("btnSubmit").disabled=true;
        return false;
      }
      else{
        document.getElementById("pawd1").innerHTML="";
        document.getElementById("text2").innerHTML="";
        return true; 
      }
  }
  
  function check2() {
      if(pass1.value==""){
        return false;
      }
      else{
        return true; 
      }
  }

  function enablebtn(){
    if(document.getElementById("email").value== "")
     {
        document.getElementById("btnSubmit").disabled=true;
        return;
     }
      if(document.getElementById("pass1").value == "" )
     {
        document.getElementById("btnSubmit").disabled = true;
        return;
     }
     if(document.getElementById("input1").value == "" ){
        document.getElementById("btnSubmit").disabled = true;
        return;
     }
     if (isEmail() && check() && validate() ) {;
     document.getElementById("btnSubmit").disabled = false;
     return;
   }
  }

  function enablebtn2(){
	      if(document.getElementById("pass1").value == "" )
	     {
	        document.getElementById("btnSubmit").disabled = true;
	        return;
	     }
	     if(document.getElementById("input1").value == "" ){
	        document.getElementById("btnSubmit").disabled = true;
	        return;
	     }
	     if (validate() ) {;
	     document.getElementById("btnSubmit").disabled = false;
	     return;
	   }
	  }
