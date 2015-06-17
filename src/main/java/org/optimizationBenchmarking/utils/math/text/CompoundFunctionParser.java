package org.optimizationBenchmarking.utils.math.text;

import java.util.ArrayList;
import java.util.Arrays;

import org.optimizationBenchmarking.utils.error.ErrorUtils;
import org.optimizationBenchmarking.utils.error.RethrowMode;
import org.optimizationBenchmarking.utils.math.functions.BinaryFunction;
import org.optimizationBenchmarking.utils.math.functions.MathematicalFunction;
import org.optimizationBenchmarking.utils.math.functions.QuaternaryFunction;
import org.optimizationBenchmarking.utils.math.functions.TernaryFunction;
import org.optimizationBenchmarking.utils.math.functions.UnaryFunction;
import org.optimizationBenchmarking.utils.math.functions.arithmetic.Absolute;
import org.optimizationBenchmarking.utils.math.functions.arithmetic.Add;
import org.optimizationBenchmarking.utils.math.functions.arithmetic.Mul;
import org.optimizationBenchmarking.utils.math.functions.arithmetic.Sub;
import org.optimizationBenchmarking.utils.math.functions.compound.BinaryFunctionBuilder;
import org.optimizationBenchmarking.utils.math.functions.compound.FunctionBuilder;
import org.optimizationBenchmarking.utils.math.functions.compound.QuaternaryFunctionBuilder;
import org.optimizationBenchmarking.utils.math.functions.compound.TernaryFunctionBuilder;
import org.optimizationBenchmarking.utils.math.functions.compound.UnaryFunctionBuilder;
import org.optimizationBenchmarking.utils.parsers.AnyNumberParser;
import org.optimizationBenchmarking.utils.parsers.Parser;
import org.optimizationBenchmarking.utils.text.charset.Brace;
import org.optimizationBenchmarking.utils.text.charset.Char;
import org.optimizationBenchmarking.utils.text.charset.Characters;

/**
 * A mathematical function parser which can build compound functions of a
 * given arity.
 *
 * @param <T>
 *          the function type
 */
public class CompoundFunctionParser<T extends MathematicalFunction>
    extends Parser<T> {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** the argument separator tag in the token list */
  private static final Object ARGUMENT_SEPARATOR = new Object();
  
  /** the white space tag used when building the token list */
  private static final Object WHITE_SPACE = new Object();

  /** the function builder */
  private final FunctionBuilder<T> m_builder;

  /** the name resolver */
  private final INameResolver m_resolver;

  /**
   * Create the mathematical function parser
   *
   * @param builder
   *          the function builder
   * @param resolver
   *          the name resolver
   */
  public CompoundFunctionParser(final FunctionBuilder<T> builder,
      final INameResolver resolver) {
    super();
    if (builder == null) {
      throw new IllegalArgumentException(//
          "Function builder cannot be null."); //$NON-NLS-1$
    }
    if (resolver == null) {
      throw new IllegalArgumentException(//
          "Name resolver cannot be null."); //$NON-NLS-1$
    }
    this.m_builder = builder;
    this.m_resolver = resolver;
  }

  /** {@inheritDoc} */
  @Override
  public final Class<T> getOutputClass() {
    return this.m_builder.getFunctionClass();
  }

  /** {@inheritDoc} */
  @Override
  public final T parseString(final String string) {
    final ArrayList<Object> tokens;
    final ArrayList<Object> stack;

    stack = new ArrayList<>();
    tokens = this.__tokenize(string, stack);

    if ((tokens == null) || (tokens.isEmpty())) {
      throw new IllegalArgumentException(//
          "Cannot parse function string '" //$NON-NLS-1$
              + string + //
              "' as it contains no tokens."); //$NON-NLS-1$
    }
    return this.__parseTokens(tokens, 0, tokens.size(), stack);
  }

  /**
   * Parse a sequence of tokens
   *
   * @param tokens
   *          the tokens
   * @param start
   *          the inclusive start index
   * @param end
   *          the exclusive end index
   * @param stack
   *          the stack
   * @return the result
   */
  @SuppressWarnings("unchecked")
  private final T __parseTokens(final ArrayList<Object> tokens,
      final int start, final int end, final ArrayList<Object> stack) {
    final int endMinus1;
    _ParsedFunction parsedFunction, best;
    Object token;
    Brace brace;
    T[] arguments;
    T parseResult;
    int index, bestIndex, currentPriority, bestPriority, argCount, argStart;

    if (start >= end) {
      throw new IllegalArgumentException(//
          "Empty sequence of tokens from index " //$NON-NLS-1$
              + start + " to " //$NON-NLS-1$
              + end + " in list " + tokens); //$NON-NLS-1$
    }

    token = tokens.get(start);
    endMinus1 = (end - 1);

    // Handle the situation where the range contains only a single token.
    if (start >= endMinus1) {
      // It must then be a parsed function, either from a constant or a
      // resolved name.
      if (token instanceof MathematicalFunction) {
        return ((T) token);
      }
      throw new IllegalArgumentException(//
          "Mathematical function expected at index " //$NON-NLS-1$
              + start + " in token list " + tokens + //$NON-NLS-1$
              ", but '" + //$NON-NLS-1$
              token + "', a " + token.getClass().getSimpleName() //$NON-NLS-1$
              + ", encountered."); //$NON-NLS-1$
    }

    // Handle the situation where the first token is an opening brace.
    // There are three possible situations:
    // 1. The whole token array is enclosed by the brace and it can be
    // removed without changing the meaning, e.g., (3+4*5)
    // 2. The whole token array is enclosed by the brace and the brace is
    // an absolute brace, e.g., |3+4*5|
    // 3. The brace ranges to one step before the end and a postfix
    // expression follows, e.g., (3+4*5)²
    // 4. The brace ends somewhere and an infix operator follows, e.g.,
    // (3+4*5)+6*7 or (3+4*5)*6+7
    checkTokens: if (token instanceof Brace) {
      stack.clear();
      brace = ((Brace) token);
      stack.add(brace.getOtherEnd());
      index = CompoundFunctionParser.__findBraceEnd(tokens, (start + 1),
          end, stack);

      if (index == endMinus1) {// cases 1+ 2
        parseResult = this.__parseTokens(tokens, (start + 1), endMinus1,
            stack);
        if (brace.getChar() == '|') {// handle case 2, absolute |x|
          return this.m_builder.compound(Absolute.INSTANCE, parseResult);
        }
        return parseResult;
      }

      // For cases 3 and 4, a parsed mathematical function must follow
      token = tokens.get(index + 1);
      if (token instanceof _ParsedFunction) {
        parsedFunction = ((_ParsedFunction) token);

        if (index == (endMinus1 - 1)) {
          // in case 3, it is a postfix function
          if ((parsedFunction.m_mode == _ParsedFunction.MODE_POSTFIX) && //
              (parsedFunction.m_func instanceof UnaryFunction)) {
            return this.m_builder.compound(//
                ((UnaryFunction) (parsedFunction.m_func)),//
                this.__parseTokens(tokens, (start + 1), index, stack));
          }

          throw new IllegalArgumentException(//
              "Expected unary postfix function at index " + endMinus1 + //$NON-NLS-1$
                  " in " + tokens + //$NON-NLS-1$
                  ", but encountered " + token); //$NON-NLS-1$
        }

        // This is the tricky case 4, because we could either have
        // a) (3+4*5)*6+7 or b) (3+4*5)+6*7. We cannot handle this here in
        // any meaningful way, so we just check if the structure is
        // correct.
        if ((parsedFunction.m_mode == _ParsedFunction.MODE_INFIX) && //
            (parsedFunction.m_func instanceof BinaryFunction)) {
          break checkTokens;
        }

        throw new IllegalArgumentException(//
            "Expected binary infix function at index " + endMinus1 + //$NON-NLS-1$
                " in " + tokens + //$NON-NLS-1$
                ", but encountered " + token); //$NON-NLS-1$
      }

      throw new IllegalArgumentException(//
          "Expected mathematical function at index " + endMinus1 + //$NON-NLS-1$
              " in "//$NON-NLS-1$
              + tokens + ", but encountered " + token//$NON-NLS-1$
              + " which is an instance of "//$NON-NLS-1$
              + token.getClass().getSimpleName());
    }

    // If we get here, the structure of tokens is such that it is not
    // enclosed by useless braces. We now need to find the lowest-priority
    // token.
    bestPriority = Integer.MAX_VALUE;
    bestIndex = (-1);
    best = null;
    stack.clear();
    for (index = start; index < end; index++) {
      token = tokens.get(index);
      if (token instanceof _ParsedFunction) {
        if (!(stack.isEmpty())) {
          continue;
        }
        parsedFunction = ((_ParsedFunction) token);

        switch (parsedFunction.m_mode) {
          case _ParsedFunction.MODE_PREFIX: {
            if (index <= start) {
              bestPriority = parsedFunction.m_func.getPrecedencePriority();
              bestIndex = start;
              best = parsedFunction;
            }
            break;
          }

          case _ParsedFunction.MODE_INFIX: {
            if ((index <= start) || (index >= endMinus1)) {
              throw new IllegalArgumentException(//
                  "No binary infix function expected at index " //$NON-NLS-1$
                      + index + ", but found " //$NON-NLS-1$
                      + parsedFunction + " in " + tokens);//$NON-NLS-1$
            }
            currentPriority = parsedFunction.m_func
                .getPrecedencePriority();
            if ((currentPriority < bestPriority) || //
                (best == null) || //
                (best.m_mode == _ParsedFunction.MODE_CALL)) {
              bestPriority = currentPriority;
              best = parsedFunction;
              bestIndex = index;
            }
            break;
          }

          case _ParsedFunction.MODE_POSTFIX: {
            if (index >= endMinus1) {
              currentPriority = parsedFunction.m_func
                  .getPrecedencePriority();
              if ((currentPriority < bestPriority) || //
                  (best == null) || //
                  (best.m_mode == _ParsedFunction.MODE_CALL)) {
                bestPriority = currentPriority;
                bestIndex = index;
                best = parsedFunction;
              }
            }
            break;
          }

          default: {// MODE_CALL
            if (index >= endMinus1) {
              throw new IllegalArgumentException(//
                  "No call function expected at index " //$NON-NLS-1$
                      + index + ", but found " //$NON-NLS-1$
                      + parsedFunction + " in " + tokens);//$NON-NLS-1$
            }

            if (index <= start) {
              bestPriority = parsedFunction.m_func.getPrecedencePriority();
              best = parsedFunction;
              bestIndex = index;
            }
            break;
          }
        }
      } else {
        CompoundFunctionParser.__stackBrace(token, stack);
      }
    }

    if (best == null) {
      throw new IllegalArgumentException(//
          "Tokens do not contain any function in range " //$NON-NLS-1$
              + start + " to " //$NON-NLS-1$
              + end + " in list " + tokens);//$NON-NLS-1$
    }

    // We found the weakest-binding mathematical function and now can split
    // the tokens into the function token and its parameters.

    switch (best.m_mode) {

      case _ParsedFunction.MODE_PREFIX: {
        return this.m_builder.compound(best.m_func,//
            this.__parseTokens(tokens, (start + 1), end, stack));
      }

      case _ParsedFunction.MODE_INFIX: {
        return this.m_builder.compound(best.m_func,//
            this.__parseTokens(tokens, start, bestIndex, stack),//
            this.__parseTokens(tokens, (bestIndex + 1), end, stack));
      }

      case _ParsedFunction.MODE_POSTFIX: {
        return this.m_builder.compound(best.m_func,//
            this.__parseTokens(tokens, start, endMinus1, stack));
      }

      default: {
        stack.clear();

        arguments = ((T[]) (new MathematicalFunction[best.m_func
            .getMaxArity()]));
        argCount = 0;
        argStart = (start + 2); // skip call and (
        for (index = argStart; index < endMinus1; index++) {
          token = tokens.get(index);

          if (!(CompoundFunctionParser.__stackBrace(token, stack))) {
            if (token == CompoundFunctionParser.ARGUMENT_SEPARATOR) {
              if (stack.isEmpty()) {
                arguments[argCount++] = //
                this.__parseTokens(tokens, argStart, index, stack);
                argStart = (index + 1);
              }
            }
          }
        }

        if (argStart < endMinus1) {
          if (stack.isEmpty()) {
            arguments[argCount++] = //
            this.__parseTokens(tokens, argStart, endMinus1, stack);
          } else {
            throw new IllegalArgumentException(//
                "Did not find closing brace " + //$NON-NLS-1$
                    stack.get(stack.size() - 1) + " in range from " //$NON-NLS-1$
                    + start + " to " + end//$NON-NLS-1$
                    + " in " + tokens);//$NON-NLS-1$
          }
        }

        return this.m_builder.compound(best.m_func,
            Arrays.copyOf(arguments, argCount));
      }
    }
  }

  /**
   * Potentially handle a brace
   *
   * @param couldBeBrace
   *          the thing which could be a brace
   * @param stack
   *          the stack
   * @return {@code true} if {@code couldBeBrace} was a brace.
   */
  private static final boolean __stackBrace(final Object couldBeBrace,
      final ArrayList<Object> stack) {
    final Brace brace;
    final int size;

    if (couldBeBrace instanceof Brace) {
      brace = ((Brace) couldBeBrace);
      size = (stack.size() - 1);
      if ((size >= 0) && (stack.get(size) == brace)) {
        stack.remove(size);
      } else {
        if (!(brace.isOpening())) {
          throw new IllegalArgumentException(
              "Expected opening brace, but found closing one " //$NON-NLS-1$
                  + brace + '.');
        }
        stack.add(brace.getOtherEnd());
      }
      return true;
    }
    return false;
  }

  /**
   * Find the index of the closing brace
   *
   * @param tokens
   *          the tokens
   * @param start
   *          the start index
   * @param end
   *          the end index
   * @param stack
   *          the stack, expected to contain the sought after end brace
   * @return the end index
   */
  private static final int __findBraceEnd(final ArrayList<Object> tokens,
      final int start, final int end, final ArrayList<Object> stack) {
    int index;
    for (index = start; index < end; index++) {
      if (CompoundFunctionParser.__stackBrace(tokens.get(index), stack)) {
        if (stack.isEmpty()) {
          return index;
        }
      }
    }

    throw new IllegalArgumentException(//
        "Did not find closing brace matching to " + //$NON-NLS-1$
            stack.get(stack.size() - 1) + " between index " + start + //$NON-NLS-1$
            " and " + end + //$NON-NLS-1$
            " in " + tokens); //$NON-NLS-1$
  }

  /**
   * Translate a given string into a sequence of tokens. An element in the
   * {@link java.util.ArrayList} may be:
   * <ol>
   * <li>an instance of
   * {@link org.optimizationBenchmarking.utils.math.functions.MathematicalFunction}
   * , in which case it is a synthetic function representing a resolved
   * name</li>
   * <li>{@link #ARGUMENT_SEPARATOR}, standing for something like {@code ,}
   * or {@code ;} which separates arguments</li>
   * <li>an instance of {@link java.lang.Number}, meaning a numerical
   * constant</li>
   * <li>an instance of
   * {@link org.optimizationBenchmarking.utils.text.charset.Brace}, in
   * which case it might be an opening or closing brace, or some special
   * brace, e.g., {@code |} indicating the start or end of an absolute
   * value</li>
   * </ol>
   *
   * @param string
   *          the string
   * @param stack
   *          a multi-purpose stack
   * @return the tokens
   */
  private final ArrayList<Object> __tokenize(final String string,
      final ArrayList<Object> stack) {
    final int end;
    final ArrayList<Object> result;
    int index, seqEnd, size;
    char lastChar, currentChar;
    Object parsedChar, lastAdded, add;
    _ParsedFunction func;
    Object error;
    String sequence;
    boolean beforeIsNegate;

    result = new ArrayList<>();
    end = string.length();
    index = 0;
    lastAdded = null;

    // In this loop, we try to split a string into a sequence of tokens.
    loop: while (index < end) {
      currentChar = string.charAt(index);

      // look for a single-char match
      parsedChar = CompoundFunctionParser.__parseChar(currentChar);
      if (parsedChar != null) {

        if (parsedChar == CompoundFunctionParser.WHITE_SPACE) {
          index++;
          continue loop;
        }

        if (parsedChar instanceof _ParsedFunction) {
          func = ((_ParsedFunction) parsedChar);
          if (func.m_mode == _ParsedFunction.MODE_INFIX) {

            if ((func.m_func instanceof Sub)) {
              if (CompoundFunctionParser.__arePlusMinusPrefixes(lastAdded,
                  result, stack)) {
                if (lastAdded == _ParsedFunction.NEGATE_PREFIX) {
                  size = result.size();
                  result.remove(--size);
                  if (size > 0) {
                    lastAdded = result.get(size - 1);
                  } else {
                    lastAdded = null;
                  }
                  index++;
                  continue loop;
                }
                parsedChar = _ParsedFunction.NEGATE_PREFIX;
              }
            } else {
              if ((func.m_func instanceof Add)) {
                if (CompoundFunctionParser.__arePlusMinusPrefixes(
                    lastAdded, result, stack)) {
                  index++;
                  continue loop; // + can then be ignored
                }
              } else {
                // ** becomes power
                if ((func.m_func instanceof Mul)
                    && (lastAdded == _ParsedFunction.MUL_INFIX)) {
                  result.set((result.size() - 1),
                      (lastAdded = _ParsedFunction.POW_INFIX));
                  index++;
                  continue loop;
                }
              }
            }
          }
        }

        CompoundFunctionParser.__add((lastAdded = parsedChar), result);
        index++;
        continue loop;
      }

      // no single character matched the input

      // try to obtain the end of the current sequence. there may be two
      // ends, in case of numbers of the form 1e3
      currentChar = 0;
      findSeqEnd: for (seqEnd = index; seqEnd < end; seqEnd++) {
        lastChar = currentChar;
        currentChar = string.charAt(seqEnd);

        switch (currentChar) {
          case '+':
          case '-': {
            if ((lastChar == 'E') || (lastChar == 'e')) {
              try {
                // if the string has the form 123E+3, then "123" must be a
                // valid double, too... ...hacky, isn't it?
                Double.parseDouble(string.substring(index, (seqEnd - 2)));
              } catch (final Throwable thisIsNotANumber) {
                break findSeqEnd;
              }
              continue findSeqEnd;
            }
            break findSeqEnd;
          }

          default: {
            if (Character.isWhitespace(currentChar)) {
              break findSeqEnd;
            }
            if (CompoundFunctionParser.__parseChar(currentChar) != null) {
              break findSeqEnd;
            }
          }
        }
      }

      // we now have a sequence from index (inclusively) to seqEnd
      // (exclusively)
      // we should find what it is...
      sequence = string.substring(index, seqEnd);
      add = null;

      getToken: {
        error = null;

        // first test whether the sequence is a number
        try {

          beforeIsNegate = (lastAdded == _ParsedFunction.NEGATE_PREFIX);
          if (beforeIsNegate) {
            try {
              add = this.m_builder.constant(//
                  AnyNumberParser.INSTANCE.parseString(//
                      '-' + sequence));
              if (add != null) {
                result.remove(result.size() - 1);
                break getToken;
              }
            } catch (final Throwable tt) {
              error = tt;
            }
          }

          add = this.m_builder.constant(//
              AnyNumberParser.INSTANCE.parseString(sequence));
          if (add != null) {
            break getToken;
          }
        } catch (final Throwable t) {
          error = ErrorUtils.aggregateError(error, t);
        }

        // finally, let's see whether it is a mathematical function, either
        // from our stash of functions or from an external class.
        try {
          add = MathematicalFunctionParser.getInstance()._parseString(
              sequence);
          if (add != null) {
            break getToken;
          }
        } catch (final Throwable t) {
          error = ErrorUtils.aggregateError(error, t);
        }

        // now test whether it is a user-defined name or function parameter
        try {
          add = this.m_resolver.resolve(sequence, this.m_builder);
          if (add != null) {
            break getToken;
          }
        } catch (final Throwable t) {
          error = ErrorUtils.aggregateError(error, t);
        }

        // ok, it was none of it: throw an error
        RethrowMode.AS_ILLEGAL_ARGUMENT_EXCEPTION
            .rethrow(//
                (((("The string '" + string) + //$NON-NLS-1$
                "' cannot be parsed to a mathematical function. In particular, the sequence '") //$NON-NLS-1$
                + sequence) + //
                "' caused headache to our parser. See the causing/suppressed errors for additional information."//$NON-NLS-1$
                ), true, error);
      }

      CompoundFunctionParser.__add((lastAdded = add), result);
      index = seqEnd;
    }

    return result;
  }

  /**
   * add a token
   *
   * @param token
   *          the token
   * @param tokens
   *          the token list
   */
  private static final void __add(final Object token,
      final ArrayList<Object> tokens) {
    final int size;
    final _ParsedFunction func;
    Object last;

    size = (tokens.size() - 1);
    if ((size >= 0) && (!(token instanceof Brace))) {
      last = tokens.get(size);
      if (last instanceof _ParsedFunction) {
        func = ((_ParsedFunction) last);
        if (func.m_mode == _ParsedFunction.MODE_CALL) {
          if (func.m_func instanceof UnaryFunction) {
            func.m_mode = _ParsedFunction.MODE_PREFIX;
          } else {
            throw new IllegalArgumentException(//
                "Only unary functions can be called without braces around their arguments, but encountered token " //$NON-NLS-1$
                    + token + " after token " + func);//$NON-NLS-1$
          }
        }
      }
    }

    tokens.add(token);
  }

  /**
   * check whether {@code +} or {@code -} are unary prefix operators
   * (instead of binary ones)?
   *
   * @param before
   *          the previous object
   * @param result
   *          the list of all tokens
   * @param stack
   *          the stack
   * @return {@code true} if {@code +} and {@code -} should be treated as
   *         unary prefix operators (instead of binary ones), {@code false}
   *         otherwise, i.e., if they should be treated as binary operators
   */
  private static final boolean __arePlusMinusPrefixes(final Object before,
      final ArrayList<Object> result, final ArrayList<Object> stack) {
    Brace brace;
    int size;
    boolean closed;

    if (before == null) {
      return true;
    }

    if (before == CompoundFunctionParser.ARGUMENT_SEPARATOR) {
      return true;
    }

    if (before instanceof _ParsedFunction) {
      return (((_ParsedFunction) before).m_mode != _ParsedFunction.MODE_POSTFIX);
    }

    if (before instanceof Brace) {
      brace = ((Brace) before);
      if (brace.isOpening()) {
        // opening braces -> +/- are prefixes
        if (brace.getChar() == '|') {
          closed = false;

          stack.clear();

          for (final Object obj : result) {
            closed = false;
            if (obj instanceof Brace) {
              size = stack.size();
              if ((size > 0) && (stack.get(--size) == obj)) {
                stack.remove(size);
                closed = true;
              } else {
                stack.add(((Brace) obj).getOtherEnd());
              }
            }
          }

          stack.clear();

          return (!closed);
        }

        return true;
      }
    }

    return false;
  }

  /**
   * Parse the given character to an object
   *
   * @param currentChar
   *          the current character
   * @return the object representing the character
   */
  private static final Object __parseChar(final char currentChar) {
    final Char charConst;
    final _ParsedFunction parsed;

    if (currentChar <= ' ') {
      return CompoundFunctionParser.WHITE_SPACE;
    }

    parsed = MathematicalFunctionParser._parseChar(currentChar);
    if (parsed != null) {
      return parsed;
    }

    switch (currentChar) {
      case ',':
      case ';': {
        return CompoundFunctionParser.ARGUMENT_SEPARATOR;
      }

      default: {

        // check for white space
        if (Character.isWhitespace(currentChar)
            || Character.isISOControl(currentChar)) {
          return CompoundFunctionParser.WHITE_SPACE;
        }
        // detect braces
        charConst = Characters.CHARACTERS.getChar(currentChar);
        if (charConst instanceof Brace) {
          return charConst;
        }
      }
    }

    return null;
  }

  /**
   * Get the default parser for unary functions
   *
   * @return the default parser for unary functions
   */
  public static final CompoundFunctionParser<UnaryFunction> getDefaultUnaryFunctionParser() {
    return __DefaultUnaryFunctionParser.INSTANCE;
  }

  /**
   * Get the default parser for binary functions
   *
   * @return the default parser for binary functions
   */
  public static final CompoundFunctionParser<BinaryFunction> getDefaultBinaryFunctionParser() {
    return __DefaultBinaryFunctionParser.INSTANCE;
  }

  /**
   * Get the default parser for ternary functions
   *
   * @return the default parser for ternary functions
   */
  public static final CompoundFunctionParser<TernaryFunction> getDefaultTernaryFunctionParser() {
    return __DefaultTernaryFunctionParser.INSTANCE;
  }

  /**
   * Get the default parser for quaternary functions
   *
   * @return the default parser for quaternary functions
   */
  public static final CompoundFunctionParser<QuaternaryFunction> getDefaultQuaternaryFunctionParser() {
    return __DefaultQuaternaryFunctionParser.INSTANCE;
  }

  /**
   * Get the default function parser for a given arity
   * 
   * @param arity
   *          the arity
   * @return the parser
   */
  @SuppressWarnings({ "unchecked", "rawtypes" })
  public static final CompoundFunctionParser<MathematicalFunction> getDefaultFunctionParser(
      final int arity) {
    switch (arity) {
      case 1: {
        return ((CompoundFunctionParser) (getDefaultUnaryFunctionParser()));
      }
      case 2: {
        return ((CompoundFunctionParser) (getDefaultBinaryFunctionParser()));
      }
      case 3: {
        return ((CompoundFunctionParser) (getDefaultTernaryFunctionParser()));
      }
      case 4: {
        return ((CompoundFunctionParser) (getDefaultQuaternaryFunctionParser()));
      }
      default: {
        return new CompoundFunctionParser<>(
            FunctionBuilder.getInstanceForArity(arity),
            DefaultParameterRenderer.INSTANCE);
      }
    }
  }

  /** the holder for the default unary function parser */
  private static final class __DefaultUnaryFunctionParser {
    /** the globally shared instance */
    static final CompoundFunctionParser<UnaryFunction> INSTANCE = //
    new CompoundFunctionParser<>(UnaryFunctionBuilder.getInstance(),
        DefaultParameterRenderer.INSTANCE);
  }

  /** the holder for the default binary function parser */
  private static final class __DefaultBinaryFunctionParser {
    /** the globally shared instance */
    static final CompoundFunctionParser<BinaryFunction> INSTANCE = //
    new CompoundFunctionParser<>(BinaryFunctionBuilder.getInstance(),
        DefaultParameterRenderer.INSTANCE);
  }

  /** the holder for the default ternary function parser */
  private static final class __DefaultTernaryFunctionParser {
    /** the globally shared instance */
    static final CompoundFunctionParser<TernaryFunction> INSTANCE = //
    new CompoundFunctionParser<>(TernaryFunctionBuilder.getInstance(),
        DefaultParameterRenderer.INSTANCE);
  }

  /** the holder for the default quaternary function parser */
  private static final class __DefaultQuaternaryFunctionParser {
    /** the globally shared instance */
    static final CompoundFunctionParser<QuaternaryFunction> INSTANCE = //
    new CompoundFunctionParser<>(QuaternaryFunctionBuilder.getInstance(),
        DefaultParameterRenderer.INSTANCE);
  }
}
