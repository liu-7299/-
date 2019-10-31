<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 2019/8/28
  Time: 22:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <title>登录</title>
</head>
<body>

用户:<input id="userName"><br>
密码:<input id="password"><br>
<input type="checkbox" onclick="remember()" id="remFlag">记住密码<br>
<input onclick="getLongion()" type="button" value="登录">
<a href="/forgetPass.jsp">忘记密码</a>


<script src="/js/jquery-3.3.1.js"></script>
<script>

    /*$(function(){
        //记住密码功能
        var cooks = document.cookie.split(";");
        console.log(cooks)
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
        }
        //if($("#remFlag").prop("checked")){}
    })*/

    //记住密码功能
    /*function remember(){
        var remFlag = $("input[type='checkbox']").is(':checked');
        if(remFlag==true){ //如果选中设置remFlag为1
            //cookie存用户名和密码,回显的是真实的用户名和密码,存在安全问题.
            var conFlag = confirm("记录密码功能不宜在公共场所(如网吧等)使用,以防密码泄露.您确定要使用此功能吗?");
            if(conFlag){ //确认标志
                $("#remFlag").val("1");
            }else{
                $("input[type='checkbox']").removeAttr('checked');
                $("#remFlag").val("");
            }
        }else{ //如果没选中设置remFlag为""
            $("#remFlag").val("");
        }
    }*/

    function getLongion(){
        var user = {};
        user.userName = $("#userName").val();
        user.password = $("#password").val();
        user.remFlag = $("#remFlag").val();
        $.ajax({
            url:"/user/login.jhtml",
            data:user,
            dataType:"json",
            type:"post",
            success:function(ref){
                if(ref.code == 200){
                    console.log("登录成功")
                    location.href = "/skip/skipListJsp.jhtml";
                }else{
                    alert(ref.msg)
                }
            }
        })
    }
</script>
</body>
</html>
