package daw2.dwes.morty1.service;

import daw2.dwes.morty1.model.Episode;
import daw2.dwes.morty1.repository.EpisodeRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class EpisodeService {

    private final EpisodeRepository episodeRepository;

    public EpisodeService(EpisodeRepository episodeRepository) {
        this.episodeRepository = episodeRepository;
    }

    public List<Episode> findEpisodesAfter2015() {
        LocalDate cutoffDate = LocalDate.of(2015, 12, 31);
        return episodeRepository.findEpisodesAfterDate(cutoffDate);
    }

    public List<Episode> findAll() {
        return episodeRepository.findAll();
    }

    public long getTotalEpisodesCount() {
        return episodeRepository.countTotalEpisodes();
    }

    public Episode save(Episode episode) {
        return episodeRepository.save(episode);
    }
}