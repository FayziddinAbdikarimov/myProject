package uz.fayziddin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import uz.fayziddin.service.ContactService;


@Controller
public class CabinetController {

    @Autowired
    ContactService contactService;

    @GetMapping("/")
    public String getCabinetPage(Model model){
        model.addAttribute("title", "Contacts List");
        model.addAttribute("contacts", contactService.getContactList());
        return "cabinet";
    }
    @GetMapping("/addModal")
    public String showModal(){
        return "addContact";
    }

}
