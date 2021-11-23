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
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class UserDto extends AbstractDto {

    @NotBlank
    @Pattern(regexp = "^[a-zA-Z0-9]{3,45}+$")
    private String username;

    @Pattern(regexp = "^[a-zA-Z0-9]{3,45}+$")
    private String firstname;

    @Pattern(regexp = "^[a-zA-Z0-9]{3,45}+$")
    private String lastname;

    @Builder
    public UserDto(long id, String username, String firstname, String lastname) {
        super(id);
        this.username = username;
        this.firstname = firstname;
        this.lastname = lastname;
    }
}
