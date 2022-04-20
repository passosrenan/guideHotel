package br.senai.sp.cfp138.hotelguide.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import br.senai.sp.cfp138.hotelguide.model.Hotel;

public interface HotelRepository extends PagingAndSortingRepository<Hotel, Long>{
	
	public List<Hotel> findByTipoId(Long idTipo);
	
	public List<Hotel> findByWifi(boolean wifi);
	
	public List<Hotel> findByUf(String uf);
}
