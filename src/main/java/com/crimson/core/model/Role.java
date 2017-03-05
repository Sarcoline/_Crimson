package com.crimson.core.model;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Entity
@Table(name = "Role")
public @Data class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idRole")
    private Long idRole;

    @Column(name = "roleName")
    @NotNull
    @Pattern(regexp = "[\\w-]+", message = "{invalid.pattern.role}")
    @Size(min = 3, max = 20, message = "{invalid.size.role}")
    private String roleName;

    @Version
    int version;

    @Builder
    public Role(String roleName){
        this.roleName = roleName;
    }

    @ManyToMany(mappedBy = "roles", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<User> users = new ArrayList<>();

}
