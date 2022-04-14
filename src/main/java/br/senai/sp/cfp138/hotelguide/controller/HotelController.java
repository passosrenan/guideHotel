package br.senai.sp.cfp138.hotelguide.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.elasticsearch.RestClientBuilderCustomizer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import br.senai.sp.cfp138.hotelguide.model.Hotel;
import br.senai.sp.cfp138.hotelguide.model.TipoHotel;
import br.senai.sp.cfp138.hotelguide.repository.HotelRepository;
import br.senai.sp.cfp138.hotelguide.repository.TipoRepository;
import br.senai.sp.cfp138.hotelguide.util.FirebaseUtil;

@Controller
public class HotelController {

	@Autowired
	TipoRepository reptipo;

	@Autowired
	HotelRepository hotelrepo;
	
	@Autowired
	FirebaseUtil fire;
	

	@RequestMapping("formHotel")
	public String form(Model model) {
		model.addAttribute("tipos", reptipo.findAllByOrderByNomeAsc());
		return "hotel/form";
	}

	@RequestMapping("salvarHotel")
	public String salvarAdmin(Hotel hotel, @RequestParam("fileFotos") MultipartFile[] fileFotos) {
		//string para url das fotos
		String fotos = hotel.getFotos();

		// percorre cada arquivo que foi submetido no formulario
		for (MultipartFile multipartFile : fileFotos) {
			if (multipartFile.getOriginalFilename().isEmpty()) {
				// vai para o próximo arquivo
				continue;
			}
			//faz upload para a nuvem e obtem a url gerada
			try {
				fotos += fire.uploadFile(multipartFile)+";";
			} catch (IOException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}
		hotel.setFotos(fotos);
		hotelrepo.save(hotel);
		return "redirect:listarHoteis/1";
	}

	@RequestMapping("listarHoteis/{page}")
	public String listarHoteis(Model model, @PathVariable("page") int page ) {
		// cria um pageable com 6 elementos por página, ordenando os objetos pelo nome,
		// de forma ascendete
		PageRequest pageable = PageRequest.of(page - 1, 6, Sort.by(Sort.Direction.ASC, "nome"));

		// cria a página atual através do repository
		Page<Hotel> pagina = hotelrepo.findAll(pageable);

		// descobrir o total de páginas
		int totalPages = pagina.getTotalPages();

		// cria uma lista de inteiros para representar as paginas
		List<Integer> pageNumbers = new ArrayList<Integer>();

		// preencher a lista com as págians
		for (int i = 0; i < totalPages; i++) {
			pageNumbers.add(i + 1);
		}

		// adiciona sa variáveis na model
		model.addAttribute("hoteis", pagina.getContent());
		model.addAttribute("paginaAtual", page);
		model.addAttribute("totalPaginas", totalPages);
		model.addAttribute("numPaginas", pageNumbers);

		return "hotel/lista";
	}

	@RequestMapping("alterarHotel")
	public String alterarCliente(Model model, Long id) {
		Hotel hoteis = hotelrepo.findById(id).get();
		model.addAttribute("hoteis", hoteis);
		return "forward:formHotel";
	}

	@RequestMapping("excluirHotel")
	public String excluirHotel(Long idHotel) {
		Hotel hotel = hotelrepo.findById(idHotel).get();
		if(hotel.getFotos().length() > 0) {
			for(String foto: hotel.verFotos()) {
				fire.deletar(foto);
			}
		}
		
		return "redirect:listarHoteis/1";
	}
	
	@RequestMapping("excluirFoto")
	public String excluirFoto(Long idHotel, int numFoto, Model model) {
		// busca o restaurante no bd
		Hotel hotel = hotelrepo.findById(idHotel).get();
		//pega a string da foto a ser excluída
		
		String fotoUrl = hotel.verFotos()[numFoto];
		
		//excluir do firebase
		fire.deletar(fotoUrl);
		
		hotel.setFotos(hotel.getFotos().replace(fotoUrl+";", ""));
		
		//salva no bd o objeto hotel
		hotelrepo.save(hotel);
		
		//adiciona o hotel na model
		model.addAttribute("hoteis", hotel);
		
		return "forward:/formHotel";

	}

}