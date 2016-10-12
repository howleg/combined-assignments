package com.cooksys.ftd.assignments.concurrency;

import java.io.IOException;
import java.net.Socket;
import java.time.LocalTime;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import com.cooksys.ftd.assignments.concurrency.model.message.Request;
import com.cooksys.ftd.assignments.concurrency.model.message.RequestType;
import com.cooksys.ftd.assignments.concurrency.model.message.Response;

public class ClientHandler implements Runnable {

	private Socket clientSocket;

	public ClientHandler(Socket clientSocket) {
		this.clientSocket = clientSocket;
	}

	@Override
	public void run() {

		try {

			JAXBContext jaxb = JAXBContext.newInstance(Request.class, RequestType.class, Response.class);
			Marshaller marshaller = jaxb.createMarshaller();
			Unmarshaller unMarshaller = jaxb.createUnmarshaller();

			while (!clientSocket.isClosed()) {
				Request request = (Request) unMarshaller.unmarshal(clientSocket.getInputStream());
				RequestType requestType = request.getType();

				switch (requestType) {

				case TIME:
					marshaller.marshal(new Response(requestType, LocalTime.now().toString(), true),
							clientSocket.getOutputStream());
					break;

				case IDENTITY:
					marshaller.marshal(new Response(requestType, clientSocket.getLocalAddress().toString(), true),
							clientSocket.getOutputStream());
					break;

				case DONE:
					marshaller.marshal(new Response(requestType, "DONE", true), clientSocket.getOutputStream());
					break;

				}
			}

		} catch (JAXBException | IOException e) {
			e.printStackTrace();
		}

	}
}
