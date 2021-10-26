package mainPackage;

import java.util.PriorityQueue;
import java.util.concurrent.Semaphore;

public class Barberia extends Thread {

	Semaphore semaforo;		//semaforo para controlar a los clientes
	Semaphore mutex;		//semaforo para controlar al barbero
	PriorityQueue <Integer> listaClientes;
	int sillasLibres;
	
	
	public Barberia(int sillasTotales) {
		semaforo = new Semaphore(1);	//inicializa el semaforo de los clientes
		mutex = new Semaphore(1);		//inicializa el semaforo del barbero
		
		listaClientes = new PriorityQueue <Integer>();
		this.sillasLibres = sillasTotales;
	}
	
	
	public void nuevoCliente(int numeroCliente) {
		
		int tiempoEntreClientes = (int) (Math.random() * 500)+500;		//tiempo de aparicion de clientes entre 500ms y 1s
		
		try {
			semaforo.acquire();	// adquiere el semaforo para que no pueda usarlo nadie mas
			
			if(sillasLibres>0) {
				
				sillasLibres--;
				listaClientes.add(numeroCliente);
				
				System.out.println("El cliente "+numeroCliente+" ENTRA en la barberia");
				System.out.println("Sillas libres: "+sillasLibres);
				System.out.println();
				
				
			}
			else {
				
				System.out.println("El cliente "+numeroCliente+" ENTRA en la barberia pero al no haber espacio se MARCHA");
				System.out.println();
				
			}
			
			sleep(tiempoEntreClientes);	//se duerme durante un tiempo hasta el siguiente cliente
			semaforo.release();	//libera el semaforo para que pueda ser utilizado por el siguiente cliente
			
			
			
		}
		catch(InterruptedException e){
			e.printStackTrace();
		}
	}
	
	
	
	
	public int terminarCliente() {
		int cliente=0;
		try {
			if(isClientesPendientes()) {
				
				mutex.acquire();	//adquiere otro semaforo encargado de controlar solo al barbero
				cliente = listaClientes.poll();	//expulsa al cliente de la lista de clientes
				sillasLibres++;
				mutex.release();	//libera el semaforo del barbero
				
			}
		}catch(InterruptedException e) {
			e.printStackTrace();
		}
		return cliente;
	}
	
	
	
	
	
	public boolean isClientesPendientes() {		//devuelve true o false dependiendo de si quedan clientes en la lista
		if(listaClientes.size()>0) {
			return true;
		}
		else {
			return false;
		}
	}
}

