package uz.pdp.appwarehouse.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.pdp.appwarehouse.entity.template.AbstractEntity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Product extends AbstractEntity {

    @ManyToOne(optional = false)
    private Category category;

    @OneToOne
    private Attachment photo;

    private String code;

    @ManyToOne
    private Measurement measurement;
}
