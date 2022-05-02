package Redis;

import org.fatmansoft.teach.dto.CourseRankDTO;
import org.fatmansoft.teach.dto.CourseRankDTOSerializator;
import org.fatmansoft.teach.repository.GlobalScoreRepository;
import org.fatmansoft.teach.service.GlobalScoreService;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.annotation.Resource;

public class RedisRepositoryTest {
    @Test
    public void test(){
        GlobalScoreRepository globalScoreRepository  = new GlobalScoreRepository();
        globalScoreRepository.setString("test","test");
    }
}
