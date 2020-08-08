package com.example.APImethods.models;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@NoArgsConstructor
@Data
public class PartnerModel {
    public class Partner {
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

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public List<Date> getAvailableDates() {
            return availableDates;
        }

        public void setAvailableDates(List<Date> availableDates) {
            this.availableDates = availableDates;
        }

        private String firstName;

        private String lastName;

        private String email;

        private String country;
        
        private List<Date> availableDates;

    }
}