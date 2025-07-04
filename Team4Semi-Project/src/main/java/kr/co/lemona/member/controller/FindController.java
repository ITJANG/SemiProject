package kr.co.lemona.member.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import kr.co.lemona.member.model.dto.Member;
import kr.co.lemona.member.model.service.MemberService;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/find")
@Slf4j
public class FindController {

	@Autowired
	private MemberService memberService; // MemberService에 아이디 찾기 메서드 추가해야 함

	@ResponseBody
	@PostMapping("/findId")
	public Map<String, Object> findId(@RequestBody Map<String, String> paramMap) {

		String foundId = memberService.findIdByNameAndEmail(paramMap);

		Map<String, Object> result = new HashMap<>();
		result.put("foundId", foundId);

		return result;
	}

	// 비밀번호 찾기 페이지에서 비밀번호 찾기 완료
	@ResponseBody
	@PostMapping("/findPw")
	public Map<String, Object> findPw(@RequestBody Member member, RedirectAttributes ra, Model model) {

		Map<String, Object> result = new HashMap<>();
		System.out.println();
		Map<String, String> foundUser = memberService.findUserByIdNameEmail(member);
		System.out.println(foundUser);
		if (foundUser != null) {
			ra.addFlashAttribute("message", "비밀번호 찾기 성공");
			result.put("sucess", true);
		} else {
			ra.addFlashAttribute("message", "비밀번호 찾기 실패");
			result.put("sucess", false);
		}
		model.addAttribute("url", "reset/findpw-update");

		return result;
	}

	@GetMapping("/findpw-update")
	public String findPwUpdate() {
		return "reset/findpw-update";
	}

	// 하는 중
	@PostMapping("/findpw-update")
	@ResponseBody
	public Map<String, Object> updatePassword(
	        @RequestParam String memberId,
	        @RequestParam String newPassword,
	        @RequestParam String confirmPassword) {

	    Map<String, Object> response = new HashMap<>();

	    boolean success = memberService.updatePassword(memberId, newPassword);

	    if (success) {
	        response.put("status", "success");
	        response.put("message", "비밀번호가 성공적으로 변경되었습니다.");
	    } else {
	        response.put("status", "fail");
	        response.put("message", "비밀번호 변경에 실패했습니다.");
	    }

	    return response;
	}

}
