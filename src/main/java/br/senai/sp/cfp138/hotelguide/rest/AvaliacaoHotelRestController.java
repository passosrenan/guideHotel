package br.senai.sp.cfp138.hotelguide.rest;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.senai.sp.cfp138.hotelguide.anotation.Privado;
import br.senai.sp.cfp138.hotelguide.anotation.Publica;
import br.senai.sp.cfp138.hotelguide.model.Avaliacao;

import br.senai.sp.cfp138.hotelguide.repository.AvaliacaoRepository;

@RestController
@RequestMapping("/api/avaliacao")
public class AvaliacaoHotelRestController {
	@Autowired
	private AvaliacaoRepository repository;
	
	@Privado
	@RequestMapping(value="", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Avaliacao> criarAvaliacao(@RequestBody Avaliacao avaliacao){
		repository.save(avaliacao);
		return ResponseEntity.created(URI.create("/avaliacao/"+avaliacao.getId())).body(avaliacao);
	}
	
	@Publica
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public Avaliacao getAvaliacao(@PathVariable("id")Long id) {
		return repository.findById(id).get();
	}
	
	@Publica
	@RequestMapping(value = "/hotel/{idHotel}", method = RequestMethod.GET)
	public List<Avaliacao> findByHotel(@PathVariable("idResta") Long idHotel) {
		return repository.findByHotelId(idHotel);
	}
}
