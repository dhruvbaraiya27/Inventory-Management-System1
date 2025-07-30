package com.inventory.designpattern.command;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class NetworkManager {
	
	int serverPort = 1234;
	
	public NetworkManager() {
		
	}
	
	public void Server() throws IOException{
		DatagramSocket datagramSocket = new DatagramSocket(serverPort);
		byte[] receiveBuffer = new byte[10000];
		
		DatagramPacket datagramPacket = null;
		System.out.println("***** Server Listening on Port ***********");
		while(true) {
			datagramPacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
			
			datagramSocket.receive(datagramPacket);

			System.out.print("\n***** Data sent by Client *****\n"+ data(receiveBuffer));
			System.out.println();
		
			if (data(receiveBuffer).toString().equalsIgnoreCase("bye")) {
				System.out.println("Exiting");
				break;
			}
			receiveBuffer = new byte[10000];
			
		}
	datagramSocket.disconnect();
	datagramSocket.close();
	datagramSocket.setSoTimeout(10);
	}
	
	private static StringBuilder data(byte[] a) {
		if(a ==null) {
			return null;
		}
		StringBuilder ret = new StringBuilder();
		int i = 0;
		while(a[i] != 0) {
			ret.append((char) a[i]);
			i++;
		}
		return ret;
	}

	public void Client(String message) throws IOException{
		Scanner scanner = new Scanner(System.in);
		String input = "NO message";
		DatagramSocket clientSocket = new DatagramSocket();
		InetAddress localAddress = InetAddress.getLocalHost();
		byte buffer[] = null;
		
		while(true) {
			
			input = message;
			buffer = input.getBytes();
			
			DatagramPacket sendPacket = new DatagramPacket(buffer, buffer.length, localAddress, serverPort);
			
			clientSocket.send(sendPacket);
			
			break;
		}
		clientSocket.disconnect();
		scanner.close();
		clientSocket.close();
	}
} 