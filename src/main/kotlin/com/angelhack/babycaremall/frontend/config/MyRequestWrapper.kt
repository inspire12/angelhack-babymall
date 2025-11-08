package com.angelhack.babycaremall.frontend.config

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletRequestWrapper
import jakarta.servlet.http.HttpServletResponse
import org.springframework.web.filter.OncePerRequestFilter

class MyRequestWrapper(req: HttpServletRequest) : HttpServletRequestWrapper(req)
