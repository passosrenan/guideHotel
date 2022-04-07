package br.senai.sp.cfp138.hotelguide.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;


import br.senai.sp.cfp138.hotelguide.model.TipoHotel;

public interface TipoRepository extends PagingAndSortingRepository<TipoHotel, Long> {

	@Query("SELECT e FROM TipoHotel e WHERE e.palavraChave LIKE %:p%")
	public List<TipoHotel> buscarKeyWord(@Param("p") String palavraChave);
	
	public List<TipoHotel> findAllByOrderByNomeAsc();
}
