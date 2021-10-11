package com.epam.esm.spring.service.util;

import com.epam.esm.spring.service.dto.CertificateDto;
import com.epam.esm.spring.service.dto.TagDto;
import com.epam.esm.spring.service.exception.EntryNonValidDurationException;
import com.epam.esm.spring.service.exception.EntryNonValidNameException;
import com.epam.esm.spring.service.exception.EntryNonValidPriceException;
import com.epam.esm.spring.service.exception.EntryNonValidRequestException;
import com.epam.esm.spring.service.exception.EntryNonValidTagNameException;
import com.epam.esm.spring.service.exception.EntryNotFoundException;
import lombok.experimental.UtilityClass;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;

@Component
public class CertificateValidator {
    // we could use some regexp from properties.file here

    public static boolean isIdValid(CertificateDto c) {
        return c.getId() != null && c.getId() > 0;
    }

    public static boolean isNameValid(CertificateDto c) {
        return c.getName() != null && !c.getName().isEmpty() && c.getName().length() > 3;
    }

    public boolean isDescriptionValid(CertificateDto c) {
        return c.getDescription() != null && !c.getDescription().isEmpty() && c.getDescription().length() > 3;
    }

    public boolean isPriceValid(CertificateDto c) {
        return c.getPrice() != null && c.getPrice().compareTo(BigDecimal.ZERO) > 0;
    }

    public boolean isDurationValid(CertificateDto c) {
        return c.getDuration() != null && c.getDuration() > 0;
    }

    public boolean isTagValid(TagDto t) {
        return t != null && t.getName() != null && t.getName().length() > 3;
    }

    public boolean isCertificateValidForUpdate(CertificateDto c) {
        if (c == null) {
            throw new EntryNonValidRequestException();
        }
        if (c.getName() != null && !isNameValid(c)) {
            throw new EntryNonValidNameException();
        }
        if (c.getDescription() != null && !isDescriptionValid(c)) {
            throw new EntryNotFoundException();
        }
        if (c.getPrice() != null && !isPriceValid(c)) {
            throw new EntryNonValidPriceException();
        }
        if (c.getDuration() != null && !isDurationValid(c)) {
            throw new EntryNonValidDurationException();
        }
        if (c.getTags() != null && !c.getTags().stream().allMatch(this::isTagValid)){
            throw new EntryNonValidTagNameException();
        }

        return true;
    }

    public boolean isCertificateValidForInsert(CertificateDto c) {
        if (c == null) {
            throw new EntryNonValidRequestException();
        }
        if (!isNameValid(c)) {
            throw new EntryNonValidNameException();
        }
        if (!isDescriptionValid(c)) {
            throw new EntryNotFoundException();
        }
        if (!isPriceValid(c)) {
            throw new EntryNonValidPriceException();
        }
        if (!isDurationValid(c)) {
            throw new EntryNonValidDurationException();
        }
        if (c.getTags() == null) {
            c.setTags(new ArrayList<>());
        }
        if (!c.getTags().stream().allMatch(this::isTagValid)){
            throw new EntryNonValidTagNameException();
        }

        return true;
    }
}
