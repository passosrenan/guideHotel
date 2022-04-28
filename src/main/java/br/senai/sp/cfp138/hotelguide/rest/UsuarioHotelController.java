package br.senai.sp.cfp138.hotelguide.rest;

import java.net.URI;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import br.senai.sp.cfp138.hotelguide.anotation.Privado;
import br.senai.sp.cfp138.hotelguide.anotation.Publica;
import br.senai.sp.cfp138.hotelguide.model.Erro;
import br.senai.sp.cfp138.hotelguide.model.Usuario;
import br.senai.sp.cfp138.hotelguide.repository.UsuarioRepository;

@RestController
@RequestMapping("/api/usuario")
public class UsuarioHotelController {
	@Autowired
	private UsuarioRepository usurepo;

	// criando usuario consumindo jason e produzindo jason
	@Publica
	@RequestMapping(value = "", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)

	public ResponseEntity<Object> criarUsuario(@RequestBody Usuario usuario) {

		try {
			// salvar usuario no bd
			usurepo.save(usuario);
			// retorna o código 201, com a url para a acesso no location e o usuário
			// inserido
			return ResponseEntity.created(URI.create("/" + usuario.getId())).body(usuario);

		} catch (DataIntegrityViolationException e) {
			Erro erro = new Erro();
			erro.setStatusCode(500);
			erro.setMensagem("Erro de Constraint: Registro duplicado");
			erro.setException(e.getClass().getName());
			return new ResponseEntity<Object>(erro, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception e) {
			Erro erro = new Erro();
			erro.setStatusCode(500);
			erro.setMensagem("Erro:" + e.getMessage());
			erro.setException(e.getClass().getName());
			return new ResponseEntity<Object>(erro, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Privado
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> atualizarUser(@RequestBody Usuario usuario, @PathVariable("id") Long id) {
		// valida o ID
		if (id != usuario.getId()) {
			throw new RuntimeException("ID inválido");
		}
		usurepo.save(usuario);
		// criar um cabeçalho HTTP
		HttpHeaders header = new HttpHeaders();
		header.setLocation(URI.create("/api/usuario"));
		return new ResponseEntity<Void>(header, HttpStatus.OK);
	}
	
	@Privado
	@RequestMapping(value="/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> excluirUsuario(@PathVariable("id")Long idUsuario){
		usurepo.deleteById(idUsuario);
		return ResponseEntity.noContent().build();
	}

	@Privado
	@RequestMapping(value = "/{idUsuario}", method = RequestMethod.GET)
	public ResponseEntity<Usuario> findHotel(@PathVariable("idUsuario") Long idUsuario) {
		// busca o restaurante
		Optional<Usuario> usuario = usurepo.findById(idUsuario);
		if (usuario.isPresent()) {
			return ResponseEntity.ok(usuario.get());

		} else {
			return ResponseEntity.notFound().build();
		}
	}

}
