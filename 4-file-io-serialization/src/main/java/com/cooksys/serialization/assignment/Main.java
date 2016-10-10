package com.cooksys.serialization.assignment;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import com.cooksys.serialization.assignment.model.Contact;
import com.cooksys.serialization.assignment.model.Instructor;
import com.cooksys.serialization.assignment.model.Session;
import com.cooksys.serialization.assignment.model.Student;

public class Main {

	/**
	 * Creates a {@link Student} object using the given studentContactFile. The
	 * studentContactFile should be an XML file containing the marshaled form of
	 * a {@link Contact} object.
	 *
	 * @param studentContactFile
	 *            the XML file to use
	 * @param jaxb
	 *            the JAXB context to use
	 * @return a {@link Student} object built using the {@link Contact} data in
	 *         the given file
	 * 
	 * 
	 * @throws JAXBException
	 */
	public static Student readStudent(File studentContactFile, JAXBContext jaxb) throws JAXBException {

		Unmarshaller unMarshaller = jaxb.createUnmarshaller();

		Contact contactInfo = (Contact) unMarshaller.unmarshal(studentContactFile);

		Student someStudent = new Student(contactInfo);

		return someStudent;

	}

	/**
	 * Creates a list of {@link Student} objects using the given directory of
	 * student contact files.
	 *
	 * @param studentDirectory
	 *            the directory of student contact files to use
	 * @param jaxb
	 *            the JAXB context to use
	 * @return a list of {@link Student} objects built using the contact files
	 *         in the given directory
	 * @throws JAXBException
	 */
	public static List<Student> readStudents(File studentDirectory, JAXBContext jaxb) throws JAXBException {

		Unmarshaller unMarshaller = jaxb.createUnmarshaller();

		List<Student> students = new ArrayList<>();

		for (File x : studentDirectory.listFiles()) {
			students.add(new Student((Contact) unMarshaller.unmarshal(x)));
		}

		return students;

	}

	/**
	 * Creates an {@link Instructor} object using the given
	 * instructorContactFile. The instructorContactFile should be an XML file
	 * containing the marshaled form of a {@link Contact} object.
	 *
	 * @param instructorContactFile
	 *            the XML file to use
	 * @param jaxb
	 *            the JAXB context to use
	 * @return an {@link Instructor} object built using the {@link Contact} data
	 *         in the given file
	 * 
	 * @throws JAXBException
	 */
	public static Instructor readInstructor(File instructorContactFile, JAXBContext jaxb) throws JAXBException {

		Unmarshaller unMarshaller = jaxb.createUnmarshaller();

		Contact contactInfo = (Contact) unMarshaller.unmarshal(instructorContactFile);

		Instructor instructor = new Instructor(contactInfo);

		return instructor;

	}

	/**
	 * Creates a {@link Session} object using the given rootDirectory. A
	 * {@link Session} root directory is named after the location of the
	 * {@link Session}, and contains a directory named after the start date of
	 * the {@link Session}. The start date directory in turn contains a
	 * directory named `students`, which contains contact files for the students
	 * in the session. The start date directory also contains an instructor
	 * contact file named `instructor.xml`.
	 *
	 * @param rootDirectory
	 *            the root directory of the session data, named after the
	 *            session location
	 * @param jaxb
	 *            the JAXB context to use
	 * @return a {@link Session} object built from the data in the given
	 *         directory
	 * @throws JAXBException 
	 */
	public static Session readSession(File rootDirectory, JAXBContext jaxb) throws JAXBException {

		Session session = new Session();

		session.setLocation(rootDirectory.getName());

		File f = rootDirectory.listFiles()[0];

		session.setStartDate(f.getName());

		File[] listy = f.listFiles();

		session.setInstructor(readInstructor(listy[0], jaxb));
		session.setStudents(readStudents(listy[1], jaxb));

		return session;
		// Session s = new Session();

	}

	/**
	 * Writes a given session to a given XML file
	 *
	 * @param session
	 *            the session to write to the given file
	 * @param sessionFile
	 *            the file to which the session is to be written
	 * @param jaxb
	 *            the JAXB context to use
	 * @throws JAXBException
	 */
	public static void writeSession(Session session, File sessionFile, JAXBContext jaxb) throws JAXBException {

		Marshaller marshal = jaxb.createMarshaller();

		marshal.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

		marshal.marshal(session, sessionFile);

	}

	/**
	 * Main Method Execution Steps: 1. Configure JAXB for the classes in the
	 * com.cooksys.serialization.assignment.model package 2. Read a session
	 * object from the <project-root>/input/memphis/ directory using the methods
	 * defined above 3. Write the session object to the
	 * <project-root>/output/session.xml file.
	 *
	 * JAXB Annotations and Configuration: You will have to add JAXB annotations
	 * to the classes in the com.cooksys.serialization.assignment.model package
	 *
	 * Check the XML files in the <project-root>/input/ directory to determine
	 * how to configure the {@link Contact} JAXB annotations
	 *
	 * The {@link Session} object should marshal to look like the following:
	 * <session location="..." start-date="..."> <instructor> <contact>...
	 * </contact> </instructor> <students> ...
	 * <student> <contact>...</contact> </student> ... </students> </session>
	 * 
	 * @throws JAXBException
	 */
	public static void main(String[] args) throws JAXBException {

		JAXBContext jaxbContext = JAXBContext.newInstance(Contact.class, Session.class);

		File inputFile = new File("input/memphis");
		File outputFile = new File("output/session.xml");

		Session session = readSession(inputFile, jaxbContext);

		writeSession(session, outputFile, jaxbContext);

	}
}
