public class DPLL {

    /**
     * Determine whether the given sentence is satisfiable using the
     * Davis-Putnam-Logeman-Loveland (DPLL) algorithm.
     *
     * @param s sentence to analyse.
     * @return true if the sentence is satisfiable.
     */
    public static boolean dpllSatisfiable(CNFSentence s) {
        if (s.isEmpty()) {
            return true;
        }
        if (s.hasEmptyClause()) {
            return false;
        }
        Variable u = s.getUnitClause();
        if (u != null) {
            return dpllSatisfiable(s.assign(u));
        }
        u = s.getPureLiteral();
        if (u != null) {
            return dpllSatisfiable(s.assign(u));
        }
        Variable v = s.pickVariable();
        if (dpllSatisfiable(s.assign(v))) {
            return true;
        } else {
            v.negate();
            return dpllSatisfiable(s.assign(v));
        }
    }

    public static void main(String[] args) {
        String repr = args[0];
        CNFSentence s = new CNFSentence(repr);
        System.out.println(dpllSatisfiable(s));
    }

}
