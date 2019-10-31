<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 2019/8/24
  Time: 23:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <title>品牌管理</title>
    <jsp:include page="/topNav/head.jsp"></jsp:include>
</head>
<body>
<%-- 导航条 --%>
<jsp:include page="/topNav/nav.jsp"></jsp:include>

<div class="container">
    <div class="row"><div class="col-md-12">
        <div class="panel panel-success">
            <div class="panel-heading">品牌查询</div>
            <div class="panel-body">
                <form class="form-horizontal" id="brandQueId">
                    <div class="form-group">
                        <label class="col-sm-2 control-label">自定义排序</label>
                        <div class="col-sm-4">
                            <input id="sortp" name="sortp" type="text" class="form-control" placeholder="请输入">
                        </div>
                    </div>
                    <div class="form-group" align="center">
                        <button type="button" class="btn btn-info btn-lg" onclick="getBrandInfo()"><i class="glyphicon glyphicon-search"></i>查询</button>
                        <button type="reset" class="btn btn-default btn-lg"><i class="glyphicon glyphicon-repeat"></i>重置</button>
                    </div>
                </form>
            </div>
        </div>
    </div></div>
<img src="https://liu-7299.oss-cn-beijing.aliyuncs.com/gen/img/ed9185c8-784f-4e0f-ae68-8e2c2a69e0cb.bmp" height="60px">
    <div class="row"><div class="col-md-12">
        <div class="panel panel-info">
        <div class="panel-heading">品牌列表</div>
        <div class="panel-body">
            <div style="float: right;">
                <button onclick="getAddBrand()" type="button" class="btn btn-primary"><i class="glyphicon glyphicon-plus"></i>添加品牌</button>
                <button onclick="closeHotBrandCache()" type="button" class="btn btn-default"><i class="glyphicon glyphicon-unchecked"></i>清理缓存</button>
            </div>
            <table id="brandList" class="table table-striped table-bordered" style="width:100%">
                <thead>
                <tr>
                    <th>全选<input type="checkbox"></th>
                    <th>品牌名</th>
                    <th>品牌图片</th>
                    <th>热销品牌</th>
                    <th>排序</th>
                    <th>操作</th>
                </tr>
                </thead>
                <tbody id="brandTbody" style="text-align: center"></tbody>
            </table>
        </div>
    </div>
    </div></div>
</div>

<div id="brandStr" style="display: none">
    <form id="sdss" class="form-horizontal"><input type="hidden" id="id" name="id">
        <div class="form-group">
            <label class="col-sm-2 control-label">品牌名</label>
            <div class="col-sm-4">
                <input type="text" class="form-control" id="name" name="name" placeholder="请输入品牌名">
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-2 control-label">品牌图片</label>
            <div class="col-sm-4">
                <input name="file" type="file" class="form-control myfile">
                <input type="hidden" class="form-control" id="imgPath" name="imgPath" placeholder="品牌图片">
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-2 control-label">是否热销</label>
            <div class="col-sm-4">
                <input name="sell" type="radio" value="1">是 <input name="sell" type="radio" value="0">否
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-2 control-label">排序</label>
            <div class="col-sm-4">
                <input type="text" class="form-control" id="sort" name="sort" placeholder="请输入排序">
            </div>
        </div>
    </form>
</div>

<jsp:include page="/topNav/script.jsp"></jsp:include>
<script>
    var brandStr = "";
    var brandparpm = {};

    $(function(){
        brandStr = $("#brandStr").html();
        $("#brandStr").html("");
        getBrandList();
    })

    //清理缓存
    function closeHotBrandCache(){
        $.post({
            url:"/cache/closeHotBrandCache.jhtml",
            success:function(ref){
                if(ref.code == 200){
                    getTooltip("清理成功");
                }
            }
        })
    }

    function getBrandInfo(){
        brandparpm.sortp = $("#sortp").val();
        getBrandList(brandparpm);
    }

    //品牌列表
    function getBrandList(brandPar){
        $("#brandList").DataTable({
            "searching": false,//是否允许检索
            "iDisplayLength": 5,//每页默认显示几行
            "lengthMenu": [1,5,20,50],//左上角下拉框的值
            "processing": true,
            "serverSide": true,
            "destroy":true,//清理缓存
            "ajax": {
                "url": "/brand/queryBrandPage.jhtml",
                "data":brandPar,
            },
            "columns": [
                { "data": function(data){
                        return '<input name="userCheck" type="checkbox" value="'+data.id+'">';
                    } },
                { "data": "name" },
                {"data":function(data){//http://192.168.217.128/img/
                        return '<img src="'+data.imgPath+'" alt="图片丢失" height="60px" ">';
                    }//
                },
                {
                    "data": "sell",render:function (sell,x,data,y){
                        return sell==1?"热销<i class='glyphicon glyphicon-fire'></i>":"非热销";
                    }
                },
                {
                    "data": "sort", render:function (sort,x,data,y) {
                                    return '<input type="text" style="width: 75px" class="form-control" value="'+sort+'" id="sort_'+data.id+'">'+
                                            ' <button class="btn btn-default btn-sm" onclick="getUpdateBrandSort('+data.id+')"><i class="glyphicon glyphicon-refresh"></i>刷新</button>';
                                }
                },
                { "data": function(data){
                        return '<button class="btn btn-warning btn-sm" onclick="getUpdateBrand('+data.id+')"><i class="glyphicon glyphicon-pencil"></i>修改</button> '+
                            '<button class="btn btn-danger btn-sm" onclick="getDeleteBrand('+data.id+')"><i class="glyphicon glyphicon-remove"></i>删除</button> '+
                            '<button class="btn btn-'+(data.sell==1?"info":"success")+' btn-sm" onclick="getUpdateBrandSell(\''+data.id+'\',\''+data.sell+'\')"><i class="glyphicon glyphicon-thumbs-'+(data.sell==1?"down":"up")+'"></i>'+(data.sell==1?"非热销":"热销中")+'</button> ';
                    } },
            ],
            "language": {
                "url": "/js/Chinese.json"
            }
        });
    }

    function getUpdateBrandSort(id){
        $.post({
            url:"/brand/updateBrandSort.jhtml",
            data:{id:id,sort:$("#sort_"+id).val()},
            success:function(ref){
                if(ref.code == 200){
                    getTooltip("修改成功","",2500);
                    getBrandList();
                }
            }
        })
    }

    function getUpdateBrandSell(id,sell){
        if(sell == 0){
            sell = 1;
        }else{
            sell = 0;
        }
        $.post({
            url:"/brand/updateBrandSell.jhtml",
            data:{id:id,sell:sell},
            success:function(ref){
                if(ref.code == 200){
                    getTooltip("修改成功","",2500);
                    getBrandList();
                }
            }
        })
    }

    function getAddBrand(){
        bootbox.dialog({
            title: '添加品牌',
            message: brandStr,
            size: 'large',//有效值为'large'和'small'
            buttons: {
                ok: {
                    label: "<i class='glyphicon glyphicon-plus-sign'>添加",
                    className: 'btn-info',
                    callback: function(data){//添加回调函数 $("#sdss").serialize()
                        var brand = {};
                        brand.name = $("#name").val();
                        brand.imgPath = $("#imgPath").val();
                        brand.sell = $("input[name='sell']").val();
                        brand.sort = $("#sort").val();
                        $.ajax({
                            url:"/brand/addBrand.jhtml",
                            data:brand,//$("#sdss",add_boot).serialize() 序列化参数 需要有name
                            type:"post",
                            dataType:"json",
                            success:function(ref){
                                if(ref.code == 200){
                                    getTooltip("添加成功","",2500);
                                    getBrandList();
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
        getImageUpload();
        //$("#imgPathh").remove();
    }

    function getUpdateBrand(id){
        $.ajax({
            url:"/brand/queryBrandById.jhtml",
            data:{id:id},
            type:"post",
            success:function(ref){
                bootbox.dialog({
                    title: '修改品牌',
                    message: brandStr,
                    size: 'large',//有效值为'large'和'small'
                    buttons: {
                        ok: {
                            label: "<i class='glyphicon glyphicon-plus-sign'>修改",
                            className: 'btn-info',
                            callback: function(data){//添加回调函数
                                var brand = {};
                                brand.id = $("#id").val();
                                brand.name = $("#name").val();
                                brand.imgPath = $("#imgPath").val();
                                brand.sell = $("input[name='sell']").val();
                                brand.sort = $("#sort").val();
                                brand.oldImgPath = ref.data.imgPath;
                                $.ajax({
                                    url:"/brand/updateBrand.jhtml",
                                    data:brand,//$("#sdss",add_boot).serialize() 序列化参数 需要有name
                                    type:"post",
                                    success:function(ref){
                                        if(ref.code == 200){
                                            getTooltip("修改成功","",2500);
                                            getBrandList();
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
                $("input[name='sort']").val(ref.data.sort);
                $("input[name='imgPath']").val(ref.data.imgPath);
                //$("#imgPathh").attr("src","http://192.168.217.128/img/"+ref.data.imgPath);
                getImageUpload();
                $("input[name='id']").val(ref.data.id);
                $("input[name='name']").val(ref.data.name);
                $("input[name='sell']").each(function(){
                    if($(this).val() == ref.data.sell){
                        $(this).prop("checked",true);
                    }
                })
            }
        })
    }

    function getDeleteBrand(id){
        $.ajax({
            url:"/brand/deleteBrand.jhtml",
            data:{id:id},
            type:"post",
            success:function(ref){
                if(ref.code == 200){
                    getTooltip("删除成功","",2500);
                    getBrandList();
                }
            }
        })
    }
    //图片上传
    function getImageUpload(){//img/imgUpload.jhtml
        // http://192.168.217.128/img/"https://liu-7299.oss-cn-beijing.aliyuncs.com/"+
        var url = $("[name='imgPath']").val();
        var urlStr = "<img src='"+url+"' height='95%'>";
        var urlArr = [];
        urlArr.push(urlStr);
        $(".myfile").fileinput({
            //上传的地址
            language : 'zh',//中文
            uploadUrl:"/img/imageUpload.jhtml",
            showUpload : false, //是否显示上传按钮，默认显示
            showRemove : false, //是否显示移除按钮，默认显示
            showCancel : false, //是否显示取消按钮，默认显示
            dropZoneEnabled : false,//是否显示拖拽区域，默认显示，占用较大区域
            //uploadAsync : true, //默认异步上传
            //showCaption : true,//是否显示文本框,默认显示
            //showPreview : true, //是否显示预览,默认显示
            //minImageWidth: 50, //图片的最小宽度
            //minImageHeight: 50,//图片的最小高度
            //maxImageWidth: 1000,//图片的最大宽度
            //maxImageHeight: 1000,//图片的最大高度
            //maxFileSize: 0,//单位为kb，如果为0表示不限制文件大小
            //minFileCount: 0,
            //maxFileCount : 1, //表示允许同时上传的最大文件个数
            //enctype : 'multipart/form-data',
            //validateInitialCount : true,
            //msgFilesTooMany : "选择上传的文件数量({n}) 超过允许的最大数值{m}！",
            //allowedFileTypes : [ 'image' ],//配置允许文件上传的类型
            //allowedPreviewTypes : [ 'image' ],//配置所有的被预览文件类型
            //allowedPreviewMimeTypes : [ 'jpg', 'png', 'gif' ],//控制被预览的所有mime类型
            //initialPreviewAsData:false,
            initialPreview:[urlArr],//初始图片
        })
        //异步上传返回结果处理
        $(".myfile").on("fileuploaded", function(event, data, previewId, index) {//event, data, previewId, index
            $("input[name='imgPath']").val(data.response.data);
        });
        //上传前
        $('.myfile').on('filepreupload', function(event, data, previewId, index) {
            console.log("filepreupload");
        });
    }

</script>

</body>
</html>
