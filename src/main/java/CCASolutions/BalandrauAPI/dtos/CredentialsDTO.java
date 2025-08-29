package CCASolutions.BalandrauAPI.dtos;

import java.time.LocalDateTime;

public record CredentialsDTO(String nombre, String password, LocalDateTime fecha) {}
