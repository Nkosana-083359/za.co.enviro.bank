package za.co.envirobank.envirobank.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import za.co.envirobank.envirobank.service.impl.CustomUserDetailsServicesImpl;
import za.co.envirobank.envirobank.utils.JwtSecurityUtil;


import java.io.IOException;
import java.util.Objects;

@AllArgsConstructor
@Component
public class JwtSecurityFilterConfig extends OncePerRequestFilter {
    private final CustomUserDetailsServicesImpl customUserDetailsService;
    private final JwtSecurityUtil jwtSecurityUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                                    FilterChain filterChain) throws ServletException, IOException {

        String authHeader = httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION);

        if (Objects.nonNull(authHeader) && authHeader.startsWith("Bearer ")) {
            String authToken = authHeader.substring(7);
            String userName = jwtSecurityUtil.extractUsername(authToken);

            if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {

                UserDetails userDetails = customUserDetailsService.loadUserByUsername(userName);

                if (jwtSecurityUtil.validateToken(authToken, userDetails)) {
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                            new UsernamePasswordAuthenticationToken(userDetails, null,
                                    userDetails.getAuthorities());

                    usernamePasswordAuthenticationToken.
                    setDetails(new WebAuthenticationDetailsSource()
                     .buildDetails(httpServletRequest));
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                }
            }
        } else {
            SecurityContextHolder.getContext().setAuthentication(null);
        }

        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}