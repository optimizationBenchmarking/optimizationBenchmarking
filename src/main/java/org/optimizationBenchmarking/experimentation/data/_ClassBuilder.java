package org.optimizationBenchmarking.experimentation.data;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;

import org.optimizationBenchmarking.utils.collections.iterators.InstanceIterator;
import org.optimizationBenchmarking.utils.collections.lists.ArrayListView;
import org.optimizationBenchmarking.utils.collections.lists.ArraySetView;
import org.optimizationBenchmarking.utils.collections.lists.BasicList;
import org.optimizationBenchmarking.utils.collections.lists.NumberList;
import org.optimizationBenchmarking.utils.collections.visitors.IVisitor;
import org.optimizationBenchmarking.utils.comparison.EComparison;
import org.optimizationBenchmarking.utils.compiler.CharSequenceJavaFileObject;
import org.optimizationBenchmarking.utils.compiler.JavaCompilerTask;
import org.optimizationBenchmarking.utils.hash.HashUtils;
import org.optimizationBenchmarking.utils.math.matrix.IMatrix;
import org.optimizationBenchmarking.utils.math.matrix.MatrixColumns;
import org.optimizationBenchmarking.utils.math.matrix.MatrixRowIterator;
import org.optimizationBenchmarking.utils.parsers.Parser;
import org.optimizationBenchmarking.utils.predicates.IPredicate;
import org.optimizationBenchmarking.utils.reflection.EPrimitiveType;
import org.optimizationBenchmarking.utils.tasks.Task;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;
import org.optimizationBenchmarking.utils.text.textOutput.MemoryTextOutput;

/**
 * An internal class for constructing the perfect classes for experimental
 * data elements on-the-fly.
 */
final class _ClassBuilder extends Task<Parser<DataPoint>> implements
    Comparator<Dimension> {

  /** the primitive types */
  private final EPrimitiveType[] m_primitiveTypes;

  /** the classes */
  private final Class<?>[] m_primitiveClasses;

  /** the parsers */
  private final Parser<?>[] m_parsers;

  /** the package */
  private final String m_package;

  /** the data point class */
  private final String m_dataPointClass;

  /** the parser class */
  private final String m_factoryClass;

  /** the run class */
  private final String m_runClass;

  /** the run columns class */
  private final String m_runColumnsClass;

  /** the run row iterator class for the columns selection */
  private final String m_runColumnsRowIteratorClass;

  /** the dimensions */
  private final ArraySetView<Dimension> m_dims;

  /** the matrix text */
  private String m_matrixTxt;

  /** the matrix choice */
  private Object m_matrixChoice;

  /**
   * create a new class builder
   * 
   * @param dimensions
   *          the dimensions
   */
  _ClassBuilder(final DimensionSet dimensions) {
    super();

    final String hc;
    final int l;
    final ArraySetView<Dimension> dims;
    Parser<?> parser;
    int i, hash;

    this.m_dims = (dims = dimensions.getData());
    l = dims.size();
    this.m_primitiveTypes = new EPrimitiveType[l];
    this.m_primitiveClasses = new Class<?>[l];
    this.m_parsers = new Parser<?>[l];
    hash = i = 0;
    for (final Dimension d : dims) {
      this.m_primitiveClasses[i] = (this.m_primitiveTypes[i] = d
          .getDataType()).getPrimitiveType();
      parser = d.getParser();
      hash = HashUtils.combineHashes(0, HashUtils.hashCode(parser));
      this.m_parsers[i] = parser;
      i++;
    }

    hc = Integer.toHexString(hash);

    this.m_package = ("_DynaDataPointPackage" + hc); //$NON-NLS-1$
    this.m_dataPointClass = ("_DynaDataPoint" + hc); //$NON-NLS-1$
    this.m_factoryClass = ("_DynaFactory" + hc); //$NON-NLS-1$
    this.m_runClass = ("_DynaRun" + hc); //$NON-NLS-1$
    this.m_runColumnsClass = ("_DynaRunColumns" + hc); //$NON-NLS-1$
    this.m_runColumnsRowIteratorClass = ("_DynaRunColumnsRowIterator" + hc); //$NON-NLS-1$

  }

  /**
   * create the data point class body
   * 
   * @return the data point class body
   */
  private final CharSequence __createDataPoint() {
    final MemoryTextOutput sb;

    sb = new MemoryTextOutput();
    sb.append("package ");//$NON-NLS-1$
    sb.append(this.m_package);
    sb.append(';');

    this.__dataPointClassHead(sb);
    this.__dataPointConstructor(sb);
    this.__dataPointClassGetters(sb);
    this.__dataPointToArray(sb);
    this.__dataPointToArray2(sb);
    this.__dataPointSize(sb);
    this.__dataPointToText(sb);
    this.__dataPointHashCode(sb);
    this.__dataPointEquals(sb);
    this.__dataPointCompareTo(sb);
    this.__dataPointValidateAfter(sb);
    this.__dataPointIMatrix(sb);
    this.__dataPointVisit(sb);
    this.__dataPointHasAny(sb);
    this.__dataPointSelect(sb);
    sb.append('}');

    return sb;
  }

  /**
   * create the data point head
   * 
   * @param sb
   *          the string builder
   */
  private final void __dataPointClassHead(final MemoryTextOutput sb) {
    int i;

    sb.append(//
    "@SuppressWarnings({\"unchecked\", \"rawtypes\", \"nls\", \"javadoc\"}) final class ");//$NON-NLS-1$
    sb.append(this.m_dataPointClass);
    sb.append(" extends ");//$NON-NLS-1$
    sb.append(DataPoint.class.getCanonicalName());
    sb.append('{');

    sb.append(// serial version uid
    "private static final long serialVersionUID = 1L;");//$NON-NLS-1$

    // declare fields: all private and final
    i = 0;
    for (final EPrimitiveType type : this.m_primitiveTypes) {
      sb.append("final ");//$NON-NLS-1$
      sb.append(type.getPrimitiveTypeName());
      sb.append(' ');
      sb.append('m');
      sb.append(i++);
      sb.append(';');
    }
  }

  /**
   * create the constructor of the data point
   * 
   * @param sb
   *          the string builder
   */
  private final void __dataPointConstructor(final MemoryTextOutput sb) {
    int i;

    sb.append(this.m_dataPointClass);
    sb.append('(');
    i = 0;
    for (final EPrimitiveType type : this.m_primitiveTypes) {
      if (i > 0) {
        sb.append(',');
      }
      sb.append(type.getPrimitiveTypeName());
      sb.append(' ');
      sb.append('p');
      sb.append(i++);
    }
    sb.append("){super();");//$NON-NLS-1$
    for (i = 0; i < this.m_primitiveTypes.length; i++) {
      sb.append("this.m");//$NON-NLS-1$
      sb.append(i);
      sb.append('=');
      sb.append('p');
      sb.append(i);
      sb.append(';');
    }
    sb.append('}');
  }

  /**
   * create the getters
   * 
   * @param sb
   *          the string builder
   */
  private final void __dataPointClassGetters(final MemoryTextOutput sb) {
    int i;
    String v;

    // the private error function called when invalid indexes are accessed

    // the primitive-type based getters
    for (final EPrimitiveType type : EPrimitiveType.TYPES) {
      if (type.isNumber()) {
        // getter
        sb.append("@Override public final ");//$NON-NLS-1$
        v = type.getPrimitiveTypeName();
        sb.append(v);
        sb.append(" get");//$NON-NLS-1$
        v = type.getPrimitiveTypeName();
        sb.append(Character.toUpperCase(v.charAt(0)));
        sb.append(v.substring(1));
        sb.append("(final int index) { switch(index) {");//$NON-NLS-1$

        i = 0;
        for (final EPrimitiveType type2 : this.m_primitiveTypes) {
          sb.append("case ");//$NON-NLS-1$
          sb.append(i);
          sb.append(": { return ");//$NON-NLS-1$
          if (!(type.isAssignableFrom(type2))) {
            sb.append('(');
            sb.append(type.getPrimitiveTypeName());
            sb.append(')');
          }
          sb.append('(');
          sb.append("this.m");//$NON-NLS-1$
          sb.append(i++);
          sb.append(");}");//$NON-NLS-1$
        }

        sb.append("default: {");//$NON-NLS-1$
        sb.append(//
        "throw new IndexOutOfBoundsException(\"Index \" + index ");//$NON-NLS-1$
        sb.append(//
        " + \" is out of the value range 0...");//$NON-NLS-1$
        sb.append(this.m_primitiveTypes.length - 1);
        sb.append(".\"); } } }");//$NON-NLS-1$
      }
    }

    // the wrapper-based getter
    sb.append("@Override public final ");//$NON-NLS-1$
    sb.append(Number.class.getCanonicalName());
    sb.append(//
    " get(final int index) { switch(index) {");//$NON-NLS-1$

    i = 0;
    for (final EPrimitiveType type2 : this.m_primitiveTypes) {
      sb.append("case ");//$NON-NLS-1$
      sb.append(i);
      sb.append(": { return ");//$NON-NLS-1$
      sb.append(type2.getWrapperClass().getCanonicalName());
      sb.append(".valueOf(this.m");//$NON-NLS-1$
      sb.append(i++);
      sb.append(");}");//$NON-NLS-1$
    }

    sb.append("default: {");//$NON-NLS-1$
    sb.append(//
    "throw new IndexOutOfBoundsException(\"Index \" + index ");//$NON-NLS-1$
    sb.append(//
    " + \" is out of the value range 0...");//$NON-NLS-1$
    sb.append(this.m_primitiveTypes.length - 1);
    sb.append(".\"); } } }");//$NON-NLS-1$
  }

  /**
   * create the to-Appendable method
   * 
   * @param sb
   *          the string builder
   */
  private final void __dataPointToText(final MemoryTextOutput sb) {
    int i, l;

    sb.append("@Override public final void toText(final ");//$NON-NLS-1$
    sb.append(ITextOutput.class.getCanonicalName());
    sb.append(" textOut) { textOut.append('[');");//$NON-NLS-1$

    l = this.m_primitiveTypes.length;
    for (i = 0; i < l; i++) {
      if (i > 0) {
        sb.append(" textOut.append(',');");//$NON-NLS-1$
        sb.append(" textOut.append(' ');");//$NON-NLS-1$
      }

      sb.append(" textOut.append(this.m");//$NON-NLS-1$
      sb.append(i);
      sb.append("); ");//$NON-NLS-1$
    }

    sb.append(" textOut.append(']'); }");//$NON-NLS-1$
  }

  // /**
  // * create the to-String method
  // *
  // * @param sb
  // * the string builder
  // */
  // private final void __dataPointToString(final MemoryTextOutput sb) {
  // int i;
  //
  // sb.append(//
  //    "@Override public final String toString() { return \"[\" + ");//$NON-NLS-1$
  //
  // for (i = 0; i < this.m_primitiveTypes.length; i++) {
  // if (i > 0) {
  //        sb.append(" ',' + ' ' + ");//$NON-NLS-1$
  // }
  //
  //      sb.append("this.m");//$NON-NLS-1$
  // sb.append(i);
  // sb.append('+');
  // }
  //
  //    sb.append("']'; }");//$NON-NLS-1$
  // }

  /**
   * create the hashCode method
   * 
   * @param sb
   *          the string builder
   */
  private final void __dataPointHashCode(final MemoryTextOutput sb) {
    int i, z;

    sb.append(//
    "@Override public final int hashCode() { return ");//$NON-NLS-1$

    z = (this.m_primitiveTypes.length - 1);
    for (i = 0; i <= z; i++) {
      if (i > 0) {
        sb.append(", ");//$NON-NLS-1$
      }
      if (i < z) {
        sb.append(HashUtils.class.getCanonicalName());
        sb.append(".combineHashes(");//$NON-NLS-1$
      }

      sb.append(HashUtils.class.getCanonicalName());
      sb.append(".hashCode(this.m");//$NON-NLS-1$
      sb.append(z - i);
      sb.append(')');
    }

    for (i = 0; i < z; i++) {
      sb.append(')');
    }
    sb.append("; }");//$NON-NLS-1$
  }

  /**
   * create the hash equals method
   * 
   * @param sb
   *          the string builder
   */
  private final void __dataPointEquals(final MemoryTextOutput sb) {
    String v;
    int i;

    sb.append(//
    "@Override public final boolean equals(final Object o) { ");//$NON-NLS-1$

    sb.append(//
    "if(o == this) { return true; } ");//$NON-NLS-1$

    sb.append("if(o instanceof ");//$NON-NLS-1$
    sb.append(this.m_dataPointClass);
    sb.append(") { final ");//$NON-NLS-1$
    sb.append(this.m_dataPointClass);
    sb.append(" d = ((");//$NON-NLS-1$
    sb.append(this.m_dataPointClass);
    sb.append(") o); return (");//$NON-NLS-1$

    i = 0;
    for (final EPrimitiveType t : this.m_primitiveTypes) {
      if (i > 0) {
        sb.append(" && "); //$NON-NLS-1$
      }
      if (t.isFloat()) {
        sb.append('(');
        sb.append(EComparison.class.getCanonicalName());
        sb.append(".compare");//$NON-NLS-1$
        v = t.getPrimitiveTypeName();
        sb.append(Character.toUpperCase(v.charAt(0)));
        sb.append(v.substring(1));
        sb.append("s(this.m");//$NON-NLS-1$
        sb.append(i);
        sb.append(",d.m");//$NON-NLS-1$
        sb.append(i);
        sb.append(")==0)");//$NON-NLS-1$
      } else {
        sb.append("(this.m");//$NON-NLS-1$
        sb.append(i);
        sb.append("==d.m");//$NON-NLS-1$
        sb.append(i);
        sb.append(')');
      }
      i++;
    }
    sb.append("); }");//$NON-NLS-1$

    sb.append("if(o instanceof ");//$NON-NLS-1$
    sb.append(NumberList.class.getCanonicalName());
    sb.append(") { final ");//$NON-NLS-1$
    sb.append(NumberList.class.getCanonicalName());
    sb.append(" f = ((");//$NON-NLS-1$
    sb.append(NumberList.class.getCanonicalName());
    sb.append(") o); return (");//$NON-NLS-1$

    i = 0;
    for (final EPrimitiveType t : this.m_primitiveTypes) {
      if (i > 0) {
        sb.append(" && "); //$NON-NLS-1$
      }
      v = t.getPrimitiveTypeName();
      if (t.isFloat()) {
        sb.append('(');
        sb.append(EComparison.class.getCanonicalName());
        sb.append(".compare");//$NON-NLS-1$
        sb.append(Character.toUpperCase(v.charAt(0)));
        sb.append(v.substring(1));
        sb.append("s(this.m");//$NON-NLS-1$
        sb.append(i);
        sb.append(",f.get");//$NON-NLS-1$
        sb.append(Character.toUpperCase(v.charAt(0)));
        sb.append(v.substring(1));
        sb.append('(');
        sb.append(i);
        sb.append("))==0)");//$NON-NLS-1$
      } else {
        sb.append("(this.m");//$NON-NLS-1$
        sb.append(i);
        sb.append("==");//$NON-NLS-1$
        sb.append("f.get");//$NON-NLS-1$
        sb.append(Character.toUpperCase(v.charAt(0)));
        sb.append(v.substring(1));
        sb.append('(');
        sb.append(i);
        sb.append(')');
        sb.append(')');
      }
      i++;
    }
    sb.append("); }");//$NON-NLS-1$

    sb.append("return super.equals(o); }");//$NON-NLS-1$
  }

  /**
   * create the size method
   * 
   * @param sb
   *          the string builder
   */
  private final void __dataPointSize(final MemoryTextOutput sb) {
    sb.append("@Override public final int size() { return ");//$NON-NLS-1$
    sb.append(this.m_parsers.length);
    sb.append(';');
    sb.append('}');
  }

  /**
   * create the compareTo method
   * 
   * @param sb
   *          the string builder
   */
  private final void __dataPointCompareTo(final MemoryTextOutput sb) {
    final Dimension[] dims;
    final HashSet<String> hs;
    boolean isIncreasing;
    String v;

    dims = this.m_dims.toArray(new Dimension[this.m_dims.size()]);
    Arrays.sort(dims, this);

    sb.append(//
    "@Override public final int compareTo(final ");//$NON-NLS-1$
    sb.append(DataPoint.class.getCanonicalName());
    sb.append(" o) {if(o == this) {return 0;} ");//$NON-NLS-1$

    hs = new HashSet<>();

    sb.append("if(o instanceof ");//$NON-NLS-1$
    sb.append(this.m_dataPointClass);
    sb.append(") {final ");//$NON-NLS-1$
    sb.append(this.m_dataPointClass);
    sb.append(" c = ((");//$NON-NLS-1$
    sb.append(this.m_dataPointClass);
    sb.append(")o);");//$NON-NLS-1$

    for (final Dimension dim : dims) {
      hs.add(dim.getDataType().getPrimitiveTypeName());
      sb.append("if(this.m");//$NON-NLS-1$
      sb.append(dim.getIndex());
      sb.append("<c.m");//$NON-NLS-1$
      sb.append(dim.getIndex());
      sb.append(") { return (");//$NON-NLS-1$
      isIncreasing = dim.getDirection().isIncreasing();
      if (isIncreasing) {
        sb.append('-');
      }
      sb.append("1); }");//$NON-NLS-1$

      sb.append("if(this.m");//$NON-NLS-1$
      sb.append(dim.getIndex());
      sb.append(">c.m");//$NON-NLS-1$
      sb.append(dim.getIndex());
      sb.append(") { return (");//$NON-NLS-1$
      if (!(isIncreasing)) {
        sb.append('-');
      }
      sb.append("1); }");//$NON-NLS-1$
    }
    sb.append("return 0;}");//$NON-NLS-1$

    for (final String ss : hs) {
      sb.append(ss);
      sb.append(" p");//$NON-NLS-1$
      sb.append(ss);
      sb.append(';');
    }

    for (final Dimension dim : dims) {
      v = dim.getDataType().getPrimitiveTypeName();
      sb.append('p');
      sb.append(v);
      sb.append("=o.get");//$NON-NLS-1$
      sb.append(Character.toUpperCase(v.charAt(0)));
      sb.append(v.substring(1));
      sb.append('(');
      sb.append(dim.getIndex());
      sb.append(')');
      sb.append(';');

      sb.append("if(this.m");//$NON-NLS-1$
      sb.append(dim.getIndex());
      sb.append("<p");//$NON-NLS-1$
      sb.append(v);
      sb.append(") { return (");//$NON-NLS-1$
      isIncreasing = dim.getDirection().isIncreasing();
      if (isIncreasing) {
        sb.append('-');
      }
      sb.append("1); }");//$NON-NLS-1$

      sb.append("if(this.m");//$NON-NLS-1$
      sb.append(dim.getIndex());
      sb.append(">p");//$NON-NLS-1$
      sb.append(v);
      sb.append(") { return (");//$NON-NLS-1$
      if (!(isIncreasing)) {
        sb.append('-');
      }
      sb.append("1); }");//$NON-NLS-1$
    }

    sb.append("return 0;}");//$NON-NLS-1$
  }

  /**
   * create the validateAfter method
   * 
   * @param sb
   *          the string builder
   */
  private final void __dataPointValidateAfter(final MemoryTextOutput sb) {
    EDimensionDirection dir;

    sb.append(//
    "@Override public final void validateAfter(final ");//$NON-NLS-1$
    sb.append(DataPoint.class.getCanonicalName());
    sb.append(" before) {boolean later = false; String reason = null; final ");//$NON-NLS-1$
    sb.append(this.m_dataPointClass);
    sb.append(" c = ((");//$NON-NLS-1$
    sb.append(this.m_dataPointClass);
    sb.append(") before); ok: { ");//$NON-NLS-1$

    for (final Dimension d : this.m_dims) {
      sb.append("if(this.m");//$NON-NLS-1$
      sb.append(d.getIndex());
      dir = d.getDirection();
      sb.append(dir.isIncreasing() ? '<' : '>');
      if (dir.isStrict()) {
        sb.append('=');
      }
      sb.append("c.m");//$NON-NLS-1$
      sb.append(d.getIndex());
      sb.append(") { reason = ((\"Value at dimension ");//$NON-NLS-1$
      sb.append(d.getIndex());
      sb.append(" of this data point must be ");//$NON-NLS-1$

      sb.append(dir.isIncreasing() ? "greater" : //$NON-NLS-1$
          "less");//$NON-NLS-1$
      if (!(dir.isStrict())) {
        sb.append(" or equal");//$NON-NLS-1$
      }

      sb.append(//
      " than the value of the same dimension in the previous data point, but \" + this.m");//$NON-NLS-1$
      sb.append(d.getIndex());
      sb.append(" + \"");//$NON-NLS-1$
      sb.append(dir.isIncreasing() ? '<' : '>');
      if (dir.isStrict()) {
        sb.append('=');
      }
      sb.append("\" + c.m");//$NON-NLS-1$
      sb.append(d.getIndex());
      sb.append(") + '.');");//$NON-NLS-1$
      sb.append(" break ok; }");//$NON-NLS-1$
      sb.append("later |= (this.m");//$NON-NLS-1$
      sb.append(d.getIndex());
      sb.append(dir.isIncreasing() ? '>' : '<');
      sb.append("c.m");//$NON-NLS-1$
      sb.append(d.getIndex());
      sb.append(");");//$NON-NLS-1$
    }

    sb.append(//
    " if(later) { return; } reason = \"The data points must differ in at least one dimension.\";}");//$NON-NLS-1$
    sb.append(//
    "throw new IllegalStateException(\" Data point \" + ");//$NON-NLS-1$
    sb.append(//
    "this.toString() + \" cannot follow the data point \" + ");//$NON-NLS-1$
    sb.append("c.toString() + \": \" + reason); }");//$NON-NLS-1$
  }

  /**
   * make the basic matrix stuff
   */
  private final void __makeBasicIMatrix() {
    final ArrayList<Integer> ints, floats;
    final MemoryTextOutput sb;
    String s;
    int i;

    sb = new MemoryTextOutput();
    sb.append(//
    "@Override public final int n() { return ");//$NON-NLS-1$
    sb.append(this.m_parsers.length);
    sb.append(';');
    sb.append('}');

    ints = new ArrayList<>();
    floats = new ArrayList<>();

    i = 0;
    for (final EPrimitiveType p : this.m_primitiveTypes) {
      if (p.isFloat()) {
        floats.add(Integer.valueOf(i));
      } else {
        ints.add(Integer.valueOf(i));
      }
      i++;
    }

    if (floats.size() <= 0) {
      sb.append(//
      "@Override public final boolean isIntegerMatrix() { return true; }");//$NON-NLS-1$
      this.m_matrixChoice = Boolean.TRUE;
    } else {
      if (ints.size() <= 0) {
        this.m_matrixChoice = Boolean.FALSE;
      } else {

        s = ""; //$NON-NLS-1$
        if (ints.size() <= floats.size()) {
          for (final Integer j : ints) {
            if (s.length() > 0) {
              s = s + "||";} //$NON-NLS-1$
            s += ("(@==" + j.intValue()) + ')'; //$NON-NLS-1$
          }
        } else {
          for (final Integer j : floats) {
            if (s.length() > 0) {
              s = s + "&&";} //$NON-NLS-1$
            s += ("(@!=" + j.intValue()) + ')'; //$NON-NLS-1$
          }
        }

        this.m_matrixChoice = ('(' + s + ')');
      }
    }

    this.m_matrixTxt = sb.toString();
  }

  /**
   * create the matrix access methods
   * 
   * @param sb
   *          the string builder
   */
  private final void __dataPointIMatrix(final MemoryTextOutput sb) {
    int i;
    String s;

    sb.append(this.m_matrixTxt);

    for (final EPrimitiveType pt : new EPrimitiveType[] {
        EPrimitiveType.DOUBLE, EPrimitiveType.LONG }) {
      sb.append(//
      "@Override public final ");//$NON-NLS-1$
      sb.append(s = pt.getPrimitiveTypeName());
      sb.append(" get");//$NON-NLS-1$
      sb.append(Character.toUpperCase(s.charAt(0)));
      sb.append(s.substring(1));
      sb.append("(final int row, final int column) {");//$NON-NLS-1$
      sb.append(//
      "if(row == 0) { switch(column) {");//$NON-NLS-1$

      for (i = 0; i < this.m_primitiveTypes.length; i++) {
        sb.append("case ");//$NON-NLS-1$
        sb.append(i);
        sb.append(": { return (");//$NON-NLS-1$
        if (pt.isInteger() && this.m_primitiveTypes[i].isFloat()) {
          sb.append('(');
          sb.append(s);
          sb.append(')');
        }

        sb.append(" this.m");//$NON-NLS-1$
        sb.append(i);
        sb.append(");}");//$NON-NLS-1$
      }

      sb.append(//
      "} } throw new IndexOutOfBoundsException(((\"Matrix access get");//$NON-NLS-1$
      sb.append(Character.toUpperCase(s.charAt(0)));
      sb.append(s.substring(1));
      sb.append("(\" + row) + ");//$NON-NLS-1$
      sb.append(//
      "',') + column + \") is invalid, the valid index range is (0, 0..");//$NON-NLS-1$
      sb.append(this.m_primitiveTypes.length - 1);
      sb.append(").\"); }");//$NON-NLS-1$
    }
  }

  /**
   * create the to-array method
   * 
   * @param sb
   *          the string builder
   */
  private final void __dataPointToArray(final MemoryTextOutput sb) {
    int i;

    for (final EPrimitiveType pt : new EPrimitiveType[] {
        EPrimitiveType.DOUBLE, EPrimitiveType.INT, EPrimitiveType.LONG }) {
      sb.append("@Override public final void toArray(");//$NON-NLS-1$
      sb.append(pt.getPrimitiveTypeName());
      sb.append("[] dest, final int destStart) {");//$NON-NLS-1$
      i = 0;
      for (final EPrimitiveType type : this.m_primitiveTypes) {
        sb.append("dest[destStart");//$NON-NLS-1$
        if (i > 0) {
          sb.append('+');
          sb.append(i);
        }
        sb.append("] = ");//$NON-NLS-1$

        if (!(pt.isAssignableFrom(type))) {
          sb.append('(');
          sb.append(pt.getPrimitiveTypeName());
          sb.append(')');
        }
        sb.append("this.m");//$NON-NLS-1$
        sb.append(i++);
        sb.append(';');
      }
      sb.append('}');
    }
  }

  /**
   * create the to-array method
   * 
   * @param sb
   *          the string builder
   */
  @SuppressWarnings({ "unchecked", "rawtypes" })
  private final void __dataPointToArray2(final MemoryTextOutput sb) {
    int i;
    EPrimitiveType t1;
    Class<? extends Number> clazz;

    t1 = this.m_primitiveTypes[0];
    for (final EPrimitiveType t2 : this.m_primitiveTypes) {
      if (t1 != t2) {
        t1 = null;
        break;
      }
    }

    if (t1 != null) {
      clazz = ((Class) (t1.getWrapperClass()));
    } else {
      clazz = Number.class;
    }

    sb.append("@Override public final ");//$NON-NLS-1$
    sb.append(clazz.getCanonicalName());
    sb.append("[] toArray() { final ");//$NON-NLS-1$
    sb.append(clazz.getCanonicalName());
    sb.append("[] dest = new ");//$NON-NLS-1$
    sb.append(clazz.getCanonicalName());
    sb.append('[');
    sb.append(this.m_primitiveTypes.length);
    sb.append("];");//$NON-NLS-1$

    i = 0;
    for (final EPrimitiveType type : this.m_primitiveTypes) {
      sb.append("dest[");//$NON-NLS-1$
      sb.append(i);
      sb.append("] = ");//$NON-NLS-1$
      sb.append(type.getWrapperClass().getCanonicalName());
      sb.append(".valueOf(this.m");//$NON-NLS-1$
      sb.append(i);
      sb.append(");");//$NON-NLS-1$
      i++;
    }

    sb.append("return dest; }");//$NON-NLS-1$

    sb.append("public <T> T[] toArray(final T[] a) { final Object[] dest; ");//$NON-NLS-1$
    sb.append("if(a==null) { return ((T[])(this.toArray())); }");//$NON-NLS-1$
    sb.append("if(a.length>=");//$NON-NLS-1$
    sb.append(this.m_primitiveTypes.length);
    sb.append(") { if(a.length>");//$NON-NLS-1$
    sb.append(this.m_primitiveTypes.length);
    sb.append(") { a[");//$NON-NLS-1$
    sb.append(this.m_primitiveTypes.length);
    sb.append("] = null; } dest = a; } else {");//$NON-NLS-1$

    i = 1;
    if (clazz != Number.class) {
      sb.append("if(a instanceof ");//$NON-NLS-1$
      sb.append(clazz.getCanonicalName());
      sb.append("[]) { dest = new ");//$NON-NLS-1$
      sb.append(clazz.getCanonicalName());
      sb.append('[');
      sb.append(this.m_primitiveTypes.length);
      sb.append("]; } else {");//$NON-NLS-1$
      i++;
    }

    sb.append("if(a instanceof ");//$NON-NLS-1$
    sb.append(Number.class.getCanonicalName());
    sb.append("[]) { dest = new ");//$NON-NLS-1$
    sb.append(Number.class.getCanonicalName());
    sb.append('[');
    sb.append(this.m_primitiveTypes.length);
    sb.append("]; } else {");//$NON-NLS-1$
    i++;

    sb.append("dest = ((T[]) (");//$NON-NLS-1$
    sb.append(Array.class.getCanonicalName());
    sb.append(".newInstance(a.getClass().getComponentType(), ");//$NON-NLS-1$
    sb.append(this.m_primitiveTypes.length);
    sb.append(")));");//$NON-NLS-1$

    for (; (--i) >= 0;) {
      sb.append('}');
    }

    i = 0;
    for (final EPrimitiveType type : this.m_primitiveTypes) {
      sb.append("dest[");//$NON-NLS-1$
      sb.append(i);
      sb.append("] = ");//$NON-NLS-1$
      sb.append(type.getWrapperClass().getCanonicalName());
      sb.append(".valueOf(this.m");//$NON-NLS-1$
      sb.append(i);
      sb.append(");");//$NON-NLS-1$
      i++;
    }
    sb.append("return ((T[])dest); }");//$NON-NLS-1$

  }

  /**
   * create the visit method
   * 
   * @param sb
   *          the string builder
   */
  private final void __dataPointVisit(final MemoryTextOutput sb) {
    int i;

    sb.append(//
    "@Override public final boolean visit(final ");//$NON-NLS-1$
    sb.append(IVisitor.class.getCanonicalName());
    sb.append("<? super ");//$NON-NLS-1$
    sb.append(Number.class.getCanonicalName());
    sb.append("> visitor) {");//$NON-NLS-1$

    i = 0;
    for (final EPrimitiveType t : this.m_primitiveTypes) {
      if (i >= (this.m_primitiveTypes.length - 1)) {
        sb.append("return ");//$NON-NLS-1$
      } else {
        sb.append("if(!(");//$NON-NLS-1$
      }

      sb.append("visitor.visit(");//$NON-NLS-1$
      sb.append(t.getWrapperClass().getCanonicalName());
      sb.append(".valueOf(this.m");//$NON-NLS-1$
      sb.append(i++);

      if (i >= (this.m_primitiveTypes.length)) {
        sb.append("));");//$NON-NLS-1$
      } else {
        sb.append(")))) { return false; }");//$NON-NLS-1$
      }
    }

    sb.append("}");//$NON-NLS-1$
  }

  /**
   * create the has any method
   * 
   * @param sb
   *          the string builder
   */
  private final void __dataPointHasAny(final MemoryTextOutput sb) {
    int i;

    sb.append(//
    "@Override public final boolean hasAny(final ");//$NON-NLS-1$
    sb.append(IPredicate.class.getCanonicalName());
    sb.append("<? super ");//$NON-NLS-1$
    sb.append(Number.class.getCanonicalName());
    sb.append("> predicate) { return (");//$NON-NLS-1$

    i = 0;
    for (final EPrimitiveType t : this.m_primitiveTypes) {
      if (i > 0) {
        sb.append("||");//$NON-NLS-1$
      }
      sb.append("(predicate.check(");//$NON-NLS-1$
      sb.append(t.getWrapperClass().getCanonicalName());
      sb.append(".valueOf(this.m");//$NON-NLS-1$
      sb.append(i++);
      sb.append(")))");//$NON-NLS-1$
    }
    sb.append("); } ");//$NON-NLS-1$
  }

  /**
   * create the select method
   * 
   * @param sb
   *          the string builder
   */
  @SuppressWarnings({ "unchecked", "rawtypes" })
  private final void __dataPointSelect(final MemoryTextOutput sb) {
    int i;
    EPrimitiveType t1;
    Class<? extends Number> clazz;

    t1 = this.m_primitiveTypes[0];
    for (final EPrimitiveType t2 : this.m_primitiveTypes) {
      if (t1 != t2) {
        t1 = null;
        break;
      }
    }

    if (t1 != null) {
      clazz = ((Class) (t1.getWrapperClass()));
    } else {
      clazz = Number.class;
    }

    sb.append("@Override public final ");//$NON-NLS-1$
    sb.append(BasicList.class.getCanonicalName());
    sb.append('<');
    sb.append(Number.class.getCanonicalName());
    sb.append("> select(final ");//$NON-NLS-1$
    sb.append(IPredicate.class.getCanonicalName());
    sb.append("<? super ");//$NON-NLS-1$
    sb.append(Number.class.getCanonicalName());
    sb.append("> predicate) { ");//$NON-NLS-1$

    if (this.m_primitiveTypes.length <= 0) {
      sb.append("return this;");//$NON-NLS-1$
    } else {
      sb.append(clazz.getCanonicalName());
      sb.append(" val; ");//$NON-NLS-1$
      sb.append(clazz.getCanonicalName());
      sb.append("[] dest = new ");//$NON-NLS-1$
      sb.append(clazz.getCanonicalName());
      sb.append('[');
      sb.append(this.m_primitiveTypes.length - 1);
      sb.append("]; int i = 0;");//$NON-NLS-1$

      i = 0;
      for (final EPrimitiveType t : this.m_primitiveTypes) {
        sb.append("val=");//$NON-NLS-1$
        sb.append(t.getWrapperClass().getCanonicalName());
        sb.append(".valueOf(this.m");//$NON-NLS-1$
        sb.append(i++);
        sb.append("); if(predicate.check(val)) {");//$NON-NLS-1$

        if (i >= this.m_primitiveTypes.length) {
          sb.append("if(i>=");//$NON-NLS-1$
          sb.append(i - 1);
          sb.append(") { return this; }");//$NON-NLS-1$
        }
        sb.append("dest[i++] = val;}");//$NON-NLS-1$
      }

      sb.append("if(i<");//$NON-NLS-1$
      sb.append(this.m_primitiveTypes.length - 1);
      sb.append(") {");//$NON-NLS-1$
      sb.append(clazz.getCanonicalName());
      sb.append("[] tmp = dest; dest = new ");//$NON-NLS-1$
      sb.append(clazz.getCanonicalName());
      sb.append("[i]; System.arraycopy(tmp, 0, dest, 0, i); } return new ");//$NON-NLS-1$
      sb.append(ArrayListView.class.getCanonicalName());
      sb.append("(dest);");//$NON-NLS-1$
    }

    sb.append('}');
  }

  /**
   * create the data point parser
   * 
   * @return the data point parser
   */
  private final CharSequence __createFactory() {
    final MemoryTextOutput sb;

    sb = new MemoryTextOutput();
    sb.append("package ");//$NON-NLS-1$
    sb.append(this.m_package);
    sb.append(';');

    this.__factoryClassHead(sb);
    this.__factoryConstructor(sb);
    this.__factoryParseString(sb);
    this.__factoryParseNumbers(sb);
    this.__factoryParseObject(sb);
    this.__factoryParseGetOutputClass(sb);
    this.__factoryCreateRun(sb);
    sb.append('}');

    return sb;
  }

  /**
   * create the data point parser head
   * 
   * @param sb
   *          the string parser builder
   */
  private final void __factoryClassHead(final MemoryTextOutput sb) {
    int i;

    sb.append(//
    "@SuppressWarnings({\"unchecked\", \"rawtypes\", \"nls\", \"javadoc\"}) public final class ");//$NON-NLS-1$
    sb.append(this.m_factoryClass);
    sb.append(" extends ");//$NON-NLS-1$
    sb.append(DataFactory.class.getCanonicalName());
    sb.append('{');

    sb.append(// serial version uid
    "private static final long serialVersionUID = 1L;");//$NON-NLS-1$

    // declare fields: all private and final
    i = 0;
    for (final Parser<?> parser : this.m_parsers) {
      sb.append("private final ");//$NON-NLS-1$
      sb.append(parser.getClass().getCanonicalName());
      sb.append(' ');
      sb.append('m');
      sb.append(i++);
      sb.append(';');
    }
  }

  /**
   * create the constructor of the parser
   * 
   * @param sb
   *          the string builder
   */
  private final void __factoryConstructor(final MemoryTextOutput sb) {
    int i;

    sb.append("public ");//$NON-NLS-1$
    sb.append(this.m_factoryClass);
    sb.append('(');

    i = 0;
    for (final Parser<?> parser : this.m_parsers) {
      if (i > 0) {
        sb.append(',');
      }
      sb.append(parser.getClass().getCanonicalName());
      sb.append(' ');
      sb.append('p');
      sb.append(i++);
    }
    sb.append("){super();");//$NON-NLS-1$
    for (i = 0; i < this.m_parsers.length; i++) {
      sb.append("this.m");//$NON-NLS-1$
      sb.append(i);
      sb.append('=');
      sb.append('p');
      sb.append(i);
      sb.append(';');
    }
    sb.append('}');
  }

  /**
   * create the data point parser string
   * 
   * @param sb
   *          the string parser builder
   */
  private final void __factoryParseString(final MemoryTextOutput sb) {
    int index;
    String v;
    char ch;

    sb.append("@Override public final ");//$NON-NLS-1$
    sb.append(this.m_dataPointClass);
    sb.append(" parseString(final String s) {");//$NON-NLS-1$

    index = 0;
    for (final EPrimitiveType type : this.m_primitiveTypes) {
      sb.append("final ");//$NON-NLS-1$
      sb.append(type.getPrimitiveTypeName());
      sb.append(' ');
      sb.append('l');
      sb.append(index++);
      sb.append(';');
    }
    sb.append("final int length = s.length();");//$NON-NLS-1$
    sb.append("int i;");//$NON-NLS-1$
    sb.append("int j = 0;");//$NON-NLS-1$

    for (index = 0; index < this.m_primitiveTypes.length; index++) {
      sb.append("i = j;");//$NON-NLS-1$

      sb.append("while(s.charAt(i) <= ' ') { i++; }");//$NON-NLS-1$
      if (index <= 0) {
        sb.append("if(s.charAt(i) == '[') { i++; }");//$NON-NLS-1$
      }

      sb.append("j = i;");//$NON-NLS-1$
      sb.append("inner");//$NON-NLS-1$
      sb.append(index);
      sb.append(": for (j = i; j < length; j++) {");//$NON-NLS-1$
      sb.append("if (s.charAt(j) <= ' ') { break inner");//$NON-NLS-1$
      sb.append(index);
      sb.append("; } }");//$NON-NLS-1$

      sb.append('l');
      sb.append(index);
      sb.append('=');
      sb.append("this.m"); //$NON-NLS-1$
      sb.append(index);
      sb.append(".parse"); //$NON-NLS-1$
      v = this.m_primitiveTypes[index].getPrimitiveTypeName();
      sb.append(Character.toUpperCase(v.charAt(0)));
      sb.append(v.substring(1));
      sb.append("(s.substring(i, ((s.charAt(j-1)=='"); //$NON-NLS-1$
      if (index < (this.m_primitiveTypes.length - 1)) {
        ch = ',';
      } else {
        ch = ']';
      }
      sb.append(ch);
      sb.append("')?(j-1):j)));"); //$NON-NLS-1$
    }

    sb.append("return new "); //$NON-NLS-1$
    sb.append(this.m_dataPointClass);
    ch = '(';
    for (index = 0; index < this.m_primitiveTypes.length; index++) {
      sb.append(ch);
      sb.append('l');
      sb.append(index);
      ch = ',';
    }
    sb.append(')');
    sb.append(';');
    sb.append('}');
  }

  /**
   * create the data point parser numbers
   * 
   * @param sb
   *          the string parser builder
   */
  private final void __factoryParseNumbers(final MemoryTextOutput sb) {
    int index;
    String v;
    char ch;

    sb.append("@Override public final ");//$NON-NLS-1$
    sb.append(this.m_dataPointClass);
    sb.append(" parseNumbers(final ");//$NON-NLS-1$
    sb.append(Number.class.getCanonicalName());
    sb.append("... numbers) {");//$NON-NLS-1$

    index = 0;
    for (final EPrimitiveType type : this.m_primitiveTypes) {
      sb.append("final ");//$NON-NLS-1$
      sb.append(type.getPrimitiveTypeName());
      sb.append(' ');
      sb.append('l');
      sb.append(index);
      sb.append("=numbers[");//$NON-NLS-1$
      sb.append(index);
      sb.append("].");//$NON-NLS-1$
      v = type.getPrimitiveTypeName();
      sb.append(v);
      sb.append("Value();");//$NON-NLS-1$
      sb.append("this.m");//$NON-NLS-1$
      sb.append(index);
      sb.append(".validate");//$NON-NLS-1$
      sb.append(Character.toUpperCase(v.charAt(0)));
      sb.append(v.substring(1));
      sb.append('(');
      sb.append('l');
      sb.append(index);
      sb.append(')');
      sb.append(';');
      index++;
    }
    sb.append("return new "); //$NON-NLS-1$
    sb.append(this.m_dataPointClass);
    ch = '(';
    for (index = 0; index < this.m_primitiveTypes.length; index++) {
      sb.append(ch);
      sb.append('l');
      sb.append(index);
      ch = ',';
    }
    sb.append(')');
    sb.append(';');
    sb.append('}');
  }

  /**
   * create the data point parser object
   * 
   * @param sb
   *          the string parser builder
   */
  private final void __factoryParseObject(final MemoryTextOutput sb) {
    sb.append("@Override public final ");//$NON-NLS-1$
    sb.append(this.m_dataPointClass);
    sb.append(" parseObject(final Object o) {");//$NON-NLS-1$

    sb.append("if(o instanceof ");//$NON-NLS-1$
    sb.append(this.m_dataPointClass);
    sb.append(") { return ((");//$NON-NLS-1$
    sb.append(this.m_dataPointClass);
    sb.append(") o); } ");//$NON-NLS-1$

    sb.append("if(o instanceof ");//$NON-NLS-1$
    sb.append(Number.class.getCanonicalName());
    sb.append("[]) { return this.parseNumbers((");//$NON-NLS-1$
    sb.append(Number.class.getCanonicalName());
    sb.append("[])o); }");//$NON-NLS-1$

    sb.append("return this.parseString(String.valueOf(o)); } ");//$NON-NLS-1$
  }

  /**
   * create the data point parser string
   * 
   * @param sb
   *          the string parser builder
   */
  private final void __factoryParseGetOutputClass(final MemoryTextOutput sb) {
    sb.append("@Override public final Class<");//$NON-NLS-1$
    sb.append(DataPoint.class.getCanonicalName());
    sb.append("> getOutputClass() { return ((Class)(");//$NON-NLS-1$
    sb.append(this.m_dataPointClass);
    sb.append(".class)); }");//$NON-NLS-1$
  }

  /**
   * create the run
   * 
   * @param sb
   *          the string parser builder
   */
  private final void __factoryCreateRun(final MemoryTextOutput sb) {
    sb.append(//
    "@Override public final ");//$NON-NLS-1$
    sb.append(Run.class.getCanonicalName());
    sb.append(" createRun(final "); //$NON-NLS-1$
    sb.append(Instance.class.getCanonicalName());
    sb.append(" instance, final "); //$NON-NLS-1$
    sb.append(Collection.class.getCanonicalName());
    sb.append('<');
    sb.append(DataPoint.class.getCanonicalName());
    sb.append("> points) { final ");//$NON-NLS-1$
    sb.append(this.m_dataPointClass);
    sb.append("[] data = points.toArray(new ");//$NON-NLS-1$
    sb.append(this.m_dataPointClass);
    sb.append("[points.size()]); for(");//$NON-NLS-1$
    sb.append(this.m_dataPointClass);
    sb.append(//
    " next : data) { instance.validateDataPoint(next); } return new ");//$NON-NLS-1$
    sb.append(this.m_runClass);
    sb.append("(data); }");//$NON-NLS-1$
  }

  /**
   * create the run class
   * 
   * @return the run class
   */
  private final CharSequence __createRun() {
    final MemoryTextOutput sb;

    sb = new MemoryTextOutput();
    sb.append("package ");//$NON-NLS-1$
    sb.append(this.m_package);
    sb.append(';');

    this.__runClassHead(sb);
    this.__runConstructor(sb);
    this.__runIMatrix(sb);
    this.__runToText(sb);
    this.__runFind(sb);
    sb.append('}');

    return sb;
  }

  /**
   * create the run head
   * 
   * @param sb
   *          the string parser builder
   */
  private final void __runClassHead(final MemoryTextOutput sb) {

    sb.append(//
    "@SuppressWarnings({\"unchecked\", \"rawtypes\", \"nls\", \"javadoc\"}) final class ");//$NON-NLS-1$
    sb.append(this.m_runClass);
    sb.append(" extends ");//$NON-NLS-1$
    sb.append(Run.class.getCanonicalName());
    sb.append('{');

    sb.append(// serial version uid
    "private static final long serialVersionUID = 1L;");//$NON-NLS-1$

    sb.append("final ");//$NON-NLS-1$
    sb.append(this.m_dataPointClass);
    sb.append("[] points;");//$NON-NLS-1$
  }

  /**
   * create the constructor of the run
   * 
   * @param sb
   *          the string builder
   */
  private final void __runConstructor(final MemoryTextOutput sb) {
    sb.append(this.m_runClass);
    sb.append("(final ");//$NON-NLS-1$
    sb.append(this.m_dataPointClass);
    sb.append("[] data) { super(data); this.points = data; }");//$NON-NLS-1$
  }

  /**
   * create the matrix access methods for runs
   * 
   * @param sb
   *          the string builder
   */
  private final void __runIMatrix(final MemoryTextOutput sb) {
    int i;
    String s;

    sb.append(//
    "@Override public final int m() { return this.points.length; }");//$NON-NLS-1$

    sb.append(this.m_matrixTxt);

    for (final EPrimitiveType pt : new EPrimitiveType[] {
        EPrimitiveType.DOUBLE, EPrimitiveType.LONG }) {
      sb.append(//
      "@Override public final ");//$NON-NLS-1$
      sb.append(s = pt.getPrimitiveTypeName());
      sb.append(" get");//$NON-NLS-1$
      sb.append(Character.toUpperCase(s.charAt(0)));
      sb.append(s.substring(1));
      sb.append("(final int row, final int column) {");//$NON-NLS-1$
      sb.append(//
      " switch(column) {");//$NON-NLS-1$

      for (i = 0; i < this.m_primitiveTypes.length; i++) {
        sb.append("case ");//$NON-NLS-1$
        sb.append(i);
        sb.append(": { return (");//$NON-NLS-1$
        if (pt.isInteger() && this.m_primitiveTypes[i].isFloat()) {
          sb.append('(');
          sb.append(s);
          sb.append(')');
        }

        sb.append(" this.points[row].m");//$NON-NLS-1$
        sb.append(i);
        sb.append(");}");//$NON-NLS-1$
      }

      sb.append(//
      "} throw new IndexOutOfBoundsException(((\"Matrix access get");//$NON-NLS-1$
      sb.append(Character.toUpperCase(s.charAt(0)));
      sb.append(s.substring(1));
      sb.append("(\" + row) + ");//$NON-NLS-1$
      sb.append(//
      "',') + column + \") is invalid, the valid index range is (0..\" + ");//$NON-NLS-1$
      sb.append(//
      "(this.points.length-1) + \", 0..");//$NON-NLS-1$
      sb.append(this.m_primitiveTypes.length - 1);
      sb.append(").\"); }");//$NON-NLS-1$
    }

    sb.append(//
    "@Override public final ");//$NON-NLS-1$
    sb.append(IMatrix.class.getCanonicalName());
    sb.append(//
    " selectColumns(final int... cols) {");//$NON-NLS-1$
    sb.append("int i;");//$NON-NLS-1$
    sb.append("checker: {");//$NON-NLS-1$
    sb.append("i = 0;");//$NON-NLS-1$
    sb.append("for (int j : cols) {");//$NON-NLS-1$
    sb.append("if (j != (i++)) {");//$NON-NLS-1$
    sb.append("break checker;");//$NON-NLS-1$
    sb.append("}");//$NON-NLS-1$
    sb.append("}");//$NON-NLS-1$
    sb.append("if (i == ");//$NON-NLS-1$
    sb.append(this.m_primitiveClasses.length);
    sb.append(") {");//$NON-NLS-1$
    sb.append("return this;");//$NON-NLS-1$
    sb.append("}} return new ");//$NON-NLS-1$
    sb.append(this.m_runColumnsClass);
    sb.append("(this, cols);}");//$NON-NLS-1$

    sb.append(//
    "@Override public final ");//$NON-NLS-1$
    sb.append(IMatrix.class.getCanonicalName());
    sb.append(//
    " selectRows(final int... rows) { if(rows.length<=0) { return this.points[rows[0]]; } return super.selectRows(rows); }");//$NON-NLS-1$
  }

  /**
   * create the appendable method for runs
   * 
   * @param sb
   *          the string builder
   */
  private final void __runToText(final MemoryTextOutput sb) {
    sb.append("@Override public final void toText(final ");//$NON-NLS-1$
    sb.append(ITextOutput.class.getCanonicalName());
    sb.append(" textOut) { boolean b; textOut.append('[');");//$NON-NLS-1$

    sb.append("b = false;");//$NON-NLS-1$
    sb.append("for(");//$NON-NLS-1$
    sb.append(this.m_dataPointClass);
    sb.append(" e : this.points) {");//$NON-NLS-1$
    sb.append("if (b) {");//$NON-NLS-1$
    sb.append("textOut.append(',');");//$NON-NLS-1$
    sb.append("textOut.append(' ');");//$NON-NLS-1$
    sb.append("} else {");//$NON-NLS-1$
    sb.append("b = true;");//$NON-NLS-1$
    sb.append("}");//$NON-NLS-1$
    sb.append("e.toText(textOut);");//$NON-NLS-1$
    sb.append("}");//$NON-NLS-1$
    sb.append("textOut.append(']');");//$NON-NLS-1$
    sb.append('}');
  }

  /**
   * create the matrix access methods for runs
   * 
   * @param sb
   *          the string builder
   */
  private final void __runFind(final MemoryTextOutput sb) {
    EDimensionDirection dir;
    boolean q;

    for (final Dimension dim : this.m_dims) {
      dir = dim.getDirection();

      sb.append("private static final ");//$NON-NLS-1$
      sb.append(this.m_dataPointClass);
      sb.append(" _find");//$NON-NLS-1$
      sb.append(dim.getIndex());
      sb.append("(final ");//$NON-NLS-1$
      sb.append(dim.getDataType().getPrimitiveTypeName());
      sb.append(" value, final ");//$NON-NLS-1$
      sb.append(this.m_dataPointClass);
      sb.append("[] data) {");//$NON-NLS-1$

      sb.append(this.m_dataPointClass);
      sb.append(" midVal");//$NON-NLS-1$
      if (!(dir.isStrict())) {
        sb.append(", next");//$NON-NLS-1$
      }
      sb.append(";int low, sh, high, mid");//$NON-NLS-1$
      if (!(dir.isStrict())) {
        sb.append(//
        ", i");//$NON-NLS-1$
      }

      sb.append("; low = 0;");//$NON-NLS-1$
      sb.append("sh = high = (data.length - 1);");//$NON-NLS-1$

      sb.append("while (low <= high) {");//$NON-NLS-1$
      sb.append("mid = ((low + high) >>> 1);");//$NON-NLS-1$
      sb.append("midVal = data[mid];");//$NON-NLS-1$
      sb.append("if (midVal.m");//$NON-NLS-1$
      sb.append(dim.getIndex());
      if (dir.isIncreasing()) {
        sb.append('<');
      } else {
        sb.append('>');
      }
      sb.append("value) { low = (mid + 1); } else {");//$NON-NLS-1$

      sb.append("if (midVal.m");//$NON-NLS-1$
      sb.append(dim.getIndex());
      if (dir.isIncreasing()) {
        sb.append('>');
      } else {
        sb.append('<');
      }
      sb.append("value) { high = (mid - 1); } else {");//$NON-NLS-1$
      if (!(dir.isStrict())) {
        sb.append("for (i = mid; (--i)>=0;) {");//$NON-NLS-1$
        sb.append("next = data[i];");//$NON-NLS-1$
        sb.append("if(next.m");//$NON-NLS-1$
        sb.append(dim.getIndex());
        sb.append("!= value) { return midVal; } ");//$NON-NLS-1$
        sb.append("midVal = next; }");//$NON-NLS-1$
      }
      sb.append("return midVal; ");//$NON-NLS-1$

      sb.append("}}}");//$NON-NLS-1$

      q = dim.getDimensionType().isSolutionQualityMeasure();
      sb.append("if(low <= 0) { return ");//$NON-NLS-1$
      if (q) {
        sb.append("data[0]");//$NON-NLS-1$
      } else {
        sb.append("null");//$NON-NLS-1$
      }

      sb.append(";} if(low > sh) { return ");//$NON-NLS-1$
      if (q) {
        sb.append("null");//$NON-NLS-1$
      } else {
        sb.append("data[sh]");//$NON-NLS-1$
      }

      sb.append(";} return data[low");//$NON-NLS-1$
      if (!q) {
        sb.append("-1");//$NON-NLS-1$
      }

      sb.append("];}");//$NON-NLS-1$
    }

    for (final EPrimitiveType clazz : new EPrimitiveType[] {
        EPrimitiveType.DOUBLE, EPrimitiveType.LONG }) {
      sb.append("@Override public final "); //$NON-NLS-1$
      sb.append(this.m_dataPointClass);
      sb.append(" find(final int column, final "); //$NON-NLS-1$
      sb.append(clazz.getPrimitiveTypeName());
      sb.append(" value) { switch(column) {"); //$NON-NLS-1$

      for (final Dimension dim : this.m_dims) {
        sb.append("case "); //$NON-NLS-1$
        sb.append(dim.getIndex());
        sb.append(": { return "); //$NON-NLS-1$
        sb.append(this.m_runClass);
        sb.append("._find"); //$NON-NLS-1$
        sb.append(dim.getIndex());
        sb.append("(("); //$NON-NLS-1$

        if (!(dim.getDataType().isAssignableFrom(clazz))) {
          sb.append('(');
          sb.append(dim.getDataType().getPrimitiveTypeName());
          sb.append(')');
        }
        sb.append("value), this.points); }"); //$NON-NLS-1$
      }

      sb.append("}"); //$NON-NLS-1$

      sb.append(//
      " throw new IndexOutOfBoundsException(\"Column \" + ");//$NON-NLS-1$
      sb.append(//
      "column + \" is invalid, valid indexes are in 0..");//$NON-NLS-1$
      sb.append(this.m_parsers.length - 1);
      sb.append(//
      ".\");");//$NON-NLS-1$
      sb.append("}"); //$NON-NLS-1$
    }

  }

  /**
   * create the run columns class body
   * 
   * @return the run columns class body
   */
  private final CharSequence __createRunColumnsClass() {
    final MemoryTextOutput sb;

    sb = new MemoryTextOutput();
    sb.append("package ");//$NON-NLS-1$
    sb.append(this.m_package);
    sb.append(';');

    this.__runColumnsClassHead(sb);
    this.__runColumnsConstructor(sb);
    this.__runColumnsGettersAndSetters(sb);
    this.__runColumnsRowIterator(sb);

    sb.append('}');
    return sb;
  }

  /**
   * create the matrix access methods for runs
   * 
   * @param sb
   *          the string builder
   */
  private final void __runColumnsGettersAndSetters(
      final MemoryTextOutput sb) {
    int i;
    String s;

    for (final EPrimitiveType pt : new EPrimitiveType[] {
        EPrimitiveType.DOUBLE, EPrimitiveType.LONG }) {
      sb.append(//
      "@Override public final ");//$NON-NLS-1$
      sb.append(s = pt.getPrimitiveTypeName());
      sb.append(" get");//$NON-NLS-1$
      sb.append(Character.toUpperCase(s.charAt(0)));
      sb.append(s.substring(1));
      sb.append("(final int row, final int column) {");//$NON-NLS-1$
      sb.append(//
      " switch(this.m_cols[column]) {");//$NON-NLS-1$

      for (i = 0; i < this.m_primitiveTypes.length; i++) {
        sb.append("case ");//$NON-NLS-1$
        sb.append(i);
        sb.append(": { return (");//$NON-NLS-1$
        if (pt.isInteger() && this.m_primitiveTypes[i].isFloat()) {
          sb.append('(');
          sb.append(s);
          sb.append(')');
        }

        sb.append(" this.points[row].m");//$NON-NLS-1$
        sb.append(i);
        sb.append(");}");//$NON-NLS-1$
      }

      sb.append(//
      "} throw new IndexOutOfBoundsException(((\"Matrix access get");//$NON-NLS-1$
      sb.append(Character.toUpperCase(s.charAt(0)));
      sb.append(s.substring(1));
      sb.append("(\" + row) + ");//$NON-NLS-1$

      sb.append(//
      "',') + column + \") is invalid, the valid index range is (0..\" + ");//$NON-NLS-1$
      sb.append(//
      "(this.points.length-1) + \", 0..");//$NON-NLS-1$
      sb.append(this.m_primitiveTypes.length - 1);
      sb.append(").\"); }");//$NON-NLS-1$
    }

    sb.append(//
    "@Override public final ");//$NON-NLS-1$
    sb.append(this.m_runColumnsClass);
    sb.append(" copy() { return this; }");//$NON-NLS-1$
  }

  /**
   * create the matrix access methods for runs iterating rows
   * 
   * @param sb
   *          the string builder
   */
  private final void __runColumnsRowIterator(final MemoryTextOutput sb) {

    sb.append("@Override public final ");//$NON-NLS-1$
    sb.append(Iterator.class.getCanonicalName());
    sb.append('<');
    sb.append(IMatrix.class.getCanonicalName());
    sb.append(//
    "> iterateRows() { if (this.points.length <= 1) { return new ");//$NON-NLS-1$"
    sb.append(InstanceIterator.class.getCanonicalName());
    sb.append('<');
    sb.append(IMatrix.class.getCanonicalName());
    sb.append(">(this); } return new ");//$NON-NLS-1$
    sb.append(this.m_runColumnsRowIteratorClass);
    sb.append("(this, this.m_cols");//$NON-NLS-1$
    if (!(this.m_matrixChoice instanceof Boolean)) {
      sb.append(", this.isInt");//$NON-NLS-1$
    }
    sb.append(");}");//$NON-NLS-1$
  }

  /**
   * create the run columns head
   * 
   * @param sb
   *          the string builder
   */
  private final void __runColumnsClassHead(final MemoryTextOutput sb) {

    sb.append(//
    "@SuppressWarnings({\"unchecked\", \"rawtypes\", \"nls\", \"javadoc\"}) final class ");//$NON-NLS-1$
    sb.append(this.m_runColumnsClass);
    sb.append(" extends ");//$NON-NLS-1$
    sb.append(MatrixColumns.class.getCanonicalName());
    sb.append('<');
    sb.append(this.m_runClass);
    sb.append('>');
    sb.append('{');

    // declare fields: all private and final
    sb.append("final ");//$NON-NLS-1$
    sb.append(this.m_dataPointClass);
    sb.append("[] points;");//$NON-NLS-1$

    if (!(this.m_matrixChoice instanceof Boolean)) {
      sb.append("final boolean isInt;");//$NON-NLS-1$
    }
  }

  /**
   * create the constructor of the data point
   * 
   * @param sb
   *          the string builder
   */
  private final void __runColumnsConstructor(final MemoryTextOutput sb) {
    final String s;

    sb.append(this.m_runColumnsClass);
    sb.append("(final ");//$NON-NLS-1$
    sb.append(this.m_runClass);
    sb.append(//
    " owner, final int[] cols) { super(owner, cols); this.points = owner.points; ");//$NON-NLS-1$

    if (!(this.m_matrixChoice instanceof Boolean)) {
      sb.append("checkIsFloat:{checkIsInt: { for(int i: cols) {if "); //$NON-NLS-1$

      s = ((String) (this.m_matrixChoice)).replace('@', 'i');
      sb.append(s);
      sb.append(//
      " {continue; } break checkIsInt; } this.isInt=true;break checkIsFloat; } this.isInt=false;}");//$NON-NLS-1$
    }

    sb.append('}');

    if (this.m_matrixChoice != Boolean.FALSE) {
      sb.append("@Override public final boolean isIntegerMatrix(){return ");//$NON-NLS-1$
      if (this.m_matrixChoice == Boolean.TRUE) {
        sb.append("true;");//$NON-NLS-1$
      } else {
        sb.append("this.isInt;"); //$NON-NLS-1$
      }
      sb.append('}');
    }

  }

  /**
   * create the run columns row iterator class body
   * 
   * @return the run columns row iterator class body
   */
  private final CharSequence __createRunColumnsRowIterator() {
    final MemoryTextOutput sb;

    sb = new MemoryTextOutput();
    sb.append("package ");//$NON-NLS-1$
    sb.append(this.m_package);
    sb.append(';');

    this.__runColumnsRowIteratorClassHead(sb);
    this.__runColumnsRowIteratorConstructor(sb);
    this.__runColumnsRowIteratorGettersAndSetters(sb);

    sb.append('}');
    return sb;
  }

  /**
   * create the run columns head
   * 
   * @param sb
   *          the string builder
   */
  private final void __runColumnsRowIteratorClassHead(
      final MemoryTextOutput sb) {

    sb.append(//
    "@SuppressWarnings({\"unchecked\", \"rawtypes\", \"nls\", \"javadoc\"}) final class ");//$NON-NLS-1$
    sb.append(this.m_runColumnsRowIteratorClass);
    sb.append(" extends ");//$NON-NLS-1$
    sb.append(MatrixRowIterator.class.getCanonicalName());
    sb.append('<');
    sb.append(this.m_runColumnsClass);
    sb.append('>');
    sb.append('{');

    // declare fields: all private and final
    sb.append("final ");//$NON-NLS-1$
    sb.append(this.m_dataPointClass);
    sb.append("[] points;");//$NON-NLS-1$

    sb.append("final int[] cols;");//$NON-NLS-1$

    if (!(this.m_matrixChoice instanceof Boolean)) {
      sb.append("final boolean isInt;");//$NON-NLS-1$
    }

  }

  /**
   * create the constructor of the data point
   * 
   * @param sb
   *          the string builder
   */
  private final void __runColumnsRowIteratorConstructor(
      final MemoryTextOutput sb) {

    sb.append(this.m_runColumnsRowIteratorClass);
    sb.append("(final ");//$NON-NLS-1$
    sb.append(this.m_runColumnsClass);
    sb.append(" owner, final int[] c");//$NON-NLS-1$
    if (!(this.m_matrixChoice instanceof Boolean)) {
      sb.append(", final boolean isi");//$NON-NLS-1$
    }

    sb.append(//
    ") { super(owner); this.points = owner.points; ");//$NON-NLS-1$

    if (!(this.m_matrixChoice instanceof Boolean)) {
      sb.append("this.isInt = isi;");//$NON-NLS-1$
    }
    sb.append("this.cols = c;");//$NON-NLS-1$

    sb.append('}');

    if (this.m_matrixChoice != Boolean.FALSE) {
      sb.append("@Override public final boolean isIntegerMatrix(){return ");//$NON-NLS-1$
      if (this.m_matrixChoice == Boolean.TRUE) {
        sb.append("true;");//$NON-NLS-1$
      } else {
        sb.append("this.isInt;"); //$NON-NLS-1$
      }
      sb.append('}');
    }

  }

  /**
   * create the matrix access methods for runs columns row iterators
   * 
   * @param sb
   *          the string builder
   */
  private final void __runColumnsRowIteratorGettersAndSetters(
      final MemoryTextOutput sb) {
    int i;
    String s;

    for (final EPrimitiveType pt : new EPrimitiveType[] {
        EPrimitiveType.DOUBLE, EPrimitiveType.LONG }) {
      sb.append(//
      "@Override public final ");//$NON-NLS-1$
      sb.append(s = pt.getPrimitiveTypeName());
      sb.append(" get");//$NON-NLS-1$
      sb.append(Character.toUpperCase(s.charAt(0)));
      sb.append(s.substring(1));
      sb.append("(final int row, final int column) {");//$NON-NLS-1$
      sb.append(//
      "if(row == 0) { switch(this.cols[column]) {");//$NON-NLS-1$

      for (i = 0; i < this.m_primitiveTypes.length; i++) {
        sb.append("case ");//$NON-NLS-1$
        sb.append(i);
        sb.append(": { return (");//$NON-NLS-1$
        if (pt.isInteger() && this.m_primitiveTypes[i].isFloat()) {
          sb.append('(');
          sb.append(s);
          sb.append(')');
        }

        sb.append(" this.points[this.m_rows[0]].m");//$NON-NLS-1$
        sb.append(i);
        sb.append(");}");//$NON-NLS-1$
      }

      sb.append(//
      "} } throw new IndexOutOfBoundsException((((\"Matrix access get");//$NON-NLS-1$
      sb.append(Character.toUpperCase(s.charAt(0)));
      sb.append(s.substring(1));
      sb.append("(\" + row) + ");//$NON-NLS-1$

      sb.append(//
      "',') + column + \") is invalid, the valid index range is (0, 0..\"");//$NON-NLS-1$
      sb.append(//
      " + (this.cols.length - 1)) + ')'); }");//$NON-NLS-1$
    }
  }

  /** {@inheritDoc} */
  @Override
  public final DataFactory call() {
    final ClassLoader cl;
    final String qP;
    final Class<?>[] classes;
    int i;

    this.__makeBasicIMatrix();

    qP = (this.m_package + '.' + this.m_factoryClass);

    cl = new JavaCompilerTask(//
        //
        new CharSequenceJavaFileObject(
            (this.m_package + '.' + this.m_dataPointClass),//
            this.__createDataPoint()),//
        //
        new CharSequenceJavaFileObject(
            (this.m_package + '.' + this.m_runClass),//
            this.__createRun()),//
        //
        new CharSequenceJavaFileObject(
            (this.m_package + '.' + this.m_runColumnsClass),//
            this.__createRunColumnsClass()),//
        //
        new CharSequenceJavaFileObject(
            (this.m_package + '.' + this.m_runColumnsRowIteratorClass),//
            this.__createRunColumnsRowIterator()),//
        //
        new CharSequenceJavaFileObject(qP, this.__createFactory())).call();

    try {
      i = this.m_parsers.length;
      classes = new Class<?>[i];
      for (; (--i) >= 0;) {
        classes[i] = this.m_parsers[i].getClass();
      }

      return ((DataFactory) (cl.loadClass(qP).getConstructor(classes)
          .newInstance((Object[]) (this.m_parsers))));
    } catch (final RuntimeException e) {
      throw e;
    } catch (final Throwable t) {
      throw new RuntimeException(t);
    }
  }

  /** {@inheritDoc} */
  @Override
  public final int compare(final Dimension o1, final Dimension o2) {
    final EDimensionType t1, t2;
    final EPrimitiveType p1, p2;

    if (o1 == o2) {
      return 0;
    }

    t1 = o1.getDimensionType();
    t2 = o2.getDimensionType();
    if (t1 != t2) {
      if (t1 == EDimensionType.QUALITY_PROBLEM_DEPENDENT) {
        return (-1);
      }
      if (t2 == EDimensionType.QUALITY_PROBLEM_DEPENDENT) {
        return (1);
      }
      if (t1 == EDimensionType.ITERATION_SUB_FE) {
        return (-1);
      }
      if (t2 == EDimensionType.ITERATION_SUB_FE) {
        return (1);
      }

      dtest: switch (t1) {
        case RUNTIME_CPU: {
          if (t2 == EDimensionType.RUNTIME_NORMALIZED) {
            return (-1);
          }
          break dtest;
        }
        case RUNTIME_NORMALIZED: {
          if (t2 == EDimensionType.RUNTIME_CPU) {
            return 1;
          }
          break dtest;
        }
        case ITERATION_ALGORITHM_STEP: {
          if ((t2 == EDimensionType.ITERATION_FE)
              || (t2 == EDimensionType.ITERATION_SUB_FE)) {
            return (1);
          }
          break dtest;
        }
        case ITERATION_FE: {
          if (t2 == EDimensionType.ITERATION_ALGORITHM_STEP) {
            return (-1);
          }
          if (t2 == EDimensionType.ITERATION_SUB_FE) {
            return (1);
          }
          break dtest;
        }
        case ITERATION_SUB_FE: {
          if ((t2 == EDimensionType.ITERATION_FE)
              || (t2 == EDimensionType.ITERATION_ALGORITHM_STEP)) {
            return (-1);
          }
          break dtest;
        }
        case QUALITY_PROBLEM_DEPENDENT: {
          if (t2 == EDimensionType.QUALITY_PROBLEM_INDEPENDENT) {
            return (-1);
          }
          break dtest;
        }
        case QUALITY_PROBLEM_INDEPENDENT: {
          if (t2 == EDimensionType.QUALITY_PROBLEM_DEPENDENT) {
            return (1);
          }
          break dtest;
        }
        default: {
          throw new IllegalStateException();
        }
      }

    }

    p1 = o1.getDataType();
    p2 = o2.getDataType();

    if (p1 != p2) {

      if (p1.isInteger()) {
        if (p2.isFloat()) {
          return (-1);
        }
      }
      if (p2.isInteger()) {
        if (p1.isFloat()) {
          return 1;
        }
      }

      if (p1.isAssignableFrom(p2)) {
        return (-1);
      }
      if (p2.isAssignableFrom(p1)) {
        return (1);
      }
    }

    return Integer.compare(o1.getIndex(), o2.getIndex());
  }

}