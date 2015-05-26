/**
 * A base class for XMLFileType output and its implementations for the
 * different methods for serializing XMLFileType data to a
 * {@link java.io.Writer} that we want to benchmark, including our
 * {@link org.optimizationBenchmarking.utils.io.xml hierarchical
 * XMLFileType API} in
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