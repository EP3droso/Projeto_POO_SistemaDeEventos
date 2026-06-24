package ucs.poo.trabalho_eventos.Evento;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import ucs.poo.trabalho_eventos.Relacionamentos.EventoTarefa;
import ucs.poo.trabalho_eventos.Tarefa.Tarefa;
import ucs.poo.trabalho_eventos.main.Utilitarios;

@JsonIdentityInfo(
    generator = ObjectIdGenerators.PropertyGenerator.class,
    property = "id",
    scope = Evento.class
)
public class Evento {

	private int id;
	private String nome;
	private String tipoEvento;
	private List<EventoTarefa> eventoTarefas;



	public Evento(String nome, int i,int id){
		this();
		setNome(nome);
		this.id=id;
		if(i==1) {
			this.tipoEvento = "Festa";
		}else if(i==2){
			this.tipoEvento = "Formatura";
		}else if(i==3){
			this.tipoEvento = "Evento Corporativo";
		}
	}

	public Evento() {
		this.eventoTarefas = new ArrayList<>();
	}


	public String getTipoEvento() {
		return tipoEvento;
	}

	public void setTipoEvento(String tipoEvento) {
		this.tipoEvento = tipoEvento;
	}

	public String getTipo(){
		return tipoEvento;
	}

	public void setTipo(String tipo) {
		this.tipoEvento = tipo;
	}

	public List<EventoTarefa> getEventoTarefas() {
		return eventoTarefas;
	}

	public void setEventoTarefas(List<EventoTarefa> eventoTarefas) {
		this.eventoTarefas = eventoTarefas;
	}
	@JsonIgnore
	public List<Tarefa> getTarefas() {
		List<Tarefa> tarefas = new ArrayList<>();
		for (EventoTarefa et : eventoTarefas) {
			tarefas.add(et.getTarefa());
		}
		return tarefas;
	}


	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Verifica se uma tarefa já está vinculada a este evento (compara pela
	 * referência da Tarefa).
	 */
	@JsonIgnore
	public boolean possuiTarefa(Tarefa tarefa) {
		for (EventoTarefa et : eventoTarefas) {
			if (et.getTarefa() != null && et.getTarefa().equals(tarefa)) {
				return true;
			}
		}
		return false;
	}

	public void cadastrarTarefa(Tarefa tarefa) {
		this.eventoTarefas.add(new EventoTarefa(this, tarefa));
	}

	public void listarTarefas() {
		List<Tarefa> tarefas = getTarefas();
		for(Tarefa aux : tarefas) {
			System.out.println((tarefas.indexOf(aux) + 1)+ " - " + aux.getNome());
		}
	}

	@JsonIgnore
	public Tarefa getTarefa(int index){
		try {
			return this.eventoTarefas.get(index).getTarefa();
		}
		catch(IndexOutOfBoundsException e) {
			return null;
		}
	}

	public void excluirTarefa(int index) {
		try {
			this.eventoTarefas.remove(index);
		}
		catch(IndexOutOfBoundsException e) {
		}
	}

	public void removerVinculoTarefa(Tarefa tarefa) {
		this.eventoTarefas.removeIf(et -> et.getTarefa() != null && et.getTarefa().equals(tarefa));
	}

	public int getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = Utilitarios.exigirNaoVazio(nome, "Nome do evento");
	}



}
