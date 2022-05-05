package br.senai.sp.cfp138.hotelguide.interceptor;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;

import br.senai.sp.cfp138.hotelguide.anotation.Privado;
import br.senai.sp.cfp138.hotelguide.anotation.Publica;
import br.senai.sp.cfp138.hotelguide.rest.UsuarioHotelController;

@Component
public class AppInterceptor implements HandlerInterceptor {
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// variável para descobrir para onde estão tentando ir
		String uri = request.getRequestURI();
		System.out.println(uri);
		System.out.println(handler.toString());

		// verifica se o handler é um Handler method, o que indica que foi encontrado um
		// método em algum controller para a requisição
		if (handler instanceof HandlerMethod) {
			// Libera o acesso a página inicial
			if (uri.equals("/")) {
				return true;
			}
			if (uri.endsWith("/error")) {
				return true;
			}
			// fazendo casting para HendlerMethod
			HandlerMethod metodo = (HandlerMethod) handler;

			if (uri.startsWith("/api")) {
				// variável para o token
				String token = null;

				// quando for um api
				// se for um método privadi
				if (metodo.getMethodAnnotation(Privado.class) != null) {
					try {
						// obtem o token da request
						token = request.getHeader("Authorization");

						// algoritmo para descriptografar
						Algorithm algoritmo = Algorithm.HMAC256(UsuarioHotelController.SECRET);

						// objeto para verificar o token
						JWTVerifier verifier = JWT.require(algoritmo).withIssuer(UsuarioHotelController.EMISSOR).build();

						// verificando se o token é valido
						DecodedJWT jwt = verifier.verify(token);

						// extrair os dados do payload
						Map<String, Claim> payload = jwt.getClaims();

						System.out.println(payload.get("nome_usuario"));
						return true;
					} catch (Exception e) {
						if (token == null) {
							response.sendError(HttpStatus.UNAUTHORIZED.value(), e.getMessage());
						} else {
							response.sendError(HttpStatus.FORBIDDEN.value(), e.getMessage());
						}
						return false;
					}
				}
				return true;
			} else {

				// se o metodo for publico for publico, libera
				if (metodo.getMethodAnnotation(Publica.class) != null) {
					return true;
				}
				// verifica e exisrte um usuario logado
				if (request.getSession().getAttribute("usuarioLogado") != null) {
					return true;
				} else {
					// redireciona para a página inicial
					response.sendRedirect("/");
					return false;
				}
			}
		}
		return true;
	}

}
