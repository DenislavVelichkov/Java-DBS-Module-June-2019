package app.domain.dto;

import com.google.gson.annotations.Expose;

import java.io.Serializable;

public class PhoneNumberDto implements Serializable {

    @Expose
    private String phoneNumber;

    public PhoneNumberDto(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
