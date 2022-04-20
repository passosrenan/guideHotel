package br.senai.sp.cfp138.hotelguide.rest;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.senai.sp.cfp138.hotelguide.anotation.Publica;
import br.senai.sp.cfp138.hotelguide.model.Hotel;
import br.senai.sp.cfp138.hotelguide.repository.HotelRepository;

@RequestMapping("/api/hotel")
@RestController
public class HotelRestController {
	@Autowired
	private HotelRepository hotelrepo;

	@Publica
	@RequestMapping(value = "", method = RequestMethod.GET)
	public Iterable<Hotel> getHotel() {
		return hotelrepo.findAll();
	}

	@Publica
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Hotel> findHotel(@PathVariable("id") Long idHotel) {
		// busca o restaurante
		Optional<Hotel> hotel = hotelrepo.findById(idHotel);
		if (hotel.isPresent()) {
			return ResponseEntity.ok(hotel.get());

		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@Publica
	@RequestMapping(value = "/tipo/{idTipo}", method = RequestMethod.GET)
	public Iterable<Hotel> findByTipo(@PathVariable("idTipo") Long idTipo) {
		return hotelrepo.findByTipoId(idTipo);
	}

	@Publica
	@RequestMapping(value = "/internet/{wifi}", method = RequestMethod.GET)
	public Iterable<Hotel> findByEstacionamento(@PathVariable("wifi") boolean wifi) {
		return hotelrepo.findByWifi(wifi);
	}
	
	@Publica
	@RequestMapping(value = "/estado/{uf}", method = RequestMethod.GET)
	public Iterable<Hotel> findByEstacionamento(@PathVariable("uf") String uf) {
		return hotelrepo.findByUf(uf);
	}
	
	
}
