package org.example.tennismatchscoreboard.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Table (name = "matches")
public class Matches {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    private Player player1;

    @ManyToOne
    private Player player2;

    @ManyToOne
    private Player winner;



}
