package com.angelhack.babycaremall.frontend.config

import jakarta.servlet.DispatcherType
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.web.filter.OncePerRequestFilter

class MyFilter : OncePerRequestFilter() {
    override fun doFilterInternal(req: HttpServletRequest, res: HttpServletResponse, chain: FilterChain) {
        val wrapped =
            if (req.dispatcherType != DispatcherType.REQUEST) {  // FORWARD/INCLUDE은 건너뜀
                chain.doFilter(req, res); return
            }
            else MyRequestWrapper(req)   // ✅ 이미 래핑돼 있으면 또 감싸지 않기
        chain.doFilter(wrapped, res)
    }
}