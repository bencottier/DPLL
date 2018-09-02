import java.util.Objects;

/**
 * Representation of an atomic logical formula (one symbol) or its negation.
 */
public class Variable extends Sentence {

    private String name;
    private boolean negated;

    public Variable(String name, boolean negated) {
        this.name = name;
        this.negated = negated;
    }

    /**
     * Copy constructor.
     */
    public Variable(Variable v) {
        this.name = v.name;
        this.negated = v.negated;
    }

    public boolean isNegated() {
        return negated;
    }

    public void negate() {
        negated = !negated;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Variable variable = (Variable) o;
        return negated == variable.negated &&
                Objects.equals(name, variable.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, negated);
    }
}
