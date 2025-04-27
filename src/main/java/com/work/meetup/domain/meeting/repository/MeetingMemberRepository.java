package com.work.meetup.domain.meeting.repository;

import com.work.meetup.domain.meeting.entity.Meeting;
import com.work.meetup.domain.meeting.entity.MeetingMember;
import com.work.meetup.domain.user.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MeetingMemberRepository extends CrudRepository<MeetingMember, Long> {
    Optional<MeetingMember> findByUserAndMeeting(User user, Meeting meeting);
    List<MeetingMember> findByUser(User user);
}
