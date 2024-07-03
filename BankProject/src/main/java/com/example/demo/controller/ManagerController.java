package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.jpa.ProductRepo;

import com.example.demo.vo.ProductManagerVO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ch.qos.logback.core.model.Model;
import jakarta.validation.Valid;


@Controller
public class ManagerController {
	
	@Autowired
	ProductRepo pr;
	
	ModelAndView mav = new ModelAndView();
	
	@RequestMapping(value = "productList")
	public ModelAndView productList() {
		mav.setViewName("admin/createProduct");
		return mav;
	}
	
	@RequestMapping(value="mainManager")
	public ModelAndView main() {
		List<ProductManagerVO> list = pr.showMain();
		mav.addObject("list", list);
		mav.setViewName("admin/mainManager");
		return mav;
	}
	
	@RequestMapping(value="savingsManager")
	public ModelAndView savings() {
		List<ProductManagerVO> list = pr.showSavings();
		mav.addObject("list", list);
		mav.setViewName("admin/savingsManager");
		return mav;
	}
	
	@RequestMapping(value="depositManager")
	public ModelAndView deposit() {
		List<ProductManagerVO> list = pr.showDeposit();
		mav.addObject("list", list);
		mav.setViewName("admin/depositManager");
		return mav;
	}
	
	@RequestMapping(value="detailSavings")
	public ModelAndView detail(@RequestParam(name="product_num") String num) {
		Optional<ProductManagerVO> list = pr.findById(num);
		ProductManagerVO savings = list.get();
		System.out.println(">>>>>>>>>>>>>>>"+savings);
		mav.addObject("savings", savings);
		mav.setViewName("admin/selectSavings");
		return mav;
	}
	
	@RequestMapping(value="detailDeposit")
	public ModelAndView detailDeposit(@RequestParam(name="product_num") String num) {
		Optional<ProductManagerVO> list = pr.findById(num);
		ProductManagerVO deposit = list.get();
		System.out.println(">>>>>>>>>>>>>>>"+deposit);
		mav.addObject("deposit", deposit);
		mav.setViewName("admin/selectDeposit");
		return mav;
	}
	
	@RequestMapping(value = "detailMain")
	public ModelAndView detailMain(@RequestParam(name="product_num") String num) {
		Optional<ProductManagerVO> list = pr.findById(num);
		ProductManagerVO main = list.get();
		mav.addObject("main", main);
		mav.setViewName("admin/selectMain");
		return mav;
	}
	
	@RequestMapping(value="modifySavings") //수정하기 곧 할거임 나중에,,,,,헤헷
	public ModelAndView modify() {
		return mav;
	}

	@RequestMapping(value="createDeposit" )
	public ModelAndView createDeposit() {
		mav.setViewName("admin/createDeposit");
		return mav;
	}
	
	@RequestMapping(value="createSaving")
	public ModelAndView createSaving() {
		mav.setViewName("admin/createSaving");
		return mav;
	}
	
	@RequestMapping(value="/createMain")
	public ModelAndView createMain()  {
		List<String> num = pr.product_num();
		System.out.println(num);
		mav.addObject("num", num);
		mav.setViewName("admin/createMain");
		return mav;
	}
	
	@ResponseBody
	@RequestMapping(value = "/productNum_CK")
	public JSONObject productNumCK(@RequestParam("product_num") String product_num) {
		JSONObject json = new JSONObject();
		int i = pr.productNum_CK(product_num);
		System.out.println(i);
		json.put("data", i);
		
	    String num = "";
	    if (product_num.startsWith("#1")) {
	        num = pr.main_ck();
	        System.out.println(num);
	    } else if (product_num.startsWith("#2")) {
	        num = pr.deposit_ck();
	        System.out.println(num);
	    } else if (product_num.startsWith("#3")) {
	        num = pr.savings_ck();
	        System.out.println(num);
	    }
		json.put("num", num);
		System.out.println(num);
		
		return json;
	}
	
	/*@RequestMapping(value="createComplete")
	public String insert(@Valid @ModelAttribute("smv") SavingsManagerVO smv,  BindingResult binding, ModelAndView mav) {
		if(binding.hasErrors()) {
			return "admin/createSavings";
		}
		smr.save(smv);*/
		
	@RequestMapping(value="createProductComplete")
	public ModelAndView insert(ProductManagerVO pmvo) {
		pr.save(pmvo);
		//mav.addObject("result", "상품이 등록 되었습니다.");
		//mav.setViewName("redirect:/savingsManager");
		mav.setViewName("admin/managePage");
		return mav;
	}
	
//	@RequestMapping(value="depositManager")
//	public ModelAndView depositManager() {
//		
//		List<ProductManagerVO> list = pr.showDeposit();
//		mav.addObject("list", list);
//		mav.setViewName("admin/depositManager");
//		return mav;
//	}
	
}
