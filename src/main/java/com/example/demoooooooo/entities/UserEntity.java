package com.example.demoooooooo.entities;

import lombok.*;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
@Table(name = "nhanvien")
public class UserEntity {

//    @GeneratedValue(strategy = GenerationType.SEQUENCE)

    @Id
    private Long id;
    @Column(name = "hoten")
    private String name;
    @Column(name = "diachi")
    private String address;
    @Column(name = "gioitinh")
    private String gender;
    @Column(name = "sodienthoai",nullable = false)
    private String phone;

    @Override
    public String toString() {
        return "UserEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", gender='" + gender + '\'' +
                ", phone=" + phone +
                '}';
    }
}