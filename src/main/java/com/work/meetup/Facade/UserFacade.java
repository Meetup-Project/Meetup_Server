package com.work.meetup.Facade;

import com.work.meetup.domain.User;
import com.work.meetup.exception.customException.UserNotFoundException;
import com.work.meetup.repository.UserRepository;
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
