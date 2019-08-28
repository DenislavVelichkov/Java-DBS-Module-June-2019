package app.domain.dto;

import app.domain.model.Person;
import com.google.gson.annotations.Expose;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class PersonDto implements Serializable {

    @Expose
    private String firstName;

    @Expose
    private String lastName;

    @Expose
    private Set<PhoneNumberDto> phoneNumbers;

    @Expose
    private AddressDto address;

    public PersonDto() {
        this.phoneNumbers = new HashSet<>();
    }

    public PersonDto(Person person) {
        this.firstName = person.getFirstName();
        this.lastName = person.getLastName();
        this.phoneNumbers = person.getPhoneNumbers()
                                .stream()
                                .map(phoneNumber -> new PhoneNumberDto(phoneNumber.getNumber()))
                                .collect(Collectors.toSet());
        this.address = new AddressDto(person.getAddress());
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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
