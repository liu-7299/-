<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 2019/8/15
  Time: 20:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <jsp:include page="/topNav/head.jsp"></jsp:include>
    <title>用户列表展示</title>
</head>
<body style="background-color: springgreen">
    <%-- 导航条 --%>
    <jsp:include page="/topNav/nav.jsp"></jsp:include>
    <%-- 组成主体 --%>
    <div class="container">
        <%-- 条件查询 --%>
        <div class="row"><div class="col-md-12">
            <div class="panel panel-success">
                <div class="panel-heading">用户查询</div>
                <div class="panel-body">
                    <form class="form-horizontal" method="post" action="/user/excelEduceUser.jhtml" id="userFormEduce">
                        <div class="form-group">
                            <label class="col-sm-2 control-label">用户名</label>
                            <div class="col-sm-4">
                                <input id="userNameM" name="userName" type="text" class="form-control" placeholder="请输入用户名">
                            </div>
                            <label class="col-sm-2 control-label">真实姓名</label>
                            <div class="col-sm-4">
                                <input id="realNameM" name="realName" type="text" class="form-control" placeholder="请输入真实姓名">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">年龄范围</label>
                            <div class="col-sm-4">
                                <div class="input-group">
                                    <input id="ageMin" name="ageMin" type="number" class="form-control" placeholder="开始年龄" aria-describedby="basic-addon1">
                                    <span class="input-group-addon"><i class="glyphicon glyphicon-resize-horizontal"></i></span>
                                    <input id="ageMax" name="ageMax" type="number" class="form-control" placeholder="结束年龄" aria-describedby="basic-addon1">
                                </div>
                            </div>
                            <label class="col-sm-2 control-label">薪资范围</label>
                            <div class="col-sm-4">
                                <div class="input-group">
                                    <input id="payMin" name="payMin" type="number" class="form-control" placeholder="开始薪资" aria-describedby="basic-addon1">
                                    <span class="input-group-addon"><i class="glyphicon glyphicon-jpy"><%-- 隐藏图标 sr-only --%></i></span>
                                    <input id="payMax" name="payMax" type="number" class="form-control" placeholder="结束薪资" aria-describedby="basic-addon1">
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">入职时间</label>
                            <div class="col-sm-4">
                                <div class="input-group">
                                    <input name="entryTimeMin" onblur="getTimeChange(this.value,'','#entryTimeMax')" id="entryTimeMin" type="text" maxlength="10" class="form_date form-control" placeholder="开始时间" aria-describedby="basic-addon1">
                                    <span class="input-group-addon"><i class="glyphicon glyphicon-calendar"></i></span><%--  form-control onchange="getTimeChange(this.value,'','#entryTimeMax')" --%>
                                    <input name="entryTimeMax" onblur="getTimeChange('',this.value,'#entryTimeMin')" id="entryTimeMax" type="text" maxlength="10" class="form_date form-control" placeholder="结束时间" aria-describedby="basic-addon1">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-2 control-label">角色</label>
                                <div id="roleIds1" class="col-sm-4">
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">地区</label>
                            <div class="col-sm-10" id="region_pdiv">
                            </div>
                        </div>
                        <div class="form-group" align="center">
                            <button type="button" class="btn btn-info btn-lg" onclick="getUserInfo()"><i class="glyphicon glyphicon-search"></i>查询</button>
                            <button type="reset" class="btn btn-default btn-lg" onclick="getReset()"><i class="glyphicon glyphicon-repeat"></i>重置</button>
                        </div>
                    </form>
                </div>
            </div>
        </div></div>
        <%-- 列表展示 --%>
        <div class="row"><div class="col-md-12">
            <div class="panel panel-success">
                <%-- 功能菜单 --%>
                <div style="background-color: #5cb85c">&nbsp;
                    <button onclick="getAddUser()" type="button" class="btn btn-primary"><i class="glyphicon glyphicon-plus"></i>添加用户</button>
                    <button onclick="getDeleteAll()" type="button" class="btn btn-danger"><i class="glyphicon glyphicon-trash"></i>批量删除</button>
                    <button onclick="excelEduce()" type="button" class="btn btn-info"><i class="glyphicon glyphicon-arrow-down"></i>excel导出o</button>
                    <button onclick="wordEduce()" type="button" class="btn btn-info"><i class="glyphicon glyphicon-arrow-down"></i>word导出o</button>
                    <button onclick="pdfEduce()" type="button" class="btn btn-info"><i class="glyphicon glyphicon-arrow-down"></i>pdf导出o</button>
                    <button onclick="excelEduceUser()" type="button" class="btn btn-info"><i class="glyphicon glyphicon-arrow-down"></i>excel导出</button>
                </div>
                <div class="panel-heading">用户列表</div>
                <div class="panel-body">
                    <table id="userList" class="table table-striped table-bordered" style="width:100%">
                        <thead>
                        <tr id="liuxy">
                            <th>全选<input type="checkbox"></th>
                            <th onclick="getEduce(this)">姓名</th>
                            <th onclick="getEduce(this)">用户</th>
                            <th onclick="getEduce(this)">年龄</th>
                            <th onclick="getEduce(this)">性别</th>
                            <th onclick="getEduce(this)">手机</th>
                            <th onclick="getEduce(this)">邮箱</th>
                            <th onclick="getEduce(this)">薪资</th>
                            <th onclick="getEduce(this)">入职时间</th>
                            <th>状态</th>
                            <th onclick="getEduce(this)">角色</th>
                            <th>地区</th>
                            <th onclick="getEduce(this)">用户头像</th>
                            <th>操作</th>
                        </tr>
                        </thead>
                        <tbody id="userTbody" style="text-align: center"></tbody>
                    </table>
                </div>
            </div>
        </div></div>
    </div>
    <%-- 测试 --%>

    <%--<link href="/js/bootstrapValidator/css/bootstrapValidator.min.css" rel="stylesheet" />
    <script src="/js/bootstrapValidator/js/bootstrapValidator.min.js"></script>--%>

    <a href="javascript:aaa()">测试</a>
    <%-- 隐藏的表单 页面加载完会清空 --%>
    <div id="userForm" ><%-- style="display: none" --%>
        <form id="sdss" class="form-horizontal"><%--  action="/user/addUser.jhtml" method="post" --%>
            <div class="form-group">
                <label class="col-sm-2 control-label">用户</label>
                <div class="col-sm-4">
                    <input type="hidden" name="id" id="id">
                    <input type="text" id="userName" class="form-control" name="userName" placeholder="请输入用户名">
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">姓名</label>
                <div class="col-sm-4">
                    <input type="text" class="form-control" id="realName" name="realName" placeholder="请输入真实姓名">
                </div>
            </div>
            <div id="passwordStyle" class="form-group">
                <label class="col-sm-2 control-label">密码</label>
                <div class="col-sm-4">
                    <input type="password" class="form-control" id="passwordQ" name="passwordQ" placeholder="请输入密码">
                </div>
            </div>
            <div id="passwordStyleQ" class="form-group">
                <label class="col-sm-2 control-label">确认</label>
                <div class="col-sm-4">
                    <input type="password" class="form-control" id="password" name="password" placeholder="请确认密码">
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">年龄</label>
                <div class="col-sm-4">
                    <input type="text" class="form-control" id="age" name="age" placeholder="请输入年龄">
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">手机</label>
                <div class="col-sm-4">
                    <input type="text" class="form-control" id="phone" name="phone" placeholder="请输入手机号">
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">邮箱</label>
                <div class="col-sm-4">
                    <input type="email" class="form-control" id="email" name="email" placeholder="请输入邮箱">
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">性别</label>
                <div class="col-sm-4">
                    <input type="radio" name="sex" value="1">男
                    <input type="radio" name="sex" value="2">女
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">薪资</label>
                <div class="col-sm-4">
                    <input type="text" class="form-control" id="pay" name="pay" placeholder="请输入薪资">
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">入职时间</label>
                <div class="col-sm-4">
                    <input type="text" maxlength="10" class="form_date form-control" name="entryTime" id="entryTime" placeholder="请选择入职时间">
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">角色</label>
                <div id="roleIds" class="col-sm-4">
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">图片</label>
                <div class="col-sm-4">
                    <img id="imgPathh" height="60px">
                    <input name="file" type="file" class="form-control myfile">
                    <input type="hidden" class="form-control" name="imgPath" id="imgPath" placeholder="请选择图片">
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">地区</label>
                <div class="col-sm-10">
                    <div id="region_div"></div>
                    <%--<span id="fly_name"></span>--%>
                    <input id="region_name" style="width: 200px" class="form-control col-sm-3" disabled>
                    <input id="region_butt" type="button" class="btn btn-info" value="编辑" onclick="onBianji(this)">
                </div>
            </div>
            <%--<div class="form-group">&lt;%&ndash; style="display: none" &ndash;%&gt;
                &lt;%&ndash;<input id="resetUser" type="reset" class="form-control">&ndash;%&gt;
                <input id="submitUser" type="submit" class="form-control">
            </div>--%>
        </form>
    </div>
    <jsp:include page="/topNav/script.jsp"></jsp:include>
    <script>
        var vali;
        function getInitVali(){
            vali = $('#sdss').bootstrapValidator({
                //debug:true,
                //onkeyup:false,//在 keyup 时验证
                //focusCleanup:true,//如果是 true 那么当未通过验证的元素获得焦点时，移除错误提示。避免和 focusInvalid 一起用
                //focusInvalid:false,//提交表单后，未通过验证的表单（第一个或提交之前获得焦点的未通过验证的表单）会获得焦点
                excluded:[":disabled"],//关键配置，表示只对于禁用域不进行验证，其他的表单元素都要验证
                trigger: "blur",//blur focus change keyup
                fields: {
                    userName: {
                        //trigger:"blur",
                        validators: {
                            notEmpty: {
                                message: '不能为空'
                            },
                            regexp: {
                                regexp: /^[a-zA-Z0-9_]+$/,
                                message: '用户名只能包含大写、小写、数字和下划线'
                            },
                            stringLength: {
                                min: 6,
                                max: 18,
                                message: '用户名长度必须在6到18位之间'
                            },
                            remote: {//ajax验证。server result:{"valid",true or false}
                                url: "/user/selectUserByName.jhtml",
                                message: '用户已存在',
                                //delay: 1000,
                                type: 'POST',
                                //自定义参数
                                /*data: {
                                    id: $("#id").val(),//#id
                                    userName: $('#userName').val(),
                                }*/
                                data: function(validator) {
                                    return {
                                        id: $("#id").val(),//#id
                                        userName: $("#userName").val(),
                                        //method : "checkUserName"//UserServlet判断调用方法关键字。
                                    }
                                },
                            }
                        }
                    },
                    realName: {
                        validators: {
                            notEmpty: {
                                message: '不能为空'
                            },
                            regexp:{
                                regexp:/^[\u4E00-\u9FA5]+[a-zA-Z]*$/,///^[\u4E00-\u9FA5]+(?:·[\u4E00-\u9FA5]+)*$/
                                message:'汉字开头，且只能出现字母、汉字',
                            },
                            stringLength: {
                                min: 2,
                                max: 8,
                                message: '姓名长度必须在2到8位之间'
                            },
                            different: {  //比较
                                field: 'userName', //需要进行比较的input name值
                                message: '姓名不能与用户名相同'
                            },
                        }
                    },
                    passwordQ: {
                        validators: {
                            notEmpty: {
                                message: '不能为空'
                            },
                            regexp: {
                                regexp: /^[a-zA-Z0-9_]+$/,
                                message: '用户名只能包含大写、小写、数字和下划线'
                            },
                            stringLength: {
                                min: 6,
                                max: 10,
                                message: '密码长度必须在6到10位之间'
                            },
                            different: {  //比较
                                field: 'userName', //需要进行比较的input name值
                                message: '密码不能与用户名相同'
                            },
                        }
                    },
                    password: {
                        validators: {
                            notEmpty: {
                                message: '不能为空'
                            },
                            identical: {  //比较
                                field: 'passwordQ', //需要进行比较的input name值
                                message: '两次密码不相同'
                            },
                        }
                    },
                    age: {
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
                                message:'输入年龄超出范围',
                            },
                        }
                    },
                    phone: {
                        validators: {
                            notEmpty: {
                                message: '不能为空'
                            },
                            regexp: {
                                regexp: /^1[3|5|7|8]{1}[0-9]{9}$/,
                                message: '请输入正确的手机号码'
                            },
                            stringLength: {
                                min: 11,
                                max: 11,
                                message: '请输入11位手机号码'
                            },
                        }
                    },
                    email: {
                        validators: {
                            notEmpty: {
                                message: '不能为空'
                            },
                            emailAddress: {
                                message: '邮箱地址输入有误'
                            }
                        }
                    },
                    sex:{
                        validators: {
                            choice: {
                                min: 1,
                                max: 1,
                                message: '选一个',
                            },
                        }
                    },
                    pay: {
                        validators: {
                            notEmpty: {
                                message: '不能为空'
                            },
                            regexp: {
                                regexp: /^[1-9]{1}[0-9]{0,4}[.]{0,1}[0-9]{0,2}$/,
                                message: '请输入正确的薪资'
                            },
                        }
                    },
                    entryTime: {
                        trigger:"blur",
                        validators: {
                            notEmpty: {
                                message: '请选择时间'
                            },

                        }
                    },
                    selectRole:{
                        trigger: "change",
                        validators: {
                            callback: {
                                message: '至少选择一个用户',
                                //返回true/false
                                callback: function (value, validator) {
                                    if (value.length == 0) {
                                        return false;
                                    } else {
                                        return true;
                                    }
                                }
                            }
                        }
                    }
                }
            })
        }
        var userForm;//表单初始值
        var userCheckArr = Array();//复选框数组
        var userInfo = {};//条件查询
        var startTime = "1970-01-01";
        var endTime = "2999-12-12";
        var nodeId;
        /*
         * 页面加载函数
         * */
        $(function(){getLiuxy();
            getRoleCheckbox("roleIds");//加载复选框
            getRoleCheckbox("roleIds1");//加载复选框 条件的
            getUserList();//执行表格
            getInitVali();//aaabb();//验证aaabb()
            getTimeChina("#entryTimeMin");
            getTimeChina("#entryTimeMax");
            userForm = $("#userForm").html();//备份
            $("#userForm").html("");//清空值
            $('#userList').on('draw.dt',function() {//表格重绘事件
                getToTrClick();
            });
            concatRegionSel("region_pdiv","<input value='0'>");//加载第一个地区选择下拉框
        })

        function concatRegionSel(id,node){
            $.post({
                url:"/region/queryRegionsById.jhtml",
                data:{id:$(node).val()},
                success:function(ref){
                    if(ref.code == 200){
                        var arr = ref.data;
                        $(node).nextAll().remove();//.parent()
                        if(arr.length > 0){
                            var str = ''+
                                '<select style="width: 120px" name="region_sel" class="form-control col-sm-3" onchange="concatRegionSel(\''+id+'\',this)">'+
                                '<option value="1">==请选择==</option>';
                            for(var i=0;i<arr.length;i++){
                                str += "<option value='"+arr[i].id+"'>"+arr[i].name+"</option>";
                            }
                            str += '</select>';
                            $("#"+id).append(str);
                        }
                    }
                }
            })
        }

        function onBianji(node){
            if($(node).val() == "编辑"){
                concatRegionSel("region_div","<input value='0'>");
                $("#region_name").attr("style","display: none");
                $(node).val("取消编辑");
            }else{
                $("#region_name").attr("style","width: 200px");
                $("#region_div").html("");
                $(node).val("编辑");
            }
        }

        function getLiuxy(){
            $("#liuxy").on("click","tr th",function(){
                console.log("绑定的事件")
            });
        }
        /*
         * 条件查询
         * */
        function getUserInfo(){
            userInfo = {};//清空
            userInfo.userName = $("#userNameM").val();
            userInfo.realName = $("#realNameM").val();
            userInfo.ageMin = $("#ageMin").val();
            userInfo.ageMax = $("#ageMax").val();
            userInfo.payMin = $("#payMin").val();
            userInfo.payMax = $("#payMax").val();
            userInfo.entryTimeMin = $("#entryTimeMin").val();
            userInfo.entryTimeMax = $("#entryTimeMax").val();
            var list = $("#roleIds1sel").val();
            userInfo.checkboxStr = list.toString();
            userInfo.region1 = $("#region_pdiv").find("select:eq(0) option:selected").val();
            userInfo.region2 = $("#region_pdiv").find("select:eq(1) option:selected").val();
            userInfo.region3 = $("#region_pdiv").find("select:eq(2) option:selected").val();
            getUserList(userInfo);
        }
        /*
         * 请求列表
         * */
        function getUserList(userM){
            $("#userList").DataTable({
                "searching": false,//是否允许检索
                "iDisplayLength": 5,//每页默认显示几行
                "lengthMenu": [1,5,20,50],//左上角下拉框的值
                "processing": true,
                "serverSide": true,
                "destroy":true,//清理缓存
                //"sortable" : true,//是否启用排序
                "ordering":false,
                "ajax": {
                    "url": "/user/queryUser.jhtml",
                    "data":userM,
                },
                "columns": [
                    { "data": function(data){
                            var str = data.id;
                            if(!str){
                                alert(0)
                            }
                            return '<input name="userCheck" type="checkbox" value="'+str+'">';
                        } },
                    { "data": "realName" },
                    { "data": "userName" },
                    { "data": "age" },
                    { "data": "sex",render:function (a) {//a,b,c,d
                            /*console.log(a);
                            console.log(b);
                            console.log(c);
                            console.log(d);
                            console.log("=======");*/
                            return (a==1?'男':a==2?'女':'不详');
                        }
                    },
                    { "data": "phone" },
                    { "data": "email" },
                    {"data":"pay"},
                    {"data":"entryTime"},
                    {"data":"status",render:function (a) {
                            return (a?"锁定":"正常");
                        }},
                    {"data":"roleName"},
                    {"data":"regionName"},
                    {"data":function(data){
                            return '<img src="http://192.168.217.128/img/'+data.imgPath+'" alt="图片丢失" height="60px" ">';
                        }},
                    { "data": function(data){
                            return '<div class="btn-group" role="group" aria-label="...">'+
                            '<button class="btn btn-info btn-sm" onclick="getUpdateUser('+data.id+')"><i class="glyphicon glyphicon-pencil"></i>修改</button>'+
                                '<button class="btn btn-danger btn-sm" onclick="getDeleteUser('+data.id+')"><i class="glyphicon glyphicon-remove"></i>删除</button>'+
                                '<button '+(data.status?"":"disabled")+' class="btn btn-success btn-sm" onclick="getUpdateUserStatus('+data.id+','+data.status+')"><i class="glyphicon glyphicon-edit"></i>解锁</button>'+
                                '</div>'+
                                '<button class="btn btn-default btn-sm" onclick="getResetUserPass('+data.id+')"><i class="glyphicon glyphicon-refresh"></i>密码重置</button>';
                        } },
                ],
                "language": {
                    "url": "/js/Chinese.json"
                }
            });
        }
        /*
         * 解锁用户
         * */
         function getUpdateUserStatus(id,boo){
             event.stopPropagation();//阻止事件冒泡
             if(boo){
                 bootbox.confirm({//弹出确认框
                     size: "small",
                     message: "确定要解锁吗？",
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
                             $.ajax({
                                 url:"/user/updateUserStatus.jhtml",
                                 data:{id:id},
                                 type:"post",
                                 dataType:"json",
                                 success:function(ref){
                                     if(ref.code == 200){
                                         getTooltip("解锁成功","",2500);
                                         getUserInfo();
                                     }
                                 }
                             })
                         }
                     }
                 })
             }
         }
         /**
          *     重置密码
          * */
         function getResetUserPass(id){
             event.stopPropagation();//阻止事件冒泡
             bootbox.confirm({//弹出确认框
                 size: "small",
                 message: "确定要重置该用户密码吗？",
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
                     if (result) {
                        $.ajax({
                            url:"/user/updateResetUserPass.jhtml",
                            data:{id:id},
                            type:"post",
                            success:function(ref){
                                if(ref.code == 200){
                                    getTooltip("重置成功")
                                }
                            }
                        })
                     }
                 }
             })
         }
        /*
         * 删除用户
         * */
        function getDeleteUser(id){
            event.stopPropagation();//阻止事件冒泡
            bootbox.confirm({//弹出确认框
                size: "small",
                message: "确定要删除吗？",
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
                        $.ajax({
                            url:"/user/deleteUserById.jhtml",
                            data:{id:id},
                            type:"post",
                            dataType:"json",
                            success:function(ref){
                                if(ref.code == 200){
                                    getTooltip("删除成功","",2500);
                                    getUserInfo();
                                }
                            }
                        })
                    }
                }
            })
        }
        /*
         * 修改用户
         * */
        function getUpdateUser(id){
            event.stopPropagation();//阻止事件冒泡
            $.ajax({
                url:"/user/queryUserById.jhtml",
                data:{id:id},
                type:"post",
                dataType:"json",
                success:function(ref){
                    if(ref.code == 200){
                        var upd_boot = bootbox.dialog({
                            title: '修改用户',
                            message: userForm,
                            size: 'large',//有效值为'large'和'small'
                            buttons: {
                                ok: {
                                    label: "<i class='glyphicon glyphicon-pencil'></i>修改",
                                    className: 'btn-info',
                                    callback: function(data){
                                        //$("#sdss").submit();
                                        var boo = $('#sdss').data('bootstrapValidator').validate();
                                        if(boo.isValid()){
                                                var userJsonForm = {};
                                                userJsonForm.id = $("#id").val();
                                                userJsonForm.realName = $("#realName").val();
                                                userJsonForm.age = $("#age").val();
                                                userJsonForm.phone = $("#phone").val();
                                                userJsonForm.email = $("#email").val();
                                                userJsonForm.sex = $("input[name='sex']:checked").val();
                                                userJsonForm.pay = $("#pay").val();
                                                userJsonForm.entryTime = $("#entryTime").val();
                                                userJsonForm.imgPath = $("#imgPath").val();
                                                var role_checked = $("#roleIdssel").val();
                                                userJsonForm.roleId = role_checked.toString();
                                                var length = $("#region_div").find("select").length;
                                                if(length == 3){
                                                    userJsonForm.region1 = $("#region_div").find("select:eq(0) option:selected").val();
                                                    userJsonForm.region2 = $("#region_div").find("select:eq(1) option:selected").val();
                                                    userJsonForm.region3 = $("#region_div").find("select:eq(2) option:selected").val();
                                                }
                                                $.ajax({
                                                    url:"/user/updateUser.jhtml",
                                                    data:userJsonForm,//序列化参数
                                                    type:"post",
                                                    dataType:"json",
                                                    success:function(res){
                                                        if(res.code == 200){
                                                            getTooltip("修改成功","",2500);
                                                            getUserInfo();
                                                        }
                                                    }
                                                })
                                            }else{
                                                return false;
                                            }

                                    }
                                },
                                noclose: {
                                    label: "<i class='glyphicon glyphicon-refresh'></i>重置",
                                    className: 'btn-warning',
                                    callback: function(){
                                        $("#roleIdssel").val([]);//清空
                                        $("#roleIdssel").selectpicker('destroy');
                                        $("#roleIdssel").selectpicker();
                                        $("#sdss")[0].reset();//重置内容
                                        $("#sdss").data('bootstrapValidator').destroy();//重置验证并刷新
                                        $('#sdss').data('bootstrapValidator', null);
                                        getInitVali();
                                        return false;
                                    }
                                },
                                cancel: {
                                    label: "<i class='glyphicon glyphicon-remove-sign'>关闭",
                                    className: 'btn-danger',
                                    callback: function(data){

                                    }
                                },
                            },
                        });
                        //等待弹出框将表单加载出来 然后给表单赋值
                        getInitVali();//验证
                        getImageUpload();
                        getTimeChina("#entryTime");
                        $("#region_name").val(ref.data.regionName);
                        $("#password").val("000000");
                        $("#passwordQ").val("000000");
                        $("#passwordStyle").css({"display":"none"});//style="display: none"
                        $("#passwordStyleQ").css({"display":"none"});
                        $("#id",upd_boot).val(ref.data.id);
                        $("#userName",upd_boot).val(ref.data.userName);
                        $("#userName",upd_boot).attr("disabled",true);
                        $("#realName",upd_boot).val(ref.data.realName);
                        $("#age",upd_boot).val(ref.data.age);
                        $("#phone",upd_boot).val(ref.data.phone);
                        $("#email",upd_boot).val(ref.data.email);
                        $("input[name=sex]",upd_boot).each(function(){
                            if(this.value == ref.data.sex){//选中状态的判断
                                $(this).attr("checked","true");
                            }
                        });
                        $("#pay").val(ref.data.pay);
                        $("#entryTime").val(ref.data.entryTime);
                        $("#imgPath").val(ref.data.imgPath);
                        $("#imgPathh").attr("src","http://192.168.217.128/img/"+ref.data.imgPath);
                        var list = ref.data.list;
                        $("#roleIdssel").selectpicker('val', list);

                        $("#sdss").submit();
                    }else{
                        getTooltip("请求异常","",2500);
                    }
                }
            })
        }
        /*
         * 添加用户
         * */
        var add_boot;
        function getAddUser(){
            add_boot = bootbox.dialog({
                title: '添加用户',
                message: userForm,
                size: 'large',//有效值为'large'和'small'
                buttons: {
                    ok: {
                        label: "<i class='glyphicon glyphicon-plus-sign'>添加",
                        className: 'btn-info',
                        callback: function(data){//添加回调函数
                            //将 userJsonForm 传给后台
                            $("#sdss").submit();
                            var boo = $('#sdss').data('bootstrapValidator');
                            boo.validate();
                            if(boo.isValid()){
                                var userJsonForm = {};
                                userJsonForm.userName = $("#userName").val();
                                userJsonForm.realName = $("#realName").val();
                                userJsonForm.password = $("#password").val();
                                userJsonForm.age = $("#age").val();
                                userJsonForm.phone = $("#phone").val();
                                userJsonForm.email = $("#email").val();
                                userJsonForm.sex = $("input[name='sex']:checked").val();
                                userJsonForm.pay = $("#pay").val();
                                userJsonForm.entryTime = $("#entryTime").val();
                                var role_checked = $("#roleIdssel").val();
                                userJsonForm.roleId = role_checked.toString();
                                userJsonForm.imgPath = $("#imgPath").val();
                                var regionLength = $("#region_div").find("select").length;
                                if(regionLength == 3){
                                    userJsonForm.region1 = $("#region_div").find("select:eq(0) option:selected").val();
                                    userJsonForm.region2 = $("#region_div").find("select:eq(1) option:selected").val();
                                    userJsonForm.region3 = $("#region_div").find("select:eq(2) option:selected").val();
                                    $.ajax({
                                        url:"/user/addUser.jhtml",
                                        data:userJsonForm,//$("#sdss",add_boot).serialize() 序列化参数 需要有name
                                        type:"post",
                                        dataType:"json",
                                        success:function(ref){
                                            if(ref.code == 200){
                                                getTooltip("添加成功","",2500);
                                                getUserList();
                                            }
                                        }
                                    })
                                }else{
                                    getTooltip("未选择地区");
                                    return false;
                                }
                            }else{
                                return false;
                            }
                        }
                    },
                    noclose: {
                        label: "<i class='glyphicon glyphicon-refresh'></i>重置",
                        className: 'btn-warning',
                        callback: function(){//重置回调函数 return false 为不关闭框
                            //$("#resetUser",add_boot).click();
                            $("#roleIdssel").val([]);//清空
                            $("#roleIdssel").selectpicker('destroy');
                            $("#roleIdssel").selectpicker();
                            $("#sdss")[0].reset();//重置内容
                            $("#sdss").data('bootstrapValidator').destroy();//重置验证并刷新
                            $('#sdss').data('bootstrapValidator', null);
                            getInitVali();
                            return false;
                        }
                    },
                    cancel: {
                        label: "<i class='glyphicon glyphicon-remove-sign'>关闭",
                        className: 'btn-danger',
                        callback: function(data){//关闭回调函数

                        }
                    },
                },
            });/*$("#userForm").html($("#sdss",add_boot));*/
            $("#roleIdssel").selectpicker();
            getTimeChina("#entryTime");
            getImageUpload();
            $("#imgPathh").remove();
            getInitVali();//验证
            concatRegionSel("region_div","<input value='0'>");//加载增加框第一个地区选择下拉框
            $("#region_name").remove();
            $("#region_butt").remove();
        }

        /*
         * 绑定tr点击事件
         * */
        function getToTrClick(){
            $("input[name='userCheck']").each(function(){
                var node = $(this).parent().parent();
                node.attr("none",false);
                node.bind("click",function(){
                    var nodeC = (node.find("td:eq(0)")).find("input:eq(0)");
                    if(!eval(node.attr("none"))){
                        userCheckArr.push(nodeC.val());
                        node.attr("style","background:yellow");
                        node.attr("none",true);
                        nodeC.prop("checked",true);
                    }else{
                        var i = userCheckArr.indexOf(nodeC.val());
                        userCheckArr.splice(i,1);
                        node.removeAttr("style");
                        node.attr("none",false);
                        //nodeC.removeAttr("checked");
                        nodeC.prop("checked",false);
                    }
                })
                if(userCheckArr.indexOf($(this).val()) != -1){
                    $(this).attr("checked","true");
                    node.attr("style","background:yellow");
                    node.attr("none",true);
                }
            })
        }
        /*
         * 批量删除
         * */
        function getDeleteAll(){
            if(userCheckArr.length > 0){
                bootbox.confirm({//弹出确认框
                    size: "small",
                    message: "确定要删除吗？",
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
                            $.ajax({
                                url:"/user/deleteAll.jhtml",
                                data:{str:userCheckArr.toString()},
                                type:"post",
                                dataType:"json",
                                success:function(ref){
                                    if(ref.code == 200){
                                        getTooltip("删除成功","",2500);
                                        getUserInfo();
                                    }
                                }
                            })
                        }
                    }
                })
            }else{
                getTooltip("请选择至少一条","",1500);
            }
        }
        /*
         * 日期控件
         * */
        function getTimeChange(st,et,no){
            startTime = st;
            endTime = et;
            nodeId = no;
        }
        function getTimeChina(timeName){
            $(timeName).datetimepicker({
                format: 'YYYY-MM-DD',
                locale: moment.locale('zh-CN'),
                showClear: true,// initialDate:"",
                minDate: new Date("1970-01-01".replace(/-/,"/")),//new Date("2019-08-01".replace(/-/,"/"))
                maxDate: new Date("2999-12-12".replace(/-/,"/")),
            }).on("focus",function(){
                console.log("单击事件");
            }).on("focus",function(e){
                console.log("得到焦点");
                console.log(e);
            }).on("blur", function (){
                console.log(new Date()+"焦点离开事件");
                if(startTime != ""){
                    $(nodeId).datetimepicker("minDate",new Date(startTime.replace(/-/,"/")));
                }
                if(endTime != ""){
                    $(nodeId).datetimepicker("maxDate",new Date(endTime.replace(/-/,"/")));
                }
            })
        }
        /*
         * 请求刷新动态复选框
         * */
        function getRoleCheckbox(roleId){
            $.ajax({
                url:"/role/queryRoleAll.jhtml",
                type:"post",
                dataType:"json",
                success:function (ref){
                    if(ref.code == 200){
                        var list = ref.data;
                        var str = '<select name="selectRole" id="'+roleId+'sel" title="请选择角色"  multiple>';
                        for(var i=0;i<list.length;i++){
                            str += "<option value='"+list[i].id+"'>"+list[i].name+"</option>";
                        }
                        str += "</select>";
                        $("#userForm").html(userForm);//放回页面方便拼接动态复选框
                        //$("#"+roleId).html("");
                        $("#"+roleId).html(str);//将复选框放入表单
                        userForm = $("#userForm").html();//从新赋值
                        $("#userForm").html("");//清空
                        $("#"+roleId+"sel").selectpicker();
                    }
                }
            })
        }
        function getReset() {
            $('#roleIds1sel').selectpicker('deselectAll');
            //$("#entryTimeMin").datetimepicker("maxDate", new Date("2999-12-12".replace(/-/, "/")));
            //$("#entryTimeMax").datetimepicker("minDate", new Date("1970-01-01".replace(/-/, "/")));
        }
        /*
         * 图片上传
         * */
        function getImageUpload(){
            $(".myfile").fileinput({
                //上传的地址
                uploadUrl:"/img/imgUpload.jhtml",
                uploadAsync : true, //默认异步上传
                showUpload : false, //是否显示上传按钮,跟随文本框的那个
                showRemove : false, //显示移除按钮,跟随文本框的那个
                showCaption : true,//是否显示标题,就是那个文本框
                showPreview : true, //是否显示预览,不写默认为true
                dropZoneEnabled : false,//是否显示拖拽区域，默认不写为true，但是会占用很大区域
                maxFileCount : 1, //表示允许同时上传的最大文件个数
                enctype : 'multipart/form-data',
                validateInitialCount : true,
                previewFileIcon : "<i class='glyphicon glyphicon-king'></i>",
                msgFilesTooMany : "选择上传的文件数量({n}) 超过允许的最大数值{m}！",
                allowedFileTypes : [ 'image' ],//配置允许文件上传的类型
                allowedPreviewTypes : [ 'image' ],//配置所有的被预览文件类型
                allowedPreviewMimeTypes : [ 'jpg', 'png', 'gif' ],//控制被预览的所有mime类型
                language : 'zh'
            })
            //异步上传返回结果处理
            $(".myfile").on("fileuploaded", function(event, data, previewId, index) {//event, data, previewId, index
                $("#imgPath").val(data.response.data);
            });
        }
        /*
         * 测试
         * */
        function aaa(){
            var sqsq = "bbb";
            eval(sqsq+'()');
        }
        function bbb(){
            alert(0);
        }
        function excelEduce(){
            if(educeArr.length > 0){
                location.href = "/user/excelEduce.jhtml?educeStr="+educeArr;
            }else{
                getTooltip("至少选择一项");
            }
        }
        function excelEduceUser(){//userFormEduce   //serializeArray()表单数组  //serialize()序列化表单
            $("#userFormEduce").submit();//excel导出
        }
        function wordEduce(){
            if(educeArr.length > 0){
                location.href = "/user/wordEduce.jhtml?educeStr="+educeArr;
            }else{
                getTooltip("至少选择一项");
            }
        }
        function pdfEduce(){
            if(educeArr.length > 0){
                location.href = "/user/pdfEduce.jhtml?educeStr="+educeArr;
            }else{
                getTooltip("至少选择一项");
            }
        }
        var educeArr = [];
        function getEduce(node){
            event.stopPropagation();//阻止事件冒泡 node.innerText
            //var str = $(node).attr("liuxy");
            if(!$(node).attr("liuxy")){
                $(node).attr("liuxy","true");
                $(node).css("background","blue");
                educeArr.push(node.innerText);
            }else{
                $(node).removeAttr("liuxy");
                //$(node).attr("style","width:31px");
                $(node).removeAttr("style");
                var i = educeArr.indexOf(node.innerText);
                educeArr.splice(i,1);
            }
        }

    </script>
</body>
</html>
