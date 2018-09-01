import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Disjunction extends Sentence {

    private Set<Literal> literals;

    public Disjunction() {
        literals = new HashSet<>();
    }

    protected void removeLiteral(Literal u) {
        literals.remove(u);
    }

    @Override
    protected Set<Sentence> getClauses() {
        Set<Sentence> clauses = new HashSet<>();
        clauses.add(this);
        return clauses;
    }

    @Override
    protected Set<Literal> getLiterals() {
        return literals;
    }

    @Override
    public Literal pickVariable() {
        Map<Literal, Integer> counts = new HashMap<>();
        Literal choice = null;
        int maxCount = 0;
        for (Literal u: literals) {
            for (Literal u: literal.getLiterals()) {
                Integer count = counts.get(u);
                if (count == null) {
                    counts.put(u, 0);
                    continue;
                }
                counts.put(u, ++count);
                if (count > maxCount) {
                    maxCount = count;
                    choice = u;
                }
            }
        }
        return choice;
    }

    @Override
    boolean isEmpty() {
        return false;
    }

    @Override
    boolean hasEmptyClause() {
        return false;
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
