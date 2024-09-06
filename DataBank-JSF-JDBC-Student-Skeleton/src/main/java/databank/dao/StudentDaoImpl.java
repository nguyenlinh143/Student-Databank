/********************************************************************************************************2*4*w*
 * File:  StudentDaoImpl.java Course materials CST8277
 *
 * @author Linh Vo
 * Student number: 041049754
 * Email: vo000077@algonquinlive.com
 */
package databank.dao;

import static java.sql.Statement.RETURN_GENERATED_KEYS;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.context.ExternalContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletContext;
import javax.sql.DataSource;

import databank.model.StudentPojo;

@SuppressWarnings("unused")
/**
 * Description: Implements the C-R-U-D API for the database
 */
//TODO don't forget this object is a managed bean with a application scope
@Named
@ApplicationScoped
public class StudentDaoImpl implements StudentDao, Serializable {
	/** Explicitly set serialVersionUID */
	private static final long serialVersionUID = 1L;
	private static final String DATABANK_DS_JNDI = "java:app/jdbc/databank";
	private static final String READ_ALL = "SELECT * FROM student";
	private static final String READ_STUDENT_BY_ID = "SELECT * FROM student where id = ?";
	private static final String INSERT_STUDENT = "INSERT INTO student(last_name, first_name, email, phone, program, created) values (?, ?, ?, ?, ?, ?)";
	private static final String UPDATE_STUDENT_ALL_FIELDS = "UPDATE student set last_name = ?, first_name = ?, email = ?, phone = ?, program = ? where id = ?";
	private static final String DELETE_STUDENT_BY_ID = "DELETE FROM student where id = ?";

	@Inject
	protected ExternalContext externalContext;

	private void logMsg(String msg) {
		((ServletContext) externalContext.getContext()).log(msg);
	}

	@Resource(lookup = DATABANK_DS_JNDI)
	protected DataSource databankDS;

	protected Connection conn;
	protected PreparedStatement readAllPstmt;
	protected PreparedStatement readByIdPstmt;
	protected PreparedStatement createPstmt;
	protected PreparedStatement updatePstmt;
	protected PreparedStatement deleteByIdPstmt;

	@PostConstruct
	protected void buildConnectionAndStatements() {
		try {
			// TODO Initialize other PreparedStatements
			logMsg("building connection and stmts");
			conn = databankDS.getConnection();
			readAllPstmt = conn.prepareStatement(READ_ALL);
			readByIdPstmt = conn.prepareStatement(READ_STUDENT_BY_ID);
			createPstmt = conn.prepareStatement(INSERT_STUDENT, RETURN_GENERATED_KEYS);
			updatePstmt = conn.prepareStatement(UPDATE_STUDENT_ALL_FIELDS);
			deleteByIdPstmt = conn.prepareStatement(DELETE_STUDENT_BY_ID);

		} catch (Exception e) {
			logMsg("something went wrong getting connection from database:  " + e.getLocalizedMessage());
		}
	}

	@PreDestroy
	protected void closeConnectionAndStatements() {
		try {
			// TODO Close other PreparedStatements
			logMsg("closing stmts and connection");
			readAllPstmt.close();
			readByIdPstmt.close();
			createPstmt.close();
			updatePstmt.close();
			deleteByIdPstmt.close();
			conn.close();
		} catch (Exception e) {
			logMsg("something went wrong closing stmts or connection:  " + e.getLocalizedMessage());
		}
	}

	@Override
	public List<StudentPojo> readAllStudents() {
		logMsg("reading all students");
		// TODO Complete the student initialization
		List<StudentPojo> students = new ArrayList<>();
		try (ResultSet rs = readAllPstmt.executeQuery();) {

			while (rs.next()) {
				StudentPojo newStudent = new StudentPojo();
				newStudent.setId(rs.getInt("id"));
				newStudent.setLastName(rs.getString("last_name"));
				newStudent.setFirstName(rs.getString("first_name"));
				newStudent.setEmail(rs.getString("email"));
				newStudent.setPhoneNumber(rs.getString("phone"));
				newStudent.setProgram(rs.getString("program"));
				newStudent.setCreated(rs.getTimestamp("created").toLocalDateTime());
				students.add(newStudent);
			}

		} catch (SQLException e) {
			logMsg("something went wrong accessing database:  " + e.getLocalizedMessage());
		}

		return students;

	}

	@Override
	public StudentPojo createStudent(StudentPojo student) {
		logMsg("creating a student");
		// TODO Complete the insertion of a new student
		// TODO Be sure to use try-and-catch statement
		// TODO Auto-generated catch block
		try {
			createPstmt.setString(1, student.getLastName());
			createPstmt.setString(2, student.getFirstName());
			createPstmt.setString(3, student.getEmail());
			createPstmt.setString(4, student.getPhoneNumber());
			createPstmt.setString(5, student.getProgram());
			createPstmt.setTimestamp(6, java.sql.Timestamp.valueOf(student.getCreated()));
			createPstmt.executeUpdate();
		} catch (SQLException e) {

			logMsg("something went wrong creating student: " + e.getLocalizedMessage());
		}

		return student;
	}

	@Override
	public StudentPojo readStudentById(int studentId) {
		logMsg("read a specific student");
		// TODO Complete the retrieval of a specific student by its id
		// TODO Be sure to use try-and-catch statement
		StudentPojo student = null;
		try {
			readByIdPstmt.setInt(1, studentId);
			try (ResultSet rs = readByIdPstmt.executeQuery()) {
				if (rs.next()) {
					student = new StudentPojo();
					student.setId(rs.getInt("id"));
					student.setLastName(rs.getString("last_name"));
					student.setFirstName(rs.getString("first_name"));
					student.setEmail(rs.getString("email"));
					student.setPhoneNumber(rs.getString("phone"));
					student.setProgram(rs.getString("program"));
					student.setCreated(rs.getTimestamp("created").toLocalDateTime());
				}
			}
		} catch (SQLException e) {
			logMsg("something went wrong reading student by id: " + e.getLocalizedMessage());
		}

		return student;
	}

	@Override
	public void updateStudent(StudentPojo student) {
		logMsg("updating a specific student");
		// TODO Complete the update of a specific student
		// TODO Be sure to use try-and-catch statement
		try {
			updatePstmt.setString(1, student.getLastName());
			updatePstmt.setString(2, student.getFirstName());
			updatePstmt.setString(3, student.getEmail());
			updatePstmt.setString(4, student.getPhoneNumber());
			updatePstmt.setString(5, student.getProgram());
			updatePstmt.setInt(6, student.getId());
			updatePstmt.executeUpdate();
		} catch (SQLException e) {
			logMsg("something went wrong updating student: " + e.getLocalizedMessage());
		}
	}

	@Override
	public void deleteStudentById(int studentId) {
		logMsg("deleting a specific student");
		// TODO Complete the deletion of a specific student
		// TODO Be sure to use try-and-catch statement
		try {
			deleteByIdPstmt.setInt(1, studentId);
			deleteByIdPstmt.executeUpdate();
		} catch (SQLException e) {
			logMsg("something went wrong deleting student: " + e.getLocalizedMessage());
		}
	}

}