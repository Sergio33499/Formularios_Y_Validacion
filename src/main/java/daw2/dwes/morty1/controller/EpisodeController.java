package daw2.dwes.morty1.controller;

import daw2.dwes.morty1.model.Episode;
import daw2.dwes.morty1.service.EpisodeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;

@Controller
@RequestMapping("/episodes")
public class EpisodeController {

    private final EpisodeService episodeService;

    public EpisodeController(EpisodeService episodeService) {
        this.episodeService = episodeService;
    }

    @GetMapping
    public String listEpisodes(Model model) {
        model.addAttribute("episodes", episodeService.findEpisodesAfter2015());
        return "episodes/list";
    }

    // Ruta 1 de la tabla: /episodes/create
    @GetMapping("/create")
    public String createEpisode() {
        Episode episode = new Episode();
        episode.setName("Nuevo Episodio Hardcoded");
        episode.setAirDate(LocalDate.now());
        episode.setEpisodeCode("S01E01");

        episodeService.save(episode);

        return "redirect:/episodes";
    }

    @GetMapping("/total")
    public String showTotalEpisodes(Model model) {
        model.addAttribute("totalEpisodes", episodeService.getTotalEpisodesCount());
        return "episodes/total";
    }
}