package com.example.student.controller;

import com.example.student.exception.ResourceNotFoundException;
import com.example.student.model.Student;
import com.example.student.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class StudentController {
	
	@Autowired
    StudentRepository studentRepository;
	
	@GetMapping("/student")
	public List<Student> getAllNotes() {
	    return studentRepository.findAll();
	}
	
	@PostMapping("/student")
	public Student createNote(@Valid @RequestBody Student student) {
	    return studentRepository.save(student);
	}
	
	@GetMapping("/student/{id}")
	public Student getNoteById(@PathVariable(value = "id") Long studentId) {
	    return studentRepository.findById(studentId)
	            .orElseThrow(() -> new ResourceNotFoundException("Note", "id", studentId));
	}
	@PutMapping("/student/{id}")
	public Student updateNote(@PathVariable(value = "id") Long studentId,
	                                        @Valid @RequestBody Student studentDetails) {

	    Student student = studentRepository.findById(studentId)
	            .orElseThrow(() -> new ResourceNotFoundException("Note", "id", studentId));

	    student.setName(studentDetails.getName());
	    student.setAge(studentDetails.getAge());

	    Student updatedStudent = studentRepository.save(student);
	    return updatedStudent;
	}
	
	
	@DeleteMapping("/student/{id}")
	public ResponseEntity<?> deleteNote(@PathVariable(value = "id") Long studentId) {
	    Student student = studentRepository.findById(studentId)
	            .orElseThrow(() -> new ResourceNotFoundException("Note", "id", studentId));

	    studentRepository.delete(student);

	    return ResponseEntity.ok().build();
	}

}
