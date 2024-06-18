package web.term.club.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import web.term.club.domain.Enum.ClubApprovalStatus;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClubAcceptRequest {
    Long memberId;
    Long id;
    String refuseInfo;
    ClubApprovalStatus clubApprovalStatus;
}
