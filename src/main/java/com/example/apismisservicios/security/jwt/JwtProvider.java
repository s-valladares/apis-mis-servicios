package com.example.apismisservicios.security.jwt;

import com.example.apismisservicios.negocios.models.entities.Persona;
import com.example.apismisservicios.negocios.services.IPersonService;
import com.example.apismisservicios.security.models.entities.UsuarioPrincipal;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtProvider {

    private final static Logger logger = LoggerFactory.getLogger(JwtProvider.class);

    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.expiration}")
    private int expiration;
    /*
    private final IPersonService personService;
   @Autowired
    public JwtProvider(IPersonService personService){
        this.personService = personService;
    }
*/
    public String generateToken(Authentication authentication){
        UsuarioPrincipal usuarioPrincipal = (UsuarioPrincipal) authentication.getPrincipal();
        Map<String, Object> info = new HashMap<>();

        /*
        Persona persona = personService.getId(Long.parseLong(usuarioPrincipal.getPersona()));

        info.put("usuario",usuarioPrincipal.getNombreUsuario());
        info.put("nombre_persona", persona.getNombres());
        info.put("apellido_persona", persona.getApellidos());
        */
        return Jwts.builder()
                .setClaims(info)
                .setSubject(usuarioPrincipal.getNombreUsuario())
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + expiration * 1000L))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    public String getNombreUsuarioFromToken(String token){
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validateToken(String token){
        try{
            Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
            return true;
        }catch (MalformedJwtException e){
            logger.error("Token mal formado");
        }catch (UnsupportedJwtException e){
            logger.error("Token no spoportado");
        }catch (ExpiredJwtException e){
            logger.error("Token expirado");
        }catch (IllegalArgumentException e){
            logger.error("Token vacío");
        }catch (SignatureException e){
            logger.error("Fallo en la firma");
        }

        return false;

    }
}
