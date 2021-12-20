package com.epam.esm.spring.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestDto extends AbstractDto {

    @NotBlank
    @Pattern(regexp = "^[a-zA-Z0-9]{3,45}+$")
    private String username;

    @NotBlank
    @Pattern(regexp = "^[a-zA-Z0-9!@#$%^&*()=+_-]{3,45}+$")
    private String password;

    @Pattern(regexp = "^[a-zA-Z0-9]{3,45}+$")
    private String firstname;

    @Pattern(regexp = "^[a-zA-Z0-9]{3,45}+$")
    private String lastname;

    @Builder
    public UserRequestDto(long id, String username, String password, String firstname, String lastname) {
        super(id);
        this.username = username;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
    }
}