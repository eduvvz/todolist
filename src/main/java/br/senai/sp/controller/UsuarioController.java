package br.senai.sp.controller;

import java.net.URI;

import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.senai.sp.dao.DAOUsuario;
import br.senai.sp.model.TokenJWT;
import br.senai.sp.model.Usuario;

@RestController
public class UsuarioController {

	@Autowired
	private DAOUsuario dao;

	@RequestMapping(value = "/usuario", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Usuario> criarTarefa(@RequestBody Usuario usuario) {
		try {
			dao.inserir(usuario);
			return ResponseEntity.created(URI.create("/usuario/" + usuario.getId())).body(usuario);
		} catch (ConstraintViolationException e) {
			e.printStackTrace();
			return new ResponseEntity<Usuario>(HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Usuario>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<TokenJWT> login(Usuario usuario) {
		
		try {
			usuario = dao.logar(usuario);
			if (usuario != null) {
				
			} else {
				return new ResponseEntity<TokenJWT>(HttpStatus.UNAUTHORIZED);
			}
		} catch (Exception e) {
			// TODO: handle exception
			return new ResponseEntity<TokenJWT>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return null;
	}

}
