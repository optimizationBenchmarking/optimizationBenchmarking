package examples.org.optimizationBenchmarking.utils.math;

import org.optimizationBenchmarking.utils.math.functions.UnaryFunction;
import org.optimizationBenchmarking.utils.math.text.CompoundFunctionParser;

/**
 * A example for using the compound function parser
 */
public final class CompoundParserTest {

  /**
   * The main function
   * 
   * @param args
   *          ignored
   */
  public static void main(String[] args) {
    final CompoundFunctionParser<UnaryFunction> parser;
    String string;

    parser = CompoundFunctionParser.getDefaultUnaryFunctionParser();

    string = ("5+4"); //$NON-NLS-1$
    System.out.println(string);
    System.out.println(parser.parseString(string));
    System.out.println();

    string = ("5+4*3"); //$NON-NLS-1$
    System.out.println(string);
    System.out.println(parser.parseString(string));
    System.out.println();

    string = ("5+4*3^6/7"); //$NON-NLS-1$
    System.out.println(string);
    System.out.println(parser.parseString(string));
    System.out.println();

    string = ("mul(5,sin(4)*ln 4)"); //$NON-NLS-1$
    System.out.println(string);
    System.out.println(parser.parseString(string));
    System.out.println();

    string = ("#1#+max[ln{5+-4*sin(45.3e-5-3)},-mul(2,1)]/|25*-1|-3*|-4|"); //$NON-NLS-1$
    System.out.println(string);
    System.out.println(parser.parseString(string));
    System.out.println();

    string = ("(5+4)*3"); //$NON-NLS-1$
    System.out.println(string);
    System.out.println(parser.parseString(string));
    System.out.println();

    string = ("sin 3.4*12+ln(45-tan 3)²"); //$NON-NLS-1$
    System.out.println(string);
    System.out.println(parser.parseString(string));
    System.out.println();
    

    string = ("sin \u03c0"); //$NON-NLS-1$
    System.out.println(string);
    System.out.println(parser.parseString(string));
    System.out.println();
  }

}
