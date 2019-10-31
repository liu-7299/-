<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 2019/9/8
  Time: 19:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <jsp:include page="/topNav/head.jsp"></jsp:include>
    <title>日志列表</title>
</head>
<body>
<%-- 导航条 --%>
<jsp:include page="/topNav/nav.jsp"></jsp:include>
<style type="text/css">.table {table-layout:fixed;}</style>
<style type="text/css">.table {table-layout:fixed;word-break:break-all;}</style>
    <div class="container">
    <%-- 条件查询 --%>
    <div class="row"><div class="col-md-12">
        <div class="panel panel-success">
            <div class="panel-heading">日志查询</div>
            <div class="panel-body">
                <form class="form-horizontal">
                    <div class="form-group">
                        <label class="col-sm-2 control-label">用户名</label>
                        <div class="col-sm-4">
                            <input id="userNameM" type="text" class="form-control" placeholder="请输入用户名">
                        </div>
                        <label class="col-sm-2 control-label">真实姓名</label>
                        <div class="col-sm-4">
                            <input id="realNameM" type="text" class="form-control" placeholder="请输入真实姓名">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">时间</label>
                        <div class="col-sm-4">
                            <div class="input-group">
                                <input id="createTimeMin" type="text" maxlength="10" class="form_date form-control" placeholder="开始时间" aria-describedby="basic-addon1">
                                <span class="input-group-addon"><i class="glyphicon glyphicon-calendar"></i></span><%--  form-control onchange="getTimeChange(this.value,'','#entryTimeMax')" --%>
                                <input id="createTimeMax" type="text" maxlength="10" class="form_date form-control" placeholder="结束时间" aria-describedby="basic-addon1">
                            </div>
                        </div>
                        <div>
                            <label class="col-sm-2 control-label">状态</label>
                            <div class="col-sm-4">
                                <select id="status" class="form-control" title="请选择状态"><%-- form-control --%>
                                    <option value="-1">==请选择==</option>
                                    <option value="1">成功</option>
                                    <option value="0">失败</option>
                                </select>
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">日志信息</label>
                        <div class="col-sm-4">
                            <input id="info" type="text" class="form-control" placeholder="请输入日志相关信息">
                        </div>
                    </div>
                    <div class="form-group" align="center">
                        <button type="button" class="btn btn-info btn-lg" onclick="getLogInfo()"><i class="glyphicon glyphicon-search"></i>查询</button>
                        <button type="reset" class="btn btn-default btn-lg"><i class="glyphicon glyphicon-repeat"></i>重置</button>
                    </div>
                </form>
            </div>
        </div>
    </div></div>
    <%-- 列表展示 --%>
    <div class="row"><div class="col-md-12">
        <div class="panel panel-success">
            <div class="panel-heading">日志列表</div>
            <div class="panel-body">
                <table id="logList" class="table table-striped table-bordered" style="width:100%">
                    <thead>
                    <tr>
                        <th>ID</th>
                        <th>用户名</th>
                        <th>真实姓名</th>
                        <th>操作信息</th>
                        <th>日志信息</th>
                        <th>状态</th>
                        <th>错误信息</th>
                        <th>时间</th>
                        <th>详细信息</th>
                    </tr>
                    </thead>
                    <tbody style="text-align: center"></tbody>
                </table>
            </div>
        </div>
    </div></div>
</div>

<jsp:include page="/topNav/script.jsp"></jsp:include>
<script>
    $(function(){
        getTimeChina("#createTimeMin");
        getTimeChina("#createTimeMax");
        getLogList();
    })
    //条件查询
    function getLogInfo(){
        var logInfo = {};
        logInfo.userName = $("#userNameM").val();
        logInfo.realName = $("#realNameM").val();
        logInfo.createTimeMin = $("#createTimeMin").val();
        logInfo.createTimeMax = $("#createTimeMax").val();
        logInfo.status = $("#status").val();
        logInfo.info = $("#info").val();
        console.log(logInfo);
        getLogList(logInfo);
    }
    //日志列表
    function getLogList(Log){
        $("#logList").DataTable({
            "searching": false,//是否允许检索
            "iDisplayLength": 5,//每页默认显示几行
            "lengthMenu": [1,5,20,50],//左上角下拉框的值
            "processing": true,
            "serverSide": true,
            "destroy":true,//清理缓存
            //"sortable" : true,//是否启用排序
            "ordering":false,
            "ajax": {
                "url": "/log/questLogByPage.jhtml",
                "data":Log,
            },
            "columns": [
                { "data": "id"},
                { "data": "userName" },
                { "data": "realName" },
                { "data": "content" },
                { "data": function(data){
                        var str =  data.info;
                        if(str != null){
                            if(str.length > 30){
                                var newStr = str.substring(0,30);
                                return newStr+"<input type='hidden' value='"+str+"' id='info_hi_"+data.id+"'>"+
                                    "<br><a id='info_a_"+data.id+"' onclick='hiddenChar(\"info_hi_"+data.id+"\",\"info_a_"+data.id+"\")'>更多</a>";
                            }else{
                                return str;
                            }
                        }
                        return "无";
                    }  },
                { "data": "status",render:function (a) {
                        return (a==1?'成功':'失败');
                    }
                },
                { "data": function(data){
                            var str =  data.errorMsg;
                            if(str != null){
                                if(str.length > 30){
                                    var newStr = str.substring(0,30);
                                    return newStr+"<input type='hidden' value='"+str+"' id='error_hi_"+data.id+"'>"+
                                        "<br><a id='error_a_"+data.id+"' onclick='hiddenChar(\"error_hi_"+data.id+"\",\"error_a_"+data.id+"\")'>更多</a>";
                                }else{
                                    return str;
                                }
                            }
                            return "无";
                    } },
                { "data": "createTime" },
                { "data": function(data){//"detail"
                        var str =  data.detail;
                        if(str != null){
                            if(str.length > 30){
                                var newStr = str.substring(0,30);
                                return newStr+"<input type='hidden' value='"+str+"' id='deta_hi_"+data.id+"'>"+
                                    "<br><a id='deta_a_"+data.id+"' onclick='hiddenChar(\"deta_hi_"+data.id+"\",\"deta_a_"+data.id+"\")'>更多</a>";
                            }else{
                                return str;
                            }
                        }
                        return "无";
                    } }
            ],
            "language": {
                "url": "/js/Chinese.json"
            }
        });
    }

    function getTimeChina(timeName){
        $(timeName).datetimepicker({
            format: 'YYYY-MM-DD HH:mm',
            locale: moment.locale('zh-CN'),
            showClear: true,// initialDate:"",
            minDate: new Date("1970-01-01".replace(/-/,"/")),//new Date("2019-08-01".replace(/-/,"/"))
            maxDate: new Date("2999-12-12".replace(/-/,"/")),
        }).on("focus",function(e){

        }).on("blur", function (){
        })
    }

    function hiddenChar(id,nid){
        var node = $("#"+id).parent();
        var newText = $("#"+id).val();
        var text = $(node).text();
        var str = '<input type="hidden" id="'+id+'" value="'+text.substring(0,text.length-2)+'">';
        if($("#"+nid).text() == "更多"){
            node.html(newText+str+"<br><a id='"+nid+"' onclick='hiddenChar(\""+id+"\",\""+nid+"\")'>收起</a>");
        }else{
            node.html(newText+str+"<br><a id='"+nid+"' onclick='hiddenChar(\""+id+"\",\""+nid+"\")'>更多</a>");
        }

    }
</script>
</body>
</html>
