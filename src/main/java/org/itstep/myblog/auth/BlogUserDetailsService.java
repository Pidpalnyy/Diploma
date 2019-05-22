package org.itstep.myblog.auth;

import lombok.RequiredArgsConstructor;
import org.itstep.myblog.entities.User;
import org.itstep.myblog.repository.UsersRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.Collection;

@RequiredArgsConstructor
public class BlogUserDetailsService implements UserDetailsService {
    private final UsersRepository usersRepository;
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = usersRepository.getByName(s);
        if(user==null) return null;
        return new UserDetails() {
            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                return new ArrayList<>();
            }

            @Override
            public String getPassword() {
                return user.getPass();
            }

            @Override
            public String getUsername() {
                return user.getName();
            }

            @Override
            public boolean isAccountNonExpired() {
                return true;
            }

            @Override
            public boolean isAccountNonLocked() {
                return true;
            }

            @Override
            public boolean isCredentialsNonExpired() {
                return true;
            }

            @Override
            public boolean isEnabled() {
                return true;
            }
        };
    }
}
