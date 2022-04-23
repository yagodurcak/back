package ar.com.indumet.workload.models.vos;

import ar.com.indumet.workload.models.entities.ProviderEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Objects;

@Data
public class ProviderVO {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("description")
    private String description;

    @JsonProperty("contact")
    private String contact;

    @JsonProperty("mail")
    private String mail;

    @JsonProperty("cuit")
    private String cuit;

    @JsonProperty("phone")
    private String phone;

    @JsonProperty("address")
    private String address;

    public ProviderVO(){    }

    public ProviderVO(ProviderEntity providerEntity) {
        this.id = providerEntity.getId();
        this.name = providerEntity.getName();
        this.address = providerEntity.getAddress();
        this.description = providerEntity.getDescription();
        this.phone = providerEntity.getPhone();
        this.contact = providerEntity.getContact();
        this.cuit = providerEntity.getCuit();
        this.mail= providerEntity.getMail();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProviderVO that = (ProviderVO) o;
        return id.equals(that.id)
                && Objects.equals(name, that.name)
                && Objects.equals(address, that.address)
                && Objects.equals(description, that.description)
                && Objects.equals(contact, that.contact)
                && Objects.equals(mail, that.mail)
                && Objects.equals(cuit, that.cuit)
                && Objects.equals(phone, that.phone);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, name, address, description, contact, mail, cuit, phone);
    }

    public ProviderEntity toEntity(){
        ProviderEntity providerEntity = new ProviderEntity();
        providerEntity.setId(this.id);
        providerEntity.setName(this.name);
        providerEntity.setAddress(this.address);
        providerEntity.setDescription(this.description);
        providerEntity.setContact(this.contact);
        providerEntity.setMail(this.mail);
        providerEntity.setCuit(this.cuit);
        providerEntity.setPhone(this.phone);
        return providerEntity;
    }
}
