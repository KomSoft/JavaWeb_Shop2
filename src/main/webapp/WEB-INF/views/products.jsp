<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>
<%@ include file="header.jsp" %>

    <c:forEach items="${products}" var="product">
      <table border='1'>
      <tr>
        <td width='150'>${product.name}</td>
        <td width='250' rowspan="2">
            <img src="${pageContext.request.contextPath}/static/images/products/${product.imageName}" width="auto" height="100px"/>
        </td>
      </tr>
       <tr>
        <td>${product.description}</td>
      </tr>
      <tr>
        <td>Price: ${product.price} UAH</td>
        <td>
            <%@ include file="buy_form.html" %>
        </td>
      </tr>
      </table><br/><br/>
    </c:forEach>

<%@ include file="footer.jsp" %>
