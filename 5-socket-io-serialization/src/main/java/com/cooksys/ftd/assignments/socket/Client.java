package com.cooksys.ftd.assignments.socket;

import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import com.cooksys.ftd.assignments.socket.model.Config;
import com.cooksys.ftd.assignments.socket.model.Student;

public class Client {

	/**
	 * The client should load a
	 * {@link com.cooksys.ftd.assignments.socket.model.Config} object from the
	 * <project-root>/config/config.xml path, using the "port" and "host"
	 * properties of the embedded
	 * {@link com.cooksys.ftd.assignments.socket.model.RemoteConfig} object to
	 * create a socket that connects to a {@link Server} listening on the given
	 * host and port.
	 *
	 * The client should expect the server to send a
	 * {@link com.cooksys.ftd.assignments.socket.model.Student} object over the
	 * socket as xml, and should unmarshal that object before printing its
	 * details to the console.
	 * 
	 * @throws JAXBException
	 * @throws IOException
	 * @throws UnknownHostException
	 */
	public static void main(String[] args) {

		try {
			// creates a JaxBContext and an unMarshaller from that jaxb
			JAXBContext jaxb = JAXBContext.newInstance(Student.class, Config.class);
			Unmarshaller unmarshaller = jaxb.createUnmarshaller();

			File configFile = new File("config/config.xml");

			Config config = (Config) unmarshaller.unmarshal(configFile);

			int port = config.getLocal().getPort();
			String host = config.getRemote().getHost();

			Socket socket = new Socket(host, port);

			Student student = (Student) unmarshaller.unmarshal(socket.getInputStream());

			System.out.println(student.toString());

			socket.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
