package in.alok.rule21D.controller;

import in.alok.rule21D.dto.Customer;
import in.alok.rule21D.dto.User;
import in.alok.rule21D.service.CustomerService;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class CustomerController {

	@Autowired
	private CustomerService customerService;

	@GetMapping(value = "/customers")
	public List<Customer> getCustomers() {

		return customerService.customers();
	}

	@PostMapping(value = "/customers", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE }, produces = "application/json")
	public ResponseEntity<List<User>> saveCustomers(@RequestParam(value = "file") MultipartFile file)
			throws Exception {

		List<User> users = customerService.saveUsers(file).get();
		return ResponseEntity.status(HttpStatus.CREATED).body(users);
	}
}
