package daw2.dwes.morty1.service;

import daw2.dwes.morty1.dto.CharacterDTO;
import daw2.dwes.morty1.dto.CharacterStatsDto;
import daw2.dwes.morty1.model.Character;
import daw2.dwes.morty1.model.Episode;
import daw2.dwes.morty1.model.Gender;
import daw2.dwes.morty1.model.Location;
import daw2.dwes.morty1.repository.CharacterRepository;
import daw2.dwes.morty1.repository.EpisodeRepository;
import daw2.dwes.morty1.repository.LocationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CharacterService {

    private final CharacterRepository characterRepository;
    private final LocationRepository locationRepository;
    private final EpisodeRepository episodeRepository;

    public CharacterService(CharacterRepository characterRepository,
                            LocationRepository locationRepository,
                            EpisodeRepository episodeRepository) {
        this.characterRepository = characterRepository;
        this.locationRepository = locationRepository;
        this.episodeRepository = episodeRepository;
    }

    public List<Character> findTallCharacters() {
        return characterRepository.findByHeightGreaterThan(1.70f);
    }

    public List<CharacterStatsDto> getCharacterStats() {
        return characterRepository.findCharacterStats();
    }

    public List<Character> findAll() {
        return characterRepository.findAll();
    }

    public Character findById(Long id) {
        return characterRepository.findById(id).orElse(null);
    }

    public CharacterDTO findDtoById(Long id) {
        Character c = findById(id);
        if (c == null) {
            return null;
        }
        CharacterDTO dto = new CharacterDTO();
        dto.setId(c.getId());
        dto.setName(c.getName());
        dto.setStatus(c.getStatus());
        dto.setSpecies(c.getSpecies());
        dto.setType(c.getType());
        dto.setGender(c.getGender() != null ? c.getGender().name() : null);
        dto.setWeight(c.getWeight());
        dto.setHeight(c.getHeight());
        dto.setLocationId(c.getLocation() != null ? c.getLocation().getId() : null);
        if (c.getEpisodes() != null) {
            dto.setEpisodeIds(c.getEpisodes().stream().map(Episode::getId).toList());
        }
        return dto;
    }

    public Character saveFromDto(CharacterDTO dto) {
        Character character = (dto.getId() != null)
                ? characterRepository.findById(dto.getId()).orElse(new Character())
                : new Character();

        character.setName(dto.getName().trim());
        character.setStatus(dto.getStatus().trim());
        character.setSpecies(dto.getSpecies().trim());
        character.setType(dto.getType().trim());
        character.setGender(Gender.valueOf(dto.getGender()));
        character.setWeight(dto.getWeight());
        character.setHeight(dto.getHeight());

        Location location = locationRepository.findById(dto.getLocationId())
                .orElseThrow(() -> new IllegalArgumentException("Localización no encontrada: " + dto.getLocationId()));
        character.setLocation(location);

        List<Episode> episodes = episodeRepository.findAllById(dto.getEpisodeIds());
        character.setEpisodes(episodes);

        return characterRepository.save(character);
    }

    public Character save(Character character) {
        return characterRepository.save(character);
    }

    public void deleteById(Long id) {
        characterRepository.deleteById(id);
    }
}