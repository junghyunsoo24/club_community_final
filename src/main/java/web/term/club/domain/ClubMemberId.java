package web.term.club.domain;

import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class ClubMemberId implements Serializable {
    private long clubId;
    private long memberId;
}