package ucs.poo.trabalho_eventos.models;

import java.util.ArrayList;
import java.util.List;

public class Empresa {

	private String nome;
	private List<Evento> eventos;
	private List<Colaborador> colaboradores;
	
	public Empresa(String nome) {
		this.nome = nome;
		eventos = new ArrayList<>();
		colaboradores = new ArrayList<>();
	}
	
	
	public void cadastrarEvento(Evento evento) {
		eventos.add(evento);
	}
	
	public void cadastrarColaboradores(Colaborador colaborador) {
		colaboradores.add(colaborador);
	}
	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

}
