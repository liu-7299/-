<%@ page contentType="text/html;charset=UTF-8" language="java" %><!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <!--
    下面一行代码自适应各设备输出，即响应式H5编程
    -->
    <meta name="viewport" content="width=device-width,initial-scale=1">
    <!--
      下面
      dist表示bootstrap4.0组件
      dist3表示boostrap3.7组件
      jquery组件要用2.2.4，不要用3.3.1版本，并且要放在bootstrap.js前面
    -->
    <!--<link rel="stylesheet" href="../dist/css/bootstrap.css">-->
    <link rel="stylesheet" href="/js/bootstrap/css/bootstrap.min.css">
    <script src="/js/jquery-3.3.1.js"></script>
    <!--<script src="../dist/js/bootstrap.js"></script>-->
    <script src="/js/bootstrap/js/bootstrap.min.js"></script>
    <title>测试bootstrp 响应式页面布局,多级下拉菜单</title>
</head>
<style>
    .dropdown-submenu {
        position: relative;
    }

    .dropdown-submenu > .dropdown-menu {
        top: 0;
        left: 100%;
        margin-top: -6px;
        margin-left: -1px;
        -webkit-border-radius: 0 6px 6px 6px;
        -moz-border-radius: 0 6px 6px;
        border-radius: 0 6px 6px 6px;
    }

    .dropdown-submenu:hover > .dropdown-menu {
        display: block;
    }

    .dropdown-submenu > a:after {
        display: block;
        content: " ";
        float: right;
        width: 0;
        height: 0;
        border-color: transparent;
        border-style: solid;
        border-width: 5px 0 5px 5px;
        border-left-color: #ccc;
        margin-top: 5px;
        margin-right: -10px;
    }

    .dropdown-submenu:hover > a:after {
        border-left-color: #fff;
    }

    .dropdown-submenu.pull-left {
        float: none;
    }

    .dropdown-submenu.pull-left > .dropdown-menu {
        left: -100%;
        margin-left: 10px;
        -webkit-border-radius: 6px 0 6px 6px;
        -moz-border-radius: 6px 0 6px 6px;
        border-radius: 6px 0 6px 6px;
    }
</style>

<body>
<div class="container">
    <!--  下面几种表示方法均可以实现-->
    <ul class="nav nav-tabs">
        <!--<ul class="nav navbar-nav navbar-brand">-->
        <!--<ul class="nav navbar-nav">-->
        <!--<ul class="nav navbar-nav">-->
        <!--<ul class="nav nav-pills ">-->
        <!--<ul class="nav nav-pills navbar-fixed-bottom">-->
        <li class="dropdown">
            <a href="#" data-toggle="dropdown" class="dropdown-toggle">菜单1<span class="caret"></span></a>
            <ul class="dropdown-menu">
                <li ><a href="#">菜单11</a></li>
                <li ><a href="#">菜单12</a></li>
                <li ><a href="#">菜单13</a></li>
                <li ><a href="#">菜单14</a></li>
                <li ><a href="#">菜单15</a></li>

            </ul>
        </li>
        <li class="dropdown">
            <a href="#" data-toggle="dropdown">菜单2<span class="caret"></span></a>
            <ul class="dropdown-menu">
                <li ><a href="#">菜单21</a></li>
                <li ><a href="#">菜单22</a></li>
                <li ><a href="#">菜单23</a></li>
                <li class="dropdown-submenu">
                    <a href="javascript:;">菜单24</a>
                    <ul class="dropdown-menu">
                        <li><a tabindex="-1"href="javascript:;">菜单241</a></li>
                        <li><a tabindex="-1"href="javascript:;">菜单242</a></li>
                        <li class="divider"></li>
                        <li><a tabindex="-1"href="javascript:;">菜单243</a></li>
                        <li class="dropdown-submenu">
                            <a tabindex="-1"href="javascript:;">菜单244####</a>
                            <ul class="dropdown-menu">
                                <li><a tabindex="-1"href="javascript:;">菜单243</a></li>
                                <li><a tabindex="-1"href="javascript:;">菜单243</a></li>
                                <li><a tabindex="-1"href="javascript:;">菜单243</a></li>
                                <li><a tabindex="-1"href="javascript:;">菜单243</a></li>
                                <li><a tabindex="-1"href="javascript:;">菜单243</a></li>
                                <li class="dropdown-submenu">
                                    <a tabindex="-1"href="javascript:;">我加的1</a>
                                    <ul class="dropdown-menu">
                                        <li><a tabindex="-1"href="javascript:;">菜单243</a></li>
                                        <li><a tabindex="-1"href="javascript:;">菜单243</a></li>
                                        <li><a tabindex="-1"href="javascript:;">菜单243</a></li>
                                        <li class="dropdown-submenu">
                                            <a tabindex="-1"href="javascript:;">wo加的2</a>
                                            <ul class="dropdown-menu">
                                                <li class="dropdown-submenu">
                                                    <a tabindex="-1"href="javascript:;">wo加的3</a>
                                                    <ul class="dropdown-menu">
                                                        <li class="dropdown-submenu">
                                                            <a tabindex="-1"href="javascript:;">wo加的4</a>
                                                        </li>
                                                        <li class="divider"></li>
                                                    </ul>
                                                </li>
                                                <li class="divider"></li>
                                            </ul>
                                        </li>
                                        <li class="divider"></li>
                                    </ul>
                                </li>
                                <li class="divider"></li>
                            </ul>
                        </li>
                    </ul>
                </li>

                <li class="dropdown-submenu">
                    <a href="#">菜单25</a>
                    <ul class="dropdown-menu">
                        <li><a href="#">菜单251</a></li>
                        <li><a href="#">菜单252</a></li>
                        <li><a href="#">菜单253</a></li>
                        <li><a href="#">菜单254</a></li>
                        <li><a href="#">菜单255</a></li>
                    </ul>
                </li>

            </ul>
        </li>

        <li class="dropdown" >
            <a href="#" class="dropdown-toggle" data-toggle="dropdown">菜单3<span class="caret"></span></a>
            <ul class="dropdown-menu">
                <li><a href="#">菜单31</a></li>
                <li><a href="#">菜单32</a></li>
                <li><a href="#">菜单33</a></li>
                <li class="dropdown-submenu">
                    <a href="#">菜单34</a>
                    <ul class="dropdown-menu">
                        <li><a href="">菜单341</a></li>
                        <li><a href="">菜单342</a></li>
                        <li><a href="">菜单343</a></li>
                        <li><a href="">菜单344</a></li>
                    </ul>
                </li>
                <li><a href="#">菜单35</a></li>
                <li class="dropdown-submenu">
                    <a href="#">菜单36</a>
                    <ul class="dropdown-menu">
                        <li><a href="#">菜单361</a></li>
                        <li><a href="#">菜单362</a></li>
                        <li><a href="#">菜单363</a></li>
                        <li><a href="#">菜单364</a></li>
                        <li><a href="#">菜单365</a></li>
                    </ul>
                </li>
            </ul>
        </li>
        <li ><a href="#">菜单4</a></li>

        <li ><a href="#" href="#" data-toggle="dropdown">菜单5<span class="caret"></span></a></li>
    </ul>
</div>
</body>
</html>