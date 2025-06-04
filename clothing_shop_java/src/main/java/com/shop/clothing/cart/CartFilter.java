package com.shop.clothing.cart;

import com.shop.clothing.cart.query.getMyCart.GetMyCartQuery;
import com.shop.clothing.common.Cqrs.ISender;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@AllArgsConstructor
public class CartFilter implements Filter {
    private final ISender _sender;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        HttpSession session = httpRequest.getSession();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.getPrincipal() != null && !auth.getPrincipal().equals("anonymousUser")

        ) {

            var cartItems = _sender.send(new GetMyCartQuery());

            // Lấy cart items của user từ database


            // Lưu vào session
            session.setAttribute("totalCartItems", cartItems.orThrow().size());
        }
        filterChain.doFilter(servletRequest, servletResponse);

    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
