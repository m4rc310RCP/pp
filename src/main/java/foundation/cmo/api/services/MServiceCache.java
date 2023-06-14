package foundation.cmo.api.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import foundation.cmo.api.db.models.auth.MUser;
import foundation.cmo.api.db.repositories.MUserRepository;


@Service
public class MServiceCache {
	@Autowired
	private MUserRepository userRepository;

	@Cacheable("username")
	public MUser getUser(String username) {
		return userRepository.findById(username).orElse(null);
	}
}
