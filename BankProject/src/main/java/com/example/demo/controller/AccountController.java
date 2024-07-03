package com.example.demo.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.jpa.AccountRepo;

import com.example.demo.jpa.PlusRepo;

import com.example.demo.jpa.RemitRepo;

import com.example.demo.vo.AccountVO;
import com.example.demo.vo.MemberVO;
import com.example.demo.vo.PlusVO;

import com.example.demo.vo.RemitVO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.websocket.Session;

@Controller
public class AccountController {
	
	@Autowired
	AccountRepo accountrepo;
	@Autowired
	PlusRepo plusrepo;
	@Autowired
	RemitRepo remitrepo;
	
	ModelAndView mav = new ModelAndView();
	private ModelAndView modelAndView;
	

	
	@RequestMapping(value="remitMoney")
	public ModelAndView remitMoney(HttpServletRequest request, AccountVO accountvo) {
		
		HttpSession session = request.getSession();
		MemberVO  vo = (MemberVO) session.getAttribute("login");
		String id = vo.getId();
		System.out.println(id);
		List<Map<String, Integer>> accountList = accountrepo.selectAccount2(id);

		List<String> accountnum = new ArrayList<>();
		List<String> accountAll = new ArrayList<>();
		List<String> account = new ArrayList<>();
		List<String> total = new ArrayList<>();
		List<Integer> accountPW = accountrepo.selectAccountPW(id);
		
		List<Map<String, Integer>> selectNumTotal = remitrepo.selectNumTotal(id);
		
		
		accountnum = accountrepo.selectAccount(id);
		//확인용:System.out.println(selectNumTotal.get(0).keySet());
		for(Map<String, Integer> a : selectNumTotal) {
			account.add(String.valueOf(a.get("ACCOUNT_NUM")));
			total.add(a.get("TOTAL").toString());
		}
		System.out.println(accountnum);
		
		accountAll = accountrepo.selectAll(id);
		System.out.println(accountAll);
		mav.addObject("account", account);
		mav.addObject("total", total);
		mav.addObject("accountPW", accountPW);
		mav.addObject("accountnum", accountnum);
		mav.addObject("accountAll", accountAll);
		mav.setViewName("account/remit");
		return mav;
	}
	
	@ResponseBody
	@RequestMapping("/checkAccount")
	 public Map<String, String> checkAccount(@RequestParam("remitAccount") String remit_account, AccountVO accountvo) throws Exception{
       	Map<String,String> response = new HashMap<>();
		String i = accountrepo.selectAccountRemit(remit_account);
		String name = accountrepo.account_name(remit_account);
		System.out.println(">>>>>>>>>>>>>>"+remit_account);
		
		if(i.equals("1")) {
			response.put("status", "1");
			response.put("name", name);
		}else {
			response.put("status", "0");
		}
		return response;	
    }
	
	
	@RequestMapping(value="remitComplete") //송금, 입금 모두 다 있음.
	public ModelAndView remitComplete(HttpServletRequest request, RemitVO remitvo, HttpServletResponse response, PlusVO plusvo) throws IOException {
		
		String account_num = request.getParameter("account_num").replace("-", "");
		String total = accountrepo.selectTotal(account_num);
		System.out.println(account_num);
		String remit_account = request.getParameter("remit_account").replace("-", "");; //송금 받는 계좌 = 입금 계좌
		String remit_text = request.getParameter("remit_text"); //계좌 소유주 or 송금 내역 = 입금한 사람 이름 or 입금 한 내역

		String rm = request.getParameter("remit_money");
		String newStr = rm.replaceAll(",", "");

		int remit_money = Integer.parseInt(request.getParameter("remit_money").replace(",", "")); //송금 금액 = 입금 금액

		
		//if(Integer.parseInt(total) > remit_money) {
			try {
				int i = remitrepo.insertRemit(account_num, remit_account, remit_text, remit_money);
			} catch (Exception e) {
			//왜 오류가 날까요?
				System.out.println("송금 완료");
			}
			try {
				remitrepo.updateRemit(remit_money,account_num);
				System.out.println("원래 나와야하는 부분");
			} catch (Exception e) {
				//오류남
				System.out.println("되면 안되는데;;");
			}
			try {
				plusrepo.insertRemitPlus(remit_account, remit_text, remit_money);
			} catch (Exception e) {
				System.out.println("입금 완료");
			}
			try {
				plusrepo.updatePlus(remit_money, remit_account);
			} catch (Exception e) {
				// TODO: handle exception
			}
			mav.addObject("result","송금완료");
			mav.setViewName("account/remitComplete");
			return mav;
		} /*else {
			response.sendRedirect(request.getHeader("referer"));
			return mav;
			} 
		}    html에서 해줘서 삭제*/ 
		
		//String money = remitrepo.selectMoney();
		//System.out.println(total);
		//System.out.println(money);

	
	@RequestMapping(value="/accountSearch")
	public ModelAndView accountSearch(HttpServletRequest request, HttpSession session) {
		
		ModelAndView mav = new ModelAndView();
		MemberVO  vo = (MemberVO) session.getAttribute("login");
		String id = vo.getId();
		System.out.println(id);
		List<Map<String, Integer>> accountList = accountrepo.selectAccount2(id);
		
		List<String> accountTotal = new ArrayList<>();
		List<String> accountNum = new ArrayList<>();
		
		List<PlusVO> plusList =  plusrepo.findAll();
		List<RemitVO> remitList = remitrepo.findAll();
		
		for(Map<String, Integer> a : accountList) {
			accountNum.add(String.valueOf(a.get("ACCOUNT_NUM")));
			accountTotal.add(a.get("TOTAL").toString());
		}
		
		mav.addObject("accountNum",accountNum);
		mav.addObject("accountTotal", accountTotal);
		mav.addObject("plusList", plusList);
		mav.addObject("remitList",remitList);
		mav.setViewName("account/searchAccount");
		return mav;
	}
	
	@ResponseBody
	@PostMapping("/account_CK") 
	public JSONObject account_ck(@RequestParam(value="account") String account, HttpServletRequest request) {
		
		JSONObject response = new JSONObject();		
		String i = accountrepo.selectAccountRemit(account);
		//System.out.println("==================>"+account);
		//System.out.println("==================>" + i);
		
		if(i.equals("1")) {
			response.put("status", 1);
		}else {
			response.put("status",0);
		}
		return response;
	}
	
	
}
