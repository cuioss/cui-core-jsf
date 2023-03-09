package com.icw.ehf.cui.components.bootstrap.composite;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.ValidatorException;

import org.junit.jupiter.api.Assertions;

import com.icw.ehf.cui.core.api.components.model.datalist.EditableDataListModel;
import com.icw.ehf.cui.core.api.validator.AbstractValidator;

@SuppressWarnings({ "rawtypes", "javadoc" })
@FacesValidator("test.EditableDataListValidator")
public class EditableDataListValidator extends AbstractValidator<EditableDataListModel> {

    @Override
    protected void validateTypeSave(final FacesContext context, final UIComponent component,
            final EditableDataListModel model) {
        Assertions.assertNotNull(model);
        throw new ValidatorException(new FacesMessage(""));
    }
}
