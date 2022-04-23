package ar.com.indumet.workload.models.entities;

import lombok.Data;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Objects;

@Data
@Entity
@Table(name = "providers", schema = "indumet-workload", catalog = "")
@Where(clause = "deleted IS NULL")
public class ProviderEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Basic
    @Column(name = "name")
    private String name;

    @Basic
    @Column(name = "description")
    private String description;

    @Basic
    @Column(name = "address")
    private String address;

    @Basic
    @Column(name = "contact")
    private String contact;

    @Basic
    @Column(name = "phone")
    private String phone;

    @Basic
    @Column(name = "mail")
    private String mail;

    @Basic
    @Column(name = "cuit")
    private String cuit;

    @Basic
    @Column(name = "created")
    private Timestamp created;

    @Basic
    @Column(name = "deleted")
    private Timestamp deleted;

    public ProviderEntity(){
        this.created = Timestamp.from(Instant.now());
    }

    public ProviderEntity(Long id, String name, String address, String description, String contact, String phone, String mail, String cuit, Timestamp created) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.description = description;
        this.contact = contact;
        this.phone = phone;
        this.mail = mail;
        this.cuit = cuit;

        if(Objects.isNull(created))
            this.created = Timestamp.from(Instant.now());
        else
            this.created = created;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProviderEntity that = (ProviderEntity) o;
        return id.equals(that.id)  &&
                Objects.equals(name, that.name) &&
                Objects.equals(address, that.address) &&
                Objects.equals(description, that.description) &&
                Objects.equals(contact, that.contact) &&
                Objects.equals(phone, that.phone) &&
                Objects.equals(mail, that.mail) &&
                Objects.equals(cuit, that.cuit) &&
                Objects.equals(created, that.created);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, address, description, contact, phone, mail, cuit, created);
    }
}