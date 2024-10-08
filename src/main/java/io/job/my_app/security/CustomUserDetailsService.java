package io.job.my_app.security;


import io.job.my_app.Entity.User;
import io.job.my_app.Entity.Security.UserRole;
import io.job.my_app.repos.RolesRepo;
import io.job.my_app.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepo usersRepository;
@Autowired
private RolesRepo rolesRepo;
   /* @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User users = this.usersRepository.findByEmail(email);
        if (users == null) {
            System.out.println("user not found");
            try {
                throw new ResourceNotFoundException("user not found","email",email);
            } catch (ResourceNotFoundException e) {
                throw new RuntimeException(e);
            }
        }else if(!users.isEnabled()){
            System.out.println("user is disabled");
            try {
                throw new UserDisabledException("User is disabled");
            } catch (UserDisabledException e) {
                throw new RuntimeException(e);
            }
        }

        return users;
    }*/
   @Override
   public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
       User user = usersRepository.findByEmail(email);
       if (user == null) {
           throw new UsernameNotFoundException("User not found with email: " + email);
       } else if (!user.isEnabled()) {
           throw new DisabledException("User is disabled");
       }


      /* Set<UserRole> roles = new HashSet<>();
       for (UserRole roleName : user.getUserRoles()) {
           UserRole role = roleName;
           if (role == null) {
               throw new IllegalArgumentException("Role not found: " + roleName);
           }
           roles.add(role);
       }
       user.setUserRoles(roles);

       usersRepository.save(user);*/
       return new User(
               user.getEmail(),
               user.getPassword(),
               user.isEnabled(),
               true,
               true,
               true,
             user.getUserRoles(),
               user.getUserId()       );
   }



    private Collection<? extends GrantedAuthority> getAuthorities(Set<UserRole> roles) {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getRole().getName()))
                .collect(Collectors.toList());
    }

}
