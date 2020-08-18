package br.com.osworks.osworksapi.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.osworks.osworksapi.domain.model.Cliente;

@RestController
public class ClienteController {

	@GetMapping("/clientes")
	public List<Cliente> listar() {
		
		Cliente cliente1 = new Cliente();
		cliente1.setId(1L);
		cliente1.setNome("Maria Cintia");
		cliente1.setTelefone("11999299141");
		cliente1.setEmail("maria@gmail.com");
		
		Cliente cliente2 = new Cliente();
		cliente2.setId(2L);
		cliente2.setNome("Jose");
		cliente2.setTelefone("11999299141");
		cliente2.setEmail("jose@gmail.com");
		
		return Arrays.asList(cliente1, cliente2);
	}
}
