package com.shop.production.shop.entity;

import lombok.Data;
import lombok.NoArgsConstructor;


import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "ProductComponent")
@Data
@NoArgsConstructor

public class ProductComponent {
   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   @Column(name = "id")
    private UUID id;

   @Column(name = "componentName")
   private String componentName;

   @Column(name = "wholeSalePrice")
   private double wholeSalePrice;

   @Column(name = "description")
   private  String description;


   @Column(name = "quantity")
   private double quantity;
}
