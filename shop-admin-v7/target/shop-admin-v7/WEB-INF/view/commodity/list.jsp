<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 2019/8/24
  Time: 23:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <jsp:include page="/topNav/head.jsp"></jsp:include>
    <title>商品管理</title>
</head>
<body>
<%-- 导航条 --%>
<jsp:include page="/topNav/nav.jsp"></jsp:include>
    <%-- 组成 --%>
    <div class="container">
    <%-- 条件查询 --%>
    <div class="row"><div class="col-md-12">
        <div class="panel panel-success">
            <div class="panel-heading">商品查询</div>
            <div class="panel-body">
                <form class="form-horizontal" id="commodityQueId" method="post" action="/commodity/excelEduceComm.jhtml">
                    <div class="form-group">
                        <label class="col-sm-2 control-label">商品名</label>
                        <div class="col-sm-4">
                            <input id="nameP" name="name" type="text" class="form-control" placeholder="请输入商品名">
                        </div>
                        <label class="col-sm-2 control-label">价格范围</label>
                        <div class="col-sm-4">
                            <div class="input-group">
                                <input id="priceMin" name="priceMin" type="number" class="form-control" placeholder="最低价格" aria-describedby="basic-addon1">
                                <span class="input-group-addon"><i class="glyphicon glyphicon-resize-horizontal"></i></span>
                                <input id="priceMax" name="priceMax" type="number" class="form-control" placeholder="最高价格" aria-describedby="basic-addon1">
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">生产时间</label>
                        <div class="col-sm-4">
                            <div class="input-group">
                                <input id="createTimeMi" name="createTimeMin" type="text" maxlength="10" class="form_date form-control" placeholder="起始时间" aria-describedby="basic-addon1">
                                <span class="input-group-addon"><i class="glyphicon glyphicon-calendar"></i></span><%--  form-control onchange="getTimeChange(this.value,'','#entryTimeMax')" --%>
                                <input id="createTimeMa" name="createTimeMax" type="text" maxlength="10" class="form_date form-control" placeholder="截止时间" aria-describedby="basic-addon1">
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">分类</label>
                        <div class="col-sm-10" id="fl_pdiv">
                        </div>
                    </div>
                    <div class="form-group" align="center">
                        <button type="button" class="btn btn-info btn-lg" onclick="getCommInfo()"><i class="glyphicon glyphicon-search"></i>查询</button>
                        <button type="reset" class="btn btn-default btn-lg"><i class="glyphicon glyphicon-repeat"></i>重置</button>
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
                <button onclick="getAddCommodity()" type="button" class="btn btn-primary"><i class="glyphicon glyphicon-plus"></i>添加商品</button>
                <button onclick="excelEduce()" type="button" class="btn btn-info"><i class="glyphicon glyphicon-arrow-down"></i>excel导出o</button>
                <button onclick="wordEduce()" type="button" class="btn btn-info"><i class="glyphicon glyphicon-arrow-down"></i>word导出o</button>
                <button onclick="pdfEduce()" type="button" class="btn btn-info"><i class="glyphicon glyphicon-arrow-down"></i>pdf导出o</button>
                <button onclick="closeShowCommodity()" type="button" class="btn btn-warning"><i class="glyphicon glyphicon-unchecked"></i>清理缓存</button>
                <button onclick="excelEduceComm()" type="button" class="btn btn-info"><i class="glyphicon glyphicon-arrow-down"></i>excel导出</button>
            </div>
            <div class="panel-heading">商品列表</div>
            <div class="panel-body">
                <table id="commodityList" class="table table-striped table-bordered" style="width:100%">
                    <thead>
                    <tr>
                        <th>全选<input type="checkbox"></th>
                        <th>商品名</th>
                        <th>商品价格</th>
                        <th>商品图片</th>
                        <th>生产日期</th>
                        <th>品牌</th>
                        <th>库存</th>
                        <th>分类</th>
                        <th>状态</th>
                        <th>操作</th>
                    </tr>
                    </thead>
                    <tbody id="commodityTbody" style="text-align: center"></tbody>
                </table>
            </div>
        </div>
    </div></div>
    </div>
    <%-- 隐藏 --%>
    <div id="commForm" style="display: none">
            <form id="sdssComm" class="form-horizontal">
                <input type="hidden" id="id">
                <div class="form-group">
                    <label class="col-sm-2 control-label">商品名</label>
                    <div class="col-sm-4">
                        <input type="text" class="form-control" id="name" placeholder="请输入商品名">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label">商品价格</label>
                    <div class="col-sm-4">
                        <input type="text" class="form-control" id="price" placeholder="请输入商品价格">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label">商品图片</label>
                    <div class="col-sm-4">
                        <input name="file" type="file" class="form-control myfile">
                        <input type="hidden" class="form-control" id="imgPath" placeholder="商品图片">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label">生产日期</label>
                    <div class="col-sm-4">
                        <input type="text" maxlength="10" class="form_date form-control" id="createTime" placeholder="请选择生产日期">
                    </div>
                </div><%-- stock status --%>
                <div class="form-group">
                    <label class="col-sm-2 control-label">库存</label>
                    <div class="col-sm-4">
                        <input type="text" class="form_date form-control" id="stock" placeholder="请输入库存">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label">状态</label>
                    <div class="col-sm-4">
                        <input type="radio" name="status" value="1">上架
                        <input type="radio" name="status" value="2">下架
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label">品牌</label>
                    <div class="col-sm-4">
                        <select id="brandId" class="form-control" title="请选择品牌">
                        </select>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label">分类</label>
                    <div class="col-sm-10">
                        <div id="fl_div"></div>
                        <%--<span id="fly_name"></span>--%>
                        <input id="fly_name" style="width: 200px" class="form-control col-sm-3" disabled>
                        <input id="fly_butt" type="button" class="btn btn-info" value="编辑" onclick="onBianji(this)">
                    </div>
                </div>
                <div style="display: none">
                    <input id="resetComm" type="reset">
                    <input id="submitComm" type="submit">
                </div>
            </form>
        </div>
    <jsp:include page="/topNav/script.jsp"></jsp:include>
    <script>
            var commForm;//表单初始值
            var commInfo = {};
            //页面加载函数
            $(function(){
                getTimeChina("#createTimeMi");
                getTimeChina("#createTimeMa");
                getCommodityList();//执行表格
                getBrandList();
                commForm = $("#commForm").html();//备份
                $("#commForm").html("");//清空值
                concatFlSel("fl_pdiv","<input value='1'>");
            })

            function concatFlSel(id,node){
                $.post({
                    url:"/fl/queryFlsById.jhtml",
                    data:{id:$(node).val()},
                    success:function(ref){
                        if(ref.code == 200){
                            var arr = ref.data;
                            $(node).nextAll().remove();//.parent()
                            if(arr.length > 0){
                                var str = ''+
                                    '<select style="width: 120px" name="fl_sel" class="form-control col-sm-3" onchange="concatFlSel(\''+id+'\',this)">'+
                                    '<option value="0">==请选择==</option>';
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
                    concatFlSel("fl_div","<input value='1'>");
                    $("#fly_name").attr("style","display: none");
                    $(node).val("取消编辑");
                }else{
                    $("#fly_name").attr("style","width: 200px");
                    $("#fl_div").html("");
                    $(node).val("编辑");
                }
            }

            //清理缓存
            function closeShowCommodity(){
                $.post({
                    url:"/cache/closeShowCommodity.jhtml",
                    success:function(ref){
                        if(ref.code == 200){
                            getTooltip("清理成功");
                        }
                    }
                })
            }

            //查询品牌列表
            function getBrandList(){
                $.ajax({
                    url:"/brand/queryBrandAll.jhtml",
                    dataType:"json",
                    type:"post",
                    async:false,
                    success:function(ref){
                        if(ref.code == 200){
                            var str = "";
                            var list = ref.data;
                            for(var i=0;i<list.length;i++){
                                str += '<option value="'+list[i].id+'">'+list[i].name+'</option>';
                            }
                            $("#brandId").html(str);
                        }
                    }
                })
            }

            /*
             * 条件查询
             * */
            function getCommInfo(){
                commInfo.name = $("#nameP").val();
                commInfo.priceMin = $("#priceMin").val();
                commInfo.priceMax = $("#priceMax").val();
                commInfo.createTimeMin = $("#createTimeMi").val();
                commInfo.createTimeMax = $("#createTimeMa").val();
                commInfo.cate1 = $("#fl_pdiv").find("select:eq(0)").val();
                commInfo.cate2 = $("#fl_pdiv").find("select:eq(1)").val();
                commInfo.cate3 = $("#fl_pdiv").find("select:eq(2)").val();
                //getCommodityList(commInfo);
                tableComm.settings()[0].ajax.data=commInfo;
                tableComm.ajax.reload();
            }
            /*
             * 请求列表
             * */
            var tableComm;
            function getCommodityList(){
                tableComm = $("#commodityList").DataTable({
                    "searching": false,//是否允许检索
                    "iDisplayLength": 5,//每页默认显示几行
                    "lengthMenu": [1,5,20,50],//左上角下拉框的值
                    "processing": true,
                    "serverSide": true,
                    "destroy":true,//清理缓存
                    "ajax": {
                        "url": "/commodity/queryCommodityPage.jhtml",
                    },
                    "columns": [
                        { "data": function(data){
                                return '<input name="userCheck" type="checkbox" value="'+data.id+'">';
                            } },
                        { "data": "name" },
                        { "data": "price" },
                        {"data":function(data){
                                return '<img src="http://192.168.217.128/img/'+data.imgPath+'" alt="图片丢失" height="60px" ">';
                            }},
                        { "data": "createTime" },
                        { "data": "brandName" },
                        { "data": "stock" },
                        { "data": "flName" },
                        { "data": function(data){
                                if(data.status == 1){
                                    return "上架";
                                }else if(data.status == 2){
                                    return "下架";
                                }else{
                                    return "异常";
                                }
                            } },
                        { "data": function(data){
                                return '<div class="btn-group" role="group" aria-label="...">'+
                                '<button class="btn btn-info btn-sm" onclick="getUpdateCommodity('+data.id+')"><i class="glyphicon glyphicon-pencil"></i>修改</button> '+
                                    '<button class="btn btn-danger btn-sm" onclick="getDeleteCommodity('+data.id+')"><i class="glyphicon glyphicon-remove"></i>删除</button> '+
                                        '<button class="btn btn-'+(data.status==1?"warning":"success")+' btn-sm" onclick="getUpdateCommodityStatus('+data.id+')"><i class="'+(data.status==1?"glyphicon glyphicon-arrow-down":"glyphicon glyphicon-arrow-up")+'"></i>'+(data.status==1?"下架":"上架")+'</button>'+
                                    '</div>';
                            } },
                    ],
                    "language": {
                        "url": "/js/Chinese.json"
                    }
                });
            }
            /*
             * 删除商品
             * */
            function getDeleteCommodity(id){
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
                                url:"/commodity/deleteCommodity.jhtml",
                                data:{id:id},
                                type:"post",
                                dataType:"json",
                                success:function(ref){
                                    if(ref.code == 200){
                                        getTooltip("删除成功","",2500);
                                        getCommInfo();
                                    }
                                },
                                error:function(ref){

                                }
                            })
                        }
                    }
                })
            }
            /*
             * 修改商品
             * */
            var upl_boot;
            function getUpdateCommodity(id){
                $.ajax({
                    url:"/commodity/queryCommodityById.jhtml",
                    data:{id:id},
                    type:"post",
                    dataType:"json",
                    success:function(ref){
                        if(ref.code == 200){
                            upl_boot = bootbox.dialog({
                                title: '修改商品',
                                message: commForm,
                                size: 'large',//有效值为'large'和'small'
                                buttons: {
                                    ok: {
                                        label: "<i class='glyphicon glyphicon-pencil'></i>修改",
                                        className: 'btn-info',
                                        callback: function(data){
                                            var comm = {};
                                            comm.id = $("#id").val();
                                            comm.name = $("#name").val();
                                            comm.price = $("#price").val();
                                            comm.imgPath = $("#imgPath").val();
                                            comm.createTime = $("#createTime").val();
                                            comm.stock = $("#stock").val();
                                            comm.status = $("input[name=status]").val();
                                            comm.brandId = $("#brandId").val();
                                            var cateSize = $("select[name='fl_sel']",upl_boot).length;
                                            var cate1 = $($("select[name='fl_sel']",upl_boot)[0]).val();
                                            var cate2 = $($("select[name='fl_sel']",upl_boot)[1]).val();
                                            var cate3 = $($("select[name='fl_sel']",upl_boot)[2]).val();
                                            var flNameStr = ref.data.flName;
                                            if(cateSize > 0 && cate2 != 0 && cate3 != 0) {
                                                comm.cate1 = cate1;
                                                comm.cate2 = cate2;
                                                comm.cate3 = cate3;
                                                var flName1 = $("#fl_div",add_boot).find("select:eq(0) option:selected").text();
                                                var flName2 = $("#fl_div",add_boot).find("select:eq(1) option:selected").text();
                                                var flName3 = $("#fl_div",add_boot).find("select:eq(2) option:selected").text();
                                                flNameStr = flName1 + " → " + flName2 + " → " + flName3;
                                            }
                                            comm.flName = flNameStr;
                                            $.post({
                                                url:"/commodity/updateCommodity.jhtml",
                                                data:comm,
                                                dataType:"json",
                                                success:function(res){
                                                    if(res.code == 200){
                                                        getTooltip("修改成功","",2500);
                                                        getCommInfo();
                                                    }
                                                }
                                            })
                                        }
                                    },
                                    noclose: {
                                        label: "<i class='glyphicon glyphicon-refresh'></i>重置",
                                        className: 'btn-warning',
                                        callback: function(){
                                            $("#resetComm",upl_boot).click();
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
                            $("#imgPath",upl_boot).val(ref.data.imgPath);
                            getImageUpload(ref.data.imgPath);
                            getTimeChina("#createTime");
                            $("#brandId").val(ref.data.brandId);
                            $("input[name=status]").each(function(){
                                if($(this).val() == ref.data.status) $(this).prop("checked",true);
                            })
                            $("#id",upl_boot).val(ref.data.id);
                            $("#name",upl_boot).val(ref.data.name);
                            $("#price",upl_boot).val(ref.data.price);
                            $("#createTime",upl_boot).val(ref.data.createTime);
                            $("#fly_name").val(ref.data.flName);
                        }
                    }
                })
            }
            /*
             * 添加商品
             * */
            var add_boot;
            function getAddCommodity(){
                add_boot = bootbox.dialog({
                    title: '添加商品',
                    message: commForm,
                    size: 'large',//有效值为'large'和'small'
                    buttons: {
                        ok: {
                            label: "<i class='glyphicon glyphicon-plus-sign'>添加",
                            className: 'btn-info',
                            callback: function(data){//添加回调函数
                                var comm = {};
                                comm.name = $("#name").val();
                                comm.price = $("#price").val();
                                comm.imgPath = $("#imgPath").val();
                                comm.createTime = $("#createTime").val();
                                comm.stock = $("#stock").val();
                                comm.status = $("input[name=status]").val();
                                comm.brandId = $("#brandId").val();
                                comm.cate1 = $($("select[name='fl_sel']",add_boot)[0]).val();
                                comm.cate2 = $($("select[name='fl_sel']",add_boot)[1]).val();
                                comm.cate3 = $($("select[name='fl_sel']",add_boot)[2]).val();
                                var cateSize = $("select[name='fl_sel']",upl_boot).length;
                                var cate1 = $($("select[name='fl_sel']",upl_boot)[0]).val();
                                var cate2 = $($("select[name='fl_sel']",upl_boot)[1]).val();
                                var cate3 = $($("select[name='fl_sel']",upl_boot)[2]).val();
                                var flNameStr = "";
                                if(cateSize > 0 && cate2 != 0 && cate3 != 0) {
                                    comm.cate1 = cate1;
                                    comm.cate2 = cate2;
                                    comm.cate3 = cate3;
                                    var flName1 = $("#fl_div",add_boot).find("select:eq(0) option:selected").text();
                                    var flName2 = $("#fl_div",add_boot).find("select:eq(1) option:selected").text();
                                    var flName3 = $("#fl_div",add_boot).find("select:eq(2) option:selected").text();
                                    flNameStr = flName1 + " → " + flName2 + " → " + flName3;
                                }
                                comm.flName = flNameStr;
                                $.ajax({
                                    url:"/commodity/addCommodity.jhtml",
                                    data:comm,//$("#sdss",add_boot).serialize() 序列化参数 需要有name
                                    type:"post",
                                    dataType:"json",
                                    success:function(ref){
                                        if(ref.code == 200){
                                            getTooltip("添加成功","",2500);
                                            getCommInfo();
                                        }
                                    }
                                })
                            }
                        },
                        noclose: {
                            label: "<i class='glyphicon glyphicon-refresh'></i>重置",
                            className: 'btn-warning',
                            callback: function(){//重置回调函数 return false 为不关闭框
                                $("#resetComm",add_boot).click();
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
                });
                getTimeChina("#createTime");
                getImageUpload();
                concatFlSel("fl_div","<input value='1'>");
                $("#fly_name").remove();
                $("#fly_butt").remove();//attr("style","display: none")
            }
           //图片上传
            function getImageUpload(SSTR){
                var url = "http://192.168.217.128/img/"+SSTR;
                var urlStr = "<img src='"+url+"' height='95%'>";
                var urlArr = [];
                if(!SSTR) urlStr = "";
                urlArr.push(urlStr);
                $(".myfile").fileinput({
                    //上传的地址
                    uploadUrl:"/img/imgUpload.jhtml",
                    language : 'zh', //中文
                    showUpload : false, //是否显示上传按钮，默认显示
                    showRemove : false, //是否显示移除按钮，默认显示
                    showCancel : false, //是否显示取消按钮，默认显示
                    dropZoneEnabled : false,//是否显示拖拽区域，默认显示，占用较大区域
                    initialPreview:[""],//初始图片
                })
                //异步上传返回结果处理
                $(".myfile").on("fileuploaded", function(event, data, previewId, index) {//event, data, previewId, index
                    $("#imgPath").val(data.response.data);
                });
                //上传前
                $('.myfile').on('filepreupload', function(event, data, previewId, index) {
                });
            }

            function getUpdateCommodityStatus(id){
                $.ajax({
                    url:"/commodity/updateCommodityStatus.jhtml",
                    data:{"id":id},
                    type:"post",
                    success:function(ref){
                        if(ref.code == 200){
                            getCommInfo();
                        }
                    }
                })
            }

            /*
             * 日期控件
             * */
            function getTimeChina(timeName){
                $(timeName).datetimepicker({
                    format: 'YYYY-MM-DD HH:mm:ss',
                    locale: moment.locale('zh-CN'),
                    showClear: true,// initialDate:"",
                })
            }
            function excelEduce(){
                //location.href = "/commodity/excelEduce.jhtml";
                alert($("#commodityQueId").serialize())
            }
            function excelEduceComm(){
                $("#commodityQueId").submit();
            }
            function wordEduce(){
                location.href = "/commodity/wordEduce.jhtml";
            }
            function  pdfEduce(){
                location.href = "/commodity/pdfEduce.jhtml";
            }
    </script>
</body>
</html>
