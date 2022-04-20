package br.senai.sp.cfp138.hotelguide.rest;



import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


import br.senai.sp.cfp138.hotelguide.anotation.Publica;

import br.senai.sp.cfp138.hotelguide.model.TipoHotel;
import br.senai.sp.cfp138.hotelguide.repository.TipoRepository;

public class TipoHotelRestController {
	@Autowired
	private TipoRepository tiporepo;
	
	@Autowired
	
	@Publica
	@RequestMapping(value="", method = RequestMethod.GET)
	public Iterable<TipoHotel> getHotel(){
		return tiporepo.findAll();
	}
	

	
}
