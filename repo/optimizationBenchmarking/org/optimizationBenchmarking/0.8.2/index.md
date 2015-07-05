---
layout: repo
title: Third Alpha Version 0.8.2
---

# Version 0.8.2: Third Alpha Version

This is the second Alpha version of our project. 
The robustness of the software has been improved, in particular it no longer strictly
requires a Java JDK, but can also run under a JRE. If the evaluator is executed under
a JDK and loads experiment data, it will automatically generate and compile data
container classes which are tailored to the kind of data being read. Under a JRE,
we will no longer throw an exception and terminate, but instead use fall-back
classes. These classes are both slower and require more memory than the tailored ones,
but at least the system will work properly. 
  
* [Stand-Alone Executable](optimizationBenchmarking-0.8.2-full.jar) [[md5](optimizationBenchmarking-0.8.2-full.jar.md5)] [[sha1](optimizationBenchmarking-0.8.2-full.jar.sha1)]
* [Executable without Dependencies](optimizationBenchmarking-0.8.2.jar) [[md5](optimizationBenchmarking-0.8.2.jar.md5)] [[sha1](optimizationBenchmarking-0.8.2.jar.sha1)]
* [Sources](optimizationBenchmarking-0.8.2-sources.jar) [[md5](optimizationBenchmarking-0.8.2-sources.jar.md5)] [[sha1](optimizationBenchmarking-0.8.2-sources.jar.sha1)]
* [JavaDoc](optimizationBenchmarking-0.8.2-javadoc.jar) [[md5](optimizationBenchmarking-0.8.2-javadoc.jar.md5)] [[sha1](optimizationBenchmarking-0.8.2-javadoc.jar.sha1)]
* [Maven `pom`](optimizationBenchmarking-0.8.2.pom) [[md5](optimizationBenchmarking-0.8.2.pom.md5)] [[sha1](optimizationBenchmarking-0.8.2.pom.sha1)]

{% include mavenPom.md projectVersion="0.8.2"%}