package web.term.club.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.term.club.domain.Club;
import web.term.club.domain.ClubMember;
import web.term.club.domain.Enum.Condition;
import web.term.club.domain.Enum.Role;
import web.term.club.domain.Member;
import web.term.club.repository.ClubMemberRepository;
import web.term.club.service.ClubMemberService;

import java.util.List;
@Service
@Transactional
public class ClubMemberServiceImpl implements ClubMemberService {

    @Autowired
    private ClubMemberRepository clubMemberRepository;

    @Override
    public List<ClubMember> getAllClubMember(Club club, Member requestMember) throws Exception {
        if(requestMember.getRole() != Role.MASTER){
            return null;
        }
        return clubMemberRepository.findByClub(club);
    }

    @Override
    public List<ClubMember> getWaitingClubMember(Club club, Member requestMember) throws Exception {
        if(requestMember.getRole() != Role.MASTER){
            return null;
        }
        return clubMemberRepository.findByClubAndCondition(club, Condition.WAITING );
    }

    @Override
    public ClubMember approveClubMember(Member requestMember, ClubMember targetMember) throws Exception {
        if(requestMember.getRole() != Role.MASTER){
            return null;
        }
        ClubMember clubMember = clubMemberRepository.findFirstById(targetMember.getId());
        clubMember.setCondition(Condition.BELONG);
        return clubMemberRepository.save(clubMember);
    }

    @Override
    public ClubMember rejectClubMember(Member requestMember, ClubMember targetMember) throws Exception {
        if(requestMember.getRole() != Role.MASTER){
            return null;
        }
        ClubMember clubMember = clubMemberRepository.findFirstById(targetMember.getId());
        clubMember.setCondition(Condition.REFUSE);
        return clubMemberRepository.save(clubMember);
    }

    @Override
    public ClubMember banClubMember(Member requestMember, ClubMember banedMember) throws Exception {
        if(requestMember.getRole() != Role.MASTER){
            return null;
        }
        ClubMember clubMember = clubMemberRepository.findFirstById(banedMember.getId());
        clubMember.setCondition(Condition.WITHDRAWAL);
        return clubMemberRepository.save(clubMember);
    }
}
