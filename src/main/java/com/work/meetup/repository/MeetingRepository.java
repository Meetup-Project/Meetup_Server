package com.work.meetup.repository;

import com.work.meetup.domain.Meeting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MeetingRepository  extends JpaRepository<Meeting, Long> {

    @Override
    Optional<Meeting> findById(Long id);

}
