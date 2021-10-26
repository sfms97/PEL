package mainPackage;

public class Main {

	public static void main(String [] args) {
		
		int i;
		int sillasTotales = 3;
		int clientRandom = (int) (Math.random() * 10)+5;	//numero de clientes del dia entre 5 y 15
		Barberia barberia = new Barberia(sillasTotales);
		Cliente clientes[] = new Cliente[clientRandom];
		
		System.out.println("En el dia de hoy van a llegar "+clientRandom+" clientes");
		System.out.println("Hay "+sillasTotales+" sillas en la sala de espera");
		System.out.println("------------------------------------------------");
		System.out.println();
		
		
		
		for(i=0; i< clientRandom; i++) {
			clientes[i] = new Cliente(i+1, barberia);
			clientes[i].start();	//comienzan a llegar los clientes (se llama al metodo run() de la clase cliente)
			
		}
		
		Barbero barbero = new Barbero(barberia);	//se crea el barbero
		barbero.start();	//comienza a trabajar el barbero (se llama al metodo run() de la clase barbero)
		
		
		for(i=0; i<clientRandom; i++) {
			
			try {
				clientes[i].join();		//se terminan los procesos de clientes
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		try {
			barbero.join();		//se termina el proceso del barbero
		}catch(InterruptedException e) {
			e.printStackTrace();
		}
		
		barbero.clientesTotalesAtendidos();
		System.out.println("Se cierra la barberia");
	}
}
