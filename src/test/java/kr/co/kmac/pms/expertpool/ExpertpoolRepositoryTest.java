package kr.co.kmac.pms.expertpool;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import kr.co.kmac.pms.common.security.repository.SecurityRepository;

@SpringBootTest
class ExpertpoolRepositoryTest {

	@Autowired
	private SecurityRepository expertpoolRepository;

//	@BeforeAll
//	static void setUpBeforeClass() throws Exception {
//	}
//
//	@AfterAll
//	static void tearDownAfterClass() throws Exception {
//	}
//
//	@BeforeEach
//	void setUp() throws Exception {
//	}
//
//	@AfterEach
//	void tearDown() throws Exception {
//	}

	@Test
	void testEncodePassword() {
		String enc = expertpoolRepository.getEncPassword("1");
		System.out.println(enc);
	}



}