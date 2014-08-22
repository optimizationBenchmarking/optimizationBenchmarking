/**
 * A base class for XML output and its implementations for the different
 * methods for serializing XML data to a {@link java.io.Writer} that we
 * want to benchmark, including our
 * {@link org.optimizationBenchmarking.utils.io.xml hierarchical XML API}
 * in
 * {@link examples.org.optimizationBenchmarking.utils.io.xml.performance.serializers.XMLAPISerialSerialization
 * serial},
 * {@link examples.org.optimizationBenchmarking.utils.io.xml.performance.serializers.XMLAPIParallelSerialization
 * semi-parallel}, and
 * {@link examples.org.optimizationBenchmarking.utils.io.xml.performance.serializers.XMLAPIParallelSerialization
 * parallel} form, as well as
 * {@link examples.org.optimizationBenchmarking.utils.io.xml.performance.serializers.XMLDOMSerialization
 * DOM} and
 * {@link examples.org.optimizationBenchmarking.utils.io.xml.performance.serializers.XMLStreamWriterSerialization
 * StreamWriters}.
 */
package examples.org.optimizationBenchmarking.utils.io.xml.performance.serializers;