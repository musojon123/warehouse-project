package uz.pdp.appwarehouse.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import uz.pdp.appwarehouse.entity.Attachment;
import uz.pdp.appwarehouse.payload.Result;
import uz.pdp.appwarehouse.service.AttachmentService;

import java.util.List;

@RestController
@RequestMapping("/attachment")
public class AttachmentController {
    @Autowired
    AttachmentService attachmentService;

    @PostMapping("/upload")
    public Result upload(MultipartHttpServletRequest request){
        return attachmentService.uploadFile(request);
    }

    @GetMapping
    public List<Attachment> getAttachments(){
        return attachmentService.getAttachments();
    }

    @GetMapping("/{id}")
    public Attachment getAttachment(Integer id){
        return attachmentService.getAttachment(id);
    }

    @PutMapping("/{id}")
    public Result editAttachment(@PathVariable Integer id, MultipartHttpServletRequest request){
        return attachmentService.editAttachment(id, request);
    }

    @DeleteMapping("/{id}")
    public Result deleteAttachment(@PathVariable Integer id){
        return attachmentService.deleteAttachment(id);
    }
}
