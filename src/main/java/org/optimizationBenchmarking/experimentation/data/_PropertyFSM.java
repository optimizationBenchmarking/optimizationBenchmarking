package org.optimizationBenchmarking.experimentation.data;

import java.util.HashMap;

import org.optimizationBenchmarking.utils.hierarchy.FSM;
import org.optimizationBenchmarking.utils.hierarchy.HierarchicalFSM;
import org.optimizationBenchmarking.utils.text.TextUtils;
import org.optimizationBenchmarking.utils.text.textOutput.MemoryTextOutput;

/**
 * The base class for everything that creates properties or property
 * values. This class manages data in an internal {@link #m_map} which is
 * then refined in a two-step process of {@link #_compile() compiling} and
 * {@link #_finalize() finalization}. (Of course, only one step is also
 * OK...)
 * 
 * @param <VT>
 *          the value type
 * @param <CT>
 *          the result of the compilation
 * @param <RT>
 *          the final result type
 * @param <OT>
 *          the owner type
 */
abstract class _PropertyFSM<VT extends _PropertyFSMRecord, CT, RT, OT>
    extends FSM {

  /** the element is open and can be modified */
  private static final int STATE_OPEN = (FSM.STATE_NOTHING + 1);
  /** the manager has been compiled */
  private static final int STATE_COMPILING = (_PropertyFSM.STATE_OPEN + 1);
  /** the manager is currently compiling */
  private static final int STATE_COMPILED = (_PropertyFSM.STATE_COMPILING + 1);
  /** the manager is currently finalizing the data */
  private static final int STATE_FINALIZING = (_PropertyFSM.STATE_COMPILED + 1);
  /** the finalization is finished */
  private static final int STATE_FINALIZED = (_PropertyFSM.STATE_FINALIZING + 1);

  /** the state names */
  private static final String[] STATE_NAMES = { null, "open",//$NON-NLS-1$
      "compiling",//$NON-NLS-1$
      "compiled",//$NON-NLS-1$
      "finalizing",//$NON-NLS-1$
      "finalized"//$NON-NLS-1$
  };

  /** the map */
  private volatile HashMap<String, VT> m_map;

  /** the compiled result */
  private volatile CT m_compiled;

  /** the finalized result */
  private volatile RT m_finalized;

  /** the owning context */
  final OT m_owner;

  /** the number of opened contexts */
  private volatile int m_opened;

  /** the number of closed contexts */
  private volatile int m_closed;

  /**
   * create
   * 
   * @param owner
   *          the owner
   */
  _PropertyFSM(final OT owner) {
    super();
    if (owner == null) {
      throw new IllegalArgumentException("Owner must not be null."); //$NON-NLS-1$
    }
    this.m_owner = owner;
    this.m_map = new HashMap<>();
  }

  /** call when opening the fsm */
  final void _open() {
    this.fsmStateAssertAndSet(FSM.STATE_NOTHING, _PropertyFSM.STATE_OPEN);
  }

  /** begin a new context */
  synchronized final void _begin() {
    this.fsmStateAssert(_PropertyFSM.STATE_OPEN);
    this.__assertOpenCloseWhenOpen();
    this.m_opened++;
  }

  /** end a context */
  synchronized final void _end() {
    this.fsmStateAssert(_PropertyFSM.STATE_OPEN);
    this.m_closed++;
    this.__assertOpenCloseWhenOpen();
  }

  /**
   * assert that all opened contexts have been closed
   * 
   * @return the number of contexts
   */
  private final int __assertOpenCloseWhenClosed() {
    final int c, o;
    if ((c = this.m_closed) == (o = this.m_opened)) {
      return c;
    }
    throw new IllegalStateException("The number of closed contexts (" + c + //$NON-NLS-1$
        ") is different from the number of opened contexts (" + o + //$NON-NLS-1$
        ") in " + this + //$NON-NLS-1$
        '.');
  }

  /** assert that the right number of contexts is open */
  private final void __assertOpenCloseWhenOpen() {
    final int c, o;
    if ((c = this.m_closed) > (o = this.m_opened)) {
      throw new IllegalStateException(
          "More contexts closed than opened: There are " + o + //$NON-NLS-1$
              " opened contexts in " + this + //$NON-NLS-1$
              " and " + c + //$NON-NLS-1$
              " closed contexts."); //$NON-NLS-1$
    }
  }

  /** {@inheritDoc} */
  @Override
  protected void fsmStateAppendName(final int state,
      final MemoryTextOutput append) {
    if ((state > 0) && (state < _PropertyFSM.STATE_NAMES.length)) {
      append.append(_PropertyFSM.STATE_NAMES[state]);
    } else {
      super.fsmStateAppendName(state, append);
    }
  }

  /**
   * compile the contents of this map
   * 
   * @param map
   *          the map to compile
   * @param count
   *          the number of elements
   * @return the compiled result
   */
  @SuppressWarnings("unchecked")
  CT _doCompile(final HashMap<String, VT> map, final int count) {
    return ((CT) (map));
  }

  /**
   * compile this property builder
   * 
   * @return the result of the compilation process
   */
  synchronized final CT _compile() {
    final HashMap<String, VT> map;
    final CT r;

    this.fsmStateAssertAndSet(_PropertyFSM.STATE_OPEN,
        _PropertyFSM.STATE_COMPILING);
    map = this.m_map;
    this.m_map = null;

    this.m_compiled = null;
    this.m_compiled = r = this._doCompile(map,
        this.__assertOpenCloseWhenClosed());
    this.fsmStateAssertAndSet(_PropertyFSM.STATE_COMPILING,
        _PropertyFSM.STATE_COMPILED);
    return r;
  }

  /**
   * create the finalized result
   * 
   * @param compiled
   *          the result of the compilation process
   * @param count
   *          the number of elements
   * @return the finalized result
   */
  @SuppressWarnings("unchecked")
  RT _doFinalize(final CT compiled, final int count) {
    return ((RT) (compiled));
  }

  /**
   * finalize the data structures and return the result
   * 
   * @return the finalized data structures
   */
  synchronized final RT _finalize() {
    final CT c;
    final RT r;

    this.fsmStateAssertAndSet(_PropertyFSM.STATE_COMPILED,
        _PropertyFSM.STATE_FINALIZING);
    this.m_finalized = null;
    c = this.m_compiled;
    this.m_compiled = null;
    if (c == null) {
      throw new IllegalStateException(//
          "Compilation result cannot be null during finalization."); //$NON-NLS-1$
    }
    this.m_finalized = r = this._doFinalize(c,
        this.__assertOpenCloseWhenClosed());
    if (r == null) {
      throw new IllegalStateException(//
          "Result of finalization must not be null."); //$NON-NLS-1$
    }
    this.fsmStateAssertAndSet(_PropertyFSM.STATE_FINALIZING,
        _PropertyFSM.STATE_FINALIZED);
    return r;
  }

  /**
   * Get the result of the compilation process.
   * 
   * @return the result of the compilation process
   */
  synchronized final CT _getCompilationResult() {
    final CT r;

    this.fsmStateAssert(_PropertyFSM.STATE_COMPILED);
    r = this.m_compiled;
    if (r == null) {
      throw new IllegalStateException(//
          "Result of compilation must not be null."); //$NON-NLS-1$
    }

    return r;
  }

  /**
   * Get the result of the finalization process.
   * 
   * @return the result of the finalization process
   */
  synchronized final RT _getFinalizationResult() {
    this.fsmStateAssert(_PropertyFSM.STATE_FINALIZED);
    return this.m_finalized;
  }

  /**
   * Check a property name
   * 
   * @param propertyName
   *          the property name
   * @return the formatted name
   */
  private static final String __checkPropertyName(final String propertyName) {
    final String n;
    n = TextUtils.normalize(propertyName);
    if (n != null) {
      return n;
    }
    throw new IllegalArgumentException((((//
        "A property name cannot be null or empty, but '" //$NON-NLS-1$
        + n) + "' is '" //$NON-NLS-1$
        ) + propertyName)
        + "' was specified as property name."); //$NON-NLS-1$

  }

  /**
   * create a new property fsm record
   * 
   * @param propertyName
   *          the name of the property
   * @param propertyDesc
   *          the description of the property
   * @param propertyValue
   *          the property value
   * @param propertyValueDesc
   *          the property value description
   * @return the new record
   */
  abstract VT _createFSMRecord(final String propertyName,
      final String propertyDesc, final Object propertyValue,
      final String propertyValueDesc);

  /**
   * Normalize local
   * 
   * @param input
   *          the input
   * @return the result
   * @param <T>
   *          the type
   */
  @SuppressWarnings({ "unchecked", "rawtypes" })
  final <T> T _normalizeLocal(final T input) {
    if (this.m_owner instanceof _FSM) {
      return ((_FSM) (this.m_owner))._normalizeLocal(input);
    }
    return ((T) (((_PropertyFSM) (this.m_owner))._normalizeLocal(input)));
  }

  /**
   * set the value of a property
   * 
   * @param propertyName
   *          the name of the property
   * @param propertyDesc
   *          the description of the property
   */
  synchronized final void _declareProperty(final String propertyName,
      final String propertyDesc) {
    final String n, d;
    final VT r;
    final HashMap<String, VT> map;

    this.fsmStateAssert(_PropertyFSM.STATE_OPEN);
    this.__assertOpenCloseWhenOpen();

    n = _PropertyFSM.__checkPropertyName(propertyName);
    d = this._normalizeLocal(propertyDesc);
    map = this.m_map;
    r = map.get(n);
    if (r != null) {
      r._mergeProperty(n, d);
    } else {
      map.put(n, this._createFSMRecord(n, d, null, null));
    }
  }

  /**
   * set the value of a property
   * 
   * @param rec
   *          the record
   */
  synchronized final void _setPropertyValue(
      final _PropertyFSMSettingRecord rec) {
    synchronized (rec) {
      this._setPropertyValue(rec.m_propertyName, rec.m_propertyDesc,
          rec.m_propertyValue, rec.m_propertyValueDesc);
    }
  }

  /**
   * set the value of a property
   * 
   * @param propertyName
   *          the name of the property
   * @param propertyDesc
   *          the description of the property
   * @param propertyValue
   *          the property value
   * @param propertyValueDesc
   *          the property value description
   */
  synchronized final void _setPropertyValue(final String propertyName,
      final String propertyDesc, final Object propertyValue,
      final String propertyValueDesc) {
    final Object v;
    final String n, vn, vd, pd;
    final VT r;
    final HashMap<String, VT> map;

    this.fsmStateAssert(_PropertyFSM.STATE_OPEN);
    this.__assertOpenCloseWhenOpen();

    n = _PropertyFSM.__checkPropertyName(propertyName);
    v = this._normalizeLocal(propertyValue);
    if (v == null) {
      throw new IllegalArgumentException(((((//
          "A property value cannot be null or empty, but the value of property '" //$NON-NLS-1$
          + n) + "' is '" //$NON-NLS-1$
          ) + propertyValue) + '\'') + '.');
    }

    if ((_PropertyValueUnspecified.NAME.equalsIgnoreCase(n)) || //
        (_PropertyValueGeneralized.NAME.equalsIgnoreCase(n))) {
      throw new IllegalArgumentException(('\'' + n)
          + "' is not permitted as property name."); //$NON-NLS-1$
    }

    if (v instanceof String) {
      vn = ((String) v);
    } else {
      vn = this._normalizeLocal(String.valueOf(v));
      if (vn == null) {
        throw new IllegalArgumentException(
            (((((//
                "The textual representation of a property value cannot be null or empty, but the value '"//$NON-NLS-1$
                + propertyValue)
                + "' of property '" //$NON-NLS-1$
            + n) + "', when transformed to text, equals '" //$NON-NLS-1$
            ) + vn) + '\'') + '.');
      }
    }
    if ((v instanceof _PropertyValueUnspecified) || //
        (v instanceof _PropertyValueGeneralized) || //
        (v instanceof HierarchicalFSM) || //
        (v instanceof _IDObject) || //
        (_PropertyValueUnspecified.NAME.equalsIgnoreCase(vn)) || //
        (_PropertyValueGeneralized.NAME.equalsIgnoreCase(vn))) {
      throw new IllegalArgumentException(((((//
          '\'' + vn) + "' is not permitted as value of property '") + //$NON-NLS-1$
          n) + '\'') + '.');
    }

    pd = this._normalizeLocal(propertyDesc);
    vd = this._normalizeLocal(propertyValueDesc);

    map = this.m_map;
    r = map.get(n);
    if (r != null) {
      synchronized (r) {
        r._mergePropertyValue(n, pd, v, vd);
        r.m_refCount++;
      }
    } else {
      map.put(propertyName, this._createFSMRecord(n, pd, v, vd));
    }
  }

  /**
   * get the property set
   * 
   * @return the property set
   */
  @SuppressWarnings("rawtypes")
  _PropertySet<?, ?, ?> _getPropertySet() {
    if (this.m_owner instanceof _PropertyFSM) {
      return ((_PropertyFSM) (this.m_owner))._getPropertySet();
    }
    throw new UnsupportedOperationException(//
        "Don't know how to obtain property set."); //$NON-NLS-1$
  }
}
