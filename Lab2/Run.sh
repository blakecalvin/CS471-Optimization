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

echo "ILS 10 100"
java Main ILS 10 100 ILS.csv
echo "ILS 20 100"
java Main ILS 20 100 ILS.csv
echo "ILS 30 100"
java Main ILS 30 100 ILS.csv

# ------ TEST SECTION END -------
