package org.example.tennismatchscoreboard.services;

import org.example.tennismatchscoreboard.models.Player;
import org.example.tennismatchscoreboard.repositories.PlayerRepositories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional (readOnly = true)
@Service
public class PlayerService {

    private final PlayerRepositories playerRepositories;

    @Autowired
    public PlayerService(PlayerRepositories playerRepositories) {
        this.playerRepositories = playerRepositories;
    }

    @Transactional
    public void savePlayer(Player player) {
        playerRepositories.save(player);
    }

    public Optional<Player> getPlayerByName(String name) {
        return playerRepositories.findByName(name);
    }

    public Optional<Player> getPlayerById(Integer id) {
        return playerRepositories.findById(id);
    }
}
