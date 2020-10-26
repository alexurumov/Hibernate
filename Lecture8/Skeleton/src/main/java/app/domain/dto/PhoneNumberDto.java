package app.domain.dto;

import app.domain.model.Person;
import com.google.gson.annotations.Expose;

public class PhoneNumberDto {

    @Expose
    private String number;

    @Expose
    private Long person_id;

    public PhoneNumberDto(String number, Person person) {
        this.number = number;
        this.person_id = person.getId();
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Long getPerson_id() {
        return person_id;
    }

    public void setPerson_id(Long person_id) {
        this.person_id = person_id;
    }
}
