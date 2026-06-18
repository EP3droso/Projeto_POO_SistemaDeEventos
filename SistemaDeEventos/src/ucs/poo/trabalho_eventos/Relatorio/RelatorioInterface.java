package ucs.poo.trabalho_eventos.Relatorio;

import java.util.Date;

public interface RelatorioInterface {
    void gerarRelatorioEvento(int eventoId);
    void gerarRelatorioPeriodo(Date inicio, Date fim);
    void gerarRelatorioRecursosTarefa(int indiceTarefa);
}