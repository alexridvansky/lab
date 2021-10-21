package com.epam.esm.spring.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private Long id;

    @NotBlank
    @Pattern(regexp = "^[a-zA-Z0-9]{3,45}+$")
    private String username;

    @Pattern(regexp = "^[a-zA-Z0-9]{3,45}+$")
    private String firstname;

    @Pattern(regexp = "^[a-zA-Z0-9]{3,45}+$")
    private String lastname;
}
