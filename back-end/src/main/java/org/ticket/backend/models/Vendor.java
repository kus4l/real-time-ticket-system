package org.ticket.backend.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Vendor
{
    @Id
    private int vendorId;
    private String vendorName;
    private String vendorEmail;
    private String vendorPhone;
}
