<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>
<%@ include file="header.jsp" %>

<h3 align="center">Your Shopping Cart:</h3>
<form action="${pageContext.request.contextPath}/pay" method="post" >
   <input type="submit" value="Pay now" />
</form>

<c:set var="itemsCount" value="0" />
<c:set var="allCount" value="0" />
<c:set var="totalPrice" value="0" />

<!--  ProductDto: ${product.key} - count: ${product.value}  -->
<c:forEach items="${userCart}" var="product">
    <table border='1'>
        <tr>
            <td width='150'>${item}<br>${product.key.name}</td>
            <td width='250' rowspan="2">
                <img src="${pageContext.request.contextPath}/static/images/products/${product.key.imageName}" width="auto" height="200px"/>
            </td>
        </tr>
        <tr>
            <td>${product.key.description}</td>
        </tr>
        <tr>
            <td>Price: ${product.key.price} UAH</td>
            <td>
                <form action="${pageContext.request.contextPath}/cart?delete=${product.key.id}" method="post" >
                    <input type="hidden" name="id" value="${product.key.id}" />
                    <input type="button" value='-' onClick="changeCount(${product.key.id}, -1)"/>
                    <input type="number" name="count" id="items_count${product.key.id}" value="${product.value}" min="1" style="width: 4em"/>
                    <input type="button" value='+' onClick="changeCount(${product.key.id}, +1)"/>
                    &nbsp;&nbsp;&nbsp;&nbsp;<input type="submit" value='Remove from Cart (not Implemented Yet)' />
                </form>
            </td>
        </tr>
    </table><br/><br/>
</c:forEach>

<script>
    function changeCount(id, i) {
        let p_id = "items_count" + id;
        let count = parseInt(document.getElementById(p_id).value) || 0;
        count += i;
        count = (count < 1) ? 1 : count;
        document.getElementById(p_id).value = count;
    }
</script>

<%@ include file="footer.jsp" %>
