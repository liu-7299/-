<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 2019/9/4
  Time: 19:34
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>表单验证</title>

    <script src="/js/jquery-3.3.1.js"></script>

    <script src="/js/bootstrap/js/bootstrap.min.js"></script>
    <link href="/js/bootstrap/css/bootstrap.min.css" rel="stylesheet" />

    <script src="/js/bootstrapValidator/js/bootstrapValidator.min.js"></script>
    <link href="/js/bootstrapValidator/css/bootstrapValidator.min.css" rel="stylesheet" />

</head>
<body>


<form class="form-horizontal">
    <div class="form-group">
        <label class="col-sm-2 control-label">用户</label>
        <div class="col-sm-3">
            <input type="text" class="form-control" name="aaa" placeholder="Email">
        </div>
    </div>
    <div class="form-group">
        <label class="col-sm-2 control-label">密码</label>
        <div class="col-sm-3">
            <input type="text" class="form-control" name="bbb" placeholder="Password">
        </div>
    </div>
    <div class="form-group">
        <label class="col-sm-2 control-label">qq密码</label>
        <div class="col-sm-3">
            <input type="text" class="form-control" name="bbbb" placeholder="Password">
        </div>
    </div>
    <div class="form-group">
        <label class="col-sm-2 control-label">邮箱</label>
        <div class="col-sm-3">
            <input type="text" class="form-control" name="ccc" placeholder="Password">
        </div>
    </div>
    <div class="form-group">
        <label class="col-sm-2 control-label">年龄</label>
        <div class="col-sm-3">
            <input type="text" class="form-control" name="ddd" placeholder="Password">
        </div>
    </div>
    <div class="form-group">
        <label class="col-sm-2 control-label">手机</label>
        <div class="col-sm-3">
            <input type="text" class="form-control" name="eee" placeholder="Password">
        </div>
    </div>
    <div class="form-group">
        <label class="col-sm-2 control-label">Password</label>
        <div class="col-sm-1">
            <input type="submit" class="form-control" placeholder="Password">
        </div>
    </div>
</form>
<br>
<br>
<br>
<input type="button" onclick="aaa()">

</body>

<script>

    $(function () {

    });

    function aaa(){console.log("执行")
        $('form').bootstrapValidator({
            fields: {
                aaa: {
                    //message: '用户名验证失败',
                    validators: {
                        notEmpty: {
                            message: '用户名不能为空'
                        },
                        stringLength: {
                            min: 6,
                            max: 18,
                            message: '用户名长度必须在6到18位之间'
                        },
                        regexp: {
                            regexp: /^[a-zA-Z0-9_]+$/,
                            message: '用户名只能包含大写、小写、数字和下划线'
                        },
                        /*remote: { // ajax校验，获得一个json数据（{'valid': true or false}）
                            url: '#',       //验证地址
                            message: '用户已存在',   //提示信息
                            type: 'POST',          //请求方式
                            data: function (validator) {  //自定义提交数据，默认为当前input name值
                                return {
                                    act: 'is_registered',
                                    username: $("input[name='bbb']").val()
                                };
                            }
                        }*/
                    },

                },
                bbb: {
                    validators: {
                        notEmpty: {
                            message: '密码不能为空'
                        },
                        different: {  //比较
                            field: 'aaa', //需要进行比较的input name值
                            message: '密码不能与用户名相同'
                        },
                    }
                },
                bbbb: {
                    validators: {
                        notEmpty: {
                            message: 'qq密码不能为空'
                        },
                        identical: {  //比较
                            field: 'bbb', //需要进行比较的input name值
                            message: '密码不相同'
                        },
                    }
                },
                ccc:{
                    validators: {
                        notEmpty: {
                            message: '邮箱地址不能为空'
                        },
                        emailAddress: {
                            message: '邮箱地址格式有误'
                        }
                    }
                },
                ddd:{
                    validators: {
                        notEmpty: {
                            message: '不能为空'
                        },
                        numeric:{
                            message:'输入数值',
                        },
                        between:{
                            min:1,
                            max:100,
                            message:'超出范围',
                        },
                    }
                },
                eee:{
                    validators: {
                        notEmpty: {
                            message: '不能为空'
                        },
                        phone:{
                            message:'输入正确的手机号',
                        }
                    }
                },
            }
        });
    }
</script>

</html>
