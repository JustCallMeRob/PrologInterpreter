# prolog-interpreter 
A small interpreter that takes as input some algebraic data-type that represents the abstract syntax-tree of a language.
In essence this is an interpreter that behaves like the [Prolog](https://www.wikiwand.com/en/Prolog) one, 
which is a logical programming language with roots in first order logic.

## Example  
```
1 ?- suitAndTieGentleman(X).
X = bunk.

2 ?- suitAndTieGentleman(bunk).
true.
```
In case 1 we ask to find all X for which the clause is true, i.e. it's a search problem. 

In case 2 we simply ask wether or not the clause is true, i.e. it's a decision problem.

