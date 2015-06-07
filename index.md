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


# Downloads
The current version of this software is [{{ site.data.currentVersion.currentVersion }}]({{ site.data.currentVersion.currentVersionRepo }}).
A stand-alone `jar` is provided [here]({{ site.data.currentVersion.currentVersionStandAloneJarURL }}) and
a set of slides introducing the whole framework and providing examples can be found
[here](http://github.com/optimizationBenchmarking/optimizationBenchmarking/raw/documentation/documents/evaluatorSlides/evaluatorSlides.pdf).

# Examples
We provide a set of examples which can be executed directly on the command line of
your Linux machine, given that you have `Java 1.7` installed (and potentially `svn` and
a LaTeX installation).

1. Comparison of Algorithms of Maximum Satisfiability Problem: [script](https://github.com/optimizationBenchmarking/optimizationBenchmarking/blob/documentation/examples/maxSat/make.sh)
2. Comparison of Some Algorithms from [BBOB'2013](http://coco.gforge.inria.fr/doku.php?id=bbob-2013): [script](https://github.com/optimizationBenchmarking/optimizationBenchmarking/blob/documentation/examples/bbob/make.sh)
3. Comparison of Some Algorithms for the [TSP](https://github.com/optimizationBenchmarking/tspSuite) : [script](https://github.com/optimizationBenchmarking/optimizationBenchmarking/blob/documentation/examples/tspSuite/make.sh)

These examples can also be run on Windows (by using different commands and/or doing stuff by hand). Right now we only provide shell scripts for Linux.

# Workflow
The *optimizationBenchmarking.org* framework prescribes the following work flow, which is discussed
in more detail in [this set of slides](http://github.com/optimizationBenchmarking/optimizationBenchmarking/raw/documentation/documents/evaluatorSlides/evaluatorSlides.pdf):

1. *Algorithm Implementation:* You implement your algorithm. Do it in a way so that you can generate
   log files containing rows such as (`passed runtime`, `best solution quality so far`) for each run (execution) of your algorithm.
2. *Choose Benchmark Instances:* Choose a set of (well-known) problem instances to apply your algorithm to.
3. *Experiments:* Well, run your algorithm, i.e., apply it a few times to each benchmark instance. You get the log files.
   Actually, you may want to do this several times with different parameter settings of your algorithm. Or maybe for different algorithms, so you have comparison data.
4. *Use Evaluator:* Now, you can use our evaluator component to find our how good your method works!
  1. *Define Dimensions:* First, you define which dimensions (runtime? number of generated solutions? solution quality?) you have measured.
  2. *Define Instances:* Now you provide information about the problem instances you used for benchmarking. For example, for a TSP instance, you may provide the number of cities. Our system can then deduce how your algorithm's performance is related to the number of cities!
  3. *Define Experiments:* Provide the values of the configuration parameters of your algorithm. The goal is that our framework can use this to find out how the performance of your method is related to its configuration parameters, if you did multiple experiments with different settings.
  4. *Define Evaluation:* Define what kind of information you want to obtain. Is it something like the ECDF? Or diagrams showing how your method progresses over time?
  5. *Define Output Format:* Do you want LaTeX output? Maybe optimized for an IEEE Transactions journal? Or rather XHTML for publishing on your website?
  6. *Run Evaluator:* You apply the evaluator. A report is created with all the information you want. 

Notice that all of this defining of information for instances or dimensions only needs to be done
once for any benchmarking scenario and can be re-used. Also it won't take much longer than a few minutes.
And implementing the algorithm is something you would need to do anyway. So although the above looks a bit much,
it is actually not. On the other hand, you will not need to draw any figure by hand anymore. Plus you
will get higher-level conclusions and a complete analysis of your algorithm as report. (Well, right
now, we just have figures, but we will get there -- once we have transitioned from alpha to beta status.) 
