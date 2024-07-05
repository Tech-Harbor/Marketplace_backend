package com.example.backend.websocket.store.entities;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "message")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReceivedMessageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String sentFrom;

    @Column(nullable = false)
    private String sendTo;

    @Column(nullable = false)
    private String text;

}