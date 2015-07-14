
The <em>optimizationBenchmarking.org</em> framework can also be used as library inside your own software. If you build with <a href="http://en.wikipedia.org/wiki/Apache_Maven">Maven</a>, then you can use version {% if include.projectVersion %}{{ include.projectVersion }}{% else %}{{ site.data.currentVersion.currentVersion }}{% endif %} of the <em>optimizationBenchmarking.org</em> framework as external dependency by including the following information in your <a href="http://en.wikipedia.org/wiki/Project_Object_Model#Project_Object_Model">Maven POM</a>.

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