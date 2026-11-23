<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>JSP - Hello World</title>
</head>
<body>
<h1><%= "Hello World!" %>
</h1>
<br/>
<a href="hello-servlet">전체 jsonl 파싱 join</a> <br>
<a href="region">지역 별 검색</a>


<form action="result">
    <input name="no">
    <button>검색</button>
</form>
</body>
</html>