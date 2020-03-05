package kr.joyful.doit.domain.boardMember;

import kr.joyful.doit.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BoardMemberRepository extends JpaRepository<BoardMember, Long> {
    @Query("select bm from BoardMember as bm" +
            " left join fetch bm.member" +
            " where bm.board.id = :boardId")
    List<BoardMember> findBoardMemberById(Long boardId);
}
