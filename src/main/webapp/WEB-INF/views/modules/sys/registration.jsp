<%--
  Created by IntelliJ IDEA.
  User: brant
  Date: 2018-11-8
  Time: 0:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title>注册</title>
    <style type="text/css">
        #registration_tab{
            width: 80%;
        }
        #registration_tab input{
            border: 0px;
        }
        #registration_tab tr td{
            border-bottom: 1px solid red;
        }
    </style>
    <script type="text/javascript" src="${ctxStatic}/jquery/jquery-1.8.3.js"></script>
    <script type="text/javascript">
        $(function () {
            console.log("加载。。。。。")
        })
    </script>
</head>
<body>

<div style="width: 100%;padding-left: 10%">
    <table id="registration_tab">
        <tr>
            <td colspan="2">推荐人：${referee}</td
        </tr>
        <tr>
            <td colspan="2"><input type="input" width="100%" placeholder="请填写您的用户昵称" ></td>
        </tr>
        <tr>
            <td colspan="2"><input type="input" width="100%" placeholder="请填写您的手机号码" ></td>
        </tr>
        <tr>
            <td colspan="2"><input type="password" width="100%" placeholder="请填写您密码" ></td>
        </tr>
        <tr>
            <td colspan="2"><input type="password" width="100%" placeholder="请确认您的密码" ></td>
        </tr>
        <tr>
            <td width="80%"><input type="input" width="100%" placeholder="请输入验证码" ></td>
            <td><a style="color: red;cursor: pointer" onclick="">获取验证码</a></td>
        </tr>
        <tr>
            <td colspan="2" style="text-align: center"><input type="button" width="80%" value="注册" ></td>
        </tr>


    </table>
</div>
</body>
</html>
