package com.cooksys.ftd.assignments.concurrency;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

import javax.xml.bind.JAXBException;

import com.cooksys.ftd.assignments.concurrency.model.config.ClientConfig;
import com.cooksys.ftd.assignments.concurrency.model.config.Config;
import com.cooksys.ftd.assignments.concurrency.model.config.ServerConfig;

public class Main {

	/**
	 * First, load a
	 * {@link com.cooksys.ftd.assignments.concurrency.model.config.Config}
	 * object from the <project-root>/config/config.xml file.
	 *
	 * If the embedded
	 * {@link com.cooksys.ftd.assignments.concurrency.model.config.ServerConfig}
	 * object is not disabled, create a {@link Server} object with the server
	 * config and spin off a thread to run it.
	 *
	 * If the embedded
	 * {@link com.cooksys.ftd.assignments.concurrency.model.config.ClientConfig}
	 * object is not disabled, create a {@link Client} object with the client
	 * config and spin off a thread to run it.
	 * 
	 * @throws JAXBException
	 * @throws IOException
	 */
	public static void main(String[] args) throws JAXBException, IOException {

		Path configPath = new File("config/config.xml").toPath();

		Config config = Config.load(configPath);

		ServerConfig serverConfig = config.getServer();
		ClientConfig clientConfig = config.getClient();

		if (!serverConfig.isDisabled()) {
			Server server = new Server(serverConfig);
			// spin off a thread to run it.
			Thread serverThread = new Thread(server);
			serverThread.start();
		}

		if (!clientConfig.isDisabled()) {
			Client client = new Client(clientConfig);
			// spin off a thread to run it.
			Thread clientThread = new Thread(client);
			clientThread.start();
		}

	}
}
