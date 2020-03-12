package kr.joyful.doit.web.board;

import kr.joyful.doit.domain.board.Board;
import kr.joyful.doit.domain.boardMember.BoardMember;
import kr.joyful.doit.domain.member.Member;
import kr.joyful.doit.domain.team.Team;
import kr.joyful.doit.service.board.BoardService;
import kr.joyful.doit.service.member.MemberService;
import kr.joyful.doit.service.team.TeamService;
import kr.joyful.doit.web.member.CurrentUser;
import kr.joyful.doit.web.member.MemberInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class BoardController {

    private final BoardService boardService;
    private final TeamService teamService;
    private final MemberService memberService;

    @PostMapping("/api/board")
    public ResponseEntity<?> create(@RequestBody BoardCreateRequestDto dto, @CurrentUser MemberInfo memberInfo) throws URISyntaxException {
        Team findTeam = teamService.findTeamById(dto.getTeamId());
        Long boardId = boardService.save(dto.toEntity(findTeam), memberInfo.getMember());
        return ResponseEntity.created(new URI("/api/board/"+boardId)).build();
    }

    @GetMapping("/api/board/{boardId}")
    public ResponseEntity<?> findBoardById(@PathVariable Long boardId) {
        List<BoardMember> boardMemberList = boardService.findBoardMemberByBoardId(boardId);
        BoardResponseDto board = new BoardResponseDto(boardMemberList);
        return ResponseEntity.ok(board);
    }

    @PutMapping("/api/board/{boardId}/member/{memberId}")
    public ResponseEntity<?> inviteMemberInBoard(@PathVariable Long boardId, @PathVariable Long memberId) {
        Board findBoard = boardService.findById(boardId);
        Member findMember = memberService.findMemberById(memberId);
        boardService.invite(findBoard, findMember);
        return ResponseEntity.ok().build();
    }
}
