window.addEventListener('load',function(){
  //注册的逻辑 后续使用正则改进
  var ipts=this.document.querySelectorAll('.inputs input')
  var ps=this.document.querySelectorAll('.inputs p')
  var last=ipts[ipts.length-1]
  ipts[2].addEventListener('focus',function(){
    if(this.value==='确认密码'){
      this.value='';
      this.type='password'
    }
   })
   ipts[2].addEventListener('blur',function(){
    if(this.value==''){
      this.type='text'
      this.value='确认密码';
      this.style.color='rgb(175, 175, 175)'
    }
   })
  

  last.addEventListener('click',function(){
    //判断用户名是否合法
    if(ipts[0].value.indexOf(" ")==-1){
      ps[0].style.display='none'
    }else{
      ps[0].style.display='block'
      return
    }
    //校验密码合法性
    if(ipts[1].value.indexOf(" ")==-1){
      ps[1].style.display='none'
    }else{
      ps[1].innerHTML="X密码不合法"
      ps[1].style.display='block'
      return;
    }
    //判断密码是否一致
    if(ipts[1].value===ipts[2].value){
      ps[1].style.display='none'
      document.form1.submit();
    }else{
      ps[1].innerHTML='X两次密码不一致！！'
      ps[1].style.display='block'
    }
    
  })

})