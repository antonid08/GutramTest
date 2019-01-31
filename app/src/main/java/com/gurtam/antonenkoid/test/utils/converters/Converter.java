package com.gurtam.antonenkoid.test.utils.converters;

/**
 * General purpose value converter interface.
 *
 * <p>It's implementation responsibility how to convert values an how to handle errors.</p>
 *
 * <ul>Use cases:
 *  <li>data binding</li>
 *  <li>data transformation</li>
 *  <li>etc.</li>
 * </ul>
 *
 * @author antonenkoid
 */
public interface Converter<I, O> {

    /**
     * Converts specified value.
     *
     * @param input a value to convert.
     * @return output an output value.
     */
    O convert(I input);

}