<%--
  Created by IntelliJ IDEA.
  User: Nghia B
  Date: 01/15/21
  Time: 16:05
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<div>
    <a href="/product?action=create">Add new product</a>
</div>
<form action="/product">
    <table>
        <tr>
            <th>#</th>
            <th>Product Name</th>
            <th>Price</th>
            <th>Quantity</th>
            <th>Color</th>
            <th>Category</th>
            <th>Action</th>
        </tr>
        <c:forEach items="${requestScope['listProduct']}" var="product">
            <tr>
                <td>${product.getId()}</td>
                <td>${product.getName()}</td>
                <td>${product.getPrice()}</td>
                <td>${product.getAmount()}</td>
                <td>${product.getColor()}</td>
                <td>${product.getCategory()}</td>
                <td>${product.get()}</td>
            </tr>
        </c:forEach>
    </table>
</form>

</body>
</html>
