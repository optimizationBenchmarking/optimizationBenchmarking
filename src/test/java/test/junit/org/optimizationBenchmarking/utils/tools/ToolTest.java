package test.junit.org.optimizationBenchmarking.utils.tools;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.optimizationBenchmarking.utils.tools.spec.ITool;

import test.junit.InstanceTest;

/**
 * the basic tool test
 * 
 * @param <T>
 *          the tool type
 */
@Ignore
public abstract class ToolTest<T extends ITool> extends InstanceTest<T> {

  /** create */
  public ToolTest() {
    super(null, null, true, false);
  }

  /**
   * get the tool to test
   * 
   * @return the tool to test
   */
  @Override
  protected abstract T getInstance();

  /** test whether the tool can be used */
  @Test(timeout = 3600000)
  public void testToolCanUse() {
    Assert.assertTrue(this.getInstance().canUse());
  }

  /**
   * test whether the tool can be used: if not, this method should throw an
   * exception
   */
  @Test(timeout = 3600000)
  public void testToolCheckCanUse() {
    this.getInstance().checkCanUse();
  }

  /** test whether the tool returns a non-{@code null} tool job builder */
  @Test(timeout = 3600000)
  public void testToolCanCreateToolJobBuilder() {
    Assert.assertNotNull(this.getInstance().use());
  }

  /** {@inheritDoc} */
  @Override
  public void validateInstance() {
    super.validateInstance();

    this.testToolCanUse();
    this.testToolCheckCanUse();
    this.testToolCanCreateToolJobBuilder();
  }
}
