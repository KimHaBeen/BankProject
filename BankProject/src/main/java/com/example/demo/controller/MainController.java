package com.example.demo.controller;

import java.io.IOException;
import java.net.http.HttpRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.jpa.AccountRepo;
import com.example.demo.jpa.EmployeeRepo;
import com.example.demo.jpa.ManagerRepo;
import com.example.demo.jpa.MemberRepo;
import com.example.demo.vo.AccountVO;
import com.example.demo.vo.ManagerVO;
import com.example.demo.vo.MemberVO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
public class MainController {
	
	@Autowired
	MemberRepo memberRepo;
	@Autowired
	ManagerRepo managerRepo;
	@Autowired
	EmployeeRepo employeRepo;
	@Autowired
	AccountRepo accountRepo;
	
	
	
	
	@RequestMapping(value="/")
	public ModelAndView index() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("index");
		return mav;
	}
	@RequestMapping(value="/register")
	public ModelAndView register(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		List<Integer> employeeNum = employeRepo.selectEnployeeNum();
		
		List<String> selectID = memberRepo.selectID();
		System.out.println(selectID);
//		System.out.println("employeeNum >>> " + employeeNum);
		List<String> member_id = memberRepo.selectMemberId();
		mav.addObject("member_id", member_id);
		mav.addObject("employeeNum", employeeNum);
		mav.setViewName("member/register");
		return mav;
	}
	@RequestMapping(value="/register_Account")
	public ModelAndView registerAccount(MemberVO member) {
		ModelAndView mav = new ModelAndView();
		memberRepo.save(member);
//		System.out.println("member >> " + member);
		mav.setViewName("forward:/");
		return mav;
	}
	@RequestMapping(value="/manager_Account")
	public ModelAndView manager_Account(ManagerVO manager, HttpSession session) {
		
		ModelAndView mav = new ModelAndView();

		try {
			managerRepo.save(manager);
			mav.setViewName("forward:/");
			return mav;
			
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("부모 키가 없습니다.");
			mav.setViewName("member/register");
			return mav;
		}
	}
	@RequestMapping(value="/login")
	public ModelAndView login() {
		ModelAndView mav = new ModelAndView();
		List<String> memberId = new ArrayList<>();
		List<String> memberPw = new ArrayList<>();
		List<String> managerId = new ArrayList<>();
		List<String> managerPw = new ArrayList<>();
		
		List<Map<String,String>> memberidpw = memberRepo.selectMemberIdPw();
		List<Map<String,String>> manageridpw = managerRepo.selectManagerIdPw();
		
//		System.out.println("여기다 >>> " + manageridpw.get(0).keySet());
		
		for(Map<String, String> a : memberidpw) {
			 memberId.add(a.get("ID").toString());
			 memberPw.add(a.get("PW").toString());
		}
		
		for(Map<String, String> a : manageridpw) {
			managerId.add(a.get("MANAGER_ID").toString());
			managerPw.add(a.get("MANAGER_PW").toString());
		}
		
		System.out.println(" >>> " + managerId.toString());
		System.out.println(" >>> " + managerPw.toString());
		mav.addObject("memberId", memberId);
		mav.addObject("memberPw", memberPw);
		mav.addObject("managerId", managerId);
		mav.addObject("managerPw", managerPw);
		
		mav.setViewName("login/login");
		return mav;
	}
	@RequestMapping(value="/loginController")
	public ModelAndView loginControl(HttpServletResponse response,HttpServletRequest request, MemberVO mem, HttpSession session) throws IOException{
		ModelAndView mav = new ModelAndView();
		
		MemberVO dbMem = null;
		
		String id = request.getParameter("id");
		String pw = request.getParameter("pw");
		dbMem = memberRepo.loginIdPw(id, pw);
		if(dbMem != null) {
			System.out.println("DB: >>"+ dbMem);
			System.out.println("로그인 완료");
			session.setAttribute("login", dbMem);
			mav.setViewName("forward:/");
			return mav;
			
		} else {
			System.out.println("로그인 실패");
			//일단 더 해야됨(메인페이지 말고 현재 페이지로 다시오게)
			mav.setViewName("forward:/");
			//response.sendRedirect(request.getHeader("referer"));
			return mav;
		}
	}
	@RequestMapping(value="mangerLoginController")
	public ModelAndView mangerLoginController(ManagerVO manager, HttpSession session) {
		ModelAndView mav = new ModelAndView();
		System.out.println(">>>>>>>>>>>>");
		System.out.println("id: "+ manager.getManager_id() + "    pw: "+manager.getManager_pw());
		
		ManagerVO dbManager = null;
		
		String id = manager.getManager_id();
		String pw = manager.getManager_pw();
		try {
			dbManager = managerRepo.getById(id);
			System.out.println("DB: >>"+ dbManager);
			System.out.println("로그인 완료");
			String managername = managerRepo.selectName(id);
			session.setAttribute("managername", managername);
			session.setAttribute("managerLogin", dbManager);
			mav.setViewName("admin/managePage");
			
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("로그인 실패");
			mav.setViewName("forward:/");
		}
		return mav;
	}
	@RequestMapping(value="/logout")
	public ModelAndView logout(HttpSession session) {
			ModelAndView mav = new ModelAndView();
			session.removeAttribute("login");
			mav.setViewName("forward:/");
			return mav;
	}
	@RequestMapping(value="/managerLogout")
	public ModelAndView managerLogout(HttpSession session) {
		ModelAndView mav = new ModelAndView();
		session.removeAttribute("managerLogin");
		session.removeAttribute("managername");
		mav.setViewName("forward:/");
		return mav;
	}
	@RequestMapping(value="/mypage")
	public ModelAndView mypage(HttpServletRequest request, HttpSession session) {
		ModelAndView mav = new ModelAndView();
		MemberVO vo = (MemberVO) session.getAttribute("login");
		String id = vo.getId();
		MemberVO member = memberRepo.getById(id);
		List<AccountVO> account = accountRepo.selectAllById(id);
		mav.addObject("member", member);
		mav.addObject("account", account);
		mav.setViewName("member/mypage");
		return mav;
	}
	
	@ResponseBody
	@PostMapping("/id_ck")
	public Map<String, String> idCK(@RequestParam(value="id") String id) {
		
		Map<String, String> response = new HashMap<>();
		String i = memberRepo.idCK(id);
		
		if(i.equals("1")) {
			response.put("status","1");
		}else {
			response.put("status","0");
		}
		return response;
	}
	
	@ResponseBody
	@RequestMapping(value="/delete_ck")
	public String delelte_ck(HttpSession session) {
		MemberVO vo = (MemberVO) session.getAttribute("login");
		String id = vo.getId();
		System.out.println(id);
		String i = accountRepo.delete_ck(id);
		
		if(i.equals("0")) {
			return "0";
		}else {
			return i;
		}
	}
	
	@ResponseBody
	@PostMapping("/pw_ck")
	public String pw_ck(@RequestBody MemberVO vo, HttpSession session, HttpServletRequest request) {
		MemberVO membervo = (MemberVO) session.getAttribute("login");
		System.out.println(vo);
		String id = membervo.getId();
		System.out.println(id);
		String pw = vo.getPw() ;
		System.out.println(pw);
		String i = memberRepo.pw_ck(id, pw);
		 System.out.println("=============>"+i);
		 session = request.getSession(false);
		 
			 if(i.equals("1")) {
				 try {
					 if(session != null) {
						 session.invalidate();
						 memberRepo.delete_member(id, pw);
				 		}
				 	}	catch (Exception e) {
				 }
				 return "1";
			 }else {
				 return pw;
			}
		
	}
	
}
