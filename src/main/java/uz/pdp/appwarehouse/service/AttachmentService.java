package uz.pdp.appwarehouse.service;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import uz.pdp.appwarehouse.entity.Attachment;
import uz.pdp.appwarehouse.entity.AttachmentContent;
import uz.pdp.appwarehouse.payload.Result;
import uz.pdp.appwarehouse.repository.AttachmentContentRepository;
import uz.pdp.appwarehouse.repository.AttachmentRepository;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Service
public class AttachmentService {

    @Autowired
    AttachmentRepository attachmentRepository;
    @Autowired
    AttachmentContentRepository attachmentContentRepository;


    @SneakyThrows
    public Result uploadFile(MultipartHttpServletRequest request){
        Iterator<String> fileNames = request.getFileNames();
        MultipartFile file = request.getFile(fileNames.next());
        Attachment attachment = new Attachment();
        attachment.setName(file.getName());
        attachment.setSize(file.getSize());
        attachment.setContentType(file.getContentType());
        Attachment savedAttachment = attachmentRepository.save(attachment);

        AttachmentContent attachmentContent = new AttachmentContent();
        attachmentContent.setBytes(file.getBytes());
        attachmentContent.setAttachment(savedAttachment);
        attachmentContentRepository.save(attachmentContent);
        return new Result("File successfully added" ,true, savedAttachment.getId());
    }

    public List<Attachment> getAttachments(){
        return attachmentRepository.findAll();
    }

    public Attachment getAttachment(Integer id){
        if (!attachmentRepository.existsById(id))
            return new Attachment();
        return attachmentRepository.getById(id);
    }

    @SneakyThrows
    public Result editAttachment(Integer id, MultipartHttpServletRequest request){
        if (!attachmentRepository.existsById(id))
            return new Result("No such Attachment with this id", false);
        Attachment editingAttachment = attachmentRepository.getById(id);
        Iterator<String> fileNames = request.getFileNames();
        MultipartFile file = request.getFile(fileNames.next());
        editingAttachment.setName(file.getName());
        editingAttachment.setSize(file.getSize());
        editingAttachment.setContentType(file.getContentType());
        Attachment editedAttachment = attachmentRepository.save(editingAttachment);

        Optional<AttachmentContent> optionalAttachmentContent = attachmentContentRepository.findAttachmentContentByAttachmentId(id);
        if (optionalAttachmentContent.isEmpty())
            return new Result("Bunday fayl mavjud emas", false);
        AttachmentContent editingAttachmentContent = optionalAttachmentContent.get();
        editingAttachmentContent.setBytes(file.getBytes());
        editingAttachmentContent.setAttachment(editedAttachment);
        attachmentContentRepository.save(editingAttachmentContent);
        return new Result("Attachment successfully edited", true);
    }

    public Result deleteAttachment(Integer id){
        if (!attachmentRepository.existsById(id))
            return new Result("No such attachment with this id", false);
        attachmentRepository.deleteById(id);
        attachmentContentRepository.deleteByAttachmentId(id);
        return new Result("Attachment successfully deleted", true);
    }
}
