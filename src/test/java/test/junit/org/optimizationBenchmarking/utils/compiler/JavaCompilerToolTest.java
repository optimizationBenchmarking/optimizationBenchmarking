package test.junit.org.optimizationBenchmarking.utils.compiler;

import org.junit.Assert;
import org.junit.Test;
import org.optimizationBenchmarking.utils.compiler.JavaCompilerJob;
import org.optimizationBenchmarking.utils.compiler.JavaCompilerJobBuilder;
import org.optimizationBenchmarking.utils.compiler.JavaCompilerTool;

import test.junit.org.optimizationBenchmarking.utils.tools.ToolTest;

/**
 * Test the
 * {@link org.optimizationBenchmarking.utils.compiler.JavaCompilerTool}
 */
public class JavaCompilerToolTest extends ToolTest<JavaCompilerTool> {

  /** create */
  public JavaCompilerToolTest() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  protected JavaCompilerTool getInstance() {
    return JavaCompilerTool.getInstance();
  }

  /** test whether an empty class can be compiled */
  @Test(timeout = 3600000)
  public void testCompileEmptyClass() {
    final JavaCompilerTool tool;
    final JavaCompilerJobBuilder builder;
    final JavaCompilerJob job;
    final ClassLoader loader;
    Class<?> clazz;

    tool = this.getInstance();
    Assert.assertNotNull(tool);

    builder = tool.use();
    Assert.assertNotNull(builder);

    builder.addClass("EmptyClass", //$NON-NLS-1$
        "public final class EmptyClass {}"); //$NON-NLS-1$

    job = builder.create();
    Assert.assertNotNull(job);

    loader = job.call();
    Assert.assertNotNull(loader);

    try {
      clazz = loader.loadClass("EmptyClass");//$NON-NLS-1$
    } catch (final Throwable error) {
      throw new RuntimeException(error);
    }
    Assert.assertNotNull(clazz);
  }

  /** test whether multiple classes can be compiled */
  @Test(timeout = 3600000)
  public void testCompileMultipleClasses() {
    final JavaCompilerTool tool;
    final JavaCompilerJobBuilder builder;
    final JavaCompilerJob job;
    final ClassLoader loader;
    Class<?> clazz1, clazz2;

    tool = this.getInstance();
    Assert.assertNotNull(tool);

    builder = tool.use();
    Assert.assertNotNull(builder);

    builder.addClass("Class1", //$NON-NLS-1$
        "public final class Class1 {public int xx;}"); //$NON-NLS-1$
    builder.addClass("Class2", //$NON-NLS-1$
        "public final class Class2 {public void test() {} }"); //$NON-NLS-1$

    job = builder.create();
    Assert.assertNotNull(job);

    loader = job.call();
    Assert.assertNotNull(loader);

    try {
      clazz1 = loader.loadClass("Class1");//$NON-NLS-1$
    } catch (final Throwable error) {
      throw new RuntimeException(error);
    }
    Assert.assertNotNull(clazz1);

    try {
      Assert.assertNotNull(clazz1.getField("xx"));//$NON-NLS-1$
    } catch (final Throwable error) {
      throw new RuntimeException(error);
    }

    try {
      clazz2 = loader.loadClass("Class2");//$NON-NLS-1$
    } catch (final Throwable error) {
      throw new RuntimeException(error);
    }
    Assert.assertNotNull(clazz2);

    try {
      Assert.assertNotNull(clazz2.getMethod("test"));//$NON-NLS-1$
    } catch (final Throwable error) {
      throw new RuntimeException(error);
    }
  }
}
