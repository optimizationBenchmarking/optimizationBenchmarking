package examples.org.optimizationBenchmarking.utils.document;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import org.optimizationBenchmarking.utils.document.spec.ELabelType;
import org.optimizationBenchmarking.utils.document.spec.ILabel;

/** a class for marking termination */
final class _RandomDocumentExampleTermination extends Thread {

  /** the maximum number of steps: {@value} */
  private static final int MAX_STEPS = 3000;

  /** the minimum maximum section depth */
  private static final int MIN_MAX_DEPTH = 3;

  /** the example is running */
  private static final int RUNNING = 0;
  /** the example is running but should terminate as soon as possible */
  private static final int TERMINATING = (_RandomDocumentExampleTermination.RUNNING + 1);
  /** the example is running but has completed all necessities */
  private static final int ALL_COMPLETED = (_RandomDocumentExampleTermination.TERMINATING + 1);
  /** the example is has terminated */
  private static final int TERMINATED = (_RandomDocumentExampleTermination.ALL_COMPLETED + 1);

  /** did we terminate ? */
  private volatile int m_state;

  /** the end time */
  private final long m_end;

  /** the finished goals */
  private final AtomicBoolean[] m_done;

  /** the maximum section depth */
  private final AtomicBoolean m_maxSectionDepth;

  /** the step counter */
  private final AtomicInteger m_steps;

  /** the list of auto-allocated labels */
  private final ArrayList<ILabel>[] m_autoAllocatedLabels;

  /**
   * create
   *
   * @param autoAllocatedLabels
   *          the list of auto-allocated labels
   * @param maxTime
   *          the default maximum runtime <em>suggestion</em>
   */
  _RandomDocumentExampleTermination(
      final ArrayList<ILabel>[] autoAllocatedLabels, final long maxTime) {
    super();

    int i;
    this.m_state = _RandomDocumentExampleTermination.RUNNING;
    this.m_end = (System.currentTimeMillis() + maxTime);

    i = _ERandomDocumentExampleElements.ELEMENT_COUNT;
    this.m_done = new AtomicBoolean[i];
    for (; (--i) >= 0;) {
      this.m_done[i] = new AtomicBoolean(false);
    }
    this.m_maxSectionDepth = new AtomicBoolean(false);

    this.m_steps = new AtomicInteger(
        _RandomDocumentExampleTermination.MAX_STEPS);
    this.m_autoAllocatedLabels = autoAllocatedLabels;
    this.__checkAllDone();
    this.start();
  }

  /**
   * set the maximum section depth
   *
   * @param maxDepth
   *          the max depth
   */
  final void _setMaxSectionDepth(final int maxDepth) {
    if ((maxDepth > _RandomDocumentExampleTermination.MIN_MAX_DEPTH)
        && (!(this.m_maxSectionDepth.getAndSet(true)))) {
      this.__checkAllDone();
    }
  }

  /**
   * get a label
   *
   * @param type
   *          the label type
   * @param rand
   *          the random number generator
   * @return the label (or AUTO)
   */
  final ILabel _getLabel(final Random rand, final ELabelType type) {
    final ArrayList<ILabel> list;
    final int size;
    ILabel label;

    list = this.m_autoAllocatedLabels[type.ordinal()];
    label = null;

    synchronized (list) {
      size = list.size();
      if (size > 0) {
        label = list.remove(rand.nextInt(size));
      }
    }

    if (label != null) {
      if (size == 1) {
        this.__checkAllDone();
      }
      return label;
    }

    return ELabelType.AUTO;
  }

  /**
   * An element has been done
   *
   * @param element
   *          the element
   */
  final void _done(final _ERandomDocumentExampleElements element) {
    final int ordinal;
    ordinal = element.ordinal();
    if (!(this.m_done[ordinal].getAndSet(true))) {
      this.__checkAllDone();
    }
  }

  /** Assume that everything is done */
  final void _assumeAllDone() {

    for (final AtomicBoolean bool : this.m_done) {
      bool.set(true);
    }
    for (final ArrayList<ILabel> labels : this.m_autoAllocatedLabels) {
      synchronized (labels) {
        labels.clear();
      }
    }
    this.m_maxSectionDepth.set(true);

    synchronized (this) {
      switch (this.m_state) {
        case RUNNING: {
          this.m_state = _RandomDocumentExampleTermination.ALL_COMPLETED;
          break;
        }
        case TERMINATING: {
          this.m_state = _RandomDocumentExampleTermination.TERMINATED;
          break;
        }
        default: {
          return;
        }
      }
      this.notifyAll();
    }
  }

  /** check whether everything is done */
  private final void __checkAllDone() {
    for (final AtomicBoolean b : this.m_done) {
      if (!(b.get())) {
        return;
      }
    }
    if (!(this.m_maxSectionDepth.get())) {
      return;
    }
    for (final ArrayList<ILabel> list : this.m_autoAllocatedLabels) {
      synchronized (list) {
        if (!(list.isEmpty())) {
          return;
        }
      }
    }
    synchronized (this) {
      switch (this.m_state) {
        case RUNNING: {
          this.m_state = _RandomDocumentExampleTermination.ALL_COMPLETED;
          break;
        }
        case TERMINATING: {
          this.m_state = _RandomDocumentExampleTermination.TERMINATED;
          break;
        }
        default: {
          //
        }
      }
      this.notifyAll();
    }
  }

  /** mark termination */
  final void _terminate() {
    synchronized (this) {
      this.m_state = _RandomDocumentExampleTermination.TERMINATED;
      this.notifyAll();
    }
  }

  /**
   * should we continue or terminate ?
   *
   * @return {@code true} if we should continue, {@code false} otherwise
   */
  final boolean _continue() {
    if (this.m_state >= _RandomDocumentExampleTermination.TERMINATED) {
      return false;
    }
    if (this.m_steps.decrementAndGet() == 0) {
      synchronized (this) {
        switch (this.m_state) {
          case RUNNING: {
            this.m_state = _RandomDocumentExampleTermination.TERMINATING;
            return true;
          }
          case TERMINATING: {
            return true;
          }
          case ALL_COMPLETED: {
            this.m_state = _RandomDocumentExampleTermination.TERMINATED;
            this.notifyAll();
            return false;
          }
          default: {
            return false;
          }
        }
      }
    }
    return true;
  }

  /**
   * did we complete all elements?
   *
   * @return {@code true} if we have completed all elements, {@code false}
   *         otherwise
   */
  final boolean _isSomethingMissing() {
    return (this.m_state < _RandomDocumentExampleTermination.ALL_COMPLETED);
  }

  /**
   * Suggest a document element to be added next: Randomly pick one from
   * {@code from}. If the element has not yet been {@link #m_done inserted
   * before}, return it. If it has {@link #m_autoAllocatedLabels unused
   * pre-allocated labels}, insert it. If it is a
   * {@link _ERandomDocumentExampleElements#FIGURE_SERIES}, and there are
   * unused pre-allocated labels of type
   * {@link org.optimizationBenchmarking.utils.document.spec.ELabelType#SUBFIGURE}
   * , return it. Otherwise, repeat with the next random element. If
   * nothing could be found, return a randomly chosen element.
   *
   * @param rand
   *          the randomizer
   * @param from
   *          the set of elements to choose from
   * @return the element
   */
  @SuppressWarnings("fallthrough")
  final _ERandomDocumentExampleElements _suggest(final Random rand,
      final _ERandomDocumentExampleElements... from) {
    final _ERandomDocumentExampleElements[] choose;
    final int length;
    _ERandomDocumentExampleElements chosen;
    int trials;

    choose = (((from == null) || (from.length <= 0)) ? _ERandomDocumentExampleElements.ELEMENTS
        : from);

    length = choose.length;

    switch (this.m_state) {
      case RUNNING:
      case TERMINATING: {
        for (trials = (length << 2); (--trials) >= 0;) {
          chosen = choose[rand.nextInt(length)];
          if (!(this.m_done[chosen.ordinal()].get())) {
            return chosen;
          }
          if (chosen.m_label == null) {
            continue;
          }
          if (!(this.m_autoAllocatedLabels[chosen.m_label.ordinal()]
              .isEmpty())) {
            return chosen;
          }
          if (chosen == _ERandomDocumentExampleElements.FIGURE_SERIES) {
            if (!(this.m_autoAllocatedLabels[ELabelType.SUBFIGURE
                .ordinal()].isEmpty())) {
              return chosen;
            }
          }
        }
      }
      default: {
        return choose[rand.nextInt(choose.length)];
      }
    }
  }

  /** {@inheritDoc} */
  @SuppressWarnings({ "fallthrough", "unused" })
  @Override
  public final void run() {
    long time;
    boolean done, drop;
    int emergencyFlushLabels;
    ArrayList<ILabel> labels;

    emergencyFlushLabels = 0;
    for (;;) {

      synchronized (this) {
        switch (this.m_state) {
          case RUNNING:
          case TERMINATING: {
            this.__checkAllDone();
            if (this.m_state == _RandomDocumentExampleTermination.TERMINATED) {
              return;
            }
          }
          case ALL_COMPLETED: {
            break;
          }

          default: {
            this.m_state = _RandomDocumentExampleTermination.TERMINATED;
            return;
          }
        }

        done = false;
        if (this.m_steps.get() <= 0) {
          done = true;
        }
        if ((time = System.currentTimeMillis()) >= this.m_end) {
          // We cannot immediately stop, as there may be allocated labels
          // which have not yet been used. So first we declare all elements
          // for which no labels are needed to be placed anymore as
          // finished. If we are still running three minutes after that, we
          // declare all elements as finished and drop all allocated
          // labels. This may lead to errors in the document (it should
          // not, but let's put effort into to make a consistent
          // example...).
          // However, we can ignore any example element that has not yet
          // been included, as this may at most leave some possible example
          // away, i.e., lead to a less comprehensive example. As I am not
          // sure (yes, I wrote the code some time ago) whether dropping
          // elements but not clearing the associated pending labels may
          // lead to deadlocks, I added the emergency label dropping
          // discussed above.

          ++emergencyFlushLabels;
          for (final _ERandomDocumentExampleElements element : _ERandomDocumentExampleElements.ELEMENTS) {

            drop = false;
            if (element.m_label != null) {
              labels = this.m_autoAllocatedLabels[element.m_label
                  .ordinal()];
              synchronized (labels) {
                drop = labels.isEmpty();
                if ((!drop) && ((emergencyFlushLabels > 180) && //
                    ((emergencyFlushLabels % _ERandomDocumentExampleElements.ELEMENTS.length) == //
                    element.ordinal()))) {
                  // The funny % part is to drop one label type at a time.
                  // Maybe this can reduce the number of potential may-be
                  // errors another little bit.
                  drop = true;
                  labels.clear();
                }
              }
            } else {
              drop = true;
            }

            if (drop) {
              this.m_done[element.ordinal()].set(true);
            }
          }

          this.__checkAllDone();
          done = true;
        }

        if (done) {
          switch (this.m_state) {
            case RUNNING: {
              this.m_state = _RandomDocumentExampleTermination.TERMINATING;
              break;
            }
            case TERMINATING: {
              break;
            }
            default: {
              this.m_state = _RandomDocumentExampleTermination.TERMINATED;
              return;
            }
          }
        }

        try {
          // The timeout may be negative if we have to wait for unused
          // pre-allocated labels - in this case, wait 1s.
          this.wait(Math.max(1000L, (this.m_end - time)));
        } catch (final InterruptedException inter) {
          this.m_state = _RandomDocumentExampleTermination.TERMINATED;
          return;
        }
      }
    }
  }
}
