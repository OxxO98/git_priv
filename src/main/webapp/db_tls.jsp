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
<label for="no">no</label>
<input name="no" value="${ret.info_no}"> <br>
<label for="year">년도</label>
<input name="year" value="${ret.info_year}"> <br>
<label for="region">지역</label>
<input name="region" value="${ret.info_region}"> <br>
<label for="title">제목</label>
<input name="title" value="${ret.info_title}"> <br>
<textarea name="content">${ret.info_content}</textarea> <br>
<h2>번역</h2>
<label for="year_ko">년도</label>
<input name="year_ko" value="${ret.info_year_ko}"> <br>
<label for="region_ko">지역</label>
<input name="region_ko" value="${ret.info_region_ko}"> <br>
<label for="title_ko">제목</label>
<input name="title_ko" value="${ret.info_title_ko}"> <br>
<textarea name="content_ko">${ret.info_content_ko}</textarea> <br>
</body>
</html>
