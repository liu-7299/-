<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 2019/8/25
  Time: 18:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <jsp:include page="/topNav/head.jsp"></jsp:include>
    <title>菜单展示</title>
</head>
<body>
    <%-- 导航条 --%>
    <jsp:include page="/topNav/nav.jsp"></jsp:include>
    <div class="container">
        <div class="row">
            <div class="col-md-12">
                <div class="panel panel-success">
                    <div>&nbsp;
                        <button onclick="getAddMenu()" type="button" class="btn btn-primary"><i class="glyphicon glyphicon-plus"></i>添加节点</button>
                        <button onclick="getUpdateMenu()" type="button" class="btn btn-warning"><i class="glyphicon glyphicon-pencil"></i>修改节点</button>
                        <button onclick="getDeleteMenu()" type="button" class="btn btn-danger"><i class="glyphicon glyphicon-trash"></i>删除节点</button>
                        <button onclick="getDelSel()" type="button" class="btn btn-success"><i class="glyphicon glyphicon-remove-circle"></i>取消选中</button>
                    </div>
                    <div class="panel-heading">树形菜单</div>
                    <div class="panel-body">
                        <ul id="menuTree" class="ztree" style="width:1000px; overflow:auto;"></ul>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <div id="menuStr" style="display: none">
        <form id="sdss" class="form-horizontal"><input type="hidden" id="id">
            <div class="form-group">
                <label class="col-sm-2 control-label">上级节点</label>
                <div class="col-sm-4">
                <input type="text" readonly class="form-control" id="pname" placeholder="上级节点">
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">节点名</label>
                <div class="col-sm-4">
                <input type="text" class="form-control" id="name" placeholder="请输入节点名">
                </div>
            </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label">节点类型</label>
                    <div class="col-sm-4">
                    <input type="radio" name="checkboxType" value="1">菜单
                    <input type="radio" name="checkboxType" value="2">按钮
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label">节点路径</label>
                    <div class="col-sm-4">
                    <input type="text" class="form-control" id="url" placeholder="请输入节点路劲">
                    </div>
                </div>
        </form>
    </div>
    <jsp:include page="/topNav/script.jsp"></jsp:include>
<script>

    var nodeStr = '';

    $(function(){
        nodeStr = $("#menuStr").html();
        $("#menuStr").html("");
        initZtree();
    })
    var zTreeObj;
    function initZtree(){
        $.ajax({
            url:"/menu/queryMenu.jhtml",
            type:"post",
            success:function(ref){
                //console.log(ref.data)
                var setting = {
                    data: {
                        simpleData: {
                            enable: true,
                        },
                        key: {
                            url: "xUrl"
                        }
                    },
                    callback: {
                        beforeClick: zTreeBeforeClick
                    },
                };
                var nodes = ref.data;
                zTreeObj = $.fn.zTree.init($("#menuTree"), setting, nodes);
                zTreeObj.expandAll(false);
            }
        })
    }
    function zTreeBeforeClick(e,n){
        n.xUrl = "";
        /*console.log(n)
        console.log(n.url)
        if(n.type == 1) n.xUrl = "";
        if(n.type == 2) n.xUrl = "";//n.url
        zTreeObj.updateNode(n);
        console.log(n.xUrl)*/
        return true;
    }
    function  getAddMenu(){
        var node = getNodes();
        if(!!node){
                bootbox.dialog({
                    title: '添加节点',
                    message: nodeStr,
                    size: 'large',//有效值为'large'和'small'
                    buttons: {
                        ok: {
                            label: "<i class='glyphicon glyphicon-plus-sign'>添加",
                            className: 'btn-info',
                            callback: function(data){//添加回调函数
                            var menu = {};
                            menu.name = $("#name").val();
                            menu.pid = node.id;
                            menu.type = $("input[name='checkboxType']:checked").val();
                            menu.url = $("#url").val();
                            console.log(menu)
                                $.ajax({
                                    url:"/menu/addMenu.jhtml",
                                    data:menu,
                                    dataType:"json",
                                    success:function(ref){
                                        if(ref.code == 200){
                                            getTooltip("添加成功","",2500)
                                            var newNode = {id:ref.data,name:menu.name,pId:menu.pid,url:menu.url,open:false,xUrl:""};
                                            zTreeObj.addNodes(node,0,newNode);
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
                        }
                    }
                })
                $("#pname").val(node.name);
        }else{
            getTooltip("一个没选","",2500)
        }
    }
    function  getUpdateMenu(){
        var node = getNodes();
        if(!!node){
            bootbox.dialog({
                title: '修改节点',
                message: nodeStr,
                size: 'large',//有效值为'large'和'small'
                buttons: {
                    ok: {
                        label: "<i class='glyphicon glyphicon-pencil'>修改",
                        className: 'btn-info',
                        callback: function(data){//添加回调函数
                            var menu = {};
                            menu.name = $("#name").val();
                            menu.id = node.id;
                            menu.pid = node.pId;
                            menu.type = $("input[name='checkboxType']:checked").val();
                            menu.url = $("#url").val();
                            console.log(menu)
                            $.ajax({
                                url:"/menu/updateMenu.jhtml",
                                data:menu,
                                success:function(ref){
                                    if(ref.code == 200){
                                        getTooltip("修改成功","",2500)
                    node.name = menu.name;
                    node.url =  menu.url;
                    zTreeObj.updateNode(node);  // 调用updateNode修改属性，一定要加这一句，不然reAsyncChildNodes没有任何反应
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
                    }
                }
            })
            var pnode = node.getParentNode();
            var str = "没有上级";
            if(!!pnode){
                str = pnode.name;
            }
            $("#pname").val(str);
            $("#name").val(node.name);
            $("#url").val(node.url);
            $("input[name='checkboxType']").each(function(){
                if(this.value == node.type){
                    $(this).attr("checked",true);
                }
            })
            $("#url").val(node.url);//console.log(node)
        }else{
            getTooltip("一个没选","",2500)
        }
    }
    function  getDeleteMenu(){
        var node = getNodes();
        if(!!node){
            if(node.id == 1){
                getTooltip("根节点不能删除")
                return false;
            }
            bootbox.confirm({//弹出确认框
                size: "small",
                message: "确定要删除该地区吗？(该地区下所属地区都会被删除)",
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
                        var treeObj = $.fn.zTree.getZTreeObj("menuTree");
                        var nodes = treeObj.transformToArray(node);
                        var str = [];
                        for(var i=0;i<nodes.length;i++){
                            str.push(nodes[i].id);
                        }
                        console.log(str)
                        $.ajax({
                            url:"/menu/deleteMenuAll.jhtml",
                            data:{"str":str.toString()},
                            success:function(ref){
                                if(ref.code == 200){
                                    getTooltip("删除成功","",2500)
                        //zTreeObj.selectNode(node.parentNode());
                        zTreeObj.removeNode(node);
                                }
                            }
                        })
                    }
                }
            })
        }else{
            getTooltip("一个没选","",2500)
        }
    }

    function getDelSel(){
        zTreeObj.cancelSelectedNode();//先取消所有的选中状态
    }

    function getNodes(){
        var nodeArr = zTreeObj.getSelectedNodes();
        return nodeArr[0];
    }

    </script>
</body>
</html>
