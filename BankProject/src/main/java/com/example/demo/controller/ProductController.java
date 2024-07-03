package com.example.demo.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.beans.PropertyEditorSupport;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;


import com.example.demo.jpa.AccountRepo;
import com.example.demo.jpa.PlusRepo;
import com.example.demo.jpa.ProductRepo;
import com.example.demo.jpa.RemitRepo;
import com.example.demo.vo.AccountVO;
import com.example.demo.vo.MemberVO;
import com.example.demo.vo.ProductManagerVO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
public class ProductController {
	
	ModelAndView mav = new ModelAndView();
	
	@Autowired
	AccountRepo accountRepo;
		@Autowired
	ProductRepo pr;
	@Autowired
	PlusRepo plusrepo;
	@Autowired
	RemitRepo remitrepo;
	
	 @InitBinder
	    public void initBinder(WebDataBinder binder) {
	        binder.registerCustomEditor(Integer.class, "total", new PropertyEditorSupport() {
	            @Override
	            public void setAsText(String text) throws IllegalArgumentException {
	                // 콤마 제거 후 정수로 변환
	                setValue(Integer.parseInt(text.replace(",", "")));
	            }
	        });
	    }
	
	@RequestMapping(value="/depositList")
	public ModelAndView deposit() {
		ModelAndView mav = new ModelAndView();
		List<ProductManagerVO> depositVO =  pr.showDeposit();
		System.out.println(">>> " + depositVO);
		mav.addObject("depositVO", depositVO);
		mav.setViewName("products/depositList");
		return mav;
	}
	@RequestMapping(value="/savings")
	public ModelAndView savings() {
		List<ProductManagerVO> list = pr.showSavings();
		System.out.println(list);
		mav.addObject("list", list);
		mav.setViewName("products/savings");
		return mav;
	}
	@RequestMapping(value="/depositProduct")
	public ModelAndView depositProduct(HttpServletRequest request, HttpSession session) {
		MemberVO vo = (MemberVO) session.getAttribute("login");
		
		String product_num = request.getParameter("product_num");
		String id = vo.getId();
		List<Map<String, Integer>> accountList = accountRepo.selectAccount2(id);
		List<Integer> accountPW = accountRepo.selectAccountPW(id);
		
		List<String> accountTotal = new ArrayList<>();
		List<String> accountNum = new ArrayList<>();
		
		for(Map<String, Integer> a : accountList) {
			accountNum.add(String.valueOf(a.get("ACCOUNT_NUM")));
			accountTotal.add(a.get("TOTAL").toString());
		}
		
		ModelAndView mav = new ModelAndView();
		ProductManagerVO deposit = pr.getById(product_num);
		
		mav.addObject("accountNum",accountNum);
		mav.addObject("accountTotal", accountTotal);
		mav.addObject("accountPW", accountPW);
		mav.addObject("deposit", deposit);
		mav.setViewName("products/depositPage");
		return mav;
	}
	@RequestMapping(value="depositController")
	public ModelAndView depositController(HttpServletRequest request, ProductManagerVO pmvo) {
		ModelAndView mav = new ModelAndView();
		HttpSession session = request.getSession();
		MemberVO vo = (MemberVO) session.getAttribute("login");
		String proAccount_num = request.getParameter("proAccount_num").replace("-", "");
		
		String id = vo.getId();
		String  product_num = pmvo.getProduct_num();
		int account_pw = Integer.parseInt(request.getParameter("account_pw"));
		int total = Integer.parseInt(request.getParameter("total").replace(",", ""));
		String account_num = request.getParameter("account_num").replace("-", "");
		String deposit_name = request.getParameter("deposit_name");
		System.out.println(">>>>>>>>>>>>>>>" + account_num);
		System.out.println(">>>>>>>>>>>>>>>" + deposit_name);
		
		try {
			accountRepo.insertAccount(proAccount_num, id, product_num, account_pw, total);
		}catch (Exception e) {
		}
		try {
			plusrepo.insertProduct(proAccount_num, deposit_name, total);
		} catch (Exception e) {
		}
		try {
			remitrepo.insertRemit(account_num, account_num, deposit_name, total);
		} catch (Exception e) {
		}
		try {
			accountRepo.updateAccountMoney2(account_num, total);
		} catch (Exception e) {
		}

		mav.setViewName("index");
		return mav;
	
		
	}
	
	@RequestMapping(value="detailProduct")
	public ModelAndView detail(@RequestParam(name="product_num") String num, HttpSession session) {
		Optional<ProductManagerVO> list = pr.findById(num);
		ProductManagerVO savings = list.get();
		System.out.println(">>>>>>>>>>>>>>>"+savings);
		//DB에 저장하기 위해 session으로 넘겨줌.
		session.setAttribute("savings", savings);
		mav.addObject("savings", savings);
		mav.setViewName("products/detailSavings");
		return mav;
	}
	
	@RequestMapping(value="joinSavings")
	public ModelAndView join(HttpSession session) {
		MemberVO getid = (MemberVO) session.getAttribute("login");
		String id = getid.getId();
		List<String> account = new ArrayList<>();
		List<String> total = new ArrayList<>();
		List<Map<String, Integer>> selectNumTotal = remitrepo.selectNumTotal(id);
		List<Integer> accountPW = accountRepo.selectAccountPW(id);
		System.out.println(">>> " + accountPW);
		for(Map<String, Integer> a : selectNumTotal) {
			account.add(String.valueOf(a.get("ACCOUNT_NUM")));
			total.add(a.get("TOTAL").toString());
		}
		
		mav.addObject("account", account);
		mav.addObject("accountPW", accountPW);
		mav.addObject("total", total);
		mav.setViewName("products/joinSavings");
		return mav;
	}
	
	@RequestMapping(value="joinComplete")
	public String join(HttpServletRequest request, HttpSession session, ProductManagerVO pmvo) {
		
		MemberVO vo = (MemberVO) session.getAttribute("login");
		
		String proAccount_num = request.getParameter("proAccount_num").replace("-", "");
		String id = vo.getId();
		String  product_num = pmvo.getProduct_num();
		System.out.println(product_num);
		int account_pw = Integer.parseInt(request.getParameter("account_pw"));
		int total = Integer.parseInt(request.getParameter("total").replace(",", ""));
		
		String account_num = request.getParameter("account_num").replace("-", "");
		String deposit_name = request.getParameter("deposit_name");
		System.out.println(">>>>>>>>>>>>>>>" + account_num);
		System.out.println(">>>>>>>>>>>>>>>" + deposit_name);
		
		try {			
			accountRepo.insertAccount(proAccount_num, id, product_num, account_pw, total);		} catch (Exception e) {
		}
		try {			
			remitrepo.insertRemit(proAccount_num, account_num, deposit_name, total);
		} catch (Exception e) {
		}
		return "redirect:/savings?id="+id;
	}
	
	@RequestMapping(value="Termination")
	   public ModelAndView Termination(HttpServletRequest request, HttpSession session) {
	      ModelAndView mav = new ModelAndView();
	      String product_num = request.getParameter("product_num");
	      String account_num = request.getParameter("account_num");
	      
	      MemberVO member = (MemberVO)session.getAttribute("login");
	      ProductManagerVO product = pr.showProduct(product_num);
	      System.out.println(product);
	      AccountVO account = accountRepo.getById(account_num);
	      System.out.println(account);
	      
	      mav.addObject("account", account);
	      mav.addObject("member", member);
	      mav.addObject("product", product);
	      
	      mav.setViewName("products/Termination");
	      return mav;
	   }

	
	@RequestMapping(value="Termination_controller")
	public ModelAndView Termination_controller(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		String account_num = request.getParameter("account_num");
		int addMoney = Integer.parseInt(request.getParameter("addMoney"));
		try {
			accountRepo.updateAccountMoney(account_num, addMoney);
		} catch (Exception e) {
			// TODO: handle exception
		}
		try {
			plusrepo.insertPlus(account_num, addMoney);
		} catch (Exception e) {
			// TODO: handle exception
		}
		mav.setViewName("index");
		return mav;
	}
	
	@ResponseBody
	@RequestMapping(value="/account_ckeck")
	public int accountCK(HttpSession session, HttpServletRequest request) {
		MemberVO login= (MemberVO) session.getAttribute("login");
		String id = login.getId();
		System.out.println(id);
		int i = pr.account_ckeck(id);
		System.out.println(i);
		return i;
	}
	
	@RequestMapping(value="mainAccountList")
	public ModelAndView accountMainList(HttpSession session) {
		MemberVO vo = (MemberVO) session.getAttribute("login");
		vo.getId();
		List<ProductManagerVO> depositVO =  pr.showMain();
		System.out.println(">>>>>>>>>>>>>>>>>>>>>"+depositVO);
		//session.setAttribute("depositVO", depositVO);
		mav.addObject("depositVO",depositVO);
		mav.setViewName("products/mainAccount");
		return mav;
	}
	
	@RequestMapping(value="mainAccountDetail")
	public ModelAndView mainAccountJoin(HttpSession session, @RequestParam("product_num") String num) {
		MemberVO vo = (MemberVO) session.getAttribute("login");
		vo.getId();
		ProductManagerVO mainList = pr.getById(num);
		System.out.println(mainList);
		mav.addObject("mainList", mainList);
		session.setAttribute("num", num);
		//session.setAttribute("mainList", mainList);
		mav.setViewName("products/mainAccountJoin");
		return mav;
	}
		
	@PostMapping("accountComplete")
	public ModelAndView accountComplete(HttpServletRequest request, HttpSession session) {
		String account_num = request.getParameter("account_num").replace("-", "");
		String id = request.getParameter("id");
		String product_num = (String) session.getAttribute("num");
		System.out.println(session.getId());
		int account_pw = Integer.parseInt(request.getParameter("account_pw"));
		int total = Integer.parseInt(request.getParameter("total").replace(",", ""));
		
		try {
			accountRepo.insertAccount(account_num,  id, product_num, account_pw, total);
		} catch (Exception e) {
			// TODO: handle exception
		}
		try {
			plusrepo.insertPlus(account_num, total);
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		mav.addObject("result","계좌 개설 완료");
		mav.setViewName("products/accountComplete");
		return mav;
	}
	
}
