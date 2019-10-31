<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 2019/9/30
  Time: 15:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <jsp:include page="/topNav/head.jsp"></jsp:include>
    <title>Title</title>
</head>
<body>
<jsp:include page="/topNav/nav.jsp"></jsp:include>
    <div class="container">
    <div class="row">
        <div class="col-md-12">
            <div class="panel panel-success">
                <div>&nbsp;
                    <button onclick="getAddZtree()" type="button" class="btn btn-primary"><i class="glyphicon glyphicon-plus"></i>添加分类</button>
                    <button onclick="getUpdateZtree()" type="button" class="btn btn-warning"><i class="glyphicon glyphicon-pencil"></i>修改分类</button>
                    <button onclick="getDeleteZtree()" type="button" class="btn btn-danger"><i class="glyphicon glyphicon-trash"></i>删除分类</button>
                </div>
                <div class="panel-heading">分类树形</div>
                <div class="panel-body">
                    <ul id="tree" class="ztree" style="width:1000px; overflow:auto;"></ul>
                </div>
            </div>
        </div>
    </div>
</div>

<div id="flStr" style="display: none">
    <form id="sdss" class="form-horizontal"><input type="hidden" id="id">
        <input type="hidden" id="pid">
        <div class="form-group">
            <label class="col-sm-2 control-label">归属名</label>
            <div class="col-sm-4">
                <input type="text" readonly class="form-control" id="pname" placeholder="归属名">
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-2 control-label">分类名</label>
            <div class="col-sm-4">
                <input type="text" class="form-control" id="name" placeholder="请输入分类名">
            </div>
        </div>
    </form>
</div>

<jsp:include page="/topNav/script.jsp"></jsp:include>
    <script>
    var flStr = "";
    $(function(){
        flStr = $("#flStr").html();
        $("#flStr").html("");
        initZtree();
    })

    var zTreeObj;
    function initZtree(){
        $.ajax({
            url:"/fl/queryFl.jhtml",
            type:"post",
            success:function(ref){
                var setting = {
                    view: {
                        showLine: true,//是否显示节点之间的连线
                        fontCss:{'color':'pink','font-weight':'bold'},//字体样式函数
                    },
                    data: {
                        simpleData: {
                            enable:true,//简单数据模式
                        }
                    },
                    callback: {
                        beforeClick: function(treeId, treeNode) {

                        },
                    },
                };
                var nodes = ref
                zTreeObj = $.fn.zTree.init($("#tree"), setting, nodes);
                zTreeObj.expandAll(false);
            }
        })
    }
    function getAddZtree(){//取到选中节点id当做新节点pid即可 id自增 name从前台获取
        var node = getNodes();
        if(!!node){
            bootbox.dialog({
                title: '添加',
                message: flStr,
                size: 'large',//有效值为'large'和'small'
                buttons: {
                    ok: {
                        label: "<i class='glyphicon glyphicon-plus-sign'>添加",
                        className: 'btn-info',
                        callback: function(data){//添加回调函数
                            var fl = {};
                            fl.pid = $("#pid").val();
                            fl.name = $("#name").val();
                            $.ajax({
                                url:"/fl/addFl.jhtml",
                                data:fl,
                                success:function(ref){
                                    if(ref.code == 200){
                                        getTooltip("添加成功","",2500)
                                        var newNode = {id:ref.data,name:fl.name,pId:fl.pid,open:false};
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
                    },
                },
            });
            $("#pid").val(node.id);
            $("#pname").val(node.name);
        }else{
            getTooltip("至少选中一个");
        }
    }
    function getUpdateZtree(){//取到选中节点id 获取pid 和 新name
        var node = getNodes();
        if(!!node){
            if(node.id != 0){
                bootbox.dialog({
                    title: '修改',
                    message: flStr,
                    size: 'large',//有效值为'large'和'small'
                    buttons: {
                        ok: {
                            label: "<i class='glyphicon glyphicon-plus-sign'>修改",
                            className: 'btn-info',
                            callback: function(data){//添加回调函数
                                var fl = {};
                                fl.id = $("#id").val();
                                fl.pid = $("#pid").val();
                                fl.name = $("#name").val();
                                $.ajax({
                                    url:"/fl/updateFl.jhtml",
                                    data:fl,
                                    success:function(ref){
                                        if(ref.code == 200){
                                            getTooltip("修改成功","",2500)
                                            node.name = fl.name;
                                            zTreeObj.updateNode(node);
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
                $("#id").val(node.id);
                $("#name").val(node.name);
                var ppid = "";
                var pname = "没有上级";
                if(!!node.getParentNode()){
                    ppid = (node.getParentNode()).id;
                    pname = (node.getParentNode()).name;
                }
                $("#pid").val(ppid);
                $("#pname").val(pname);
            }else{
                getTooltip("根节点不可操作");
            }
        }else{
            getTooltip("至少选中一个");
        }
    }
    function getDeleteZtree(){//通过id删除当前节点 并删除该节点下所有节点
        var node = getNodes();
        if(!!node){
            if(node.id != 0){
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
                        if(result){//getTooltip("删除成功","",2500);
                            var treeObj = $.fn.zTree.getZTreeObj("tree");
                            var nodes = treeObj.transformToArray(node);
                            var str = [];
                            for(var i=0;i<nodes.length;i++){
                                str.push(nodes[i].id);
                            }
                            $.ajax({
                                url:"/fl/deleteFlAll.jhtml",
                                data:{"str":str.toString()},
                                success:function(ref){
                                    if(ref.code == 200){
                                        getTooltip("删除成功","",2500)
                                        zTreeObj.removeNode(node);
                                    }
                                }
                            })
                        }
                    }
                })
            }else{
                getTooltip("根节点不可操作");
            }
        }else{
            getTooltip("至少选中一个");
        }
    }

    function getNodes(){
        var nodeArr = zTreeObj.getSelectedNodes();
        return nodeArr[0];
    }

</script>
</body>
</html>
