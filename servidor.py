
import socket
import threading
import sys
import pickle
import os

class Servidor():
    
    # 1. Al iniciar el servidor pedimos que nos indiquen el puerto a usar
	def __init__(self, host=socket.gethostname(), port = input("Introduce el puerto que desea usar: ")):
		self.clientes = []
		self.mensajes = []
        
        # Mostramos la IP del servidor que usaran los clientes para conectar y levantamos la conexion
		print("Su IP es: " + socket.gethostbyname(host))
		self.sock = socket.socket()
		self.sock.bind((str(host), int(port)))
		self.sock.listen(20)
		self.sock.setblocking(False)
        
        # Iniciamos los daemons
		accept = threading.Thread(target=self.aceptarC)
		process = threading.Thread(target=self.procesarC)

		accept.daemon = True
		accept.start()

		process.daemon = True
		process.start()
		
		for thread in threading.enumerate():
			print("Hilo: " + thread.name + "\n" + "PID: "+ str(os.getpid()) +  "\n")
            
        # Mostramos usuarios activos
		print("Users activos: " + str(threading.activeCount()))

		while True:
            #Opcion para salir 
			msg = input('SALIR = Q\n')
			if msg == 'Q':

				print("* Adiós*")
				self.sock.close()
				sys.exit()
			else:
				pass


    #Funcion para establecer una conexion entre clientes y puedan recibir mensajes
	def broadcast(self, msg, cliente):
		self.mensajes.append(pickle.loads(msg))
		print("Mensajes: " + str(pickle.loads(msg)))
		for c in self.clientes:
			try:
				if c != cliente:
					c.send(msg)
					 # Almacenamos el historial del chat en un txt
					archivo = open("u22033190AI1.txt", "a")
					archivo.write(str(pickle.loads(msg)+"\n"))
					archivo.close()
			except:
				self.clientes.remove(c)

    #Funcion para aceptar la conexion
	def aceptarC(self):
		while True:
			try:
				conn, addr = self.sock.accept()
				print(f"\nConexion aceptada via {conn}\n")
				conn.setblocking(False)
				self.clientes.append(conn)
				for client in self.clientes: 
					data = pickle.dumps(client.username + 'connected')
					self.broadcast(data,client) 
			except:
				pass

    #Funcion para procesar el envio de mensajes
	def procesarC(self):
		
		while True:
			if len(self.clientes) > 0:
				for c in self.clientes:
					try:
						data = c.recv(32)
						if data:
							self.broadcast(data,c)
					except:
						pass
	

start = Servidor()