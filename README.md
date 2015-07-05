# [optimizationBenchmarking](http://optimizationbenchmarking.github.io/optimizationBenchmarking/)

[<img alt="Travis CI Build Status" src="https://img.shields.io/travis/optimizationBenchmarking/optimizationBenchmarking/master.svg" height="20"/>](https://travis-ci.org/optimizationBenchmarking/optimizationBenchmarking/)
[<img alt="Codeship Build Status" src="https://img.shields.io/codeship/40b0dfd0-b2aa-0132-9d6a-62be5ffebe05.svg" height="20"/>](https://codeship.com/projects/40b0dfd0-b2aa-0132-9d6a-62be5ffebe05/status?branch=master)
[<img alt="CircleCI Build Status" src="https://img.shields.io/circleci/project/optimizationBenchmarking/optimizationBenchmarking.svg" height="20"/>](https://circleci.com/gh/optimizationBenchmarking/optimizationBenchmarking)
[<img alt="Semaphore Build Status" src="https://semaphoreci.com/api/v1/projects/7e98df8c-dc67-416f-a660-cb6f803fc3cf/380468/shields_badge.svg" height="20"/>](https://semaphoreci.com/thomasweise/optimizationbenchmarking)

Please visit our [website](http://www.optimizationBenchmarking.org/) to
download the latest release in form of a stand-alone executable.
A set of slides with examples and descriptions
how to use the system can be found [here](https://raw.githubusercontent.com/optimizationBenchmarking/optimizationBenchmarking/documentation/documents/evaluatorSlides/evaluatorSlides.pdf).

This is the main source repository of the optimizationBenchmarking.org tool suite.
The optimizationBenchmarking.org tool suite supports researchers in
evaluating and comparing the performance of (anytime) optimization
algorithms, such as
[Local Search] (http://en.wikipedia.org/wiki/Local_search_%28optimization%29),
[Evolutionary Algorithms](http://en.wikipedia.org/wiki/Evolutionary_algorithm),
[Swarm Intelligence](http://en.wikipedia.org/wiki/Swarm_intelligence) methods,
[Branch and Bound](http://en.wikipedia.org/wiki/Branch_and_bound),
and virtually all other
[metaheuristics](http://en.wikipedia.org/wiki/Metaheuristic).

## System Requirements

1. Java 1.7: Ideally a JDK, because under a JRE, the software is both slower and needs more memory
2. optional: a LaTeX installation such as MikTeX or TexLive
3. the [third party libraries](https://github.com/optimizationBenchmarking/optimizationBenchmarking/blob/master/LICENSE.md) `optimizationBenchmarking` depends on if and only if you do not use the stand-alone/full executable

## Optimization and Anytime Algorithms

Optimization algorithms are algorithms which can find (approximate)
solutions for computationally hard (e.g., [NP-hard](http://en.wikipedia.org/wiki/NP-hard)) problems,
such as the
[Traveling Salesman Problem](http://en.wikipedia.org/wiki/Travelling_salesman_problem),
the [Maximum Satisfiability Problem](http://en.wikipedia.org/wiki/Maximum_satisfiability_problem),
or the [Bin Packing Problem](http://en.wikipedia.org/wiki/Bin_packing_problem).
For this kind of problems, solvers cannot guarantee to always find the
globally best possible solution within feasible time. In order to
solve these problems, solution quality has to be traded in for shorter
runtime.

Anytime optimization algorithms do this by starting
with a more or less random (and hence usually bad) approximation
of the solution and improve this approximation during their course.
Comparing two such algorithms is not an easy thing, since it involves
comparing behavior over runtime.

In this project, we try to provide a set of tools to make this process
easier. The currently available tool can load log files (with rows of, e.g., the form
`consumed-runtime, best-solution-found`) and render performance reports in a variety of
different formats, including LaTeX and XHTML. These reports contain performance metrics
and comparisons carried out according to a user-provided specification.

## Examples

We provide a set of examples which can be executed directly on the command lines of
your Linux or Windows machine, given that you have `Java 1.7` installed (and potentially `svn` and a LaTeX installation). No further installation or downloads are required, nothing will be installed (just downloaded into the current folder).

1. Comparison of Algorithms of Maximum Satisfiability Problem: [[Linux](https://github.com/optimizationBenchmarking/optimizationBenchmarking/blob/documentation/examples/maxSat/make.sh)] [[Windows](https://github.com/optimizationBenchmarking/optimizationBenchmarking/blob/documentation/examples/maxSat/make.bat)]
2. Comparison of Some Algorithms from [BBOB'2013](http://coco.gforge.inria.fr/doku.php?id=bbob-2013): [[Linux](https://github.com/optimizationBenchmarking/optimizationBenchmarking/blob/documentation/examples/bbob/make.sh)] [[Windows](https://github.com/optimizationBenchmarking/optimizationBenchmarking/blob/documentation/examples/bbob/make.bat)]
3. Comparison of Some Algorithms for the [TSP](https://github.com/optimizationBenchmarking/tspSuite): [[Linux](https://github.com/optimizationBenchmarking/optimizationBenchmarking/blob/documentation/examples/tspSuite/make.sh)] [[Windows](https://github.com/optimizationBenchmarking/optimizationBenchmarking/blob/documentation/examples/tspSuite/make.bat)]