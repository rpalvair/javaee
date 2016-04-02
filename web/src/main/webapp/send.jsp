<!DOCTYPE HTML>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<html>
 <jsp:include page="head.jsp"/>
    <body>
        <div class="container">
                <div class="row margin-top-50">
             <form action="${root}/send" method="post">
               <div class="form-group">
                 <label for="content">Content</label>
                 <textarea class="form-control" name="content" id="content" placeholder="content" rows="3"></textarea>
               </div>

               <button type="submit" class="btn btn-default">Submit</button>
             </form>


              </div>
            <div class="row">
                <c:out value="${error}"/>
            </div>

            </div>
    </body>
</html>