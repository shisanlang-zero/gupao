package com.gupao.netty.reactor;

import java.io.IOException;
import java.nio.channels.SocketChannel;

public class Acceptor implements Runnable {
	private Reactor reactor;

	public Acceptor(Reactor reactor) {
		this.reactor = reactor;
	}

	public void run() {
		try {
			//接受一个客户端发送过来的channel请求
			SocketChannel socketChannel = reactor.serverSocketChannel.accept();
			//ServerSocketChannel可以设置成非阻塞模式。在非阻塞模式下，accept() 方法会立刻返回，如果还没有新进来的连接，返回的将是null。 
			//因此，需要检查返回的SocketChannel是否是null
			if (socketChannel != null) {
				// 调用Handler来处理channel
				System.out.println("调用Handler操作类来处理channel");
				new SocketReadHandler(reactor.selector, socketChannel);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
