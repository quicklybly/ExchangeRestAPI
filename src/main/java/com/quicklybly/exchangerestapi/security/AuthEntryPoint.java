package com.quicklybly.exchangerestapi.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.quicklybly.exchangerestapi.dto.ErrorDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Objects;

@Component
public class AuthEntryPoint implements AuthenticationEntryPoint {

    private static final ObjectMapper JSON_MAPPER = new ObjectMapper();
    private static final XmlMapper XML_MAPPER = new XmlMapper();

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        String acceptType = request.getHeader("Accept");

        String xmlType = "application/xml";
        if (Objects.nonNull(acceptType) && xmlType.equals(acceptType)) {
            response.setContentType(xmlType);
            XML_MAPPER.writer().writeValue(response.getOutputStream(), new ErrorDTO(authException.getMessage()));
        } else {
            String jsonType = "application/xml";
            response.setContentType(jsonType);
            JSON_MAPPER.writer().writeValue(response.getOutputStream(), new ErrorDTO(authException.getMessage()));
        }
    }
}
