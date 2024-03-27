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

    @Query("select storage from Storage storage where (:status is null or storage.status = :status)")
    public List<Storage> findStorageByStatus(@Param("status") String status, Pageable pageable);

    @Query("select count(storage.id) from Storage storage")
    public int getCountAllStorage();

    @Query("select bookStorage.id from Storage storage inner join storage.bookStoragesFromStorage bookStorage on storage.id = bookStorage.storageToBookStorage.id")
    public List<Long> findAllBookStorageId();

    public List<Storage> findAllByLocation(String location, Pageable pageable);

    public int countStorageByLocation(String location);

    public List<Storage> findAllByPhone(Long phone, Pageable pageable);

    public int countStorageByPhone(Long phone);

    public Storage findFirstById(Long id);
    public Storage findFirstByOrderByIdDesc();
}
