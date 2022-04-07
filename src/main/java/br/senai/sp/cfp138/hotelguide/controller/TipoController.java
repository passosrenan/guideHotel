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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.senai.sp.cfp138.hotelguide.model.Administrador;
import br.senai.sp.cfp138.hotelguide.model.TipoHotel;
import br.senai.sp.cfp138.hotelguide.repository.TipoRepository;

@Controller
public class TipoController {
	
	@Autowired
	private TipoRepository tipo;
	
	@RequestMapping("formularioTIPO")
	public String cadastratTipo() {
		return "tipoHotel/formTipo";
	}
	@RequestMapping(value = "salvarTipo", method = RequestMethod.POST)
	public String salvarAdmin(@Valid TipoHotel tipos) {
		tipo.save(tipos);
		return "redirect:listarTipos/1";
	}
	
	@RequestMapping("listarTipos/{totalElements}/{page}")
	public String listar(Model model, @PathVariable("page") int page, @PathVariable("totalElements") int totalElements) {
		// cria um pageable com 6 elementos por página, ordenando os objetos pelo nome,
		// de forma ascendete
		PageRequest pageable = PageRequest.of(page - 1, totalElements, Sort.by(Sort.Direction.ASC, "nome"));

		// cria a página atual através do repository
		Page<TipoHotel> pagina = tipo.findAll(pageable);

		// descobrir o total de páginas
		int totalPages = pagina.getTotalPages();

		// cria uma lista de inteiros para representar as paginas
		List<Integer> pageNumbers = new ArrayList<Integer>();

		// preencher a lista com as págians
		for (int i = 0; i < totalPages; i++) {
			pageNumbers.add(i + 1);
		}

		// adiciona sa variáveis na model
		model.addAttribute("tipos", pagina.getContent());
		model.addAttribute("paginaAtual", page);
		model.addAttribute("totalPaginas", totalPages);
		model.addAttribute("totalElements", totalElements);
		model.addAttribute("numPaginas", pageNumbers);

		return "tipoHotel/lista";
	}
	
	@RequestMapping("alterarTipos")
	public String alterarCliente(Model model, Long id) {
		TipoHotel tipos = tipo.findById(id).get();
		model.addAttribute("tipos", tipos);
		return "forward:formularioTIPO";
	}
	
	@RequestMapping("excluirTipos")
	public String excluirCliente(Long id) {
		tipo.deleteById(id);
		return "redirect:listarTipos/1";
	}
	
	@RequestMapping("buscarPelaPalavra")
	public String buscarPorPalavra(String palavraChave, Model model) {
		model.addAttribute("tipos", tipo.buscarKeyWord("%" + palavraChave + "%"));
		return "tipoHotel/lista";
	}


}
