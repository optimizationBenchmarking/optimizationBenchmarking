package org.optimizationBenchmarking.experimentation.evaluation.impl.evaluator.data;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.optimizationBenchmarking.experimentation.evaluation.impl.EvaluationModuleParser;
import org.optimizationBenchmarking.experimentation.evaluation.spec.IEvaluationModule;
import org.optimizationBenchmarking.utils.collections.lists.ArrayListView;
import org.optimizationBenchmarking.utils.config.Definition;
import org.optimizationBenchmarking.utils.config.DefinitionBuilder;
import org.optimizationBenchmarking.utils.config.Parameter;
import org.optimizationBenchmarking.utils.reflection.ReflectionUtils;
import org.optimizationBenchmarking.utils.text.TextUtils;

/** The module descriptions */
public final class ModuleDescriptions extends
    ArrayListView<ModuleDescription> {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** the joint parameter definition */
  private Definition m_jointDefinition;

  /**
   * create the module descriptions
   *
   * @param description
   *          the array of descriptions
   */
  ModuleDescriptions(final ModuleDescription[] description) {
    super(description);
    Arrays.sort(description, new __Cmp());
  }

  /**
   * Get the module description for the given name
   *
   * @param name
   *          the name
   * @return the description
   */
  public final ModuleDescription forName(final String name) {
    int low, high, mid, cmp;
    ModuleDescription midVal;

    if (name == null) {
      throw new IllegalArgumentException(//
          "Cannot find module definition for a null name."); //$NON-NLS-1$
    }

    low = 0;
    high = (this.m_data.length - 1);

    while (low <= high) {
      mid = ((low + high) >>> 1);
      midVal = this.m_data[mid];
      cmp = String.CASE_INSENSITIVE_ORDER.compare(midVal.getName(), name);

      if (cmp < 0) {
        low = (mid + 1);
      } else {
        if (cmp > 0) {
          high = (mid - 1);
        } else {
          return midVal;
        }
      }
    }

    throw new IllegalArgumentException(//
        "Cannot find module definition for name '" + //$NON-NLS-1$
            name + '\'' + '.');
  }

  /**
   * Get the module description for the given module
   *
   * @param module
   *          the module
   * @return the description
   */
  public final ModuleDescription forModule(final IEvaluationModule module) {
    if (module == null) {
      throw new IllegalArgumentException(//
          "Cannot find module definition for a null module."); //$NON-NLS-1$
    }
    return this.forClass(module.getClass());
  }

  /**
   * Get the module description for the given class
   *
   * @param clazz
   *          the class
   * @return the description
   */
  public final ModuleDescription forClass(
      final Class<? extends IEvaluationModule> clazz) {
    if (clazz == null) {
      throw new IllegalArgumentException(//
          "Cannot find module definition for a null class."); //$NON-NLS-1$
    }
    for (final ModuleDescription md : this.m_data) {
      if (clazz.equals(md.getModuleClass())) {
        return md;
      }
    }
    throw new IllegalArgumentException(//
        "Cannot find module definition for class ' " + //$NON-NLS-1$
            clazz + '\'' + '.');
  }

  /**
   * Get the module description for the given class
   *
   * @param clazz
   *          the class
   * @return the description
   */
  public final ModuleDescription forClass(final String clazz) {
    Class<? extends IEvaluationModule> cclazz;
    try {
      cclazz = ReflectionUtils.findClass(clazz, IEvaluationModule.class);
    } catch (final Throwable error) {
      cclazz = EvaluationModuleParser.getInstance().parseString(clazz)
          .getClass();
    }
    return this.forClass(cclazz);
  }

  /**
   * Get the joint parameters.
   *
   * @return the joint set of parameters
   */
  public synchronized Definition getJointParameters() {
    LinkedHashMap<String, Parameter<?>> parameters;
    HashMap<String, String> merged;
    Definition def;
    boolean allowsMore;
    Parameter<?> other;
    String namelk, name;

    if (this.m_jointDefinition == null) {

      allowsMore = false;
      parameters = new LinkedHashMap<>();
      merged = null;
      // collect all parameters, store name collisions as null
      for (final ModuleDescription md : this.m_data) {
        def = md.getParameters();
        allowsMore |= def.allowsMore();
        for (final Parameter<?> param : def) {
          name = param.getName();
          namelk = TextUtils.toLowerCase(name);

          if (parameters.containsKey(namelk)) {
            other = parameters.get(namelk);
            if ((other == null) || (other.equals(param))) {
              continue;
            }
            parameters.put(namelk, null);
            if (merged == null) {
              merged = new HashMap<>();
            }
            merged.put(namelk, name);
            continue;
          }
          parameters.put(namelk, param);
        }
      }

      // now build the definition
      try (final DefinitionBuilder builder = new DefinitionBuilder()) {
        for (final Map.Entry<String, Parameter<?>> entry : parameters
            .entrySet()) {
          other = entry.getValue();
          if (other != null) {
            builder.add(other);
            continue;
          }
          namelk = entry.getKey();
          if (namelk == null) {
            continue;
          }
          if (merged != null) {
            name = merged.get(namelk);
            if (name == null) {
              name = namelk;
            }
          } else {
            name = namelk;
          }

          builder
              .stringParameter(
                  name,//
                  "This parameter has different meanings for different modules.", //$NON-NLS-1$
                  null);
        }
        builder.setAllowsMore(allowsMore);
        this.m_jointDefinition = builder.getResult();
      }

    }

    return this.m_jointDefinition;
  }

  /** the internal comparator class to sort the module list */
  private static final class __Cmp implements
      Comparator<ModuleDescription> {
    /** create */
    __Cmp() {
      super();
    }

    /** {@inheritDoc} */
    @Override
    public final int compare(final ModuleDescription o1,
        final ModuleDescription o2) {
      return String.CASE_INSENSITIVE_ORDER.compare(o1.getName(),
          o2.getName());
    }
  }
}
