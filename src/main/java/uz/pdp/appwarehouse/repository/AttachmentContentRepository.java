package uz.pdp.appwarehouse.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import uz.pdp.appwarehouse.entity.AttachmentContent;

import java.util.Optional;

public interface AttachmentContentRepository extends JpaRepository<AttachmentContent, Integer> {
    @Query("select a from AttachmentContent a where a.attachment.id = ?1")
    Optional<AttachmentContent> findAttachmentContentByAttachmentId(Integer id);

    @Modifying
    @Query("delete from AttachmentContent a where a.attachment.id = ?1")
    void deleteByAttachmentId(Integer id);
}
