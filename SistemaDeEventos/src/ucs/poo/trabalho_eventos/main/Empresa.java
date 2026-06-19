package ucs.poo.trabalho_eventos.main;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import ucs.poo.trabalho_eventos.Colaborador.Colaborador;
import ucs.poo.trabalho_eventos.Evento.Evento;
import ucs.poo.trabalho_eventos.Recurso.Recurso;
import ucs.poo.trabalho_eventos.Relacionamentos.ColaboradorTarefa;
import ucs.poo.trabalho_eventos.Relacionamentos.RecursoTarefa;
import ucs.poo.trabalho_eventos.Relatorio.RelatorioInterface;
import ucs.poo.trabalho_eventos.Tarefa.Tarefa;

public class Empresa{
	
	private String nome;
	private List<Evento> eventos;
	private List<Colaborador> colaboradores;
	private List<Tarefa> tarefasDB;
	private List<Recurso> recursosDB;
	
	private int idAtualEventos=0;
	private int idAtualEColaboradores=0;
	private int idAtualTarefas=0;
	private int idAtualRecursos=0;

	
	public int getIdAtualEventos() {
		return idAtualEventos;
	}



	public void setIdAtualEventos(int idAtualEventos) {
		this.idAtualEventos = idAtualEventos;
	}



	public int getIdAtualEColaboradores() {
		return idAtualEColaboradores;
	}



	public void setIdAtualEColaboradores(int idAtualEColaboradores) {
		this.idAtualEColaboradores = idAtualEColaboradores;
	}



	public int getIdAtualTarefas() {
		return idAtualTarefas;
	}



	public void setIdAtualTarefas(int idAtualTarefas) {
		this.idAtualTarefas = idAtualTarefas;
	}



	public int getIdAtualRecursos() {
		return idAtualRecursos;
	}



	public void setIdAtualRecursos(int idAtualRecursos) {
		this.idAtualRecursos = idAtualRecursos;
	}
	
	public Empresa(String nome) {
		this(); 
		this.nome = nome;
	}
	
	public Empresa() {
		this.eventos = new ArrayList<>();
		this.colaboradores = new ArrayList<>();
		this.tarefasDB = new ArrayList<>();
		this.recursosDB = new ArrayList<>();
	}


    
    

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }



	public List<Tarefa> getTarefasDB() {
		return tarefasDB;
	}

	public void setTarefasDB(List<Tarefa> tarefasDB) {
		this.tarefasDB = tarefasDB;
	}

	public List<Recurso> getRecursosDB() {
		return recursosDB;
	}

	public void setRecursosDB(List<Recurso> recursosDB) {
		this.recursosDB = recursosDB;
	}

	public List<Evento> getEventos() {
		return eventos;
	}

	public void setEventos(List<Evento> eventos) {
		this.eventos = eventos;
	}

	public List<Colaborador> getColaboradores() {
		return colaboradores;
	}

	public void setColaboradores(List<Colaborador> colaboradores) {
		this.colaboradores = colaboradores;
	}
}