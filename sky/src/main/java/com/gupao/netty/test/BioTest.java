package com.gupao.netty.test;

import java.io.IOException;

import com.gupao.netty.client.BioClient;
import com.gupao.netty.server.BioServier;

public class BioTest {

	public static void main(String[] args) throws InterruptedException {
		new Thread(new Runnable() {
			
			public void run() {
				try {
					BioServier.bioStart();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
			}
		}).start();
		
		//此处为避免客户端比服务端先启动，此处先睡眠
		Thread.sleep(100);
		new Thread(new Runnable() {
			
			public void run() {
				String exprssion = "hello BioServer";
				try {
					BioClient.bioSend(exprssion);
					Thread.sleep(1000);
				} catch (IOException e) {
					e.printStackTrace();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
			}
		}).start();	

	}

}