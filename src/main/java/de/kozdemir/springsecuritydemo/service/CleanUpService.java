package de.kozdemir.springsecuritydemo.service;

import de.kozdemir.springsecuritydemo.repository.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional
public class CleanUpService {

    @Autowired
    private TokenRepository tokenRepository;

    //@Scheduled(cron = "@hourly") // @yearly, @daily, @midnight, @hourly
    @Scheduled(cron = "0 */2 * * * ?") // alle 2 Minuten
    public void deleteExpiredTokens() {
        tokenRepository.deleteByCreatedAtBefore(LocalDateTime.now().minusDays(7));
        System.out.println("Clean UP");
    }


}
