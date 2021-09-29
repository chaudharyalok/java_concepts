package in.alok.rule21D.dao;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.stereotype.Component;

import in.alok.rule21D.dto.Customer;

@Component
public class CustomerDao {

	public List<Customer> fetchCustomer(){
		
		return IntStream.rangeClosed(1, 10)
				.mapToObj(i -> new Customer(i,"customer " + i))
				.collect(Collectors.toList());
	}
}
