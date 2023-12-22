package com.project.SMSTwilio.repository;

import com.project.SMSTwilio.entity.OneTimePassword;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OneTimePasswordRepository extends JpaRepository<OneTimePassword, Long> {

    OneTimePassword findTopByUserIdAndCode(Long userId, String code);
    OneTimePassword findTopByUserIdOrderByIdDesc(Long id);
}
