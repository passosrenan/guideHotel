package br.senai.sp.cfp138.hotelguide.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.naming.Binding;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.senai.sp.cfp138.hotelguide.model.Administrador;
import br.senai.sp.cfp138.hotelguide.repository.AdminRepository;
import br.senai.sp.cfp138.hotelguide.util.HashUtil;

@Controller
public class AdmController {
	@Autowired
	private AdminRepository repoAdm;

	@RequestMapping("formularioADM")
	public String cadastrarCliente() {
		return "adm/formAdm";
	}

	@RequestMapping(value = "salvarAdmin", method = RequestMethod.POST)
	public String salvarAdmin(@Valid Administrador admin, BindingResult result, RedirectAttributes attr) {

		if (result.hasErrors()) {
			attr.addFlashAttribute("mensagemErro", "Verifique os campos...");
			return "redirect:formularioADM";
		}
		//verifica se esta sendo feita uma alteração ao inves de uma inserçao
		boolean alteracao = admin.getId()!= null ? true : false;
		
		//verifica se a senha estáa vazia
		if(admin.getSenha().equals(HashUtil.hash256(""))) {
			if(!alteracao) {
				//extrai a parte do e-mail antes do @
				String parte = admin.getEmail().substring(0,admin.getEmail().indexOf("@"));
				//define a senha do admin
				admin.setSenha(parte);
			}else {
				
			}
		}

		try {

			repoAdm.save(admin);
			attr.addFlashAttribute("mensagemSucesso", "Cadastro Realizado com sucesso!");
			return "redirect:formularioADM";

		} catch (Exception e) {
			attr.addFlashAttribute("mensagemErro", "Houve um erro ao cadastar o Administrador: " + e.getMessage());
		}
		return "redirect:formularioADM";
	}

	// request mapping para listar, informando á página desejada
	@RequestMapping("listarAdmin/{page}")
	public String listar(Model model, @PathVariable("page") int page) {
		// cria um pageable com 6 elementos por página, ordenando os objetos pelo nome,
		// de forma ascendete
		PageRequest pageable = PageRequest.of(page - 1, 6, Sort.by(Sort.Direction.ASC, "nome"));

		// cria a página atual através do repository
		Page<Administrador> pagina = repoAdm.findAll(pageable);

		// descobrir o total de páginas
		int totalPages = pagina.getTotalPages();

		// cria uma lista de inteiros para representar as paginas
		List<Integer> pageNumbers = new ArrayList<Integer>();

		// preencher a lista com as págians
		for (int i = 0; i < totalPages; i++) {
			pageNumbers.add(i + 1);
		}

		// adiciona sa variáveis na model
		model.addAttribute("admins", pagina.getContent());
		model.addAttribute("paginaAtual", page);
		model.addAttribute("totalPaginas", totalPages);
		model.addAttribute("numPaginas", pageNumbers);

		return "adm/lista";
	}

	@RequestMapping("alterarAdmin")
	public String alterarCliente(Model model, Long id) {
		Administrador administrador = repoAdm.findById(id).get();
		model.addAttribute("admins", administrador);
		return "forward:formularioADM";
	}

	@RequestMapping("excluirAdmin")
	public String excluirCliente(Long id) {
		repoAdm.deleteById(id);
		return "redirect:listarAdmin/1";
	}

}
