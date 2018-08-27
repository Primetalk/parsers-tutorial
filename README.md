# parsers-tutorial

A tutorial project that demonstrates parser combinators (for a talk).

The tutorial has a few commits. Each commit corresponds to a single slide
in a discussion.

### 0. Introduction

Parser is a function that converts plain text to 
a computer comprehensible data structure.

Parser combinators allows to construct a new parser from simple parsers.
The program that uses parser combinators resemble grammar definition
and can be easily understood.

For our tutorial we'll use Li Haoyi's FastParse library, maven build, 
ScalaTest/FlatSpec for tests. 


### 3. Introduce parsers


    git checkout bf44507
Parsers that could be used with combinators
We'll start with a
