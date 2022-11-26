
<%@ page isELIgnored="false" %>
<!-- %@ page contentType="text/html;charset=UTF-8" language="java" %-->

<%@ include file="header.jsp" %>
<center>
<table border='0'><form action='registration' method='POST'>
    <tr>
       <td width='150'>Login</td>
       <td width='200'><input type='email' name='login' value='<%=request.getParameter("login") != null ? request.getParameter("login") : "" %>' /></td>
       <td width='250'>${sessionScope.errors[0]}</td>
    </tr>
    <tr>
        <td>Password</td>
        <td><input type='password' name='password' /></td>
        <td>${sessionScope.errors[1]}</td>
    </tr>
    <tr>
       <td>Re-Password</td>
       <td><input type='password' name='rePassword' /></td>
       <td>${sessionScope.errors[2]}</td>
    </tr>
    <tr>
       <td>Name</td>
       <td><input type='text' name='fullName' value='<%=request.getParameter("fullName") != null ? request.getParameter("fullName") : "" %>' /></td>
       <td>${sessionScope.errors[3]}</td>
    </tr>
    <tr>
       <td>Region</td>
       <td><select name='region'>
          <option value=''>Select your region</option>
          <option value='Kyiv' <%="Kyiv".equals(request.getParameter("region")) ? "selected" : "" %> >Kyiv region</option>
          <option value='Lviv' <%="Lviv".equals(request.getParameter("region")) ? "selected" : "" %> >Lviv region</option>
          <option value='Odesa' <%="Odesa".equals(request.getParameter("region")) ? "selected" : "" %> >Odesa region</option>
       </select></td>
       <td>${sessionScope.errors[4]}</td>
    </tr>
    <tr>
       <td>Gender</td>
       <td>F<input type='radio' name='gender' value='F' <%= "F".equalsIgnoreCase(request.getParameter("gender")) ? "checked" : ""  %> />
           M<input type='radio' name='gender' value='M' <%= "M".equalsIgnoreCase(request.getParameter("gender")) ? "checked" : ""  %> />
       </td>
       <td>${sessionScope.errors[5]}</td>
    </tr>
    <tr>
       <td>Comment</td>
       <td><textarea name='comment' cols='21' rows='5'><%= request.getParameter("comment") != null ? request.getParameter("comment") : "" %></textarea></td>
       <td>${sessionScope.errors[6]}</td>
    </tr>
    <tr>
       <td>Glory to Ukraine</td>
       <td><input type='checkbox' name='agreement' <%="on".equalsIgnoreCase(request.getParameter("agreement")) ? "checked" : "" %> /></td>
       <td>${sessionScope.errors[7]}</td>
    </tr>
    <tr>
       <td>&nbsp;</td>
       <td align='center'><input type='submit' value='Send' /></td>
       <td>&nbsp;</td>
    </tr>
</form></table>
</center>
<%@ include file="footer.jsp" %>
