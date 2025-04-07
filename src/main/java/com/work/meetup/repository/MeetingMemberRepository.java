package com.work.meetup.repository;

import com.work.meetup.domain.Meeting;
import com.work.meetup.domain.MeetingMember;
import com.work.meetup.domain.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MeetingMemberRepository extends CrudRepository<MeetingMember, Long> {
    Optional<MeetingMember> findByUserAndMeeting(User user, Meeting meeting);
    List<MeetingMember> findByUser(User user);
}
