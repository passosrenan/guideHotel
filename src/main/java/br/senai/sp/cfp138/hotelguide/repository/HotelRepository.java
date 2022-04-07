package br.senai.sp.cfp138.hotelguide.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import br.senai.sp.cfp138.hotelguide.model.Hotel;

public interface HotelRepository extends PagingAndSortingRepository<Hotel, Long>{

}
