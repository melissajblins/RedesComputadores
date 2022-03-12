/*
É um servidor TCP.
Porta 9000.
Necessário executar primeiro o servidor.
*/

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

class TCPServer {

	public static void main(String args[]) throws Exception {

		ServerSocket serverSocket = new ServerSocket(9001);
		System.out.println("Esperando conexão na porta 9001");
		Socket s = serverSocket.accept();
		String sentence = "Redes";
		while (!(sentence.equals("encerrar"))) {
			System.out.println("Conexão estabelecida de " + s.getInetAddress());

			BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream()));
			String input = br.readLine();

			if (input != null) {
				DataOutputStream output = new DataOutputStream(s.getOutputStream());
				output.writeBytes(input.toUpperCase() + "\n");
				sentence = input;
			} else {
				sentence = "encerrar";
			}
		}
		s.close();
	}
}
