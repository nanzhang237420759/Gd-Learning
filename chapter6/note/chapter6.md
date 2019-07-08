## 本章目标

1. 使用serevlet 实现用户登录操作



## Servlet

1. 建立Web应用

2. 编写Servlet 继承自HttpServlet

   ```java
   package com.foodworld.web;
   
   import java.io.IOException;
   
   import javax.servlet.ServletException;
   import javax.servlet.http.HttpServlet;
   import javax.servlet.http.HttpServletRequest;
   import javax.servlet.http.HttpServletResponse;
   
   
   public class UserServlet extends HttpServlet { 
   	
   	@Override
   	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
   		// TODO Auto-generated method stub
   		super.doGet(req, resp);
   	}
   
   	@Override
   	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
   		// TODO Auto-generated method stub
   		super.doPost(req, resp);
   	}
   	
   }
   ```

   

3. 配置web.xml

   ```xml
   <servlet>
       <servlet-name>UserServlet</servlet-name>
       <servlet-class>com.foodworld.web.UserServlet</servlet-class>
   </servlet>
   
   <servlet-mapping>
     <servlet-name>UserServlet</servlet-name>
     <url-pattern>/UserServlet</url-pattern>
   </servlet-mapping>
   ```

   

4. 启动tomcat，访问Servlet



## 登录实现

**servlet  doPost**

```java
request.setCharacterEncoding("UTF-8");
response.setContentType("text/html;charset=UTF-8");
String uName=request.getParameter("userName");
String pwd=request.getParameter("pwd");
if("admin".equals(uName)&&"admin".equals(pwd)){
  HttpSession session=request.getSession();
  session.setAttribute("login", uName);
  response.sendRedirect("index.jsp");
}else{
  PrintWriter out = response.getWriter();
  out.println("<script type='text/javascript'>alert('登录失败');location.href='login.jsp';</script>");
  out.close();
}
```



**login.jsp**

加入登录验证和请求

```javascript
$(".loginConfirm").on("click", function () {
                // window.location.href = 'index.html'
                
            	 if ($("[name='account']").val() == "") {
                     $("[name='account']").focus();
                     return;
                 }
               

                 if ($("[name='password']").val() == "") {
                     $("[name='password']").focus();
                     return;
                 } 

                 $.ajax({
                     url:"UserServlet",
                     type:"post",
                     dataType:"json",
                     data:{"account":$('#account').val(),"password":$('#password').val()},//数据为登录名和登录密码
                     
                     beforeSend:function(){ 
                         $('#loginConfirm').val("登录中");
                     },
                     
                     success:function(data){//处理返回的信息，true则跳转，false则提示密码错误
                         if(data==false){
                             $("[name='account']").val("");
                             $("[name='password']").attr('placeholder',"密码错误");
                             $('#loginConfirm').val("登录");
                         }else{
                        	 alert('登录成功')
                               window.location.href = 'index.jsp';
                         }
                     },
                 });
            });
```





