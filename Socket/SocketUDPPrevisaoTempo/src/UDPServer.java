/*
É um servidor UDP.
Porta 9876.
Necessário executar primeiro o servidor.
*/

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

class UDPServer {

	// Sortear a previsão do tempo para cada dia
	public static int getRandomNumber(int min, int max) {
		return (int) ((Math.random() * (max - min)) + min);
	}

	public static void main(String args[]) throws Exception {
		// Cria lista de temperaturas
		List<String> listaTemperatura = new ArrayList<String>();
		listaTemperatura.add("Min: 20ºC - Max: 25°C");
		listaTemperatura.add("Min: 25ºC - Max: 28°C");
		listaTemperatura.add("Min: 10ºC - Max: 15°C");
		listaTemperatura.add("Min: 30ºC - Max: 34°C");
		listaTemperatura.add("Min: -2ºC - Max: 4°C");
		listaTemperatura.add("Min: 20ºC - Max: 22°C");

		// Cria lista de possibilidade de chuva
		List<String> listaChuva = new ArrayList<String>();
		listaChuva.add("Possibilidade: 20%");
		listaChuva.add("Possibilidade: 25%");
		listaChuva.add("Possibilidade: 50%");
		listaChuva.add("Possibilidade: 5%");
		listaChuva.add("Possibilidade: 80%");

		// Cria set de cidades
		Set<String> cidades = new HashSet<>(Arrays.asList("SAO PAULO", "RIO DE JANEIRO", "CACONDE"));

		// Cria lista de usuários permitidos com base nos IPs
		Set<String> listaIP = new HashSet<String>();
		listaIP.add("127.0.0.1"); // LocalHost
		listaIP.add("52.67.192.255"); // Amazon
		listaIP.add("152.0.131.132"); // Netflix

		try (DatagramSocket serverSocket = new DatagramSocket(9876)) {
			while (true) {
				byte[] receiveData = new byte[1024];
				byte[] sendData = new byte[1024];

				DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
				System.out.println("Servidor aguardando...");

				// Recebendo solicitação de conexão
				serverSocket.receive(receivePacket);
				InetAddress IPAddress = receivePacket.getAddress();

				// Averiguando a solicitação do cliente por confirmação na lista de IPs
				if (listaIP.contains(IPAddress.getHostAddress())) {

					// Conexão aceita e notificação para o usuário
					int port = receivePacket.getPort();
					String mensagemConexaoAceita = "Conexão Aceita!\nEnvie o nome da cidade: ";
					sendData = mensagemConexaoAceita.getBytes();
					DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
					serverSocket.send(sendPacket);

					// Aguardando nome da cidade
					receiveData = new byte[1024];
					receivePacket = new DatagramPacket(receiveData, receiveData.length);
					System.out.println("Servidor aguardando cidade...");
					serverSocket.receive(receivePacket);
					String cidade = new String(receivePacket.getData());
					System.out.println("Cidade recebida: " + cidade);
		
					// Aguardando número de dias
					sendData = "Envie a quantidade de dias: ".getBytes();
					sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
					serverSocket.send(sendPacket);
					receiveData = new byte[1024];
					receivePacket = new DatagramPacket(receiveData, receiveData.length);
					serverSocket.receive(receivePacket);
					
					// Convertendo quantidade de dias para tipo int
					int qtdDias = ByteBuffer.wrap(receivePacket.getData()).getInt();
					System.out.println("Quantidade de dias recebida: " + qtdDias);
					
					// Iterando a cada dia
					String resposta = "\n";
					for (int i = 0; i < qtdDias; i++) {
						String temperatura = listaTemperatura.get(getRandomNumber(0, listaTemperatura.size() - 1));
						String chuva = listaChuva.get(getRandomNumber(0, listaChuva.size() - 1));
						resposta += "Dia: " + (i + 1) + "; Temp: " + temperatura + "; Possibilidade de Chuva: " + chuva
								+ "\n";
					}

					// Enviando ao usuário a previsão do tempo para cada dia 
					sendData = resposta.getBytes();
					sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
					serverSocket.send(sendPacket);

				}
			}
		}
	}
}
