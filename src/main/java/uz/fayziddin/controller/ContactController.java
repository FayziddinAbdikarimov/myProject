package uz.fayziddin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.fayziddin.service.AttachmentService;
import uz.fayziddin.service.ContactService;

@Controller
@RequestMapping("/contact")
public class ContactController {

    @Autowired
    ContactService contactService;

    @Autowired
    AttachmentService attachmentService;

    @PostMapping("/add")
    public String addContact(@RequestParam String first_name, @RequestParam String last_name,
                             @RequestParam String phone, @RequestParam String email,
                             @RequestParam int attachment_id, Model model){
        contactService.addContact(first_name, last_name, phone, email, attachment_id);
        model.addAttribute("contacts", contactService.getContactList());
        return "cabinet";
    }
    @GetMapping("/edit/{id}")
    public String getEditPage(@PathVariable int id, Model model){
        model.addAttribute("aContact", contactService.getContactById(id));
        return "editContact";
    }
    @PostMapping("/update")
    public String updateContact(@RequestParam int id, @RequestParam String first_name, @RequestParam String last_name,
                             @RequestParam String phone, @RequestParam String email, Model model){
        contactService.editContact(id,first_name, last_name, phone, email);
        model.addAttribute("contacts", contactService.getContactList());
        return "cabinet";
    }
    @GetMapping("/delete/{id}")
    public String getDeletePage(@PathVariable int id, Model model){
        contactService.deleteContact(id);
        model.addAttribute("contacts", contactService.getContactList());
        return "cabinet";
    }
    @PostMapping("/ilova")
    @ResponseBody
    public Integer saveFile(@RequestParam(name = "file") MultipartFile multipartFile){
           return attachmentService.saveAttachment(multipartFile);
    }

    @GetMapping("/ilova/{id}")
    @ResponseBody
    public byte[] getFileContent(@PathVariable int id){
        return attachmentService.getContent(id);
    }


}
