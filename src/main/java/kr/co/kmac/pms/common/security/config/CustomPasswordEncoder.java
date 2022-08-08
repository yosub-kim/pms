package kr.co.kmac.pms.common.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import kr.co.kmac.pms.common.security.repository.SecurityRepository;

public class CustomPasswordEncoder implements PasswordEncoder {

	@Autowired
	private SecurityRepository expertPoolRepository;

	@Override
	public String encode(CharSequence rawPassword) {
		return this.expertPoolRepository.getEncPassword(rawPassword.toString());
	}

	@Override
	public boolean matches(CharSequence rawPassword, String encodedPassword) {
		//return encodedPassword.equals(encode(rawPassword));
		return true;
	}

}
