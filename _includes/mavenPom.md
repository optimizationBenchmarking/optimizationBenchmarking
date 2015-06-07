
The *optimizationBenchmarking.org* framework can also be used as library inside your own software. If you build with [Maven](http://en.wikipedia.org/wiki/Apache_Maven), then you can use *optimizationBenchmarking.org* framework as external dependency by including the following information in your [Maven POM](http://en.wikipedia.org/wiki/Project_Object_Model#Project_Object_Model).


{% if include.projectVersion %}
{% highlight xml %}
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
{% highlight xml %}
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