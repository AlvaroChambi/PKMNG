package es.developer.achambi.pkmng.modules.create.screen;

import es.developer.achambi.pkmng.core.ui.presentation.TypePresentation;

public class MoveConfigurationRepresentation {
    public final int id;
    public final String name;
    public final TypePresentation type;
    public final String power;
    public final boolean isEmpty;

    public MoveConfigurationRepresentation(int id, String name, TypePresentation type,
                                           String power, boolean isEmpty) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.power = power;
        this.isEmpty = isEmpty;
    }
}


