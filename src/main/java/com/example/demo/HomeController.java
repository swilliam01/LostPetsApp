package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.jws.WebParam;
import javax.validation.Valid;
import java.security.Principal;

@Controller
public class HomeController {

  @Autowired
  MessageRepository messageRepository;


  @Autowired
  private UserService userService;


    @RequestMapping("/secure")
    public String secure(Principal principal, Model model){
        User myuser =((CustomUserDetails)((UsernamePasswordAuthenticationToken)principal)
                .getPrincipal()).getUser();
        model.addAttribute("myuser",myuser);
        return "secure";
    }

    @RequestMapping("/")
    public String listCourses(Model model){
        model.addAttribute("messages", messageRepository.findAll());
        return "list";
    }

  @GetMapping("/register")
  public String showRegistrationPage(Model model){
    model.addAttribute("user", new User());
    return"registration";
  }

  @PostMapping("/register")
  public String processRegistrationPage(@Valid
                                        @ModelAttribute("user") User user, BindingResult result,
                                        Model model){
    model.addAttribute("user",user);
    if (result.hasErrors()){
      return "registration";
    }
    else{
      userService.saveUser(user);
      model.addAttribute("message", "User Account Created");
    }
    return "login";
  }

    @RequestMapping("/login")
    public String login(){
        return "login";
    }

  @GetMapping("/add")
  public String listNow (Model model){
    model.addAttribute("message", new Message());
    return "listform";
  }
  @PostMapping("/process")
  public String processList (@Valid @ModelAttribute("message") Message message, BindingResult result){
    if(result.hasErrors()){
      return "listform";
    }
    messageRepository.save(message);
    return "redirect:/";
  }

  @RequestMapping("/edit/{id}")
  public String updateList (@PathVariable ("id") long id, Model model){
    model.addAttribute("message", messageRepository.findById(id).get());
    return "listform";
  }
  @RequestMapping("/details/{id}")
  public String showList (@PathVariable ("id") long id, Model model){
    model.addAttribute("message", messageRepository.findById(id).get());
    return "show";
  }
  @RequestMapping("/delete/{id}")
  public String delList (@PathVariable ("id") long id){
    messageRepository.deleteById(id);
    return "redirect:/";
  }
}
