package daw2.dwes.morty1.service;

import daw2.dwes.morty1.dto.LocationDTO;
import daw2.dwes.morty1.model.Character;
import daw2.dwes.morty1.model.Location;
import daw2.dwes.morty1.repository.CharacterRepository;
import daw2.dwes.morty1.repository.LocationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class LocationService {

    private final LocationRepository locationRepository;
    private final CharacterRepository characterRepository;

    public LocationService(LocationRepository locationRepository,
                           CharacterRepository characterRepository) {
        this.locationRepository = locationRepository;
        this.characterRepository = characterRepository;
    }

    public List<Location> findAll() {
        return locationRepository.findAll();
    }

    public Location findById(Long id) {
        return locationRepository.findById(id).orElse(null);
    }

    public Location save(Location location) {
        return locationRepository.save(location);
    }

    public void deleteById(Long id) {
        locationRepository.deleteById(id);
    }

    public LocationDTO findDtoById(Long id) {
        Location loc = findById(id);
        if (loc == null) {
            return null;
        }
        LocationDTO dto = new LocationDTO();
        dto.setId(loc.getId());
        dto.setName(loc.getName());
        dto.setType(loc.getType());
        dto.setDimension(loc.getDimension());

        List<Long> selectedIds = new ArrayList<>();
        for (Character c : characterRepository.findAll()) {
            if (c.getLocation() != null && loc.getId().equals(c.getLocation().getId())) {
                selectedIds.add(c.getId());
            }
        }
        dto.setCharacterIds(selectedIds);
        return dto;
    }

    @Transactional
    public Location saveFromDto(LocationDTO dto) {
        Location location = (dto.getId() != null)
                ? locationRepository.findById(dto.getId()).orElse(new Location())
                : new Location();

        location.setName(dto.getName().trim());
        location.setType(dto.getType().trim());
        location.setDimension(dto.getDimension().trim());

        Location savedLocation = locationRepository.save(location);

        List<Long> targetCharacterIds = (dto.getCharacterIds() != null) ? dto.getCharacterIds() : List.of();
        List<Character> allCharacters = characterRepository.findAll();

        for (Character c : allCharacters) {
            boolean shouldBelong = targetCharacterIds.contains(c.getId());
            if (shouldBelong) {
                c.setLocation(savedLocation);
                characterRepository.save(c);
            } else if (c.getLocation() != null && savedLocation.getId().equals(c.getLocation().getId())) {
                c.setLocation(null);
                characterRepository.save(c);
            }
        }

        return savedLocation;
    }
}