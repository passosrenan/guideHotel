package br.senai.sp.cfp138.hotelguide.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import br.senai.sp.cfp138.hotelguide.model.Administrador;

public interface AdminRepository extends PagingAndSortingRepository<Administrador, Long>{
	public Administrador findByEmailAndSenha(String email, String senha);
	
	
}
