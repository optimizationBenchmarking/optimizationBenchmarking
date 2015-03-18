package test.junit.org.optimizationBenchmarking.experimentation.dataAndIO;

import org.junit.experimental.categories.Category;

import test.junit.CategorySlowTests;
import examples.org.optimizationBenchmarking.experimentation.dataAndIO.RandomBBOBParallelExample;

/** A class for creating in parallel sets */
@Category(CategorySlowTests.class)
public class RandomBBOBParallelTest extends ExperimentSetTest {

  /** create */
  public RandomBBOBParallelTest() {
    super(new RandomBBOBParallelExample());
  }
}