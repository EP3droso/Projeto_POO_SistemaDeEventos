package ucs.poo.trabalho_eventos.models;

import java.util.ArrayList;
import java.util.List;

public class Empresa {

	private String nome;
	private List<Evento> eventos;
	private List<Colaborador> colaboradores;
	
	public Empresa(String nome) {
		this.nome = nome;
		this.eventos = new ArrayList<>();
		this.colaboradores = new ArrayList<>();
	}
	
	public boolean eventosIsEmpty(){
		return this.eventos.isEmpty();
	}
	
	
	public void cadastrarEvento(Evento evento) {
		this.eventos.add(evento);
		evento.eventoSendoCadastrado(this.eventos.indexOf(evento));
	}
	
	public void cadastrarColaboradores(Colaborador colaborador) {
		this.colaboradores.add(colaborador);
	}
	
	
	public void listarEventos() {
		System.out.println("Os eventos cadastrados são:");
		for(Evento evento : this.eventos){
			System.out.println("Evento " + evento.getNome() + "  ID: " + evento.getId());
		}
	}
	
	public Evento getEvento(int id){
		return this.eventos.get(id);
	}
	
	
	public Colaborador getColaborador(int id) {
		return this.colaboradores.get(id);
	}
	
	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

}
