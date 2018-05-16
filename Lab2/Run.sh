#!/bin/bash

cd src/
javac *.java
javac FitnessFormulas/*.java

# ------ TEST SECTION -------

# input syntax:
# java Main [Algorithm] [# of Dimensions] [# of Iterations/Generations] [Output file]
#
# Algorithms:
# BS = Blind Search
# LS = Local Search
# ILS = Iterative Local Search
# GA = Genetic Algorithm
# DE = Differential Evolution

java Main GA 10 100 ILS.csv
java Main GA 20 100 ILS.csv
java Main GA 30 100 ILS.csv

# ------ TEST SECTION END -------
