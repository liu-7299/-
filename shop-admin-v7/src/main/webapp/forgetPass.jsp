<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 2019/9/10
  Time: 21:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>忘记密码</title>
    <jsp:include page="topNav/head.jsp"></jsp:include>
</head>
<body>

    <div class="container">
        <div class="row"><div class="col-md-12">
            <div class="panel panel-success">
                <div class="panel-heading">忘记密码</div>
                <div class="panel-body">
                    <form class="form-horizontal">
                        <div class="form-group">
                            <label class="col-sm-3 control-label">请输入您的账户</label>
                            <div class="col-sm-7">
                                <input id="userName" type="text" class="form-control" placeholder="请输入用户名">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">请输入您的邮箱</label>
                            <div class="col-sm-7">
                                <input id="email" type="text" class="form-control" placeholder="请输入邮箱地址">
                            </div>
                        </div>
                        <div class="form-group" align="center">
                            <div class="col-sm-2"></div>
                            <div class="col-sm-4">
                                <button style="background-color: darkseagreen" type="button" class="form-control" onclick="getPass()"><i class="glyphicon glyphicon-new-window"></i>向邮箱发送密码</button>
                            </div>

                            <div class="col-sm-3">
                                <button style="background-color: #9d9d9d" type="reset" class="form-control"><i class="glyphicon glyphicon-repeat"></i>重置</button>
                            </div>
                            <div class="col-sm-2"></div>
                           </div>
                    </form>
                </div>
            </div>
        </div></div>
    </div>


<jsp:include page="topNav/script.jsp"></jsp:include>
<script>

    function getPass(){
        var userName = $("#userName").val();
        var email = $("#email").val();
        if(userName != "" && email != ""){
            var user = {};
            user.userName = userName;
            user.email = email;
            $.ajax({
                url:"/user/forgetPassword.jhtml",
                data:user,
                type:"post",
                success:function(ref){
                    if(ref.code == 200){
                        getTooltip(ref.msg);
                        setTimeout(function(){
                            window.history.go(-1);
                        },1500)
                    }else{
                        getTooltip(ref.msg);
                    }
                }
            })
        }else{
            getTooltip("请输入信息");
        }
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

</script>
</body>
</html>
