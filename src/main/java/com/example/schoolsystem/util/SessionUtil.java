package com.example.schoolsystem.util;

import org.springframework.web.bind.annotation.RequestHeader;

public class SessionUtil {

    public static String extractSessionId(String cookieSessionId, String headerSessionId) {
        if (cookieSessionId != null && !cookieSessionId.isEmpty()) {
            return cookieSessionId;
        }
        if (headerSessionId != null && !headerSessionId.isEmpty()) {
            System.out.println(headerSessionId+ " this is a header sessionId");
            return headerSessionId;
        }
        return null;
    }
}