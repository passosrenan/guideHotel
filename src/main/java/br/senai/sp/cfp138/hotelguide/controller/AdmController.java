package br.senai.sp.cfp138.hotelguide.controller;

import javax.naming.Binding;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.senai.sp.cfp138.hotelguide.model.Administrador;
import br.senai.sp.cfp138.hotelguide.repository.AdminRepository;

@Controller
public class AdmController {
	@Autowired
	private AdminRepository repoAdm;
	
	@RequestMapping("formularioADM")
	public String cadastrarCliente() {
		return "adm/formAdm";
	}
	
	@RequestMapping(value = "salvarAdmin", method = RequestMethod.POST)
	public String salvarAdmin(@Valid Administrador admin, BindingResult result, RedirectAttributes attr ) {
		
		if(result.hasErrors()) {
			attr.addFlashAttribute("mensagemErro", "Verifique os campos...");
			return "redirect:formularioADM";	
		}
		
		try {
			
			repoAdm.save(admin);
			attr.addFlashAttribute("mensagemSucesso", "Cadastro Realizado com sucesso!");
			return "redirect:formularioADM";
			
		} catch (Exception e) {
			attr.addFlashAttribute("mensagemErro", "Houve um erro ao cadastar o Administrador: "+e.getMessage());
		}
		return "redirect:formularioADM";
		
		
	}
	
		

	
	
	
	
	
	
	
	
}
