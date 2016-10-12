package com.cooksys.ftd.assignments.concurrency;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicInteger;

import com.cooksys.ftd.assignments.concurrency.model.config.ServerConfig;

public class Server implements Runnable {

	static AtomicInteger numHandlers = new AtomicInteger();

	private int maxClients;
	private int port;
	private ServerSocket ss;
	private boolean disabled;

	public Server(ServerConfig config) throws IOException {
		this.maxClients = config.getMaxClients();
		this.port = config.getPort();
		this.ss = new ServerSocket(port);
		this.disabled = config.isDisabled();
	}

	@Override
	public void run() {

		if (!disabled) {

			while (true) {

				if (maxClients < 0 || numHandlers.get() < maxClients) {

					try {
						Socket clientSocket = ss.accept();
						numHandlers.incrementAndGet();
						new Thread(new ClientHandler(clientSocket)).start();
					} catch (IOException e) {
						e.printStackTrace();
					}

				}

			}
		}

	}
}
