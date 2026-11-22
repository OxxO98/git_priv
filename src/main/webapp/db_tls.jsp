<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

<form action="api/translate" method="post">
    <input name="no" value="${ret.info_no}"> <br>
    <input name="year" value="${ret.info_year}"> <br>
    <input name="region" value="${ret.info_region}"> <br>
    <textarea name="content">${ret.info_content}</textarea> <br>
    <button>번역</button>
</form>

<input value="${json}">

</body>
</html>
