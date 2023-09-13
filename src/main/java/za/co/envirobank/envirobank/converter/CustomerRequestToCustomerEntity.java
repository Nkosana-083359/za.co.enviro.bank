package za.co.envirobank.envirobank.converter;

import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import za.co.envirobank.envirobank.model.entities.Customer;
import za.co.envirobank.envirobank.transfereobject.CustomerRequest;

import java.util.Objects;
@Component
@RequiredArgsConstructor
public class CustomerRequestToCustomerEntity implements Converter<CustomerRequest, Customer> {
    @Override
    public Customer convert(CustomerRequest source) {
        if(Objects.nonNull(source)){
            Customer customer = new Customer();
            customer.setName(source.getName());
            customer.setSurname(source.getSurname());
            customer.setPhoneNumber(source.getPhoneNumber());
            customer.setIdNumber(source.getIdNum());
            customer.setEmail(source.getEmail());
            customer.setPassword(source.getPassword());

            return customer;

        }


        return null;
    }
}
