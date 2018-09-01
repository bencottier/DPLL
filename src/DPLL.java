public class DPLL {

    /**
     * Determine whether the given sentence is satisfiable using the
     * Davis-Putnam-Logeman-Loveland (DPLL) algorithm.
     *
     * @param s sentence to analyse.
     * @return true if the sentence is satisfiable.
     */
    public boolean dpllSatisfiable(Sentence s) {
        if (s.isEmpty()) {
            return true;
        }
        if (s.hasEmptyClause()) {
            return false;
        }
        Literal u = s.getUnitClause();
        if (u != null) {
            return dpllSatisfiable(s.assign(u));
        }
        u = s.getPureLiteral();
        if (u != null) {
            return dpllSatisfiable(s.assign(u));
        }
        Literal v = s.pickVariable();
        if (dpllSatisfiable(s.assign(v))) {
            return true;
        } else {
            return dpllSatisfiable(s.assign(Logic.not(v)));
        }
    }

    /**
     * Choose a literal in a sentence based on number of occurrences.
     *
     * @param s the sentence to analyse.
     * @return literal in the sentence with the highest occurence.
     */
    public Literal pickVariable(Sentence s) {
        return null;
    }

    public static void main(String[] args) {

    }

}
