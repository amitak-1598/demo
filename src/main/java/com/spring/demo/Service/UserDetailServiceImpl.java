package com.spring.demo.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.spring.demo.Repository.UserRepository;
import com.spring.demo.entity.User;

@Component
public class UserDetailServiceImpl implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// get user from db
		User user = userRepository.findByUsername(username);
		if (user != null) {
			// user found
			return org.springframework.security.core.userdetails.User.builder().username(user.getUsername())
					.password(user.getPassword()).roles(user.getRoles().toArray(new String[0])).build();
		}
		throw new UsernameNotFoundException("User not found in the db");

	}

}
