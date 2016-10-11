package com.cooksys.ftd.assignments.socket;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import com.cooksys.ftd.assignments.socket.model.Config;
import com.cooksys.ftd.assignments.socket.model.Student;

public class Server extends Utils {

	/**
	 * Reads a {@link Student} object from the given file path
	 *
	 * @param studentFilePath
	 *            the file path from which to read the student config file
	 * @param jaxb
	 *            the JAXB context to use during unmarshalling
	 * @return a {@link Student} object unmarshalled from the given file path
	 * @throws JAXBException
	 */
	public static Student loadStudent(String studentFilePath, JAXBContext jaxb) throws JAXBException {

		if (jaxb == null)
			jaxb = JAXBContext.newInstance(Student.class);

		File studentConfig = new File(studentFilePath);
		Unmarshaller unmarshaller = jaxb.createUnmarshaller();
		Student student = (Student) unmarshaller.unmarshal(studentConfig);

		return student;
	}

	/**
	 * The server should load a
	 * {@link com.cooksys.ftd.assignments.socket.model.Config} object from the
	 * <project-root>/config/config.xml path, using the "port" property of the
	 * embedded {@link com.cooksys.ftd.assignments.socket.model.LocalConfig}
	 * object to create a server socket that listens for connections on the
	 * configured port.
	 *
	 * Upon receiving a connection, the server should unmarshal a
	 * {@link Student} object from a file location specified by the config's
	 * "studentFilePath" property. It should then re-marshal the object to xml
	 * over the socket's output stream, sending the object to the client.
	 *
	 * Following this transaction, the server may shut down or listen for more
	 * connections.
	 * 
	 * @throws JAXBException
	 * @throws IOException
	 */
	public static void main(String[] args) {

		try {
			JAXBContext jaxb = JAXBContext.newInstance(Config.class, Student.class);
			Unmarshaller unmarshaller = jaxb.createUnmarshaller();

			File configFile = new File("config/config.xml");
			Config config = (Config) unmarshaller.unmarshal(configFile);
			int port = config.getLocal().getPort();

			//////////////////////////////////////////////////////////
			ServerSocket ss = new ServerSocket(port);
			System.out.println("listening on port# " + port + "...");
			Socket clientSocket = ss.accept();
			/////////////////////////////////////////////////////////

			Student student = loadStudent(config.getStudentFilePath(), jaxb);

			Marshaller marshaller = jaxb.createMarshaller();

			marshaller.marshal(student, clientSocket.getOutputStream());
			System.out.println("successfully sent Student");
			// closing stuff
			clientSocket.close();
			ss.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
