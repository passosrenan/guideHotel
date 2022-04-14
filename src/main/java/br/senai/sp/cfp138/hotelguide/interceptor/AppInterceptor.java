package br.senai.sp.cfp138.hotelguide.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import br.senai.sp.cfp138.hotelguide.anotation.Publica;

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
				return true;
			}
		}
		return true;
	}

}
