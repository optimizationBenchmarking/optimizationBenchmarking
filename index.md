---
layout: main
title: optimizationBenchmarking.org Main Code Repository
permalink: /
---

# Introduction
This is the main repository of the *optimizationBenchmarking.org* framework, a `Java 1.7` software designed
to make the evaluation, benchmarking, and comparison of [optimization](http://en.wikipedia.org/wiki/Mathematical_optimization) or
[Machine Learning](http://en.wikipedia.org/wiki/Machine_learning) algorithms easier.
It can load log files created by an optimization or Machine Learning algorithm implementation, evaluate
how the implementation has progressed over time, and compare its performance to other algorithms (or implementations) -- over several different benchmark cases.
It can create reports in [LaTeX](http://en.wikipedia.org/wiki/LaTeX) (ready for publication) or
[XHTML](http://en.wikipedia.org/wiki/XHTML) formats or export its findings in
text files which may later be loaded by other applications.


## Downloads
The current version of this software is [{{ site.data.currentVersion.currentVersion }}]({{ site.data.currentVersion.currentVersionRepo }}).
A stand-alone `jar` is provided [here]({{ site.data.currentVersion.currentVersionStandAloneJarURL }}) and
a set of slides introducing the whole framework and providing examples can be found
[here]({{ site.data.documentation.evaluatorSlidesURL }}).

## Examples
We provide a set of examples which can be executed directly on the command lines of
your Linux or Windows machine, given that you have `Java 1.7` installed (and potentially `svn` and a LaTeX installation). No further installation or downloads are required, nothing will be installed (just copied into the current folder).

1. Comparison of Algorithms of Maximum Satisfiability Problem: [[Linux]({{ site.data.documentation.exampleMaxSatLinuxURL }})] [[Windows]({{ site.data.documentation.exampleMaxSatWindowsURL }})]
2. Comparison of Some Algorithms from [BBOB'2013](http://coco.gforge.inria.fr/doku.php?id=bbob-2013): [[Linux]({{ site.data.documentation.exampleBBOBLinuxURL }})] [[Windows]({{ site.data.documentation.exampleBBOBWindowsURL }})]
3. Comparison of Some Algorithms for the [TSP](https://github.com/optimizationBenchmarking/tspSuite): [[Linux]({{ site.data.documentation.exampleTSPSuiteLinuxURL }})] [[Windows]({{ site.data.documentation.exampleTSPSuiteWindowsURL }})]

## Workflow
The *optimizationBenchmarking.org* framework prescribes the following work flow, which is discussed
in more detail in [this set of slides]({{ site.data.documentation.evaluatorSlidesURL }}):

1. *Algorithm Implementation:* You implement your algorithm. Do it in a way so that you can generate
   log files containing rows such as (`passed runtime`, `best solution quality so far`) for each run (execution) of your algorithm.
2. *Choose Benchmark Instances:* Choose a set of (well-known) problem instances to apply your algorithm to.
3. *Experiments:* Well, run your algorithm, i.e., apply it a few times to each benchmark instance. You get the log files.
   Actually, you may want to do this several times with different parameter settings of your algorithm. Or maybe for different algorithms, so you have comparison data.
4. *Use Evaluator:* Now, you can use our evaluator component to find our how good your method works!
   For this, you can define the *dimensions* you have measured (such as runtime and solution quality),
   the features of your benchmark instances (such as number of cities in a TSP), the parameter settings
   of your algorithm (such as population size of an EA), information you want to get (ECDF? performance over time?),
   and how you want to get it (LaTeX, optimized for IEEE Transactions, ACM, or Springer LNCS? or maybe XHTML for the web?).
   Our evaluator will create the report with the desired information in the desired format.