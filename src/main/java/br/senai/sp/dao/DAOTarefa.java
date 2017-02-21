package br.senai.sp.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import br.senai.sp.model.Tarefa;

@Repository
public class DAOTarefa implements InterfaceDAO<Tarefa>{

	@PersistenceContext
	private EntityManager manager;
	
	@Transactional
	public void inserir(Tarefa objeto) {
		// TODO Auto-generated method stub
		manager.persist(objeto);
		
	}

	public Tarefa buscar(Long id) {
		// TODO Auto-generated method stub
		return manager.find(Tarefa.class, id);
	}

	public List<Tarefa> listar() {
		// TODO Auto-generated method stub
		
		TypedQuery<Tarefa> query = manager.createQuery("SELECT t FROM Tarefa t",
				Tarefa.class);
		
		return query.getResultList();
	}

	@Transactional
	public void excluir(Long id) {
		// TODO Auto-generated method stub
		Tarefa tarefa = buscar(id);
		manager.remove(tarefa);
	}

}
