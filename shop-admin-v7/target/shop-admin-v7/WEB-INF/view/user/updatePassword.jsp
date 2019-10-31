<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 2019/9/10
  Time: 17:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <jsp:include page="/topNav/head.jsp"></jsp:include>
    <title>修改密码</title>
</head>
<body>
<%-- 导航条 --%>
<jsp:include page="/topNav/nav.jsp"></jsp:include>
<div class="container">
    <div class="row"><div class="col-md-12">
        <div class="panel panel-success">
            <div class="panel-heading">修改密码</div>
            <div class="panel-body">
                <form class="form-horizontal">
                    <div class="form-group">
                        <label class="col-sm-2 control-label">旧密码</label>
                        <div class="col-sm-6">
                            <input id="oldPassword" type="text" class="form-control" placeholder="请输入旧密码">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">新密码</label>
                        <div class="col-sm-6">
                            <input id="newPassword" type="text" class="form-control" placeholder="请输入新密码">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">确认密码</label>
                        <div class="col-sm-6">
                            <input id="password" type="text" class="form-control" placeholder="请输入确认密码">
                        </div>
                    </div>
                    <div class="form-group" align="center">
                        <button type="button" class="btn btn-info btn-lg" onclick="getUpdate()"><i class="glyphicon glyphicon-refresh"></i>更新</button>
                        <button type="reset" class="btn btn-default btn-lg"><i class="glyphicon glyphicon-repeat"></i>重置</button>
                    </div>
                </form>
            </div>
        </div>
    </div></div>
</div>

<jsp:include page="/topNav/script.jsp"></jsp:include>
<script>

    function getUpdate(){
        var oldPassword = $("#oldPassword").val();
        var newPassword = $("#newPassword").val();
        var password = $("#password").val();
        if(password == "" || newPassword == "" || oldPassword == ""){
            getTooltip("信息不能为空")
        }else if(password != newPassword){
            getTooltip("两次密码不一致")
        }else{
            var up = {};
            up.oldPassword = $("#oldPassword").val();
            up.newPassword = $("#newPassword").val();
            up.password = $("#password").val();
            $.ajax({
                url:"/user/updateUserPassword.jhtml",
                data:up,
                type:"post",
                success:function(ref){
                    if(ref.code == 200){
                        bootbox.alert({
                            message: "修改成功，点击OK回到上页",
                            callback: function(){
                                window.history.go(-1);
                            }
                        });
                    }
                }
            })
        }
    }

</script>
</body>
</html>
