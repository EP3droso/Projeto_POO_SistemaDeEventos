package ucs.poo.trabalho_eventos.Relatorio;

import java.util.Date;
import ucs.poo.trabalho_eventos.Evento.Evento;
import ucs.poo.trabalho_eventos.Relacionamentos.ColaboradorTarefa;
import ucs.poo.trabalho_eventos.Relacionamentos.EventoTarefa;
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

        if (eventoAlvo.getEventoTarefas().isEmpty()) {
            System.out.println("Nenhuma tarefa vinculada a este evento.");
            return;
        }

        for (EventoTarefa et : eventoAlvo.getEventoTarefas()) {
            System.out.println("\nTarefa ID: " + et.getTarefa().getId() + " - Nome: " + et.getTarefa().getNome());

            if (!et.foiExecutada()) {
                System.out.println("-> [Tarefa ainda não executada neste evento]");
                continue;
            }

            System.out.println("-> Execução: " + et.getHoraIni() + " -> " + et.getHoraFim());

            System.out.println("-> Colaboradores:");
            if (et.getColaboradoresTarefas().isEmpty()) {
                System.out.println("   [Nenhum colaborador alocado]");
            } else {
                for (ColaboradorTarefa ct : et.getColaboradoresTarefas()) {
                    System.out.println("   - " + ct.getColaborador().getNome());
                }
            }

            System.out.println("-> Recursos:");
            if (et.getRecursosTarefas().isEmpty()) {
                System.out.println("   [Nenhum recurso alocado]");
            } else {
                for (RecursoTarefa rt : et.getRecursosTarefas()) {
                    System.out.println("   - " + rt.getRecurso().getNome() + " | Qtd: " + rt.getQuantidade());
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
                for (EventoTarefa et : evento.getEventoTarefas()) {
                    if (!et.foiExecutada()) continue;

                    long tarefaIniMs = et.getHoraIni().getTime();
                    long tarefaFimMs = et.getHoraFim().getTime();

                    if (tarefaIniMs >= inicioMs && tarefaFimMs <= fimMs) {
                        StringBuilder colabs = new StringBuilder();
                        for (ColaboradorTarefa ct : et.getColaboradoresTarefas()) {
                            if (colabs.length() > 0) colabs.append(", ");
                            colabs.append(ct.getColaborador().getNome());
                        }
                        System.out.println("Evento: " + evento.getNome() +
                                           " | Tarefa: " + et.getTarefa().getNome() +
                                           " | Colaboradores: " + (colabs.length() > 0 ? colabs : "nenhum") +
                                           " | Período: " + et.getHoraIni() + " -> " + et.getHoraFim());
                        encontrou = true;
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
        System.out.println("RECURSOS ALOCADOS PARA A TAREFA ID: " + idTarefa);
        System.out.println("=============================================");

        boolean achouTarefaNoEvento = false;
        boolean achouRecurso = false;

        for (Evento ev : empresa.getEventos()) {
            for (EventoTarefa et : ev.getEventoTarefas()) {
                if (et.getTarefa() != null && et.getTarefa().getId() == idTarefa) {
                    achouTarefaNoEvento = true;
                    System.out.println("\nEvento: " + ev.getNome() + " | Tarefa: " + et.getTarefa().getNome());

                    if (et.getRecursosTarefas().isEmpty()) {
                        System.out.println("  -> Nenhum recurso alocado nesta instância.");
                    } else {
                        for (RecursoTarefa rt : et.getRecursosTarefas()) {
                            if (rt.getRecurso() != null) {
                                System.out.println("  - Recurso: " + rt.getRecurso().getNome() +
                                                   " (Tipo: " + rt.getRecurso().getTipo() + ")" +
                                                   " | Qtd: " + rt.getQuantidade());
                                achouRecurso = true;
                            }
                        }
                    }
                }
            }
        }

        if (!achouTarefaNoEvento) {
            boolean existeNoBanco = false;
            for (Tarefa t : empresa.getTarefasDB()) {
                if (t.getId() == idTarefa) { existeNoBanco = true; break; }
            }
            if (existeNoBanco) {
                System.out.println("A tarefa existe no banco, mas não está alocada em nenhum evento.");
            } else {
                System.out.println("Erro: Nenhuma tarefa com o ID " + idTarefa + " foi localizada no sistema.");
            }
        } else if (!achouRecurso) {
            System.out.println("\nA tarefa está nos eventos, mas nenhum recurso ficou associado a ela.");
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
