package ucs.poo.trabalho_eventos.Tarefa;

public class TarefaForaDeOrdemException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    private final String nomeTarefaBloqueante;

    public TarefaForaDeOrdemException(String nomeTarefaBloqueante) {
        super("Pré-requisito não concluído: '" + nomeTarefaBloqueante + "' precisa ser executada antes.");
        this.nomeTarefaBloqueante = nomeTarefaBloqueante;
    }

    public String getNomeTarefaBloqueante() {
        return nomeTarefaBloqueante;
    }
}
