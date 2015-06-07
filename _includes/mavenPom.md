{% if include.projectVersion %}
{% highlight Maven POM %}
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
    <version>{{ include.projectVersion }}</version>
  </dependency>
</dependencies>
{% endhighlight %}
{% else %}
{% highlight Maven POM %}
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
    <version>{{ site.data.currentVersion.currentVersion }}</version>
  </dependency>
</dependencies>
{% endhighlight %}
{% endif %}