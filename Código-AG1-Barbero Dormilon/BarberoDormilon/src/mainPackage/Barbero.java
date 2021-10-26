package mainPackage;

public class Barbero extends Thread{
	
	private Barberia barberia;
	private int clientesAtendidos;

	public Barbero(Barberia barberia) {
		this.barberia = barberia;
		clientesAtendidos =0;
	}
	
	public void run() {
		int retardo;
		int numeroCliente;
		while(barberia.isClientesPendientes()) {
			try {
				retardo = (int) (Math.random() * 1000)+1000;	//tiempo que tarda el barbero en cortar el pelo entre 1s y 2s
				numeroCliente = barberia.terminarCliente();		//llama a la funcion para terminar un cliente en la clase barberia
				
				System.out.println("El Barbero va a cortarle el pelo al cliente "+ numeroCliente);
				System.out.println("Sillas libres: "+ barberia.sillasLibres);
				System.out.println();
				
				sleep(retardo);		//se duerme durante el tiempo declarado arriba
				
				System.out.println("El Barbero ha TERMINADO de cortar el pelo al cliente "+numeroCliente+" en un tiempo de "+retardo+" ms");
				System.out.println();
				
				clientesAtendidos++;
				
			}catch(InterruptedException e){
				e.printStackTrace();
			}
		}
	}
	
	public void clientesTotalesAtendidos() {
		System.out.println("Clientes atendidos en el dia de hoy: "+ clientesAtendidos);
	}
}
