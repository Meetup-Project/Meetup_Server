package com.work.meetup.domain.user.Facade;

import com.work.meetup.domain.user.entity.User;
import com.work.meetup.global.exception.customException.UserNotFoundException;
import com.work.meetup.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserFacade {

    private final UserRepository userRepository;

    public User getCurrentUser() {
        String id =
                SecurityContextHolder.getContext().getAuthentication().getName();
        return getUserById(id);
    }

    public User getUserById(String id) {
        return userRepository.findById((long) Integer.parseInt(id)).orElseThrow(()-> UserNotFoundException.EXCEPTION);
    }
}
