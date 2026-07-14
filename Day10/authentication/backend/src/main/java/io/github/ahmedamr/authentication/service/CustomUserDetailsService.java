package io.github.ahmedamr.authentication.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import io.github.ahmedamr.authentication.entity.AppUser;
import io.github.ahmedamr.authentication.entity.SecurityUser;
import io.github.ahmedamr.authentication.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

	private final UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
		AppUser user = userRepository.findWithRolesById(Long.parseLong(id))
				.orElseThrow(() -> new UsernameNotFoundException("{error.user-not-found}"));
		return new SecurityUser(user);
	}
}
