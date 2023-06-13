package foundation.cmo.api;

import java.util.Locale;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import io.leangen.graphql.metadata.messages.MessageBundle;

@SpringBootApplication
@EnableCaching
@EnableScheduling
public class ApiApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(ApiApplication.class, args);
	}

	
	@Bean
	MessageBundle messageBundle() {
		return key -> getString(key);
	}
	
	public String getString(String pattern, Object... args) {
		try {
			return messageSource().getMessage(pattern, args, Locale.forLanguageTag("pt-BR"));
		} catch (Exception e) {
			return pattern;
		}
	}
	
	@Bean
	LocaleResolver localeResolver() {
		SessionLocaleResolver sessionLocaleResolver = new SessionLocaleResolver();
		sessionLocaleResolver.setDefaultLocale(Locale.forLanguageTag("pt-BR"));
		return sessionLocaleResolver;
	}

	@Bean("messageSource")
	MessageSource messageSource() {
		ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
		messageSource.setBasenames("messages/message");
//		messageSource.setDefaultEncoding("UTF-8");
		return messageSource;
	}
}
