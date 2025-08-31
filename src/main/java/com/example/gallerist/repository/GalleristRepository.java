package com.example.gallerist.repository;

import com.example.gallerist.model.Gallerist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public  interface GalleristRepository extends JpaRepository<Gallerist,Long> {

}
