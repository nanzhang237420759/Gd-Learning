## 本章目标

1.  发布菜谱功能
2. 掌握Commons-FileUpload组件上传文件的功能
3.  使用jstl标签展示列表

## 发布菜谱

[下载jar](http://commons.apache.org/)



1. 建立CookBook实体类

   ```java
   public class CookBook {
   	
   	private String title;
   	
   	private String tags;
   	
   	private String content;
   	
   	private Date  createDate;
   	
   	private int  likeNum;
   	
   	......
   }
   ```

   

2. 建立CookBookDao类

   ```java
   public class CookBookDao {
   	 private QueryRunner runner = null;//查询运行器
   	  
   	 public CookBookDao(){
   		 runner = new QueryRunner();
   	 }
   	
   	// 添加save 方法
   	public int save(CookBook entity) throws Exception { 
   		String sql = "insert into cookbook(title,tags,content,create_time) values(?,?,?,?)";
   		return runner.update(DBUtil.getConn(),sql, entity.getTitle(),entity.getTags(),entity.getContent(),entity.getCreateDate());
   		 
   	} 
   }
   ```

   

3. 建立CookBookService类

   ```java
   public class CookBookService {
   	
   	CookBookDao dao = new CookBookDao();
   	
   	public int save(CookBook entity) throws Exception { 
   		return dao.save(entity);
   	}
   
   }
   ```

   

4. 建立CookBookServlet 类

   ```java
   public class CookBookServlet extends HttpServlet {
   
   	@Override
   	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException { 
   		doPost(req,resp);
   	}
   
   	@Override
   	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
   		 
   	}
   }
   ```

5. 配置web.xml

   ```xml
    <servlet>
   	<servlet-name>CookBookServlet</servlet-name>
   	<servlet-class>com.foodworld.web.CookBookServlet</servlet-class>
     </servlet>
   
     <servlet-mapping>
   	  <servlet-name>CookBookServlet</servlet-name>
   	  <url-pattern>/cookBookServlet</url-pattern>
     </servlet-mapping>
     
   ```

6. 全局编码设置(过滤器)

   ```
   package com.foodworld.web;
   
   import java.io.IOException;
   
   import javax.servlet.Filter;
   import javax.servlet.FilterChain;
   import javax.servlet.FilterConfig;
   import javax.servlet.ServletException;
   import javax.servlet.ServletRequest;
   import javax.servlet.ServletResponse;
   
   public class EncodeFilter implements Filter {
   	private String encode = null;
   
   	public void destroy() {
   		encode = null;
   	}
   
   	// 对所有页面设置字符集
   	public void doFilter(ServletRequest request, ServletResponse response,
   			FilterChain chain) throws IOException, ServletException {
   		if (null == request.getCharacterEncoding()) {
   			request.setCharacterEncoding(encode);
   		}
   		chain.doFilter(request, response);
   	}
   
   	public void init(FilterConfig filterConfig) throws ServletException {
   		String encode = filterConfig.getInitParameter("encode");
   		if (this.encode == null) {
   			this.encode = encode;
   		}
   	}
   
   }
   ```

   **web.xml**

   ```
   <filter>
         <filter-name>EncodeFilter</filter-name>
   	  <filter-class>com.foodworld.web.EncodeFilter</filter-class>
   	  <init-param>
   	  	<param-name>encode</param-name>
   	  	<param-value>UTF-8</param-value>
   	  </init-param>	  
     </filter>
     <filter-mapping>
     	  <filter-name>EncodeFilter</filter-name> 
     	  <url-pattern>/*</url-pattern>  	   	  
     </filter-mapping> 
   ```

   

7. 将cookBookAdd.html 转化成cookBookAdd.jsp

   ```jsp
   <%@ page language="java" contentType="text/html; charset=UTF-8"
       pageEncoding="UTF-8"%>
   <!DOCTYPE html>
   <html>
   <head>
     <meta charset="UTF-8" />
     <meta name="viewport" content="width=device-width, initial-scale=1.0" />
     <meta http-equiv="X-UA-Compatible" content="ie=edge" />
     <title>发布菜谱</title>
     <link rel="stylesheet" href="css/style.css" />
     <style>
       .contentBox {
         margin: 30px auto 62px;
         background: #fff;
       }
   
       .editRegion {
         margin: 0 auto;
         padding: 30px 0;
         width: 600px;
       }
   
       .editRegion input,
       .editRegion textarea {
         margin-bottom: 10px;
         padding-left: 20px;
         width: calc(100% - 20px);
         background: #f5f6f5;
         border: 1px solid #dcdcdc;
         font-size: 14px;
         resize: none;
       }
   
       .editRegion textarea {
         padding: 10px 0 10px 20px;
       }
   
       .editName {
         height: 38px;
       }
   
       .editDescribe {
         height: 60px;
       }
   
       .imgPicker,
       .imgPicker2 {
         width: 100%;
         height: 400px;
         display: block;
         border: 1px solid #dcdcdc;
         overflow: hidden;
         background: url("img/fileImg.png") no-repeat center #f5f6f5;
         background-position: 50% 160px;
         text-align: center;
         color: #888888;
       }
   
       .imgPicker2 {
         display: inline-block;
         width: 260px;
         height: 170px;
         background-position: 50% 40px;
       }
   
       .contentBox .imgPicker-file {
         outline: none;
         width: 0;
         -webkit-opacity: 0;
         -moz-opacity: 0;
         opacity: 0;
         position: absolute;
         filter: opacity(0);
       }
   
       .imgPicker img,
       .img-box {
         width: 100%;
         height: 100%;
       }
   
       .imgTips {
         margin: 210px 0 10px;
       }
   
       .material {
         font-size: 0;
       }
   
       .material input {
         width: 258px;
         height: 38px;
         margin-right: 10px;
       }
   
       .materialDelete {
         width: 20px;
         height: 20px;
         background: url("img/materialDelete.png") no-repeat center;
         vertical-align: middle;
         cursor: pointer;
       }
   
       .editRegion h3 {
         margin: 30px 0 20px;
         font-size: 16px;
       }
   
       .editAdd {
         width: 568px;
         height: 38px;
         line-height: 38px;
         border: 1px dashed #dcdcdc;
         color: #888888;
         text-align: center;
         cursor: pointer;
       }
   
       .practice li {
         margin-bottom: 20px;
       }
   
       .practice p {
         margin-bottom: 10px;
       }
   
       .practice textarea {
         width: 548px;
         height: 40px;
       }
   
       .practice .imgTips {
         margin-top: 90px;
       }
   
       .practice .materialDelete {
         margin-left: 5px;
         vertical-align: initial;
       }
   
       .menuLabel li {
         margin-bottom: 10px;
       }
   
       .menuLabel p {
         margin-bottom: 10px;
       }
   
       .menuLabel span {
         display: inline-block;
         margin: 0 5px 10px 0;
         width: 100px;
         height: 40px;
         line-height: 40px;
         border: 1px solid #dcdcdc;
         text-align: center;
         cursor: pointer;
         background: #f5f6f5;
         color: #888888;
       }
   
       .editWarning img {
         margin-right: 5px;
       }
   
       .editBut {
         margin: 30px 0;
         text-align: center;
       }
   
       .editBut input {
         width: 280px;
         height: 40px;
         border-radius: 4px;
         background: #fc5a34;
         font-size: 16px;
         color: #fff;
         cursor: pointer;
       }
     </style>
   </head>
   
   <body>
     <!-- 头部 -->
     <jsp:includepage="include/header.jsp"/>
     <!-- 头部 -->
   
     <!-- 内容 --> 
     <div class="contentBox">
       <div class="editRegion">
        <form action="cookBookServlet?action=add" method="post">
            <input type="text" class="editName" name="title" placeholder="菜谱名称" />
            <input type="text" class="editName" name="tags" placeholder="标签 多个用,分隔" />
   	       <textarea class="editDescribe" name="content"  placeholder="菜谱描述"></textarea>  
   	      <div class="editWarning"> 
   	        <span>请完整的填写所有信息！</span>
   	      </div>
   	
   	      <div class="editBut">
   	         <input type="submit" value="发&nbsp布"/>
   	      </div>
          </form>
       </div> 
     </div> 
     <!-- 内容 -->
   
     <!-- 底部 -->
      <jsp:includepage="include/footer.jsp"/> 
     <!-- 底部 -->
   
     <script src="js/jquery-3.1.1.min.js"></script> 
     <script>
       $(function () {
          
       });
     </script>
   </body>
   
   </html>
   ```

   

   8. 修改servlet

      ```
      protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException { 
      		String action =  request.getParameter("action");
      		CookBookService cookBookService = new CookBookService();  
      		//保存操作
      		if("add".equals(action)) { 
      			String title = request.getParameter("title");
      			String tags = request.getParameter("tags");
      			String content = request.getParameter("content");
      			CookBook cookBook = new CookBook();
      			cookBook.setTitle(title);
      			cookBook.setTags(tags);
      			cookBook.setContent(content);
      			cookBook.setCreateDate(new Date());  
      			int result = 0;
      			try {
      				result = cookBookService.save(cookBook); 
      			} catch (Exception e) {
      				// TODO Auto-generated catch block
      				e.printStackTrace();
      				request.setAttribute("msg", "错误");
      			  	request.getRequestDispatcher("cookBookAdd.jsp").forward(request,response);
      			} 
      			if(result > 0) {
      					response.sendRedirect("index.jsp");
      				}else {
      					request.setAttribute("msg", "错误");
      				  	request.getRequestDispatcher("cookBookAdd.jsp").forward(request,response);
      				} 
      		}
      	}
      ```

      

   

## 图片上传

1. 引入jar包

   ```
   commons-*.jar
   ```

2. 修改cookBookAdd.jsp 添加input

   ```
    <input type="file" name="file" placeholder="请选择图片" />
   ```

3. 修改form表单的内容

   ```html
   <form action="cookBookServlet?action=add" method="post" enctype="multipart/form-data">
   ... ... 
   </form>
   ```

   **上传文件时form标签的method属性必须取值为post**

4. 修改数据库,添加字段 imageurl

   ```
   alert table cookbook  ADD imageurl  varchar(200);
   ```

5. cookbook 添加属性 imageurl

   ```java
   	private String imageurl;
   ```

6. cookbookDao 修改

   ```java
   public int save(CookBook entity) throws Exception { 
   		Connection coon = DBUtil.getConn();
   		String sql = "insert into cookbook(title,tags,content,imageurl,create_time) values(?,?,?,?,?)";
   		int result = runner.update(coon,sql, entity.getTitle(),entity.getTags(),entity.getContent(),entity.getImageurl(),entity.getCreateDate());
   		DBUtil.close(null, null, coon);
   		return result;
   	} 
   ```

   

7. 修改servlet的接收

```java
protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException { 
		String action =  request.getParameter("action");
		CookBookService cookBookService = new CookBookService(); 
		String uploadFileName = ""; //上传的文件名
		//保存操作
		if("add".equals(action)) {
			//请求信息中的内容是否是multipart类型
			boolean isMultipart = ServletFileUpload.isMultipartContent(request); 
			//上传文件的存储路径（服务器文件系统上的绝对文件路径）
			String uploadFilePath = request.getSession().getServletContext().getRealPath("upload/" ); 
			if (isMultipart) {
				FileItemFactory factory = new DiskFileItemFactory();
				ServletFileUpload upload = new ServletFileUpload(factory);
				try {
					//解析form表单中所有文件
					FileItemIterator iter = upload.getItemIterator(request); 
					CookBook cookBook = new CookBook();
					while (iter.hasNext()) {   //依次处理每个文件 
						FileItemStream item = (FileItemStream) iter.next();
					    String fieldName = item.getFieldName();
					    InputStream stream = item.openStream();
						if (item.isFormField()){  //普通表单字段  
							BeanUtils.setProperty(cookBook, fieldName, Streams.asString(stream));
						}else{  //文件表单字段 
							String fileName = item.getName();
							if (fileName != null && !fileName.equals("")) {
								//设置存取的图片文件名  
								uploadFileName = fileName.substring(fileName.lastIndexOf("\\")+1);
								File saveFile = new File(uploadFilePath, fileName);
								FileUtils.copyInputStreamToFile(stream, saveFile);  
								System.out.println(uploadFileName);  
                //	cookBook.setImageurl(uploadFileName); 
							}
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}   
		}
	} 
```



## JSTL

1. 引入jar

   ```
   jstl.jar
   standard.jar
   ```

   

2. 页面使用

   ```jsp
   <c:forEach var="data" items="${datas}">
   		${data.title} <br>
   	</c:forEach>
   ```