/*
Cria uma mensagem no IP 127.0.0.1 (LocalHost)
Manda a mensagem para a porta 9000, a mesma do Servidor.
*/

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

public class TCPClient {

	public static void main(String args[]) throws Exception {

		Socket s = new Socket("127.0.0.1", 9001);

		OutputStream os = s.getOutputStream();
		DataOutputStream serverWriter = new DataOutputStream(os);

		InputStreamReader isrServer = new InputStreamReader(s.getInputStream());
		BufferedReader serverReader = new BufferedReader(isrServer);

		BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
		String sentence = inFromUser.readLine();

		while (!sentence.equals("encerrar")) {
			serverWriter.writeBytes(sentence + "\n");

			String response = serverReader.readLine();
			System.out.println(response);

			sentence = inFromUser.readLine();
		}
		s.close();
	}
}
