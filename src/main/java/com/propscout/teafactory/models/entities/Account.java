package com.propscout.teafactory.models.entities;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "accounts")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "transaction_acc_no", unique = true)
    private Long transactionAccNo;

    @Column(name = "acc_bal")
    private Double accBal = 0.0;

    @Column(name = "created_at")
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "center_id")
    private Center center;


    @OneToMany(mappedBy = "account")
    private List<TeaRecord> teaRecordItems = new ArrayList<>();

    public Account() {
    }

    public Account(Long transactionAccNo) {
        this.transactionAccNo = transactionAccNo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getTransactionAccNo() {
        return transactionAccNo;
    }

    public void setTransactionAccNo(Long transactionAccNo) {
        this.transactionAccNo = transactionAccNo;
    }

    public Double getAccBal() {
        return accBal;
    }

    public void setAccBal(Double accBal) {
        this.accBal = accBal;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Center getCenter() {
        return center;
    }

    public void setCenter(Center center) {
        this.center = center;
    }

    public List<TeaRecord> getTeaRecordItems() {
        return teaRecordItems;
    }

    public void setTeaRecordItems(List<TeaRecord> teaRecordItems) {
        this.teaRecordItems = teaRecordItems;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", transactionAccNo=" + transactionAccNo +
                ", accBal=" + accBal +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", user=" + user +
                ", center=" + center +
                '}';
    }
}
