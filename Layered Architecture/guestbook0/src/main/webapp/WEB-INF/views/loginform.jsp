<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>loginform</title>
</head>
<body>
<h1>관리자 로그인</h1>
<br>
<br> ${errorMessage}
<br>

<form method="post" action="Login">
    암호 : <input type="password" name="passwd"><br> <!-- input 상자를 이용해서 password 입력을 받아온다. !-->
    <input type="submit">  <!-- submit 버튼을 누르면 Login이라는 요청을 보내게 된다.!-->
</form>
</body>
</html>
