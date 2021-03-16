package com.ozeeesoftware.forumrestapi.config;

import com.ozeeesoftware.forumrestapi.model.user.Role;
import com.ozeeesoftware.forumrestapi.model.user.User;
import com.ozeeesoftware.forumrestapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
public class FirstSetupConfig implements ApplicationListener<ContextRefreshedEvent> {

    private boolean alreadySetup = false;

    @Autowired
    private UserRepository userRepo;

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (!alreadySetup) {
            User adminUser = userRepo.findByUsername("admin");
            if (adminUser == null) {

                // CREATE ADMIN USER
                User newUser = new User();
                newUser.setUserName("admin");
                newUser.setPassword("admin123");
                newUser.setName("admin");
                newUser.setSurname("admin");
                newUser.setEmail("admin");
                newUser.setRole(Role.ADMIN);
                userRepo.save(newUser);
            }
            alreadySetup = true;
        }
    }


}
