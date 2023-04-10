package next.sh.driving_school.rest.provided.facade;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import next.sh.driving_school.exception.BadRequestException;
import next.sh.driving_school.models.domain.ResponseObject;
import next.sh.driving_school.models.entity.Eleve;
import next.sh.driving_school.models.entity.User;
import next.sh.driving_school.rest.converter.EleveConverter;
import next.sh.driving_school.rest.provided.vo.EleveVo;
import next.sh.driving_school.security.util.JwtUtil;
import next.sh.driving_school.service.EleveService;
import next.sh.driving_school.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("${api.endpoint}/auth")
public class authRest {
    @Autowired
    EleveConverter eleveConverter;
    @Autowired
    EleveService eleveService;
    @Autowired
    UserDetailsServiceImpl userService;
    @RequestMapping(method = RequestMethod.GET, value = "/principal")
    public ResponseEntity<ResponseObject<?>> getPrincipal(Principal principal) {
        User user = this.userService.findByUsername(principal.getName()).orElse(null);
        ResponseObject<User> responseObject = new ResponseObject<>(true,
                "get principal!!", user);
        return new ResponseEntity<>(responseObject, HttpStatus.OK);

    }

    @GetMapping("/refresh")
    public void refreshJeton(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String auth = request.getHeader(JwtUtil.AUTH_HEADER);
        if (auth != null && auth.startsWith(JwtUtil.BEARER)) {
            try {
                String jwt = auth.substring(JwtUtil.BEARER.length());
                Algorithm algorithm = Algorithm.HMAC256(JwtUtil.SECRET);
                JWTVerifier jwtVerifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = jwtVerifier.verify(jwt);
                String username = decodedJWT.getSubject();
                User user = this.userService.findByUsername(username).orElse(null);

                if (user == null)
                    throw new RuntimeException("Refresh token required");

                List<String> authority = new ArrayList<>();
                authority.add(user.getRole());
                String jwtSuccessToken = JWT.create()
                        .withSubject(user.getUsername())
                        .withExpiresAt(new Date(System.currentTimeMillis() + JwtUtil.EXPIRED_JETON))
                        .withIssuer(request.getRequestURL().toString())
                        .withClaim(JwtUtil.AUTHORITIES, authority)
                        .sign(algorithm);
                String refreshJeton = JWT.create()
                        .withSubject(user.getUsername())
                        .withExpiresAt(new Date(System.currentTimeMillis() + JwtUtil.EXPIRED_JETON))
                        .withIssuer(request.getRequestURL().toString())
                        .sign(algorithm);
                Map<String, String> jeton = new HashMap<>();

                jeton.put("successJeton", jwtSuccessToken);
                jeton.put("refreshJeton", refreshJeton);
                response.setContentType("application/json");
                new ObjectMapper().writeValue(response.getOutputStream(), jeton);
            } catch (Exception e) {
                response.setHeader("error-message", e.getMessage());
                response.sendError(HttpServletResponse.SC_FORBIDDEN);
            }

        } else {
            throw new RuntimeException("Refresh token required");
        }
    }

}
