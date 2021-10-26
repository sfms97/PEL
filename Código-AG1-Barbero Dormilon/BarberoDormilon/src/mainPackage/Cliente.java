package mainPackage;

public class Cliente extends Thread {

	private int identificador;
	private Barberia barberia;
	
	public Cliente(int identif, Barberia barberia) {
		this.barberia = barberia;
		this.identificador = identif;
	}
	
	public void run() {
		barberia.nuevoCliente(identificador); 
	}
}
