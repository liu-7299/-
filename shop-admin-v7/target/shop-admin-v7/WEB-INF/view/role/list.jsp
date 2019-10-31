<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 2019/8/22
  Time: 0:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <jsp:include page="/topNav/head.jsp"></jsp:include>
    <title>角色管理</title>
</head>
<body>
<%-- 导航条 --%>
<jsp:include page="/topNav/nav.jsp"></jsp:include>

    <div class="container">
        <div class="panel panel-info">
            <div class="panel-heading">角色列表</div>
            <div class="panel-body">
                <div style="float: right;">
                    <button onclick="getAddRole()" type="button" class="btn btn-primary"><i class="glyphicon glyphicon-plus"></i>添加角色</button>
                </div>
                <table id="roleList" class="table table-striped table-bordered" style="width:100%">
                    <thead>
                    <tr>
                        <th>全选<input type="checkbox"></th>
                        <th>角色名</th>
                        <th>操作</th>
                    </tr>
                    </thead>
                    <tbody id="roleTbody" style="text-align: center"></tbody>
                </table>
            </div>
        </div>
    </div>
    <div id="roleStr" style="display: none">
        <form id="sdss" class="form-horizontal"><input type="hidden" id="id">
            <div class="form-group">
                <label class="col-sm-2 control-label">角色名</label>
                <div class="col-sm-4">
                    <input type="text" class="form-control" id="name" placeholder="请输入角色名">
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">菜单管理</label>
                <div class="col-sm-4">
                    <ul id="menuTree" class="ztree" style="width:1000px; overflow:auto;"></ul>
                </div>
            </div>
        </form>
    </div>
    <jsp:include page="/topNav/script.jsp"></jsp:include>
    <script>
        var roleStr;
        $(function(){
            initZtree();
            getRoleList();
            setTimeout(function(){
                console.log('我是页面加载函数')
                roleStr = $("#roleStr").html();
                $("#roleStr").html("");
            })
        })

        //角色列表
        function getRoleList(){
            $("#roleList").DataTable({
                "searching": false,//是否允许检索
                "iDisplayLength": 5,//每页默认显示几行
                "lengthMenu": [1,5,20,50],//左上角下拉框的值
                "processing": true,
                "serverSide": true,
                "destroy":true,//清理缓存
                "ajax": {
                    "url": "/role/queryRole.jhtml",
                },
                "columns": [
                    { "data": function(data){
                            return '<input name="userCheck" type="checkbox" value="'+data.id+'">';
                        } },
                    { "data": "name" },
                    { "data": function(data){
                            return '<button class="btn btn-warning btn-sm" onclick="getUpdateRole('+data.id+')"><i class="glyphicon glyphicon-pencil"></i>修改</button> '+
                                '<button class="btn btn-danger btn-sm" onclick="getDeleteRole('+data.id+')"><i class="glyphicon glyphicon-remove"></i>删除</button>';
                        } },
                ],
                "language": {
                    "url": "/js/Chinese.json"
                }
            });
        }

        function getAddRole(){
            bootbox.dialog({
                title: '添加角色',
                message: roleStr,
                size: 'large',//有效值为'large'和'small'
                buttons: {
                    ok: {
                        label: "<i class='glyphicon glyphicon-plus-sign'>添加",
                        className: 'btn-info',
                        callback: function(data){//添加回调函数
                            var nodeArr = zTreeObj.getCheckedNodes();
                            var nodeIdArr = [];
                            for(var i=0;i<nodeArr.length;i++){
                                nodeIdArr.push(nodeArr[i].id);
                            }
                            //var role = $("#sdss").serialize();
                            var role = {};
                            role.name = $("#name").val();
                            role.nodeIdArr = nodeIdArr.toString();
                            $.ajax({
                                url:"/role/addRole.jhtml",
                                data:role,//$("#sdss",add_boot).serialize() 序列化参数 需要有name
                                type:"post",
                                dataType:"json",
                                success:function(ref){
                                    if(ref.code == 200){
                                        getTooltip("添加成功","",2500);
                                        getRoleList();
                                    }
                                }
                            })
                        }
                    },
                    cancel: {
                        label: "<i class='glyphicon glyphicon-remove-sign'>关闭",
                        className: 'btn-danger',
                        callback: function(data){//关闭回调函数

                        }
                    },
                },
            });
            initZtree();
        }

        function getUpdateRole(id){
            $.ajax({
                url:"/role/queryRoleById.jhtml",
                data:{id:id},
                type:"post",
                success:function(ref){
                    bootbox.dialog({
                        title: '修改角色',
                        message: roleStr,
                        size: 'large',//有效值为'large'和'small'
                        buttons: {
                            ok: {
                                label: "<i class='glyphicon glyphicon-plus-sign'>修改",
                                className: 'btn-info',
                                callback: function(data){//添加回调函数
                                    var nodeArr = zTreeObj.getCheckedNodes();
                                    var nodeIdArr = [];
                                    for(var i=0;i<nodeArr.length;i++){
                                        nodeIdArr.push(nodeArr[i].id);
                                    }
                                    var role = {};

                                    role.id = $("#id").val();
                                    role.name = $("#name").val();
                                    role.nodeIdArr = nodeIdArr.toString();
                                    $.ajax({
                                        url:"/role/updateRole.jhtml",
                                        data:role,//$("#sdss",add_boot).serialize() 序列化参数 需要有name
                                        type:"post",
                                        success:function(ref){
                                            if(ref.code == 200){
                                                getTooltip("修改成功","",2500);
                                                getRoleList();
                                            }
                                        }
                                    })
                                }
                            },
                            cancel: {
                                label: "<i class='glyphicon glyphicon-remove-sign'>关闭",
                                className: 'btn-danger',
                                callback: function(data){//关闭回调函数

                                }
                            },
                        },
                    });
                    initZtree();
                    $("#id").val(ref.data.id);
                    $("#name").val(ref.data.name);
                    var arr = ref.data.list;
                    setTimeout(function(){
                        for(var i=0;i<arr.length;i++){
                            var node = zTreeObj.getNodeByParam("id",arr[i],null);
                            zTreeObj.checkNode(node,true);
                        }
                    },50)
                }
            })
        }

        function getDeleteRole(id){
            $.ajax({
                url:"/role/deleteRoleById.jhtml",
                data:{id:id},
                type:"post",
                success:function(ref){
                    if(ref.code == 200){
                        getTooltip("删除成功","",2500);
                        getRoleList();
                    }
                }
            })
        }

        var zTreeObj;
        var code = "error";
        function initZtree(){
            $.ajax({
                url:"/menu/queryMenu.jhtml",
                type:"post",
                //async:true,
                success:function(ref){
                    code = "ok";
                    var setting = {
                        data: {
                            simpleData: {
                                enable: true,
                            }
                        },
                        check: {
                            enable: true,
                        },
                        callback: {
                            beforeClick: function(name,n,id){
                                n.url = "";
                            },
                        },
                    };
                    var nodes = ref.data;
                    zTreeObj = $.fn.zTree.init($("#menuTree"), setting, nodes);
                    zTreeObj.expandAll(false);
                    console.log("我是初始化函数")
                }
            })
        }
    </script>
</body>
</html>
