<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <title>登录</title>
    <meta name="description" content="particles.js is a lightweight JavaScript library for creating particles.">
    <meta name="author" content="Vincent Garreau" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <link rel="stylesheet" media="screen" href="/js/jsp/Demo/style.css">
    <link rel="stylesheet" type="text/css" href="/js/jsp/Demo/reset.css"/>
    <script type="text/javascript" charset="utf-8" id="tr-app" src="/js/jsp/Demo/jquery.min.js"></script></head>
    <jsp:include page="topNav/head.jsp"></jsp:include>
<body>

<div id="particles-js" style="background: url('/js/index.png')"><%-- style="background: #67b168" style="background: url('/js/index.png')" --%>
    <div class="login">
        <div class="login-top" style="margin-top: -50px">
            登录
        </div>
        <div class="login-center clearfix">
            <div class="login-center-img"><img src="/js/jsp/Demo/name.png"/></div>
            <div class="login-center-input">
                <input type="text" id="userName" placeholder="请输入您的用户名" onfocus="this.placeholder=''" onblur="this.placeholder='请输入您的用户名'" autocomplete="off"/>
                <div class="login-center-input-text">账户</div>
            </div>
        </div>
        <div class="login-center clearfix">
            <div class="login-center-img"><img src="/js/jsp/Demo/password.png"/></div>
            <div class="login-center-input">
                <input type="password" id="password" placeholder="请输入您的密码" onfocus="this.placeholder=''" onblur="this.placeholder='请输入您的密码'" autocomplete="off"/>
                <div class="login-center-input-text">密码</div>
            </div>
        </div>
        <div class="login-center clearfix">
            <div class="login-center-img"><img src="/js/jsp/Demo/name.png"/></div>
            <div class="login-center-input">
                <input type="text" id="code" placeholder="请输入验证码" onfocus="this.placeholder=''" onblur="this.placeholder='请输入验证码'" autocomplete="off"/>
                <div class="login-center-input-text">验证码</div>
            </div>
        </div>
        <div class="login-center clearfix"><%--  clearfix --%>
            <div align="center">
                <img src="/img/code" id="imgCode"><br>
                <a href="#" onclick="imgCode()">看不清，换一张</a>
            </div>
            <div style="text-align: center;margin-bottom: 5px">
                <input type="checkbox" onclick="remember()" id="remFlag">记住密码&nbsp;&nbsp;&nbsp;
            </div>
            <div class="login-center-input" style="text-align: center">
                <a href="/forgetPass.jsp">忘记密码?</a>
            </div>
        </div>
        <div class="login-button" onclick="getLongion()">
            登录
        </div>
        <div class="login-button" style="background-color: #9d9d9d;margin-top: 5px" onclick="getReset()">
            重置
        </div>
    </div>
    <div class="sk-rotating-plane"></div>
</div>

<!-- scripts -->
<jsp:include page="topNav/script.jsp"></jsp:include>
<script src="/js/jsp/Demo/particles.min.js"></script>
<script src="/js/jsp/Demo/app.js"></script>
<script type="text/javascript">

    function imgCode(){
        $("#imgCode").attr("src","/img/code?"+new Date().getTime());
    }

    $(function(){
        addClass(document.querySelector(".sk-rotating-plane"), "active")
        document.querySelector(".login").style.display = "none"
        setTimeout(function(){
            removeClass(document.querySelector(".login"), "active")
            removeClass(document.querySelector(".sk-rotating-plane"), "active")
            document.querySelector(".login").style.display = "block"
        },1500)
        //记住密码功能
        /*var cooks = document.cookie.split(";");
        var sstr = cooks[0];
        var ii = sstr.indexOf("=");
        if(ii != -1){
            str = sstr.substring(ii+2,sstr.length-1);
            var username = str.split(",")[0];
            var password = str.split(",")[1];
            //自动填充用户名和密码
            $("#userName").val(username);
            $("#password").val(password);
            $("#remFlag").prop("checked",true);
            $("#remFlag").val("1");
        }*/
        //if($("#remFlag").prop("checked")){}
    })

    function hasClass(elem, cls) {
        cls = cls || '';
        if (cls.replace(/\s/g, '').length == 0) return false; //当cls没有参数时，返回false
        return new RegExp(' ' + cls + ' ').test(' ' + elem.className + ' ');
    }

    function addClass(ele, cls) {
        if (!hasClass(ele, cls)) {
            ele.className = ele.className == '' ? cls : ele.className + ' ' + cls;
        }
    }

    function removeClass(ele, cls) {
        if (hasClass(ele, cls)) {
            var newClass = ' ' + ele.className.replace(/[\t\r\n]/g, '') + ' ';
            while (newClass.indexOf(' ' + cls + ' ') >= 0) {
                newClass = newClass.replace(' ' + cls + ' ', ' ');
            }
            ele.className = newClass.replace(/^\s+|\s+$/g, '');
        }
    }

    //记住密码功能
    function remember(){
        var remFlag = $("input[type='checkbox']").is(':checked');
        if(remFlag==true){ //如果选中设置remFlag为1
            //cookie存用户名和密码,回显的是真实的用户名和密码,存在安全问题.
            bootbox.confirm({//弹出确认框
                size: "small",
                message: "记录密码功能不宜在公共场所(如网吧等)使用,以防密码泄露.您确定要使用此功能吗?",
                buttons: {
                    confirm: {//由此参数返回result = true
                        label: '<i class="glyphicon glyphicon-ok"></i>确定',
                        className: 'btn-danger',
                    },
                    cancel: {//由此参数返回result = false
                        label: '<i class="glyphicon glyphicon-remove"></i>取消',
                        className: 'btn-success',
                    }
                },
                callback: function (result) {
                    if(result){
                        $("#remFlag").val("1");
                    }else{
                        $("input[type='checkbox']").removeAttr('checked');
                        $("#remFlag").val("");
                    }
                }
            })
        }else{ //如果没选中设置remFlag为""
            $("#remFlag").val("");
        }
    }

    document.querySelector(".login-button").onclick = function(){
        addClass(document.querySelector(".login"), "active")
        setTimeout(function(){
            addClass(document.querySelector(".sk-rotating-plane"), "active")
            document.querySelector(".login").style.display = "none"
        },11)
        setTimeout(function(){
            removeClass(document.querySelector(".login"), "active")
            removeClass(document.querySelector(".sk-rotating-plane"), "active")
            document.querySelector(".login").style.display = "block"

            var user = {};
            user.userName = $("#userName").val();
            user.password = $("#password").val();
            user.code = $("#code").val();
            user.remFlag = $("#remFlag").val();
            if(user.userName == ""){
                getTooltip("账户不能为空")
                removeStyle();
            }else if(user.password == ""){
                getTooltip("密码不能为空")
                removeStyle();
            }else{
                $.ajax({
                    url:"/user/login.jhtml",
                    data:user,
                    dataType:"json",
                    type:"post",
                    success:function(ref){
                        if(ref.code == 200){
                            getTooltip("登陆成功");
                            setTimeout(function(){
                                location.href = "/skip/skipListJsp.jhtml";
                            },1500)
                        }else{
                            getTooltip(ref.msg);
                            removeStyle();
                        }
                    }
                })
            }

        },10)
    }

    function removeStyle(){
        setTimeout(function(){
            removeClass(document.querySelector(".login"), "active")
            removeClass(document.querySelector(".sk-rotating-plane"), "active")
            document.querySelector(".login").style.display = "block"
        },2000)
    }

    function getTooltip(str,size,time){
        var getSize = "small";
        var getTime = 1000 + 500;
        if(str == ""){
            str = " ";
        }
        if(!!size){
            getSize = size;
        }
        if(!!time){
            getTime = time;
        }
        bootbox.dialog({
            message: str,//内容
            size:getSize,//大小
            backdrop:true,//点击关闭 不管用 alert专用
            closeButton: false,//关闭按钮
        });
        setTimeout(function(){
            bootbox.hideAll();//关闭弹框
        },getTime);
    }

    function getReset(){
        $("#userName").val("");
        $("#password").val("");
        $("#remFlag").val("");
        $("#remFlag").prop('checked',false);
    }

</script>
</body>
</html>