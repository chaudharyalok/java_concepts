package in.alok.rule21D.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import in.alok.rule21D.dao.CustomerDao;
import in.alok.rule21D.dto.Customer;
import in.alok.rule21D.dto.User;

@Service
public class CustomerService {

	@Autowired
	private CustomerDao customerDao;
	
	Logger logger = LoggerFactory.getLogger(CustomerService.class);
	
	public List<Customer> customers(){
		logger.info("customers m/d called");
		return customerDao.fetchCustomer();
	}
	
	@Async
	public CompletableFuture<List<User>> saveUsers(MultipartFile file) throws Exception {
		
		long start = System.currentTimeMillis();
		List<User> users = parseCSVFile(file);
		averageAge(users);
		averageAgeMale(users);
		long end = System.currentTimeMillis();
		
		logger.info("saving list of users of size {} and thread:{}",users.size(),Thread.currentThread().getName());
		logger.info("time taken in service layer:{}",(end-start));
		
		return CompletableFuture.completedFuture(users);
	}
	
	private void averageAge(List<User> users) {
		Double result = users.stream()
				.map(user -> user.getAge())
				.collect(Collectors.averagingInt(age -> age));
		logger.info("average age of users is: {}",result);
	}
	
	private void averageAgeMale(List<User> users) {
		Double result = users.stream()
				.filter(user -> user.getGender().equals("Female"))
				.peek(user -> System.out.println(user.getName()))
				.map(user -> user.getAge()).collect(Collectors.averagingInt(age -> age));
		logger.info("average age of female users is: {}",result);
	}
	
	private List<User> parseCSVFile(final MultipartFile file) throws Exception{
		final List<User> users = new ArrayList<>();
			try(final BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()))){
				String line;
				while((line = br.readLine()) != null) {
					final String[] data = line.split(",");
					final User user = new User();
					user.setId(Integer.valueOf(data[0]));
					user.setName(data[1]);
					user.setAge(Integer.valueOf(data[2]));
					user.setEmail(data[3]);
					user.setGender(data[4]);
					
					users.add(user);
				}
				return users;
			
		} catch(final IOException e) {
			logger.error("failed to parse CSV file {}", e);
			throw new Exception("failed to parse CSV file {}", e);
		}
		
	}
}
