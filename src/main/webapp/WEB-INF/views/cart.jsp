<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>
<%@ include file="header.jsp" %>

<h3 align="center">Your Shopping Cart:</h3>

<c:set var="itemsCount" value="0" />
<c:set var="allCount" value="0" />
<c:set var="totalPrice" value="0" />

<!--  ProductDto: ${product.key} - count: ${product.value}  -->
<c:forEach items="${userCart}" var="product">
    <table border="1">
        <tr>
            <td width="150">${product.key.name}</td>
            <td width="250" rowspan="2">
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
                    <input type="hidden" name="delete" value="" />
                    <input type="button" value="-" onClick="changeCount(${product.key.id}, -1)"/>
                    <input type="number" id="items_count${product.key.id}" value="${product.value}" min="1" style="width: 4em"/>
                    <input type="button" value="+" onClick="changeCount(${product.key.id}, +1)"/>
                    &nbsp;&nbsp;&nbsp;&nbsp;<input type="submit" value="Remove from Cart (not Implemented Yet)" />
                    (Changed value not save yet)
                </form>
            </td>
        </tr>
    </table><br/>
    <c:set var="itemsCount" value="${itemsCount + 1}" />
    <c:set var="allCount" value="${allCount + product.value}" />
    <c:set var="totalPrice" value="${totalPrice + product.key.price * product.value}" />
</c:forEach>
<form action="${pageContext.request.contextPath}/pay" method="post" >
    <table border='1'>
        <tr>
            <td width="100" align="right">Total items:</td>
            <td width="100" >${itemsCount}</td>
        </tr>
        <tr>
            <td width="100" align="right">Total count:</td>
            <td width="100" >${allCount}</td>
        </tr>
        <tr>
            <td width="100" align="right">Total price:</td>
            <td width="100" >${totalPrice}</td>
        </tr>
    </table>
    <center><input type="submit" value="Pay now" /></center>
</form>

<script>
    function changeCount(id, i) {
        let p_id = "items_count" + id;
        let count = parseInt(document.getElementById(p_id).value) || 0;
        count += i;
        count = (count < 1) ? 1 : count;
        document.getElementById(p_id).value = count;
//    How to change value in Map ???
    }
</script>

<%@ include file="footer.jsp" %>
