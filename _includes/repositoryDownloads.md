## Downloads
{% if include.hasGUI == "true" %}
* [Stand-Alone GUI](https://github.com/optimizationBenchmarking/optimizationBenchmarkingGui/releases/download/v{{ include.projectVersion }}/optimizationBenchmarkingGui-{{ include.projectVersion }}-full.jar) &mdash; This includes all necessary dependencies, such as core sources and required libraries. You need nothing else to evaluate your experiments and conveniently edit all meta-data for your experiments and evaluation processes in a comfortable user interface.
{% endif %}
* [Stand-Alone Command-Line Tool]({{ site.baseurl }}/repo/optimizationBenchmarking/org/optimizationBenchmarking/{{ include.projectVersion }}/optimizationBenchmarking-{{ include.projectVersion }}-full.jar) [[md5]({{ site.baseurl }}/repo/optimizationBenchmarking/org/optimizationBenchmarking/{{ include.projectVersion }}/optimizationBenchmarking-{{ include.projectVersion }}-full.jar.md5)] [[sha1]({{ site.baseurl }}/repo/optimizationBenchmarking/org/optimizationBenchmarking/{{ include.projectVersion }}/optimizationBenchmarking-{{ include.projectVersion }}-full.jar.sha1)] &mdash; This includes all necessary dependencies. You can evaluate your experiments from the command line using this tool.
* [Command-Line Tool without Dependencies]({{ site.baseurl }}/repo/optimizationBenchmarking/org/optimizationBenchmarking/{{ include.projectVersion }}/optimizationBenchmarking-{{ include.projectVersion }}.jar) [[md5]({{ site.baseurl }}/repo/optimizationBenchmarking/org/optimizationBenchmarking/{{ include.projectVersion }}/optimizationBenchmarking-{{ include.projectVersion }}.jar.md5)] [[sha1]({{ site.baseurl }}/repo/optimizationBenchmarking/org/optimizationBenchmarking/{{ include.projectVersion }}/optimizationBenchmarking-{{ include.projectVersion }}.jar.sha1)] &mdash; This is just the raw code of the core command-line tool. You will need the required third-party libraries to be in the classpath to run this.
{% if include.hasGUI == "true" %}
* [GUI without Dependencies ](https://github.com/optimizationBenchmarking/optimizationBenchmarkingGui/releases/download/v{{ include.projectVersion }}/optimizationBenchmarkingGui-{{ include.projectVersion }}.jar) &mdash; This is just the raw code of the GUI. You will need the required third-party libraries as well as the core jar (command-line tool) in the classpath to run this.
{% endif %}
* [Sources (of Command-Line Tool)]({{ site.baseurl }}/repo/optimizationBenchmarking/org/optimizationBenchmarking/{{ include.projectVersion }}/optimizationBenchmarking-{{ include.projectVersion }}-sources.jar) [[md5]({{ site.baseurl }}/repo/optimizationBenchmarking/org/optimizationBenchmarking/{{ include.projectVersion }}/optimizationBenchmarking-{{ include.projectVersion }}-sources.jar.md5)] [[sha1]({{ site.baseurl }}/repo/optimizationBenchmarking/org/optimizationBenchmarking/{{ include.projectVersion }}/optimizationBenchmarking-{{ include.projectVersion }}-sources.jar.sha1)] &mdash; This `jar` includes all sources of the core command-line tool.
{% if include.hasGUI == "true" %}
* [Sources of GUI](https://github.com/optimizationBenchmarking/optimizationBenchmarkingGui/releases/download/v{{ include.projectVersion }}/optimizationBenchmarkingGui-{{ include.projectVersion }}-sources.jar) &mdash; This `jar` includes all sources of the GUI, but not the core command-line tool.
{% endif %}
* [JavaDoc (of Command-Line Tool)]({{ site.baseurl }}/repo/optimizationBenchmarking/org/optimizationBenchmarking/{{ include.projectVersion }}/optimizationBenchmarking-{{ include.projectVersion }}-javadoc.jar) [[md5]({{ site.baseurl }}/repo/optimizationBenchmarking/org/optimizationBenchmarking/{{ include.projectVersion }}/optimizationBenchmarking-{{ include.projectVersion }}-javadoc.jar.md5)] [[sha1]({{ site.baseurl }}/repo/optimizationBenchmarking/org/optimizationBenchmarking/{{ include.projectVersion }}/optimizationBenchmarking-{{ include.projectVersion }}-javadoc.jar.sha1)] &mdash; This `jar` includes the comprehensive javdoc documentation of the core command-line tool.
{% if include.hasGUI == "true" %}
* [JavaDoc of GUI](https://github.com/optimizationBenchmarking/optimizationBenchmarkingGui/releases/download/v{{ include.projectVersion }}/optimizationBenchmarkingGui-{{ include.projectVersion }}-javadoc.jar) &mdash; This `jar` includes the comprehensive javdoc documentation of the GUI, but not the core command-line tool.
{% endif %}
* [Maven `pom`]({{ site.baseurl }}/repo/optimizationBenchmarking/org/optimizationBenchmarking/{{ include.projectVersion }}/optimizationBenchmarking-{{ include.projectVersion }}.pom) [[md5]({{ site.baseurl }}/repo/optimizationBenchmarking/org/optimizationBenchmarking/{{ include.projectVersion }}/optimizationBenchmarking-{{ include.projectVersion }}.pom.md5)] [[sha1]({{ site.baseurl }}/repo/optimizationBenchmarking/org/optimizationBenchmarking/{{ include.projectVersion }}/optimizationBenchmarking-{{ include.projectVersion }}.pom.sha1)] &mdash; This file is used by Maven to automatically find all dependencies when you use our core code as part of your software.

Besides the downloads from our Maven [repository]({{ site.baseurl }}/repo/optimizationBenchmarking/org/optimizationBenchmarking/{{ include.projectVersion }}/index.html), you can also download this version of the command-line tool from [GitHub](https://github.com/optimizationBenchmarking/optimizationBenchmarking/releases/tag/v{{ include.projectVersion }}) (where only the sources are provided).
## Maven POM
{% include mavenPom.md projectVersion=include.projectVersion %}