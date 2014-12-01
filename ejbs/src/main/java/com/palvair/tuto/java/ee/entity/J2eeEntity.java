package com.palvair.tuto.java.ee.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

/**
 * @author rpalvair
 */
@Entity
@EqualsAndHashCode
@ToString
@Table(name = "j2eeentity")
public class J2eeEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter
    @Setter
    private Long id;


    @Column(name = "name")
    @Getter
    @Setter
    private String name;

}
