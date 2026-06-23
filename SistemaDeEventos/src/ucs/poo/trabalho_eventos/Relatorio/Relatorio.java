package ucs.poo.trabalho_eventos.Relatorio;

import java.util.Date;
import ucs.poo.trabalho_eventos.Evento.Evento;
import ucs.poo.trabalho_eventos.Relacionamentos.ColaboradorTarefa;
import ucs.poo.trabalho_eventos.Relacionamentos.HistoricoUsoRecurso;
import ucs.poo.trabalho_eventos.Relacionamentos.RecursoTarefa;
import ucs.poo.trabalho_eventos.Tarefa.Tarefa;
import ucs.poo.trabalho_eventos.main.Empresa;

public class Relatorio implements RelatorioInterface {

    @Override
    public void gerarRelatorioEvento(int eventoId, Empresa empresa) {
        Evento eventoAlvo = null;
        for (Evento e : empresa.getEventos()) {
            if (e.getId() == eventoId) {
                eventoAlvo = e;
                break;
            }
        }

        if (eventoAlvo == null) {
            System.out.println("Evento não encontrado.");
            return;
        }

        System.out.println("\n=============================================");
        System.out.println("RELATÓRIO DO EVENTO: " + eventoAlvo.getNome());
        System.out.println("=============================================");
        
        if (eventoAlvo.getTarefas().isEmpty()) {
            System.out.println("Nenhuma tarefa vinculada a este evento.");
            return;
        }

        for (Tarefa t : eventoAlvo.getTarefas()) {
            System.out.println("\nTarefa ID: " + t.getId() + " - Nome: " + t.getNome());
            System.out.println("-> Execuções Realizadas:");
            if (t.getColaboradoresTarefas().isEmpty()) {
                System.out.println("   [Nenhuma execução registrada para esta tarefa]");
            } else {
                for (ColaboradorTarefa ct : t.getColaboradoresTarefas()) {
                    System.out.println("   - Colaborador: " + ct.getColaborador().getNome() + 
                                       " | Início: " + ct.getHoraIni() + " | Fim: " + ct.getHoraFim());
                }
            }
        }
    }

    @Override
    public void gerarRelatorioPeriodo(Date inicio, Date fim, Empresa empresa) {
        if (inicio == null || fim == null || fim.before(inicio)) {
            System.out.println("Período inválido.");
            return;
        }

        System.out.println("\n=============================================");
        System.out.println("EXECUÇÕES REALIZADAS NO PERÍODO");
        System.out.println("De: " + inicio + " até " + fim);
        System.out.println("=============================================");

        long inicioMs = inicio.getTime();
        long fimMs = fim.getTime();

        boolean encontrou = false;        
        
        if (empresa.getEventos() != null) {
            for (Evento evento : empresa.getEventos()) {
                if (evento.getTarefas() != null) {
                    for (Tarefa t : evento.getTarefas()) {
                        if (t.getColaboradoresTarefas() != null) {
                            for (ColaboradorTarefa ct : t.getColaboradoresTarefas()) {
                                
                                
                                if (ct.getHoraIni() != null && ct.getHoraFim() != null) {
                                    long tarefaIniMs = ct.getHoraIni().getTime();
                                    long tarefaFimMs = ct.getHoraFim().getTime();

                                    
                                    if (tarefaIniMs >= inicioMs && tarefaFimMs <= fimMs) {
                                        System.out.println("Evento: " + evento.getNome() + 
                                                           " | Tarefa: " + t.getNome() + 
                                                           " | Colaborador: " + (ct.getColaborador() != null ? ct.getColaborador().getNome() : "Desconhecido") +
                                                           " | Período: " + ct.getHoraIni() + " -> " + ct.getHoraFim());
                                        encontrou = true;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        
        if (!encontrou) {
            System.out.println("Nenhuma execução realizada neste período.");
        }
    }
    @Override
    public void gerarRelatorioRecursosTarefa(int idTarefa, Empresa empresa) {
        System.out.println("\n=============================================");
        System.out.println("BUSCANDO RECURSOS PARA O ID DA TAREFA: " + idTarefa);
        System.out.println("=============================================");

        boolean encontrouQualquerTarefa = false;
        boolean encontrouRecurso = false;

        
        if (empresa.getTarefasDB() != null) {
            for (Tarefa t : empresa.getTarefasDB()) {
                if (t.getId() == idTarefa) {
                    encontrouQualquerTarefa = true;
                    System.out.println("\nNome: " + t.getNome());
                    
                    if (t.getRecursosTarefas() != null && !t.getRecursosTarefas().isEmpty()) {
                        for (RecursoTarefa rt : t.getRecursosTarefas()) {
                            if (rt.getRecurso() != null) {
                                System.out.println("  - Recurso: " + rt.getRecurso().getNome() + 
                                                   " (Tipo: " + rt.getRecurso().getTipo() + ")" +
                                                   " | Qtd: " + rt.getRecurso().getQuantidade());
                                encontrouRecurso = true;
                            }
                        }
                    } else {
                        System.out.println("  -> Nenhum recurso alocado nesta instância do Banco Geral.");
                    }
                }
            }
        }

        if (empresa.getEventos() != null) {
            for (ucs.poo.trabalho_eventos.Evento.Evento evento : empresa.getEventos()) {
                if (evento.getTarefas() != null) {
                    for (Tarefa t : evento.getTarefas()) {
                        if (t.getId() == idTarefa) {
                            encontrouQualquerTarefa = true;
                            System.out.println("\n Nome da Tarefa: " + t.getNome());
                            
                            if (t.getRecursosTarefas() != null && !t.getRecursosTarefas().isEmpty()) {
                                for (RecursoTarefa rt : t.getRecursosTarefas()) {
                                    if (rt.getRecurso() != null) {
                                        System.out.println("  - Recurso: " + rt.getRecurso().getNome() + 
                                                           " (Tipo: " + rt.getRecurso().getTipo() + ")" +
                                                           " | Qtd Alocada/Consumida: " + rt.getRecurso().getQuantidade());
                                        encontrouRecurso = true;
                                    }
                                }
                            } else {
                                System.out.println("  -> Nenhum recurso alocado nesta instância do Evento.");
                            }
                        }
                    }
                }
            }
        }

        if (!encontrouQualquerTarefa) {
            System.out.println("Erro: Nenhuma tarefa com o ID " + idTarefa + " foi localizada no sistema.");
        } else if (!encontrouRecurso) {
            System.out.println("\nA tarefa existe, mas nenhuma unidade de recurso ficou associada a ela.");
        }
        System.out.println("=============================================\n");
    }
    @Override
    public void verificarUsoRecursoAoLongoDoTempo(int idRecurso, Empresa empresa) {
        System.out.println("\n=============================================");
        System.out.println("HISTÓRICO DE USO DO RECURSO AO LONGO DO TEMPO");
        System.out.println("=============================================");

        if (empresa.getHistoricoUsoRecursos() == null || empresa.getHistoricoUsoRecursos().isEmpty()) {
            System.out.println("Nenhum histórico de utilização registrado no sistema.");
            return;
        }

        boolean encontrou = false;
        for (HistoricoUsoRecurso hur : empresa.getHistoricoUsoRecursos()) {
            if (hur.getIdRecurso() == idRecurso) {
                System.out.println("- Data/Hora: " + hur.getDataUso() + 
                                   " | Tarefa Vinculada: " + hur.getNomeTarefa() + 
                                   " | Quantidade Utilizada: " + hur.getQuantidadeUsada());
                encontrou = true;
            }
        }

        if (!encontrou) {
            System.out.println("Nenhum registro de uso encontrado para o ID especificado.");
        }
    }
}