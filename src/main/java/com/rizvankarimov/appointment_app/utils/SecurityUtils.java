package com.rizvankarimov.appointment_app.utils;


import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.util.StringUtils;

/**
 * @author sa
 * @date 23.07.2023
 * @time 14:00
 */
public class SecurityUtils
{
    public static final String ROLE_PREFIX = "ROLE_";
    public static final String AUTH_HEADER = HttpHeaders.AUTHORIZATION;
    public static final String AUTH_TOKEN_HEADER = "Bearer";
    public static final String AUTH_TOKEN_PREFIX = AUTH_TOKEN_HEADER + " ";

    public static SimpleGrantedAuthority convertToAuthority(String role)
    {
        String formattedRole = role.startsWith(ROLE_PREFIX) ? role : ROLE_PREFIX + role;

        return new SimpleGrantedAuthority(formattedRole);
    }

    public static String extractAuthTokenFromRequest(HttpServletRequest request)
    {
        String bearerToken = request.getHeader(AUTH_HEADER);

        if (StringUtils.hasLength(bearerToken) && bearerToken.startsWith(AUTH_TOKEN_PREFIX))
        {
            return bearerToken.substring(7);
        }
        return null;
    }
}