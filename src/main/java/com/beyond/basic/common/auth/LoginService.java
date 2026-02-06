package com.beyond.basic.common.auth;

import com.beyond.basic.author.domain.Author;
import com.beyond.basic.author.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Component
public class LoginService implements UserDetailsService {
    private final AuthorRepository authorRepository;

    @Autowired
    public LoginService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    // 매개변수로 주어지는 username는 사용자가 입력한 email값이다.
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Author author = authorRepository.findByEmail(username).orElseThrow(EntityNotFoundException::new);
        List<GrantedAuthority> authorityList = new ArrayList<>();
        authorityList.add(new SimpleGrantedAuthority("ROLE_" + author.getRole()));
        return new User(author.getEmail(), author.getPassword(), authorityList);
    }
}
