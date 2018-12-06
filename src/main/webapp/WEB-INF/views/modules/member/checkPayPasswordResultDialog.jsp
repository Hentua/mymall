<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>验证结果</title>
    <meta name="decorator" content="default"/>
    <script type="text/javascript">
        var checkResult = '${checkResult}';
        window.parent.window.checkResult = checkResult;
        parent.$.jBox.close();
    </script>
</head>
<body style="overflow-x: hidden;">
</body>
</html>