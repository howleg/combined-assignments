package com.cooksys.ftd.assignments.socket;

public class Launcher {

	public static void main(String[] args) throws InterruptedException {

		Thread serverThread = new Thread(new Server());
		Thread clientThread = new Thread(new Client());

		serverThread.start();
		Thread.sleep(1000 );
		clientThread.start();
	}

}
