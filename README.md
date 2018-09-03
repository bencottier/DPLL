## DPLL Algorithm
A simple implementation of the DPLL algorithm.
DPLL takes a sentence in the conjunctive normal form of propositional logic and determines whether the given sentence is satisfiable.

### Usage
After compiling the java classes, run from the command line using

`java DPLL "<CNF sentence>"`

where `<CNF sentence>` uses only the negation (~), disjunction (v) and conjunction (&) operators in CNF form, e.g.

- `A`
- `A v B`
- `(A v ~B) & C`
- `(A v B) & (B v ~C)`

The operator symbols can be changed in `Sentence.java`.

We hope you find this tool satisfying.
