package ucs.poo.trabalho_eventos.Evento;

import java.util.List;
import java.util.Scanner;

import ucs.poo.trabalho_eventos.Tarefa.Tarefa;
import ucs.poo.trabalho_eventos.main.Empresa;
import ucs.poo.trabalho_eventos.main.Sistema;
import ucs.poo.trabalho_eventos.main.Utilitarios;

public class Functions {
	
	public static boolean eventosIsEmpty(Empresa empresa) {
		if(empresa.getEventos().isEmpty()) {
			System.out.println("Nenhum evento cadastrado.");
			return true;
		}
			
		return false;
	}
	
	
	public static void cadastrarEvento(Empresa empresa, Sistema sistema, List<Evento> eventosDB) {
		Scanner sc = new Scanner(System.in);
		
		System.out.println("Insira o nome do evento:");
        String nomeEvento = sc.nextLine();
        System.out.println("Insira o tipo do evento:\n"
        		+ "1-Festa\n"
        		+ "2-Formatura\n"
        		+ "3-Evento Corporativo");
        int tipo = Utilitarios.lerInteiroComVerificacao();
        Evento eventoAux = new Evento(nomeEvento, tipo, empresa.getIdAtualEventos());
        
        empresa.setIdAtualEventos(empresa.getIdAtualEventos()+1);
        
        eventosDB.add(eventoAux);
        empresa.setEventos(eventosDB);
        sistema.serializarEmpresa(empresa);
	}
	
	public static  void listarEventos(Empresa empresa) {
		System.out.println("Os eventos cadastrados são:");
		for(Evento evento : empresa.getEventos()){
			System.out.println(empresa.getEventos().indexOf(evento) +" - " + evento.getNome());
		}
	}
	
	public static Evento getEvento(int index, Empresa empresa){
		try {
		return empresa.getEventos().get(index);
		}
		catch(IndexOutOfBoundsException e) {
			System.out.println("Não existe um evento nesse index");
			return null;
		}
	}
	
	public static  Evento getEvento(String nome, Empresa empresa){
		for(Evento aux : empresa.getEventos()) {
			if(nome.equals(aux.getNome())) {
				return aux;
			}
		}
		
		System.out.println("Não existe um evento com esse nome");
		return null;
		
	}
	
	public static void excluirEvento(int index, Empresa empresa, Sistema sistema) {
		Evento eventoAux = getEvento(index,empresa);
		if(eventoAux != null) {
			empresa.getEventos().remove(eventoAux);
		}
		sistema.serializarEmpresa(empresa);
	}

	public static void alterarEvento(Evento eventoAux,Empresa empresa,Sistema sistema) {
		Scanner sc = new Scanner(System.in);
			
		System.out.println("Digite o novo nome do evento:");
        String novoNome = sc.nextLine();
        System.out.println("Insira o novo do evento:\n"
        		+ "1-Festa\n"
        		+ "2-Formatura\n"
        		+ "3-Evento Corporativo");
        int novoTipo = Utilitarios.lerInteiroComVerificacao();
		
        String enter = System.lineSeparator();
        if(!enter.equals(novoNome)) {
        	eventoAux.setNome(novoNome);
        }
        
        //VER DA VERIFICAÇÂO DE Utilitarios.lerInteiroComVerificacao();
        if(!enter.equals(novoTipo)) {
    		if(novoTipo==1) {
    			eventoAux.setTipo("Festa");
    		}else if(novoTipo==2){
    			eventoAux.setTipo("Formatura");
    		}else if(novoTipo==3){
    			eventoAux.setTipo("Evento Corporativo");
    		}
        	
        } 
        
        sistema.serializarEmpresa(empresa);
	
	}
	
	public static void mostrarInfoEvento(Evento eventoAlvo, Empresa empresa) {
		System.out.println("|---------------------------------------------------------------------------|");
        System.out.println(eventoAlvo.getNome() + ":" );
        System.out.println("Tarefas:");
        for(Tarefa t : eventoAlvo.getTarefas()) {
        	System.out.println(eventoAlvo.getTarefas().indexOf(t) + " - " + t.getNome());
        }
	}
	
}
