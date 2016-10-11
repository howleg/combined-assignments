package com.cooksys.ftd.assignments.socket;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import com.cooksys.ftd.assignments.socket.model.Config;
import com.cooksys.ftd.assignments.socket.model.LocalConfig;
import com.cooksys.ftd.assignments.socket.model.RemoteConfig;
import com.cooksys.ftd.assignments.socket.model.Student;

public class Trey {

	public static void main(String[] args) throws JAXBException {
		
		//just to generate the xml for me
				
		LocalConfig lConfig = new LocalConfig();
		lConfig.setPort(21);
		
		RemoteConfig rConfig = new RemoteConfig();
		rConfig.setPort(80);
		rConfig.setHost("127.0.0.1");
		
		Config config = new Config();
		config.setLocal(lConfig);
		config.setRemote(rConfig);
		config.setStudentFilePath("test/stud");
		
		
		JAXBContext jaxb = JAXBContext.newInstance(Config.class,Student.class);
		Marshaller marshaller = jaxb.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		
		
		File configFile = new File("test/config.xml");
		marshaller.marshal(config, configFile);
		
		
		
		Student stud = new Student();
		stud.setFavoriteIDE("VisualStudio");
		stud.setFavoriteParadigm("F It!");
		stud.setFirstName("Jim");
		stud.setLastName("Bob");
		stud.setFavoriteLanguage("C#");
		
		
		File studFile = new File("test/stud.xml");
		marshaller.marshal(stud, studFile);


	}

}
