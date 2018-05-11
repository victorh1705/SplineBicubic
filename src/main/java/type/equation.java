package type;

/**
 * Enumeration to help define which value to calculate
 * <p>
 * <b>1st Derivative</b> - function_x || function_yy
 * <p>
 * <b>2nd Derivative</b> - function_xx || function_yy || function_xy
 */
public enum equation {
    function,
    function_x,
    function_y,
    function_xy,
    function_xx,
    function_yy
}
