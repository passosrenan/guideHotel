package br.senai.sp.cfp138.hotelguide.controller;


import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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

@Controller
public class HotelController {
		
	@Autowired
	private TipoRepository reptipo;
	
	@Autowired
	private HotelRepository hotelrepo;
	
	@RequestMapping("formHotel")
	public String form(Model model) {
		model.addAttribute("tipos", reptipo.findAllByOrderByNomeAsc());
		return "hotel/form";
	}


	@RequestMapping("salvarHotel")
		public String salvarAdmin(Hotel hotel, @RequestParam("fileFotos") MultipartFile[] fileFotos) {
		System.out.println(fileFotos.length);
		//hotelrepo.save(hotel);
		return "redirect:listarHoteis/1";
}
	
	
	@RequestMapping("listarHoteis/{totalElements}/{page}")
	public String listarHoteis(Model model, @PathVariable("page") int page, @PathVariable("totalElements") int totalElements) {
		// cria um pageable com 6 elementos por página, ordenando os objetos pelo nome,
		// de forma ascendete
		PageRequest pageable = PageRequest.of(page - 1, totalElements, Sort.by(Sort.Direction.ASC, "nome"));

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
		model.addAttribute("totalElements", totalElements);
		model.addAttribute("numPaginas", pageNumbers);

		return "hotel/lista";
	}
	
	
	@RequestMapping("alterarHotel")
	public String alterarCliente(Model model, Long id) {
		Hotel hoteis = hotelrepo.findById(id).get();
		model.addAttribute("hoteis", hoteis );
		return "forward:formHotel";
	}
	
	@RequestMapping("excluirHotel")
	public String excluirHotel(Long id) {
		hotelrepo.deleteById(id);
		return "redirect:listarHoteis/1";
	}
	
	
	
	
	
}