package com.propscout.teafactory.models.dtos;

public class ScheduleItemDto {

    private Long id;
    private Integer centerId;
    private Integer userId;
    private Integer month;
    private String daytime;

    public ScheduleItemDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCenterId() {
        return centerId;
    }

    public void setCenterId(Integer centerId) {
        this.centerId = centerId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public String getDaytime() {
        return daytime;
    }

    public void setDaytime(String daytime) {
        this.daytime = daytime;
    }

    @Override
    public String toString() {
        return "ScheduleItemDto{" +
                "id=" + id +
                ", centerId=" + centerId +
                ", userId=" + userId +
                ", month=" + month +
                ", daytime='" + daytime + '\'' +
                '}';
    }
}
