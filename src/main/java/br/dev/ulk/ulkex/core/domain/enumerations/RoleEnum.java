package br.dev.ulk.ulkex.core.domain.enumerations;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public enum RoleEnum {

    ADMIN("ADMIN"),
    BASIC("BASIC");

    private String description;

    RoleEnum(String description) {
        this.description = description;
    }
}