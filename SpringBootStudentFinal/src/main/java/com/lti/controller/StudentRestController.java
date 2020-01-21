package com.lti.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lti.model.Student;
import com.lti.service.StudentService;

@RestController
@RequestMapping(path = "students")
@CrossOrigin
public class StudentRestController {

	@Autowired
	private StudentService service;

	// http://localhost:9090/students
	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE) // JSON
																								// XML??
	public List<Student> findAllStudents() {
		List<Student> students = service.getAllStudents();

		return students;
	}

	// http://localhost:9090/students/100
	@RequestMapping(path = "{rollNumber}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public Student findStudentByRollNumber(@PathVariable("rollNumber") int rollNumber) {
		Student student = service.findStudentByRollNumber(rollNumber);
		return student;
	}

	// http://localhost:9090/students
	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> addStudent(@RequestBody Student student) {
		ResponseEntity<String> response;
		boolean result=service.addStudent(student);
		if(result){
			response=new ResponseEntity<String>("Student is added",HttpStatus.CREATED);
		}else{
			response=new ResponseEntity<String>("Student is not added",HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return response;
	}
	@ExceptionHandler(Exception.class)
	public ResponseEntity<String> handleException(Exception ex){
		ResponseEntity<String> error=new ResponseEntity<String>(ex.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
		return error;
	}

	// http://localhost:9090/students/
	@RequestMapping(path = "{rollno}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public void deleteStudent(@PathVariable("rollno") int rollNumber) {
		service.deleteStudent(rollNumber);
	}

	// http://localhost:9090/students
	@RequestMapping(path = "update", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public void updateStudent(@RequestBody Student student) {
		service.updateStudent(student);
	}
}
