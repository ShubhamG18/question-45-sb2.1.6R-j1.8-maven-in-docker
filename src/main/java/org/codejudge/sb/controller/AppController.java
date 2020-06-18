package org.codejudge.sb.controller;

import javax.validation.Valid;

import org.codejudge.sb.dto.ErrorResponseDto;
import org.codejudge.sb.dto.MarkModel;
import org.codejudge.sb.model.Lead;
import org.codejudge.sb.service.LeadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/")
public class AppController {

	@ApiOperation("This is the hello world api")
	@GetMapping("/hello")
	public String hello() {
		return "Hello World!!";
	}

	@Autowired
	private LeadService leadService;

	@GetMapping("/api/leads/{id}")
	public ResponseEntity<Object> fetchLead(@PathVariable("id") String id) {
		try {
			int leadId = Integer.parseInt(id);
			java.util.Optional<Lead> lead = leadService.getLead(leadId);
			if (lead.isPresent()) {
				return new ResponseEntity<>(lead.get(), HttpStatus.OK);
			} else {
				return new ResponseEntity<>(new Lead(), HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			ErrorResponseDto erd = new ErrorResponseDto("failure", "reason");
			return new ResponseEntity<>(erd, HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping("/api/leads/")
	public ResponseEntity<Object> saveLead(@Valid @RequestBody Lead lead) {
		if(leadService.checkEmailAlreadyPresent(lead.getEmail())) {
			ErrorResponseDto erd = new ErrorResponseDto("failure", "reason");
			return new ResponseEntity<>(erd, HttpStatus.BAD_REQUEST);
		}
		Lead lead2 = leadService.saveLead(lead);
		return new ResponseEntity<>(lead2, HttpStatus.CREATED);
	}
	
	@PutMapping("/api/leads/{id}")
	public ResponseEntity<Object> updateLead(@PathVariable("id") String id,@Valid @RequestBody Lead lead) {
		try {
			int leadId = Integer.parseInt(id);
			if(leadService.checkEmailAlreadyPresent(lead.getEmail())) {
				ErrorResponseDto erd = new ErrorResponseDto("failure", "reason");
				return new ResponseEntity<>(erd, HttpStatus.BAD_REQUEST);
			}
			Lead updatedLead =new Lead();
			updatedLead.setId(leadId);
			leadService.saveLead(updatedLead);
			ErrorResponseDto erd = new ErrorResponseDto("sucesss");
			return new ResponseEntity<>(erd, HttpStatus.ACCEPTED);
		} catch (Exception e) {
			ErrorResponseDto erd = new ErrorResponseDto("failure", "reason");
			return new ResponseEntity<>(erd, HttpStatus.BAD_REQUEST);
		}
	}
	
	@DeleteMapping("/api/leads/{id}")
	public ResponseEntity<Object> deleteLead(@PathVariable("id") String id) {
		try {
			int leadId = Integer.parseInt(id);
			leadService.deleteLead(leadId);
			ErrorResponseDto erd = new ErrorResponseDto("sucesss");
			return new ResponseEntity<>(erd, HttpStatus.OK);
		} catch (Exception e) {
			ErrorResponseDto erd = new ErrorResponseDto("failure", "reason");
			return new ResponseEntity<>(erd, HttpStatus.BAD_REQUEST);
		}
	}
	
	@PutMapping("/api/mark_lead/{id}")
	public ResponseEntity<ErrorResponseDto> markLead(@PathVariable String id, @Valid @RequestBody MarkModel markModel) {
		try {
			int leadId = Integer.parseInt(id);
			leadService.markLeader(leadId, markModel);
			ErrorResponseDto erd = new ErrorResponseDto("Contacted");
			erd.setCommunication(markModel.getCommunication());
			return new ResponseEntity<>(erd, HttpStatus.ACCEPTED);
		} catch (Exception e) {
			ErrorResponseDto erd = new ErrorResponseDto("failure", "reason");
			return new ResponseEntity<>(erd, HttpStatus.BAD_REQUEST);
		}
	}

}
