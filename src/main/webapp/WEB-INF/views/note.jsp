<%--
  Created by IntelliJ IDEA.
  User: Vlad
  Date: 20.03.2021
  Time: 15:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>
<html>
<head>
    <title>${note.getNoteName()}</title>
</head>
<body>
<ul>

   <li><b>Note ID:</b> <c:out value=" ${note.getNoteId()}"> /</c:out></li>
    <li><b>Note Name:</b> <c:out value=" ${note.getNoteName()}"> /</c:out> </li>
    <li><b>Note Description: </b><c:out value=" ${note.getNoteDescription()}"> /</c:out> </li>
    <li><b>Note Creation Time: </b><c:out value=" ${note.getNoteCreationTime()}"> /</c:out> </li>

</ul>
</body>
</html>
