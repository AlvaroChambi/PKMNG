package es.developer.achambi.pkmng.core.ui.presentation;


public class TypesPresentation {
    public final String effectiveAgainst;
    public final String weakAgainst;
    public final TypePresentation first;
    public final TypePresentation second;

    public TypesPresentation( TypePresentation first, TypePresentation second,
                              String effectiveAgainst, String weakAgainst ) {
        this.effectiveAgainst = effectiveAgainst;
        this.weakAgainst = weakAgainst;
        this.first = first;
        this.second = second;
    }
}
