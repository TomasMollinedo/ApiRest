package med.voll.api.infra.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import med.voll.api.domain.usuario.Usuario;
import med.voll.api.domain.usuario.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        //Obtenemos el token del header
        var authHeader = request.getHeader("Authorization");
        if (authHeader!=null){
            var token = authHeader.replace("Bearer ","");
            var subject = tokenService.getSubject(token);
            if (subject!=null){
                //Sabemos que el token es valido. Forzamos la autenticacion
                var usuario = usuarioRepository.findByLogin(subject);
                var authentication = new UsernamePasswordAuthenticationToken(usuario, null,
                        usuario.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        filterChain.doFilter(request,response);
    }
    //en este metodo basicamente tomamos el token del header, si no llega nulo le sacamos el Bearer y nos quedamos solo con el token
    //de ahi nos quedamos con el subject del token y si este no es nulo significa que el usuario es valido
    // ahora encontramos al usuario por el login y dsp le decimos a spring que el login es valido pq verifico que el usuario existe, forzamos un inicio de sesion
    //despues seteamos manualmente la autenticacion de ese inicio de sesion por lo que para los demas request el usuario ya va a estar autenticado
}
