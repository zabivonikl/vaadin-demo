package com.zabivonikl.vaadindemo.data.entity;

import javax.persistence.*;

@MappedSuperclass
public abstract class AbstractEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "idGenerator")
    @SequenceGenerator(name = "idGenerator")
    private Long id;

    @Version
    private int version;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getVersion() {
        return version;
    }

    @Override
    public int hashCode() {
        if (getId() != null) {
            return getId().hashCode();
        }
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof AbstractEntity other)) {
            return false;
        }

        if (getId() != null) {
            return getId().equals(other.getId());
        }
        return super.equals(other);
    }
}
