package ucs.poo.trabalho_eventos.Relatorio;

import java.util.Date;
import ucs.poo.trabalho_eventos.main.Empresa;

public interface RelatorioInterface {
    void gerarRelatorioEvento(int eventoId, Empresa empresa);
    void gerarRelatorioPeriodo(Date inicio, Date fim, Empresa empresa);
    void gerarRelatorioRecursosTarefa(int idTarefa, Empresa empresa);
    void verificarUsoRecursoAoLongoDoTempo(int idRecurso, Empresa empresa);
}