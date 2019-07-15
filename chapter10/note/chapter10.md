## 本章目标

1.  掌握分页的显示原理
2.  实现分页功能



## 当前显示数据的优缺点

**优势**

1. 数据能够按照指定格式显示，布局清晰
2. 不受信息数量的限制

**不足**

 当数据量较多，从数据库中读出的时候，当数据上百条或更多时，数据库的压力非常大，可能有假死机的现象

有没有其他的解决方案呢?

**使用分页技术**



## 分页实现的思路

1.  确定每页显示的数据量
2. 确定分页显示所需的总页数
3. 编写SQL语句，实现数据库查询
4. 在页面中展示



## 分页信息封装



```java
package com.foodworld.utils;

public class PageInfo {
	
	private int currentPage = 1;
	private int pageSize = 5;
	
	public PageInfo() {
		
	}
	
	
	public PageInfo(int currentPage, int pageSize) {
		this.currentPage = currentPage;
		this.pageSize = pageSize;
		
	}
	

	public int getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	} 
	

}

```



## dao 

```java
// 分页查询
	public Map<String,Object> listPage(PageInfo pageInfo) throws Exception {
		 Map<String,Object> resultMap = new HashMap<String, Object>();
		List<CookBook> result = null;
		Connection conn = DBUtil.getConn();
		ScalarHandler<Long> scalarHandler = new ScalarHandler();
		String countSql= "select count(1) from cookbook";
		long count = runner.query(conn, countSql, scalarHandler);
		int startIndex = (pageInfo.getCurrentPage() - 1) * pageInfo.getPageSize(); 
		int endIndex = startIndex + pageInfo.getPageSize();
		long pageCount = count % pageInfo.getPageSize() == 0
				? count / pageInfo.getPageSize() 
			   : (count / pageInfo.getPageSize()) + 1;  
		System.out.println("总数据条数:"+count);
		System.out.println("分页数是:"+pageCount);
		String sql = "select * from  cookbook limit ?,?"; 
		result = runner.query(conn, sql, new BeanListHandler<CookBook>(CookBook.class),startIndex,endIndex);
		resultMap.put("pageCount", pageCount);
		resultMap.put("list", result); 
		DBUtil.close(null, null, conn); 
		return resultMap;
	}
```





## servlet

```java
	String page = request.getParameter("page");
			int currentPage = 1; // 当前页
			int pageSize = 5; // 每页显示条数
			if(page !=null && !page.equals("")) {
				currentPage = Integer.parseInt(page);
			}  
			PageInfo pageInfo = new PageInfo(currentPage,pageSize);
			
			try {
				 Map<String,Object> resultMap = cookBookService.listPage(pageInfo); 
				List<CookBook> cookBookList =(List<CookBook>)resultMap.get("list");
				request.setAttribute("datas", cookBookList); 
				request.setAttribute("pageCount", resultMap.get("pageCount")); 
				request.setAttribute("pageInfo", pageInfo); 
				request.getRequestDispatcher("cookBookList.jsp").forward(request,response);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				request.getRequestDispatcher("index.jsp").forward(request,response);
			}  
```



##  jsp 页面展示





```jsp
 <a href="<c:url value='cookBookServlet?action=list&page=1'/>">首页</a>
      	<c:if test="${pageCount != 1 }"> 
  			<a href="<c:url value='cookBookServlet?action=list&page=${pageInfo.currentPage - 1 }'/>"><上一页</a>
  		</c:if>
   		<c:forEach begin="1" end="${pageCount}" step="1" var="num">
  			<c:choose>
  				<c:when test="${num != pageInfo.currentPage}">
  					<a href="<c:url value='cookBookServlet?action=list&page=${num }'/>">${num }</a>
  				</c:when>
  				<c:otherwise> 
  					<a class="activate">${num }</a>
  				</c:otherwise>
  			</c:choose>
  		</c:forEach>
  		<c:if test="${pageInfo.currentPage < pageCount}">
  			<a href="<c:url value='cookBookServlet?action=list&page=${pageInfo.currentPage+1 }'/>">下一页></a>
  			 <span class="nextPage"><a href="<c:url value='cookBookServlet?action=list&page=${pageCount}'/>">尾页</a> </span> 
  		</c:if>  
```



