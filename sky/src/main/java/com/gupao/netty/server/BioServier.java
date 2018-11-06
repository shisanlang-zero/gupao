package com.gupao.netty.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * BIO服务器端
 * @author Administrator
 *
 */
public class BioServier {
	private static int defaultPort = 7777;
	
	private static  ServerSocket serverSocket;
	
	public static void bioStart() throws IOException {
		start(defaultPort);
	}

	private synchronized static void start(int port) throws IOException {
		if(serverSocket != null) {
			return;
		}
		
		try {
			serverSocket = new ServerSocket(port);
			System.out.println("服务端已经启动，端口号为：" + port);
			
			while (true) {
				//接收一个客户端的请求
				Socket accept = serverSocket.accept();
				System.out.println("服务器端接受到一个请求");
				//为该请求分配一个线程处理
				new Thread(new BioServerHandler(accept)).start();
			}
			
			
			
		} finally {
			if(serverSocket != null) {
				serverSocket.close();
				serverSocket = null;
			}
		}
		
	}

}
