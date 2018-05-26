package type;

/**
 * Enumeration to help define which value to calculate
 * <p>
 * <b>1st Derivative</b> - FUNCTION_X || FUNCTION_YY
 * <p>
 * <b>2nd Derivative</b> - FUNCTION_XX || FUNCTION_YY || FUNCTION_XY
 */
public enum equation {
    FUNCTION,
    FUNCTION_X,
    FUNCTION_Y,
    FUNCTION_XY,
    FUNCTION_XX,
    FUNCTION_YY
}
