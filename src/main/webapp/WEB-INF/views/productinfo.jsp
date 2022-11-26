<%@ page isELIgnored="false" %>
<%@ include file="header.jsp" %>

   <table border='1'>
      <tr>
          <td width='100'>Name</td>
          <td width='400'>${productInfo.name}</td>
      </tr>
       <tr>
        <td>Description</td>
        <td>${productInfo.description}</td>
      </tr>
      <tr>
        <td><input type='button' value='Buy now' />&nbsp;&nbsp;&nbsp;&nbsp;Price:</td>
        <td>${productInfo.price} UAH</td>
      </tr>
      <tr>
        <td colspan="2">
            <img src="${pageContext.request.contextPath}/static/images/products/${productInfo.imageName}" width="400px" height="auto"/> </td>
        </td>
      </tr>
   </table>

<%@ include file="footer.jsp" %>
