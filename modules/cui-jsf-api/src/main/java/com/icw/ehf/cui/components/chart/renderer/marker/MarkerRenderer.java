package com.icw.ehf.cui.components.chart.renderer.marker;

import com.icw.ehf.cui.components.chart.renderer.Renderer;

import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author i000576
 * @see <a href=
 *      "http://www.jqplot.com/docs/files/jqplot-markerRenderer-js.html#$.jqplot.MarkerRenderer">MarkerRenderer</a>
 */
@ToString
@EqualsAndHashCode(callSuper = false)
public class MarkerRenderer extends Renderer {

    /** serial Version UID */
    private static final long serialVersionUID = 4001151503269081389L;

    /**
     *
     */
    public MarkerRenderer() {
        super("$.jqplot.MarkerRenderer");
    }

}
