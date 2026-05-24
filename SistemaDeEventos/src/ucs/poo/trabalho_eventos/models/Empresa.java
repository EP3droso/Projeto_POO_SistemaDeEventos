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
		evento.eventoSendoCadastrado(eventos.indexOf(evento));
	}
	
	public void cadastrarColaboradores(Colaborador colaborador) {
		colaboradores.add(colaborador);
	}
	
	
	public void listarEventos() {
		System.out.println("Os eventos cadastrados são:");
		for(Evento evento : eventos){
			System.out.println("Evento " + evento.getNome() + "  ID: " + evento.getId());
		}
	}
	
	public Evento getEvento(int id){
		return eventos.get(id);
	}
	
	/*
	public Colaborador getColaborador(int id) {
		return colaboradores.get(id);
	}
	*/
	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

}
