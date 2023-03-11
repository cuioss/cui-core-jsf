package de.cuioss.jsf.components.chart.options;

import java.util.List;

import de.cuioss.jsf.components.chart.axes.Axes;
import de.cuioss.jsf.components.chart.hook.PlotHookFunctionProvider;
import de.cuioss.jsf.components.chart.layout.Grid;
import de.cuioss.jsf.components.chart.layout.Series;
import de.cuioss.jsf.components.chart.layout.Title;
import de.cuioss.jsf.components.chart.model.NoDataIndicator;
import de.cuioss.jsf.components.chart.options.color.Color;
import de.cuioss.jsf.components.chart.options.legend.Legend;
import de.cuioss.jsf.components.chart.plugin.IPluginConsumer;
import de.cuioss.jsf.components.chart.plugin.PluginSupport;
import de.cuioss.jsf.components.chart.plugin.cursor.Cursor;
import de.cuioss.jsf.components.chart.plugin.highlighter.Highlighter;
import de.cuioss.jsf.components.chart.renderer.DataRenderer;
import de.cuioss.jsf.components.js.support.JsArray;
import de.cuioss.jsf.components.js.support.JsObject;
import lombok.EqualsAndHashCode;
import lombok.Setter;
import lombok.ToString;

/**
 * container to hold all of the merged options. Convienence for plugins.
 *
 * @author Eugen Fischer
 */
@ToString(callSuper = false, doNotUseGetters = true)
@EqualsAndHashCode(callSuper = false, doNotUseGetters = true)
public class Options extends JsObject implements IPluginConsumer, PlotHookFunctionProvider {

    private static final long serialVersionUID = 6181479150360964626L;

    private static final String HOOK_NAME = "options_hook";

    private final PluginSupport plugins = new PluginSupport();

    /**
     * prop: title
     * Title object. See <Title> for specific options. As a shortcut, you
     * can specify the title option as just a string like: title: 'My Plot'
     * and this will create a new title object with the specified text.
     */
    @Setter
    private Title title = new Title();

    /**
     * up to 4 axes are supported, each with its own options, see
     * {@linkplain de.cuioss.jsf.components.chart.axes.Axis} for axis
     * specific options.
     */
    private Axes axes = null;

    private Cursor cursor;

    /**
     * True to animate the series on initial plot draw (renderer dependent). Actual animation
     * functionality must be supported in the renderer.
     */
    private final boolean animate = false;

    // private baseCanvas ???
    /**
     * true to intercept right click events and fire a 'jqplotRightClick' event. this will also
     * block the context menu.
     */
    private final boolean captureRightClick = false;

    /**
     * A callable which can be used to preprocess data passed into the plot.
     * Will be called with 3 arguments: the plot data, a reference to the plot,
     * and the value of dataRendererOptions.
     */
    private DataRenderer dataRenderer;

    /**
     * Options that will be passed to the dataRenderer.
     * Can be of any type.
     */
    private DataRendererOptions dataRendererOptions;

    /**
     * True to animate series after a call to the replot() method. Use with caution! Replots can
     * happen very frequently under certain circumstances (e.g. resizing, dragging points) and
     * animation in these situations can cause problems.
     */
    private final int defaultAxisStart = 1;

    /**
     * True to execute the draw method even if the plot target is hidden.
     * Generally, this should be false. Most plot elements will not be sized/
     * positioned correclty if renderered into a hidden container. To render into
     * a hidden container, call the replot method when the container is shown.
     */
    private final boolean drawIfHidden = false;

    /**
     * Fill between 2 line series in a plot
     */
    private final FillOptions fillBetween = new FillOptions();

    /**
     * css spec for the font-family attribute. Default for the entire plot.
     */
    private String fontFamily;

    /**
     * css spec for the font-size attribute. Default for the entire plot.
     */
    private String fontSize;

    /**
     * See <Grid> for grid specific options.
     */
    private Grid grid;

    /**
     * Override grid setting
     *
     * @param gridValue {@link Grid}
     *
     * @return {@link Options} in fluent api style
     */
    public Options setGrid(final Grid gridValue) {
        this.grid = gridValue;
        return this;
    }

    /**
     * Legend
     */
    @Setter
    private Legend legend;

    /**
     * Highlighter
     */
    private Highlighter highlighter;

    /**
     * prop: series
     * Array of series object options.
     * see <Series> for series specific options.
     * this.series = [];
     */
    private JsArray<Series> series;

    /**
     * default options that will be applied to all series. {@linkplain #addSeriaOption(Series)} can
     * be used to define different seria options
     */
    @Setter
    private Series seriesDefaults;

    /**
     * Options to set up a mock plot with a data loading indicator if no data is specified.
     */
    private NoDataIndicator noDataIndicator;

    /**
     * colors to use for portions of the line below zero
     */
    private List<Color> negativeSeriesColors;

    /**
     * = $.jqplot.config.defaultColors;
     */
    private List<Color> seriesColors;

    private final boolean sortData = true;

    /**
     * prop: stackSeries
     * true or false, creates a stack or "mountain" plot.
     * Not all series renderers may implement this option.
     */
    private final boolean stackSeries = false;

    /**
     * a shortcut for axis syncTicks options. Not implemented yet.
     */
    private final boolean syncXTicks = true;

    /**
     * a shortcut for axis syncTicks options. Not implemented yet.
     */
    private final boolean syncYTicks = true;

    /**
     * prop textColor
     * css spec for the css color attribute. Default for the entire plot.
     */
    private final Color textColor = null;

    /**
     * Count how many times the draw method has been called while the plot is visible.
     * Mostly used to test if plot has never been dran (=0), has been successfully drawn
     * into a visible container once (=1) or draw more than once into a visible container.
     * Can use this in tests to see if plot has been visibly drawn at least one time.
     * After plot has been visibly drawn once, it generally doesn't need redrawing if its
     * container is hidden and shown.
     */
    private final int _drawCount = 0;

    /**
     * sum of y values for all series in plot.
     * used in mekko chart.
     */
    private final int _sumy = 0;

    private final int _sumx = 0;

    /**
     * {@linkplain Options} Constructor
     */
    public Options() {
        super("options");
    }

    @Override
    public String asJavaScriptObjectNotation() {
        this.addProperty(this.title);
        this.addProperty(this.axes);
        this.addProperty(this.seriesDefaults);
        this.addProperty("series", this.series);
        this.addProperty(this.cursor);
        this.addProperty(this.legend);
        this.addProperty(this.highlighter);
        this.addProperty(this.grid);
        return createAsJSONObjectWithoutName();
    }

    @Override
    public List<String> usedPlugins() {

        this.plugins.add(this.axes);
        this.plugins.add(this.seriesDefaults);
        if (null != this.series) {
            for (final Series seria : this.series) {
                this.plugins.add(seria);
            }
        }
        this.plugins.add(this.cursor);
        this.plugins.add(this.legend);
        this.plugins.add(this.highlighter);
        return this.plugins.usedPlugins();
    }

    @Override
    public String getHookFunctionCode() {
        if (null != this.highlighter) {
            return this.highlighter.getHookFunctionCode();
        }
        return "";
    }

    @Override
    public String getIdentifier() {
        return HOOK_NAME;
    }

    /**
     * @param seria {@linkplain Series} option object
     *
     * @return fluent api style
     */
    public Options addSeriaOption(final Series seria) {
        if (null != seria) {
            if (null == this.series) {
                this.series = new JsArray<>();
            }
            this.series.addValueIfNotNull(seria);
        }
        return this;
    }

    /**
     * Set or override highlighter settings
     *
     * @param value {@link Highlighter}
     *
     * @return fluent api style
     */
    public Options setHighlighter(final Highlighter value) {
        this.highlighter = value;
        return this;
    }

    /**
     * @return lazy initialized {@link Highlighter} object
     */
    public Highlighter getHighlighter() {
        if (null == this.highlighter) {
            this.highlighter = new Highlighter();
        }
        return this.highlighter;
    }

    /**
     * Set configured plugin Cursor
     *
     * @param value {@link Cursor}
     *
     * @return fluent api style
     */
    public Options setCursor(final Cursor value) {
        this.cursor = value;
        return this;
    }

    /**
     * lazy initialized
     *
     * @return {@linkplain Axes}
     */
    public Axes getAxes() {
        if (null == this.axes) {
            this.axes = new Axes();
        }
        return this.axes;
    }
}
