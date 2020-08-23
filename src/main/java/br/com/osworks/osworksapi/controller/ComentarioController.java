package br.com.osworks.osworksapi.controller;

import static org.springframework.http.HttpStatus.CREATED;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.osworks.osworksapi.domain.exception.EntityNotFoundException;
import br.com.osworks.osworksapi.domain.model.Comentario;
import br.com.osworks.osworksapi.domain.model.OrdemServico;
import br.com.osworks.osworksapi.domain.repository.OrdemServicoRepository;
import br.com.osworks.osworksapi.domain.service.GestaoOrdemServicoService;
import br.com.osworks.osworksapi.model.ComentarioInput;
import br.com.osworks.osworksapi.model.ComentarioModel;

@RestController
@RequestMapping("/ordens-servico/{ordemServicoId}/comentarios")
public class ComentarioController {

	@Autowired
	private GestaoOrdemServicoService gestaoOrdemServico;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private OrdemServicoRepository ordemServicoRepository;
	
	@PostMapping
	@ResponseStatus(CREATED)
	public ComentarioModel adicionar(@PathVariable Long ordemServicoId,
			@Valid @RequestBody ComentarioInput comentarioInput) {
		
		Comentario comentario = gestaoOrdemServico.adicionarComentario(ordemServicoId,
				comentarioInput.getDescricao());
		
		return toModel(comentario);
	}
	
	@GetMapping
	public List<ComentarioModel> listar(@PathVariable Long ordemServicoId){
		OrdemServico ordemServico = ordemServicoRepository.findById(ordemServicoId)
				.orElseThrow(() -> new EntityNotFoundException("Ordem de serviço não encontrada."));
		
		return toCollectionModel(ordemServico.getComentarios());
	}
	private ComentarioModel toModel (Comentario comentario) {
		
		return modelMapper.map(comentario, ComentarioModel.class);
	}
	
	private List<ComentarioModel>toCollectionModel(List<Comentario> comentarios){
		return comentarios.stream()
				.map(comentario -> toModel(comentario))
				.collect(Collectors.toList());
	}

}
