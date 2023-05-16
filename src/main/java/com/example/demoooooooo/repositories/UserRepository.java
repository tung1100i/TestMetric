package com.example.demoooooooo.repositories;


import com.example.demoooooooo.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {


    @Query(value = "select * from NHANVIEN where hoten like %?1%", nativeQuery = true)
    List<UserEntity> findAllByName(String name);

    @Query(value = "select * from NHANVIEN where diachi like %?1%", nativeQuery = true)
    List<UserEntity> findAllByAddress(String address);

    @Query(value = "select * from NHANVIEN where sodienthoai like %?1", nativeQuery = true)
    List<UserEntity> findByPhone(String phone);

    @Query(value = "INSERT INTO NHANVIEN VALUES (:id,:name,:add,:gender,:phone)", nativeQuery = true)
    UserEntity insertById(@Param("id") Long id,@Param("name") String name,@Param("add") String add,@Param("gender") String gender,@Param("phone") String phone);

    @Query(value = "UPDATE NHANVIEN nv SET nv.hoten=:name,nv.diachi=:add,nv.gioitinh=:gender,nv.sodienthoai=:phone WHERE nv.id=:id;", nativeQuery = true)
    void updateById(@Param("id") Long id,@Param("name") String name,@Param("add") String add,@Param("gender") String gender,@Param("phone") String phone);


}
