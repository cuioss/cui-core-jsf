package de.icw.cui.core.api.components.model.resultContent;

import com.icw.ehf.cui.portal.cdi.api.IDisplayNameProviderTypedGenerator;

import de.cuioss.test.valueobjects.ValueObjectTest;
import de.cuioss.test.valueobjects.api.contracts.VerifyConstructor;
import de.cuioss.test.valueobjects.api.generator.PropertyGenerator;

@PropertyGenerator(IDisplayNameProviderTypedGenerator.class)
@VerifyConstructor(of = { "notificationBoxValue", "notificationBoxState" })
class NotificationBoxMessageTest extends ValueObjectTest<NotificationBoxMessage> {

}
