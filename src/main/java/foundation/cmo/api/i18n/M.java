package foundation.cmo.api.i18n;


import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

@Component
public class M {

	@Autowired
	private MessageSource messages;

	public String getString(String code, Object... args) {
		try {
			return messages.getMessage(code, args, Locale.forLanguageTag("pt-BR"));
		} catch (Exception e) {
			return code;
		}
	}
}