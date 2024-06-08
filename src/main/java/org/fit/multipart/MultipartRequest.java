package org.fit.multipart;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.fit.multipart.MultipartResource.UploadSchema;
import org.jboss.resteasy.reactive.PartType;
import org.jboss.resteasy.reactive.RestForm;
import org.jboss.resteasy.reactive.multipart.FileUpload;

import jakarta.ws.rs.core.MediaType;

public class MultipartRequest {
	@RestForm("file")
	@PartType(MediaType.APPLICATION_OCTET_STREAM)
	@Schema(implementation = UploadSchema.class)
	private FileUpload file;

	@RestForm("name")
	@PartType(MediaType.TEXT_PLAIN)
	private String name;

	@RestForm("lastName")
	@PartType(MediaType.TEXT_PLAIN)
	private String lastName;

	@RestForm("email")
	@PartType(MediaType.TEXT_PLAIN)
	private String email;

	@RestForm("birthDate")
	@PartType(MediaType.TEXT_PLAIN)
	private String birthDate;

	@RestForm("jmbg")
	@PartType(MediaType.TEXT_PLAIN)
	private String jmbg;

	@RestForm("countryName")
	@PartType(MediaType.TEXT_PLAIN)
	private String countryName;

	@RestForm("phoneNumbers")
	@PartType(MediaType.TEXT_PLAIN)
	private List<String> phoneNumbers;

	public FileUpload getFile() {
		return file;
	}

	public void setFile(FileUpload file) {
		this.file = file;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}

	public String getJmbg() {
		return jmbg;
	}

	public void setJmbg(String jmbg) {
		this.jmbg = jmbg;
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public List<String> getPhoneNumbers() {
		return phoneNumbers;
	}

	public void setPhoneNumbers(List<String> phoneNumbers) {
		this.phoneNumbers = phoneNumbers;
	}

	public Date getParsedBirthDate() throws ParseException {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		return formatter.parse(this.birthDate);
	}

}
