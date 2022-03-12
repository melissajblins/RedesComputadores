
/*
É um servidor UDP.
Porta 9876.
Necessário executar primeiro o servidor.
*/
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.HashSet;
import java.util.Set;

class UDPServer {
	public static void main(String args[]) throws Exception {
		// Cria lista de usuários permitidos com base nos IPs
		Set<String> listaIP = new HashSet<String>();
		listaIP.add("127.0.0.1"); // LocalHost
		listaIP.add("52.67.192.255"); // Amazon
		listaIP.add("152.0.131.132"); // Netflix

		DatagramSocket serverSocket = new DatagramSocket(9876);
		byte[] receiveData = new byte[1024];
		byte[] sendData = new byte[1024];
		while (true) {
			DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
			System.out.println("Servidor aguardando...");
			
			serverSocket.receive(receivePacket);
			String sentence = new String(receivePacket.getData());
			System.out.println("Mensagem recebida: " + sentence);
			
			// Verficando se o IP está na lista de IPs
			InetAddress IPAddress = receivePacket.getAddress();
			if (listaIP.contains(IPAddress.getHostAddress())) {
				int port = receivePacket.getPort();
				String capitalizedSentence = sentence.toUpperCase();
				sendData = capitalizedSentence.getBytes();
				DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
				serverSocket.send(sendPacket);
			}
		}
	}
}
