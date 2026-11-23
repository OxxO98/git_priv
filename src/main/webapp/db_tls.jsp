<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="css/index.css">
    <link rel="stylesheet" href="css/main.css">
    <script src="https://code.jquery.com/jquery-3.8.0.js" integrity="sha256-/kBJEgNvXFuC0vfFREdzfv7vyPT1es3X+YuHXzwOmtA=" crossorigin="anonymous"></script>
</head>
<body>

<%--<form action="result">--%>
<%--    <input name="no">--%>
<%--    <button>검색</button>--%>
<%--</form>--%>

<%--<h1>no 검색 결과</h1>--%>
<form action="api/translate" method="post">
    <input name="no" value="${ret.info_no}"> <br>
    <input name="year" value="${ret.info_year}"> <br>
    <input name="title" value="${ret.info_title}"> <br>
    <input name="region" value="${ret.info_region}"> <br>
    <textarea name="content">${ret.info_content}</textarea> <br>
    <button>번역</button>
</form>

<input value="${json}">
<script src="js/translate"></script>
</body>
</html>
