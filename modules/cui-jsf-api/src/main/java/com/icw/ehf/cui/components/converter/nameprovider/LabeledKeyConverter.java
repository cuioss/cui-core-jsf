package com.icw.ehf.cui.components.converter.nameprovider;

import java.text.MessageFormat;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.FacesConverter;

import com.icw.ehf.cui.core.api.application.bundle.CuiResourceBundleAccessor;
import com.icw.ehf.cui.core.api.converter.AbstractConverter;
import com.icw.ehf.cui.core.api.security.CuiSanitizer;

import de.cuioss.uimodel.nameprovider.LabeledKey;

/**
 * Converter for the type {@link LabeledKey}
 *
 * @author Oliver Wolff
 */
@FacesConverter(forClass = LabeledKey.class)
public class LabeledKeyConverter extends AbstractConverter<LabeledKey> {

    private final CuiResourceBundleAccessor bundleAccessor = new CuiResourceBundleAccessor();

    @Override
    protected String convertToString(final FacesContext context,
            final UIComponent component, final LabeledKey value) {
        String result;
        if (value.getParameter().isEmpty()) {
            result = bundleAccessor.getValue().getString(value.getContent());
        } else {
            result = MessageFormat.format(bundleAccessor.getValue().getString(value.getContent()),
                    value.getParameter().toArray(new Object[value.getParameter().size()]));
        }
        return CuiSanitizer.COMPLEX_HTML_PRESERVE_ENTITIES.apply(result);
    }

}
