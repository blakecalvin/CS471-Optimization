#!/bin/bash

cd src/
javac *.java

# ------ TEST SECTION -------

# input syntax:
# java Project_2 [Algorithm #] [# of Dimensions] [# of Iterations] [Output file]
#
# Algorithms:
# 1. Blind Search
# 2. Local Search
# 3. Iterative Local Search

java Project_2 1 10 100 BlindSearch.csv
ECHO "#1 Complete"
java Project_2 1 20 100 BlindSearch.csv
ECHO "#2 Complete"
java Project_2 1 30 100 BlindSearch.csv
ECHO "#3 Complete"

java Project_2 2 10 100 LocalSearch.csv
ECHO "#4 Complete"
java Project_2 2 20 100 LocalSearch.csv
ECHO "#5 Complete"
java Project_2 2 30 100 LocalSearch.csv
ECHO "#6 Complete"

java Project_2 3 10 100 IterativeLocalSearch.csv
ECHO "#7 Complete"
java Project_2 3 20 100 IterativeLocalSearch.csv
ECHO "#8 Complete"
java Project_2 3 30 100 IterativeLocalSearch.csv
ECHO "#9 Complete"

# ------ TEST SECTION END -------