package kr.co.kmac.pms.common.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.kmac.pms.common.domain.PmsUserDetail;
import kr.co.kmac.pms.common.security.repository.SecurityRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
@Transactional
public class SecurityService implements UserDetailsService {

	@Autowired
	private SecurityRepository securityRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		PmsUserDetail user = securityRepository.getUserByUserId(username);
		if (user == null) {
			throw new UsernameNotFoundException(username);
		}
		return (UserDetails) user;
	}

}
