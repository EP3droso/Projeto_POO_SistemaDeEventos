package ucs.poo.trabalho_eventos.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ucs.poo.trabalho_eventos.interfaces.Relatorio;

public class Empresa implements Relatorio {
	
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
		try {
		return this.eventos.get(id);
		}
		catch(IndexOutOfBoundsException e) {
			System.out.println("Não existe um evento com esse ID");
			return null;
		}
	}


	
	

    public void cadastrarColaborador(Colaborador colaborador) {
        colaboradores.add(colaborador);
    }

    public void listarColaboradores() {
        if(colaboradores.isEmpty()) {
            System.out.println("Nenhum colaborador cadastrado.");
            return;
        }

        System.out.println("Lista de Colaboradores:");
        for(Colaborador colaborador : colaboradores) {
            System.out.println(colaborador);
        }
    }

    public Colaborador getColaborador(int id) {
        for(Colaborador colaborador : colaboradores) {
            if(colaborador.getId() == id) {
                return colaborador;
            }
        }

        return null;
    }

    public void excluirColaborador(int id) {
        Colaborador colaborador = getColaborador(id);
        if(colaborador == null) {
            System.out.println("Colaborador não encontrado.");
            return;
        }
        colaboradores.remove(colaborador);
        System.out.println("Colaborador removido com sucesso!");
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }


    @Override
    public void gerarRelatorioEvento(int eventoId) {

        Evento evento = getEvento(eventoId);

        if(evento == null) {
            System.out.println("Evento não encontrado.");
            return;
        }

        System.out.println("----Relatório do Evento: " + evento.getNome() + "-----");

        evento.acompanharAndamento();
        evento.consultarHistoricoTarefas();
    }

    @Override
    public void gerarRelatorioPeriodo(Date inicio, Date fim) {
        if (inicio == null || fim == null || fim.before(inicio)) {
            System.out.println("Período inválido.");
            return;
        }
        System.out.println("------Execuções entre " + inicio + " e " + fim + "-------");
        for (Evento evento : eventos) {
            for (Tarefa t : evento.getTarefas()) {
                for (ColaboradorTarefa ct : t.getColaboradoresTarefas()) {
                    if (!ct.getHoraIni().before(inicio)
                            && !ct.getHoraFim().after(fim)) {
                        System.out.println(
                                "Evento: " + evento.getNome()+ " | Tarefa: " + t.getNome()+ " | " + ct);
                    }
                }
            }
        }
    }

    @Override
    public void gerarRelatorioRecursosTarefa(int indiceTarefa) {
        Tarefa tarefaEncontrada = null;
        for(Evento e : eventos) {
            if(indiceTarefa >= 0 &&
               indiceTarefa < e.getTarefas().size()) {
                tarefaEncontrada =
                    e.getTarefas().get(indiceTarefa);
                break;
            }
        }

        if(tarefaEncontrada == null) {
            System.out.println("Tarefa não encontrada!");
            return;
        }

        System.out.println("\n----- RECURSOS DA TAREFA -----");
        System.out.println("Tarefa: " + tarefaEncontrada.getNome());

        if(tarefaEncontrada.getRecursosTarefas().isEmpty()) {
            System.out.println("Nenhum recurso registrado.");
            return;
        }

        for(RecursoTarefa rt : tarefaEncontrada.getRecursosTarefas()) {
            System.out.println("Recurso: "+ rt.getRecurso().getNome());
            System.out.println("Quantidade usada: " + rt.getRecurso().getQuantidade());
            System.out.println("----------------------");
        }
    }
}