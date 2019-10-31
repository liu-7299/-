<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 2019/9/2
  Time: 19:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <jsp:include page="/topNav/head.jsp"></jsp:include>
    <title>首页</title>
</head>
<body background="/js/index.png">
    <%-- 导航条 --%>
    <jsp:include page="/topNav/nav.jsp"></jsp:include>
    <jsp:include page="/topNav/script.jsp"></jsp:include>
<div id="new" style="display: none">
    <form class="form-horizontal" role="form">
        <div class="form-group">
            <label class="col-sm-2 control-label">名字</label>
            <div class="col-sm-10">
                <div id="tt1" style="display: none">
                <select id="t1" onchange="test('tt1')" style="width: 300px" class="form-control col-sm-3" title="一级">
                    <option>1</option>
                    <option>2</option>
                    <option>3</option>
                </select>
                </div>
                <div id="tt2" style="display: none">
                <select id="t2" onchange="test('tt2')" style="width: 300px" class="form-control col-sm-3" title="二级">
                    <option>4</option>
                    <option>5</option>
                    <option>6</option>
                </select>
                </div>
                <div id="tt3" style="display: none">
                <select id="t3" style="width: 300px" class="form-control col-sm-3" title="三级">
                    <option>7</option>
                    <option>8</option>
                    <option>9</option>
                </select>
                </div>
            </div>
        </div>
    </form>
</div>

    <input onclick="test1(this)" type="button" value="编辑">

</body>
<script>

    $(function(){
        $("#tt1").attr("style","");
    })

    function test(id){
        if(id == "tt1"){
            $("#tt3").attr("style","display: none")
            $("#tt2").attr("style","")
        }
        if(id == "tt2"){
            $("#tt3").attr("style","")
        }
    }

    function test1(node){
        if($(node).val() == "编辑"){
            $("#new").attr("style","");
            $("#tt2").attr("style","display: none");
            $("#tt3").attr("style","display: none");
            $(node).val("取消编辑");
        }else{
            $(node).val("编辑");
            $("#new").attr("style","display: none");
        }
    }
</script>
</html>
