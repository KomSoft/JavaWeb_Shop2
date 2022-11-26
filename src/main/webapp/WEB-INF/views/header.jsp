<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!--
Template Name: Modular Business
Author: <a href="http://www.os-templates.com/">OS Templates</a>
Author URI: http://www.os-templates.com/
Licence: Free to use under our free template licence terms
Licence URI: http://www.os-templates.com/template-terms
-->
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Java WEB Work Site</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/static/layout/layout.css" type="text/css" />
<script type="text/javascript" src="${pageContext.request.contextPath}/static/layout/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/layout/jquery.jcarousel.pack.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/layout/jquery.easing.1.3.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/layout/jquery.jcarousel.setup.js"></script>
<style>

 .count {
    color: #0000FF;
    position: relative;
    margin: 2px 10px 5px 5px;
    font-size: 12px;
    font-weight: 600;
    border-radius: 5px;
    border-color: #FFFFFF;
    height: 30px;
    width: 30px;
    background: #FFFFFF;
    
}

</style>

</head>
<body id="top">
<div class="wrapper col1">
  <div id="topbar">
    <p>Tel: +38 (063) 125-xxxx | Mail: info@domain.com</p>
    <ul>
      <li><a href="${pageContext.request.contextPath}/home">Home</a></li>
      <li><a href="${pageContext.request.contextPath}/products">Products</a></li>
      <li><a href="${pageContext.request.contextPath}/cart">Cart<span class="count">${sessionScope.userCart.size()}</span></a></li>
      <li><a href="${pageContext.request.contextPath}/registration">Registration</a></li>
      <li><a href="${pageContext.request.contextPath}/login">Login</a></li>
      <c:if test="${sessionScope.authenticatedUser != null}" >
         <li class="last"><a href='${pageContext.request.contextPath}/login?logoutKey'>Logout</a></li>
      </c:if>
    </ul>
    <br class="clear" />
  </div>
</div>
<!-- ####################################################################################################### -->
<div class="wrapper col2">
  <div id="header">
    <div class="fl_left">
      <h1><a href="${pageContext.request.contextPath}/home">KomSoft Example</a></h1>
      <p>Free Website Template</p>
    </div>
    <div class="fl_right"> <a href="#"><img src="${pageContext.request.contextPath}/static/images/banner468x60.gif" alt="" /></a> </div>
    <br class="clear" />
  </div>
</div>
<!-- ####################################################################################################### -->
<div class="wrapper col4">
  <div id="featured_slide">
    <div id="featured_content">
      <ul>
<%@ include file="slides.jsp" %>      
      </ul>
    </div>
    <a href="javascript:void(0);" id="featured-item-prev"><img src="${pageContext.request.contextPath}/static/layout/prev.png" alt="" /></a> <a href="javascript:void(0);" id="featured-item-next"><img src="${pageContext.request.contextPath}/static/layout/next.png" alt="" /></a> </div>
</div>
<!-- ####################################################################################################### -->
<div class="wrapper col5">
  <div id="container">
<!-- side menu -->      
    <div id="column">
      <div class="holder">
          <c:choose>
              <c:when test="${sessionScope.authenticatedUser != null}">
                  <h2 align="center">Welcome back, ${sessionScope.authenticatedUser}!</h2>
                  <center><a href="${pageContext.request.contextPath}/login?logoutKey">Logout</a></center>
                  <p align="center">Your <a href="${pageContext.request.contextPath}/cart">
                      <img src="${pageContext.request.contextPath}/static/layout/cart20x20.png" alt="Кошик" /></a>
                      <c:choose>
                          <c:when test="${sessionScope.userCart == null}">
                             is empty
                          </c:when>
                          <c:otherwise>
                              has (${sessionScope.userCart.size()}) item(s)
                          </c:otherwise>
                      </c:choose>
                  </p>
               </c:when>
              <c:otherwise>
                  <h2 align="center">Welcome, Guest!</h2>
                  <p align="center">To put items into Cart please login</p>
              </c:otherwise>
          </c:choose>
         <ul>
         <li><a href="${pageContext.request.contextPath}/home">Home</a></li>
         <c:if test="${sessionScope.categories == null}">
            <jsp:include page="/category" />
         </c:if>

         <c:forEach items="${sessionScope.categories}" var="cat">
            <li><a href="${pageContext.request.contextPath}/products?category=${cat.id}">${cat.name}</a></li>
         </c:forEach>
         <li><a href="${pageContext.request.contextPath}/login">Login</a></li>
         <li><a href="${pageContext.request.contextPath}/registration">Registration</a></li>
         
         </ul>
         <br><br><br>
         Please enter your email to join our mailing list<br>
         <form action="#" method="post">
            <fieldset>
               <legend>News Letter</legend>
               <input type="text" value="Enter Email Here&hellip;"  onfocus="this.value=(this.value=='Enter Email Here&hellip;')? '' : this.value ;" />
               <input type="submit" name="news_go" id="news_go" value="Join" />
            </fieldset>
         </form>
         <p>To unsubscribe please <br><a href="#">click here &raquo;</a></p>
      </div>
    </div>
<!-- content -->    
    <div id="content">