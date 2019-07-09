## 本章目标

1. 使用<jsp:includepage />标签
2. <%%> 标签
3. <%= %> 标签



## 内容

1.  将头部内容剥离到 include 文件下 header.jsp
2.  在主页引入

```
<jsp:includepage="include/header.jsp"/>
```

3. 修改header.jsp 的登录为

   ```
      <% 
   			if (nickName == null){ 
   		%>
             <a href=login.jsp class="loginBut">登录</a>
             <a href="#">注册</a>
           <%
   			} else { 
           %>    
           	<a href="">欢迎:<%= nickName %></a>     
           <%
   			}
           %>
   ```

4.  修改userDao.java 的语句

   ```
   nick_name as nickName
   ```

5.  改造servelet

   ```
   String action = request.getParameter("action");
   
   if ("loginout".equals(action)) {//登录
   }
   ```

6. 改造 jsp

   ```
   UserServlet?action=logout
   ```

7. 退出功能

```
request.getSession().removeAttribute("loginuser");
response.sendRedirect("index.jsp");
```

