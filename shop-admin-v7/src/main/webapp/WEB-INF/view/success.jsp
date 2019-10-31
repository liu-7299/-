<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 2019/8/15
  Time: 0:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <form action="/user/addUser.jhtml" method="post">
        姓名：<input name="realName"><br>
        用户：<input name="userName"><br>
        密码：<input name="password"><br>
        性别：<input name="sex" type="radio" value="1">男<input name="sex" type="radio" value="0">女<br>
        年龄：<input name="age"><br>
        手机：<input name="phone"><br>
        邮箱：<input name="email"><br>
        <input type="submit">
    </form>
</head>
<body>
    <h1>成功</h1>
</body>
</html>
