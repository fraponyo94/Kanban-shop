package com.shop.production.shop.entity;

import javafx.scene.control.Button;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.persistence.*;

@Entity
@Table(name="Employee")
@Data
@NoArgsConstructor
public class Employee {
    @Id
    @Column(name = "email")
    private String email;

    @Column(name = "first_name",nullable = false)
    private String firstName;

    @Column(name = "last_name",nullable = false)
    private String lastName;

    @Column(name = "phone_number",nullable = false)
    private String phoneNumber;

    @Transient
    private Button edit;
    @Transient
    private Button delete;






}
