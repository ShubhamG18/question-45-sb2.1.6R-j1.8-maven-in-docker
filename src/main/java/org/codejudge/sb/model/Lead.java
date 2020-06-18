package org.codejudge.sb.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Required;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Entity
@Data
@JsonInclude(Include.NON_NULL)
public class Lead {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@NotNull
	@Column(name = "first_name")
	@JsonProperty("first_name")
	private String firstName;
	
	@NotNull
	@JsonProperty("last_name")
	@Column(name = "last_name")
	private String lastName;
	
	@NotNull
	@Column
	private Long mobile;
	
	@Column
	@Email
	@NotNull
	private String email;
	
	@NotNull
	@JsonProperty("location_type")
	@Column(name = "location_type")
	private String locationType;
	
	@NotNull
	@JsonProperty("location_string")
	@Column(name = "location_string")
	private String locationString;
	
	@Column
	private String status;
	
	@Column
	private String communication;

}
