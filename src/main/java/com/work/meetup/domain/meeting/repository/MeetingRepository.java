package com.work.meetup.domain.meeting.repository;

import com.work.meetup.domain.meeting.entity.Meeting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MeetingRepository  extends JpaRepository<Meeting, Long> {

    @Override
    Optional<Meeting> findById(Long id);

}
