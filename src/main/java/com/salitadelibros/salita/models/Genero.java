package com.salitadelibros.salita.models;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

public enum Genero {
    @Enumerated(EnumType.STRING)
    POESIA,
    @Enumerated(EnumType.STRING)
    TEATRO,
    @Enumerated(EnumType.STRING)
    NARRATIVA
}
