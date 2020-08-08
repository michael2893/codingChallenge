package com.example.APImethods.models;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor

public class InvitationModel {

        private int attendeeCount;

        private List<String> attendees;

        private String name;

        public int getAttendeeCount() {
                return attendeeCount;
        }

        public void setAttendeeCount(int attendeeCount) {
                this.attendeeCount = attendeeCount;
        }

        public List<String> getAttendees() {
                return attendees;
        }

        public void setAttendees(List<String> attendees) {
                this.attendees = attendees;
        }

        public String getName() {
                return name;
        }

        public void setName(String name) {
                this.name = name;
        }

        public Date getStartDate() {
                return startDate;
        }

        public void setStartDate(Date startDate) {
                this.startDate = startDate;
        }

        private Date startDate;
}
