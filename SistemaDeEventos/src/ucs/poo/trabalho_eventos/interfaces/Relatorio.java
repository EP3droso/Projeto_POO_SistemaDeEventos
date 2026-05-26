package ucs.poo.trabalho_eventos.interfaces;

import java.util.Date;

public interface Relatorio {
    void gerarRelatorioEvento(int eventoId);
    void gerarRelatorioPeriodo(Date inicio, Date fim);
    void gerarRelatorioRecursosTarefa(int indiceTarefa);
}