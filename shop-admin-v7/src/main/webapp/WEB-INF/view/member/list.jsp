<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 2019/10/22
  Time: 0:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <jsp:include page="/topNav/head.jsp"></jsp:include>
    <title>会员管理</title>
</head>
<body>
<%-- 导航条 --%>
<jsp:include page="/topNav/nav.jsp"></jsp:include>

<div class="container">
    <div class="row"><div class="col-md-12">
        <div class="panel panel-success">
            <div class="panel-heading">会员查询</div>
            <div class="panel-body">
                <form class="form-horizontal" id="brandQueId">
                    <div class="form-group">
                        <label class="col-sm-2 control-label">会员名</label>
                        <div class="col-sm-4">
                            <input id="userName" type="text" class="form-control" placeholder="请输入会员名">
                        </div>
                        <label class="col-sm-2 control-label">手机号</label>
                        <div class="col-sm-4">
                            <input id="phone" type="text" class="form-control" placeholder="请输入手机号">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">会员生日</label>
                        <div class="col-sm-4">
                            <div class="input-group">
                                <input id="birthdayMin" type="text" maxlength="10" class="form_date form-control" placeholder="开始时间" aria-describedby="basic-addon1">
                                <span class="input-group-addon"><i class="glyphicon glyphicon-calendar"></i></span><%--  form-control onchange="getTimeChange(this.value,'','#entryTimeMax')" --%>
                                <input id="birthdayMax" type="text" maxlength="10" class="form_date form-control" placeholder="结束时间" aria-describedby="basic-addon1">
                            </div>
                        </div>
                        <label class="col-sm-2 control-label">地区</label>
                        <div class="col-sm-4" id="region_pdiv">
                        </div>
                    </div>
                    <div class="form-group" align="center">
                        <button type="button" class="btn btn-info btn-lg" onclick="getMemberInfo()"><i class="glyphicon glyphicon-search"></i>查询</button>
                        <button type="reset" class="btn btn-default btn-lg"><i class="glyphicon glyphicon-repeat"></i>重置</button>
                    </div>
                </form>
            </div>
        </div>
    </div></div>

    <div class="row"><div class="col-md-12">
        <div class="panel panel-info">
            <div class="panel-heading">会员列表</div>
            <div class="panel-body">
                <table id="memberList" class="table table-striped table-bordered" style="width:100%">
                    <thead>
                    <tr>
                        <th>全选<input type="checkbox"></th>
                        <th>会员名</th>
                        <th>会员真实名</th>
                        <th>手机号</th>
                        <th>邮箱</th>
                        <th>生日</th>
                        <th>地区</th>
                    </tr>
                    </thead>
                    <tbody id="memberTbody" style="text-align: center"></tbody>
                </table>
            </div>
        </div>
    </div></div>
</div>

<jsp:include page="/topNav/script.jsp"></jsp:include>
<script>

    $(function(){
        getTimeChina("#birthdayMin");
        getTimeChina("#birthdayMax");
        concatRegionSel("region_pdiv","<input value='0'>");//加载第一个地区选择下拉框
        getMenmberList();
    })

    function getMemberInfo(){
        var member = {};
        member.userName = $("#userName").val();
        member.phone = $("#phone").val();
        member.birthdayMin = $("#birthdayMin").val();
        member.birthdayMax = $("#birthdayMax").val();//region_sel
        member.region1 = $("#region_pdiv").find("select:eq(0)").val();
        member.region2 = $("#region_pdiv").find("select:eq(1)").val();
        member.region3 = $("#region_pdiv").find("select:eq(2)").val();
        memberTable.settings()[0].ajax.data = member;
        memberTable.ajax.reload();
    }

    var memberTable;
    function getMenmberList(){
        memberTable = $("#memberList").DataTable({
            "searching": false,//是否允许检索
            "iDisplayLength": 5,//每页默认显示几行
            "lengthMenu": [1,5,20,50],//左上角下拉框的值
            "processing": true,
            "serverSide": true,
            "destroy":true,//清理缓存
            //"sortable" : true,//是否启用排序
            "ordering":false,
            "ajax": {
                "url": "/member/queryMemberByPage.jhtml",
                "dataSrc": function (json) {
                    //这个json 就是一个变量名
                    console.log(json.data);
                    json.draw = json.data.draw;
                    json.recordsTotal = json.data.recordsTotal;
                    json.recordsFiltered = json.data.recordsFiltered;
                    return json.data.data;
                },
            },
            "columns": [
                { "data": function(data){
                        var str = data.id;
                        if(!str){
                            alert(0)
                        }
                        return '<input name="userCheck" type="checkbox" value="'+str+'">';
                    } },
                { "data": "userName" },
                { "data": "realName" },
                { "data": "phone" },
                { "data": "email" },
                {"data" : "birthday"},
                {"data" : "regionName"}
            ],
            "language": {
                "url": "/js/Chinese.json"
            }
        });
    }

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
                            '<select style="width: 110px" name="region_sel" class="form-control col-sm-3" onchange="concatRegionSel(\''+id+'\',this)">'+
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

    function getTimeChina(timeName){
        $(timeName).datetimepicker({
            format: 'YYYY-MM-DD',
            locale: moment.locale('zh-CN'),
            showClear: true,
        })
    }

</script>
</body>
</html>
