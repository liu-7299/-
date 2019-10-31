<%@ page import="com.fh.admin.servlet.DistributedSession" %>
<%@ page import="com.fh.admin.util.RedisUtil" %>
<%@ page import="com.fh.admin.util.KeyUtil" %>
<%@ page import="com.alibaba.fastjson.JSONObject" %>
<%@ page import="com.fh.admin.po.User" %><%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 2019/8/28
  Time: 12:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="fmt"%>
<style>
    .dropdown-submenu {
        position: relative;
    }

    .dropdown-submenu > .dropdown-menu {
        top: 0;
        left: 100%;
        margin-top: -6px;
        margin-left: -1px;
        -webkit-border-radius: 0 6px 6px 6px;
        -moz-border-radius: 0 6px 6px;
        border-radius: 0 6px 6px 6px;
    }

    .dropdown-submenu:hover > .dropdown-menu {
        display: block;
    }

    .dropdown-submenu > a:after {
        display: block;
        content: " ";
        float: right;
        width: 0;
        height: 0;
        border-color: transparent;
        border-style: solid;
        border-width: 5px 0 5px 5px;
        border-left-color: #ccc;
        margin-top: 5px;
        margin-right: -10px;
    }

    .dropdown-submenu:hover > a:after {
        border-left-color: #fff;
    }

    .dropdown-submenu.pull-left {
        float: none;
    }

    .dropdown-submenu.pull-left > .dropdown-menu {
        left: -100%;
        margin-left: 10px;
        -webkit-border-radius: 6px 0 6px 6px;
        -moz-border-radius: 6px 0 6px 6px;
        border-radius: 6px 0 6px 6px;
    }
</style>
<script src="/js/jquery-3.3.1.js"></script>
<nav class="navbar navbar-inverse">
    <div class="container-fluid">
        <!-- Brand and toggle get grouped for better mobile display -->
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="#">飞狐后台管理系统</a>
        </div>
        <div id="dgs" class="collapse navbar-collapse">
            <ul class="nav navbar-nav navbar-right">
                <%
                    String sessionId = DistributedSession.getSessionId(request, response);
                    String users = RedisUtil.get(KeyUtil.buildUserKey(sessionId));
                    User user = JSONObject.parseObject(users,User.class);
                %>
                <li><a><%=user.getRealName()%>，欢迎登陆，今天第<%=user.getLoginCount()%><span id="zh"></span>次登录，
                    <span id="liuxy"></span>
                    <fmt:formatDate value="<%=user.getTopLoginTime()%>" pattern="yyyy-MM-dd HH:mm:ss"></fmt:formatDate>
                </a></li>
                <li><a href="/skip/skipListJsp.jhtml">返回首页</a></li>
                <li><a href="/user/logout.jhtml">退出</a></li>
                <li><a href="/user/skipUpdatePasswordJsp.jhtml">修改密码</a></li>
            </ul>
        </div>
        <div class="collapse navbar-collapse">
        </div>
    </div>
</nav>

<script>
    /*$(window).resize(function() {
        $("nav").width(document.body.offsetWidth  + "px");
        $("nav").height(document.body.clientHeight+ "px");
    });*/
    $(function(){
        initNavZtree();
        $.ajaxSetup({
            complete:function(ref){
                var data = ref.responseJSON;
                if(!!data.code && data.code != 200){
                    getTooltip(data.msg)
                }
            }
        })
    })
    var list;
    function initNavZtree(){
        $.ajax({
            url:"/menu/queryMenuByUserId.jhtml",
            dataType:"json",
            type:"post",
            success:function(ref){
                if(ref.code == 200){
                    list = ref.data;
                    //console.log(list)
        /*for(var i=0;i<list.length;i++){
            var ob = {};
            ob.p = false;
            ob.n = false;
            for(var j=0;j<list.length;j++){
                if(list[i].pid == list[j].id) ob.p = true;
                if(list[i].id == list[j].pid) ob.n = true;
            }
            /!* -- 正常情况下菜单id都是跟添加顺序一样的 mysql查询默认id排序 所以不需要判断子父节点的拼接顺序 -- *!/
            if (!ob.p && !ob.n)//追加单独的节点
                $("#ulId").append('<li><a href="' + list[i].url + '">' + list[i].name + '</a></li>');
            if (!ob.p && ob.n)//追加没有父节点 有子节点的 生成动态id
                $("#ulId").append('<li class="dropdown"><a href="' + list[i].url + '" data-toggle="dropdown">' + list[i].name + '<span class="caret"></span></a><ul id="ul_' + list[i].id + '" class="dropdown-menu"></ul></li>');
            /!* -- 菜单id顺序导致缺失节点可通过下一个for循环追加接下来的节点 -- *!/
            if (ob.p && ob.n)//追加有父节点 有子节点 id为动态的
                $("#ul_" + list[i].pid).append('<li class="dropdown-submenu"><a href="' + list[i].url + '">' + list[i].name + '</a><ul id="ul_' + list[i].id + '" class="dropdown-menu"></ul></li>');
            if (ob.p && !ob.n)//追加有父节点没有子节点的 即最后一层
                $("#ul_" + list[i].pid).append('<li><a href="' + list[i].url + '">' + list[i].name + '</a></li>');
         }*/

            get(1,1);
            if(list.length > 0){
                var yuanlai = $("#dgs").html();
                $("#dgs").html(sstrr);
                $("#uuu").append(yuanlai);
            }
                }
            }
        })
    }
    var sstrr = '';
    function get(id,salf){
        var arr = toFnSelP(id);
        if(arr.length > 0){
            if(salf == 1){
                sstrr += '<ul id="uuu" class="nav nav-tabs">';
            }else{
                sstrr += '<ul class="dropdown-menu">';
            }
            for(var i=0;i<arr.length;i++){
                var node = arr[i];
                var be = toFnSelN(node);
                if(be){
                    if(salf == 1){
                        sstrr += '<li>'+
                        '<a href="#" class="dropdown-toggle" data-toggle="dropdown">'+arr[i].name+'<span class="caret"></span></a>';
                    }else{
                        sstrr += '<li class="dropdown-submenu">'+
                        '<a href="'+node.url+'">'+arr[i].name+'</a>';
                    }
                }else{
                    sstrr += '<li ><a href="'+node.url+'">'+arr[i].name+'</a>';
                }
                get(node.id,2);
                sstrr += '</li>';
            }
            sstrr += '</ul>';
        }
    }

    function toFnSelP(id){
        var sarr = [];
        for(var i=0;i<list.length;i++){
            if(id == list[i].pid) sarr.push(list[i]);//有返回true
        }
        return sarr;//没有false
    }
    function toFnSelN(obj){
        for(var i=0;i<list.length;i++){
            if(obj.id == list[i].pid) return true;
        }
        return false;
    }

    /*
    * 提示框
    * */
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