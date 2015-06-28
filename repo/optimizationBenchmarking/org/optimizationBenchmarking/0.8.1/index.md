---
layout: repo
title: Second Alpha Version 0.8.1
---

# Version 0.8.1: Second Alpha Version

This is the second Alpha version of our project. It contains some major
changes compared to the first alpha version:

1. You can now transform input data for the supported diagrams with almost arbitrary
   functions. Let's say you want the x-axis of an ECDF diagram to not just represent
   your function evaluations (`FEs`) dimensions, but the logarithm of `1+FEs`? Then
   just specify `<cfg:parameter name="xAxis" value="lg(1+FEs)" />` in your
   `evaluation.xml`!
2. You can use all numerical instance features and experiment parameters, as well
   as dimension limits in these expressions too! Let's say you have measured data
   for experiments with Traveling Salesman Problems (TSPs) and you also want to
   scale the above FEs by dividing them by the square of the feature `n` which
   denotes the number of cities in a benchmark instance. Then how about specifying
   `<cfg:parameter name="xAxis" value="lg((1+FEs)/(n²))" />`?
3. Under the hood, the font support has been improved. When creating LaTeX output,
   we use the same fonts as LaTeX uses. However, these may not have glyphs for some
   unicode characters -- maybe they (e.g., `cmr`) do not support `²`. In order
   to deal with this, we use composite fonts which then render `²` with a glyph from
   a platform-default font which has that gylph. Not beautiful, but for now it will
   do.
4. We now actually print some form of descriptive text into our reports. There still
   is quite some way to go to get good text, but we are moving forward.

These changes broke the compatibility of the settings for ECDF and Aggregate functions
in the `evaluation.xml` files. I hope this will not happen often anymore, but we
are still in an alpha phase of our system, so it could not be avoided. The new
features are quite nice, so I think they are worth it.
  
* [Stand-Alone Executable](optimizationBenchmarking-0.8.1-full.jar) [[md5](optimizationBenchmarking-0.8.1-full.jar.md5)] [[sha1](optimizationBenchmarking-0.8.1-full.jar.sha1)]
* [Executable without Dependencies](optimizationBenchmarking-0.8.1.jar) [[md5](optimizationBenchmarking-0.8.1.jar.md5)] [[sha1](optimizationBenchmarking-0.8.1.jar.sha1)]
* [Sources](optimizationBenchmarking-0.8.1-sources.jar) [[md5](optimizationBenchmarking-0.8.1-sources.jar.md5)] [[sha1](optimizationBenchmarking-0.8.1-sources.jar.sha1)]
* [JavaDoc](optimizationBenchmarking-0.8.1-javadoc.jar) [[md5](optimizationBenchmarking-0.8.1-javadoc.jar.md5)] [[sha1](optimizationBenchmarking-0.8.1-javadoc.jar.sha1)]
* [Maven `pom`](optimizationBenchmarking-0.8.1.pom) [[md5](optimizationBenchmarking-0.8.1.pom.md5)] [[sha1](optimizationBenchmarking-0.8.1.pom.sha1)]

{% include mavenPom.md projectVersion="0.8.1"%}