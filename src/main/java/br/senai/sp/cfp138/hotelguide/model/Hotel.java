package br.senai.sp.cfp138.hotelguide.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.Data;

@Entity
@Data
public class Hotel {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nome;
	@Column(columnDefinition = "TEXT")
	private String descricao;
	private String cep;
	private String endereco;
	private String numero;
	private String bairro;
	private String cidade;
	private String uf;
	private boolean estacionamento;
	@Column(columnDefinition = "TEXT")
	private String fotos;
	@ManyToOne
	private TipoHotel tipo;
	private String complemento;
	private boolean wifi;
	private String site;
	private String telefone;
	private String redesSociais;
	@OneToMany(mappedBy = "hotel")
	private List<Avaliacao> avaliacoes;
	public String[] verFotos() {
		return this.fotos.split(";");
	}
	
}
