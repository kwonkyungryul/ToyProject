package com.example.toyproject.util;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class FilterResponseUtil {
    public static void result(HttpStatus status, HttpServletRequest request, HttpServletResponse resp, Exception e) throws IOException {
        resp.setStatus(status.value());
        resp.setContentType(MediaType.APPLICATION_JSON_VALUE);
        resp.getWriter().println(
                "{\n" +
                        "    \"type\": \"about:blank\",\n" +
                        "    \"title\": \""+ status.getReasonPhrase() +"\",\n" +
                        "    \"status\": "+ resp.getStatus() +",\n" +
                        "    \"detail\": \"" + e.getMessage() + "\",\n" +
                        "    \"instance\": \"" + request.getServletPath() + "\"\n"
                        + "}"
        );
    }
}
