package org.optimizationBenchmarking.utils.hierarchy;

import org.optimizationBenchmarking.utils.comparison.EComparison;
import org.optimizationBenchmarking.utils.text.textOutput.MemoryTextOutput;

/**
 * A finite state machine (FSM) allows you to construct an object that can
 * ensure that certain things or actions are done in a specified sequence.
 * For this purpose, it manages an integer state and a set of at most 32
 * flags which can be set and asserted. This FSM is guarded against
 * parallel access by synchronization.
 */
public class FSM {

  /** no flags. the initial flags of all FSMs */
  protected static final int FLAG_NOTHING = 0;
  /** the initial state of all FSMs */
  protected static final int STATE_NOTHING = 0;

  /** the state */
  private volatile int m_state;

  /** the flags */
  private volatile int m_flags;

  /** create the fsm */
  protected FSM() {
    super();
    this.m_state = FSM.STATE_NOTHING;
    this.m_flags = FSM.FLAG_NOTHING;
  }

  /**
   * Set the state of this finite state machine
   * 
   * @param state
   *          the new state of this finite state machine
   */
  protected synchronized final void fsmStateSet(final int state) {
    this.m_state = state;
  }

  /**
   * Get the state of this finite state machine
   * 
   * @return the state of this finite state machine
   */
  protected synchronized final int fsmStateGet() {
    return this.m_state;
  }

  /**
   * Append the name of a given state to a string builder
   * 
   * @param state
   *          the state
   * @param sb
   *          the string builder
   */
  protected void fsmStateAppendName(final int state,
      final MemoryTextOutput sb) {
    if (state != FSM.STATE_NOTHING) {
      sb.append(state);
    } else {
      sb.append("{nothing}"); //$NON-NLS-1$
    }
  }

  /**
   * Assert that the state of finite state machine is related to
   * {@code mustState} according to {@code comp} and throw a
   * {@link java.lang.IllegalStateException} otherwise.
   * 
   * @param state
   *          the state this finite state machine must be in
   * @param comp
   *          the comparator
   * @return the state
   * @throws IllegalStateException
   *           if this FSM is not in state {@code state}
   */
  protected synchronized final int fsmStateAssert(final EComparison comp,
      final int state) {
    final MemoryTextOutput sb;
    final int is;

    is = this.m_state;
    if (comp.compare(this.m_state, state)) {
      return is;
    }
    sb = new MemoryTextOutput();
    sb.append("The state of "); //$NON-NLS-1$
    FSM._name(this, sb);
    sb.append(" should be "); //$NON-NLS-1$
    sb.append(comp.toString());
    sb.append(' ');
    sb.append('\'');
    this.fsmStateAppendName(state, sb);
    sb.append("', but it is in state '"); //$NON-NLS-1$
    this.fsmStateAppendName(is, sb);
    sb.append('\'');
    sb.append('.');
    throw new IllegalStateException(sb.toString());
  }

  /**
   * Assert that this finite state machine is in the given state, and
   * throws an {@link java.lang.IllegalStateException} otherwise.
   * 
   * @param state
   *          the state this finite state machine must be in
   * @return the state
   * @throws IllegalStateException
   *           if this FSM is not in state {@code state}
   */
  protected final int fsmStateAssert(final int state) {
    return this.fsmStateAssert(EComparison.SAME, state);
  }

  /**
   * Atomically assert that this finite state machine is in the state
   * {@code mustState} and move it to state {@code nextState} or throws an
   * {@link java.lang.IllegalStateException} otherwise.
   * 
   * @param mustState
   *          the state this finite state machine must be in
   * @param nextState
   *          the state the finite state machine should be moved to
   * @return the old state
   * @throws IllegalStateException
   *           if this FSM is not in state {@code state}
   */
  protected final int fsmStateAssertAndSet(final int mustState,
      final int nextState) {
    return this.fsmStateAssertAndSet(EComparison.SAME, mustState,
        nextState);
  }

  /**
   * Atomically assert that the state of finite state machine is related to
   * {@code mustState} according to {@code comp} and move it to state
   * {@code nextState} or throws an {@link java.lang.IllegalStateException}
   * otherwise.
   * 
   * @param mustState
   *          the state this finite state machine must be in
   * @param comp
   *          the comparator
   * @param nextState
   *          the state the finite state machine should be moved to
   * @return the old state
   * @throws IllegalStateException
   *           if this FSM is not in state {@code state}
   */
  protected synchronized final int fsmStateAssertAndSet(
      final EComparison comp, final int mustState, final int nextState) {
    final int ret;
    ret = this.fsmStateAssert(comp, mustState);
    this.m_state = nextState;
    return ret;
  }

  /**
   * Set some flags
   * 
   * @param set
   *          the flags to set
   * @return {@code true} if at least one the flags was not yet set
   */
  protected synchronized final boolean fsmFlagsSet(final int set) {
    final int flags, newFlags;

    this.m_flags = newFlags = ((flags = this.m_flags) | set);
    return (newFlags != flags);
  }

  /**
   * Clear some flags
   * 
   * @param clear
   *          the flags to clear
   * @return {@code true} if at least one of the flags was already set
   */
  protected synchronized final boolean fsmFlagsClear(final int clear) {
    final int flags, newFlags;
    this.m_flags = newFlags = ((flags = this.m_flags) & (~clear));
    return (newFlags != flags);
  }

  /**
   * Append a string representation of a given flag to a string builder
   * 
   * @param flagValue
   *          the value of the flag: always a power of 2
   * @param flagIndex
   *          the bit-index corresponding to the flag:
   *          <code>flagValue=2<sup>flagIndex</sup></code>
   * @param append
   *          the string builder to append to
   */
  protected void fsmFlagsAppendName(final int flagValue,
      final int flagIndex, final MemoryTextOutput append) {
    append.append(flagIndex);
  }

  /**
   * Convert a flag set to a string
   * 
   * @param flags
   *          the flags
   * @param fsm
   *          the finite state machine
   * @param sb
   *          the string builder
   */
  private static final void __flagsToSB(final int flags, final FSM fsm,
      final MemoryTextOutput sb) {
    int f, i;
    boolean found;

    f = flags;
    i = 0;
    found = true;
    do {

      if ((f & 1) != 0) {
        f >>>= 1;

        if (found) {
          found = false;
        } else {
          sb.append(',');
        }

        fsm.fsmFlagsAppendName((1 << i), i, sb);
      } else {
        f >>>= 1;
      }
      i += 1;
    } while (f != 0);
    if (found) {
      sb.append("{none}"); //$NON-NLS-1$
    }
  }

  /**
   * Assert that this finite state machine has all of the given flags set,
   * and throws an {@link java.lang.IllegalStateException} otherwise.
   * 
   * @param mustBeSet
   *          the flags that must be set
   * @throws IllegalStateException
   *           if at least one of the flags in {@code mustBeSet} is not set
   *           in the FSM
   */
  protected synchronized final void fsmFlagsAssertTrue(final int mustBeSet) {
    this.__fsmFlagsAssert(this.m_flags, mustBeSet, 0);
  }

  /**
   * Assert that this finite state machine does not have the given
   * configuration of flags set, and throws an
   * {@link java.lang.IllegalStateException} otherwise.
   * 
   * @param mustNotBeSet
   *          the flags that must not be set
   * @throws IllegalStateException
   *           if this FSM has any of the flags in {@code mustNotBeSet} set
   */
  protected synchronized final void fsmFlagsAssertFalse(
      final int mustNotBeSet) {
    this.__fsmFlagsAssert(this.m_flags, 0, mustNotBeSet);
  }

  /**
   * Assert that the flags in {@code mustBeSEt} are set and that those in
   * {@code mustNotBeSet} are not set, throw an
   * {@link java.lang.IllegalStateException} otherwise.
   * 
   * @param flags
   *          the flags
   * @param mustBeSet
   *          the flags that must be set
   * @param mustNotBeSet
   *          the flags that must not be set
   * @throws IllegalStateException
   *           if this FSM has any of the flags in {@code mustNotBeSet} set
   *           or misses some in {@code mustBeSet}
   */
  private final void __fsmFlagsAssert(final int flags,
      final int mustBeSet, final int mustNotBeSet) {
    final int missing, tooMuch;
    final MemoryTextOutput sb;

    missing = ((~flags) & mustBeSet);
    tooMuch = (flags & mustNotBeSet);

    if ((missing != 0) || (tooMuch != 0)) {
      sb = new MemoryTextOutput();
      sb.append("Inconsistent flags in "); //$NON-NLS-1$
      FSM._name(this, sb);
      sb.append(" the flags "); //$NON-NLS-1$
      if (missing != 0) {
        FSM.__flagsToSB(missing, this, sb);
        sb.append(" are not set but should be set"); //$NON-NLS-1$
      }
      if (tooMuch != 0) {
        if (missing != 0) {
          sb.append(" and the flags "); //$NON-NLS-1$
        }
        FSM.__flagsToSB(tooMuch, this, sb);
        sb.append(" should not be set but are set"); //$NON-NLS-1$
      }
      sb.append('.');
      throw new IllegalStateException(sb.toString());
    }
  }

  /**
   * Assert that the flags in {@code mustBeSEt} are set and that those in
   * {@code mustNotBeSet} are not set.
   * 
   * @param mustBeSet
   *          the flags that must be set
   * @param mustNotBeSet
   *          the flags that must not be set
   * @throws IllegalStateException
   *           if this FSM has any of the flags in {@code mustNotBeSet} set
   *           or misses some in {@code mustBeSet}
   */
  protected synchronized final void fsmFlagsAssert(final int mustBeSet,
      final int mustNotBeSet) {
    this.__fsmFlagsAssert(this.m_flags, mustBeSet, mustNotBeSet);
  }

  /**
   * In one atomic operation:
   * <ol>
   * <li>Assert that the flags in {@code mustBeSEt} are set and that those
   * in {@code mustNotBeSet} are not set.</li>
   * <li>
   * <ol>
   * <li>If so, set the flags in {@code set} and clear those in
   * {@code clear}.</li>
   * <li>Throw an {@link java.lang.IllegalStateException} otherwise.</li>
   * </ol>
   * </li>
   * </ol>
   * 
   * @param mustBeSet
   *          the flags that must be set
   * @param mustNotBeSet
   *          the flags that must not be set
   * @param set
   *          the flags to set
   * @param clear
   *          the flags to clear
   * @throws IllegalStateException
   *           if this FSM has any of the flags in {@code mustNotBeSet} set
   *           or misses some in {@code mustBeSet}
   */
  protected synchronized final void fsmFlagsAssertAndUpdate(
      final int mustBeSet, final int mustNotBeSet, final int set,
      final int clear) {
    final int flags;

    this.__fsmFlagsAssert((flags = this.m_flags), mustBeSet, mustNotBeSet);
    this.m_flags = ((flags | set) & (~clear));
  }

  /**
   * append the state information to the destination
   * 
   * @param dest
   *          the destination
   */
  void _appendState(final MemoryTextOutput dest) {
    dest.append("state="); //$NON-NLS-1$
    this.fsmStateAppendName(this.m_state, dest);
    dest.append(",flags="); //$NON-NLS-1$
    FSM.__flagsToSB(this.m_flags, this, dest);
  }

  /**
   * get the name of an hierarchy element
   * 
   * @param element
   *          the element
   * @param dest
   *          the destination string builder
   */
  static final void _name(final FSM element, final MemoryTextOutput dest) {
    if (element == null) {
      dest.append((Object) null);
    } else {
      dest.append(element.getClass().getSimpleName());
      dest.append('#');
      dest.append(System.identityHashCode(element));
      dest.append('[');
      element._appendState(dest);
      dest.append(']');
    }
  }

  /** {@inheritDoc} */
  @Override
  public String toString() {
    final MemoryTextOutput sb;

    sb = new MemoryTextOutput();
    FSM._name(this, sb);
    return sb.toString();
  }

}
