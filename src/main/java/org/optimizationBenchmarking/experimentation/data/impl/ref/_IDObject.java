package org.optimizationBenchmarking.experimentation.data.impl.ref;

import java.io.Serializable;
import java.util.Comparator;
import java.util.List;

import org.optimizationBenchmarking.experimentation.data.spec.DataElement;
import org.optimizationBenchmarking.utils.document.impl.SemanticComponentUtils;
import org.optimizationBenchmarking.utils.document.spec.IMath;
import org.optimizationBenchmarking.utils.math.text.IParameterRenderer;
import org.optimizationBenchmarking.utils.text.ETextCase;
import org.optimizationBenchmarking.utils.text.ITextable;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;
import org.optimizationBenchmarking.utils.text.textOutput.MemoryTextOutput;

/** An internal class for named id objects. */
abstract class _IDObject extends DataElement implements
    Comparable<_IDObject>, ITextable, Serializable {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** the id */
  int m_id;
  /** the owning named id object set */
  DataElement m_owner;

  /** Create the instance of the ID object */
  _IDObject() {
    super();
    this.m_id = (-1);
  }

  /**
   * the internal comparison
   *
   * @param o
   *          the object
   * @return the result
   */
  int _compareTo(final _IDObject o) {
    return 0;
  }

  /** {@inheritDoc} */
  @SuppressWarnings({ "rawtypes", "unchecked" })
  @Override
  public final int compareTo(final _IDObject o) {
    final Class<?> c1, c2;
    final Object s1, s2;
    String nameThis, nameO, descThis, descO;
    int i, a, b;

    if (o == this) {
      return 0;
    }
    if (o == null) {
      return (-1);
    }

    if (o.m_id < this.m_id) {
      return 1;
    }
    if (o.m_id > this.m_id) {
      return (-1);
    }

    i = this._compareTo(o);
    if (i != 0) {
      return i;
    }

    if (((this instanceof _NamedIDObject) || //
        (this instanceof _NamedIDObjectSet) || //
        (this instanceof Experiment))//
        && //
        ((o instanceof _NamedIDObject) || //
            (o instanceof _NamedIDObjectSet) || //
        (o instanceof Experiment))) {

      nameThis = this.getName();
      nameO = o.getName();

      if (nameThis != nameO) {
        if (nameThis == null) {
          if (nameO != null) {
            return 1;
          }
        } else {
          if (nameO == null) {
            return (-1);
          }
          i = nameThis.compareTo(nameO);
          if (i != 0) {
            return i;
          }
        }
      }

      descThis = this.getDescription();
      descO = o.getDescription();

      if (descThis != descO) {
        if (descThis == null) {
          if (descO != null) {
            return 1;
          }
        } else {
          if (descO == null) {
            return (-1);
          }
          i = descThis.compareTo(descO);
          if (i != 0) {
            return i;
          }
        }
      }
    }

    s1 = this.m_owner;
    s2 = o.m_owner;

    if (s1 == null) {
      return (s2 != null) ? (1) : (0);
    }
    if (s2 == null) {
      return (-1);
    }

    if (s1 != s2) {
      i = 0;
      if (s1 instanceof Comparable) {
        try {
          i = ((Comparable) s1).compareTo(s2);
          if (i != 0) {
            return i;
          }
        } catch (final Throwable t) {// IGNORE
        }
      }

      if (s2 instanceof Comparable) {
        try {
          i = ((Comparable) s2).compareTo(s1);
          if (i != 0) {
            return (-i);
          }
        } catch (final Throwable t) {// IGNORE
        }
      }
    }

    if (s1 instanceof Comparator) {
      try {
        i = ((Comparator) s1).compare(this, o);
        if (i != 0) {
          return (-i);
        }
      } catch (final Throwable t) {// IGNORE
      }
    }

    if (s2 instanceof Comparator) {
      try {
        i = ((Comparator) s2).compare(this, o);
        if (i != 0) {
          return (-i);
        }
      } catch (final Throwable t) {// IGNORE
      }
    }

    a = this.hashCode();
    b = o.hashCode();

    if (a != b) {
      return ((a < b) ? (-1) : 1);
    }

    a = (c1 = this.getClass()).hashCode();
    b = (c2 = o.getClass()).hashCode();
    if (a != b) {
      return ((a < b) ? (-1) : 1);
    }

    a = System.identityHashCode(this);
    b = System.identityHashCode(o);
    if (a != b) {
      return ((a < b) ? (-1) : 1);
    }

    a = System.identityHashCode(c1);
    b = System.identityHashCode(c2);
    if (a != b) {
      return ((a < b) ? (-1) : 1);
    }

    throw new IllegalArgumentException(//
        "The objects '" + this + "' and '" + o + //$NON-NLS-1$ //$NON-NLS-2$
            " are different instances, but they have no feature that can be used to distinguish them."//$NON-NLS-1$
    );
  }

  /**
   * read resolve
   *
   * @return the read resolve
   */
  @SuppressWarnings("rawtypes")
  protected Object readResolve() {
    final Class<?> clazz;
    Object k;

    if ((this.m_id >= 0) && (this.m_owner != null)) {
      clazz = this.getClass();

      if (this.m_owner instanceof ElementSet) {
        try {
          k = ((ElementSet) (this.m_owner)).m_data.get(this.m_id);
          if (clazz.isInstance(k)) {
            return k;
          }
        } catch (final Throwable tt) {
          // ignore
        }
      }

      if (this.m_owner instanceof List) {
        try {
          k = ((List) (this.m_owner)).get(this.m_id);
          if (clazz.isInstance(k)) {
            return k;
          }
        } catch (final Throwable tt) {
          // ignore
        }
      }
    }
    return this;
  }

  /**
   * write replace
   *
   * @return the replacement
   */
  protected final Object writeReplace() {
    return this.readResolve();
  }

  /** {@inheritDoc} */
  @Override
  public int hashCode() {
    return this.m_id;
  }

  /** {@inheritDoc} */
  @Override
  public final boolean equals(final Object o) {
    return (o == this);
  }

  /**
   * Make the full path of this object
   *
   * @param textOut
   *          the text output to append to
   */
  private final void __makePath(final ITextOutput textOut) {
    if (this.m_owner instanceof _IDObject) {
      ((_IDObject) this.m_owner).__makePath(textOut);
      textOut.append('/');
    }
    textOut.append(this.getName());
  }

  /** {@inheritDoc} */
  @Override
  public void toText(final ITextOutput textOut) {
    this.__makePath(textOut);
  }

  /**
   * Obtain the name of this object.
   *
   * @return the name of this object.
   */
  String getName() {
    return ((this.getClass().getSimpleName() + '_') + this.m_id);
  }

  /**
   * Obtain the description of this object.
   *
   * @return the description of this object, or {@code null} if none is
   *         specified
   */
  String getDescription() {
    return null;
  }

  /** {@inheritDoc} */
  @Override
  public String toString() {
    final MemoryTextOutput sb;

    sb = new MemoryTextOutput();
    this.toText(sb);
    return sb.toString();
  }

  /**
   * Render this object as mathematical formula to a text output
   *
   * @param out
   *          the text output
   * @param renderer
   *          the parameter renderer
   */
  void mathRender(final ITextOutput out, final IParameterRenderer renderer) {
    SemanticComponentUtils.mathRender(this.getName(), out, renderer);
  }

  /**
   * Render this object as mathematical formula to a math context
   *
   * @param out
   *          the math context
   * @param renderer
   *          the parameter renderer
   */
  void mathRender(final IMath out, final IParameterRenderer renderer) {
    SemanticComponentUtils.mathRender(this.getName(), out, renderer);
  }

  /**
   * Print the short name
   *
   * @param textOut
   *          the text output device
   * @param textCase
   *          the text case
   * @return the next text case
   */
  ETextCase printShortName(final ITextOutput textOut,
      final ETextCase textCase) {
    return SemanticComponentUtils.printShortName(this.getName(), textOut,
        textCase, true);
  }

  /**
   * Print the long name
   *
   * @param textOut
   *          the text output device
   * @param textCase
   *          the text case
   * @return the next text case
   */
  ETextCase printLongName(final ITextOutput textOut,
      final ETextCase textCase) {
    return this.printShortName(textOut, textCase);
  }

  /**
   * Print the description
   *
   * @param textOut
   *          the text output device
   * @param textCase
   *          the text case
   * @return the next text case
   */
  ETextCase printDescription(final ITextOutput textOut,
      final ETextCase textCase) {
    return SemanticComponentUtils.printDescription(this.getDescription(),
        textOut, textCase);
  }

  /**
   * the internal blueprint method for getting a path component
   *
   * @return the path component
   */
  String getPathComponentSuggestion() {
    return this.getName();
  }
}
