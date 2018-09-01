import java.util.Set;

public class EmptyClause extends Sentence {

    @Override
    protected Set<Sentence> getClauses() {
        return null;
    }

    @Override
    protected Set<Literal> getLiterals() {
        return null;
    }

    @Override
    public Literal pickVariable() {
        return null;
    }

    @Override
    boolean isEmpty() {
        return false;
    }

    @Override
    boolean hasEmptyClause() {
        return true;
    }

    @Override
    Literal getUnitClause() {
        return null;
    }

    @Override
    Literal getPureLiteral() {
        return null;
    }

    @Override
    Sentence assign(Literal u) {
        return null;
    }
}
