{% if include.projectVersion %}
```Maven POM
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
```
{% else %}
```Maven POM
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
```
{% endif %}