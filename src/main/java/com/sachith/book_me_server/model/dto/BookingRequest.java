package com.sachith.book_me_server.model.dto;

import com.sachith.book_me_server.model.enums.SportType;

import java.time.LocalDate;
import java.util.List;

public class BookingRequest {
    private LocalDate date;
    private List<Long> slotIds;
    private SportType sportType;
    private String name;
    private String phone;

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public List<Long> getSlotIds() {
        return slotIds;
    }

    public void setSlotIds(List<Long> slotIds) {
        this.slotIds = slotIds;
    }

    public SportType getSportType() {
        return sportType;
    }

    public void setSportType(SportType sportType) {
        this.sportType = sportType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
