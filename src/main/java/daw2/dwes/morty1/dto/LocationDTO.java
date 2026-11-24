package daw2.dwes.morty1.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.ArrayList;
import java.util.List;

public class LocationDTO {

    private Long id;

    @NotBlank(message = "El nombre es obligatorio")
    private String name;

    @NotBlank(message = "El tipo es obligatorio")
    private String type;

    @NotBlank(message = "La dimensión es obligatoria")
    private String dimension;

    @NotEmpty(message = "Debes seleccionar al menos un personaje")
    private List<@NotNull @Positive Long> characterIds = new ArrayList<>();

    public LocationDTO() {
    }

    public LocationDTO(Long id, String name, String type, String dimension, List<Long> characterIds) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.dimension = dimension;
        this.characterIds = characterIds;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDimension() {
        return dimension;
    }

    public void setDimension(String dimension) {
        this.dimension = dimension;
    }

    public List<Long> getCharacterIds() {
        return characterIds;
    }

    public void setCharacterIds(List<Long> characterIds) {
        this.characterIds = characterIds;
    }
}