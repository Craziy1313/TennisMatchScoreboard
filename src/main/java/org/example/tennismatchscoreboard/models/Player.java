package org.example.tennismatchscoreboard.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Table(name = "players")
public class Player {

    @Id
    @GeneratedValue (strategy= GenerationType.IDENTITY)
    private int id;

    @Column (name = "name", unique = true, nullable = false)
    private String name;

    public Player() {
    }

    public Player(String name) {
        this.name = name;
    }

}
