package net.pandamc.core.tags;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TagType {

    CUSTOM("Customs"),
    HASHTAG("HashTags"),
    PREMIUM("Premiums"),
    PAISES("Paises"),
    COMUNES("Comunes");

    private final String name;
}
