package ucs.poo.trabalho_eventos.main;

import java.util.Scanner;

import ucs.poo.trabalho_eventos.Colaborador.MenuColaborador;
import ucs.poo.trabalho_eventos.Evento.MenuEvento;
import ucs.poo.trabalho_eventos.Recurso.MenuRecurso;
import ucs.poo.trabalho_eventos.Tarefa.MenuTarefa;
import ucs.poo.trabalho_eventos.Relatorio.MenuRelatorio;

public class Main {

	private static void mostrarMenuPrincipal(String nomeEmpresa) {
	    System.out.println("\r\n"
	    		+ "---------------------------------------------\nMENU de " + nomeEmpresa);
	    System.out.println("1 - Menu de Eventos");
	    System.out.println("2 - Menu de Colaboradores");
	    System.out.println("3 - Menu de Tarefas");
	    System.out.println("4 - Menu de Recursos");
	    System.out.println("5 - Relatorios");
	    System.out.println("0 - Sair");
	}	
	
	private static String login() {
		Scanner sc = new Scanner(System.in);
		System.out.println("LOGIN");
		System.out.println("Insira o nome de sua Empresa:");
		String nomeEmpresa = sc.nextLine();
		System.out.println("Insira seu usuario:");
		String nome = sc.nextLine();
		System.out.println("Insira sua senha:");
		sc.next();
		System.out.println("Seja muito bem vindo " + nome + "!");
		return nomeEmpresa;
	}
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);		
		String nomeEmpresa = login();
		Empresa empresa = new Empresa(nomeEmpresa);
		
		System.out.println("Seja bem vindo ao menu principal do Sistema de Controle de Eventos (**o menu visual será inserido posteriormente)");

		for(;;) {
			
			mostrarMenuPrincipal(nomeEmpresa);
			
			int intEntrada = Utilitarios.lerInteiroComVerificacao();
				
			if(intEntrada == 1) {
				MenuEvento menuEvento = new MenuEvento();
				menuEvento.menuEvento(empresa);
			}
			
			    
			else if(intEntrada == 2) {
				MenuColaborador menuColaborador = new MenuColaborador();
				menuColaborador.menuColaborador(empresa);
			}		
			
			else if (intEntrada == 3) {
			    MenuTarefa menuTarefa = new MenuTarefa();
			    menuTarefa.menuTarefa(empresa);
			}

			else if(intEntrada == 4) {
				MenuRecurso menuRecurso = new MenuRecurso ();
				menuRecurso.menuRecurso(empresa);
			}
			else if (intEntrada == 5) {
			    MenuRelatorio menuRelatorio = new MenuRelatorio();
			    menuRelatorio.menuRelatorio(empresa);
			}
			
			else if(intEntrada == 0) {
				System.out.println("Saindo...");
				System.exit(0);
			}
			
			else {
				System.out.println("O numero entrado não é uma das opções do menu!");
			}		
		}
	}
	
	
}