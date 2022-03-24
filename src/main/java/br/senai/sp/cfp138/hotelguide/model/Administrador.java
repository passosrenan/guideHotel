package br.senai.sp.cfp138.hotelguide.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import br.senai.sp.cfp138.hotelguide.util.HashUtil;
import lombok.Data;

@Data
@Entity
public class Administrador {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotEmpty
	private String nome;
	@Column(unique = true)
	@Email
	private String email;
	//garante que o campo não fique vazio 
	@NotEmpty
	private String senha;
	
	//método para setar a senha aplicando hash
	public void setSenha(String senha) {
		//aplica o hash e seta a senha no objeto
		this.senha = HashUtil.hash256(senha);
	}

}

