<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="css/index.css">
    <link rel="stylesheet" href="css/main.css">
    <link rel="stylesheet" href="css/modal.css">
</head>
<body>

<form action="region" method="post">
    <label for="sel_region">과일 선택:</label>
    <select id="sel_region" name="sel_region">
        <option value="">-- 선택하세요 --</option>
        <c:forEach items="${region}" var="r">
            <option value="${r.info_region}" selected="${selected_region == r.info_region}">${r.info_region}</option>
        </c:forEach>
    </select>
    <button>조회</button>
</form>

<h1>지역별 검색 결과</h1>
<table>
    <thead style="position : sticky; top:0">
    <tr>
        <th>no</th>
        <th>제목</th>
        <th>글자수</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${ret}" var="r">
        <tr>
            <td>${r.info_no}</td>
            <td onclick="openModal(${r.info_no})">${r.info_title}</td>
            <td>${r.info_content.length()}</td>
        </tr>
    </c:forEach>
    </tbody>
</table>

<dialog id="modal">
    <div class="modal-header">
        <h2>상세 정보</h2>
        <button type="button" onclick="closeModal()">닫기</button>
    </div>

    <div id="modalContent">
        <!-- JSP 내용이 들어갈 영역 -->
    </div>
</dialog>

<%--style="height:80%; overflow-y:auto;"--%>
<%--style="display:block; max-height:300px; overflow-y:scroll;"--%>
<%--<input value="${json}">--%>

</body>
<script src="js/region.js"></script>
<script>
    const handleDetail = (no) => {
        location.href = 'result?no=' + no;
    }
</script>
</html>
