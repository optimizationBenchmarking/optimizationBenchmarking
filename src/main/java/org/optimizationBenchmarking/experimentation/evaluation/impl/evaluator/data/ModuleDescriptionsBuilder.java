package org.optimizationBenchmarking.experimentation.evaluation.impl.evaluator.data;

import java.util.ArrayList;
import java.util.HashSet;

import org.optimizationBenchmarking.experimentation.evaluation.spec.IEvaluationModule;
import org.optimizationBenchmarking.utils.hierarchy.BuilderFSM;
import org.optimizationBenchmarking.utils.reflection.ReflectionUtils;
import org.optimizationBenchmarking.utils.text.TextUtils;

/** A builder for module lists. */
public final class ModuleDescriptionsBuilder extends
    BuilderFSM<ModuleDescriptions> {

  /** the classes */
  private HashSet<Class<? extends IEvaluationModule>> m_classes;
  /** the names */
  private HashSet<String> m_names;
  /** the list of descriptions */
  private ArrayList<ModuleDescription> m_descs;

  /** create the module list builder */
  public ModuleDescriptionsBuilder() {
    super(null);
    this.m_classes = new HashSet<>();
    this.m_names = new HashSet<>();
    this.m_descs = new ArrayList<>();
    this.open();
  }

  /**
   * Add a module description
   *
   * @param desc
   *          the description
   */
  public synchronized final void addModule(final ModuleDescription desc) {
    final Class<? extends IEvaluationModule> clazz;
    final String name;

    this.fsmStateAssert(BuilderFSM.STATE_OPEN);

    clazz = desc.getModuleClass();
    if (this.m_classes.contains(clazz)) {
      throw new IllegalArgumentException("Class " + clazz + //$NON-NLS-1$
          " already defined as module."); //$NON-NLS-1$
    }

    name = desc.getName();
    if (this.m_names.contains(TextUtils.toLowerCase(name))) {
      throw new IllegalArgumentException("Name '" + name + //$NON-NLS-1$
          "' already defined as module."); //$NON-NLS-1$
    }

    this.m_classes.add(clazz);
    this.m_names.add(name);
    this.m_descs.add(desc);
  }

  /**
   * Add a module description
   *
   * @param name
   *          the module name
   * @param clazz
   *          the module class
   * @param description
   *          the module description
   */
  public synchronized final void addModule(final String name,
      final Class<? extends IEvaluationModule> clazz,
      final String description) {
    this.addModule(new ModuleDescription(//
        this.normalize(name), clazz,//
        this.normalize(description)));
  }

  /**
   * Add a module description
   *
   * @param name
   *          the module name
   * @param clazz
   *          the module class
   * @param description
   *          the module description
   */
  @SuppressWarnings("unused")
  public final void addModule(final String name, final String clazz,
      final String description) {
    final Class<? extends IEvaluationModule> cclazz;

    try {
      cclazz = ReflectionUtils.findClass(clazz, IEvaluationModule.class);
    } catch (LinkageError | ClassNotFoundException error) {
      throw new IllegalArgumentException("Class '" + clazz //$NON-NLS-1$
          + "' cannot be resolved for becoming a module entry."); //$NON-NLS-1$
    }

    this.addModule(name, cclazz, description);
  }

  /** {@inheritDoc} */
  @Override
  protected final ModuleDescriptions compile() {
    final ArrayList<ModuleDescription> list;

    list = this.m_descs;
    this.m_descs = null;
    this.m_classes = null;
    this.m_names = null;

    return new ModuleDescriptions(list.toArray(//
        new ModuleDescription[list.size()]));
  }
}
