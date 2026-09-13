package ch.supsi.cardgames.models;

public enum Condition {
    NUOVA,COME_NUOVA,USATA, LEGGERMENTE_DANNEGIATA,DANNEGIATA;

    @Override
    public String toString() {
        return switch (this){
            case NUOVA -> "Nuova";
            case COME_NUOVA -> "Come nuova";
            case USATA  -> "Usata";
            case LEGGERMENTE_DANNEGIATA -> "Leggermente Danneggiata";
            case DANNEGIATA -> "Danneggiata";

        };
    }
}
