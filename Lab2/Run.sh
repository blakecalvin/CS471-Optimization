#!/bin/bash

cd src/
javac *.java

# ------ TEST SECTION -------

# input syntax:
# java Main [Algorithm #] [# of Dimensions] [# of Iterations] [Output file]
#
# Algorithms:
# 1. Blind Search
# 2. Local Search
# 3. Iterative Local Search

java Main 1 10 100 BlindSearch.csv
ECHO "#1 Complete"
java Main 1 20 100 BlindSearch.csv
ECHO "#2 Complete"
java Main 1 30 100 BlindSearch.csv
ECHO "#3 Complete"

java Main 2 10 100 LocalSearch.csv
ECHO "#4 Complete"
java Main 2 20 100 LocalSearch.csv
ECHO "#5 Complete"
java Main 2 30 100 LocalSearch.csv
ECHO "#6 Complete"

java Main 3 10 100 IterativeLocalSearch.csv
ECHO "#7 Complete"
java Main 3 20 100 IterativeLocalSearch.csv
ECHO "#8 Complete"
java Main 3 30 100 IterativeLocalSearch.csv
ECHO "#9 Complete"

# ------ TEST SECTION END -------