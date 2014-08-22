package org.optimizationBenchmarking.utils.hierarchy;

import java.io.Closeable;

import org.optimizationBenchmarking.utils.ErrorUtils;
import org.optimizationBenchmarking.utils.text.TextUtils;
import org.optimizationBenchmarking.utils.text.textOutput.MemoryTextOutput;

/**
 * <p>
 * The base class for &quot;hierarchical&quot; APIs. The idea of an
 * hierarchical API is that we are able to declare the start and the end of
 * a scope and arbitrarily nest scopes. Each scope passes through states
 * regarding its hierarchical and open/closed status like a finite state
 * machine.
 * </p>
 * <p>
 * You can imagine it like StAX XML writing, where the you can declare the
 * {@link javax.xml.stream.XMLStreamWriter#writeStartElement(String) start}
 * and the {@link javax.xml.stream.XMLStreamWriter#writeEndElement() end}
 * of an element by calling a
 * {@link javax.xml.stream.XMLStreamWriter#writeStartElement(String) start}
 * and an {@link javax.xml.stream.XMLStreamWriter#writeEndElement() end}
 * method of a given {@link javax.xml.stream.XMLStreamWriter object}. Now
 * in StaX, you can nest such calls, so they form something like nested
 * scopes. However, you need to keep track to match all start and end
 * calls, otherwise there will be errors.
 * </p>
 * <p>
 * In our API, this matching is managed with a simple and elegant trick
 * (yes, I am proud of myself): we implement each scope as instance of the
 * interface {@link java.io.Closeable}, which in turn extends
 * {@link java.lang.AutoCloseable} &ndash; and this interface can be
 * managed with the new <code>try-with</code> clause introduced in Java
 * 1.7. The class {@link HierarchicalFSM} here implements this concept in a
 * hierarchical way: Each {@link HierarchicalFSM} can have either one
 * {@link #getOwner() owner} (if it is a nested scope) or no
 * {@link #getOwner() owner} (if it is the root scope). It can be opened
 * (exactly once) and closed, and its owner is informed about these
 * actions. In particular, the following API calls take place:
 * </p>
 * <ol>
 * <li>In its constructor, an subclass of {@link HierarchicalFSM} must call
 * the method {@link #open()} exactly once.
 * <ol>
 * <li>If the element has an {@link #getOwner() owner}, this will invoke
 * the method {@link #beforeChildOpens(HierarchicalFSM,boolean)} of the
 * owner. Here the owner can check if the child is allowed at this point in
 * time and throw an exception to veto. If it does not throw an exception,
 * the child will be enqueued in its owner's child queue.</li>
 * <li>If there either is no owner or the owner's
 * {@link #beforeChildOpens(HierarchicalFSM,boolean)} method completed
 * successfully, the method {@link #onOpen()} of the element is now
 * invoked.</li>
 * <li>If the {@link #onOpen()} method has completed successfully and the
 * element has an {@link #getOwner() owner}, the owner's
 * {@link #afterChildOpened(HierarchicalFSM, boolean)} method is invoked.</li>
 * <li>If all of that went well, the element is officially
 * &quot;open&quot;.</li>
 * </ol>
 * </li>
 * <li>If the element reaches the end of its scope, the {@link #close()}
 * method will automatically be invoked by Java's <code>try-with</code>
 * clause. This will then lead to the following events:
 * <ol>
 * <li>The element's {@link #onClose()} method will be called.</li>
 * <li>After the {@link #onClose()} method has been completed and the
 * element has an {@link #getOwner() owner}, it gets ready for purging from
 * the owner's child queue.</li>
 * </ol>
 * <li>Flushing of the Child Queue:
 * <ol>
 * <li>At certain points in time during the execution, the child queue of
 * an owner may be {@link #__flush()} and the child elements ready for
 * purging are passed to the {@link #afterChildClosed(HierarchicalFSM)}
 * method. This happens in the exactly same sequence in which the children
 * have entered the {@link #beforeChildOpens(HierarchicalFSM,boolean)}
 * method. In other words, if multiple child-scopes are opened and closed
 * concurrently, they are still processed in the right order.</li>
 * <li>The moments when the queue is checked for elements that can be
 * purged are whenever a child element has completed its {@link #onClose()}
 * method and when the owner enters its {@link #close()} method, but before
 * its {@link #onClose()} method.</li>
 * <li>If a child has not been closed when its owner is enters its
 * {@link #close()} method, this will cause an error.</li>
 * </ol>
 * </li>
 * </ol>
 * <p>
 * Our API is entirely thread-safe: An arbitrary number of threads may
 * create and use scopes in parallel. The proper sequence of events is
 * still ensured.
 * </p>
 * <p>
 * However, our API is also fail-fast: I built it so that you need to
 * follow the steps detailed above exactly. You can call {@link #open()}
 * exactly once and each of the <code>before&hellip;</code>,
 * <code>after&hellip;</code>, and <code>on&hellip;</code> methods must and
 * will be called exactly once for each object and parameter. Otherwise you
 * get exceptions.
 * </p>
 */
public class HierarchicalFSM extends FSM implements Closeable {

  /** the ground state */
  private static final int STATE_NEW = 0;
  /** we begin opening this element */
  private static final int STATE_OPEN_BEGIN = (HierarchicalFSM.STATE_NEW + 1);

  /** we begin opening this element as a child element */
  private static final int STATE_BEFORE_BEFORE_CHILD_OPENS_CHILD_A = (HierarchicalFSM.STATE_OPEN_BEGIN + 1);

  /** we begin opening this element as a child element */
  private static final int STATE_BEFORE_BEFORE_CHILD_OPENS_CHILD_B = (HierarchicalFSM.STATE_BEFORE_BEFORE_CHILD_OPENS_CHILD_A + 1);

  /** the afterChildOpen method of the owner has been called */
  private static final int STATE_AFTER_BEFORE_CHILD_OPENS_CHILD = (HierarchicalFSM.STATE_BEFORE_BEFORE_CHILD_OPENS_CHILD_B + 1);

  /** the {@link #onOpen()} method has been called */
  private static final int STATE_AFTER_ON_OPEN = (HierarchicalFSM.STATE_AFTER_BEFORE_CHILD_OPENS_CHILD + 1);
  /**
   * the {@link #afterChildOpened(HierarchicalFSM,boolean)} method of the
   * owner has been called
   */
  private static final int STATE_AFTER_AFTER_CHILD_OPENED_CHILD = (HierarchicalFSM.STATE_AFTER_ON_OPEN + 1);

  /**
   * the element has been opened
   * 
   * @see #open()
   */
  private static final int STATE_OPENED = (HierarchicalFSM.STATE_AFTER_AFTER_CHILD_OPENED_CHILD + 1);

  /** the before child open call has been done */
  private static final int STATE_BEFORE_BEFORE_CHILD_OPENS_OWNER = (HierarchicalFSM.STATE_OPENED + 1);
  /** we are after the before child open call */
  private static final int STATE_AFTER_BEFORE_CHILD_OPENS_OWNER_A = (HierarchicalFSM.STATE_BEFORE_BEFORE_CHILD_OPENS_OWNER + 1);
  /** we are after the before child open call */
  private static final int STATE_AFTER_BEFORE_CHILD_OPENS_OWNER_B = (HierarchicalFSM.STATE_AFTER_BEFORE_CHILD_OPENS_OWNER_A + 1);
  /** we begin the after child open call */
  private static final int STATE_BEFORE_AFTER_CHILD_OPENED_OWNER = (HierarchicalFSM.STATE_AFTER_BEFORE_CHILD_OPENS_OWNER_B + 1);
  /** the after child open call has been done */
  private static final int STATE_AFTER_AFTER_CHILD_OPENED_OWNER = (HierarchicalFSM.STATE_BEFORE_AFTER_CHILD_OPENED_OWNER + 1);

  /** the state right before the onChildClosed method is called */
  private static final int STATE_BEFORE_AFTER_CHILD_CLOSED_OWNER = (HierarchicalFSM.STATE_AFTER_AFTER_CHILD_OPENED_OWNER + 1);
  /** the state right after the onChildClosed method is called */
  private static final int STATE_AFTER_AFTER_CHILD_CLOSED_OWNER = (HierarchicalFSM.STATE_BEFORE_AFTER_CHILD_CLOSED_OWNER + 1);

  /** the process of closing the element has begun */
  private static final int STATE_CLOSE_BEGIN = (HierarchicalFSM.STATE_AFTER_AFTER_CHILD_CLOSED_OWNER + 1);

  /** the element is before calling its onClose method */
  private static final int STATE_BEFORE_ON_CLOSE = (HierarchicalFSM.STATE_CLOSE_BEGIN + 1);

  /** the element has called its onClose method */
  private static final int STATE_AFTER_ON_CLOSE = (HierarchicalFSM.STATE_BEFORE_ON_CLOSE + 1);

  /** the element has called its onChildClose method */
  private static final int STATE_BEFORE_AFTER_CHILD_CLOSED_CHILD = (HierarchicalFSM.STATE_AFTER_ON_CLOSE + 1);

  /** the element has called its onClose method */
  private static final int STATE_AFTER_AFTER_CHILD_CLOSED_CHILD = (HierarchicalFSM.STATE_BEFORE_AFTER_CHILD_CLOSED_CHILD + 1);

  /** the element is finished */
  private static final int STATE_CLOSED = (HierarchicalFSM.STATE_AFTER_AFTER_CHILD_CLOSED_CHILD + 1);

  /** the state names */
  private static final String[] STATE_NAMES;

  /** the state descriptions */
  private static final String[] STATE_DESCS;

  static {
    STATE_NAMES = new String[HierarchicalFSM.STATE_CLOSED + 1];
    STATE_DESCS = new String[HierarchicalFSM.STATE_NAMES.length];

    HierarchicalFSM.STATE_DESCS[HierarchicalFSM.STATE_NEW] = ((HierarchicalFSM.STATE_NAMES[HierarchicalFSM.STATE_NEW] = "NEW") + //$NON-NLS-1$
    ": The element has just has been created and is in the 'new' state, i.e., no API method has yet been invoked on it. In particular, the open() method has not yet been called."); //$NON-NLS-1$

    HierarchicalFSM.STATE_DESCS[HierarchicalFSM.STATE_OPEN_BEGIN] = ((HierarchicalFSM.STATE_NAMES[HierarchicalFSM.STATE_OPEN_BEGIN] = "OPEN_BEGIN") + //$NON-NLS-1$
    ": The element has just entered its 'open' method."); //$NON-NLS-1$

    HierarchicalFSM.STATE_DESCS[HierarchicalFSM.STATE_BEFORE_BEFORE_CHILD_OPENS_CHILD_A] = ((HierarchicalFSM.STATE_NAMES[HierarchicalFSM.STATE_BEFORE_BEFORE_CHILD_OPENS_CHILD_A] = "BEFORE_BEFORE_CHILD_OPENS_CHILD_A") + //$NON-NLS-1$
    ": The element is a child element with a non-null owner and it is waiting for its owner to invoke the 'beforeChildOpens' method."); //$NON-NLS-1$

    HierarchicalFSM.STATE_DESCS[HierarchicalFSM.STATE_BEFORE_BEFORE_CHILD_OPENS_CHILD_B] = ((HierarchicalFSM.STATE_NAMES[HierarchicalFSM.STATE_BEFORE_BEFORE_CHILD_OPENS_CHILD_B] = "BEFORE_BEFORE_CHILD_OPENS_CHILD_B") + //$NON-NLS-1$
    ": The element is a child element with a non-null owner and it is waiting for its owner to complete the 'beforeChildOpens' method."); //$NON-NLS-1$

    HierarchicalFSM.STATE_DESCS[HierarchicalFSM.STATE_AFTER_BEFORE_CHILD_OPENS_CHILD] = ((HierarchicalFSM.STATE_NAMES[HierarchicalFSM.STATE_AFTER_BEFORE_CHILD_OPENS_CHILD] = "AFTER_BEFORE_CHILD_OPENS_CHILD") + //$NON-NLS-1$
    ": The element is a child element with a non-null owner and its owner has just completed its 'beforeChildOpens' method."); //$NON-NLS-1$

    HierarchicalFSM.STATE_DESCS[HierarchicalFSM.STATE_AFTER_ON_OPEN] = ((HierarchicalFSM.STATE_NAMES[HierarchicalFSM.STATE_AFTER_ON_OPEN] = "AFTER_ON_OPEN") + //$NON-NLS-1$
    ": The element has just completed its 'onOpen' method."); //$NON-NLS-1$

    HierarchicalFSM.STATE_DESCS[HierarchicalFSM.STATE_AFTER_AFTER_CHILD_OPENED_CHILD] = ((HierarchicalFSM.STATE_NAMES[HierarchicalFSM.STATE_AFTER_AFTER_CHILD_OPENED_CHILD] = "AFTER_AFTER_CHILD_OPENED_CHILD") + //$NON-NLS-1$
    ": The element is a child element with a non-null owner and its owner has just completed its 'afterChildOpened' method."); //$NON-NLS-1$

    HierarchicalFSM.STATE_DESCS[HierarchicalFSM.STATE_OPENED] = ((HierarchicalFSM.STATE_NAMES[HierarchicalFSM.STATE_OPENED] = "OPENED") + //$NON-NLS-1$
    "OPENED: The element has completed its 'open' method."); //$NON-NLS-1$

    HierarchicalFSM.STATE_DESCS[HierarchicalFSM.STATE_BEFORE_BEFORE_CHILD_OPENS_OWNER] = ((HierarchicalFSM.STATE_NAMES[HierarchicalFSM.STATE_BEFORE_BEFORE_CHILD_OPENS_OWNER] = "BEFORE_BEFORE_CHILD_OPENS_OWNER") + //$NON-NLS-1$
    ": The element is an owner element which is about to invoke its 'beforeChildOpens' method on a child element (which is about to be opened)."); //$NON-NLS-1$

    HierarchicalFSM.STATE_DESCS[HierarchicalFSM.STATE_AFTER_BEFORE_CHILD_OPENS_OWNER_A] = ((HierarchicalFSM.STATE_NAMES[HierarchicalFSM.STATE_AFTER_BEFORE_CHILD_OPENS_OWNER_A] = //
    "AFTER_BEFORE_CHILD_OPENS_OWNER_A") + //$NON-NLS-1$
    ": The element is an owner element which has just finished to invoking its 'beforeChildOpens' method on a child element (which is about to be opened)."); //$NON-NLS-1$

    HierarchicalFSM.STATE_DESCS[HierarchicalFSM.STATE_AFTER_BEFORE_CHILD_OPENS_OWNER_A] = ((HierarchicalFSM.STATE_NAMES[HierarchicalFSM.STATE_AFTER_BEFORE_CHILD_OPENS_OWNER_A] = "AFTER_BEFORE_CHILD_OPENS_OWNER_B") + //$NON-NLS-1$
    ": The element is an owner element which has just finished to invoking its 'beforeChildOpens' method on a child element (which is about to be opened) and enqueued the child into its child queue."); //$NON-NLS-1$

    HierarchicalFSM.STATE_DESCS[HierarchicalFSM.STATE_BEFORE_AFTER_CHILD_OPENED_OWNER] = ((HierarchicalFSM.STATE_NAMES[HierarchicalFSM.STATE_BEFORE_AFTER_CHILD_OPENED_OWNER] = "BEFORE_AFTER_CHILD_OPENED_OWNER") + //$NON-NLS-1$
    ": The element is an owner element which is about to invoke its 'afterChildOpened' method on a child element (which just has been opened)."); //$NON-NLS-1$

    HierarchicalFSM.STATE_DESCS[HierarchicalFSM.STATE_AFTER_AFTER_CHILD_OPENED_OWNER] = ((HierarchicalFSM.STATE_NAMES[HierarchicalFSM.STATE_AFTER_AFTER_CHILD_OPENED_OWNER] = "AFTER_AFTER_CHILD_OPENED_OWNER") + //$NON-NLS-1$
    ": The element is an owner element which has just completed invoking its 'afterChildOpened' method on a child element (which just has been opened)."); //$NON-NLS-1$

    HierarchicalFSM.STATE_DESCS[HierarchicalFSM.STATE_BEFORE_AFTER_CHILD_CLOSED_OWNER] = ((HierarchicalFSM.STATE_NAMES[HierarchicalFSM.STATE_BEFORE_AFTER_CHILD_CLOSED_OWNER] = "BEFORE_AFTER_CHILD_CLOSED_OWNER") + //$NON-NLS-1$
    ": The element is an owner element which is just about to invoke its 'afterChildClosed' method on a child element (which just has been closed)."); //$NON-NLS-1$

    HierarchicalFSM.STATE_DESCS[HierarchicalFSM.STATE_AFTER_AFTER_CHILD_CLOSED_OWNER] = ((HierarchicalFSM.STATE_NAMES[HierarchicalFSM.STATE_AFTER_AFTER_CHILD_CLOSED_OWNER] = "AFTER_AFTER_CHILD_CLOSED_OWNER") + //$NON-NLS-1$
    ": The element is an owner element which is has just finished invoking its 'afterChildClosed' method on a child element (which just has been closed)."); //$NON-NLS-1$

    HierarchicalFSM.STATE_DESCS[HierarchicalFSM.STATE_CLOSE_BEGIN] = ((HierarchicalFSM.STATE_NAMES[HierarchicalFSM.STATE_CLOSE_BEGIN] = "CLOSE_BEGIN") + //$NON-NLS-1$
    ": The element has just begun its process of closing and disposal, i.e., for which 'close' was invoked."); //$NON-NLS-1$

    HierarchicalFSM.STATE_DESCS[HierarchicalFSM.STATE_BEFORE_ON_CLOSE] = ((HierarchicalFSM.STATE_NAMES[HierarchicalFSM.STATE_BEFORE_ON_CLOSE] = "BEFORE_ON_CLOSE") + //$NON-NLS-1$
    ": The element is about to call its 'onClose' method."); //$NON-NLS-1$

    HierarchicalFSM.STATE_DESCS[HierarchicalFSM.STATE_AFTER_ON_CLOSE] = ((HierarchicalFSM.STATE_NAMES[HierarchicalFSM.STATE_AFTER_ON_CLOSE] = "AFTER_ON_CLOSE") + //$NON-NLS-1$
    ": The element has just completed its 'onClose' method."); //$NON-NLS-1$

    HierarchicalFSM.STATE_DESCS[HierarchicalFSM.STATE_BEFORE_AFTER_CHILD_CLOSED_CHILD] = ((HierarchicalFSM.STATE_NAMES[HierarchicalFSM.STATE_BEFORE_AFTER_CHILD_CLOSED_CHILD] = "BEFORE_AFTER_CHILD_CLOSED_CHILD") + //$NON-NLS-1$
    ": The element is a child element with a non-null owner and this owning element is about to invoke its 'afterChildClosed' method on this child."); //$NON-NLS-1$

    HierarchicalFSM.STATE_DESCS[HierarchicalFSM.STATE_AFTER_AFTER_CHILD_CLOSED_CHILD] = ((HierarchicalFSM.STATE_NAMES[HierarchicalFSM.STATE_AFTER_AFTER_CHILD_CLOSED_CHILD] = "AFTER_AFTER_CHILD_CLOSED_CHILD") + //$NON-NLS-1$
    ": The element is a child element with a non-null owner and this owning element has finished invoking its 'afterChildClosed' method on this child."); //$NON-NLS-1$

    HierarchicalFSM.STATE_DESCS[HierarchicalFSM.STATE_CLOSED] = ((HierarchicalFSM.STATE_NAMES[HierarchicalFSM.STATE_CLOSED] = "CLOSED") + //$NON-NLS-1$
    ": The element has completed its live cycle. Its 'close' method has successfully been invoked and if it has an owner element, that owner element has completed its 'onChildClosed' method successful as well. There should be no more reference to this object and it should be about getting garbage-collected."); //$NON-NLS-1$

  }

  /** the owning element */
  final HierarchicalFSM m_owner;

  /** the state of this object */
  private volatile int m_hstate;

  /** the next element in this child queue */
  private volatile HierarchicalFSM m_next;

  /** the child queue */
  private volatile HierarchicalFSM m_childQueue;

  /**
   * create
   * 
   * @param owner
   *          the owning element
   */
  protected HierarchicalFSM(final HierarchicalFSM owner) {
    super();
    this.m_hstate = HierarchicalFSM.STATE_NEW;
    this.m_owner = owner;
  }

  /** {@inheritDoc} */
  @Override
  final void _appendState(final MemoryTextOutput dest) {
    final int h;
    super._appendState(dest);
    dest.append(",hstate="); //$NON-NLS-1$
    h = this.m_hstate;
    if ((h >= 0) && (h < HierarchicalFSM.STATE_NAMES.length)) {
      dest.append(HierarchicalFSM.STATE_NAMES[h]);
    } else {
      dest.append(h);
    }
  }

  /**
   * Get the owner
   * 
   * @return the owner
   */
  protected HierarchicalFSM getOwner() {
    return this.m_owner;
  }

  /**
   * Assert that there are no open children. This method must be called in
   * a synchronized context, as it itself is not synchronized.
   */
  protected final void assertNoChildren() {
    final HierarchicalFSM fsm;
    final MemoryTextOutput sb;

    fsm = this.m_childQueue;
    if (fsm != null) {
      sb = new MemoryTextOutput();
      FSM._name(this, sb);
      sb.append(" currently should not have any open children, but has child ");//$NON-NLS-1$      
      FSM._name(fsm, sb);
      sb.append('.');
      throw new IllegalStateException(sb.toString());
    }
  }

  /**
   * assert that we are in a given state
   * 
   * @param shouldState
   *          the state we should be in
   */
  private final void __assertState(final int shouldState) {
    final int isState;
    final MemoryTextOutput sb;

    if ((isState = this.m_hstate) != shouldState) {
      sb = new MemoryTextOutput();
      sb.append("The element "); //$NON-NLS-1$
      FSM._name(this, sb);
      sb.append(" should be in hierarchy state '");//$NON-NLS-1$
      sb.append(HierarchicalFSM.STATE_NAMES[shouldState]);
      sb.append(" but is in hierarchy state '");//$NON-NLS-1$
      sb.append(HierarchicalFSM.STATE_NAMES[isState]);
      sb.append('.');
      throw new IllegalStateException(sb.toString());
    }
  }

  /**
   * This method is called at most once, when the hierarchy element is
   * opened.
   */
  protected synchronized void onOpen() {
    this.__assertState((this.m_owner == null) ? HierarchicalFSM.STATE_OPEN_BEGIN
        : HierarchicalFSM.STATE_AFTER_BEFORE_CHILD_OPENS_CHILD);
    this.m_hstate = HierarchicalFSM.STATE_AFTER_ON_OPEN;
  }

  /** Open this object. */
  protected final void open() {
    final boolean hasOtherChildren;
    final HierarchicalFSM owner;

    if ((owner = this.m_owner) != null) {
      synchronized (owner) {
        synchronized (this) {
          this.__assertState(HierarchicalFSM.STATE_NEW);
          this.m_hstate = HierarchicalFSM.STATE_BEFORE_BEFORE_CHILD_OPENS_CHILD_A;
          hasOtherChildren = owner.__callBeforeChildOpen(this);
          this.__assertState(HierarchicalFSM.STATE_AFTER_BEFORE_CHILD_OPENS_CHILD);
          this.onOpen();
          this.__assertState(HierarchicalFSM.STATE_AFTER_ON_OPEN);
          owner.__callAfterChildOpen(this, hasOtherChildren);
          this.__assertState(HierarchicalFSM.STATE_AFTER_AFTER_CHILD_OPENED_CHILD);
          this.m_hstate = HierarchicalFSM.STATE_OPENED;
        }
      }
    } else {
      synchronized (this) {
        this.__assertState(HierarchicalFSM.STATE_NEW);
        this.m_hstate = HierarchicalFSM.STATE_OPEN_BEGIN;
        this.onOpen();
        this.__assertState(HierarchicalFSM.STATE_AFTER_ON_OPEN);
        this.m_hstate = HierarchicalFSM.STATE_OPENED;
      }
    }
  }

  /**
   * This method is called at most once for any child hierarchy element
   * that is opened.
   * 
   * @param child
   *          the child that is about to be opened
   * @param hasOtherChildren
   *          {@code true} if and only if this fsm has other open children
   */
  protected synchronized void beforeChildOpens(
      final HierarchicalFSM child, final boolean hasOtherChildren) {
    final MemoryTextOutput sb;

    if (child.m_owner != this) {
      sb = new MemoryTextOutput();
      sb.append("Element "); //$NON-NLS-1$
      FSM._name(child, sb);
      sb.append(" is not a child element of element "); //$NON-NLS-1$
      FSM._name(this, sb);
      sb.append(", so you cannot invoke 'beforeChildOpens' for it.");//$NON-NLS-1$
      throw new IllegalArgumentException(sb.toString());
    }

    this.__assertState(HierarchicalFSM.STATE_BEFORE_BEFORE_CHILD_OPENS_OWNER);

    synchronized (child) {
      child.__assertState(//
          HierarchicalFSM.STATE_BEFORE_BEFORE_CHILD_OPENS_CHILD_B);
      child.m_hstate = HierarchicalFSM.STATE_AFTER_BEFORE_CHILD_OPENS_CHILD;
    }

    this.m_hstate = HierarchicalFSM.STATE_AFTER_BEFORE_CHILD_OPENS_OWNER_A;
  }

  /**
   * Called before a child can execute its onOpen method
   * 
   * @param element
   *          the child element
   * @return {@code true} if and only if there are other open children,
   *         {@code false} if the new child will become the only open child
   *         if this method does not throw an exception
   */
  private final boolean __callBeforeChildOpen(final HierarchicalFSM element) {
    final MemoryTextOutput sb;
    final boolean hasOtherChildren;
    HierarchicalFSM prev, next;

    this.__assertState(HierarchicalFSM.STATE_OPENED);
    this.m_hstate = HierarchicalFSM.STATE_BEFORE_BEFORE_CHILD_OPENS_OWNER;

    if (element.m_next != null) {
      sb = new MemoryTextOutput();
      sb.append("Element ");//$NON-NLS-1$
      FSM._name(element, sb);
      sb.append(" cannot become a child element of element ");//$NON-NLS-1$
      FSM._name(this, sb);
      sb.append(", because it has already a successor element, namely "); //$NON-NLS-1$
      FSM._name(element.m_next, sb);
      sb.append('.');
      throw new IllegalStateException(sb.toString());
    }

    element.__assertState(//
        HierarchicalFSM.STATE_BEFORE_BEFORE_CHILD_OPENS_CHILD_A);
    element.m_hstate = HierarchicalFSM.STATE_BEFORE_BEFORE_CHILD_OPENS_CHILD_B;

    prev = null;
    next = this.m_childQueue;
    while (next != null) {
      if (next == element) {
        sb = new MemoryTextOutput();
        FSM._name(element, sb);
        sb.append(" cannot become a child element of element ");//$NON-NLS-1$
        FSM._name(this, sb);
        sb.append(", because it is already enqueued as opened.");//$NON-NLS-1$
        throw new IllegalStateException(sb.toString());
      }
      prev = next;
      next = next.m_next;
    }
    hasOtherChildren = (prev != null);

    this.beforeChildOpens(element, hasOtherChildren);
    this.__assertState(HierarchicalFSM.STATE_AFTER_BEFORE_CHILD_OPENS_OWNER_A);

    checkQueueAgain: {
      if (prev == null) {
        if (this.m_childQueue == null) {
          this.m_childQueue = element;
          break checkQueueAgain;
        }
      } else {
        if (prev.m_next == null) {
          prev.m_next = element;
          break checkQueueAgain;
        }
      }

      if (this.m_childQueue != null) {
        sb = new MemoryTextOutput();
        sb.append("The child queue of element "); //$NON-NLS-1$
        FSM._name(this, sb);
        sb.append(" has changed during the opening of child ");//$NON-NLS-1$
        FSM._name(element, sb);
        sb.append('.');
        throw new IllegalStateException(sb.toString());
      }
    }

    this.m_hstate = HierarchicalFSM.STATE_AFTER_BEFORE_CHILD_OPENS_OWNER_B;
    return hasOtherChildren;
  }

  /**
   * This method is called at most once for any child hierarchy element
   * that is opened.
   * 
   * @param child
   *          the child that is about to be opened
   * @param hasOtherChildren
   *          {@code true} if and only if this FSM has other open children,
   *          {@code false} if the new child is the only open child
   */
  protected synchronized void afterChildOpened(
      final HierarchicalFSM child, final boolean hasOtherChildren) {
    final MemoryTextOutput sb;

    if (child.m_owner != this) {
      sb = new MemoryTextOutput();
      sb.append("Element "); //$NON-NLS-1$
      FSM._name(child, sb);
      sb.append(" is not a child element of element ");//$NON-NLS-1$
      FSM._name(this, sb);
      sb.append(", so you cannot invoke 'afterChildOpened' for it.");//$NON-NLS-1$
      throw new IllegalArgumentException(sb.toString());
    }

    this.__assertState(HierarchicalFSM.STATE_BEFORE_AFTER_CHILD_OPENED_OWNER);

    synchronized (child) {
      child.__assertState(HierarchicalFSM.STATE_AFTER_ON_OPEN);
      child.m_hstate = HierarchicalFSM.STATE_AFTER_AFTER_CHILD_OPENED_CHILD;
    }
    this.m_hstate = HierarchicalFSM.STATE_AFTER_AFTER_CHILD_OPENED_OWNER;
  }

  /**
   * Called after a child can execute its onOpen method
   * 
   * @param element
   *          the child element
   * @param hasOtherChildren
   *          {@code true} if and only if this FSM has other open children,
   *          {@code false} if the new child is the only open child
   */
  private final void __callAfterChildOpen(final HierarchicalFSM element,
      final boolean hasOtherChildren) {
    this.__assertState(HierarchicalFSM.STATE_AFTER_BEFORE_CHILD_OPENS_OWNER_B);
    this.m_hstate = HierarchicalFSM.STATE_BEFORE_AFTER_CHILD_OPENED_OWNER;
    this.afterChildOpened(element, hasOtherChildren);
    this.__assertState(HierarchicalFSM.STATE_AFTER_AFTER_CHILD_OPENED_OWNER);
    this.m_hstate = HierarchicalFSM.STATE_OPENED;
  }

  /**
   * This method is invoked the object is closed.
   */
  protected synchronized void onClose() {
    this.__assertState(HierarchicalFSM.STATE_BEFORE_ON_CLOSE);
    this.m_hstate = HierarchicalFSM.STATE_AFTER_ON_CLOSE;
  }

  /**
   * This method is invoked after a child has been closed, and in the same
   * sequence in which children are opened.
   * 
   * @param child
   *          the child element
   */
  protected synchronized void afterChildClosed(final HierarchicalFSM child) {
    final MemoryTextOutput sb;

    if (child.m_owner == this) {
      this.__assertState(HierarchicalFSM.STATE_BEFORE_AFTER_CHILD_CLOSED_OWNER);

      synchronized (child) {
        child.__assertState(//
            HierarchicalFSM.STATE_BEFORE_AFTER_CHILD_CLOSED_CHILD);
        child.m_hstate = HierarchicalFSM.STATE_AFTER_AFTER_CHILD_CLOSED_CHILD;
      }
      this.m_hstate = HierarchicalFSM.STATE_AFTER_AFTER_CHILD_CLOSED_OWNER;
    } else {
      sb = new MemoryTextOutput();
      sb.append("Element "); //$NON-NLS-1$
      FSM._name(child, sb);
      sb.append(" is not a child element of element ");//$NON-NLS-1$
      FSM._name(this, sb);
      sb.append(", so you cannot invoke 'afterChildClosed' for it.");//$NON-NLS-1$
      throw new IllegalArgumentException(sb.toString());
    }
  }

  /** flush all closed children */
  private final void __flush() {
    HierarchicalFSM child;
    Throwable error;

    this.__assertState(HierarchicalFSM.STATE_OPENED);
    error = null;

    looper: for (;;) {
      child = this.m_childQueue;
      if (child == null) {
        break looper;
      }

      synchronized (child) {
        if (child.m_hstate != HierarchicalFSM.STATE_AFTER_ON_CLOSE) {
          break looper;
        }

        child.m_hstate = HierarchicalFSM.STATE_BEFORE_AFTER_CHILD_CLOSED_CHILD;
        this.m_hstate = HierarchicalFSM.STATE_BEFORE_AFTER_CHILD_CLOSED_OWNER;

        try {
          this.afterChildClosed(child);
          child.__assertState(//
              HierarchicalFSM.STATE_AFTER_AFTER_CHILD_CLOSED_CHILD);
          child.m_hstate = HierarchicalFSM.STATE_CLOSED;
          this.__assertState(HierarchicalFSM.STATE_AFTER_AFTER_CHILD_CLOSED_OWNER);
        } catch (final RuntimeException a) {
          error = ErrorUtils.aggregateError(error, a);
        }

        this.m_childQueue = child.m_next;
      }
    }

    this.m_hstate = HierarchicalFSM.STATE_OPENED;

    if (error != null) {
      ErrorUtils.throwAsRuntimeException(error);
    }
  }

  /** perform the closing */
  private synchronized final void __close() {
    final int oldState;
    final MemoryTextOutput sb;

    oldState = this.m_hstate;
    if (oldState >= HierarchicalFSM.STATE_CLOSE_BEGIN) {
      return;
    }
    this.m_hstate = HierarchicalFSM.STATE_CLOSE_BEGIN;

    if (oldState != HierarchicalFSM.STATE_OPENED) {
      sb = new MemoryTextOutput();
      sb.append("The element "); //$NON-NLS-1$
      FSM._name(this, sb);
      sb.append(" should be in hierarchy state '");//$NON-NLS-1$
      sb.append(HierarchicalFSM.STATE_NAMES[HierarchicalFSM.STATE_OPENED]);
      sb.append("', but is in hierarchy state ");//$NON-NLS-1$
      sb.append(HierarchicalFSM.STATE_NAMES[oldState]);
      throw new IllegalStateException(sb.toString());
    }

    if (this.m_childQueue != null) {
      sb = new MemoryTextOutput();
      sb.append("Cannot close "); //$NON-NLS-1$
      FSM._name(this, sb);
      sb.append(" since it still has child elements, namely ");//$NON-NLS-1$
      FSM._name(this.m_childQueue, sb);
      sb.append('.');
      throw new IllegalStateException(sb.toString());
    }

    this.m_hstate = HierarchicalFSM.STATE_BEFORE_ON_CLOSE;
    this.onClose();
    this.__assertState(HierarchicalFSM.STATE_AFTER_ON_CLOSE);

    if (this.m_owner == null) {
      this.m_hstate = HierarchicalFSM.STATE_CLOSED;
    }
  }

  /** {@inheritDoc} */
  @Override
  public final void close() {
    if (this.m_owner != null) {
      synchronized (this.m_owner) {
        this.__close();
        this.m_owner.__flush();
      }
    } else {
      this.__close();
    }
  }

  /**
   * Throw an error indicating that the given child is not allowed.
   * 
   * @param child
   *          the child
   */
  protected final void throwChildNotAllowed(final HierarchicalFSM child) {
    final MemoryTextOutput sb;

    sb = new MemoryTextOutput();
    FSM._name(this, sb);
    sb.append(" is an instance of "); //$NON-NLS-1$
    sb.append(TextUtils.className(this.getClass()));
    sb.append(" and does not allow the child element ");//$NON-NLS-1$
    FSM._name(child, sb);
    if (child != null) {
      sb.append(", which is an instance of ");//$NON-NLS-1$
      sb.append(TextUtils.className(child.getClass()));
    }
    sb.append('.');

    throw new IllegalArgumentException(sb.toString());
  }

}
