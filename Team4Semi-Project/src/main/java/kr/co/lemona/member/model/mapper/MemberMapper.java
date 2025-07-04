package kr.co.lemona.member.model.mapper;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import kr.co.lemona.member.model.dto.Member;

@Mapper
public interface MemberMapper {
   

	/**
	 * 로그인 SQL 실행
	 * @param memberEmail
	 * @return
	 */
	Member login(String memberId);

	/**
	 * 이메일 중복검사
	 * @param memberEmail
	 * @return
	 */
	int checkEmail(String memberEmail);

	/**
	 * 닉네임 중복검사
	 * @param memberNickname
	 * @return
	 */
	int checkNickname(String memberNickname);
	
	/** 아이디 체크
	 * @param memberId
	 * @return
	 */
	int checkId(String memberId); // 추가

	/**
	 * 회원 가입
	 * @param inputMember
	 * @return
	 */
	int register(Member inputMember);

	/** 아이디 찾기
	 * @param params
	 * @return
	 */
	String findIdByNameAndEmail(Map<String, String> params);
	
	/** 비밀번호 찾기
	 * @param member
	 * @return
	 * Map 으로 받아서 params 로 받아야 함
	 */
	Map<String, String> findUserByIdNameEmail(Member member);


	/** 비밀번호 업데이트
	 * @param memberId
	 * @param newPassword
	 * @return
	 */
	boolean updatePassword(@Param("memberId") String memberId, @Param("newPassword") String newPassword);
	



}
