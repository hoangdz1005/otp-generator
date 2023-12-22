package com.project.SMSTwilio.service;
import com.project.SMSTwilio.entity.OneTimePassword;
import com.project.SMSTwilio.repository.OneTimePasswordRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class OneTimePasswordService {

    private final OneTimePasswordRepository oneTimePasswordRepository;

    public OneTimePasswordService(OneTimePasswordRepository oneTimePasswordRepository) {
        this.oneTimePasswordRepository = oneTimePasswordRepository;
    }

    public void save(OneTimePassword oneTimePassword){
        oneTimePasswordRepository.save(oneTimePassword);
    }

    public OneTimePassword findTopByUserId(Long id){
        return oneTimePasswordRepository.findTopByUserIdOrderByIdDesc(id);
    }

    public List<OneTimePassword> getAll() {
        return oneTimePasswordRepository.findAll();
    }

}
