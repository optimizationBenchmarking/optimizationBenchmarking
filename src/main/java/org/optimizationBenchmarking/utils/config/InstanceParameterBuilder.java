package org.optimizationBenchmarking.utils.config;

import java.util.LinkedHashMap;

import org.optimizationBenchmarking.utils.collections.lists.ArrayListView;
import org.optimizationBenchmarking.utils.hierarchy.NormalizingFSM;

/**
 * A builder for a definition.
 */
public final class InstanceParameterBuilder extends NormalizingFSM {

  /** the base parameter */
  private final Parameter<?> m_base;

  /** the parameters */
  private LinkedHashMap<String, DefinitionElement> m_choices;

  /** are more elements allowed than the choices suggest? */
  private final boolean m_allowsMore;

  /**
   * create the definition builder
   *
   * @param owner
   *          the owning definition builder
   * @param base
   *          the base parameter
   * @param allowsMore
   *          are more elements allowed than the choices suggest?
   */
  InstanceParameterBuilder(final DefinitionBuilder owner,
      final Parameter<?> base, final boolean allowsMore) {
    super(owner);

    if (owner == null) {
      throw new IllegalArgumentException(
          "Owning definition builder cannot be null.");//$NON-NLS-1$
    }

    if (base == null) {
      throw new IllegalArgumentException("Parameter base cannot be null."); //$NON-NLS-1$
    }
    this.m_base = base;
    this.m_choices = new LinkedHashMap<>();
    this.m_allowsMore = allowsMore;
    this.open();
  }

  /**
   * Add a choice
   *
   * @param name
   *          the name
   * @param description
   *          the description
   */
  public synchronized final void addChoice(final String name,
      final String description) {
    final DefinitionElement de;

    de = new DefinitionElement(name, description);
    if (this.m_choices.containsKey(de.m_name)) {
      throw new IllegalArgumentException("Choice with name '" + name //$NON-NLS-1$
          + "' already defined."); //$NON-NLS-1$
    }
    this.m_choices.put(de.m_name, this.normalize(de));
  }

  /**
   * take the result
   *
   * @return the result
   */
  @SuppressWarnings({ "rawtypes", "unchecked" })
  synchronized final InstanceParameter<?> _take() {
    final ArrayListView<DefinitionElement> alv;
    LinkedHashMap<String, DefinitionElement> al;

    al = this.m_choices;
    this.m_choices = null;

    alv = this.normalize(ArrayListView.collectionToView(al.values()));
    al = null;

    return new InstanceParameter(this.m_base.m_name,
        this.m_base.m_description, this.m_base.m_parser,
        ((String) (this.m_base.m_default)), alv, this.m_allowsMore);
  }
}
