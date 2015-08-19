package test.junit.org.optimizationBenchmarking.experimentation.evaluation;

import org.junit.Assert;
import org.junit.Test;
import org.optimizationBenchmarking.experimentation.evaluation.impl.EvaluationModuleDescriptions;
import org.optimizationBenchmarking.experimentation.evaluation.impl.evaluator.data.ModuleDescription;
import org.optimizationBenchmarking.experimentation.evaluation.impl.evaluator.data.ModuleDescriptions;
import org.optimizationBenchmarking.utils.config.Definition;
import org.optimizationBenchmarking.utils.config.DefinitionElement;
import org.optimizationBenchmarking.utils.config.Parameter;

import test.junit.TestBase;

/**
 * Check whether we can load the evaluation module descriptions.
 */
public final class EvaluationModuleDescriptionsTest extends TestBase {
  /** create */
  public EvaluationModuleDescriptionsTest() {
    super();
  }

  /**
   * Get the descriptions
   * 
   * @return the descriptions
   */
  private static final ModuleDescriptions __get() {
    final ModuleDescriptions desc;

    desc = EvaluationModuleDescriptions.getDescriptions();
    Assert.assertNotNull(desc);
    Assert.assertFalse(desc.isEmpty());
    Assert.assertEquals(desc, desc);
    desc.hashCode();

    return desc;
  }

  /** load the descriptions */
  @Test(timeout = 3600000)
  public final void testLoadDescriptions() {
    __get();
  }

  /** load the descriptions */
  @Test(timeout = 3600000)
  public final void testSingleDescriptions() {
    final ModuleDescriptions desc;

    desc = __get();

    for (ModuleDescription mod : desc) {
      __asserDefinitionElement(mod);
      Assert.assertNotNull(mod.getModuleClass());
      __assertDefinition(mod.getParameters());
    }
  }

  /** load the joint definition */
  @Test(timeout = 3600000)
  public final void testJointDefinition() {
    __assertDefinition(__get().getJointParameters());
  }

  /**
   * Assert the definitions
   * 
   * @param def
   *          the definition
   */
  private static final void __assertDefinition(final Definition def) {
    Assert.assertNotNull(def);
    for (Parameter<?> param : def) {
      __asserDefinitionElement(param);
      Assert.assertNotNull(param.getParser());
    }
  }

  /**
   * Assert that a definition element is valid.
   * 
   * @param de
   *          the element
   */
  private static final void __asserDefinitionElement(
      final DefinitionElement de) {
    String str;

    Assert.assertNotNull(de);

    str = de.getName();
    Assert.assertNotNull(str);
    Assert.assertFalse(str.isEmpty());

    str = de.getDescription();
    Assert.assertNotNull(str);
    Assert.assertFalse(str.isEmpty());

    Assert.assertTrue(de.equals(de));
    de.hashCode();
  }
}
