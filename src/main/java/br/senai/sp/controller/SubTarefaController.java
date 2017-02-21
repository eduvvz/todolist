package br.senai.sp.controller;

import java.net.URI;

import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.senai.sp.dao.DAOSubTarefa;
import br.senai.sp.model.Subtarefa;

@RestController
public class SubTarefaController {

	@Autowired
	private DAOSubTarefa dao;

	@RequestMapping(value = "/tarefa/{id}/subtarefa", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Subtarefa> criarSubTarefa(@PathVariable("id") Long idTarefa,
			@RequestBody Subtarefa subtarefa) {
		try {

			dao.salvar(idTarefa, subtarefa);
			return ResponseEntity.created(URI.create("/subtarefa/" + subtarefa.getId())).body(subtarefa);
		} catch (ConstraintViolationException e) {
			e.printStackTrace();
			return new ResponseEntity<Subtarefa>(HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Subtarefa>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value = "/subtarefa/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, method = RequestMethod.GET)
	public Subtarefa buscarTarefa(@PathVariable Long id) {
		return dao.buscar(id);
	}

	@RequestMapping(value = "/subtarefa/{idSubtarefa}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Void> marcarFeito(@PathVariable Long idSubtarefa, @RequestBody Subtarefa subtarefa) {
		try {
			boolean feito = subtarefa.isFeita();
			dao.marcarFeito(idSubtarefa, feito);
			HttpHeaders headers = new HttpHeaders();
			headers.setLocation(URI.create("/subtarefa/" + idSubtarefa));
			ResponseEntity<Void> response = new ResponseEntity<Void>(headers, HttpStatus.OK);
			return response;
		} catch (ConstraintViolationException e) {
			e.printStackTrace();
			return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value="/subtarefa/{idSubtarefa}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> excluirSubtarefa(@PathVariable Long idSubtarefa){
		dao.excluir(idSubtarefa);
		return ResponseEntity.noContent().build();
	}

}
