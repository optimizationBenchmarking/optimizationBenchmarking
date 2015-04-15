package test.junit.org.optimizationBenchmarking.experimentation.evaluation;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.optimizationBenchmarking.utils.config.Configuration;
import org.optimizationBenchmarking.utils.config.ConfigurationBuilder;
import org.optimizationBenchmarking.utils.document.impl.abstr.DocumentConfiguration;
import org.optimizationBenchmarking.utils.document.impl.latex.LaTeXConfigurationBuilder;
import org.optimizationBenchmarking.utils.document.impl.xhtml10.XHTML10ConfigurationBuilder;
import org.optimizationBenchmarking.utils.io.paths.TempDir;

import test.junit.CategorySlowTests;
import test.junit.InstanceTest;
import test.junit.TestBase;
import examples.org.optimizationBenchmarking.experimentation.dataAndIO.BBOBExample;
import examples.org.optimizationBenchmarking.experimentation.dataAndIO.CSVEDIExample;
import examples.org.optimizationBenchmarking.experimentation.dataAndIO.Example1;
import examples.org.optimizationBenchmarking.experimentation.dataAndIO.Example2Random;
import examples.org.optimizationBenchmarking.experimentation.dataAndIO.ExperimentSetCreator;
import examples.org.optimizationBenchmarking.experimentation.dataAndIO.RandomExample;
import examples.org.optimizationBenchmarking.experimentation.dataAndIO.TSPSuiteExample;
import examples.org.optimizationBenchmarking.experimentation.evaluation.EvaluationExample;

/**
 * A test of an evaluation module.
 */
@Ignore
public class EvaluationConfigTest extends InstanceTest<EvaluationExample> {

  /**
   * Perform the module test
   * 
   * @param example
   *          the example
   */
  protected EvaluationConfigTest(final EvaluationExample example) {
    super(null, example, true, false);
  }

  /**
   * Process a given experiment source and destination
   * 
   * @param input
   *          the experiment set source
   * @param dest
   *          the destination
   */
  private final void __testApply(final ExperimentSetCreator input,
      final DocumentConfiguration dest) {
    final EvaluationExample example;
    final Configuration config;

    example = this.getInstance();
    Assert.assertNotNull(example);

    try (final ConfigurationBuilder builder = new ConfigurationBuilder()) {
      builder.setDebug();
      builder.put(Configuration.PARAM_LOGGER, TestBase.getNullLogger());
      config = builder.getResult();
    }

    try (final TempDir temp = new TempDir()) {

      example.process(input, dest, temp.getPath(), config, null);

    } catch (final Throwable error) {
      throw new AssertionError(error);
    }
  }

  /**
   * Apply the module to the BBOB Example under a default LaTeX output
   * config
   */
  @Test(timeout = 3600000)
  @Category(CategorySlowTests.class)
  public void testConfigOnBBOBExampleAndDefaultLaTeX() {
    this.__testApply(new BBOBExample(TestBase.getNullLogger()),
        new LaTeXConfigurationBuilder().immutable());
  }

  /**
   * Apply the module to the TSP Suite Example under a default XHTML 1.0
   * output config
   */
  @Test(timeout = 3600000)
  @Category(CategorySlowTests.class)
  public void testConfigOnTSPSuiteExampleAndDefaultXHTML10() {
    this.__testApply(new TSPSuiteExample(TestBase.getNullLogger()),
        new XHTML10ConfigurationBuilder().immutable());
  }

  /**
   * Apply the module to the TSP Suite Example under a default LaTeX output
   * config
   */
  @Test(timeout = 3600000)
  @Category(CategorySlowTests.class)
  public void testConfigOnTSPSuiteExampleAndDefaultLaTeX() {
    this.__testApply(new TSPSuiteExample(TestBase.getNullLogger()),
        new LaTeXConfigurationBuilder().immutable());
  }

  /**
   * Apply the module to the BBOB Example under a default XHTML 1.0 output
   * config
   */
  @Test(timeout = 3600000)
  @Category(CategorySlowTests.class)
  public void testConfigOnBBOBExampleAndDefaultXHTML10() {
    this.__testApply(new BBOBExample(TestBase.getNullLogger()),
        new XHTML10ConfigurationBuilder().immutable());
  }

  /**
   * Apply the module to the CSV-EDI Example under a default LaTeX output
   * config
   */
  @Test(timeout = 3600000)
  @Category(CategorySlowTests.class)
  public void testConfigOnCSVEDIExampleAndDefaultLaTeX() {
    this.__testApply(new CSVEDIExample(TestBase.getNullLogger()),
        new LaTeXConfigurationBuilder().immutable());
  }

  /**
   * Apply the module to the CSV-EDI Example under a default XHTML 1.0
   * output config
   */
  @Test(timeout = 3600000)
  @Category(CategorySlowTests.class)
  public void testConfigOnCSVEDIExampleAndDefaultXHTML10() {
    this.__testApply(new CSVEDIExample(TestBase.getNullLogger()),
        new XHTML10ConfigurationBuilder().immutable());
  }

  /**
   * Apply the module to the Example 1 under a default LaTeX output config
   */
  @Test(timeout = 3600000)
  public void testConfigOnExample1AndDefaultLaTeX() {
    this.__testApply(new Example1(TestBase.getNullLogger()),
        new LaTeXConfigurationBuilder().immutable());
  }

  /**
   * Apply the module to the Example 1 under a default XHTML 1.0 output
   * config
   */
  @Test(timeout = 3600000)
  public void testConfigOnExample1AndDefaultXHTML10() {
    this.__testApply(new Example1(TestBase.getNullLogger()),
        new XHTML10ConfigurationBuilder().immutable());
  }

  /**
   * Apply the module to the Example 2 Random under a default LaTeX output
   * config
   */
  @Test(timeout = 3600000)
  @Category(CategorySlowTests.class)
  public void testConfigOnExample2RandomAndDefaultLaTeX() {
    this.__testApply(new Example2Random(TestBase.getNullLogger()),
        new LaTeXConfigurationBuilder().immutable());
  }

  /**
   * Apply the module to the Example 2 Random under a default XHTML 1.0
   * output config
   */
  @Test(timeout = 3600000)
  @Category(CategorySlowTests.class)
  public void testConfigOnExample2RandomAndDefaultXHTML10() {
    this.__testApply(new Example2Random(TestBase.getNullLogger()),
        new XHTML10ConfigurationBuilder().immutable());
  }

  /**
   * Apply the module to the Random data under a default LaTeX output
   * config
   */
  @Test(timeout = 3600000)
  public void testConfigOnRandomExampleAndDefaultLaTeX() {
    this.__testApply(new RandomExample(TestBase.getNullLogger()),
        new LaTeXConfigurationBuilder().immutable());
  }

  /**
   * Apply the module to the Random data under a default XHTML 1.0 output
   * config
   */
  @Test(timeout = 3600000)
  public void testConfigOnRandomExampleAndDefaultXHTML10() {
    this.__testApply(new RandomExample(TestBase.getNullLogger()),
        new XHTML10ConfigurationBuilder().immutable());
  }

  /** {@inheritDoc} */
  @Override
  public void validateInstance() {
    super.validateInstance();
    this.testConfigOnBBOBExampleAndDefaultLaTeX();
    this.testConfigOnBBOBExampleAndDefaultXHTML10();
    this.testConfigOnTSPSuiteExampleAndDefaultLaTeX();
    this.testConfigOnTSPSuiteExampleAndDefaultXHTML10();
    this.testConfigOnCSVEDIExampleAndDefaultLaTeX();
    this.testConfigOnCSVEDIExampleAndDefaultXHTML10();
    this.testConfigOnExample1AndDefaultLaTeX();
    this.testConfigOnExample1AndDefaultXHTML10();
    this.testConfigOnExample2RandomAndDefaultLaTeX();
    this.testConfigOnExample2RandomAndDefaultXHTML10();
    this.testConfigOnRandomExampleAndDefaultLaTeX();
    this.testConfigOnRandomExampleAndDefaultXHTML10();
  }
}
