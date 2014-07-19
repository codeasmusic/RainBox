    //修改密码调用
    function check3() {
    with(document.all){
      if(oldPass.value.length<6 || oldPass.value.length>20){
    	  document.getElementById("icon1").className= "";
        document.getElementById("info1").innerHTML="<a>密码长度不符</a>";
        return false;
      }
      else{
        document.getElementById("icon1").className="glyphicon glyphicon-ok";
        document.getElementById("icon1").innerHTML="";
        document.getElementById("info1").innerHTML="";
        return true; 
      }
    }
  }

    function check() {
    with(document.all){
      if(pass1.value.length<6 || pass1.value.length>20){
    	  document.getElementById("icon2").className= "";
        document.getElementById("info2").innerHTML="<a>密码长度不符</a>";
        return false;
      }
      else{
        document.getElementById("icon2").className="glyphicon glyphicon-ok";
        document.getElementById("icon2").innerHTML="";
        document.getElementById("info2").innerHTML="";
        return true; 
      }
    }
  }

    function check2(){
    with(document.all){
    if(pass1.value != pass2.value){
    	document.getElementById("icon3").className= "";
        document.getElementById("info3").innerHTML="<a>两次输入的密码不一致，请重新输入！</a>";
        return false;
      }
      else{
        document.getElementById("icon3").className="glyphicon glyphicon-ok";
        document.getElementById("icon3").innerHTML="";
        document.getElementById("info3").innerHTML="";
        return true; 
      }
    }
  }


      //修改密码调用
    function enablebtn3(){
    if(document.getElementById("oldPass").value == "" )
     {
        document.getElementById("btnSubmit3").disabled = true;
        return;
     }
     if(document.getElementById("pass1").value == "" )
     {
        document.getElementById("btnSubmit3").disabled = true;
        return;
     }
     if(document.getElementById("pass2").value == "" )
     {
        document.getElementById("btnSubmit3").disabled = true;
        return;
     }
     if (check3() && check() && check2() ) {
     document.getElementById("btnSubmit3").disabled = false;
     return;
   }
  }