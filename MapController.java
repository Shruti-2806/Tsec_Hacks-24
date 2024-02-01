package com.example.hacks;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MapController {
	
	@GetMapping("/map") 
	public String get(Model model) {
		Double l1[] = new Double[8];
		Double l2[] = new Double[8];
		String l3[] = new String[8];
		l1[0] = 19.07;
		l2[0] = 72.75;
		l1[1] = 19.06;
		l2[1] = 72.77;
		l1[2] = 19.08;
		l2[2] = 72.75;
		l1[3] = 19.07;
		l2[3] = 72.77;
		l1[4] = 19.05;
		l2[4] = 72.75;
		l1[5] = 19.1;
		l2[5] = 72.75;
		l1[6] = 19.06;
		l2[6] = 72.74;
		l1[7] = 19.06;
		l2[7] = 72.74;
		model.addAttribute("list1", l1);
        model.addAttribute("list2", l2);
        model.addAttribute("list3", l3);
        return "map.html";
	}
	
}
