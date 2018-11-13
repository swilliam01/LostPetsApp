package com.example.demo;

import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.Map;

@Controller
public class HomeController {

  @Autowired
  PetRepository petRepository;


  @Autowired
  private UserService userService;

  @Autowired
  UserRepository userRepository;

  @Autowired
  CloudinaryConfig cloudc;


  @RequestMapping("/")
  public String listPets (Model model) {
    model.addAttribute("pets", petRepository.findAll());
    if(userService.getUser() != null) {
      model.addAttribute("user_id", userService.getUser().getId());
    }
    return "list";
  }

  @GetMapping("/register")
  public String showRegistrationPage(Model model) {
    model.addAttribute("user", new User());
    return "registration";
  }

  @PostMapping("/register")
  public String processRegistrationPage(@Valid
                                        @ModelAttribute("user") User user, BindingResult result,
                                        Model model) {
    model.addAttribute("user", user);
    if (result.hasErrors()) {
      return "registration";
    } else {
      userService.saveUser(user);
      model.addAttribute("pet", "User Account Created");
    }
    return "login";
  }

  @RequestMapping("/login")
  public String login() {
    return "login";
  }

  @GetMapping("/add")
  public String listNow(Model model) {
    model.addAttribute("pet", new Pet());
    return "listform";
  }

  @PostMapping("/process")
  public String processList(@Valid @ModelAttribute("pet") Pet pet, BindingResult result,
                            @RequestParam("file")MultipartFile file){
    if (result.hasErrors()) {
      return "listform";
    }
    pet.setUser(userService.getUser());
    petRepository.save(pet);
//    return "redirect:/";

    if(file.isEmpty()){
      return "redirect:/add";
    }
    try{
      Map uploadResult = cloudc.upload(file.getBytes(),
              ObjectUtils.asMap("resourcetype", "auto"));
      pet.setHeadshot(uploadResult.get("url").toString());
      petRepository.save(pet);
    }catch (IOException e){
      e.printStackTrace();
      return "redirect:/add";
    }
    return "redirect:/";

  }

  @RequestMapping("/edit/{id}")
  public String update(@PathVariable("id") long id, Model model) {
      model.addAttribute("pet", petRepository.findById(id).get());
      return "listform";
    }

//    @RequestMapping("/details/{id}")
//    public String showDetails ( @PathVariable("id") long id, Model model){
//      model.addAttribute("pet", petRepository.findById(id).get());
//      return "show";
//    }
    @RequestMapping("/delete/{id}")
    public String delete ( @PathVariable("id") long id){
      petRepository.deleteById(id);
      return "redirect:/";
    }
  @RequestMapping("/status/{id}")
  public String changeStatus ( @PathVariable("id") long id, Model model){
    model.addAttribute("pet", petRepository.findById(id).get());
    return "changeStatus";
  }

  }
