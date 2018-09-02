import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;


/**
 * Representation of a logical disjunction.
 */
public class Disjunction extends Sentence {

    /** The set of variables contained in the disjunction. */
    protected Set<Variable> variables;

    public Disjunction() {
        variables = new HashSet<>();
    }

    /**
     * Copy constructor.
     */
    public Disjunction(Disjunction d) {
        variables = new HashSet<>();
        for (Variable v: d.variables) {
            variables.add(new Variable(v));
        }
    }

    /**
     * Construct a sentence from a string representation.
     *
     * Expects a certain format, e.g.
     * ~P v Q
     *
     * @param repr the string representation of the sentence.
     * @throws IllegalArgumentException if the format is incorrect.
     */
    public Disjunction(String repr)
            throws IllegalArgumentException {
        variables = new HashSet<>();
        if (repr.length() == 0) {
            return;
        }
        String[] splitOR = repr.split(OR);
        for (String v : splitOR) {
            v = v.trim();
            boolean negated = false;
            if (v.contains(NOT)) {
                v = v.replace(NOT, "");
                negated = true;
            }
            Variable var = new Variable(v, negated);
            variables.add(var);
        }
    }

    /**
     * If this is a unit clause, return its one variable.
     *
     * @return the variable if this is a unit clause, else null.
     */
    protected Variable getUnitClause() {
        if (variables.size() == 1) {
            Iterator<Variable> it = variables.iterator();
            return it.next();
        }
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Disjunction that = (Disjunction) o;
        return Objects.equals(variables, that.variables);
    }

    @Override
    public int hashCode() {
        return Objects.hash(variables);
    }
}
