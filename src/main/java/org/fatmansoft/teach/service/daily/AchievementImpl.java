package org.fatmansoft.teach.service.daily;

import org.fatmansoft.teach.repository.daily.AchievementRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class AchievementImpl {
    @Resource
    private AchievementRepository achievementRepository;
}
