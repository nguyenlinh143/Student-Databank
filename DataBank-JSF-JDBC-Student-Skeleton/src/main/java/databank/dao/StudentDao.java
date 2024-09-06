/********************************************************************************************************2*4*w*
 * File:  StudentDao.java Course materials CST8277
 *
 * @author Linh Vo
 * Student number: 041049754
 * Email: vo000077@algonquinlive.com
 */
package databank.dao;

import java.util.List;

import databank.model.StudentPojo;

/**
 * Description:  API for the database C-R-U-D operations
 */
public interface StudentDao {

	// C
	public StudentPojo createStudent(StudentPojo student);

	// R
	public StudentPojo readStudentById(int studentId);

	public List<StudentPojo> readAllStudents();

	// U
	public void updateStudent(StudentPojo student);

	// D
	public void deleteStudentById(int studentId);

}