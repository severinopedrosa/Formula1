package controller;

import java.util.Random;
import java.util.concurrent.Semaphore;

public class ThreadCarro extends Thread {
	
	private int idCarro;
	private int numeroEquipe;
	private static int[] tempoMaisRapido = {10000, 10000, 10000, 10000, 10000, 10000, 10000,
											10000, 10000, 10000, 10000, 10000, 10000, 10000};
	
	private Semaphore limitePista;
	private Semaphore limiteEquipe;
	
	public ThreadCarro(int idCarro, int numeroEquipe, Semaphore semaforoCarrosPista, Semaphore semaforoCarroEquipe) {
		this.idCarro = idCarro;
		this.numeroEquipe = numeroEquipe;
		this.limitePista = semaforoCarrosPista;
		this.limiteEquipe = semaforoCarroEquipe;
	}
	
	@Override
	public void run() {
		
		try {
			
			limitePista.acquire();
			limiteEquipe.acquire();
			entrarNaPista(numeroEquipe);
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			limiteEquipe.release();
			limitePista.release();
			liberarPista(numeroEquipe);
		}
	}	
	
	public void percorrerPista(){
		
		for(int i = 0; i < 3; i++){
			
			Random random = new Random();
			
			int tempo;
			tempo = random.nextInt((300 - 60) + 1) + 60;
			
			System.out.println("Duracao da " + (i+1) + "o. volta do #" + idCarro + ", equipe " + numeroEquipe + ": " + tempo);
			
			if(tempo < tempoMaisRapido[idCarro])
				tempoMaisRapido[idCarro] = tempo;
		
			try{
				sleep((long) tempo);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		System.out.println("#" + idCarro + " da equipe " + numeroEquipe + " finalizou as voltas e teve o tempo de " + tempoMaisRapido[idCarro] + " segundos.");
	}
	
	public void entrarNaPista(int numeroEquipe){

		System.out.println("#" + idCarro + " da equipe " + numeroEquipe + " entrou na pista.");
		percorrerPista();
		
	}
	
	public void liberarPista(int numeroEquipe){
		System.out.println("#" + idCarro + " da equipe " + numeroEquipe + " liberou a pista.");
	}
	
}
