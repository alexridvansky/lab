package com.epam.esm.spring.service.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.hateoas.Links;
import org.springframework.hateoas.RepresentationModel;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public abstract class AbstractDto extends RepresentationModel<AbstractDto> {
    private Long id;

    @JsonProperty("links")
    @JsonInclude(value = JsonInclude.Include.CUSTOM, valueFilter = EmptyLinksFilter.class)
    @Override
    public Links getLinks() {
        return super.getLinks();
    }

    static class EmptyLinksFilter{

        @Override
        public boolean equals(Object obj) {

            if (!(obj instanceof Links)) {
                return false;
            }
            Links links = (Links) obj;
            return links.isEmpty();
        }
    }
}
