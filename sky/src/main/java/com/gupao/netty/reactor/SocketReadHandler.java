package com.gupao.netty.reactor;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

public class SocketReadHandler implements Runnable {
	private SocketChannel socketChannel;
	private String localCharset = "UTF-8"; // 编码格式

	public SocketReadHandler(Selector selector, SocketChannel socketChannel) throws IOException {
		this.socketChannel = socketChannel;
		// 设置套接字通道为非阻塞模式
		socketChannel.configureBlocking(false);
		SelectionKey selectionKey = socketChannel.register(selector, 0);
		// 将SelectionKey绑定为本Handler 下一步有事件触发时，将调用本类的run方法，如dispatch(SelectionKey key)
		selectionKey.attach(this);
		// 同时将SelectionKey标记为可读，以便读取。
		selectionKey.interestOps(SelectionKey.OP_READ);
		//唤醒
		//某个线程调用select()方法后阻塞了，即使没有通道已经就绪，也有办法让其从select()方法返回。只要让其它线程在第一个线程调用select()方法的那个对象上调用Selector.wakeup()方法即可。阻塞在select()方法上的线程会立马返回
		selector.wakeup();
	}

	/***
	 *   处理读取数据
	 */
	public void run() {
		System.out.println("实际业务Handler开始操作具体业务逻辑");
		ByteBuffer inputBuffer = ByteBuffer.allocate(1024);
		inputBuffer.clear();
		try {
			if(socketChannel.read(inputBuffer) == -1) {
				// 没有内容则关闭通道
				socketChannel.close();
			} else {
				// 将缓冲器转换为读状态
				inputBuffer.flip();
				// 将缓冲器中接收到的值按localCharset格式编码保存
				String receivedRequestData = Charset.forName(localCharset).newDecoder().decode(inputBuffer).toString();
				System.out.println("接收到客户端的请求数据：" + receivedRequestData);
				// 返回响应数据给客户端
				String responseData = "已接收到你的请求数据，响应数据为：(响应数据)";
				inputBuffer = ByteBuffer.wrap(responseData.getBytes(localCharset));
				socketChannel.write(inputBuffer);
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// 关闭通道
			try {
				socketChannel.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}