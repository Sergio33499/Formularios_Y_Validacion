package daw2.dwes.morty1.controller;

import daw2.dwes.morty1.dto.CharacterDTO;
import daw2.dwes.morty1.model.Gender;
import daw2.dwes.morty1.service.CharacterService;
import daw2.dwes.morty1.service.EpisodeService;
import daw2.dwes.morty1.service.LocationService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/characters")
public class CharacterController {

    private final CharacterService characterService;
    private final LocationService locationService;
    private final EpisodeService episodeService;

    public CharacterController(CharacterService characterService,
                               LocationService locationService,
                               EpisodeService episodeService) {
        this.characterService = characterService;
        this.locationService = locationService;
        this.episodeService = episodeService;
    }

    private void populateFormCatalogs(Model model) {
        model.addAttribute("locations", locationService.findAll());
        model.addAttribute("episodes", episodeService.findAll());
        model.addAttribute("genders", Gender.values());
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("characters", characterService.findAll());
        return "characters/list";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("characterDTO", new CharacterDTO());
        model.addAttribute("formAction", "/characters/create");
        model.addAttribute("pageTitle", "Crear Character");
        populateFormCatalogs(model);
        return "characters/form";
    }

    @PostMapping("/create")
    public String processCreateForm(@Valid @ModelAttribute("characterDTO") CharacterDTO characterDTO,
                                    BindingResult bindingResult,
                                    Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("formAction", "/characters/create");
            model.addAttribute("pageTitle", "Crear Character");
            populateFormCatalogs(model);
            return "characters/form";
        }
        characterService.saveFromDto(characterDTO);
        return "redirect:/characters";
    }

    @GetMapping("/edit/{id:[0-9]+}")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        CharacterDTO dto = characterService.findDtoById(id);
        if (dto == null) {
            return "redirect:/characters";
        }
        model.addAttribute("characterDTO", dto);
        model.addAttribute("formAction", "/characters/edit/" + id);
        model.addAttribute("pageTitle", "Editar Character");
        populateFormCatalogs(model);
        return "characters/form";
    }

    @PostMapping("/edit/{id:[0-9]+}")
    public String processEditForm(@PathVariable("id") Long id,
                                  @Valid @ModelAttribute("characterDTO") CharacterDTO characterDTO,
                                  BindingResult bindingResult,
                                  Model model) {
        if (bindingResult.hasErrors()) {
            characterDTO.setId(id);
            model.addAttribute("formAction", "/characters/edit/" + id);
            model.addAttribute("pageTitle", "Editar Character");
            populateFormCatalogs(model);
            return "characters/form";
        }
        characterDTO.setId(id);
        characterService.saveFromDto(characterDTO);
        return "redirect:/characters";
    }

    @GetMapping("/delete/{id:[0-9]+}")
    public String deleteCharacter(@PathVariable("id") Long id) {
        characterService.deleteById(id);
        return "redirect:/characters";
    }

    @GetMapping("/stats")
    public String showStats(Model model) {
        model.addAttribute("stats", characterService.getCharacterStats());
        return "characters/stats";
    }

    @GetMapping("/{id:[0-9]+}")
    public String detail(@PathVariable Long id, Model model) {
        model.addAttribute("character", characterService.findById(id));
        return "characters/detail";
    }
}