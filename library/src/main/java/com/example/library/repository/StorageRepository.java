package com.example.library.repository;

import com.example.library.entity.Storage;
import com.example.library.more.StorageMore;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StorageRepository extends JpaRepository<Storage, Long> {
    @Query("select storage from Storage storage")
    public List<Storage> findAllStorage(Pageable pageable);

    public List<Storage> findAllByLocation(String location, Pageable pageable);
    public List<Storage> findAllByPhone(Long phone, Pageable pageable);

    public Storage findFirstById(Long id);
}
