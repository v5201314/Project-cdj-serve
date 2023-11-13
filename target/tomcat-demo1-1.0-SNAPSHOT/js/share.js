window.addEventListener('load',function(){


    var username=document.querySelector('#username');
    var gou=document.querySelector('#gou');
//获取
    if(localStorage.getItem('username')){
        username.value=localStorage.getItem('username');
        gou.checked=true;
    }
    gou.addEventListener('change',function(){
        if(this.checked){
            //添加
            localStorage.setItem('username',username.value);
        }
        else{
            //删除
            localStorage.removeItem('username');
        }
    })


})