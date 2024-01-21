package madcamp.week4.service;

import madcamp.week4.model.Organization;
import madcamp.week4.repository.OrganizationRepository;
import madcamp.week4.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrganizationService {

    // 방생성 (초대코드 받아오기)
    // 방들어가기
    // 특정 그룹을 눌렀을때 해당하는 사람들의 리스트의 파일이 모두 떠야함

    @Autowired
    private OrganizationRepository organizationRepository;


}
