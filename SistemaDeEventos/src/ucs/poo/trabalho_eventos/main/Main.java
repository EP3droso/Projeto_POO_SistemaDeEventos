package ucs.poo.trabalho_eventos.main;

import java.io.IOException;
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
		
		Sistema sistema = new Sistema();
		

		if(sistema.ArquivoExiste()) {
			Empresa empresa = null;
			String nomeEmpresa;
			if((empresa = sistema.desserializarEmpresa()) == null) {
				nomeEmpresa = login();
				empresa = new Empresa(nomeEmpresa);
			}
			

			
			nomeEmpresa = empresa.getNome();
			
			Scanner sc = new Scanner(System.in);		
			
			MenuEvento menuEvento = new MenuEvento();
			MenuColaborador menuColaborador = new MenuColaborador();
			MenuTarefa menuTarefa = new MenuTarefa();
			MenuRecurso menuRecurso = new MenuRecurso ();
			MenuRelatorio menuRelatorio = new MenuRelatorio();
			
			System.out.println("Seja bem vindo ao menu principal do Sistema de Controle de Eventos (**o menu visual será inserido posteriormente)");
	
			for(;;) {
				
				mostrarMenuPrincipal(nomeEmpresa);
				
				int intEntrada = Utilitarios.lerInteiroComVerificacao();
					
				if(intEntrada == 1) {
					
					menuEvento.menuEvento(empresa,sistema);
				}
				
				    
				else if(intEntrada == 2) {
					
					menuColaborador.menuColaborador(empresa,sistema);
				}		
				
				else if (intEntrada == 3) {
				    
				    menuTarefa.menuTarefa(empresa,sistema);
				}
	
				else if(intEntrada == 4) {
					
					menuRecurso.menuRecurso(empresa,sistema);
				}
				else if (intEntrada == 5) {
				    
				    menuRelatorio.menuRelatorio(empresa,sistema);
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
		else {
			System.out.println("O arquivo não existe! Houve algum erro na criação do arquivo");
		}
		

	}
}
	
	
