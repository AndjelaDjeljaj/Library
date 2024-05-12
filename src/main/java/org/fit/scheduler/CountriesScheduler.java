package org.fit.scheduler;

import java.util.List;

import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.fit.model.rest.client.Country;
import org.fit.rest.client.CountryClient;
import org.fit.service.CountryService;

import io.quarkus.scheduler.Scheduled;
import jakarta.inject.Inject;

public class CountriesScheduler {

	@Inject
	@RestClient
	private CountryClient countryClient;
	
	@Inject
	private CountryService countryService;
	

	@Scheduled(every = "60s")
	public void getAllCounries() {
		List<Country> countries = countryClient.getAll();
		countryService.saveCountries(countries);
	}
}
