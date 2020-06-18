package org.codejudge.sb.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@JsonInclude(Include.NON_NULL)
@Data
public class ErrorResponseDto {
	private String status;
	private String reason;
	private String communication;

	public ErrorResponseDto(String status, String reason) {
		super();
		this.status = status;
		this.reason = reason;
	}

	public ErrorResponseDto(String status) {
		this.status = status;
	}

}
