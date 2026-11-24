package daw2.dwes.morty1.controller;

import daw2.dwes.morty1.dto.LocationDTO;
import daw2.dwes.morty1.service.CharacterService;
import daw2.dwes.morty1.service.LocationService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/locations")
public class LocationController {

    private final LocationService locationService;
    private final CharacterService characterService;

    public LocationController(LocationService locationService,
                              CharacterService characterService) {
        this.locationService = locationService;
        this.characterService = characterService;
    }

    private void populateCharacters(Model model) {
        model.addAttribute("characters", characterService.findAll());
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("locations", locationService.findAll());
        return "locations/list";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("locationDTO", new LocationDTO());
        model.addAttribute("formAction", "/locations/create");
        model.addAttribute("pageTitle", "Crear Location");
        populateCharacters(model);
        return "locations/form";
    }

    @PostMapping("/create")
    public String processCreateForm(@Valid @ModelAttribute("locationDTO") LocationDTO locationDTO,
                                    BindingResult bindingResult,
                                    Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("formAction", "/locations/create");
            model.addAttribute("pageTitle", "Crear Location");
            populateCharacters(model);
            return "locations/form";
        }
        locationService.saveFromDto(locationDTO);
        return "redirect:/locations";
    }

    @GetMapping("/edit/{id:[0-9]+}")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        LocationDTO dto = locationService.findDtoById(id);
        if (dto == null) {
            return "redirect:/locations";
        }
        model.addAttribute("locationDTO", dto);
        model.addAttribute("formAction", "/locations/edit/" + id);
        model.addAttribute("pageTitle", "Editar Location");
        populateCharacters(model);
        return "locations/form";
    }

    @PostMapping("/edit/{id:[0-9]+}")
    public String processEditForm(@PathVariable("id") Long id,
                                  @Valid @ModelAttribute("locationDTO") LocationDTO locationDTO,
                                  BindingResult bindingResult,
                                  Model model) {
        if (bindingResult.hasErrors()) {
            locationDTO.setId(id);
            model.addAttribute("formAction", "/locations/edit/" + id);
            model.addAttribute("pageTitle", "Editar Location");
            populateCharacters(model);
            return "locations/form";
        }
        locationDTO.setId(id);
        locationService.saveFromDto(locationDTO);
        return "redirect:/locations";
    }

    @GetMapping("/delete/{id:[0-9]+}")
    public String deleteLocation(@PathVariable("id") Long id) {
        locationService.deleteById(id);
        return "redirect:/locations";
    }

    @GetMapping("/{id:[0-9]+}")
    public String detail(@PathVariable Long id, Model model) {
        model.addAttribute("location", locationService.findById(id));
        return "locations/detail";
    }
}