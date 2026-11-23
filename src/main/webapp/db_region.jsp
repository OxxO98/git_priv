<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <script src="https://code.jquery.com/jquery-3.8.0.js" integrity="sha256-/kBJEgNvXFuC0vfFREdzfv7vyPT1es3X+YuHXzwOmtA=" crossorigin="anonymous"></script>
    <link rel="stylesheet" href="css/index.css">
    <link rel="stylesheet" href="css/main.css">
    <link rel="stylesheet" href="css/modal.css">
</head>
<body>

<form action="region" method="post">
    <label for="sel_region">지역 선택:</label>
    <select id="sel_region" name="sel_region">
        <option value="">-- 선택하세요 --</option>
        <c:forEach items="${region}" var="r">
            <option value="${r.info_region}" ${selected_region eq r.info_region ? 'selected' : ''}>${r.info_region} | ${r.info_region_ko}</option>
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
        <th>번역</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${ret}" var="r">
        <tr>
            <td>${r.info_no}</td>
            <td onclick="openModal(${r.info_no})">${r.info_title}</td>
            <td>${r.info_content.length()}</td>
            <td>
                <button onclick="handleTranslate(this)"
                    data-no="<c:out value='${r.info_no}'/>"
                    data-title="<c:out value='${r.info_title}'/>"
                    data-content="<c:out value='${r.info_content}'/>"
                    ${r.info_content_ko ne null ? 'disabled' : ''}
                >번역</button>
            </td>
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
<script src="js/translate.js"></script>
<script>
    const handleDetail = (no) => {
        location.href = 'result?no=' + no;
    }
</script>
</html>

<%--                <button--%>
<%--                        data-no="${ret.info_no}"--%>
<%--                        data-title="${ret.info_title}"--%>
<%--                        data-content="${ret.info_content}"--%>
<%--                >번역--%>
<%--                </button>--%>