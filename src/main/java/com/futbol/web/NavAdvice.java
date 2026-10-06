package com.futbol.web;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class NavAdvice {
    /** Ruta actual, para resaltar la opción activa del menú. */
    @ModelAttribute("uri")
    public String uri(HttpServletRequest request) { return request.getRequestURI(); }
}
