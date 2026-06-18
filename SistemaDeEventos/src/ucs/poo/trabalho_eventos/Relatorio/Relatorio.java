package ucs.poo.trabalho_eventos.Relatorio;

import java.util.Date;

import ucs.poo.trabalho_eventos.Evento.Evento;
import ucs.poo.trabalho_eventos.Relacionamentos.ColaboradorTarefa;
import ucs.poo.trabalho_eventos.Relacionamentos.RecursoTarefa;
import ucs.poo.trabalho_eventos.Tarefa.Tarefa;
import ucs.poo.trabalho_eventos.main.Empresa;

public class Relatorio extends Empresa implements RelatorioInterface {
	
	public Relatorio(String nome) {
		super(nome);
		// TODO Auto-generated constructor stub
	}
	
	@Override
    public void gerarRelatorioEvento(int eventoId) {
		/*
        Evento evento = getEvento(eventoId);

        if(evento == null) {
            System.out.println("Evento não encontrado.");
            return;
        }

        System.out.println("----Relatório do Evento: " + evento.getNome() + "-----");

        evento.acompanharAndamento();
        evento.consultarHistoricoTarefas();
        */
    }
	
	 @Override
	    public void gerarRelatorioPeriodo(Date inicio, Date fim) {
		 /*
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
	        */
	        
	    }
	 
	 @Override
	    public void gerarRelatorioRecursosTarefa(int indiceTarefa) {
		 /*
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
	        */
	    }
	    
}
