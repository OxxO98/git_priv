<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

<%--<c:forEach items="${tl_translations}" var="tl">--%>
<%--    <div>${tl.value.key} ${tl.value.name} ${tl.value.desc}</div>--%>
<%--</c:forEach>--%>

<%--<c:forEach items="${tl_key_map}" var="tl">--%>
<%--    <div>${tl.key} ${tl.value}</div>--%>
<%--</c:forEach>--%>

<%--<c:forEach items="${tl_cmp_translations}" var="tl">--%>
<%--    <div>${tl.key} ${tl.name} ${tl.desc}</div>--%>
<%--</c:forEach>--%>

<%--<div>${tl_cmp_total}</div>--%>

<%--<c:forEach items="${bi_items}" var="bi">--%>
<%--    <div>${bi.qid} ${bi.name} ${bi.desc}</div>--%>
<%--</c:forEach>--%>


<c:forEach items="${ret}" var="r">
    <div>${r.value}</div>
</c:forEach>
total join ${ret_size}

<%--<c:forEach items="${cmp_ret}" var="r">--%>
<%--    <div>${r.value}</div>--%>
<%--</c:forEach>--%>
total cmp ${cmp_ret_size}


</body>
</html>
