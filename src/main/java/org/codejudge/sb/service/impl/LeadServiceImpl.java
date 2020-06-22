package org.codejudge.sb.service.impl;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import javax.validation.Valid;

import org.codejudge.sb.dto.MarkModel;
import org.codejudge.sb.exception.CustomException;
import org.codejudge.sb.model.Lead;
import org.codejudge.sb.repo.LeadRepository;
import org.codejudge.sb.service.LeadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LeadServiceImpl implements LeadService {

	Set<String> emails = new HashSet<>();
	Set<Long> mobiles=new HashSet<>();
	@Autowired
	private LeadRepository leadRepository;

	@Override
	public Optional<Lead> getLead(int id) {
		return leadRepository.findById(id);
	}

	@Override
	public boolean checkEmailAlreadyPresent(String email) {
		return emails.contains(email);
	}

	@Override
	public Lead saveLead(Lead lead) throws CustomException {
		if(mobiles.contains(lead.getMobile())) {
			throw new CustomException();
		}
		emails.add(lead.getEmail());
		mobiles.add(lead.getMobile());
		lead.setStatus("Created");
		return leadRepository.save(lead);
	}

	@Override
	public void deleteLead(int leadId) {
		leadRepository.deleteById(leadId);
	}

	@Override
	public void markLeader(int leadId, @Valid MarkModel markModel) {
		Optional<Lead> optionalLead = leadRepository.findById(leadId);
		if (optionalLead.isPresent()) {
			Lead lead = optionalLead.get();
			lead.setCommunication(markModel.getCommunication());
			lead.setStatus("Contacted");
			leadRepository.save(lead);
		}
	}

	@Override
	public void updateLead(int id, Lead updatedLead) throws CustomException {
		Optional<Lead> optionalLead = leadRepository.findById(id);
		if(optionalLead.isPresent()) {
			Lead lead=optionalLead.get();
			if((!lead.getEmail().equals(updatedLead.getEmail())) && emails.contains(updatedLead.getEmail())){
				throw new CustomException();
			}
			if((!lead.getMobile().equals(updatedLead.getMobile())) && mobiles.contains(updatedLead.getMobile())){
				throw new CustomException();
			}
			updatedLead.setId(id);
			leadRepository.save(updatedLead);
		}else {
			throw new CustomException();
		}
	}

}


