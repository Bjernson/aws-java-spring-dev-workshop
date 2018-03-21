package hello.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.model.Region;
import com.amazonaws.regions.RegionUtils;

import hello.logics.IntegratedTrans;
import hello.repository.mysql.UserRepository;

@Controller
@RequestMapping(path="/workshop")
public class LogicsController {
	
	@Autowired 
	private IntegratedTrans trans;
	
	//add to test a integrate logics
	@GetMapping(path = "/trans/integrated")
  public @ResponseBody String transIntegrated(@RequestParam  String bucket
  		, @RequestParam String prefix, @RequestParam String region) {
			Regions _region = Regions.fromName(region);
  			trans.RetrieveAndSave(bucket, prefix, _region);
      return "Transferred.";
  } 
  
  
}
