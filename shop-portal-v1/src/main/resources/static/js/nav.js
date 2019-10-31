var login_status = false;
/*
    '            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">\n' +
    '                <span class="sr-only">Toggle navigation</span>\n' +
    '                <span class="glyphicon glyphicon-plus-sign"></span>\n' +
    '                <span class="icon-bar"></span>\n' +
    '                <span class="icon-bar"></span>\n' +
    '            </button>\n' +
* */
var navHtml = '<nav class="navbar navbar-inverse">\n' +
    '    <div class="container-fluid">\n' +
    '        <div class="navbar-header">\n' +

    '            <a class="navbar-brand" href="/">飞狐电商前台</a>\n' +
    '            <ul class="nav navbar-nav">\n' +
    '            </ul>\n' +
    '        </div>\n' +
    '        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">\n' +
    '            <ul class="nav navbar-nav navbar-right">\n' +
    '                <li id="navUserMsg"></li>\n' +
    '                <li><a href="/login.html">登录</a></li>\n' +
    '                <li></li>\n' +
    '                <li><a href="/index-enroll.html">注册</a></li>\n' +
    '            </ul>\n' +
    '        </div>\n' +
    '    </div>\n' +
    '</nav>';

$(function () {
    $.ajaxSetup({
        beforeSend:function(request){
            var key = $.cookie("x-auth");
            //var toKen = $.cookie("toKen");
            request.setRequestHeader("x-auth",key);
            //request.setRequestHeader("toKen",toKen);
        }
    })
    $("#navHtml").html(navHtml);
    initMsg();
})

function initMsg(){
    $.ajax({
        url:"http://localhost:8081/members/gainMessage",
        async:false,
        beforeSend:function(xhr){
            xhr.setRequestHeader("x-auth",$.cookie("x-auth"))
        },
        success:function(ref){
            if(ref.code == 200){
                login_status = true;
                var node = $("#navUserMsg");
                $(node).html('<a>欢迎' + ref.data.realName + '登录</a>');
                $(node).next().html('<a onclick="loginOut()">退出</a>');
                $(node).next().next().html('<a href="/cart-student.html">购物车</a>');
            }
        }
    })
}

function loginOut(){

    $.ajax({
        url:"http://localhost:8081/members/loginOut",
        /*headers:{
            "x-auth" : $.cookie("x-auth")
        },*/
        success:function(ref){
            if(ref.code == 200){
                var node = $("#navUserMsg");
                $(node).html('');
                $(node).next().html('<a href="/login.html">登录</a>');
                $(node).next().next().html('');
                location.href = "/";
            }
        }
    })
}

function login(id){
    bootbox.dialog({
        title: '登录',
        backdrop:false,
        message: '用户:<input id="userName"><br>'+
        '密码:<input id="password"><br>',
        size: 'small',//有效值为'large'和'small'
        buttons: {
            ok: {
                label: "<i class='glyphicon glyphicon-plus-sign'>登录",
                className: 'btn-info',
                callback: function (data) {//添加回调函数 $("#sdss").serialize()
                    var userName = $("#userName").val();
                    var password = $("#password").val();
                    $.get({
                        url:"http://localhost:8081/members/login",
                        data:{userName:userName,password:password},
                        async:false,
                        success:function(ref){
                            if(ref.code == 200){
                                $.cookie("x-auth",ref.data);
                                addCartItem(id,1);
                            }
                        }
                    })
                }
            },
            cancel: {
                label: "<i class='glyphicon glyphicon-remove-sign'>关闭",
                className: 'btn-danger',
                callback: function (data) {//关闭回调函数
                }
            },
        },
    })
}

function addCart(id,count,status,newCount){
    if(!login_status){
        login(id);
    }
    if(login_status){
        if(status == 2){
            if(newCount == 1){
                console.log(new Date().getTime() + " 减")
                alert("不能操作")
            }
        }
        addCartItem(id,count,status);
    }
}

function addCartItem(id,count,status){
    $.post({
        url:"http://localhost:8081/carts/add",
        data:{commId:id,count:count},
        success:function(ref){
            if(ref.code == 200){
                if(!status){
                    location.href = "/cart-student.html";
                    return;
                }
                if(status == 1 || status == 2){
                    initBody();
                }
            }
        }
    })
}