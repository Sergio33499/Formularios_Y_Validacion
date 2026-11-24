package daw2.dwes.morty1.dto;

import jakarta.validation.constraints.*;

import java.util.ArrayList;
import java.util.List;

public class CharacterDTO {

    private Long id;

    @NotBlank(message = "El nombre es obligatorio")
    private String name;

    @NotBlank(message = "El estado es obligatorio")
    private String status;

    @NotBlank(message = "La especie es obligatoria")
    private String species;

    @NotBlank(message = "El tipo es obligatorio")
    private String type;

    @NotBlank(message = "El género es obligatorio")
    @Pattern(
            regexp = "^(Male|Female|Genderless|unknown)$",
            message = "El género debe ser Male, Female, Genderless o unknown"
    )
    private String gender;

    @NotNull(message = "El peso es obligatorio")
    @DecimalMin(value = "0.0", inclusive = false, message = "El peso debe ser mayor que 0")
    @DecimalMax(value = "1000000.0", inclusive = false, message = "El peso debe ser menor que 1000000")
    private Float weight;

    @NotNull(message = "La altura es obligatoria")
    @DecimalMin(value = "0.0", inclusive = false, message = "La altura debe ser mayor que 0")
    @DecimalMax(value = "1000000.0", inclusive = false, message = "La altura debe ser menor que 1000000")
    private Float height;

    @NotNull(message = "La localización es obligatoria")
    @Positive(message = "El ID de localización debe ser un número positivo")
    @Max(value = Long.MAX_VALUE, message = "El ID de localización supera el valor máximo de Long")
    private Long locationId;

    @NotEmpty(message = "El personaje debe estar como mínimo en un episodio")
    private List<@NotNull @Positive Long> episodeIds = new ArrayList<>();

    public CharacterDTO() {
    }

    public CharacterDTO(Long id, String name, String status, String species, String type,
                        String gender, Float weight, Float height, Long locationId,
                        List<Long> episodeIds) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.species = species;
        this.type = type;
        this.gender = gender;
        this.weight = weight;
        this.height = height;
        this.locationId = locationId;
        this.episodeIds = episodeIds;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getSpecies() { return species; }
    public void setSpecies(String species) { this.species = species; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }

    public Float getWeight() { return weight; }
    public void setWeight(Float weight) { this.weight = weight; }

    public Float getHeight() { return height; }
    public void setHeight(Float height) { this.height = height; }

    public Long getLocationId() { return locationId; }
    public void setLocationId(Long locationId) { this.locationId = locationId; }

    public List<Long> getEpisodeIds() { return episodeIds; }
    public void setEpisodeIds(List<Long> episodeIds) { this.episodeIds = episodeIds; }
}