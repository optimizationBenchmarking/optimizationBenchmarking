---
layout: repo
title: Alpha Version 0.8.0
---

# Version 0.8.0: First Alpha Version

This is the first Alpha version of our project. It is not beautiful, lacks some
functionality, and surely contains some minor bugs. But it works. You can use it
to evaluate data gathered from your own experiments,
with [COCO](http://coco.gforge.inria.fr/doku.php?id=start)),
or with [TSP Suite](https://github.com/optimizationBenchmarking/tspSuite). It can
produce reports in LaTeX, XHTML, or in a textual format suitable for export to
other applications. So, although we still have some way to go, this release is a
first major milestone and can already be used. 
  
* [Stand-Alone Executable](optimizationBenchmarking-0.8.0-full.jar) [[md5](optimizationBenchmarking-0.8.0-full.jar.md5)] [[sha1](optimizationBenchmarking-0.8.0-full.jar.sha1)]
* [Executable without Dependencies](optimizationBenchmarking-0.8.0.jar) [[md5](optimizationBenchmarking-0.8.0.jar.md5)] [[sha1](optimizationBenchmarking-0.8.0.jar.sha1)]
* [Sources](optimizationBenchmarking-0.8.0-sources.jar) [[md5](optimizationBenchmarking-0.8.0-sources.jar.md5)] [[sha1](optimizationBenchmarking-0.8.0-sources.jar.sha1)]
* [JavaDoc](optimizationBenchmarking-0.8.0-javadoc.jar) [[md5](optimizationBenchmarking-0.8.0-javadoc.jar.md5)] [[sha1](optimizationBenchmarking-0.8.0-javadoc.jar.sha1)]
* [Maven `pom`](optimizationBenchmarking-0.8.0.pom) [[md5](optimizationBenchmarking-0.8.0.pom.md5)] [[sha1](optimizationBenchmarking-0.8.0.pom.sha1)]

    <repositories>
      <repository>
        <id>optimizationBenchmarking</id>
        <url>http://optimizationbenchmarking.github.io/optimizationBenchmarking/repo/</url>
      </repository>
    </repositories>
    <dependencies>
      <dependency>
        <groupId>optimizationBenchmarking.org</groupId>
        <artifactId>optimizationBenchmarking</artifactId>
        <version>0.8.0</version>
      </dependency>
    </dependencies>