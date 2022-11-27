<%@ page isELIgnored="false" %>
<%@ include file="header.jsp" %>

<c:set var="product" value="${products[0]}" />
<table border='1'>
    <tr>
        <td width='100'>Name</td>
        <td width='400'>${product.name}</td>
    </tr>
    <tr>
        <td>Description</td>
        <td>${product.description}</td>
    </tr>
    <tr>
        <td>Price:</td>
        <td>${product.price} UAH
            <%@ include file="buy_form.html" %>
        </td>
    </tr>
    <tr>
        <td colspan="2">
            <img src="${pageContext.request.contextPath}/static/images/products/${product.imageName}" width="400px" height="auto"/>
        </td>
    </tr>
</table>

<%@ include file="footer.jsp" %>
