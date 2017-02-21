package br.senai.sp.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import br.senai.sp.model.Usuario;

@Repository
public class DAOUsuario{

	@PersistenceContext
	private EntityManager manager;

	@Transactional
	public void inserir(Usuario objeto) {
		// TODO Auto-generated method stub
		manager.persist(objeto);
	}

	public Usuario logar(Usuario usuario) {
		TypedQuery<Usuario> query = manager
				.createQuery("select u from Usuario u where u.email = :email and u.senha = :senha", Usuario.class);
		query.setParameter("email", usuario.getEmail());
		query.setParameter("senha", usuario.getSenha());
		try {
			return query.getSingleResult();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
