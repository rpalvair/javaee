<!DOCTYPE HTML>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<jsp:include page="head.jsp"/>
    <body>
        <div class="container">
            <div class="row margin-top-50">
            <table class="table table-bordered">
                <c:forEach items="${messages}" var="i">
                                   <tr>
                                        <td><b>Message received :</b></td>
                                        <td><c:out value="${i}"/></td>
                                     </tr>
                                </c:forEach>
            </table>
            </div>
            <div class="row margin-top-50">
            <a class="btn btn-default" href="${root}/send">Send another message</a>
            </div>
        </div>


    </body>
</html>