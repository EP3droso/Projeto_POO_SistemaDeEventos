package ucs.poo.trabalho_eventos.main;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ucs.poo.trabalho_eventos.Colaborador.Colaborador;
import ucs.poo.trabalho_eventos.Evento.Evento;
import ucs.poo.trabalho_eventos.Recurso.Recurso;
import ucs.poo.trabalho_eventos.Relacionamentos.ColaboradorTarefa;
import ucs.poo.trabalho_eventos.Relacionamentos.RecursoTarefa;
import ucs.poo.trabalho_eventos.Relatorio.Relatorio;
import ucs.poo.trabalho_eventos.Tarefa.Tarefa;

public class Empresa implements Relatorio {
	
	private String nome;
	private List<Evento> eventos = new ArrayList<>();;
	private List<Colaborador> colaboradores = new ArrayList<>();;
	//private String nomeEmpresa;
	private List<Tarefa> tarefasDB = new ArrayList<>();
	private List<Recurso> recursosDB = new ArrayList<>();

	
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
			System.out.println("Evento " + evento.getNome() + "  ID: " + eventos.indexOf(evento));
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
    
    public void alterarColaborador(int id, String novoNome, String novoEmail, String novaSenha, String novaFuncao) {
    	Colaborador colaborador = getColaborador(id);
    	
		if(colaborador == null) {
			System.out.println("Colaborador não encontrado.");
			return;
		}
		
		colaborador.setNome(novoNome);
		colaborador.setEmail(novoEmail);
		colaborador.setSenha(novaSenha);
		colaborador.setFuncao(novaFuncao);	
		
		System.out.println("Colaborador alterado com sucesso!");
	}
    public void mostrarColaboradores() {
        if(colaboradores.isEmpty()) {
            System.out.println("Nenhum colaborador cadastrado.");
            return;
        }
        System.out.println("Colaboradores cadastrados:");
        for(Colaborador colaborador : colaboradores) {
            System.out.println("ID: " + colaborador.getId()+ " | Nome: " + colaborador.getNome());
        }
    }
    
    public void buscarColaboradorPorNome(String nome) {
        boolean encontrado = false;
        for(Colaborador colaborador : colaboradores) {
            if(colaborador.getNome().equalsIgnoreCase(nome)) {
                System.out.println(colaborador);
                encontrado = true;
            }
        }

        if(!encontrado) {
            System.out.println("Nenhum colaborador encontrado com esse nome.");
        }
    }
    
    public Colaborador getColaborador(String nome) {
        for(Colaborador colaborador : colaboradores) {
            if(nome.equals(colaborador.getNome())) {
                return colaborador;
            }
        }

        return null;
    }

    
	
	public Evento getEvento(String nome){
		
		for(Evento aux : this.eventos) {
			if(nome.equals(aux.getNome())) {
				return aux;
			}
		}
		
		System.out.println("Não existe um evento com esse ID");
		return null;
		
	}
	
	
	public void excluirEvento(int id) {
		Evento eventoAux = this.getEvento(id);
		if(eventoAux != null) {
			this.eventos.remove(eventoAux);
		}
		for(Evento eventoAux2 : this.eventos) {
			eventoAux2.eventoSendoCadastrado(this.eventos.indexOf(eventoAux));
		}
	}
	

	public void alterarEvento(int id, String nome) {
		Evento eventoAux = this.getEvento(id);
		if(eventoAux != null) {
			eventoAux.setNome(nome);
			return;
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
}