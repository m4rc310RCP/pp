package foundation.cmo.api.services;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Locale;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.jpa.repository.JpaRepository;

import foundation.cmo.api.utils.CopyFieldsUtils;

public class MService {
	
	@Autowired 
	private MessageSource messages;
	
	protected <T> void cloneObject(String sfield, String jpaMethod, JpaRepository<T,?> repository, T value) {
		try {
			Class<? extends Object> type = value.getClass();
			Field field = type.getDeclaredField(sfield);
			field.setAccessible(true);

			Object obj = field.get(value);

			if (obj == null) {
				return;
			}

			Class<?> repositoryType = repository.getClass();

			Method method = CopyFieldsUtils.getMethod(repositoryType, jpaMethod);
			method.setAccessible(true);

			Optional<?> optional = (Optional<?>) method.invoke(repository, obj);
			if (optional.isPresent()) {
				CopyFieldsUtils.copyAtoB(optional.get(), value);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getString(String code, Object... args) {
		return messages.getMessage(code, args, Locale.forLanguageTag("pt-BR"));
	}
	
}
