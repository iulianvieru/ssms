package com.assignament.ssms.web;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.data.domain.PageRequest;


import com.assignament.ssms.model.Student;
import com.assignament.ssms.model.StudentRepository;
import com.assignament.ssms.util.StudentExcelParser;

@RestController
@RequestMapping("/api")
public class StudentController {

	private StudentRepository studentRepository;
	//private final StorageService storageService;

	public StudentController(StudentRepository studentRepository) {
		this.studentRepository = studentRepository;
		//this.storageService= storageService;
	}
	
	
	@GetMapping("/students")
	Collection<Student> getStudents() {
		
		return studentRepository.findAll();
	}
	
	@GetMapping("/students/{page}/{limit}")
	Collection<Student> getStudentsPage(@PathVariable int page,@PathVariable int limit) {
		
		
		return studentRepository.findAll(PageRequest.of(page, limit)).getContent();
	}

	
	@GetMapping("/students/{id}")
	ResponseEntity<?> getGroup(@PathVariable Long id) {
		System.out.println("Requested all data");
		Optional<Student> student = studentRepository.findById(id);
		return student.map(response -> ResponseEntity.ok().body(response))
				.orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}

	
	@PostMapping("/students")
	ResponseEntity<Student> createGroup(@RequestBody Student student) throws URISyntaxException {
		
		Student result = studentRepository.save(student);
		return ResponseEntity.created(new URI("/api/students/" + result.getId())).body(result);
	}

	
	@PutMapping("/students/{id}")
	ResponseEntity<Student> updateStudent(@RequestBody Student student) {

		Student result = studentRepository.save(student);
		return ResponseEntity.ok().body(result);
	}

	
	@DeleteMapping("/students/{id}")
	public ResponseEntity<?> deleteStudent(@PathVariable Long id) {
		studentRepository.deleteById(id);
		return ResponseEntity.ok().build();
	}

	
	@GetMapping(value = "/students/download")
	public void exportToExcel(HttpServletResponse response) throws IOException {
		response.setContentType("application/octet-stream");
		DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
		String currentDateTime = dateFormatter.format(new Date());

		String headerKey = "Content-Disposition";
		String headerValue = "attachment; filename=users_" + currentDateTime + ".xlsx";
		response.setHeader(headerKey, headerValue);

		List<Student> studentList= studentRepository.findAll();

		StudentExcelParser excelExporter = new StudentExcelParser(studentList);

		excelExporter.export(response);
	}
	
	//TODO validate the excel
	@PostMapping("/students/upload")
	public String handleFileUpload(@RequestParam("file") MultipartFile file,
			RedirectAttributes redirectAttributes) {
		StudentExcelParser excelParser= new StudentExcelParser(file);
		studentRepository.saveAll(excelParser.getStudentList());
		System.out.println("Uploading file "+file.getOriginalFilename());
		redirectAttributes.addFlashAttribute("message",
				"You successfully uploaded " + file.getOriginalFilename() + "!");
//		Map<String,String> resposne=new HashMap<String,String>();
//		resposne.put("message", "You successfully uploaded " + file.getOriginalFilename() + "!");
//		resposne.put("studentsImported", String.valueOf(excelParser.getStudentList().size()));
//		return ResponseEntity.ok().build(resposne);
		return "redirect:/";
	}
}
