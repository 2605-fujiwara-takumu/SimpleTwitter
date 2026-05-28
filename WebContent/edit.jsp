<%@page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>つぶやき編集画面</title>
    <link href="./css/style.css" rel="stylesheet" type="text/css">
</head>
<body>
	<form action="edit" method="post">
         つぶやき<br />
         <input type="hidden" name="editMessageId" value="${editMessage.id}">
         <textarea name="editText" cols="100" rows="5" class="tweet-box" >${editMessage.text}</textarea>
         <br />
         <input value="更新" type="submit"/>
     </form>
     <br />
     <a href="./">戻る</a>
     <br />
     <div class="copyright"> Copyright(c)YourName</div>
</body>
</html>