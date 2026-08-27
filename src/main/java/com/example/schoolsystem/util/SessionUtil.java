package com.example.schoolsystem.util;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

public class SessionUtil {

    public static String getSessionId(HttpServletRequest request) {
        String cookieSessionId = getCookieValue(request, "sessionId");
        if (cookieSessionId != null && !cookieSessionId.isEmpty()) {
            return cookieSessionId;
        }
        return request.getHeader("X-Session-Id");
    }

    public static Long getSessionIdAsLong(HttpServletRequest request) {
        String sessionId = getSessionId(request);
        return sessionId != null ? Long.parseLong(sessionId) : null;
    }

    private static String getCookieValue(HttpServletRequest request, String name) {
        if (request.getCookies() == null) {
            return null;
        }
        for (Cookie cookie : request.getCookies()) {
            if (cookie != null && name.equals(cookie.getName())) {
                return cookie.getValue();
            }
        }
        return null;
    }
}
