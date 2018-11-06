package com.gupao.netty.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class BioClient {
	//端口
	private static int defaultPort = 7777;
	//IP
	private static String defaultIp = "127.0.0.1";

	public static void bioSend(String exprssion) throws IOException {
		send(defaultPort, exprssion);
	}

	private static void send(int port, String exprssion) throws IOException {
		System.out.println("客户端开始封装请求信息");
		BufferedReader in = null;
		PrintWriter out = null;
		Socket socket = null;
		//向服务器段建立请求连接
		try {
			socket = new Socket(defaultIp, port);
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new PrintWriter(socket.getOutputStream(), true);
			out.println(exprssion +"\r\n");
			System.out.println("客户端返回结果是："+ in.readLine());
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(in != null) {
				in.close();
				in = null;
			}
			
			if(out != null) {
				out.close();
				out = null;
			}
			
			if(socket != null) {
				socket.close();
				socket = null;
			}
		}
		
	}
	
	
}
