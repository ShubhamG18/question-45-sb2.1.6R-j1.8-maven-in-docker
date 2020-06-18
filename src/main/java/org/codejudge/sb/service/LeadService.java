package org.codejudge.sb.service;

import java.util.Optional;

import javax.validation.Valid;

import org.codejudge.sb.dto.MarkModel;
import org.codejudge.sb.model.Lead;

public interface LeadService {

	Optional<Lead> getLead(int leadId);

	boolean checkEmailAlreadyPresent(String email);

	Lead saveLead(Lead lead);

	void deleteLead(int leadId);

	void markLeader(int leadId, @Valid MarkModel markModel);

}
