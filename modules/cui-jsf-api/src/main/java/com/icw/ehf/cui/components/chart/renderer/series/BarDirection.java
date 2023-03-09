package com.icw.ehf.cui.components.chart.renderer.series;

import lombok.Getter;

/**
 * ‘vertical’ = up and down bars, ‘horizontal’ = side to side bars
 *
 * @author i000576
 */
public enum BarDirection {

    /** */
    VERTICAL("vertical"),
    /** */
    HORIZONTAL("horizontal");

    @Getter
    private final String direction;

    BarDirection(final String value) {
        this.direction = value;
    }
}
