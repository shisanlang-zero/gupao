package com.gupao.netty.reactor;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class ReactorTest {

	public static void main(String[] args) throws IOException, InterruptedException {
		new Thread(new Reactor(7777)).start();

		Thread.sleep(100);

		new Thread(new Runnable() {

			public void run() {
				SocketChannel socketChannel = null;
				try {
					socketChannel = SocketChannel.open();
					//建立一个通道连接
					socketChannel.connect(new InetSocketAddress(InetAddress.getLocalHost(), 7777));
					//设置为非阻塞模式，可以异步写入数据
//					socketChannel.configureBlocking(false);
					String newData = "input String to write to file..." + System.currentTimeMillis();

					//非直接缓冲区：通过 allocate() 方法分配缓冲区，将缓冲区建立在 JVM 的内存中
					ByteBuffer buf = ByteBuffer.allocate(48);
					//直接缓冲区：通过 allocateDirect() 方法分配直接缓冲区，将缓冲区建立在物理内存中。可以提高效率
//					ByteBuffer buf2 = ByteBuffer.allocateDirect(48);
					buf.clear();
					buf.put(newData.getBytes());
					buf.flip();
					
					//如果SocketChannel在非阻塞模式下，此时调用connect()，该方法可能在连接建立之前就返回了。为了确定连接是否建立，
					//可以调用finishConnect()的方法
//					while(!socketChannel.finishConnect() ){
//						//此处写操作
//					}
					
					
					
					//向通道input数据
					while (buf.hasRemaining()) {//判断buf中是否还有数据元素
						socketChannel.write(buf);
					}
					
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					try {
						socketChannel.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}

			}
		}).start();

	}

}