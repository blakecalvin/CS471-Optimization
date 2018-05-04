#!/bin/bash

cd src/
javac *.java
javac FitnessFunctions/*.java

# ------ TEST SECTION -------

# input syntax:
# java Main [Algorithm] [# of Dimensions] [# of Iterations] [Output file]
#
# Algorithms:
# BS = Blind Search
# LS = Local Search
# ILS = Iterative Local Search
# GA = Genetic Algorithm
# DE = Differential Evolution

java Main GA 10 100 GA.csv

# ------ TEST SECTION END -------