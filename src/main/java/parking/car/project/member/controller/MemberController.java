package parking.car.project.member.controller;

import java.time.LocalDate;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.inject.Inject;
import lombok.RequiredArgsConstructor;
import parking.car.project.member.entity.Member;
import parking.car.project.member.service.MemberService;
import parking.car.project.member.dto.MemberDTO;

@Controller
@RequiredArgsConstructor
public class MemberController {
	private static final Logger logger = LogManager.getLogger(MemberController.class);
	@Inject
	private final MemberService memberService;

	@GetMapping("/MemberSelect")
	public String list(Model model) {
		model.addAttribute("list", memberService.findAllMembers());
		logger.info("list", model);
		return "member/member_select_view";
	}

	@GetMapping("/MemberSelectDetail")
	public String detail(Model model, @RequestParam("member_code") Integer member_code) {
		model.addAttribute("memberDTO", memberService.findMemberById(member_code));
		return "member/member_select_detail_view";
	}

	@GetMapping("/MemberInsert")
	public String insert() {
		return "member/member_insert_view";
	}

	@PostMapping("/MemberInsert")
	public String insert(Model model, MemberDTO memberDTO) {
		Member member = new Member();
		member.setMember_id(memberDTO.getMember_id());
		member.setMember_name(memberDTO.getMember_name());
		member.setMember_password(memberDTO.getMember_password());
		member.setMember_email(memberDTO.getMember_email());
		member.setMember_callnumber(memberDTO.getMember_callnumber());
		member.setMember_birthday(memberDTO.getMember_birthday());
		member.setMember_gender(memberDTO.getMember_gender());
		member.setMember_joinday(LocalDate.now());
		member.setMember_car_num1(memberDTO.getMember_car_num1());
		member.setMember_car_num2(memberDTO.getMember_car_num2());
		member.setMember_car_num3(memberDTO.getMember_car_num3());
		member.setMember_rating(memberDTO.getMember_rating());
		model.addAttribute("list", memberService.findAllMembers());
		memberService.saveMember(member);
		return "member/member_insert";
	}

	@GetMapping("/MemberUpdate")
	public String update(Model model, @RequestParam("member_code") Integer member_code) {
		Member member = memberService.findMemberById(member_code);
		if(member != null) {
			MemberDTO memberDTO = new MemberDTO();
			memberDTO.setMember_code(member.getMember_code());
			memberDTO.setMember_id(member.getMember_id());
			memberDTO.setMember_name(member.getMember_name());
			memberDTO.setMember_password(member.getMember_password());
			memberDTO.setMember_email(member.getMember_email());
			memberDTO.setMember_callnumber(member.getMember_callnumber());
			memberDTO.setMember_birthday(member.getMember_birthday());
			memberDTO.setMember_gender(member.getMember_gender());
			memberDTO.setMember_joinday(member.getMember_joinday());
			memberDTO.setMember_car_num1(member.getMember_car_num1());
			memberDTO.setMember_car_num2(member.getMember_car_num2());
			memberDTO.setMember_car_num3(member.getMember_car_num3());
			memberDTO.setMember_rating(member.getMember_rating());
			model.addAttribute("memberDTO", memberDTO);
		}
		return "member/member_update_view";
	}

	@PostMapping("/MemberUpdate")
	@Transactional
	public String update(MemberDTO memberDTO) {
	    // 기존 회원 엔티티를 데이터베이스에서 조회합니다.
	    Member member = memberService.findMemberById(memberDTO.getMember_code());
	    
	    if (member != null) {
	        // DTO에서 값을 설정합니다.
	        member.setMember_id(memberDTO.getMember_id());
	        member.setMember_name(memberDTO.getMember_name());
	        member.setMember_password(memberDTO.getMember_password());
	        member.setMember_email(memberDTO.getMember_email());
	        member.setMember_callnumber(memberDTO.getMember_callnumber());
	        member.setMember_birthday(memberDTO.getMember_birthday());
	        member.setMember_gender(memberDTO.getMember_gender());
	        member.setMember_car_num1(memberDTO.getMember_car_num1());
	        member.setMember_car_num2(memberDTO.getMember_car_num2());
	        member.setMember_car_num3(memberDTO.getMember_car_num3());
	        member.setMember_rating(memberDTO.getMember_rating());
	        
	        // 변경된 내용을 저장합니다.
	        memberService.saveMember(member);
	    }
	    
	    return "member/member_update"; // 수정 후 이동할 페이지를 반환합니다.
	}

	@GetMapping("/MemberDelete")
	public String delete() {
		return "member/member_delete_view";

	}

	@PostMapping("/MemberDelete")
	public String delete(@RequestParam("member_code") Integer member_code) {
		memberService.deleteMemberById(member_code);
		return "member/member_delete";

	}

//	// restcontroller는 <a>태그의 맵핑을 받지 못하므로 @controller에 맵핑을 설정한다.
//	@GetMapping("/MemberSelectView")
//	public String selectAllView() {
//		// ajax 쓸거면 리턴 수정 해야함
//		return null;
//
//	}
}