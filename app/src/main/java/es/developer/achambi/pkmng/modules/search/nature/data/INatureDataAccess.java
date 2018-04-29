package es.developer.achambi.pkmng.modules.search.nature.data;

import java.util.ArrayList;

import es.developer.achambi.pkmng.modules.search.nature.model.Nature;

public interface INatureDataAccess {
    ArrayList<Nature> accessData();
    Nature accessNatureDate(int natureId);
}
