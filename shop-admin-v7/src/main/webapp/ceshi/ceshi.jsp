<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 2019/8/19
  Time: 13:02
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <title>Title</title>
    <link href="/js/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <style>
        .show-grid [class ^="col-"] {
            padding-top: 10px;
            padding-bottom: 10px;
            background-color: #eee;
            border: 1px solid #ddd;
            background-color: rgba(86, 61, 124, .15);
            border: 1px solid rgba(86, 61, 124, .2);
        }
    </style>
</head>
<body class="show-grid">
    <h1>你好</h1>
    <div class="container-fluid">
        <div class="row">
            <div class="col-md-12">第一行</div>
        </div>
        <div class="row" style="height: 180px">
            <div class="col-md-1" style="height: 100%">
                <div class="row" style="height: 25%"><div class="col-md-12" style="height: 100%">左1</div></div>
                <div class="row" style="height: 75%"><div class="col-md-12" style="height: 100%">左2</div></div>
            </div>
            <div class="col-md-1" style="height: 100%">
                <div class="row" style="height: 25%"><div class="col-md-12" style="height: 100%">左3</div></div>
                <div class="row" style="height: 75%"><div class="col-md-12" style="height: 100%">左4</div></div>
            </div>
            <div class="col-md-8" style="height: 100%">
                <div class="row" style="height: 50%"><div class="col-md-12" style="height: 100%">第二行中上</div></div>
                <div class="row" style="height: 50%"><div class="col-md-12" style="height: 100%">第二行中下</div></div>
            </div>
            <div class="col-md-1" style="height: 100%">
                <div class="row" style="height: 25%"><div class="col-md-12" style="height: 100%">右1</div></div>
                <div class="row" style="height: 75%"><div class="col-md-12" style="height: 100%">右2</div></div>
            </div>
            <div class="col-md-1" style="height: 100%">
                <div class="row" style="height: 25%"><div class="col-md-12" style="height: 100%">右3<button value="456" /></div></div>
                <div class="row" style="height: 75%"><div class="col-md-12" style="height: 100%">右4<liuxy onclick="aaa()" class="glyphicon glyphicon-transfer"></liuxy></div></div>
            </div>
        </div>
        <div class="row">
            <div class="col-md-4">
                <div class="row"><div class="col-md-12">上1</div></div>
                <div class="row"><div class="col-md-12">下1<span></span></div></div>
            </div>
            <div class="col-md-2">
                <div class="row"><div class="col-md-12">上2</div></div>
                <div class="row"><div class="col-md-12">下2</div></div>
            </div>
            <div class="col-md-2">
                <div class="row"><div class="col-md-12">上3</div></div>
                <div class="row"><div class="col-md-12">下3</div></div>
            </div>
            <div class="col-md-4">
                <div class="row"><div class="col-md-12">上4</div></div>
                <div class="row"><div class="col-md-12">下4</div></div>
            </div>
        </div>
    </div>
    <br onclick="aaa()">
<script src="/js/jquery-3.3.1.js"></script>
<script src="/js/bootstrap/js/bootstrap.min.js"></script>
<script>
    function aaa(){
        alert(0);
    }

</script>
</body>
</html>
