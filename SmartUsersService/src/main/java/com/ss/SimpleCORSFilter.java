/**SmartSoftware User - Service */
/**
 * Description: SimpleCORSFilter
 * Name of Project: SmartSoftware
 * Created on: Feb 10, 2020
 * Modified on: Feb 10, 2020 11:19:38 AM
 * @author Juned
 * Version: 
 */
package com.ss;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;


@Component
public class SimpleCORSFilter implements Filter {
	private Logger log = (Logger) LoggerFactory.getLogger(SimpleCORSFilter.class);

	private static final Set<String> EMPTY = new HashSet<>();

	String allowedOriginsString = "*";

	private Set<String> parseAllowedOrigins(String allowedOriginsString) {
		if (!StringUtils.isEmpty(allowedOriginsString)) {
			return new HashSet<>(Arrays.asList(allowedOriginsString.split(",")));
		} else {
			return EMPTY;
		}
	}

	/**
	 * @param allowedOriginsString
	 * @return
	 */
	public Set<String> getAllowedOrigins(String allowedOriginsString) {
		return parseAllowedOrigins(allowedOriginsString);
	}

	/* (non-Javadoc)
	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 */
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		log.info("Inside cross origin filter");
			HttpServletResponse response = (HttpServletResponse) res;
			response.setHeader("Access-Control-Allow-Origin", "*");
			response.setHeader("Access-Control-Allow-Methods", "POST, PUT, GET, OPTIONS,DELETE");
			response.setHeader("Access-Control-Max-Age", "3600");
			response.setHeader("Access-Control-Allow-Headers", "content-type, session, userid, langid, tenantid");
			response.setHeader("Access-Control-Allow-Credentials", "true");
            response.setHeader("X-Frame-Options", "ALLOW-FROM");
		 
	 
		chain.doFilter(req, response);
	}

	/* (non-Javadoc)
	 * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
	 */
	public void init(FilterConfig filterConfig) {
		// to do nothing
	}

	/* (non-Javadoc)
	 * @see javax.servlet.Filter#destroy()
	 */
	public void destroy() {
		// to do nothing
	}

}
