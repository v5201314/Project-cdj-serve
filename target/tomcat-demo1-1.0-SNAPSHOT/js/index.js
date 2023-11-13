window.addEventListener('load',()=>{

  var ipts=document.querySelectorAll('.inputs input')
  var ps=this.document.querySelectorAll('.inputs p')

  for(var i=0;i<ipts.length;i++){
    ipts[i].addEventListener('focus',function(){
      this.style.color='black'
     })
  }
  
 ipts[0].addEventListener('focus',function(){
  if(this.value==='用户名'){
    this.value='';
  }
 })
 ipts[0].addEventListener('blur',function(){
  if(this.value===''){
    this.value='用户名';
    this.style.color='rgb(175, 175, 175)'
  }
 })
 ipts[1].addEventListener('focus',function(){
  if(this.value==='密码'){
    this.value='';
    this.type='password'
  }
 })
 ipts[1].addEventListener('blur',function(){
  if(this.value===''){
    this.type='text'
    this.value='密码';
    this.style.color='rgb(175, 175, 175)'
  }
 })
 var last=ipts[ipts.length-1]

  last.addEventListener('click',function(){
    //判断用户名是否合法
    if(ipts[0].value.indexOf(" ")===-1){
      ps[0].style.display='none'
    }else{
      ps[0].style.display='block'
      return
    }
    //校验密码合法性
    if(ipts[1].value.indexOf(" ")===-1){
      ps[1].style.display='none'
        document.form2.submit();
    }else{
      ps[1].innerHTML="X密码不合法"
      ps[1].style.display='block'
    }


})
})