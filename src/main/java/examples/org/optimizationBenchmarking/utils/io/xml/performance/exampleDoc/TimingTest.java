package examples.org.optimizationBenchmarking.utils.io.xml.performance.exampleDoc;

import examples.org.optimizationBenchmarking.utils.io.xml.performance.serializers.SerializationMethod;
import examples.org.optimizationBenchmarking.utils.io.xml.performance.serializers.XMLAPISerialSerialization;
import examples.org.optimizationBenchmarking.utils.io.xml.performance.serializers.XMLStreamWriterSerialization;

/** A timing tester for the different APIs */
public class TimingTest {

  /** the available serialization methods */
  public static final SerializationMethod[] METHODS = {
    new XMLStreamWriterSerialization(),//
    new XMLAPISerialSerialization(),//
  };

  /** the numbers of nodes */
  public static final int[] NODE_NUMBERS = { 100, 1000, 10000, 100000,
    1000000, 10000000 };

  /** the number of runs per experiment */
  public static final int RUNS_PER_EXPERIMENT = 1000;

  /** the number of dry runs per experiment */
  public static final int DRY_RUNS_PER_EXPERIMENT = 10;

}
