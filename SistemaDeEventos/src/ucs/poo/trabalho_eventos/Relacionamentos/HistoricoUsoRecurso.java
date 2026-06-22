package ucs.poo.trabalho_eventos.Relacionamentos;

import java.util.Date;

public class HistoricoUsoRecurso {

    private int idRecurso;
    private String nomeRecurso;
    private String nomeTarefa;
    private String nomeEvento;
    private int quantidadeUsada;
    private boolean foiDevolvido; // true = equipamento, false = consumivel
    private Date dataUso;

    public HistoricoUsoRecurso(int idRecurso, String nomeRecurso, String nomeTarefa, 
                                String nomeEvento, int quantidadeUsada, 
                                boolean foiDevolvido, Date dataUso) {
        this.idRecurso = idRecurso;
        this.nomeRecurso = nomeRecurso;
        this.nomeTarefa = nomeTarefa;
        this.nomeEvento = nomeEvento;
        this.quantidadeUsada = quantidadeUsada;
        this.foiDevolvido = foiDevolvido;
        this.dataUso = dataUso;
    }

    public HistoricoUsoRecurso() {}

    public int getIdRecurso() { return idRecurso; }
    public void setIdRecurso(int idRecurso) { this.idRecurso = idRecurso; }
    public String getNomeRecurso() { return nomeRecurso; }
    public void setNomeRecurso(String nomeRecurso) { this.nomeRecurso = nomeRecurso; }
    public String getNomeTarefa() { return nomeTarefa; }
    public void setNomeTarefa(String nomeTarefa) { this.nomeTarefa = nomeTarefa; }
    public String getNomeEvento() { return nomeEvento; }
    public void setNomeEvento(String nomeEvento) { this.nomeEvento = nomeEvento; }
    public int getQuantidadeUsada() { return quantidadeUsada; }
    public void setQuantidadeUsada(int quantidadeUsada) { this.quantidadeUsada = quantidadeUsada; }
    public boolean isFoiDevolvido() { return foiDevolvido; }
    public void setFoiDevolvido(boolean foiDevolvido) { this.foiDevolvido = foiDevolvido; }
    public Date getDataUso() { return dataUso; }
    public void setDataUso(Date dataUso) { this.dataUso = dataUso; }

    @Override
    public String toString() {
        String situacao = foiDevolvido ? "Devolvido ao estoque" : "Consumido definitivamente";
        return String.format("Recurso: %s | Tarefa: %s | Evento: %s | Qtd: %d | %s | Data: %s",
                nomeRecurso, nomeTarefa, nomeEvento, quantidadeUsada, situacao, dataUso);
    }
}
