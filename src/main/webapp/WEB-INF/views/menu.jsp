
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>
<html>
<head>
    <title>Note Table</title>
</head>
<body>
<ul>
    <table>
        <tr>
            <th>ID</th>
            <th>Note Name</th>
        </tr>

        <c:forEach var="note" items="${notes}">

            <tr>

                <td><a href="<c:url value="/notes/${note.getNoteId()}" />">${note.getNoteId()}</a></td>
                <td><a href="<c:url value="/notes/${note.getNoteId()}" />">${note.getNoteName()}</a></td>
                <td>
                    <form action="<c:url value="/post/delete/" />" method="post">
                        <input type="hidden" value="${note.getNoteId()}" name="note_id">
                        <input type="submit" value="DELETE" name="delete_note">
                    </form>
                </td>


            </tr>

        </c:forEach></table>

</ul>

<form action="<c:url  value="/addnote"/>" method="post">

    Note Name:         <input type="text" name="name"><br>
    Note Description: <input type="text" name="description"><br>
    <input type="submit">

</form>


</body>
</html>
