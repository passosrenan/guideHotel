package br.senai.sp.cfp138.hotelguide.util;

import java.io.IOException;
import java.util.UUID;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import com.google.auth.Credentials;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;

public class FirebaseUtil {
	// varivel para guardar as credenciais do firebase

	private Credentials credenciais;

	// variável para acrescentar o storage
	private Storage storage;

	// constante para o nome do bucket
	private final String BUCKET_NAME = "hotelguide-f162b.appspot.com";

	// cosntante para o prefixo da url enderço padrão do google
	private final String PREFIX = "firebasestorage.googleapis.com/v0/b/" + BUCKET_NAME + "/o/";

	// constante para o sulfix da url
	private final String SULFIX = "?alt=media";

	// constante para a url final
	private final String DOWLOAD_URL = PREFIX + "%s" + SULFIX;

	public FirebaseUtil() {
		// buscar as credenciais
		Resource resource = new ClassPathResource("chavefire.json");

		
		try {
			// ler o arquivo para obter a credencial
			credenciais = GoogleCredentials.fromStream(resource.getInputStream());
			
			//acessa o serviço de storage
			storage = StorageOptions.newBuilder().setCredentials(credenciais).build().getService();
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage());
			
		}

	}
	
	public String uploadFile(MultipartFile arquivo) {
		//gera string aleatoria para o nome do arquivo 
		String extensao = UUID.randomUUID().toString() + getExtensao(arquivo.getOriginalFilename());
		return extensao;
	}
	
	private String getExtensao(String extensao) {
		//retorna o trecho da string que vai do ultimo ponto até o fim
		return extensao.substring(extensao.lastIndexOf('.'));
	}
}
