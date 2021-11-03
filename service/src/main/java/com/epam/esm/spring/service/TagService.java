package com.epam.esm.spring.service;

import com.epam.esm.spring.repository.model.Tag;
import com.epam.esm.spring.service.dto.CertificateDto;
import com.epam.esm.spring.service.dto.TagDto;

import java.util.List;

public interface TagService extends CrudService<TagDto> {

    /**
     * Gets Tag by NAME given
     *
     * @param name is the NAME of the Tag
     * @return TagDto object instance if Tag with NAME given found
     */
    TagDto findByName(String name);

    /**
     * Checks whether Tags from the list are
     * present in the db, if so - reads and stores ist id ,
     * if not - creates and stores id of just created one
     *
     * @param tags list of Tags
     */
    List<Tag> processTagList(List<Tag> tags);

    /**
     * Checks out whether Tag with NAME given exists
     *
     * @param name the name of Tag
     * @return true in case such Tag found in the DB
     */
    boolean isExist(String name);

    /**
     * Gets the most widely used tag of a user with the highest cost of all orders.
     *
     * @return TagDto tag
     */
    TagDto findMostUsed();
}
