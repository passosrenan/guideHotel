package br.senai.sp.cfp138.hotelguide.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import br.senai.sp.cfp138.hotelguide.model.Avaliacao;



public interface AvaliacaoRepository extends PagingAndSortingRepository<Avaliacao, Long>{
	
	public List<Avaliacao> findByHotelId(Long idHotel);

}
