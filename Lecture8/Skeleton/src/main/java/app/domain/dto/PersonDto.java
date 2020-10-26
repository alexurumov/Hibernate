package app.domain.dto;

import app.domain.model.Person;
import app.domain.model.PhoneNumber;
import com.google.gson.annotations.Expose;

import java.util.Set;
import java.util.stream.Collectors;

public class PersonDto {

    @Expose
    private String firstName;

    @Expose
    private Set<PhoneNumberDto> phoneNumbers;

    @Expose
    private AddressDto address;

    public PersonDto(Person p) {
        this.firstName = p.getFirstName();
        this.phoneNumbers = p.getPhoneNumbers()
                .stream()
                .map(phone -> new PhoneNumberDto(phone.getNumber(), p))
                .collect(Collectors.toSet());

        this.address = new AddressDto(p.getAddress());
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public Set<PhoneNumberDto> getPhoneNumbers() {
        return phoneNumbers;
    }

    public void setPhoneNumbers(Set<PhoneNumberDto> phoneNumbers) {
        this.phoneNumbers = phoneNumbers;
    }

    public AddressDto getAddress() {
        return address;
    }

    public void setAddress(AddressDto address) {
        this.address = address;
    }
}
