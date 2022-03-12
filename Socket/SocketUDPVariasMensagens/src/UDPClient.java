
/*
Cria uma mensagem no IP 127.0.0.1 (LocalHost)
Manda a mensagem para a porta 9876, a mesma do Servidor.
*/
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

class UDPClient {
	public static void main(String args[]) throws Exception {
		BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
		DatagramSocket clientSocket = new DatagramSocket();
		InetAddress IPAddress = InetAddress.getByName("127.0.0.1");
		String sentence = inFromUser.readLine();
		while (!sentence.equals("fim")) {
			byte[] sendData = new byte[1024];
			byte[] receiveData = new byte[1024];
			sendData = sentence.getBytes();
			DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 9876);
			clientSocket.send(sendPacket);
			DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
			clientSocket.receive(receivePacket);
			String modifiedSentence = new String(receivePacket.getData());
			System.out.println("Do servidor:" + modifiedSentence);
			sentence = inFromUser.readLine();
		}
		clientSocket.close();
	}
}
