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

	// 初始化设置字符集
	public void init(FilterConfig filterConfig) throws ServletException {  
		String encode = filterConfig.getInitParameter("encode");
		if (this.encode == null) {
			this.encode = encode;
		}
	}

}