package org.codejudge.sb.dto;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class MarkModel {

	@NotNull
	private String communication;
}
	